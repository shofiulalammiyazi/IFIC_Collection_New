package com.csinfotechbd.retail.loan.dashboard;

import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.csinfotechbd.collection.dashboard.*;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/retail/loan/team/dashboard/")
public class RetailLoanTeamDashboardController {

    private final EmployeeService employeeService;
    private final DashboardService dashboardService;


    // Retrieve team details info
    @GetMapping("teamdetails")
    @ResponseBody
    public TeamDetailsViewModel getTeamDetailsInfo(@RequestParam(value = "pin") String pin,
                                                   @RequestParam(value = "unit") String unit,
                                                   HttpSession session) {
        // Get dealer list
        EmployeeInfoEntity employeeInfoEntity = Optional.ofNullable(employeeService.getByPin(pin)).orElse(new EmployeeInfoEntity());
        List<PeopleAllocationLogicInfo> allocationLogicList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(), employeeInfoEntity.getDesignation().getName(), unit);
        List<EmployeeInfoEntity> dealerList = getDealerList(allocationLogicList);

        // Get team details
        List<TeamDetailModel> dealerDetail = new ArrayList<>();

        List<LoanAccountDistributionInfo> loanDist = (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");
        dealerDetail = getDealerDetailListLoan(dealerList, loanDist);

        // Team details tab element (With calculation of total account and total outstanding)
        return new TeamDetailsViewModel(employeeInfoEntity.getPin(), employeeInfoEntity.getUser().getFirstName(), dealerDetail);
    }

    //Get Dealer List
    private List<EmployeeInfoEntity> getDealerList(List<PeopleAllocationLogicInfo> allocationList) {
        List<EmployeeInfoEntity> dealerList = new ArrayList<>();

        for (PeopleAllocationLogicInfo allocationLogic : allocationList) {
            EmployeeInfoEntity dealer = allocationLogic.getDealer();
            if (!dealerList.contains(dealer)) {
                dealerList.add(dealer);
            }
        }

        return dealerList;
    }

    //Get Dealer List Model
    private List<TeamDetailModel> getDealerDetailListLoan(List<EmployeeInfoEntity> dealerList, List<LoanAccountDistributionInfo> cardDist) {
        List<TeamDetailModel> teamDetailModelList = new ArrayList<>();

        for (EmployeeInfoEntity dealer : dealerList) {
            TeamDetailModel detailModel = new TeamDetailModel();
            detailModel.setDesignation("Dealer");
            detailModel.setDealerName(dealer.getUser().getFirstName());
            detailModel.setDealerPin(dealer.getPin());

            List<LoanAccountDistributionInfo> checkedItem = new ArrayList<>();
            for (LoanAccountDistributionInfo distributionInfo : cardDist) {
                if (distributionInfo.getDealerPin().equals(dealer.getPin())) {
                    detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                    detailModel.setOutstanding(detailModel.getOutstanding() + Double.parseDouble(distributionInfo.getOutStanding()));

                    checkedItem.add(distributionInfo);
                }
            }
            teamDetailModelList.add(detailModel);
            cardDist.remove(checkedItem);
        }

        return teamDetailModelList;
    }


}
