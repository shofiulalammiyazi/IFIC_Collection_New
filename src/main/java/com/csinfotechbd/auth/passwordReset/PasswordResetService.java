package com.csinfotechbd.auth.passwordReset;

import com.csinfotechbd.user.User;

public interface PasswordResetService {

    PasswordResetToken findPasswordResetTokenByToken(String token);

    void createTokenForUser(User user, String token);

    UserPasswordResetDto savePassword(UserPasswordResetDto userPasswordResetDto);

    void deletePassworResetToken(PasswordResetToken passwordResetToken);
}
