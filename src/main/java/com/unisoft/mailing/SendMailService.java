package com.unisoft.mailing;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@AllArgsConstructor
@Service
public class SendMailService {
    
    private JavaMailSender mailSender;
    private Environment environment;
    
    public boolean sendMail(Mail mail){
        try {
            Thread sentEmailThread = new Thread(() -> {
                try {
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setFrom(environment.getProperty("support.email"));
                    if(mail.getRecipient() != null && !mail.getRecipient().isEmpty()){
                        String recipients[] = mail.getRecipient().split(",");
                        mimeMessageHelper.setTo(recipients);
                        if(mail.getCc() != null && !mail.getCc().isEmpty()) {
                            String[] emailCC = mail.getCc().split(",");
                            mimeMessageHelper.setCc(emailCC);
                        }
                        mimeMessageHelper.setSubject(mail.getSubject());
                        mimeMessageHelper.setText(mail.getMessage());

                        mailSender.send(mimeMessage);
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            sentEmailThread.start();
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
}
