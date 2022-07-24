package com.unisoft.mailing;

import lombok.Data;


@Data
public class Mail {
    
    private String recipient;
    private String cc;
    private String subject;
    private String message;
    
    public Mail() {
    }
}
