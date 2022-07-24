package com.unisoft.collection.samd.mailtobranch;

import lombok.Data;


@Data
public class SamdMail {
    
    private String recipient;
    private String cc;
    private String subject;
    private String message;
    
    public SamdMail() {
    }
}
