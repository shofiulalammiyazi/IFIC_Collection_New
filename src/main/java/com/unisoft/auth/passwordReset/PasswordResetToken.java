package com.unisoft.auth.passwordReset;

import com.unisoft.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Setter
@Getter
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private Date expiryDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;


    public PasswordResetToken() { }

    public PasswordResetToken(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
