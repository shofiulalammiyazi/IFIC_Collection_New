package com.unisoft.user.ad;

import lombok.Data;

@Data
public class UserAdPayload {
    private String username;
    private String password;

    public UserAdPayload(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
