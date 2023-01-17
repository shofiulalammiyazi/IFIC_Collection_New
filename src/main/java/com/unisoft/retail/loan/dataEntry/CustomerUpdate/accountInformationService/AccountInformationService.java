package com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService;

import com.unisoft.collection.dashboard.AdvanceSearchPayload;
import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.distribution.loan.LoanDistributionService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationDto;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationDao;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Autowired
    private LoanAccountDistributionRepository loanAccountDistributionRepository;

    //@Scheduled("")
    public void getAccountInformationData() {

        List<AccountInformationDto> dataList = accountInformationDao.getData();
        List<AccountInformationEntity> accountInformationEntities = new ArrayList<>();

        for (AccountInformationDto dto : dataList) {
            if ((dto.getLoanACNo() != null && dto.getLoanACNo().trim().length() == 13)
                    && (dto.getBranchMnemonic() != null && dto.getBranchMnemonic().trim().length() > 0)
                    && (dto.getProductCode() != null && dto.getProductCode().trim().length() > 0)
                    && (dto.getDealReference() != null && dto.getDealReference().trim().length() > 0)) {

                String account = dto.getLoanACNo().trim();
                String branchMnemonic = dto.getBranchMnemonic().trim();
                String productCode = dto.getProductCode().trim();
                String dealReference = dto.getDealReference().trim();

                AccountInformationEntity accountInformationEntity;

                accountInformationEntity = accountInformationRepository.
                        findByLoanACNoAndBranchMnemonicAndProductCodeAndDealReference(account, branchMnemonic, productCode, dealReference);


                if (accountInformationEntity == null) {
                    accountInformationEntity = new AccountInformationEntity();
                    accountInformationEntity.setCreatedDate(new Date());

                    accountInformationEntity.setLoanACNo(dto.getLoanACNo().trim());

                } else {
                    accountInformationEntity.setModifiedDate(new Date());
                }


                String lastPaymentAmount = dto.getLastPaymentAmount();
                String settlementLinkAccountBalance = dto.getSettlementLinkAccountBalance();
                String linkMotherAccountNo = dto.getLinkMotherAccountNo();
                String routingNo = dto.getRoutingNo();
                String mobile = dto.getMobile();
                String branchName = dto.getBranchName();
                String branchCode = dto.getBranchCode();
                String overDue = dto.getOverdue();
                String emiAmount = dto.getEmiAmount();

                String productType = dto.getProductType();
                String actualTenor = dto.getActualTenor();
                String totalOutstanding = dto.getTotalOutstanding();
                String borrowerName = dto.getBorrowersName();
                String profession = dto.getProfession();
                String email = dto.getEmail();
                String nid = dto.getNid();
                String professionSegment = dto.getProfessionSegment();

                String disbursementAmount = dto.getDisbursementAmount();
                String customerId = dto.getCustomerId();
                String customerName = dto.getCustomerName();
                String customerType = dto.getCustomerType();
                String spouse = dto.getSpouse();

                String gender = dto.getGender();
                String fatherName = dto.getFatherName();
                String motherName = dto.getMotherName();
                String ni = dto.getNi();
                String tin = dto.getTin();

                String contractNo = dto.getContractNo();
                String contractNoHome = dto.getContractNoHome();
                String economicPurposeName = dto.getEconomicPurposeName();
                String economicPurposeCode = dto.getEconomicPurposeCode();
                String productName = dto.getProductName();
                String sectorCode = dto.getSectorCode();
                String sectorName = dto.getSectorName();
                String address1 = dto.getAddress1();
                String address2 = dto.getAddress2();
                String address3 = dto.getAddress3();
                String address4 = dto.getAddress4();
                String address5 = dto.getAddress5();
                String accountTitle = dto.getAccountTitle();
                String smeCodeIndustryScalID = dto.getSmeCodeIndustryScaleID();
                String interestRate = dto.getInterestRate();
                String sanctionAmount = dto.getSanctionAmount();
                String linkAccountStatus = dto.getLinkAccountStatus();
                String linkAcProductCode = dto.getLinkACProductCode();
                String dealAcBasic = dto.getDealAcBasic();
                String dealAcSuffix = dto.getDealAcSuffix();
                String partyId = dto.getPartyId();

                String docType = dto.getDocType();

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

                //new added at 16-01-2023
                accountInformationEntity.setDivision(dto.getDivision());
                accountInformationEntity.setDistrict(dto.getDistrict());
                accountInformationEntity.setScheduleStartDate(dto.getScheduleStartDate());
                accountInformationEntity.setDealBalanceAtStartDate(dto.getDealBalanceAtStartDate());
                accountInformationEntity.setCalculatedMaturityDate(dto.getCalculatedMaturityDate());
                accountInformationEntity.setFirstRepaymentAmount(dto.getFirstRepaymentAmount());
                accountInformationEntity.setLastRepaymentAmount(dto.getLastRepaymentAmount());
                accountInformationEntity.setTotalNoOfInstallment(dto.getTotalNoOfInstallment());
                accountInformationEntity.setFrequencyCode(dto.getFrequencyCode());
                accountInformationEntity.setLoanCLStatus(dto.getLoanCLStatus());
                accountInformationEntity.setLatestDisbursementAmount(dto.getLatestDisbursementAmount());
                accountInformationEntity.setSanctionAmount(dto.getSanctionAmount());
                //accountInformationEntity.setNextEMIDate(dto.getNextEMIDate());

                try {
                    accountInformationEntity.setNextEMIDate(dateUtils.db2ToOracleDateFormat(dto.getNextEMIDate().trim()));
                } catch (Exception e) {
                    //accountInformationEntity.setLatestDisbursementDate(dto.getLatestDisbursementDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "getLatestDisbursementDate = " + dto.getNextEMIDate());
                }

                if(dto.getJointStatus() == null || dto.getJointStatus().equalsIgnoreCase("n"))
                    accountInformationEntity.setJointStatus("NOT JOINT");
                else
                    accountInformationEntity.setJointStatus("JOINT");

                try {
                    accountInformationEntity.setLatestDisbursementDate(dateUtils.db2ToOracleDateFormat(dto.getLatestDisbursementDate().trim()));
                } catch (Exception e) {
                    accountInformationEntity.setLatestDisbursementDate(dto.getLatestDisbursementDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "getLatestDisbursementDate = " + dto.getLatestDisbursementDate());
                }


                try {
                    if (linkAccountStatus.toLowerCase().equals("n")) {
                        accountInformationEntity.setLinkAccountStatus("Active");
                    } else if (linkAccountStatus.toLowerCase().equals("y")) {
                        accountInformationEntity.setLinkAccountStatus("Inactive");
                    }
                } catch (Exception e) {
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

                accountInformationEntity.setLoanAccountNew(account + "" + branchMnemonic + "" + productCode + "" + dealReference);

                if (!accountInformationEntity.getIsDistributed().equalsIgnoreCase("Y"))
                    accountInformationEntity.setIsDistributed("N");
                else
                    accountInformationEntity.setIsDistributed("N");

                try {
                    accountInformationEntity.setLastPaymentDate(dateUtils.db2ToOracleDateFormat(dto.getLastPaymentDate().trim()));
                } catch (Exception e) {
                    accountInformationEntity.setLastPaymentDate(dto.getLastPaymentDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "emidate = " + dto.getLastPaymentDate());
                }
                try {
                    accountInformationEntity.setEmiDate(dateUtils.db2ToOracleDateFormat(dto.getEmiDate().trim()));

                } catch (Exception e) {
                    accountInformationEntity.setEmiDate(dto.getEmiDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "emidate = " + dto.getEmiDate());
                }
                try {
                    accountInformationEntity.setDisbursementDate(dateUtils.db2ToOracleDateFormat(dto.getDisbursementDate().trim()));
                } catch (Exception e) {
                    accountInformationEntity.setDisbursementDate(dto.getDisbursementDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "emidate = " + dto.getDisbursementDate());
                }
                try {
                    accountInformationEntity.setExpiryDate(dateUtils.db2ToOracleDateFormat(dto.getExpiryDate().trim()));
                } catch (Exception e) {
                    accountInformationEntity.setExpiryDate(dto.getExpiryDate());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "emidate = " + dto.getExpiryDate());
                }
                try {
                    accountInformationEntity.setDob(dateUtils.db2ToOracleDateFormat(dto.getDob().trim()));
                } catch (Exception e) {
                    accountInformationEntity.setDob(dto.getDob());
                    System.out.println("accountNo===" + dto.getLoanACNo() + "emidate = " + dto.getDob());
                }
                accountInformationEntity.setDisbursementDate(dto.getDisbursementDate());
                accountInformationEntities.add(accountInformationEntity);

                System.out.println("test " + dto.getLoanACNo());

                LoanAccountDistributionInfo loanAccountDistributionInfo =
                        loanAccountDistributionRepository.findByAccountNoAndLatest(dto.getLoanACNo().trim(), "1");

                if (loanAccountDistributionInfo != null &&
                        (!loanAccountDistributionInfo.getOutStanding().equals(dto.getTotalOutstanding())
                                || !String.valueOf(loanAccountDistributionInfo.getOpeningOverDue()).equals(dto.getOverdue()))) {
                    UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    LoanAccountDistributionInfo loanAccountDistributionInfo1 = new LoanAccountDistributionInfo();
                    BeanUtils.copyProperties(loanAccountDistributionInfo, loanAccountDistributionInfo1);
                    loanAccountDistributionInfo.setLatest("0");
                    loanAccountDistributionInfo.setStartDate(loanAccountDistributionInfo.getCreatedDate());
                    loanAccountDistributionInfo.setEndDate(new Date());

                    loanAccountDistributionRepository.save(loanAccountDistributionInfo);

                    loanAccountDistributionInfo1.setLatest("1");
                    loanAccountDistributionInfo1.setWriteOffAccount("0");
                    loanAccountDistributionInfo1.setSamAccount("0");
                    loanAccountDistributionInfo1.setCreatedDate(new Date());
                    loanAccountDistributionInfo1.setCreatedBy(user.getUsername());
                    loanAccountDistributionInfo1.setStatusDate(new Date());
                    loanAccountDistributionInfo1.setOutStanding(dto.getTotalOutstanding());
                    loanAccountDistributionInfo1.setOpeningOverDue(dto.getOverdue() != null ? Double.parseDouble(dto.getOverdue()) : 0.0);
                    loanAccountDistributionInfo1.setDpdBucket(dto.getDpd());
                    loanAccountDistributionInfo1.setEmiAmount(Double.parseDouble(dto.getEmiAmount()));
                    loanAccountDistributionInfo1.setStartDate(new Date());
                    loanAccountDistributionRepository.save(loanAccountDistributionInfo1);
                }

                if (accountInformationEntities.size() == 1000) {
                    accountInformationRepository.saveAll(accountInformationEntities);
                    System.out.println("innerlopp");
                    accountInformationEntities.clear();
                }
            }
        }


        if (accountInformationEntities.size() > 0 && accountInformationEntities.size() < 1000) {
            System.out.println("outerloop");

            accountInformationRepository.saveAll(accountInformationEntities);
            accountInformationEntities.clear();
        }
    }

    public AccountInformationEntity getAccountInformation(String accountNo) {
        return accountInformationRepository.getByLoanAccountNo(accountNo);
    }

    public AccountInformationEntity getAllAccountInformation(String accountNo, String branchMnemonic, String productCode, String dealReference) {
        return accountInformationRepository.getAllByLoanAccountNo(accountNo, branchMnemonic, productCode, dealReference);
    }


    public AccountInformationEntity findAccountInformationByLoanAccountNo(String accountNumber) {
        return accountInformationRepository.findAccountInformationEntityByLoanACNo(accountNumber);
    }

    public List<AccountInformationEntity> findAccountInformationEntityByLoanAccountNo(String accountNumber) {
        return accountInformationRepository.findAllByLoanACNo(accountNumber);
    }

    public AccountInformationEntity findAccountInformationEntityByLoanAccountNew(String accountNumber) {
        return accountInformationRepository.findAccountInformationEntityByLoanAccountNew(accountNumber);
    }

    public List<AccountInformationEntity> findAll() {
        return accountInformationRepository.findAll();
    }

    public List<AccountInformationEntity> advancedSearch(String accountNo, String cif, String customerName, String motherName, String mobileNo, String nid, String dob, String email, String passportNo, String organization, String linkAccount, String customerId, String autoDebit, String loanId, String clsFlag, String active) {
        if (!dob.isEmpty()) {
            String[] monthShortNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            String[] date = dob.split("/");
            for (int i = 0; i < monthShortNames.length; i++) {
                if (monthShortNames[i].equals(date[1])) {
                    int index = i + 1;
                    if (index < 10) date[1] = "0" + String.valueOf(i + 1);
                    else date[1] = String.valueOf(i + 1);
                    break;
                }
            }
            dob = date[2] + "-" + date[1] + "-" + date[0];
            System.out.println(date[0]);
        }
        return accountInformationRepository.advancedSearch(accountNo, customerName, motherName, mobileNo, nid, dob, email, linkAccount, customerId);
//        advanceSearchPayload.getOrganization(), advanceSearchPayload.getAutoDebit(),advanceSearchPayload.getCif(), advanceSearchPayload.getClsFlag(),
    }

    public List<AccountInformationEntity> advancedSearch(AdvanceSearchPayload payload) {
        return accountInformationRepository.advancedSearchDashboard(payload.getAccountNo(), payload.getCustomerName(), payload.getMotherName(), payload.getFatherName(), payload.getMobile(),
                payload.getNationalId(), payload.getDateOfBirth(), payload.getEmail(), payload.getLinkAccount(), payload.getCustomerId(), payload.getTin());
    }

    public ResponseEntity findAllAndPagination(int page, int length, String accountNo) {
        Pageable pageElements = PageRequest.of(page, length);
        Page<AccountInformationEntity> allProducts;
        if (accountNo != null && accountNo != "") {
            allProducts = accountInformationRepository.findAllByLoanACNo(accountNo, pageElements);
        } else {
            allProducts = accountInformationRepository.findAllAcc(pageElements);
        }
        return ResponseEntity.ok(allProducts);
    }

    public ResponseEntity findAllAndPaginationByIsSmsSent(int page, int length, String accountNo) {
        Pageable pageElements = PageRequest.of(page, length);
        Page<AccountInformationEntity> allProducts;
        if (accountNo != null && accountNo != "") {
            allProducts = accountInformationRepository.findAllByLoanACNoByIsSmsEntity(accountNo, pageElements);
        } else {
            allProducts = accountInformationRepository.findAllAccIsSmsEntity(pageElements);
        }
        return ResponseEntity.ok(allProducts);
    }

}
