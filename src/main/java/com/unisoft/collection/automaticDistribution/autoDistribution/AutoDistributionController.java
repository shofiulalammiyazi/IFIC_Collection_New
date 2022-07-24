package com.unisoft.collection.automaticDistribution.autoDistribution;

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicService;
import com.unisoft.collection.automaticDistribution.distributionExceptionLoan.DistributionExceptionLoan;
import com.unisoft.collection.automaticDistribution.distributionExceptionLoan.DistributionExceptionLoanRepository;
import com.unisoft.collection.automaticDistribution.postInterim.PostInterimLoanDistributionTemp;
import com.unisoft.collection.automaticDistribution.postInterim.PostInterimLoanDistributionTempRepository;
import com.unisoft.collection.automaticDistribution.postInterimPeopleLoan.PostPeopleLoan;
import com.unisoft.collection.automaticDistribution.postInterimPeopleLoan.PostPeopleLoanRepository;
import com.unisoft.collection.automaticDistribution.samDistributionRule.SamDistributionRuleInfo;
import com.unisoft.collection.automaticDistribution.samDistributionRule.SamDistributionRuleRepository;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionInfo;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionRepository;
import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.AutoDistributionTemp;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.AutoDistributionTempRepository;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanApi.LoanApiPayload;
import com.unisoft.collection.distribution.samAccountHandover.AutoDistributionLoanSamTemp;
import com.unisoft.collection.distribution.samAccountHandover.AutoDistributionLoanSamTempRepository;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverInfo;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverRepository;
import com.unisoft.collection.distribution.unallocated.UnallocatedLoanList;
import com.unisoft.collection.distribution.unallocated.UnallocatedLoanListRepository;
import com.unisoft.collection.settings.dealerPerformance.DealerPerformance;
import com.unisoft.collection.settings.dealerPerformance.DealerPerformanceRepository;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.user.UserPrincipal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/*
Created by   Islam at 8/27/2019
*/
@Controller
@AllArgsConstructor
@RequestMapping(value = "/collection/autodistribution")
public class AutoDistributionController {

    private LoanAccountDistributionRepository loanAccountDistributionRepository;

    private DealerPerformanceRepository dealerPerformanceRepository;

    private PeopleAllocationLogicService peopleAllocationLogicService;

    private AutoDistributionTempRepository autoDistributionTempRepository;

    private DPDBucketService dpdBucketService;

    private DistributionExceptionLoanRepository distributionExceptionLoanRepository;

    private SamDistributionRuleRepository samDistributionRuleRepository;

    private AutoDistributionLoanSamTempRepository autoDistributionLoanSamTempRepository;

    private PostPeopleLoanRepository postPeopleLoanRepository;

    private PostInterimLoanDistributionTempRepository postInterimLoanDistributionTempRepository;

    private UnallocatedLoanListRepository unallocatedLoanListRepository;

    private LoanAgencyDistributionRepository loanAgencyDistributionRepository;

    private LoanAccountBasicService loanAccountBasicService;

    private SamAccountHandoverRepository samAccountHandoverRepository;


    @GetMapping
    public String getAutoDistributionPage() {
//        List<UnallocatedLoanList> unallocatedLoanLists = new ArrayList<>();
//        List<UnallocatedCardList> unallocatedCardLists = new ArrayList<>();
//
//        unallocatedLoanListRepository.deleteAll(unallocatedLoanListRepository.findAll());
//        unallocatedCardListRepository.deleteAll(unallocatedCardListRepository.findAll());
//
//        List<LoanAccountDistributionInfo> loanAccountDistributionInfos = loanAccountDistributionRepository.findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByCreatedDateDesc(getStartDate(), getEndDate(), "0", "0", "1");
//        for(LoanAccountDistributionInfo l: loanAccountDistributionInfos){
//            UnallocatedLoanList unallocatedLoanList = new UnallocatedLoanList();
//            unallocatedLoanList.setLoanAccountBasicInfo(l.getLoanAccountBasicInfo());
//            unallocatedLoanList.setDpdBucket(l.getDpdBucket());
//            unallocatedLoanList.setDpd(l.getDpd());
//            unallocatedLoanList.setOpeningOverDue(l.getOpeningOverDue());
//            unallocatedLoanList.setOutStanding(l.getOutStanding());
//            unallocatedLoanList.setDealerPin("0");
//            unallocatedLoanList.setDealerName("0");
//            unallocatedLoanList.setSupervisorPin("0");
//            unallocatedLoanList.setSupervisorName("0");
//            unallocatedLoanList.setSamAccount("0");
//            unallocatedLoanList.setWriteOffAccount("0");
//            unallocatedLoanList.setCreatedDate(new Date());
//            unallocatedLoanList.setProductGroup(l.getProductGroup());
//            unallocatedLoanList.setSchemaCode(l.getSchemaCode());
//            unallocatedLoanLists.add(unallocatedLoanList);
//
//        }
//        List<CardAccountDistributionInfo> cardAccountDistributionInfos = cardAccountDistributionRepository.findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByCreatedDateDesc(getStartDate(), getEndDate(), "0", "0", "1");
//        for(CardAccountDistributionInfo c: cardAccountDistributionInfos){
//            UnallocatedCardList unallocatedCardList = new UnallocatedCardList();
//            unallocatedCardList.setCardAccountBasicInfo(c.getCardAccountBasicInfo());
//            unallocatedCardList.setAgeCode(c.getAgeCode());
//            unallocatedCardList.setBillingCycle(c.getBillingCycle());
//            unallocatedCardList.setOutstandingAmount(c.getOutstandingAmount());
//            unallocatedCardList.setDealerPin("0");
//            unallocatedCardList.setDealerName("0");
//            unallocatedCardList.setSupervisorPin("0");
//            unallocatedCardList.setSupervisorName("0");
//            unallocatedCardList.setSamAccount("0");
//            unallocatedCardList.setWriteOffAccount("0");
//            unallocatedCardList.setVvip(c.getVvip());
//            unallocatedCardList.setSecureCard(c.getSecureCard());
//            unallocatedCardList.setCreatedDate(new Date());
//            unallocatedCardList.setProductGroup(c.getProductGroup());
//            unallocatedCardList.setPlasticCd(c.getPlasticCd());
//            unallocatedCardLists.add(unallocatedCardList);
//        }
//        unallocatedLoanListRepository.saveAll(unallocatedLoanLists);
//        unallocatedCardListRepository.saveAll(unallocatedCardLists);
        return "collection/automaticDistribution/main";
    }

    @PostMapping
    public String getAccountList() {
        List<DPDBucketEntity> dpdBucketEntities = dpdBucketService.getActiveList();
        DistributionExceptionLoan distributionExceptionLoan = distributionExceptionLoanRepository.findFirstByOrderByIdDesc();

        List<String> samIngoreList = new ArrayList<>();
        List<String> writeOffIgnoreList = new ArrayList<>();
        List<String> productGroupList = distributionExceptionLoan.getProductsList()
                .stream().map(p -> p.getCode().toUpperCase()).collect(Collectors.toList());

        boolean samIgnore = distributionExceptionLoan.getSamIgnore().equals("Yes") ? samIngoreList.add("1") : samIngoreList.add("2");
        boolean writeOffIngore = distributionExceptionLoan.getWriteOffIgnore().equals("Yes") ? writeOffIgnoreList.add("1") : writeOffIgnoreList.add("2");
        if (productGroupList.size() == 0) productGroupList.add("All Product");

//        List<LoanAccountDistributionInfo> previousLoanDistributionInfos = loanAccountDistributionRepository
//                .findByCreatedDateLessThanAndSamAccountNotInAndWriteOffAccountNotInAndSchemaCodeNotIn(startDate, samIngoreList, writeOffIgnoreList,
//                        productGroupList);
        List<LoanAccountDistributionInfo> previousLoanDistributionInfos = new ArrayList<>();

        List<UnallocatedLoanList> unallocatedLoanLists = unallocatedLoanListRepository
                .findByCreatedDateIsBetweenAndSamAccountNotInAndWriteOffAccountNotInAndSchemaCodeNotIn(
                        getStartDate(), getEndDate(), samIngoreList, writeOffIgnoreList, productGroupList);

        for (UnallocatedLoanList l : unallocatedLoanLists) {
            LoanAccountDistributionInfo loanAccountDistributionInfo = new LoanAccountDistributionInfo();
            loanAccountDistributionInfo.setLoanAccountBasicInfo(l.getLoanAccountBasicInfo());
            loanAccountDistributionInfo.setDpdBucket(l.getDpdBucket());
            loanAccountDistributionInfo.setDpd((double) l.getDpd());
            loanAccountDistributionInfo.setOpeningOverDue(l.getOpeningOverDue());
            loanAccountDistributionInfo.setOutStanding(Double.toString(l.getOutStanding()));
            loanAccountDistributionInfo.setDealerPin("0");
            loanAccountDistributionInfo.setDealerName("0");
            loanAccountDistributionInfo.setSupervisorPin("0");
            loanAccountDistributionInfo.setSupervisorName("0");
            loanAccountDistributionInfo.setSamAccount("0");
            loanAccountDistributionInfo.setWriteOffAccount("0");
            loanAccountDistributionInfo.setCreatedDate(new Date());
            loanAccountDistributionInfo.setProductGroup(l.getProductGroup());
            loanAccountDistributionInfo.setSchemeCode(l.getSchemaCode());
            previousLoanDistributionInfos.add(loanAccountDistributionInfo);

        }


        previousLoanDistributionInfos.sort((o1, o2) -> {
            if (o1.getCreatedDate() == null || o2.getCreatedDate() == null)
                return 0;
            return o1.getCreatedDate().compareTo(o2.getCreatedDate());
        });

        List<LoanAccountDistributionInfo> uniquePreviousLoandDistributionInfos = new ArrayList<>();
        Set<String> productList = new HashSet<>();

        for (int i = previousLoanDistributionInfos.size() - 1; i >= 0; i--) {
            LoanAccountDistributionInfo loanAccountDistributionInfo = previousLoanDistributionInfos.get(i);
            boolean check = uniquePreviousLoandDistributionInfos.stream().
                    anyMatch(f -> f.getLoanAccountBasicInfo().getId()
                            .equals(loanAccountDistributionInfo.getLoanAccountBasicInfo().getId()));

            if (!check) {
                String accountProductGroup = loanAccountDistributionInfo.getSchemeCode().toUpperCase();
                String accountDpdBucket = loanAccountDistributionInfo.getDpdBucket().toUpperCase();

                boolean b = distributionExceptionLoan.getProductGroupDpdBuckets()
                        .stream()
                        .anyMatch(p -> p.getProductGroup().toUpperCase().equals(accountProductGroup)
                                && p.getDpdBucket().toUpperCase().equals(accountDpdBucket));

                if (!b) {
                    productList.add(loanAccountDistributionInfo.getSchemeCode());
                    uniquePreviousLoandDistributionInfos.add(loanAccountDistributionInfo);
                }
            }
        }
        List<LoanAccountDistributionInfo> bucketAndOutstandingSortingLoanDistributionInfos = new ArrayList<>();

        for (String s : productList) {
            List<LoanAccountDistributionInfo> collect = uniquePreviousLoandDistributionInfos
                    .stream()
                    .filter(f -> f.getSchemeCode().equals(s)).collect(Collectors.toList());

            for (DPDBucketEntity d : dpdBucketEntities) {
                String bucketName = d.getBucketName();
                List<LoanAccountDistributionInfo> list = collect
                        .stream()
                        .filter(f -> f.getDpdBucket().equals(bucketName))
                        .sorted((o1, o2) -> Double.compare(Double.parseDouble(o2.getOutStanding()),
                                Double.parseDouble(o1.getOutStanding()))).collect(Collectors.toList());

                bucketAndOutstandingSortingLoanDistributionInfos.addAll(list);
            }

        }

        List<DealerPerformance> all = dealerPerformanceRepository.findAllByEmployeeInfoEntityNotIn(distributionExceptionLoan.getEmployeeInfos());
        for (DealerPerformance d : all) {

            // NEED TO ADJUST CODE HERE
            PeopleAllocationLogicInfo byDealerId = peopleAllocationLogicService.getByDealerIdAndDistributionType(d.getEmployeeInfoEntity(), "Loan", "Regular");
            if (byDealerId != null && byDealerId.getUnit().equals("Loan")) {
                d.setProductGroupEntityList(byDealerId.getProductTypeEntityLoan());
                d.setProductTypeEntityList(byDealerId.getProductTypeEntity());
                d.setDpdBucketEntityList(byDealerId.getDpdBucketEntity());
            }
        }

        all.sort(Comparator.comparingInt(DealerPerformance::getOverAllKpi).reversed());
        List<LoanAccountDistributionInfo> dealerLoanDistributionInfos = new ArrayList<>();

        for (LoanAccountDistributionInfo l : bucketAndOutstandingSortingLoanDistributionInfos) {

            for (DealerPerformance dealerPerformance : all) {
                boolean productGroupCheck = false;
                boolean dpdBucketCheck = false;

                productGroupCheck = dealerPerformance.getProductGroupEntityList().stream()
                        .anyMatch(p -> p.getCode().toUpperCase().equals(l.getSchemeCode().toUpperCase()));
                dpdBucketCheck = dealerPerformance.getDpdBucketEntityList().stream()
                        .anyMatch(d -> d.getBucketName().equals(l.getDpdBucket()));


                if (productGroupCheck && dpdBucketCheck) {
                    l.setDealerName(dealerPerformance.getEmployeeInfoEntity().getUser().getFirstName());
                    dealerLoanDistributionInfos.add(l);
                    all.remove(dealerPerformance);
                    all.add(dealerPerformance);
                    break;
                }
            }
        }

        List<AutoDistributionTemp> saveToDatabaseLoanDistributionInfo = new ArrayList<>();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String distributionExceptionLoanJson = gson.toJson(distributionExceptionLoan);

        for (LoanAccountDistributionInfo l : dealerLoanDistributionInfos) {
            AutoDistributionTemp autoDistributionTemp = new AutoDistributionTemp();
            autoDistributionTemp.setSupervisorName(l.getSupervisorName());
            autoDistributionTemp.setSupervisorPin(l.getSupervisorPin());
            autoDistributionTemp.setDealerName(l.getDealerName());
            autoDistributionTemp.setDealerPin(l.getDealerPin());
            autoDistributionTemp.setSamAccount(l.getSamAccount());
            autoDistributionTemp.setWriteOffAccount(l.getWriteOffAccount());
            autoDistributionTemp.setStatusDate(l.getStatusDate());
            autoDistributionTemp.setLoanAccountBasicInfo(l.getLoanAccountBasicInfo());
            autoDistributionTemp.setProductGroup(l.getProductGroup());
            autoDistributionTemp.setDpdBucket(l.getDpdBucket());
            autoDistributionTemp.setOutStanding(l.getOutStanding());
            autoDistributionTemp.setDistributionExceptionLoanJson(distributionExceptionLoanJson);
            saveToDatabaseLoanDistributionInfo.add(autoDistributionTemp);
        }
        autoDistributionTempRepository.deleteAll(autoDistributionTempRepository.findAll());
        autoDistributionTempRepository.saveAll(saveToDatabaseLoanDistributionInfo);

        return "redirect:/collection/autodistribution/list";
    }

    @PostMapping(value = "/sam")
    public String saveToSam(Model model) {
        LocalDate today = LocalDate.now();

        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        SamDistributionRuleInfo firstByOrderByIdDesc = samDistributionRuleRepository.findFirstByOrderByIdDesc();

        List<LoanAccountDistributionInfo> byCreatedDateLessThanAndSchemaCodeInAndDpdBucketIn =
                loanAccountDistributionRepository.findByCreatedDateLessThanAndSchemeCodeInAndDpdBucketIn(
                        startDate,
                        firstByOrderByIdDesc.getProductTypeEntityList().stream().map(
                                p -> p.getCode().toUpperCase()).collect(Collectors.toList()),
                        firstByOrderByIdDesc.getDpdBucketEntities().stream().map(
                                d -> d.getBucketName()).collect(Collectors.toList())
                );

        List<AutoDistributionLoanSamTemp> autoDistributionLoanSamTemps = new ArrayList<>();
        for (LoanAccountDistributionInfo l : byCreatedDateLessThanAndSchemaCodeInAndDpdBucketIn) {
            AutoDistributionLoanSamTemp autoDistributionTemp = new AutoDistributionLoanSamTemp();
            autoDistributionTemp.setSupervisorName(l.getSupervisorName());
            autoDistributionTemp.setSupervisorPin(l.getSupervisorPin());
            autoDistributionTemp.setDealerName(l.getDealerName());
            autoDistributionTemp.setDealerPin(l.getDealerPin());
            autoDistributionTemp.setSamAccount(l.getSamAccount());
            autoDistributionTemp.setWriteOffAccount(l.getWriteOffAccount());
            autoDistributionTemp.setStatusDate(l.getStatusDate());
            autoDistributionTemp.setLoanAccountBasicInfo(l.getLoanAccountBasicInfo());
            autoDistributionTemp.setProductGroup(l.getProductGroup());
            autoDistributionTemp.setDpdBucket(l.getDpdBucket());
            autoDistributionTemp.setOutStanding(l.getOutStanding());
            autoDistributionLoanSamTemps.add(autoDistributionTemp);
        }
        autoDistributionLoanSamTempRepository.deleteAll(autoDistributionLoanSamTempRepository.findAll());
        autoDistributionLoanSamTempRepository.saveAll(autoDistributionLoanSamTemps);
        return "redirect:/collection/autodistribution/samlist";
    }

    @PostMapping(value = "/postinterim")
    public String saveLoanPostInterim(Model model) {
        LocalDate today = LocalDate.now();

        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<DPDBucketEntity> dpdBucketEntities = dpdBucketService.getAll();

        List<DPDBucketEntity> x = dpdBucketEntities.stream().filter(d -> {
            char c = d.getBucketName().charAt(0);
            return (!(c >= 'A' && c <= 'Z'));
        }).collect(Collectors.toList());

        dpdBucketEntities.removeAll(x);
        dpdBucketEntities.sort(Comparator.comparing(DPDBucketEntity::getBucketName));
        x.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getBucketName())));
        dpdBucketEntities.addAll(x);

        PostPeopleLoan distributionExceptionLoan = postPeopleLoanRepository.findFirstByOrderByIdDesc();

        List<String> samIngoreList = new ArrayList<>();
        List<String> writeOffIgnoreList = new ArrayList<>();
        List<String> productGroupList = distributionExceptionLoan.getProductGroupEntityList()
                .stream().map(p -> p.getCode().toUpperCase()).collect(Collectors.toList());

        boolean samIgnore = distributionExceptionLoan.getSamIgnore().equals("Yes") ? samIngoreList.add("1") : samIngoreList.add("2");
        boolean writeOffIngore = distributionExceptionLoan.getWriteOffIgnore().equals("Yes") ? writeOffIgnoreList.add("1") : writeOffIgnoreList.add("2");
        if (productGroupList.size() == 0) productGroupList.add("All Product");

        List<LoanAccountDistributionInfo> loanAccountDistributionInfos =
                loanAccountDistributionRepository
                        .findByCreatedDateIsBetweenAndDealerNameAndSupervisorNameAndSamAccountNotInAndWriteOffAccountNotInAndSchemeCodeNotIn(
                                startDate, new Date(), "0", "0", samIngoreList, writeOffIgnoreList, productGroupList
                        );


        Set<String> productTypeEntities = new HashSet<>();
        List<LoanAccountDistributionInfo> uniqueLoanAccountDistributionInfo = new ArrayList<>();

        loanAccountDistributionInfos.forEach(loanAccountDistributionInfo -> {
            boolean contains = uniqueLoanAccountDistributionInfo.contains(loanAccountDistributionInfo);
            productTypeEntities.add(loanAccountDistributionInfo.getSchemeCode());
            if (!contains) {
                uniqueLoanAccountDistributionInfo.add(loanAccountDistributionInfo);
            }
        });

        List<LoanAccountDistributionInfo> bucketAndOutstandingSortingLoanDistributionInfos = new ArrayList<>();

        for (String s : productTypeEntities) {
            List<LoanAccountDistributionInfo> collect = uniqueLoanAccountDistributionInfo
                    .stream()
                    .filter(f -> f.getSchemeCode().equals(s)).collect(Collectors.toList());

            for (DPDBucketEntity d : dpdBucketEntities) {
                String bucketName = d.getBucketName();
                List<LoanAccountDistributionInfo> list = collect
                        .stream()
                        .filter(f -> f.getDpdBucket().equals(bucketName))
                        .sorted((o1, o2) -> Double.compare(Double.parseDouble(o2.getOutStanding()),
                                Double.parseDouble(o1.getOutStanding()))).collect(Collectors.toList());

                bucketAndOutstandingSortingLoanDistributionInfos.addAll(list);
            }

        }

        List<DealerPerformance> all = dealerPerformanceRepository.findAll();
        for (DealerPerformance d : all) {

            // NEED TO ADJUST CODE HERE

            PeopleAllocationLogicInfo byDealerId = peopleAllocationLogicService.getByDealerIdAndDistributionType(d.getEmployeeInfoEntity(), "Loan", "Interim");
            if (byDealerId != null) {
                if (byDealerId.getUnit().equals("Loan")) {
                    d.setProductGroupEntityList(byDealerId.getProductTypeEntityLoan());
                    d.setProductTypeEntityList(byDealerId.getProductTypeEntity());
                    d.setDpdBucketEntityList(byDealerId.getDpdBucketEntity());
                }
            }


        }

        all.sort(Comparator.comparingInt(DealerPerformance::getOverAllKpi).reversed());
        List<LoanAccountDistributionInfo> dealerLoanDistributionInfos = new ArrayList<>();

        for (LoanAccountDistributionInfo l : bucketAndOutstandingSortingLoanDistributionInfos) {
            for (DealerPerformance dealerPerformance : all) {
                boolean productGroupCheck = false;
                boolean dpdBucketCheck = false;

                productGroupCheck = dealerPerformance.getProductGroupEntityList().stream()
                        .anyMatch(p -> p.getCode().toUpperCase().equals(l.getSchemeCode().toUpperCase()));
                dpdBucketCheck = dealerPerformance.getDpdBucketEntityList().stream()
                        .anyMatch(d -> d.getBucketName().equals(l.getDpdBucket()));


                if (productGroupCheck && dpdBucketCheck) {
                    l.setDealerName(dealerPerformance.getEmployeeInfoEntity().getUser().getFirstName());
                    dealerLoanDistributionInfos.add(l);
                    all.remove(dealerPerformance);
                    all.add(dealerPerformance);
                    break;
                }
            }
        }

        List<PostInterimLoanDistributionTemp> saveToDatabaseLoanDistributionInfo = new ArrayList<>();

        for (LoanAccountDistributionInfo l : dealerLoanDistributionInfos) {
            PostInterimLoanDistributionTemp autoDistributionTemp = new PostInterimLoanDistributionTemp();
            autoDistributionTemp.setSupervisorName(l.getSupervisorName());
            autoDistributionTemp.setSupervisorPin(l.getSupervisorPin());
            autoDistributionTemp.setDealerName(l.getDealerName());
            autoDistributionTemp.setDealerPin(l.getDealerPin());
            autoDistributionTemp.setSamAccount(l.getSamAccount());
            autoDistributionTemp.setWriteOffAccount(l.getWriteOffAccount());
            autoDistributionTemp.setStatusDate(l.getStatusDate());
            autoDistributionTemp.setLoanAccountBasicInfo(l.getLoanAccountBasicInfo());
            autoDistributionTemp.setProductGroup(l.getProductGroup());
            autoDistributionTemp.setDpdBucket(l.getDpdBucket());
            autoDistributionTemp.setOutStanding(l.getOutStanding());
            saveToDatabaseLoanDistributionInfo.add(autoDistributionTemp);
        }
        postInterimLoanDistributionTempRepository.deleteAll(postInterimLoanDistributionTempRepository.findAll());
        postInterimLoanDistributionTempRepository.saveAll(saveToDatabaseLoanDistributionInfo);

        return "redirect:/collection/autodistribution/postinterimlist";
    }

    @GetMapping(value = "/postinterimlist")
    public String getPostInterimList(Model model) {

        List<PostInterimLoanDistributionTemp> all = postInterimLoanDistributionTempRepository.findAllByOrderByIdDesc();
        List<PostInterimLoanDistributionTemp> autoDistributionTemps = new ArrayList<>();

        for (int i = all.size() - 1; i >= 0; i--) {
            PostInterimLoanDistributionTemp postInterimLoanDistributionTemp = all.get(i);
            boolean check = autoDistributionTemps
                    .stream().anyMatch(a -> a.getLoanAccountBasicInfo().getId().equals(postInterimLoanDistributionTemp.getLoanAccountBasicInfo().getId()));
            if (!check)
                autoDistributionTemps.add(postInterimLoanDistributionTemp);
        }
        model.addAttribute("tempList", autoDistributionTemps);
        return "collection/automaticDistribution/postinterimlist";
    }

    @GetMapping(value = "/samlist")
    public String getSamList(Model model) {

        List<AutoDistributionLoanSamTemp> all = autoDistributionLoanSamTempRepository.findAllByOrderByIdDesc();
        List<AutoDistributionLoanSamTemp> autoDistributionTemps = new ArrayList<>();
        List<AutoDistributionSamViewModel> autoDistributionSamViewModelList = new ArrayList<AutoDistributionSamViewModel>();

        for (int i = all.size() - 1; i >= 0; i--) {
            AutoDistributionLoanSamTemp autoDistributionLoanSamTemp = all.get(i);
            boolean check = autoDistributionTemps
                    .stream().anyMatch(a -> a.getLoanAccountBasicInfo().getId().equals(autoDistributionLoanSamTemp.getLoanAccountBasicInfo().getId()));
            if (!check)
                autoDistributionTemps.add(autoDistributionLoanSamTemp);

            AutoDistributionSamViewModel autoDistributionSamViewModel = new AutoDistributionSamViewModel();
            autoDistributionSamViewModel.setDpdBucket(autoDistributionLoanSamTemp.getDpdBucket());
            autoDistributionSamViewModel.setAccountNo(autoDistributionLoanSamTemp.getLoanAccountBasicInfo().getAccountNo());
            autoDistributionSamViewModel.setOutstandingAmount(autoDistributionLoanSamTemp.getOutStanding());
            autoDistributionSamViewModelList.add(autoDistributionSamViewModel);
        }
        Gson gson = new Gson();

        model.addAttribute("tempList", autoDistributionSamViewModelList);
        model.addAttribute("tempListJson", gson.toJson(autoDistributionSamViewModelList));
        return "collection/automaticDistribution/loansamlist";
    }

    @GetMapping(value = "/list")
    public String getList(Model model) {
        List<AutoDistributionTemp> all = autoDistributionTempRepository.findAllByOrderByIdDesc();
        List<AutoDistributionTemp> autoDistributionTemps = new ArrayList<>();
        List<AutoDistributionLoanViewModel> autoDistributionLoanViewModelList = new ArrayList<AutoDistributionLoanViewModel>();
        Gson gson = new Gson();
        for (int i = 0; i < all.size(); i++) {
            AutoDistributionTemp autoDistributionTemp = all.get(i);
            boolean check = autoDistributionTemps
                    .stream().anyMatch(a -> a.getLoanAccountBasicInfo().getId().equals(autoDistributionTemp.getLoanAccountBasicInfo().getId()));
            if (!check)
                autoDistributionTemps.add(autoDistributionTemp);
            AutoDistributionLoanViewModel autoDistributionLoanViewModel = new AutoDistributionLoanViewModel();
            autoDistributionLoanViewModel.setAccountNo(all.get(i).getLoanAccountBasicInfo().getAccountNo());
            autoDistributionLoanViewModel.setDealerName(all.get(i).getDealerName());
            autoDistributionLoanViewModel.setOutstandingAmount(all.get(i).getOutStanding());
            autoDistributionLoanViewModel.setDpdBucket(all.get(i).getDpdBucket());
            autoDistributionLoanViewModelList.add(autoDistributionLoanViewModel);
        }
        if (autoDistributionTemps.size() > 0) {
            DistributionExceptionLoan attributeValue = gson.fromJson(autoDistributionTemps.get(0).getDistributionExceptionLoanJson(), DistributionExceptionLoan.class);
            model.addAttribute("distributionException", attributeValue);
        }
        model.addAttribute("tempList", autoDistributionTemps);
        model.addAttribute("tempListJson", gson.toJson(autoDistributionLoanViewModelList));
        return "collection/automaticDistribution/list";
    }

    @ResponseBody
    @PostMapping("/confirmSamDist")
    public boolean confirmSamDistribution(@Valid @RequestBody LoanApiPayload loanApiPayload) {
        boolean success = false;
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<AutoDistributionLoanSamTemp> autoDistributionLoanSamTempList = new ArrayList<AutoDistributionLoanSamTemp>();
        List<SamAccountHandoverInfo> samAccountHandoverInfoList = new ArrayList<SamAccountHandoverInfo>();

        for (String s : loanApiPayload.getList()) {
            LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(s);

            autoDistributionLoanSamTempList.addAll(autoDistributionLoanSamTempRepository.findByLoanAccountBasicInfo(loanAccountBasicInfo));

            LoanAccountDistributionInfo distributionInfo = loanAccountDistributionRepository.findFirstByCreatedDateLessThanAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), loanAccountBasicInfo, "1");
            if (distributionInfo != null) {
                distributionInfo.setLatest("0");
                if (distributionInfo.getMonitoringStatus().toUpperCase().equals("DUAL")) {
                    LoanAgencyDistributionInfo loanAgencyDistributionInfo = loanAgencyDistributionRepository.findFirstByCreatedDateLessThanAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), loanAccountBasicInfo, "1");
                    loanAgencyDistributionInfo.setLatest("0");
                    loanAgencyDistributionRepository.save(loanAgencyDistributionInfo);
                }
                loanAccountDistributionRepository.save(distributionInfo);

                SamAccountHandoverInfo samAccountHandoverInfo = new SamAccountHandoverInfo();

                samAccountHandoverInfo.setSamAccount("1");
                samAccountHandoverInfo.setProductGroup(distributionInfo.getProductGroup());
                samAccountHandoverInfo.setSchemaCode(distributionInfo.getSchemeCode());
                samAccountHandoverInfo.setOutstandingAmount(distributionInfo.getOutStanding());
                samAccountHandoverInfo.setDpdBucket(distributionInfo.getDpdBucket());
                samAccountHandoverInfo.setSamRecievedDate(new Date());
                samAccountHandoverInfo.setAccountName(loanAccountBasicInfo.getAccountName());
                samAccountHandoverInfo.setLoanAccountNo(loanAccountBasicInfo.getAccountNo());
                samAccountHandoverInfo.setCustomerId(loanAccountBasicInfo.getCustomer().getCustomerId());
                samAccountHandoverInfo.setCreatedDate(new Date());
                samAccountHandoverInfo.setCreatedBy(userPrincipal.getUsername());
                samAccountHandoverInfo.setLatest("1");
                samAccountHandoverInfo.setProductUnit("Loan");
                samAccountHandoverInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);

                samAccountHandoverInfoList.add(samAccountHandoverInfo);
            } else {
                LoanAgencyDistributionInfo loanAgencyDistributionInfo = loanAgencyDistributionRepository.findFirstByCreatedDateLessThanAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), loanAccountBasicInfo, "1");
                ;
                if (loanAgencyDistributionInfo != null) {
                    loanAgencyDistributionInfo.setLatest("0");
                    loanAgencyDistributionRepository.save(loanAgencyDistributionInfo);
                }


                LoanAccountDistributionInfo loanAccountDistributionInfo = loanAccountDistributionRepository.findFirstByCreatedDateLessThanAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), loanAccountBasicInfo, "1");


                SamAccountHandoverInfo samAccountHandoverInfo = new SamAccountHandoverInfo();

                samAccountHandoverInfo.setSamAccount("1");
                samAccountHandoverInfo.setProductGroup(loanAccountDistributionInfo.getProductGroup());
                samAccountHandoverInfo.setSchemaCode(loanAccountDistributionInfo.getSchemeCode());
                samAccountHandoverInfo.setOutstandingAmount(loanAccountDistributionInfo.getOutStanding());
                samAccountHandoverInfo.setDpdBucket(loanAccountDistributionInfo.getDpdBucket());
                samAccountHandoverInfo.setSamRecievedDate(new Date());
                samAccountHandoverInfo.setAccountName(loanAccountBasicInfo.getAccountName());
                samAccountHandoverInfo.setLoanAccountNo(loanAccountBasicInfo.getAccountNo());
                samAccountHandoverInfo.setCustomerId(loanAccountBasicInfo.getCustomer().getCustomerId());
                samAccountHandoverInfo.setCreatedDate(new Date());
                samAccountHandoverInfo.setCreatedBy(userPrincipal.getUsername());
                samAccountHandoverInfo.setLatest("1");
                samAccountHandoverInfo.setProductUnit("Loan");
                samAccountHandoverInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);

                samAccountHandoverInfoList.add(samAccountHandoverInfo);
            }
        }
        try {
            samAccountHandoverRepository.saveAll(samAccountHandoverInfoList);
            autoDistributionLoanSamTempRepository.deleteAll(autoDistributionLoanSamTempList);
            success = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return success;
    }

    @ResponseBody
    @PostMapping("/confirmLoanDist")
    public boolean confirmLoanDistribution(@Valid @RequestBody LoanApiPayload loanApiPayload) {
        boolean success = false;
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<AutoDistributionTemp> autoDistributionTempList = new ArrayList<AutoDistributionTemp>();
        List<LoanAccountDistributionInfo> loanAccountDistributionInfoList = new ArrayList<LoanAccountDistributionInfo>();
        for (String s : loanApiPayload.getList()) {
            LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(s);
            AutoDistributionTemp autoDistributionTemp = autoDistributionTempRepository.findByLoanAccountBasicInfo(loanAccountBasicInfo);
            autoDistributionTempList.add(autoDistributionTemp);
            LoanAccountDistributionInfo loanAccountDistributionInfo = new LoanAccountDistributionInfo();
            loanAccountDistributionInfo.setId(null);
            loanAccountDistributionInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
            loanAccountDistributionInfo.setDealerPin(autoDistributionTemp.getDealerPin());
            loanAccountDistributionInfo.setSamAccount(autoDistributionTemp.getSamAccount());
            loanAccountDistributionInfo.setWriteOffAccount(autoDistributionTemp.getWriteOffAccount());
            loanAccountDistributionInfo.setOutStanding(autoDistributionTemp.getOutStanding());
            loanAccountDistributionInfo.setDpdBucket(autoDistributionTemp.getDpdBucket());
            loanAccountDistributionInfo.setDpd(Double.parseDouble(autoDistributionTemp.getDpd() != null ? autoDistributionTemp.getDpd() : "0"));
            loanAccountDistributionInfo.setProductGroup(autoDistributionTemp.getProductGroup());
            loanAccountDistributionInfo.setLatest("1");
            loanAccountDistributionInfo.setCreatedDate(new Date());
            loanAccountDistributionInfo.setCreatedBy(userPrincipal.getUsername());
            LoanAccountDistributionInfo prevLoanAccountDistributionInfo = loanAccountDistributionRepository.findFirstByLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(loanAccountBasicInfo, "1");
            if (prevLoanAccountDistributionInfo != null) {
                prevLoanAccountDistributionInfo.setLatest("0");
                loanAccountDistributionInfoList.add(prevLoanAccountDistributionInfo);
            }
            loanAccountDistributionInfoList.add(loanAccountDistributionInfo);

        }

        try {
            autoDistributionTempRepository.deleteAll(autoDistributionTempList);
            loanAccountDistributionRepository.saveAll(loanAccountDistributionInfoList);
            success = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return success;
    }


    public Date getStartDate() {
        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        return Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getEndDate() {
        LocalDate today = LocalDate.now();
        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
