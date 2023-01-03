package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService;

import com.unisoft.collection.dashboard.AdvanceSearchPayload;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationDto;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationDao;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

           /* if (dto.getLoanACNo() !=null) {
                accountInformationEntity.setLoanACNo(dto.getLoanACNo().trim());
            }else {
                accountInformationEntity.setLoanACNo(dto.getLoanACNo());
            }
*/
            try {
                if (dto.getLastPaymentDate() != null) {
                    accountInformationEntity.setLastPaymentDate(dateUtils.db2ToOracleDateFormat(dto.getLastPaymentDate().trim()));
                } else {
                    accountInformationEntity.setLastPaymentDate(dto.getLastPaymentDate());
                }
            }catch (Exception e){
                System.out.println("accountNo==="+dto.getLoanACNo() +"emidate = "+dto.getLastPaymentDate());
            }

            if (dto.getLastPaymentAmount() !=null) {
                accountInformationEntity.setLastPaymentAmount(dto.getLastPaymentAmount().trim());
            }else {
                accountInformationEntity.setLastPaymentAmount(dto.getLastPaymentAmount());
            }
            if (dto.getSettlementLinkAccountBalance() !=null) {
                accountInformationEntity.setSettlementLinkAccountBalance(dto.getSettlementLinkAccountBalance().trim());
            }else {
                accountInformationEntity.setSettlementLinkAccountBalance(dto.getSettlementLinkAccountBalance());
            }

            if (dto.getLinkMotherAccountNo() !=null) {
                accountInformationEntity.setLinkMotherAccountNo(dto.getLinkMotherAccountNo().trim());
            }else {
                accountInformationEntity.setLinkMotherAccountNo(dto.getLinkMotherAccountNo());
            }

            if (dto.getRoutingNo() !=null) {
                accountInformationEntity.setRoutingNo(dto.getRoutingNo().trim());
            }else {
                accountInformationEntity.setRoutingNo(dto.getRoutingNo());
            }

            if (dto.getMobile() !=null) {
                accountInformationEntity.setMobile(dto.getMobile().trim());
            }else {
                accountInformationEntity.setMobile(dto.getMobile());
            }

            if (dto.getBranchName() !=null) {
                accountInformationEntity.setBranchName(dto.getBranchName().trim());
            }else {
                accountInformationEntity.setBranchName(dto.getBranchName());
            }

            if (dto.getBranchCode() !=null) {
                accountInformationEntity.setBranchCode(dto.getBranchCode().trim());
            }else {
                accountInformationEntity.setBranchCode(dto.getBranchCode());
            }

            if (dto.getOverdue() !=null) {
                accountInformationEntity.setOverdue(dto.getOverdue().trim());
            }else {
                accountInformationEntity.setOverdue(dto.getOverdue());
            }

            if (dto.getEmiAmount() !=null) {
                accountInformationEntity.setEmiAmount(dto.getEmiAmount().trim());
            }else {
                accountInformationEntity.setEmiAmount(dto.getEmiAmount());
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

            // accountInformationEntity.setLastPaymentAmount(resultSet.getString("OMNWR01"));
            if (dto.getProductType() !=null) {
                accountInformationEntity.setProductType(dto.getProductType().trim());
            }else {
                accountInformationEntity.setProductType(dto.getProductType());
            }
            //accountInformationDto.setSanctionAmount(resultSet.getString("OTDLA2"));

            if (dto.getActualTenor() !=null) {
                accountInformationEntity.setActualTenor(dto.getActualTenor().trim());
            }else {
                accountInformationEntity.setActualTenor(dto.getActualTenor());
            }

            if (dto.getLatestDisbursementDate() !=null) {
                accountInformationEntity.setDisbursementDate(dateUtils.db2ToOracleDateFormat(dto.getDisbursementDate().trim()));
            }else {
                accountInformationEntity.setDisbursementDate(dto.getDisbursementDate());
            }

            if (dto.getTotalOutstanding() !=null) {
                accountInformationEntity.setTotalOutstanding(dto.getTotalOutstanding().trim());
            }else {
                accountInformationEntity.setTotalOutstanding(dto.getTotalOutstanding());
            }

            if (dto.getBorrowersName() !=null) {
                accountInformationEntity.setBorrowersName(dto.getBorrowersName().trim());
            }else {
                accountInformationEntity.setBorrowersName(dto.getBorrowersName());
            }

            if (dto.getProfession() !=null) {
                accountInformationEntity.setProfession(dto.getProfession().trim());
            }else {
                accountInformationEntity.setProfession(dto.getProfession());
            }

            if (dto.getEmail() !=null) {
                accountInformationEntity.setEmail(dto.getEmail().trim());
            }else {
                accountInformationEntity.setEmail(dto.getEmail());
            }

            if (dto.getNid() !=null) {
                accountInformationEntity.setNid(dto.getNid().trim());
            }else {
                accountInformationEntity.setNid(dto.getNid());
            }

            if (dto.getProfessionSegment() !=null) {
                accountInformationEntity.setProfessionSegment(dto.getProfessionSegment().trim());
            }else {
                accountInformationEntity.setProfessionSegment(dto.getProfessionSegment());
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

            if (dto.getDisbursementAmount() !=null) {
                accountInformationEntity.setDisbursementAmount(dto.getDisbursementAmount().trim());
            }else {
                accountInformationEntity.setDisbursementAmount(dto.getDisbursementAmount());
            }


            if (dto.getCustomerName() !=null) {
                accountInformationEntity.setCustomerName(dto.getCustomerName().trim());
            }else {
                accountInformationEntity.setCustomerName(dto.getCustomerName());
            }
            //accountInformationDto.setCustomerId(resultSet.getString("GFCPNC"));
            // accountInformationDto.setCustomerType(resultSet.getString(""));
            // accountInformationDto.setSpouse(resultSet.getString(""));

            try{
                if (dto.getDob() !=null) {
                    accountInformationEntity.setDob(dateUtils.db2ToOracleDateFormat(dto.getDob().trim()));
                }else {
                    accountInformationEntity.setDob(dto.getDob());
                }
            }catch (Exception e){
                System.out.println("loanacc = "+ dto.getLoanACNo() +"dob = "+ dto.getDob());
            }


            if (dto.getGender() !=null) {
                accountInformationEntity.setGender(dto.getGender().trim());
            }else {
                accountInformationEntity.setGender(dto.getGender());
            }

            if (dto.getFatherName() !=null) {
                accountInformationEntity.setFatherName(dto.getFatherName().trim());
            }else {
                accountInformationEntity.setFatherName(dto.getFatherName());
            }

            if (dto.getMotherName() !=null) {
                accountInformationEntity.setMotherName(dto.getMotherName().trim());
            }else {
                accountInformationEntity.setMotherName(dto.getMotherName());
            }

            if (dto.getNi() !=null) {
                accountInformationEntity.setNi(dto.getNi().trim());
            }else {
                accountInformationEntity.setNi(dto.getNi());
            }

            if (dto.getTin() !=null) {
                accountInformationEntity.setTin(dto.getTin().trim());
            }else {
                accountInformationEntity.setTin(dto.getTin());
            }


            //****************** new add *********************

            if (dto.getContractNo() !=null) {
                accountInformationEntity.setContractNo(dto.getContractNo().trim());
            }else {
                accountInformationEntity.setContractNo(dto.getContractNo());
            }

            if (dto.getContractNoHome() !=null) {
                accountInformationEntity.setContractNoHome(dto.getContractNoHome().trim());
            }else {
                accountInformationEntity.setContractNoHome(dto.getContractNoHome());
            }

            if (dto.getEconomicPurposeName() !=null) {
                accountInformationEntity.setEconomicPurposeName(dto.getEconomicPurposeName().trim());
            }else {
                accountInformationEntity.setEconomicPurposeName(dto.getEconomicPurposeName());
            }

            if (dto.getEconomicPurposeCode() !=null) {
                accountInformationEntity.setEconomicPurposeCode(dto.getEconomicPurposeCode().trim());
            }else {
                accountInformationEntity.setEconomicPurposeCode(dto.getEconomicPurposeCode());
            }

            if (dto.getProductName() !=null) {
                accountInformationEntity.setProductName(dto.getProductName().trim());
            }else {
                accountInformationEntity.setProductName(dto.getProductName());
            }

            if (dto.getSectorCode() !=null) {
                accountInformationEntity.setSectorCode(dto.getSectorCode().trim());
            }else {
                accountInformationEntity.setSectorCode(dto.getSectorCode());
            }

            if (dto.getSectorName() !=null) {
                accountInformationEntity.setSectorCode(dto.getSectorName().trim());
            }else {
                accountInformationEntity.setSectorCode(dto.getSectorName());
            }

            if (dto.getAddress1() !=null) {
                accountInformationEntity.setAddress1(dto.getAddress1().trim());
            }else {
                accountInformationEntity.setAddress1(dto.getAddress1());
            }

            if (dto.getAddress2() !=null) {
                accountInformationEntity.setAddress2(dto.getAddress2().trim());
            }else {
                accountInformationEntity.setAddress2(dto.getAddress2());
            }

            if (dto.getAddress3() !=null) {
                accountInformationEntity.setAddress3(dto.getAddress3().trim());
            }else {
                accountInformationEntity.setAddress3(dto.getAddress3());
            }

            if (dto.getAddress4() !=null) {
                accountInformationEntity.setAddress4(dto.getAddress4().trim());
            }else {
                accountInformationEntity.setAddress4(dto.getAddress4());
            }

            if (dto.getAddress5() !=null) {
                accountInformationEntity.setAddress5(dto.getAddress5().trim());
            }else {
                accountInformationEntity.setAddress5(dto.getAddress5());
            }

            if (dto.getAccountTitle() !=null) {
                accountInformationEntity.setAccountTitle(dto.getAccountTitle().trim());
            }else {
                accountInformationEntity.setAccountTitle(dto.getAccountTitle());
            }

            if (dto.getSmeCodeIndustryScaleID() !=null) {
                accountInformationEntity.setSmeCodeIndustryScaleID(dto.getSmeCodeIndustryScaleID().trim());
            }else {
                accountInformationEntity.setSmeCodeIndustryScaleID(dto.getSmeCodeIndustryScaleID());
            }

            if (dto.getInterestRate() !=null) {
                accountInformationEntity.setInterestRate(dto.getInterestRate().trim());
            }else {
                accountInformationEntity.setInterestRate(dto.getInterestRate());
            }

            if (dto.getSanctionAmount() !=null) {
                accountInformationEntity.setSanctionAmount(dto.getSanctionAmount().trim());
            }else {
                accountInformationEntity.setSanctionAmount(dto.getSanctionAmount());
            }

            if (dto.getLinkAccountStatus() !=null) {
                accountInformationEntity.setLinkAccountStatus(dto.getLinkAccountStatus().trim());
            }else {
                accountInformationEntity.setLinkAccountStatus(dto.getLinkAccountStatus());
            }

            if (dto.getLinkACProductCode() !=null) {
                accountInformationEntity.setLinkACProductCode(dto.getLinkACProductCode().trim());
            }else {
                accountInformationEntity.setLinkACProductCode(dto.getLinkACProductCode());
            }

            if (dto.getBranchMnemonic() !=null) {
                accountInformationEntity.setBranchMnemonic(dto.getBranchMnemonic().trim());
            }else {
                accountInformationEntity.setBranchMnemonic(dto.getBranchMnemonic());
            }

            if (dto.getDealAcBasic() !=null) {
                accountInformationEntity.setDealAcBasic(dto.getDealAcBasic().trim());
            }else {
                accountInformationEntity.setDealAcBasic(dto.getDealAcBasic());
            }

            if (dto.getDealAcSuffix() !=null) {
                accountInformationEntity.setDealAcSuffix(dto.getDealAcSuffix().trim());
            }else {
                accountInformationEntity.setDealAcSuffix(dto.getDealAcSuffix());
            }

            if (dto.getPartyId() !=null) {
                accountInformationEntity.setPartyId(dto.getPartyId().trim());
            }else {
                accountInformationEntity.setPartyId(dto.getPartyId());
            }

            if (dto.getDocType() !=null) {
                accountInformationEntity.setDocType(dto.getDocType().trim());
            }else {
                accountInformationEntity.setDocType(dto.getDocType());
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

}
