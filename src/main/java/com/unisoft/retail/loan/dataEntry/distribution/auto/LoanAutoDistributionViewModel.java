package com.unisoft.retail.loan.dataEntry.distribution.auto;

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicDto;
import com.unisoft.utillity.StringUtils;
import com.google.gson.Gson;
import lombok.Data;

import javax.persistence.Tuple;
import java.sql.Clob;
import java.util.Objects;

@Data
public class LoanAutoDistributionViewModel {

    private PeopleAllocationLogicDto dealerDetails = new PeopleAllocationLogicDto();
    private LoanAutoDistributionDto[] accountList = {};
    private int totalAccount;
    private double totalOutstanding;

    public LoanAutoDistributionViewModel() {
    }

    public LoanAutoDistributionViewModel(Tuple data) {

        String dealerPin = Objects.toString(data.get("DEALER_PIN"), "");
        String dealerName = Objects.toString(data.get("DEALER_NAME"), "");

        String teamLeaderPin = Objects.toString(data.get("TEAM_LEAD_PIN"), "");
        String teamLeaderName = Objects.toString(data.get("TEAM_LEAD_NAME"), "");

        String supervisorPin = Objects.toString(data.get("SUPERVISOR_PIN"), "");
        String supervisorName = Objects.toString(data.get("SUPERVISOR_NAME"), "");

        String managerPin = Objects.toString(data.get("MANAGER_PIN"), "");
        String managerName = Objects.toString(data.get("MANAGER_NAME"), "");

        totalAccount = ((Number) data.get("TOTAL_ACCOUNT")).intValue();
        totalOutstanding = ((Number) data.get("TOTAL_OUTSTANDING")).doubleValue();

        dealerDetails.setDealerPin(dealerPin);
        dealerDetails.setDealerName(dealerName);
        dealerDetails.setTeamLeaderPin(teamLeaderPin);
        dealerDetails.setTeamLeaderName(teamLeaderName);
        dealerDetails.setSupervisorPin(supervisorPin);
        dealerDetails.setSupervisorName(supervisorName);
        dealerDetails.setManagerPin(managerPin);
        dealerDetails.setManagerName(managerName);


        try {
            Clob accountsClob = (Clob) data.get("ACCOUNT_LIST");
            String accountsJson = StringUtils.clobToString(accountsClob);
            accountList = new Gson().fromJson(accountsJson, LoanAutoDistributionDto[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
