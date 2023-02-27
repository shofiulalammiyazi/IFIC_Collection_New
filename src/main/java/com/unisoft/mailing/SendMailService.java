package com.unisoft.mailing;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

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

        helper.addAttachment("hero.jpg", new ClassPathResource("/logo/ific.png"));

        mailSender.send(msg);
    }

    public Boolean createPasswordResetTokenMail(Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        if(mail.getRecipient() != null && !mail.getRecipient().isEmpty()) {
            String recipients[] = mail.getRecipient().split(",");
            try {
                mailMessage.setTo(new InternetAddress(mail.getRecipient()).toString());
            } catch (AddressException e) {
                e.printStackTrace();
            }

            mailMessage.setTo(recipients);
            if (mail.getCc() != null && !mail.getCc().isEmpty()) {
                String[] emailCC = mail.getCc().split(",");
                mailMessage.setCc(emailCC);
            }
        }
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        try {
            mailMessage.setFrom(new InternetAddress(environment.getProperty("support.email"),"IFIC BANK").toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //mailMessage.setFrom(environment.getProperty("support.email"));

        mailSender.send(mailMessage);
        return true;
    }
}
