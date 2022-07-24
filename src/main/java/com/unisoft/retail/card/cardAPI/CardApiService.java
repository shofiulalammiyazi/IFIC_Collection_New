package com.unisoft.retail.card.cardAPI;

import com.unisoft.retail.card.cardAPIDto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CardApiService {


    private final String retAddress = "172.25.32.31";
    private final String clerk = "COLLECTION";
    private final String password = "Casablanca";

    public List<ContractDetails> getContractList(String clientId)  {

        String sessionId = CardAPIManager.getValueByTagAsStringAfterDecode(CardAPIManager.getSessionId(),"m0:Id");

        List<ContractDetails> contractList = new ArrayList<>();
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "<env:Header/>\n" +
                "<env:Body>\n" +
                "<GetBackOfficeInfoRq xmlns:ns3=\"http://schemas.compassplus.com/two/1.0/fimi_types.xsd\" xmlns:ns4=\"http://schemas.compassplus.com/two/1.0/fimi.xsd\" xmlns:ns5=\"http://schemas.compassplus.com/two/3.6/fimi.xsd\">\n" +
                "<Request Ver=\"3.1\" Product=\"FIMI\" RetAddress=\""+retAddress+"\" Clerk=\""+clerk+"\" Password=\""+password+"\" Session=\""+sessionId+"\">\n" +
                "<IdentType>5</IdentType>\n" +
                "<InfoType>getContractInfoByIdClient</InfoType>\n" +
                "<Ident>"+clientId+"</Ident>\n" +
                "<Format>1</Format>\n" +
                "</Request>\n" +
                "</GetBackOfficeInfoRq>\n" +
                "</env:Body>\n" +
                "</env:Envelope>";
        String response = CardAPIManager.apiRequestProcess(request);

        String responseValue = CardAPIManager.getValueAsStringAfterDecode(response);
    
        Scanner scanner = new Scanner(responseValue);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] c = line.split("\\|");
            ContractDetails contractDetails = new ContractDetails();
            for (int i = 0; i < c.length; i++) {
                contractDetails.setClientId(c[0]);
                contractDetails.setContractNo(c[1]);
                contractDetails.setCreatedDate(c[2]);
                contractDetails.setContractStatus(c[3]);
                contractDetails.setContractType(c[4]);
            }
            contractList.add(contractDetails);
        }
        return contractList;
    }


    public List<PaymentInfo> getPaymentInfo(String contractNo)  {

        String sessionId = CardAPIManager.getValueByTagAsStringAfterDecode(CardAPIManager.getSessionId(),"m0:Id");

        List<PaymentInfo> paymentInfoList = new ArrayList<>();
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "<env:Header/>\n" +
                "<env:Body>\n" +
                "<GetBackOfficeInfoRq xmlns:ns3=\"http://schemas.compassplus.com/two/1.0/fimi_types.xsd\" xmlns:ns4=\"http://schemas.compassplus.com/two/1.0/fimi.xsd\" xmlns:ns5=\"http://schemas.compassplus.com/two/3.6/fimi.xsd\">\n" +
                "<Request Ver=\"3.1\" Product=\"FIMI\" RetAddress=\""+retAddress+"\" Clerk=\""+clerk+"\" Password=\""+password+"\" Session=\""+sessionId+"\">\n" +
                "<IdentType>5</IdentType>\n" +
                "<InfoType>getPaymentInfo</InfoType>\n" +
                "<Ident>"+contractNo+"</Ident>\n" +
                "<Format>1</Format>\n" +
                "</Request>\n" +
                "</GetBackOfficeInfoRq>\n" +
                "</env:Body>\n" +
                "</env:Envelope>";

        String response = CardAPIManager.apiRequestProcess(request);

        String responseValue = CardAPIManager.getValueAsStringAfterDecode(response);

        Scanner scanner = new Scanner(responseValue);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] c = line.split("\\|");
            PaymentInfo paymentInfo = new PaymentInfo();
            for (int i = 0; i < c.length; i++) {
                paymentInfo.setContractNo(c[0]);
                paymentInfo.setBdtPayment(c[1]);
                paymentInfo.setUsdPayment(c[2]);
            }
            paymentInfoList.add(paymentInfo);
        }
        return paymentInfoList;
    }

    public List<BilledAmountDto> getTotalBilledAmount(String contractNo)  {

        String sessionId = CardAPIManager.getValueByTagAsStringAfterDecode(CardAPIManager.getSessionId(),"m0:Id");

        List<BilledAmountDto> billedAmountDtoList = new ArrayList<>();
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "<env:Header/>\n" +
                "<env:Body>\n" +
                "<GetBackOfficeInfoRq xmlns:ns3=\"http://schemas.compassplus.com/two/1.0/fimi_types.xsd\" xmlns:ns4=\"http://schemas.compassplus.com/two/1.0/fimi.xsd\" xmlns:ns5=\"http://schemas.compassplus.com/two/3.6/fimi.xsd\">\n" +
                "<Request Ver=\"3.1\" Product=\"FIMI\" RetAddress=\""+retAddress+"\" Clerk=\""+clerk+"\" Password=\""+password+"\" Session=\""+sessionId+"\">\n" +
                "<IdentType>5</IdentType>\n" +
                "<InfoType>getTotalBilledAmount</InfoType>\n" +
                "<Ident>"+contractNo+"</Ident>\n" +
                "<Format>1</Format>\n" +
                "</Request>\n" +
                "</GetBackOfficeInfoRq>\n" +
                "</env:Body>\n" +
                "</env:Envelope>\n";

        String response = CardAPIManager.apiRequestProcess(request);

        String responseValue = CardAPIManager.getValueAsStringAfterDecode(response);

        Scanner scanner = new Scanner(responseValue);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] c = line.split("\\|");
            BilledAmountDto paymentInfo = new BilledAmountDto();
            for (int i = 0; i < c.length; i++) {
                paymentInfo.setContractNo(c[0]);
                paymentInfo.setBdtBilledAmount(c[1]);
                paymentInfo.setUsdBilledAmount(c[2]);
            }
            billedAmountDtoList.add(paymentInfo);
        }
        return billedAmountDtoList;
    }


    public List<PostedAmount> getTotalPostedAmount(String contractNo)  {

        String sessionId = CardAPIManager.getValueByTagAsStringAfterDecode(CardAPIManager.getSessionId(),"m0:Id");

        List<PostedAmount> postedAmountList = new ArrayList<>();
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "<env:Header/>\n" +
                "<env:Body>\n" +
                "<GetBackOfficeInfoRq xmlns:ns3=\"http://schemas.compassplus.com/two/1.0/fimi_types.xsd\" xmlns:ns4=\"http://schemas.compassplus.com/two/1.0/fimi.xsd\" xmlns:ns5=\"http://schemas.compassplus.com/two/3.6/fimi.xsd\">\n" +
                "<Request Ver=\"3.1\" Product=\"FIMI\" RetAddress=\""+retAddress+"\" Clerk=\""+clerk+"\" Password=\""+password+"\" Session=\""+sessionId+"\">\n" +
                "<IdentType>5</IdentType>\n" +
                "<InfoType>getTotalPostedAmount</InfoType>\n" +
                "<Ident>"+contractNo+"</Ident>\n" +
                "<Format>1</Format>\n" +
                "</Request>\n" +
                "</GetBackOfficeInfoRq>\n" +
                "</env:Body>\n" +
                "</env:Envelope>\n";

        String response = CardAPIManager.apiRequestProcess(request);

        String responseValue = CardAPIManager.getValueAsStringAfterDecode(response);

        Scanner scanner = new Scanner(responseValue);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] c = line.split("\\|");
            PostedAmount postedAmount = new PostedAmount();
            for (int i = 0; i < c.length; i++) {
                postedAmount.setContractNo(c[0]);
                postedAmount.setBdtUnbilledAmount(c[1]);
                postedAmount.setUsdUnbilledAmount(c[2]);
            }
            postedAmountList.add(postedAmount);
        }
        return postedAmountList;
    }

    public List<OnHoldTransaction> getOnHoldTransactions(String contractNo)  {

        String sessionId = CardAPIManager.getValueByTagAsStringAfterDecode(CardAPIManager.getSessionId(),"m0:Id");

        List<OnHoldTransaction> onHoldTxnList = new ArrayList<>();
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "<env:Header/>\n" +
                "<env:Body>\n" +
                "<GetBackOfficeInfoRq xmlns:ns3=\"http://schemas.compassplus.com/two/1.0/fimi_types.xsd\" xmlns:ns4=\"http://schemas.compassplus.com/two/1.0/fimi.xsd\" xmlns:ns5=\"http://schemas.compassplus.com/two/3.6/fimi.xsd\">\n" +
                "<Request Ver=\"3.1\" Product=\"FIMI\" RetAddress=\""+retAddress+"\" Clerk=\""+clerk+"\" Password=\""+password+"\" Session=\""+sessionId+"\">\n" +
                "<IdentType>5</IdentType>\n" +
                "<InfoType>getOnHoldTransactions</InfoType>\n" +
                "<Ident>"+contractNo+"</Ident>\n" +
                "<Format>1</Format>\n" +
                "</Request>\n" +
                "</GetBackOfficeInfoRq>\n" +
                "</env:Body> </env:Envelope>";

        String response = CardAPIManager.apiRequestProcess(request);

        String responseValue = CardAPIManager.getValueAsStringAfterDecode(response);

        Scanner scanner = new Scanner(responseValue);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] c = line.split("\\|");
            OnHoldTransaction onHoldTransaction = new OnHoldTransaction();
            for (int i = 0; i < c.length; i++) {
                onHoldTransaction.setContractNo(c[0]);
                onHoldTransaction.setDrOnHoldBdt(c[1]);
                onHoldTransaction.setCrOnHoldBdt(c[2]);
                onHoldTransaction.setDrOnHoldUsd(c[3]);
                onHoldTransaction.setCrOnHoldUsd(c[4]);
            }
            onHoldTxnList.add(onHoldTransaction);
        }
        return onHoldTxnList;
    }

    public List<AccruedInterestInfoDto> getAccruedInterestInfo(String contractNo) {

        String sessionId = CardAPIManager.getValueByTagAsStringAfterDecode(CardAPIManager.getSessionId(),"m0:Id");

        List<AccruedInterestInfoDto> accruedInterestInfoDtos = new ArrayList<>();

        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "<env:Header/> <env:Body>\n" +
                "<GetBackOfficeInfoRq xmlns:ns3=\"http://schemas.compassplus.com/two/1.0/fimi_types.xsd\" xmlns:ns4=\"http://schemas.compassplus.com/two/1.0/fimi.xsd\" xmlns:ns5=\"http://schemas.compassplus.com/two/3.6/fimi.xsd\">\n" +
                "<Request Ver=\"3.1\" Product=\"FIMI\" RetAddress=\""+retAddress+"\" Clerk=\""+clerk+"\" Password=\""+password+"\" Session=\""+sessionId+"\">\n" +
                "<IdentType>5</IdentType>\n" +
                "<InfoType>getAccruedInterestInfo</InfoType>\n" +
                "<Ident>"+contractNo+"</Ident>\n" +
                "<Format>1</Format>\n" +
                "</Request>\n" +
                "</GetBackOfficeInfoRq>\n" +
                "</env:Body>\n" +
                "</env:Envelope>";

        String response = CardAPIManager.apiRequestProcess(request);

        String responseValue = CardAPIManager.getValueAsStringAfterDecode(response);

        Scanner scanner = new Scanner(responseValue);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] c = line.split("\\|");
            AccruedInterestInfoDto accruedInterestInfoDto = new AccruedInterestInfoDto();
            for (int i = 0; i < c.length; i++) {
                accruedInterestInfoDto.setContractNo(c[0]);
                accruedInterestInfoDto.setAccruedInterestBdt(c[1]);
                accruedInterestInfoDto.setAccruedInterestUsd(c[2]);
            }
            accruedInterestInfoDtos.add(accruedInterestInfoDto);
        }
        return accruedInterestInfoDtos;
    }

    public TotalEmiDues getTotalEmiDuesByContractNo(String contractNo) {

        String sessionId = CardAPIManager.getValueByTagAsStringAfterDecode(CardAPIManager.getSessionId(),"m0:Id");

        TotalEmiDues totalEmiDues = new TotalEmiDues();
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "<env:Header/>\n" +
                "<env:Body>\n" +
                "<GetBackOfficeInfoRq xmlns:ns3=\"http://schemas.compassplus.com/two/1.0/fimi_types.xsd\" xmlns:ns4=\"http://schemas.compassplus.com/two/1.0/fimi.xsd\" xmlns:ns5=\"http://schemas.compassplus.com/two/3.6/fimi.xsd\">\n" +
                "<Request Ver=\"3.1\" Product=\"FIMI\" RetAddress=\""+retAddress+"\" Clerk=\""+clerk+"\" Password=\""+password+"\" Session=\""+sessionId+"\">\n" +
                "<IdentType>5</IdentType>\n" +
                "<InfoType>getTotalEMIDues</InfoType>\n" +
                "<Ident>"+contractNo+"</Ident>\n" +
                "<Format>1</Format>\n" +
                "</Request>\n" +
                "</GetBackOfficeInfoRq>\n" +
                "</env:Body>\n" +
                "</env:Envelope>";
        String response = CardAPIManager.apiRequestProcess(request);
        String responseValue = CardAPIManager.getValueAsStringAfterDecode(response);

        Scanner scan = new Scanner(responseValue);
        while (scan.hasNext()) {
            String dataLine = scan.nextLine();
            String[] c = dataLine.split("\\|");

            totalEmiDues.setContractNo(c[0]);
            totalEmiDues.setTotalEMIAmount(c[1]);
            totalEmiDues.setTotalPaidEMIAmount(c[2]);
            totalEmiDues.setTotalUnpaidEMIAmount(c[3]);
        }
        return totalEmiDues;
    }

    public List<CurrentMinPaymentDto> getCurrentMinPayment(String contractNo) {

        String sessionId = CardAPIManager.getValueByTagAsStringAfterDecode(CardAPIManager.getSessionId(),"m0:Id");

        List<CurrentMinPaymentDto> currentMinPaymentDtos = new ArrayList<>();
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "<env:Body>\n" +
                "<GetBackOfficeInfoRq xmlns:ns3=\"http://schemas.compassplus.com/two/1.0/fimi_types.xsd\" xmlns:ns4=\"http://schemas.compassplus.com/two/1.0/fimi.xsd\" xmlns:ns5=\"http://schemas.compassplus.com/two/3.6/fimi.xsd\">\n" +
                "<Request Ver=\"3.1\" Product=\"FIMI\" RetAddress=\""+retAddress+"\" Clerk=\""+clerk+"\" Password=\""+password+"\" Session=\""+sessionId+"\">\n" +
                "<IdentType>5</IdentType>\n" +
                "<InfoType>getCurrentMinPayment</InfoType>\n" +
                "<Ident>"+contractNo+"</Ident>\n" +
                "<Format>1</Format>\n" +
                "</Request>\n" +
                "</GetBackOfficeInfoRq>\n" +
                "</env:Body>\n" +
                "</env:Envelope>";
        String response = CardAPIManager.apiRequestProcess(request);
        String responseValue = CardAPIManager.getValueAsStringAfterDecode(response);

        Scanner scan = new Scanner(responseValue);
        while (scan.hasNext()) {
            String dataLine = scan.nextLine();
            String[] c = dataLine.split("\\|");
            CurrentMinPaymentDto currentMinPaymentDto = new CurrentMinPaymentDto();

            for (int i = 0; i < c.length; i++) {
                currentMinPaymentDto.setContractNo(c[0]);
                currentMinPaymentDto.setBdtAmount(c[1]);
                currentMinPaymentDto.setUsdAmount(c[2]);
            }
            currentMinPaymentDtos.add(currentMinPaymentDto);
        }
        return currentMinPaymentDtos;
    }

    public List<PaymentDuringMonthDto> getPaymentInfoDuringMonth(String contractNo, String monthYear) {

        String sessionId = CardAPIManager.getValueByTagAsStringAfterDecode(CardAPIManager.getSessionId(),"m0:Id");

        List<PaymentDuringMonthDto> paymentDuringMonthDtos = new ArrayList<>();
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                "<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "<env:Body>\n" +
                "<GetBackOfficeInfoRq xmlns:ns3=\"http://schemas.compassplus.com/two/1.0/fimi_types.xsd\" xmlns:ns4=\"http://schemas.compassplus.com/two/1.0/fimi.xsd\" xmlns:ns5=\"http://schemas.compassplus.com/two/3.6/fimi.xsd\">\n" +
                "<Request Ver=\"3.1\" Product=\"FIMI\" RetAddress=\""+retAddress+"\" Clerk=\""+clerk+"\" Password=\""+password+"\" Session=\""+sessionId+"\">\n" +
                "<IdentType>5</IdentType>\n" +
                "<InfoType>getPaymentInfoDuringMonth</InfoType>\n" +
                "<Ident>"+contractNo+"|"+monthYear+"</Ident>\n" +
                "<Format>1</Format>\n" +
                "</Request>\n" +
                "</GetBackOfficeInfoRq>\n" +
                "</env:Body>\n" +
                "</env:Envelope>";
        String response = CardAPIManager.apiRequestProcess(request);
        String responseValue = CardAPIManager.getValueAsStringAfterDecode(response);

        Scanner scan = new Scanner(responseValue);
        while (scan.hasNext()) {
            String dataLine = scan.nextLine();
            String[] c = dataLine.split("\\|");
            PaymentDuringMonthDto paymentDuringMonthDto = new PaymentDuringMonthDto();

            for(int i=0; i<c.length; i++){
                paymentDuringMonthDto.setContractNo(c[0]);
                paymentDuringMonthDto.setBdtPayment(c[2]);
                paymentDuringMonthDto.setUsdPayment(c.length<5 ? "0" : c[4]);
            }
            paymentDuringMonthDtos.add(paymentDuringMonthDto);
        }
        return paymentDuringMonthDtos;
    }
}