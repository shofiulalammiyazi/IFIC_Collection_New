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

@RestController
@RequestMapping("/collection/card/approved-transaction")
public class CollectionReportOneController  {
    @Autowired
    CollectionReportOneService collectionReportOneService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getApprovedTransactionsSearchData(@RequestParam(value = "contractId") String contractId,
                                                                                 @RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate) throws IOException {
        List<ApproveTransactionsUnbilledTransactionsPayment> listApproveTransactions = collectionReportOneService.getSearchApproveTransactionPaymentDataList(contractId, startDate, endDate);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("listApproveTransactions", listApproveTransactions);
        resultMap.put("msg", "data-has been gooten");
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

}
