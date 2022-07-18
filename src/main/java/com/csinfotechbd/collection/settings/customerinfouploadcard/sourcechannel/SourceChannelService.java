package com.csinfotechbd.collection.settings.customerinfouploadcard.sourcechannel;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerloanprofile.referenceinfo.ReferenceInfoEntity;
import com.csinfotechbd.customerloanprofile.referenceinfo.ReferenceInfoStatus;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class SourceChannelService {



    @Autowired
    private SourceChannelRepository repository;
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;


    public Map<String, String> saveSourceChannel(MultipartFile multipartFile) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> errors = new LinkedHashMap<>();
        XSSFWorkbook xssfWorkbook = null;
        ArrayList<SourceChannel> sourceChannelArrayList=new ArrayList<>();
        String contractid=null;
        try {
            xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            Map<String,String> map = new HashMap<>();
            for (int i = 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                try {
                    XSSFRow row = xssfSheet.getRow(i);
                    SourceChannel sourceChannel;
                    String contactId = row.getCell(0).toString().trim();
                    if(map.get(contactId) == null) {
                        map.put(contactId, contactId);
                        sourceChannel = repository.findByContractId(map.get(contactId));
                        if (sourceChannel == null) {
                            sourceChannel = new SourceChannel();
                            sourceChannel.setCreatedDate(new Date());
                        }

                        sourceChannel.setContractId(contactId != null ? contactId : null);
                        sourceChannel.setSource(row.getCell(1) != null ? row.getCell(1).toString().trim() : null);
                        sourceChannel.setCreatedDate(new Date());
                        sourceChannel.setCreatedBy(user.getEmpId());
                        sourceChannelArrayList.add(sourceChannel);
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    errors.put(contractid, "Something went wrong");
                }

                if (i % batchSize == 0 && i > 0) {
                    repository.saveAll(sourceChannelArrayList);
                    sourceChannelArrayList.clear();
                }

            }
            if (sourceChannelArrayList.size() > 0) {
                repository.saveAll(sourceChannelArrayList);
                sourceChannelArrayList.clear();
            }


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return errors;
    }

    public List<SourceChannel>allSourceChannel(){
        return repository.findAll();
    }

}
