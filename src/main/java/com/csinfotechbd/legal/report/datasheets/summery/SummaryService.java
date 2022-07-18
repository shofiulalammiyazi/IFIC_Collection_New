package com.csinfotechbd.legal.report.datasheets.summery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class SummaryService {

    @Autowired
    private SummaryRepository summaryRepository;

    public List<Summary> getallPendingCase() {
        List<Tuple> tupleList = summaryRepository.getAllPendingCase();
        List<Summary> summaryList = new ArrayList<>();
        int sl = 1;
        for (Tuple tuple: tupleList){
            Summary summary = new Summary();
            summary.setSL(sl);
            sl += 1;

            summary.setBranchName(tuple.get("branchName"));
            summary.setNosOfARS(tuple.get("nosOfARS"));
            summary.setArthaRinRelatedSuitValue(tuple.get("arthaRinRelatedSuitValue"));
//            summary.setBranchName(tuple.get("nosOfExCase"));
            summary.setExeCaseRelatedSuitValue(tuple.get("exeCaseRelatedSuitValue"));
            summary.setTotalNumberArtharin(tuple.get("totalNumberArtharin"));
            summary.setTotalSuitValue(tuple.get("totalSuitValue"));
            summary.setNosOfNIAct(tuple.get("nosOfNIAct"));
            summary.setChequeAmount(tuple.get("chequeAmount"));
//            summary.setBranchName(tuple.get("nosMoneySuitsAgainstBank"));
//            summary.setBranchName(tuple.get("amountAgainstBank"));
            summary.setNumberWritPetition(tuple.get("numberWritPetition"));
//            summary.setBranchName(tuple.get("amount"));
            summaryList.add(summary);
        }
        return summaryList;
    }
}
