package com.csinfotechbd.collection.samd.mailtobranch;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SamdMailService {
    @Autowired
   private SamdMailRepository repository;

    private JavaMailSender mailSender;
    private Environment environment;
    
    public boolean sendMail(SamdMail samdMail){
        try {
            Thread sendSamdEmailThread = new Thread(() -> {
                try {
                    MimeMessage samdMimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper samdMimeMessageHelper = new MimeMessageHelper(samdMimeMessage, true);
                    samdMimeMessageHelper.setFrom(environment.getProperty("support.email"));
                    String recipients[] = samdMail.getRecipient().split(",");
                    samdMimeMessageHelper.setTo(recipients);
                    if(samdMail.getCc() != null && !samdMail.getCc().isEmpty()) {
                        String[] emailCC = samdMail.getCc().split(",");
                        samdMimeMessageHelper.setCc(emailCC);
                    }
                    samdMimeMessageHelper.setSubject(samdMail.getSubject());
                    samdMimeMessageHelper.setText(samdMail.getMessage());
            
                    mailSender.send(samdMimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            sendSamdEmailThread.start();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public void sendMailWithAttachment() throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
    
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
    
        helper.setTo("satubu51@gmail.com");
    
        helper.setSubject("Testing from Spring Boot");
    
        helper.setText("Find the attached image", true);
    
        helper.addAttachment("hero.jpg", new ClassPathResource("/logo/ucbl_logo_x.png"));
    
        mailSender.send(msg);
    }





    public List<SamdMailAccountData> getSamdMailAccountData() {
        List<Tuple> samdMailAccountData = repository.getBranchWiseSamdDistributionAccount();
        return getSamdMailData(samdMailAccountData);
    }

    private List<SamdMailAccountData> getSamdMailData(List<Tuple> samdMailAccountData) {
        List<SamdMailAccountData> samdMailAccountDataList = new ArrayList<>();
        for (Tuple tuple : samdMailAccountData) {
            SamdMailAccountData dto = new SamdMailAccountData();
            try {

                dto.setAccountNumber(((String) Optional.ofNullable(tuple.get("ACCOUNT_NO")).orElse("-")));
                dto.setAccountName(((String) Optional.ofNullable(tuple.get("ACCOUNT_NAME")).orElse("-")));
                dto.setBranchCode(((String) Optional.ofNullable(tuple.get("BRANCH_CODE")).orElse("-")));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            samdMailAccountDataList.add(dto);
        }
        return samdMailAccountDataList;
    }
}
