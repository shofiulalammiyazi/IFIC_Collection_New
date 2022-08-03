package com.unisoft.auth.passwordReset;

import com.unisoft.user.User;
import com.unisoft.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserDao userDao;

    @Override
    public PasswordResetToken findPasswordResetTokenByToken(String token) {
        return passwordResetTokenRepository.findPasswordResetTokenByToken(token);
    }

    @Override
    public void createTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 2);
        Date expiryDate = calendar.getTime();
        passwordResetToken.setExpiryDate(expiryDate);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public UserPasswordResetDto savePassword(UserPasswordResetDto userPasswordResetDto) {
        User user = userDao.findUserByUsername(userPasswordResetDto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userPasswordResetDto.getPassword()));
        userDao.update(user);
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findPasswordResetTokenByUser(user);
        passwordResetTokenRepository.delete(passwordResetToken);
        return userPasswordResetDto;
    }

    @Override
    public void deletePassworResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }

}
