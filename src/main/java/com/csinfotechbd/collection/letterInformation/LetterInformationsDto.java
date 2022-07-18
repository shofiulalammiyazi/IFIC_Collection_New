package com.csinfotechbd.collection.letterInformation;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;

@Data
public class LetterInformationsDto {

    private String id;

    private String lastLetterType;
    private String letterRefNo;
    private String letterSendDate;
    private String letterReturnDate;
    private String returnReason;
    private String receivedBy;

    private String fileName;

    private String dmsFileId;

    private String dmsFileType;

    public LetterInformationsDto(){}

    public LetterInformationsDto(Tuple tuple) {
        this.id = Objects.toString(tuple.get("id"), "-");

        this.fileName = Objects.toString(tuple.get("fileName"), " ");
        this.dmsFileId = Objects.toString(tuple.get("dmsId"), " ");
        this.dmsFileType = Objects.toString(tuple.get("fileType"), " ");
        this.lastLetterType = Objects.toString(tuple.get("lastLetterType"), "-");
        this.letterRefNo = Objects.toString(tuple.get("letterRefNo"), "-");
        this.receivedBy = Objects.toString(tuple.get("receivedBy"), "receivedBy");
        this.letterSendDate = Objects.toString(tuple.get("letterSendDate"), "-");
        this.letterReturnDate = Objects.toString(tuple.get("letterReturnDate"), "-");
        this.returnReason = Objects.toString(tuple.get("returnReason"), "-");
    }
}
