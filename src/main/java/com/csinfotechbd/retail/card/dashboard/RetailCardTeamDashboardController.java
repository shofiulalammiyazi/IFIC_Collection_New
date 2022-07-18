package com.csinfotechbd.retail.card.dashboard;

import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.csinfotechbd.collection.dashboard.DashboardService;
import com.csinfotechbd.collection.dashboard.TeamDetailModel;
import com.csinfotechbd.collection.dashboard.TeamDetailsViewModel;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/retail/card/team/dashboard/")
public class RetailCardTeamDashboardController {

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

        List<CardAccountDistributionInfo> cardAccountDist = (List<CardAccountDistributionInfo>) session.getAttribute("cardDistributionList");
        for (CardAccountDistributionInfo distributionInfo : cardAccountDist) {
            double totalPayment = 0;
            if (distributionInfo.getAgeCode().toUpperCase().equals("CR")) {
                distributionInfo.setAgeCode("0");
            }
        }
        dealerDetail = getDealerDetailListCard(dealerList, cardAccountDist);


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

    //Get Card Team Detail
    private List<TeamDetailModel> getDealerDetailListCard(List<EmployeeInfoEntity> dealerList, List<CardAccountDistributionInfo> cardDist) {
        List<TeamDetailModel> teamDetailModelList = new ArrayList<>();

        for (EmployeeInfoEntity dealer : dealerList) {
            TeamDetailModel detailModel = new TeamDetailModel();
            detailModel.setDesignation("Dealer");
            if (dealer != null) {
                detailModel.setDealerName(dealer.getUser().getFirstName());
                detailModel.setDealerPin(dealer.getPin());
            }

            List<CardAccountDistributionInfo> checkedItem = new ArrayList<>();
            for (CardAccountDistributionInfo distributionInfo : cardDist) {
                if (dealer != null) {
                    if (distributionInfo.getDealerPin().equals(dealer.getPin())) {
                        detailModel.setNoOfAcc(detailModel.getNoOfAcc() + 1);
                        detailModel.setOutstanding(detailModel.getOutstanding() + Double.parseDouble(distributionInfo.getOutstandingAmount()));

                        checkedItem.add(distributionInfo);
                    }
                }
            }
            teamDetailModelList.add(detailModel);
            cardDist.remove(checkedItem);
        }

        return teamDetailModelList;
    }


}
