package com.csinfotechbd.collection.dashboard;

import lombok.Data;

import java.util.List;

@Data
public class TeamDetailsViewModel {

    private String employeePin;
    private String employeeName;
    private long userTotalAcc;
    private double userTotalOutStanding;
    private List<TeamDetailModel> dealerDetail;

    public TeamDetailsViewModel() {
    }

    public TeamDetailsViewModel(String employeePin, String employeeName, List<TeamDetailModel> dealerDetail) {
        this.dealerDetail = dealerDetail;
        userTotalAcc = 0;
        userTotalOutStanding = 0;
        for (TeamDetailModel detailModel : dealerDetail) {
            userTotalAcc += detailModel.getNoOfAcc();
            userTotalOutStanding += detailModel.getOutstanding();
        }
    }

    public String getEmployeePin() {
        return employeePin;
    }

    public void setEmployeePin(String employeePin) {
        this.employeePin = employeePin;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public List<TeamDetailModel> getDealerDetail() {
        return dealerDetail;
    }

    public void setDealerDetail(List<TeamDetailModel> dealerDetail) {
        this.dealerDetail = dealerDetail;
    }

    public long getUserTotalAcc() {
        return userTotalAcc;
    }

    public void setUserTotalAcc(long userTotalAcc) {
        this.userTotalAcc = userTotalAcc;
    }

    public double getUserTotalOutStanding() {
        return userTotalOutStanding;
    }

    public void setUserTotalOutStanding(double userTotalOutStanding) {
        this.userTotalOutStanding = userTotalOutStanding;
    }
}
