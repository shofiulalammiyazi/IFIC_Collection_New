package com.csinfotechbd.legal.report.bangladeshBankReports.artharinAndOtherCourts;


import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArtharinAndOtherCourtsService {

    private final ArtharinAndOtherCourtsRepository repository;
    private final DateUtils dateUtils;

    public Map<String, ArtharinAndOtherCourtsDto> getReport(Date endDate) {
        Map<String, ArtharinAndOtherCourtsDto> result = new HashMap<>();
        result.put("ArtharinAdalat", new ArtharinAndOtherCourtsDto());
        result.put("BankruptcyCourt", new ArtharinAndOtherCourtsDto());
        result.put("Others", new ArtharinAndOtherCourtsDto());

        List<Tuple> data = repository.getReport(endDate);

        for (Tuple suit : data) {
            String courtName = Objects.toString(suit.get("COURT_NAME"), "").replace(" ", "");
            ArtharinAndOtherCourtsDto suitData = result.get(courtName);
            if (suitData == null) {
                suitData = new ArtharinAndOtherCourtsDto();
            }
            suitData.setValuesFromTuple(suit);
            result.put(courtName, suitData);
        }
        return result;
    }
}
