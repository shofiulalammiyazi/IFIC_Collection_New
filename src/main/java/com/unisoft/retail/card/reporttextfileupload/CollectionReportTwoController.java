package com.unisoft.retail.card.reporttextfileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value ="/collection/card/report-2")
@RestController
public class CollectionReportTwoController {

    @Autowired
    CollectionReportTwoService collectionReportOneService;

    @GetMapping("/fdr/list")
    public ResponseEntity<Map<String, Object>> getFdrFromReportTwo(@RequestParam(value = "contractId") String contractId) throws IOException {
        List<FdrDto> fdrDto = collectionReportOneService.findAllFdrByContractNo(contractId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("fdrDto", fdrDto);
        resultMap.put("msg", "data-has been gooten");
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }


}
