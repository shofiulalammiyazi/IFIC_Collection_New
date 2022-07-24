package com.unisoft.collection.samd.dashboard;

import com.unisoft.collection.dashboard.DealerPerformanceDataDao;
import com.unisoft.collection.dashboard.DealerPerformanceDataEntity;
import com.unisoft.collection.dashboard.EsauRatingDataModel;
import com.unisoft.collection.settings.esau.loan.ESAULoanEntity;
import com.unisoft.collection.settings.esau.loan.ESAULoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Implemented By :~ Hasibul Islam
 *                   Software Engineer
 * 28-Apr-2021 01:10 PM
 * */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/samd/dashboard/team")
public class SamdDashTeamCombinedController {

    private final DealerPerformanceDataDao dealerPerformanceDataDao;
    private final ESAULoanService esauLoanService;

    @GetMapping(value = "/esau/loan")
    @ResponseBody
    public EsauRatingDataModel getEsauLoan(@RequestParam(value = "userPin") String userPin,
                                               @RequestParam(value = "unit") String unit,
                                               @RequestParam(value = "designation") String designation,
                                               @RequestParam(value = "userId") String userId) {
        DealerPerformanceDataEntity dealerPerformanceData = dealerPerformanceDataDao.getCurrentMonthPerformanceByUserPin(userPin, unit.toUpperCase());

        List<ESAULoanEntity> esauCardList = esauLoanService.getAll();
        EsauRatingDataModel esauRatingDataModel = new EsauRatingDataModel();

        if (dealerPerformanceData != null) {
            for (ESAULoanEntity esauCard : esauCardList) {
                if (esauCard.getFinalAvgLowerLimit() <= dealerPerformanceData.getPerformanceAvg() && dealerPerformanceData.getPerformanceAvg() <= esauCard.getFinalAvgUpperLimit()) {
                    esauRatingDataModel.setPerformanceAvg(dealerPerformanceData.getPerformanceAvg());
                    esauRatingDataModel.setRatingName(esauCard.getRatingName());
                    //break;
                }
            }
        }
        return esauRatingDataModel;
    }
}
