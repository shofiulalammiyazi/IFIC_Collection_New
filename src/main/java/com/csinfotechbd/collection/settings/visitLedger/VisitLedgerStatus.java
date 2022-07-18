package com.csinfotechbd.collection.settings.visitLedger;

public enum VisitLedgerStatus {


    DEFAULT("DEFAULT"),
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    DECLINE("DECLINE");

    private String status;

    VisitLedgerStatus(String status) {

        this.status = status;
    }

    public String getStatus(){
      return status;
    }
}
