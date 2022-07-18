package com.csinfotechbd.retail.card.cardAPI;

import com.csinfotechbd.retail.card.cardAPIDto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardAPIController {
    
    @Autowired
    private CardApiService cardApiService;
    
    @GetMapping(value = "/test/details")
    public void getContractDetailList(){
        String countryCode="Canada";
        String request = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"> " +
                " <soap12:Body> " +
                " <GetHolidaysAvailable xmlns=\"http://www.holidaywebservice.com/HolidayService_v2/\"> " +
                " <countryCode>"+countryCode+"</countryCode>" +
                " </GetHolidaysAvailable>" +
                " </soap12:Body>" +
                "</soap12:Envelope>";
        String response = CardAPIManager.apiRequestProcess(request);
    }
    
    @GetMapping(value = "/contract/info")
    public List<ContractDetails> getContractInfoByIdClient(@RequestParam("clientId") String clientId) {
        return cardApiService.getContractList(clientId);
    }

    @GetMapping(value = "/payment/info")
    public List<PaymentInfo> getPaymentInfo(@RequestParam("contractNo") String contractNo) {
        return cardApiService.getPaymentInfo(contractNo);
    }

    @GetMapping(value = "/billed/amount")
    public List<BilledAmountDto> getTotalBilledAmount(@RequestParam("contractNo") String contractNo) {
        return cardApiService.getTotalBilledAmount(contractNo);
    }

    @GetMapping(value = "/posted/amount")
    public List<PostedAmount> getTotalPostedAmount(@RequestParam("contractNo") String contractNo) {
        return cardApiService.getTotalPostedAmount(contractNo);
    }

    @GetMapping(value = "/onhold/transaction")
    public List<OnHoldTransaction> getOnHoldTransactions(@RequestParam("contractNo") String contractNo) {
        return cardApiService.getOnHoldTransactions(contractNo);
    }

    @GetMapping(value = "/acccrued/interest")
    public List<AccruedInterestInfoDto> getAccruedInterestInfo(@RequestParam("contractNo") String contractNo) {
        return cardApiService.getAccruedInterestInfo(contractNo);
    }

    @GetMapping(value = "/currentmin/payment")
    public List<CurrentMinPaymentDto> getCurrentMinPayment(@RequestParam("contractNo") String contractNo) {
        return cardApiService.getCurrentMinPayment(contractNo);
    }

    @GetMapping(value = "/paymentduring/month")
    public List<PaymentDuringMonthDto> getPaymentInfoDuringMonth(@RequestParam("contractNo") String contractNo,
                                                                 @RequestParam("monthYear") String monthYear) {
        return cardApiService.getPaymentInfoDuringMonth(contractNo,monthYear);
    }

    @GetMapping(value = "/emi/dues")
    public TotalEmiDues getContractDetails(@RequestParam("contractNo") String contractNo){
        TotalEmiDues contractDetailsByClientId = cardApiService.getTotalEmiDuesByContractNo(contractNo);
        return contractDetailsByClientId;
    }
}
