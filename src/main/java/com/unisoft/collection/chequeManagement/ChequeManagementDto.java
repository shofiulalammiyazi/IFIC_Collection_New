package com.unisoft.collection.chequeManagement;


import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class ChequeManagementDto {


    private String id;

    private String chequeNo;

    private String accountNo;

    private String accountName;

    private String bankName;

    private String branchName;
    private String amount;
    private String receiveDate;

    private String fileName;

    private String dmsFileId;

    private String dmsFileType;

    private String routingNo;
    private String chequeDate;
    private String receivedBy;
    private String presentStatus;


    public ChequeManagementDto() {
    }

    public ChequeManagementDto(Tuple tuple) {
        this.id = Objects.toString(tuple.get("id"), "-");
        this.chequeNo = Objects.toString(tuple.get("chequeNo"), "-");
        this.accountNo = Objects.toString(tuple.get("accountNo"), "-");
        this.accountName = Objects.toString(tuple.get("accountName"), "-");
        this.bankName = Objects.toString(tuple.get("bankName"), "-");
        this.branchName = Objects.toString(tuple.get("branchName"), "-");
        this.amount = Objects.toString(tuple.get("amount"), "-");
        this.receiveDate = Objects.toString(tuple.get("receiveDate"), "-");
        this.fileName = Objects.toString(tuple.get("fileName"), " ");
        this.dmsFileId = Objects.toString(tuple.get("dmsId"), " ");
        this.dmsFileType = Objects.toString(tuple.get("fileType"), " ");
        this.routingNo = Objects.toString(tuple.get("routingNo"), "routingNo");
        this.chequeDate = Objects.toString(tuple.get("chequeDate"), "chequeDate");
        this.receivedBy = Objects.toString(tuple.get("receivedBy"), "receivedBy");
        this.presentStatus = Objects.toString(tuple.get("presentStatus"), "presentStatus");
    }
}
