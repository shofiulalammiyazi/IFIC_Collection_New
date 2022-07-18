package com.csinfotechbd.reports.retail.loan.clReportProfessionSegmentWise;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfessionSegmentWiseCLReportDto {

    private String prdName;
    List<ProfessionSegmentReport> getProductList = new ArrayList<>();

}
