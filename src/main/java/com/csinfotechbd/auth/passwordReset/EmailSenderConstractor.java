package com.csinfotechbd.auth.passwordReset;

import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderConstractor {

    @Autowired
    private Environment environment;

    public SimpleMailMessage createPasswordResetTokenMail(EmployeeInfoEntity employeeInfoEntity, String token, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(employeeInfoEntity.getEmail());
        mailMessage.setSubject("Your Password Reset Code: ");
        mailMessage.setText(message + "\n" + token);
        mailMessage.setFrom(environment.getProperty("support.email"));
        return mailMessage;
    }
}
