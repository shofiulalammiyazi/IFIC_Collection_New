package com.csinfotechbd.auth.passwordReset;


import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;

@Controller
@RequestMapping("/auth/password-reset")
public class PasswordResetController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private EmailSenderConstractor emailSenderConstractor;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/by-email")
    public String passwordByEmailView() {
        return "auth/passwordReset/password_reset_email_form";
    }

    @GetMapping("/email-verify")
    public String verifyByEmail(Model model, @RequestParam(value = "email") String email, HttpServletRequest request) {
        EmployeeInfoEntity employeeInfoEntity = employeeService.findByEmail(email);
        if (employeeInfoEntity != null) {
            int length = 6;
            boolean useLetters = true;
            boolean useNumbers = true;
            String token = RandomStringUtils.random(length, useLetters, useNumbers);
            String message = "Password reset code.";
            passwordResetService.createTokenForUser(employeeInfoEntity.getUser(), token);
            String tokenUrl = "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            SimpleMailMessage simpleMailMessage = emailSenderConstractor.createPasswordResetTokenMail(employeeInfoEntity, token, message);
            mailSender.send(simpleMailMessage);
            model.addAttribute("username", employeeInfoEntity.getUser().getUsername());
            return "auth/passwordReset/password_verification";
        }
        model.addAttribute("warning", true);
        return "auth/passwordReset/password_reset_email_form";
    }

    @GetMapping("/verification-code")
    public String verifyToken(@RequestParam(value = "token") String token, Model model) {
        PasswordResetToken passwordResetToken = passwordResetService.findPasswordResetTokenByToken(token);
        if (passwordResetToken == null) {
            model.addAttribute("invalidToken", true);
            return "auth/passwordReset/password_verification";
        }
        Calendar calendar = Calendar.getInstance();
        if (passwordResetToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            model.addAttribute("tokenExpired", true);
            passwordResetService.deletePassworResetToken(passwordResetToken);
            return "auth/passwordReset/password_verification";
        }
        model.addAttribute("username", passwordResetToken.getUser().getUsername());
        model.addAttribute("UserPasswordResetDto", new UserPasswordResetDto());
        return "auth/passwordReset/password_reset_form";
    }

    @PostMapping("/save-password")
    public String changePassword(Model model,
                                 @Valid @ModelAttribute("UserPasswordResetDto") UserPasswordResetDto userPasswordResetDto,
                                 BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("error", true);
            model.addAttribute("username", userPasswordResetDto.getUsername());
            model.addAttribute("UserPasswordResetDto", userPasswordResetDto);
            return "auth/passwordReset/password_reset_form";
        }
        else {
            passwordResetService.savePassword(userPasswordResetDto);
        }
        return "redirect:/";
    }
}
