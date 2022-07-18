package com.csinfotechbd.collection.samd.dataEntry.backgroundOfTheBorrower;

import lombok.Data;

@Data
public class BackgroundOfTheBorrowerPayload {
    private String account;
    private String backgroundOfTheBorrower;

    public BackgroundOfTheBorrowerPayload() {
    }

    public BackgroundOfTheBorrowerPayload(String account, String backgroundOfTheBorrower) {
        this.account = account;
        this.backgroundOfTheBorrower = backgroundOfTheBorrower;
    }
}
