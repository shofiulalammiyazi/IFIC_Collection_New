package com.csinfotechbd.customerloanprofile.inteRateChangingHistory;

import lombok.Data;

import java.util.Date;

@Data
public class InteRateChangingHistoryDto {
    private Date changingHistory;
    private double outstanding;
    private double overdue;
    private String status;
    private double interestRate;
}
