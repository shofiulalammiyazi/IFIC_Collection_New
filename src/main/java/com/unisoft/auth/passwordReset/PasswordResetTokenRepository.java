package com.unisoft.auth.passwordReset;

import com.unisoft.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findPasswordResetTokenByToken(String token);
    PasswordResetToken findPasswordResetTokenByUser(User user);
}
