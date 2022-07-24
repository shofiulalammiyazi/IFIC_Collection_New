package com.unisoft.dms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.unisoft.utillity.StringUtils;
import com.unisoft.cryptography.AESCrypto;
import com.unisoft.cryptography.CryptoException;
import com.unisoft.dms.logicdoc.LogiDocDao;
import com.unisoft.dms.logicdoc.LogiDocSettingsEntity;
import lombok.RequiredArgsConstructor;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConnectionException;
import org.apache.chemistry.opencmis.commons.impl.MimeTypes;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class DmsFileSaver {

    private final StringUtils stringUtils;
    private final LogiDocDao logiDocDao;

    /**
     * Modification made according to Logicaldoc version 8.x.x and CMIS documentation
     * Applicable for logicaldoc-8.x.x-tomcat-bundle.zip (Operating System Independent)
     * Modified by    on 29 April 2021
     *
     * @return CMIS Session or null if failed to create session
     * @throws CmisConnectionException
     */
    public Session cmisSession() throws CmisConnectionException {
        try {

            LogiDocSettingsEntity lDoc = logiDocDao.findLogiDocData();
            SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
            Map<String, String> parameter = new HashMap<>();
            parameter.put(SessionParameter.USER, lDoc.getUsername());
            parameter.put(SessionParameter.PASSWORD, lDoc.getPassword());
            parameter.put(SessionParameter.ATOMPUB_URL, "http://" + lDoc.getIp() + ":" + lDoc.getPort() + "/logicaldoc/service/cmis");
            parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
            List<Repository> repositories = sessionFactory.getRepositories(parameter);
            Repository repository = repositories.get(0);
            parameter.put(SessionParameter.REPOSITORY_ID, repository.getId());
            Session session = sessionFactory.createSession(parameter);
            return session;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Folder folderCreate(String folderName, String path, Session session) {
        try {

            Folder root = (Folder) session.getObjectByPath(path);
            Map<String, String> newFolderProps = new HashMap<>();
            newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
            newFolderProps.put(PropertyIds.NAME, folderName);
            root.createFolder(newFolderProps);
            return root;

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Modified by    at 29 April 2021
     * Modifications:
     * 1. Remove repetitive cmisSession() call by session parameter,
     * 2. Create folders by path instead of by static folder name parameters
     *
     * @param fileUpload
     * @param path
     * @param fileName
     * @param session
     * @return ID of created document or empty String if failed to create document
     * @throws IOException
     * @throws CryptoException
     */
    public String saveFileToDmsFilePath(MultipartFile fileUpload, String path, String fileName, Session session) throws IOException, CryptoException {
        Folder targetFolder = createFolderByPath(path, session);
        return targetFolder != null ? getIDofCreatedDoc(targetFolder, fileUpload, fileName) : "";
    }

    public String saveFileToDmsFilePath(MultipartFile fileUpload, String path, String fileName) throws IOException, CryptoException {
        Session cmisSession = cmisSession();
        return saveFileToDmsFilePath(fileUpload, path, fileName, cmisSession);
    }

    /**
     * Created by    on 29 April 2021
     *
     * @param path
     * @param session
     * @return CMIS Folder or null if failed to create path
     */
    private Folder createFolderByPath(String path, Session session) {
        Folder targetFolder = null;
        String parentFolderName = "/Default"; // '/Default' is the root folder
        String[] folderNames = org.springframework.util.StringUtils.hasText(path) ? path.split("/") : new String[0];
        // Create folders in the path sequentially
        for (String currentFolderName : folderNames) {
            String expectedPath = parentFolderName + "/" + currentFolderName;
            if (!session.existsPath(expectedPath)) {
                targetFolder = folderCreate(currentFolderName, parentFolderName, session);
            }
            parentFolderName += "/" + currentFolderName;
        }
        // Return the folder
        if (session.existsPath(parentFolderName)) {
            targetFolder = (Folder) session.getObjectByPath(parentFolderName);
        }
        return targetFolder;
    }

    public String getIDofCreatedDoc(Folder targetFolder, MultipartFile fileUpload) throws IOException, CryptoException {
        String filename = Optional.ofNullable(fileUpload.getOriginalFilename()).orElse("");
        if (filename.contains(".")) {
            List<String> s = stringUtils.split(filename, ".");
            s.remove(s.size() - 1);
            filename = String.join(".", s);
        }
        String fileType = fileUpload.getContentType();
        Document doc = createFileToDms(targetFolder, filename, fileType, fileUpload.getInputStream());
        return doc.getId();
    }

    public String getIDofCreatedDoc(Folder targetFolder, MultipartFile fileUpload, String fileName) throws IOException, CryptoException {
        String fileType = fileUpload.getContentType();
        Document doc = createFileToDms(targetFolder, fileName, fileType, fileUpload.getInputStream());
        return doc.getId();
    }

    public Document createFileToDms(Folder folder, String filename, String fileType, InputStream inputStream) throws IOException, CryptoException {
        //encrypt
        String key = AESCrypto.ENCRYPTION_KEY;
        byte[] encryptedBytes = AESCrypto.encrypt(key, IOUtils.toByteArray(inputStream));

        ContentStream contentStream = new ContentStreamImpl(filename, null, fileType, new ByteArrayInputStream(encryptedBytes));
        Map<String, Object> properties = new HashMap<>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        String extension = MimeTypes.getExtension(fileType);
        properties.put(PropertyIds.NAME, filename + extension);
        properties.put(PropertyIds.CONTENT_STREAM_FILE_NAME, filename);
        properties.put(PropertyIds.CONTENT_STREAM_MIME_TYPE, fileType);
        return folder.createDocument(properties, contentStream, VersioningState.MAJOR);
    }

    /*
     * public void retriveFile(String documentId,Session documentSession){
     *
     *
     * }
     */

    /**
     * Modified By    on 29 April 2021
     *
     * @param documentId
     * @return
     */
    public String getDocumentURL(String documentId) {
        Session session = cmisSession();
        return getDocumentURL(documentId, session);
    }

    public String getDocumentURL(String documentId, Session session) {
        ObjectId objectId = session.createObjectId(documentId);
        Document doc = (Document) session.getObject(objectId);
        return doc.getContentUrl();
    }

    /**
     * Modified by    on 28 April 2021
     *
     * @param documentId
     * @return
     * @throws IOException
     * @throws CryptoException
     */
    public InputStream getContentStream(String documentId, Session session) throws IOException, CryptoException {
        Document doc = (Document) session.getObject(session.createObjectId(documentId));
        ContentStream docContentStream = session.getContentStream(doc);
        //decrypt
        String key = AESCrypto.ENCRYPTION_KEY;
        byte[] decryptedBytes = AESCrypto.decrypt(key, IOUtils.toByteArray(docContentStream.getStream()));
        return new ByteArrayInputStream(decryptedBytes);
    }

    public InputStream getContentStream(String documentId) throws IOException, CryptoException {
        return getContentStream(documentId, cmisSession());
    }

    /**
     * Created by Tanmoy on 05/03/2017.
     *
     * @return: void.
     * @param: documentId.
     * @usage: LoanInitService->findLoanByLoanIdWithBranchCusstomerAddress;
     */
    public String getDocumentName(String documentId) {
        Document doc = (Document) cmisSession().getObject(cmisSession().createObjectId(documentId));
        return doc.getName();
    }

    /**
     * Created by Tanmoy on 05/03/2017.
     *
     * @return: void.
     * @param: documentId.
     * @usage: LoanInitService->findLoanByLoanIdWithBranchCusstomerAddress;
     */
    public String getDocumentType(String documentId) {
        Document doc = (Document) cmisSession().getObject(cmisSession().createObjectId(documentId));
        return doc.getContentStreamMimeType();
    }

    /**
     * Created by Tanmoy on 29/03/2017.
     *
     * @return: Folder.
     * @param: path.
     * @usage: CreditMemoService->saveCreditMemoToDms;
     */
    public Folder getFolderByPath(String path) {
        return (Folder) cmisSession().getObjectByPath(path);
    }

    /**
     * Created by Tanmoy on 29/03/2017.
     *
     * @return: Folder.
     * @param: path.
     * @usage: CreditMemoService->saveCreditMemoToDms;
     */
    public boolean isFolderExistsByPath(String path) {
        return cmisSession().existsPath(path);
    }

    /**
     * Created by Tanmoy on 12/03/2017.
     *
     * @return: string.
     * @param: doc.
     * @usage: CreditMemoService->generateCreditMemo
     */
    public String getDocumentId(Document document) {
        return document.getId();
    }
}
