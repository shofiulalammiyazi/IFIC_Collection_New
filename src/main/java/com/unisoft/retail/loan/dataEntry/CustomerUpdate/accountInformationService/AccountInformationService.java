package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService;

import com.unisoft.collection.dashboard.AdvanceSearchPayload;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationDto;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationDao;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.unisoft.retail.loan.dataEntry.distribution.auto.Datatable;

@Service
public class AccountInformationService {

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @Autowired
    private AccountInformationDao accountInformationDao;

    @Autowired
    private DateUtils dateUtils;

    //@Scheduled("")
    public void getAccountInformationData(){

        List<AccountInformationDto> dataList = accountInformationDao.getData();
        List<AccountInformationEntity> accountInformationEntities = new ArrayList<>();

        for(AccountInformationDto dto:dataList ){
            AccountInformationEntity accountInformationEntity;

            accountInformationEntity = accountInformationRepository.findByLoanACNoAndBranchMnemonicAndProductCodeAndDealReference(dto.getLoanACNo() !=null?dto.getLoanACNo().trim():"",dto.getBranchMnemonic() !=null?dto.getBranchMnemonic().trim():"",
                    dto.getProductCode() !=null?dto.getProductCode().trim():"",dto.getDealReference() !=null?dto.getDealReference().trim():"");


            if (accountInformationEntity ==null){
                accountInformationEntity = new AccountInformationEntity();
                accountInformationEntity.setCreatedDate(new Date());

                if (dto.getLoanACNo() !=null) {
                    accountInformationEntity.setLoanACNo(dto.getLoanACNo().trim());
                }else {
                    accountInformationEntity.setLoanACNo(dto.getLoanACNo());
                }

            }else {
                accountInformationEntity.setModifiedDate(new Date());
            }


            String lastPaymentAmount = dto.getLastPaymentAmount() !=null?dto.getLastPaymentAmount().trim():"";
            String settlementLinkAccountBalance = dto.getSettlementLinkAccountBalance() !=null?dto.getSettlementLinkAccountBalance().trim():"";
            String linkMotherAccountNo = dto.getLinkMotherAccountNo() !=null?dto.getLinkMotherAccountNo().trim():"";
            String routingNo = dto.getRoutingNo() !=null?dto.getRoutingNo().trim():"";
            String mobile = dto.getMobile() !=null?dto.getMobile().trim():"";
            String branchName = dto.getBranchName() !=null?dto.getBranchName().trim():"";
            String branchCode = dto.getBranchCode() !=null?dto.getBranchCode().trim():"";
            String overDue = dto.getOverdue() !=null?dto.getOverdue().trim():"";
            String emiAmount = dto.getEmiAmount() !=null?dto.getEmiAmount().trim():"";

            String productType = dto.getProductType() !=null?dto.getProductType().trim():"";
            String actualTenor = dto.getActualTenor() !=null?dto.getActualTenor().trim():"";
            String totalOutstanding = dto.getTotalOutstanding() !=null?dto.getTotalOutstanding().trim():"";
            String borrowerName = dto.getBorrowersName() !=null?dto.getBorrowersName().trim():"";
            String profession = dto.getProfession() !=null?dto.getProfession().trim():"";
            String email = dto.getEmail() !=null?dto.getEmail().trim():"";
            String nid = dto.getNid() !=null?dto.getNid().trim():"";
            String professionSegment = dto.getProfessionSegment() !=null?dto.getProfessionSegment().trim():"";

            String disbursementAmount = dto.getDisbursementAmount() !=null?dto.getDisbursementAmount().trim():"";
            String customerId = dto.getCustomerId() !=null?dto.getCustomerId().trim():"";
            String customerName = dto.getCustomerName() !=null?dto.getCustomerName().trim():"";
            String customerType = dto.getCustomerType() !=null?dto.getCustomerType().trim():"";
            String spouse = dto.getSpouse() !=null?dto.getSpouse().trim():"";

            String gender = dto.getGender() !=null?dto.getGender().trim():"";
            String fatherName = dto.getFatherName() !=null?dto.getFatherName().trim():"";
            String motherName = dto.getMotherName() !=null?dto.getMotherName().trim():"";
            String ni = dto.getNi() !=null?dto.getNi().trim():"";
            String tin = dto.getTin() !=null?dto.getTin().trim():"";

            String contractNo = dto.getContractNo() !=null?dto.getContractNo().trim():"";
            String contractNoHome = dto.getContractNoHome() !=null?dto.getContractNoHome().trim():"";
            String economicPurposeName = dto.getEconomicPurposeName() !=null?dto.getEconomicPurposeName().trim():"";
            String economicPurposeCode = dto.getEconomicPurposeCode() !=null?dto.getEconomicPurposeCode().trim():"";
            String productName = dto.getProductName() !=null?dto.getProductName().trim():"";
            String sectorCode = dto.getSectorCode() !=null?dto.getSectorCode().trim():"";
            String sectorName = dto.getSectorName() !=null?dto.getSectorName().trim():"";
            String address1 = dto.getAddress1() !=null?dto.getAddress1().trim():"";
            String address2 = dto.getAddress2() !=null?dto.getAddress2().trim():"";
            String address3 = dto.getAddress3() !=null?dto.getAddress3().trim():"";
            String address4 = dto.getAddress4() !=null?dto.getAddress4().trim():"";
            String address5 = dto.getAddress5() !=null?dto.getAddress5().trim():"";
            String accountTitle = dto.getAccountTitle() !=null?dto.getAccountTitle().trim():"";
            String smeCodeIndustryScalID = dto.getSmeCodeIndustryScaleID() !=null?dto.getSmeCodeIndustryScaleID().trim():"";
            String interestRate = dto.getInterestRate() !=null?dto.getInterestRate().trim():"";
            String sanctionAmount = dto.getSanctionAmount() !=null?dto.getSanctionAmount().trim():"";
            String linkAccountStatus = dto.getLinkAccountStatus() !=null?dto.getLinkAccountStatus().trim():"";
            String linkAcProductCode = dto.getLinkACProductCode() !=null?dto.getLinkACProductCode().trim():"";
            String dealAcBasic = dto.getDealAcBasic() !=null?dto.getDealAcBasic().trim():"";
            String dealAcSuffix = dto.getDealAcSuffix() !=null?dto.getDealAcSuffix().trim():"";
            String partyId = dto.getPartyId() !=null?dto.getPartyId().trim():"";

            String docType = dto.getDocType() !=null?dto.getDocType().trim():"";
            String account = dto.getLoanACNo() !=null?dto.getLoanACNo().trim():"";
            String branchMnemonic = dto.getBranchMnemonic() !=null?dto.getBranchMnemonic().trim():"";
            String productCode = dto.getProductCode() !=null?dto.getProductCode().trim():"";
            String dealReference = dto.getDealReference() !=null?dto.getDealReference().trim():"";

            accountInformationEntity.setLastPaymentAmount(lastPaymentAmount);
            accountInformationEntity.setSettlementLinkAccountBalance(settlementLinkAccountBalance);
            accountInformationEntity.setLinkMotherAccountNo(linkMotherAccountNo);
            accountInformationEntity.setRoutingNo(routingNo);
            accountInformationEntity.setMobile(mobile);
            accountInformationEntity.setBranchName(branchName);
            accountInformationEntity.setBranchCode(branchCode);
            accountInformationEntity.setOverdue(overDue);
            accountInformationEntity.setEmiAmount(emiAmount);

            accountInformationEntity.setProductType(productType);
            accountInformationEntity.setActualTenor(actualTenor);
            accountInformationEntity.setTotalOutstanding(totalOutstanding);
            accountInformationEntity.setBorrowersName(borrowerName);
            accountInformationEntity.setProfession(profession);
            accountInformationEntity.setEmail(email);
            accountInformationEntity.setNid(nid);
            accountInformationEntity.setProfessionSegment(professionSegment);

            accountInformationEntity.setDisbursementAmount(disbursementAmount);
            accountInformationEntity.setCustomerId(customerId);
            accountInformationEntity.setCustomerName(customerName);
            accountInformationEntity.setCustomerType(customerType);
            accountInformationEntity.setSpouse(spouse);

            accountInformationEntity.setGender(gender);
            accountInformationEntity.setFatherName(fatherName);
            accountInformationEntity.setMotherName(motherName);
            accountInformationEntity.setNi(ni);
            accountInformationEntity.setTin(tin);

            accountInformationEntity.setContractNo(contractNo);
            accountInformationEntity.setContractNoHome(contractNoHome);
            accountInformationEntity.setEconomicPurposeName(economicPurposeName);
            accountInformationEntity.setEconomicPurposeCode(economicPurposeCode);
            accountInformationEntity.setProductName(productName);
            accountInformationEntity.setSectorCode(sectorCode);
            accountInformationEntity.setSectorName(sectorName);
            accountInformationEntity.setAddress1(address1);
            accountInformationEntity.setAddress2(address2);
            accountInformationEntity.setAddress3(address3);
            accountInformationEntity.setAddress4(address4);
            accountInformationEntity.setAddress5(address5);
            accountInformationEntity.setAccountTitle(accountTitle);
            accountInformationEntity.setSmeCodeIndustryScaleID(smeCodeIndustryScalID);
            accountInformationEntity.setInterestRate(interestRate);
            accountInformationEntity.setSanctionAmount(sanctionAmount);

            try {
                if (linkAccountStatus.toLowerCase().equals("n")) {
                    accountInformationEntity.setLinkAccountStatus("Active");
                }else if(linkAccountStatus.toLowerCase().equals("y")) {
                    accountInformationEntity.setLinkAccountStatus("Inactive");
                }
            }catch (Exception e){
                System.out.println("status");
            }

            accountInformationEntity.setLinkACProductCode(linkAcProductCode);
            accountInformationEntity.setDealAcBasic(dealAcBasic);
            accountInformationEntity.setDealAcSuffix(dealAcSuffix);
            accountInformationEntity.setPartyId(partyId);

            accountInformationEntity.setDocType(docType);
            accountInformationEntity.setBranchMnemonic(branchMnemonic);
            accountInformationEntity.setProductCode(productCode);
            accountInformationEntity.setDealReference(dealReference);

            accountInformationEntity.setLoanAccountNew(account+""+branchMnemonic+""+productCode+""+dealReference);

            try {
                if (dto.getLastPaymentDate() != null) {
                    accountInformationEntity.setLastPaymentDate(dateUtils.db2ToOracleDateFormat(dto.getLastPaymentDate().trim()));
                } else {
                    accountInformationEntity.setLastPaymentDate(dto.getLastPaymentDate());
                }
            }catch (Exception e){
                System.out.println("accountNo==="+dto.getLoanACNo() +"emidate = "+dto.getLastPaymentDate());
            }

            try{
                if (dto.getEmiDate() !=null) {
                    accountInformationEntity.setEmiDate(dateUtils.db2ToOracleDateFormat(dto.getEmiDate().trim()));
                }else {
                    accountInformationEntity.setEmiDate(dto.getEmiDate());
                }
            }catch (Exception e){
                System.out.println("accountNo==="+dto.getLoanACNo() +"emidate = "+dto.getEmiDate());
            }

            try{
                if (dto.getDisbursementDate() !=null) {
                    accountInformationEntity.setDisbursementDate(dateUtils.db2ToOracleDateFormat(dto.getDisbursementDate().trim()));
                }else {
                    accountInformationEntity.setDisbursementDate(dto.getDisbursementDate());
                }
            }catch (Exception e){
                System.out.println("accountNo==="+dto.getLoanACNo() +"emidate = "+dto.getEmiDate());
            }

            try {
                if (dto.getExpiryDate() != null) {
                    accountInformationEntity.setExpiryDate(dateUtils.db2ToOracleDateFormat(dto.getExpiryDate().trim()));
                } else {
                    accountInformationEntity.setExpiryDate(dto.getExpiryDate());
                }
            }catch (Exception e){
                System.out.println("accountNo==="+dto.getLoanACNo() +"emidate = "+dto.getExpiryDate());
            }

            try{
                if (dto.getDob() !=null) {
                    accountInformationEntity.setDob(dateUtils.db2ToOracleDateFormat(dto.getDob().trim()));
                }else {
                    accountInformationEntity.setDob(dto.getDob());
                }
            }catch (Exception e){
                System.out.println("loanacc = "+ dto.getLoanACNo() +"dob = "+ dto.getDob());
            }

            if ((dto.getLoanACNo() !=null)){
                accountInformationEntities.add(accountInformationEntity);
            }

            System.out.println("test "+dto.getLoanACNo());

            if(accountInformationEntities.size() == 1000){
                accountInformationRepository.saveAll(accountInformationEntities);
                System.out.println("innerlopp");
                accountInformationEntities.clear();
            }
        }

        if(accountInformationEntities.size() > 0 && accountInformationEntities.size() < 1000){
            System.out.println("outerloop");

            accountInformationRepository.saveAll(accountInformationEntities);
            accountInformationEntities.clear();
        }
    }

    public AccountInformationEntity getAccountInformation(String accountNo) {
        return accountInformationRepository.getByLoanAccountNo(accountNo);
    }

    public AccountInformationEntity getAllAccountInformation(String accountNo, String branchMnemonic, String productCode, String dealReference) {
        return accountInformationRepository.getAllByLoanAccountNo(accountNo,branchMnemonic,productCode,dealReference);
    }


    public AccountInformationEntity findAccountInformationByLoanAccountNo(String accountNumber) {
        return accountInformationRepository.findAccountInformationEntityByLoanACNo(accountNumber);
    }

    public List<AccountInformationEntity> findAccountInformationEntityByLoanAccountNo(String accountNumber){
        return accountInformationRepository.findAllByLoanACNo(accountNumber);
    }

    public List<AccountInformationEntity> findAll(){
        return accountInformationRepository.findAll();
    }

    public List<AccountInformationEntity> advancedSearch(String accountNo, String cif, String customerName, String motherName, String mobileNo, String nid, String dob, String email, String passportNo, String organization, String linkAccount, String customerId, String autoDebit, String loanId, String clsFlag, String active){
        if (!dob.isEmpty()) {
            String []monthShortNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            String []date = dob.split("/");
            for (int i = 0; i < monthShortNames.length; i++) {
                if (monthShortNames[i].equals(date[1])) {
                    int index = i + 1;
                    if (index < 10) date[1] =  "0" + String.valueOf(i + 1);
                    else date[1] = String.valueOf(i + 1);
                    break;
                }
            }
            dob = date[2]+"-"+date[1]+"-"+date[0];
            System.out.println(date[0]);
        }
        return accountInformationRepository.advancedSearch(accountNo,customerName, motherName, mobileNo, nid, dob, email, linkAccount, customerId);
//        advanceSearchPayload.getOrganization(), advanceSearchPayload.getAutoDebit(),advanceSearchPayload.getCif(), advanceSearchPayload.getClsFlag(),
    }

    public List<AccountInformationEntity> advancedSearch(AdvanceSearchPayload payload){
        return accountInformationRepository.advancedSearchDashboard(payload.getAccountNo(), payload.getCustomerName(), payload.getMotherName(), payload.getFatherName(), payload.getMobile(),
                payload.getNationalId(), payload.getDateOfBirth(), payload.getEmail(), payload.getLinkAccount(), payload.getCustomerId(), payload.getTin());
    }

    public ResponseEntity findAllAndPagination(int page, int length, String accountNo){
        Pageable pageElements = PageRequest.of(page, length);
        Page<AccountInformationEntity> allProducts;
        if (accountNo != null && accountNo != ""){
            allProducts = accountInformationRepository.findAllByLoanACNo(accountNo, pageElements);
        }else {
            allProducts = accountInformationRepository.findAllAcc(pageElements);
        }
        return ResponseEntity.ok(allProducts);
    }

}
