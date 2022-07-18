package com.csinfotechbd.legal.report.datasheets.appellateDivisionReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppellateDivisionService {

    @Autowired
    private AppellateDivisionRepository appellateDivisionRepository;

    public List<AppellateDivision> getCaseList() {
        List<Tuple> tupleList = appellateDivisionRepository.getCaseList();
        List<AppellateDivision> appellateDivisionList = new ArrayList<>();

        int sl = 1;
        for (Tuple tuple: tupleList){
            AppellateDivision appellateDivision = new AppellateDivision();

            appellateDivision.setSL(sl);
            sl +=1;

            appellateDivision.setBranchCode(tuple.get("branchCode"));
            appellateDivision.setLd(tuple.get("ld"));
            appellateDivision.setBranchName(tuple.get("branchName"));
//            appellateDivision.setBranchCode(tuple.get("caseName"));
            appellateDivision.setCaseNo(tuple.get("caseNo"));
            appellateDivision.setCaseYear(tuple.get("caseYear"));
            appellateDivision.setDateOfFiling(tuple.get("dateOfFiling"));
            appellateDivision.setAccountName(tuple.get("accountName"));
            appellateDivision.setPetitionerName(tuple.get("petitionerName"));
            appellateDivision.setOppositeParty(tuple.get("oppositeParty"));
            appellateDivision.setAmountInvolved(tuple.get("amountInvolved"));
            appellateDivision.setLegalExpense(tuple.get("legalExpense"));
            appellateDivision.setBLAName(tuple.get("bLAName"));
            appellateDivision.setCourtName(tuple.get("courtName"));
            appellateDivision.setByWhomFiledByBankOrAgainstBank(tuple.get("byWhomFiledByBankOrAgainstBank"));
            appellateDivision.setFirstOrderDate(tuple.get("firstOrderDate"));
            appellateDivision.setHearingDate(tuple.get("hearingDate"));
            appellateDivision.setCourseOfAction(tuple.get("courseOfAction"));
            appellateDivision.setSubjectMatterOfTheCase(tuple.get("SubjectMatterOfTheCase"));
            appellateDivision.setStatus(tuple.get("Status"));

            appellateDivisionList.add(appellateDivision);


        }
        return appellateDivisionList;
    }
}
