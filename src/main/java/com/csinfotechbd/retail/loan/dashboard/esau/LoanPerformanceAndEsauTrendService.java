package com.csinfotechbd.retail.loan.dashboard.esau;

import com.csinfotechbd.collection.dashboard.PerforamnceAchivmentModel;
import com.csinfotechbd.retail.loan.dashboard.kpi.LoanKpiTargetVsAchievement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanPerformanceAndEsauTrendService {

    private final LoanPerformanceAndEsauTrendRepository repository;

    public List<LoanPerformanceAndEsauTrendDataModel> getPerformanceAndEsauTrendSummary(List<String> dealerPins, Date beginingMonth) {
        return repository.getPerformanceAndEsauTrendSummary(dealerPins, beginingMonth).stream().map(LoanPerformanceAndEsauTrendDataModel::new).collect(Collectors.toList());
    }

    public PerforamnceAchivmentModel getPerformanceAchivment(String pin,Date startDate,Date EndDate) {
        Tuple tuple= repository.getPerformanceAndAchivment(pin, startDate, EndDate) ;
        PerforamnceAchivmentModel perforamnceAchivmentModel=new PerforamnceAchivmentModel();
        if(tuple!=null){
            perforamnceAchivmentModel.setPerformance(tuple.get("ACHIEVEMENT") == null ? 0.0 : Double.parseDouble(tuple.get("ACHIEVEMENT").toString())  );
        }
        return perforamnceAchivmentModel;

    }




}
