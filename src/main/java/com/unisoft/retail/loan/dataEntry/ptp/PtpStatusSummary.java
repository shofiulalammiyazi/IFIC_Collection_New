package com.unisoft.retail.loan.dataEntry.ptp;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.lang.NonNull;

/*
  Created by Md.   Islam on 12/31/2019

  Modified by Yasir Araphat on 05 April, 2021
*/
@Data
public class PtpStatusSummary {

    private String status;
    private long totalAccount;
    private double ptpAmount;
    private PtpDetailsDto[] ptpDetails;

    public PtpStatusSummary() {
    }

    public PtpStatusSummary(@NonNull String status, @NonNull String accNo, @NonNull Long totalAccount, @NonNull Double ptpAmount) {
        this.status = status;
        this.totalAccount = totalAccount;
        this.ptpAmount = ptpAmount;
        try {
            setPtpDetails(accNo);
        }catch (Exception ignored){
        }
    }

    public void setPtpDetails(String accNo) {
        ptpDetails  = new Gson().fromJson(accNo, PtpDetailsDto[].class);
    }


}
