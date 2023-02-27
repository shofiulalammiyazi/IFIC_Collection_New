package com.unisoft.collection.automaticDistribution.autoDistribution;
/*
Created by   Islam at 9/7/2019
*/

import com.google.gson.Gson;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicService;
import com.unisoft.collection.automaticDistribution.distributionExceptionCard.DistributionException;
import com.unisoft.collection.automaticDistribution.distributionExceptionCard.DistributionExceptionRepository;
import com.unisoft.collection.automaticDistribution.postInterim.PostInterimCardDistributionTemp;
import com.unisoft.collection.automaticDistribution.postInterim.PostInterimCardDistributionTempRepository;
import com.unisoft.collection.automaticDistribution.postInterim.PostInterimCondition;
import com.unisoft.collection.automaticDistribution.postInterim.PostInterimConditionRepository;
import com.unisoft.collection.automaticDistribution.postInterimException.PostInterimException;
import com.unisoft.collection.automaticDistribution.postInterimException.PostInterimExceptionRepository;
import com.unisoft.collection.automaticDistribution.samDistributionRuleCard.SamRuleCardInfo;
import com.unisoft.collection.automaticDistribution.samDistributionRuleCard.SamRuleCardRepository;
import com.unisoft.collection.distribution.cardApi.CardApiPayload;
import com.unisoft.collection.distribution.samAccountHandover.AutoDistributionCardSamTemp;
import com.unisoft.collection.distribution.samAccountHandover.AutoDistributionCardSamTempRepository;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverInfo;
import com.unisoft.collection.distribution.samAccountHandover.SamAccountHandoverRepository;
import com.unisoft.collection.distribution.unallocated.UnallocatedCardList;
import com.unisoft.collection.distribution.unallocated.UnallocatedCardListRepository;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.dealerPerformance.DealerPerformance;
import com.unisoft.collection.settings.dealerPerformance.DealerPerformanceRepository;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.AutoDistributionCardTemp;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.AutoDistributionCardTempRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionRepository;
import com.unisoft.user.UserPrincipal;
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

@Controller
@AllArgsConstructor
@RequestMapping(value = "/collection/card/autodistribution")
public class AutoDistributionCardController {

    private CardAccountDistributionRepository cardAccountDistributionRepository;

    private DistributionExceptionRepository distributionExceptionRepository;

    private DealerPerformanceRepository dealerPerformanceRepository;

    private PeopleAllocationLogicService peopleAllocationLogicService;

    private AgeCodeService ageCodeService;

    private AutoDistributionCardTempRepository autoDistributionCardTempRepository;

    private SamRuleCardRepository samRuleCardRepository;

    private AutoDistributionCardSamTempRepository autoDistributionCardSamTempRepository;

    private PostInterimExceptionRepository postInterimExceptionRepository;

    private PostInterimConditionRepository postInterimConditionRepository;

    private PostInterimCardDistributionTempRepository postInterimCardDistributionTempRepository;

    private UnallocatedCardListRepository unallocatedCardListRepository;

    private SamAccountHandoverRepository samAccountHandoverRepository;

    private CardAgencyDistributionRepository cardAgencyDistributionRepository;

    private CardAccountBasicService cardAccountBasicService;

    List<AgeCodeEntity> getSortedAgeCodeEntities() {
        List<AgeCodeEntity> ageCodeEntities = ageCodeService.getAll();

        List<AgeCodeEntity> x = ageCodeEntities.stream().filter(d -> {
            char c = d.getName().charAt(0);
            return (!(c >= 'A' && c <= 'Z'));
        }).collect(Collectors.toList());

        ageCodeEntities.removeAll(x);
        ageCodeEntities.sort(Comparator.comparing(AgeCodeEntity::getName));
        x.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getName())));
        ageCodeEntities.addAll(x);
        return ageCodeEntities;
    }


    @PostMapping
    public String getAccountList() {
        LocalDate today = LocalDate.now();

        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<AgeCodeEntity> ageCodeEntities = getSortedAgeCodeEntities();

        DistributionException distributionExceptionCard = distributionExceptionRepository.findFirstByOrderByIdDesc();

        List<String> samIngoreList = new ArrayList<>();
        List<String> writeOffIgnoreList = new ArrayList<>();
        List<String> productGroupList = distributionExceptionCard.getProductsList().stream().map(ProductTypeEntity::getCode).collect(Collectors.toList());
        ;
        List<String> vvipList = new ArrayList<>();
        List<String> secureCard = new ArrayList<>();

        boolean samIgnore = distributionExceptionCard.getSamIgnore().equals("Yes") ? samIngoreList.add("1") : samIngoreList.add("2");
        boolean writeOffIngore = distributionExceptionCard.getWriteOffIgnore().equals("Yes") ? writeOffIgnoreList.add("1") : writeOffIgnoreList.add("2");
        boolean securedCard = distributionExceptionCard.getSecureCard().equals("Yes") ? secureCard.add("SECURED CREDIT CARD") : secureCard.add("All Card");
        boolean vvip = distributionExceptionCard.getSecureCard().equals("Yes") ? vvipList.add("VVIP") : vvipList.add("All VVIP");
        if (productGroupList.size() == 0) productGroupList.add("All Product");


//        List<CardAccountDistributionInfo> previousCardDistributionInfos = cardAccountDistributionRepository
//                .findByCreatedDateLessThanAndSamAccountNotInAndWriteOffAccountNotInAndProductGroupNotInAndVvipNotInAndSecureCardNotIn(
//                startDate, samIngoreList, writeOffIgnoreList,
//                        productGroupList,
//                        vvipList,
//                        secureCard
//        );

        List<CardAccountDistributionInfo> previousCardDistributionInfos = new ArrayList<>();

        List<UnallocatedCardList> unallocatedCardList = unallocatedCardListRepository.findByCreatedDateLessThanAndSamAccountNotInAndWriteOffAccountNotInAndProductGroupNotInAndVvipNotInAndSecureCardNotIn(
                startDate, samIngoreList, writeOffIgnoreList,
                productGroupList,
                vvipList,
                secureCard
        );

        for (UnallocatedCardList c : unallocatedCardList) {
            CardAccountDistributionInfo cardAccountDistributionInfo = new CardAccountDistributionInfo();
            cardAccountDistributionInfo.setCardAccountBasicInfo(c.getCardAccountBasicInfo());
            cardAccountDistributionInfo.setAgeCode(c.getAgeCode());
            cardAccountDistributionInfo.setBillingCycle(c.getBillingCycle());
            cardAccountDistributionInfo.setOutstandingAmount(c.getOutstandingAmount());
            cardAccountDistributionInfo.setDealerPin("0");
            cardAccountDistributionInfo.setDealerName("0");
            cardAccountDistributionInfo.setSupervisorPin("0");
            cardAccountDistributionInfo.setSupervisorName("0");
            cardAccountDistributionInfo.setSamAccount("0");
            cardAccountDistributionInfo.setWriteOffAccount("0");
            cardAccountDistributionInfo.setVvip(c.getVvip());
            cardAccountDistributionInfo.setSecureCard(c.getSecureCard());
            cardAccountDistributionInfo.setCreatedDate(new Date());
            cardAccountDistributionInfo.setProductGroup(c.getProductGroup());
            cardAccountDistributionInfo.setPlasticCd(c.getPlasticCd());
            previousCardDistributionInfos.add(cardAccountDistributionInfo);
        }

        previousCardDistributionInfos.sort((o1, o2) -> {
            if (o1.getCreatedDate() == null || o2.getCreatedDate() == null)
                return 0;
            return o1.getCreatedDate().compareTo(o2.getCreatedDate());
        });

        List<CardAccountDistributionInfo> uniquePreviousCarddDistributionInfos = new ArrayList<>();
        Set<String> productList = new HashSet<>();

        for (int i = previousCardDistributionInfos.size() - 1; i >= 0; i--) {
            CardAccountDistributionInfo cardAccountDistributionInfo = previousCardDistributionInfos.get(i);
            boolean check = uniquePreviousCarddDistributionInfos.stream().
                    anyMatch(f -> f.getCardAccountBasicInfo().getId().equals(cardAccountDistributionInfo.getCardAccountBasicInfo().getId()));
            if (!check) {
                String accountProductGroup = cardAccountDistributionInfo.getProductGroup().toUpperCase();
                String accountDpdBucket = cardAccountDistributionInfo.getAgeCode();
                String plasticCd = cardAccountDistributionInfo.getPlasticCd();
                String billingCycle = cardAccountDistributionInfo.getBillingCycle();

                boolean b = distributionExceptionCard.getProductGroupAgeCodes()
                        .stream()
                        .anyMatch(p -> {
                            String productGroup = p.getProductGroup().toUpperCase();
                            String ageCode = p.getAgeCode();
                            return productGroup.equals(accountProductGroup)
                                    && ageCode.equals(accountDpdBucket);
                        });
                boolean billingCycleCheck = Arrays.asList(distributionExceptionCard.getBillingCycle()).contains(billingCycle);
                boolean plasticCdCheck = Arrays.asList(distributionExceptionCard.getPlasticCd()).contains(plasticCd);

                if (!b && !billingCycleCheck && !plasticCdCheck) {
                    productList.add(cardAccountDistributionInfo.getProductGroup());
                    uniquePreviousCarddDistributionInfos.add(cardAccountDistributionInfo);

                }

            }
        }

        List<CardAccountDistributionInfo> bucketAndOutstandingSortingCardDistributionInfos = new ArrayList<>();

        for (String s : productList) {
            List<CardAccountDistributionInfo> collect = uniquePreviousCarddDistributionInfos
                    .stream()
                    .filter(f -> f.getProductGroup().equals(s)).collect(Collectors.toList());

            for (AgeCodeEntity d : ageCodeEntities) {
                String ageCodeName = d.getName();
                List<CardAccountDistributionInfo> list = collect
                        .stream()
                        .filter(f -> {
                            String ageCode = f.getAgeCode();
                            return ageCode.equals(ageCodeName);
                        })
                        .sorted((o1, o2) -> Double.compare(Double.parseDouble(o2.getOutstandingAmount()),
                                Double.parseDouble(o1.getOutstandingAmount()))).collect(Collectors.toList());

                bucketAndOutstandingSortingCardDistributionInfos.addAll(list);
            }

        }

        List<DealerPerformance> all = dealerPerformanceRepository.findAllByEmployeeInfoEntityNotIn(distributionExceptionCard.getEmployeeInfos());
        for (DealerPerformance d : all) {

            // NEED TO ADJUST CODE HERE

            PeopleAllocationLogicInfo byDealerId = peopleAllocationLogicService.getByDealerId(d.getEmployeeInfoEntity(), "Card");
            if (byDealerId != null) {
                if (byDealerId.getUnit().equals("Card")) {
                    d.setProductTypeEntityList(byDealerId.getProductTypeEntity());
                    d.setAgeCodeEntityList(byDealerId.getAgeCodeEntity());
                }
            }
        }

        all.sort(Comparator.comparingInt(DealerPerformance::getOverAllKpi).reversed());

        LinkedList<DealerPerformance> dealerPerformances = new LinkedList<>(all);

        List<CardAccountDistributionInfo> dealerCardDistributionInfos = new ArrayList<>();

        for (CardAccountDistributionInfo l : bucketAndOutstandingSortingCardDistributionInfos) {

            for (DealerPerformance dealerPerformance : dealerPerformances) {
                boolean productGroupCheck = false;
                boolean dpdBucketCheck = false;

                productGroupCheck = dealerPerformance.getProductTypeEntityList().stream()
                        .anyMatch(p -> p.getCode().toUpperCase().equals(l.getProductGroup().toUpperCase()));
                dpdBucketCheck = dealerPerformance.getAgeCodeEntityList().stream()
                        .anyMatch(d -> d.getName().equals(l.getAgeCode()));

                if (productGroupCheck && dpdBucketCheck) {
                    l.setDealerName(dealerPerformance.getEmployeeInfoEntity().getUser().getFirstName());
                    dealerCardDistributionInfos.add(l);
                    dealerPerformances.remove(dealerPerformance);
                    dealerPerformances.add(dealerPerformance);
                }
            }


        }

        List<AutoDistributionCardTemp> saveToDatabaseLoanDistributionInfo = new ArrayList<>();

        for (CardAccountDistributionInfo l : dealerCardDistributionInfos) {
            AutoDistributionCardTemp autoDistributionTemp = new AutoDistributionCardTemp();
            autoDistributionTemp.setSupervisorName(l.getSupervisorName());
            autoDistributionTemp.setSupervisorPin(l.getSupervisorPin());
            autoDistributionTemp.setDealerName(l.getDealerName());
            autoDistributionTemp.setDealerPin(l.getDealerPin());
            autoDistributionTemp.setSamAccount(l.getSamAccount());
            autoDistributionTemp.setWriteOffAccount(l.getWriteOffAccount());
            autoDistributionTemp.setStatusDate(l.getStatusDate());
            autoDistributionTemp.setCardAccountBasicInfo(l.getCardAccountBasicInfo());
            autoDistributionTemp.setProductGroup(l.getProductGroup());
            autoDistributionTemp.setAgeCode(l.getAgeCode());
            autoDistributionTemp.setOutstandingAmount(l.getOutstandingAmount());
            autoDistributionTemp.setVvip(l.getVvip());
            autoDistributionTemp.setBillingCycle(l.getBillingCycle());
            autoDistributionTemp.setPlasticCd(l.getPlasticCd());
            autoDistributionTemp.setSecureCard(l.getSecureCard());
            autoDistributionTemp.setCustomerName(l.getCustomerName());
            autoDistributionTemp.setStatusDate(l.getStatusDate());
            saveToDatabaseLoanDistributionInfo.add(autoDistributionTemp);
        }
        autoDistributionCardTempRepository.deleteAll(autoDistributionCardTempRepository.findAll());
        autoDistributionCardTempRepository.saveAll(saveToDatabaseLoanDistributionInfo);
        return "redirect:/collection/card/autodistribution/list";
    }

    @PostMapping(value = "/postinterim")
    public String autoDistributePostInterim(Model model) {

        LocalDate today = LocalDate.now();

        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        PostInterimException postInterimException = postInterimExceptionRepository.findFirstByOrderByIdDesc();
        PostInterimCondition postInterimCondition = postInterimConditionRepository.findFirstByOrderByIdDesc();

        List<String> samIngoreList = new ArrayList<>();
        List<String> writeOffIgnoreList = new ArrayList<>();
        List<String> plasticCd = Arrays.asList(postInterimException.getAgeCode());
        List<String> vvipList = new ArrayList<>();
        List<String> secureCard = new ArrayList<>();
        List<String> customerName = postInterimException.getCustomerBasicInfoEntityList().stream().map(CustomerBasicInfoEntity::getCustomerName).collect(Collectors.toList());

        boolean samIgnore = postInterimException.getSamIgnore().equals("Yes") ? samIngoreList.add("1") : samIngoreList.add("2");
        boolean writeOffIngore = postInterimException.getWriteOffIgnore().equals("Yes") ? writeOffIgnoreList.add("1") : writeOffIgnoreList.add("2");
        boolean securedCard = postInterimException.getSecureCard().equals("Yes") ? secureCard.add("SECURED CREDIT CARD") : secureCard.add("All Card");
        boolean vvip = postInterimException.getSecureCard().equals("Yes") ? vvipList.add("VVIP") : vvipList.add("All VVIP");
        if (plasticCd.size() == 0) plasticCd.add("-1");
        if (customerName.size() == 0) customerName.add("Ayesha Takia");

        List<CardAccountDistributionInfo> cardAccountDistributionInfoList = cardAccountDistributionRepository
                .findByCreatedDateIsBetweenAndDealerNameAndSupervisorNameAndAgeCodeInAndBillingCycleInAndProductGroupInAndSamAccountNotInAndWriteOffAccountNotInAndVvipNotInAndSecureCardNotInAndCustomerNameNotIn
                        (startDate, new Date(),
                                "0", "0", postInterimCondition.getAgeCodeEntities()
                                        .stream()
                                        .map(AgeCodeEntity::getName).collect(Collectors.toList()),
                                Arrays.asList(postInterimCondition.getBillingCycle()),
                                postInterimCondition.getProductGroupEntities()
                                        .stream()
                                        .map(ProductTypeEntity::getCode).collect(Collectors.toList()),
                                samIngoreList,
                                writeOffIgnoreList,
                                vvipList,
                                secureCard,
                                customerName);

        List<AgeCodeEntity> ageCodeEntities = getSortedAgeCodeEntities();

        Set<String> productTypeEntities = new HashSet<>();
        List<CardAccountDistributionInfo> uniqueCardAccountDistributionInfo = new ArrayList<>();

        cardAccountDistributionInfoList.forEach(cardAccountDistributionInfo -> {
            boolean contains = uniqueCardAccountDistributionInfo.contains(cardAccountDistributionInfo);
            productTypeEntities.add(cardAccountDistributionInfo.getProductGroup());
            if (!contains) {
                uniqueCardAccountDistributionInfo.add(cardAccountDistributionInfo);
            }
        });

        List<CardAccountDistributionInfo> ageCodeAndOutstandingSortingCardDistributionInfos = new ArrayList<>();

        for (String s : productTypeEntities) {
            List<CardAccountDistributionInfo> collect = uniqueCardAccountDistributionInfo
                    .stream()
                    .filter(f -> f.getProductGroup().equals(s)).collect(Collectors.toList());

            for (AgeCodeEntity a : ageCodeEntities) {
                String ageCode = a.getName();
                List<CardAccountDistributionInfo> list = collect
                        .stream()
                        .filter(f -> f.getAgeCode().equals(ageCode))
                        .sorted((o1, o2) -> Double.compare(Double.parseDouble(o2.getOutstandingAmount()),
                                Double.parseDouble(o1.getOutstandingAmount()))).collect(Collectors.toList());

                ageCodeAndOutstandingSortingCardDistributionInfos.addAll(list);
            }

        }

        List<DealerPerformance> all = dealerPerformanceRepository.findAllByEmployeeInfoEntityNotIn(postInterimException.getEmployeeInfoEntities());
        for (DealerPerformance d : all) {

            // NEED TO ADJUST CODE HERE

            PeopleAllocationLogicInfo byDealerId = peopleAllocationLogicService.getByDealerIdAndDistributionType(d.getEmployeeInfoEntity(), "Card", "Interim");
            if (byDealerId != null) {
                d.setProductTypeEntityList(byDealerId.getProductTypeEntity());
                d.setAgeCodeEntityList(byDealerId.getAgeCodeEntity());
            }
        }

        all.sort(Comparator.comparingInt(DealerPerformance::getOverAllKpi).reversed());
        LinkedList<DealerPerformance> dealerPerformances = new LinkedList<>(all);

        List<CardAccountDistributionInfo> dealerCardDistributionInfos = new ArrayList<>();

        for (CardAccountDistributionInfo l : ageCodeAndOutstandingSortingCardDistributionInfos) {
            for (DealerPerformance dealerPerformance : dealerPerformances) {
                boolean productGroupCheck = false;
                boolean dpdBucketCheck = false;

                productGroupCheck = dealerPerformance.getProductTypeEntityList().stream()
                        .anyMatch(p -> p.getCode().toUpperCase().equals(l.getProductGroup().toUpperCase()));
                dpdBucketCheck = dealerPerformance.getAgeCodeEntityList().stream()
                        .anyMatch(d -> d.getName().equals(l.getAgeCode()));

                if (productGroupCheck && dpdBucketCheck) {
                    l.setDealerName(dealerPerformance.getEmployeeInfoEntity().getUser().getFirstName());
                    dealerCardDistributionInfos.add(l);
                    dealerPerformances.remove(dealerPerformance);
                    dealerPerformances.add(dealerPerformance);
                }
            }
        }


        List<PostInterimCardDistributionTemp> saveToDatabaseLoanDistributionInfo = new ArrayList<>();

        for (CardAccountDistributionInfo l : dealerCardDistributionInfos) {
            PostInterimCardDistributionTemp postInterimCardDistributionTemp = new PostInterimCardDistributionTemp(
                    l.getSupervisorName(),
                    l.getSupervisorPin(),
                    l.getDealerName(),
                    l.getDealerPin(),
                    l.getSamAccount(),
                    l.getWriteOffAccount(),
                    l.getStatusDate(),
                    l.getCardAccountBasicInfo(),
                    l.getProductGroup(),
                    l.getAgeCode(),
                    l.getOutstandingAmount(),
                    l.getVvip(),
                    l.getBillingCycle(),
                    l.getPlasticCd(),
                    l.getSecureCard(),
                    l.getCustomerName());
            saveToDatabaseLoanDistributionInfo.add(postInterimCardDistributionTemp);
        }

        postInterimCardDistributionTempRepository.deleteAll(postInterimCardDistributionTempRepository.findAll());
        postInterimCardDistributionTempRepository.saveAll(saveToDatabaseLoanDistributionInfo);
        return "redirect:/collection/card/autodistribution/postinterim";
    }

    @PostMapping(value = "/sam")
    public String autoDistributionSam(Model model) {

        LocalDate today = LocalDate.now();

        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        SamRuleCardInfo firstByOrderByIdDesc = samRuleCardRepository.findFirstByOrderByIdDesc();
        List<CardAccountDistributionInfo> byCreatedDateLessThanAndAgeCodeIn = cardAccountDistributionRepository.findByCreatedDateLessThanAndAgeCodeInAndLatest(startDate, firstByOrderByIdDesc.getAgeCodeEntityList().stream().map(a -> a.getName()).collect(Collectors.toList()), "1");
        List<AutoDistributionCardSamTemp> autoDistributionCardSamTemps = new ArrayList<>();

        for (CardAccountDistributionInfo l : byCreatedDateLessThanAndAgeCodeIn) {
            AutoDistributionCardSamTemp autoDistributionSamTemp = new AutoDistributionCardSamTemp(
                    l.getSupervisorName(),
                    l.getSupervisorPin(),
                    l.getDealerName(),
                    l.getDealerPin(),
                    l.getSamAccount(),
                    l.getWriteOffAccount(),
                    l.getStatusDate(),
                    l.getCardAccountBasicInfo(),
                    l.getProductGroup(),
                    l.getAgeCode(),
                    l.getOutstandingAmount(),
                    l.getVvip(),
                    l.getBillingCycle(),
                    l.getPlasticCd(),
                    l.getSecureCard(),
                    l.getCustomerName());
            autoDistributionCardSamTemps.add(autoDistributionSamTemp);
        }
        autoDistributionCardSamTempRepository.deleteAll(autoDistributionCardSamTempRepository.findAll());
        autoDistributionCardSamTempRepository.saveAll(autoDistributionCardSamTemps);
        return "redirect:/collection/card/autodistribution/samlist";
    }

    @GetMapping(value = "/samlist")
    public String getListSam(Model model) {
        List<AutoDistributionCardSamTemp> all = autoDistributionCardSamTempRepository.findAllByOrderByIdDesc();
        List<AutoDistributionCardSamTemp> autoDistributionCardSamTemps = new ArrayList<>();
        List<AutoDistributionSamViewModel> autoDistributionSamViewModelList = new ArrayList<AutoDistributionSamViewModel>();

        for (int i = all.size() - 1; i >= 0; i--) {
            AutoDistributionCardSamTemp autoDistributionCardTemp = all.get(i);
            boolean check = autoDistributionCardSamTemps
                    .stream().anyMatch(a -> a.getCardAccountBasicInfo().getId().equals(autoDistributionCardTemp.getCardAccountBasicInfo().getId()));
            if (!check)
                autoDistributionCardSamTemps.add(autoDistributionCardTemp);
            AutoDistributionSamViewModel autoDistributionSamViewModel = new AutoDistributionSamViewModel();
            autoDistributionSamViewModel.setAgeCode(autoDistributionCardTemp.getAgeCode());
            autoDistributionSamViewModel.setAccountNo(autoDistributionCardTemp.getCardAccountBasicInfo().getContractId());
            autoDistributionSamViewModel.setDealerName(autoDistributionCardTemp.getDealerPin());
            autoDistributionSamViewModelList.add(autoDistributionSamViewModel);
        }
        Gson gson = new Gson();
        model.addAttribute("tempList", autoDistributionSamViewModelList);
        model.addAttribute("tempListJson", gson.toJson(autoDistributionSamViewModelList));
        return "collection/automaticDistribution/cardsamlist";
    }

    @GetMapping(value = "/postinterim")
    public String getListPostInterim(Model model) {
        List<PostInterimCardDistributionTemp> all = postInterimCardDistributionTempRepository.findAllByOrderByIdDesc();
        List<PostInterimCardDistributionTemp> autoDistributionCardSamTemps = new ArrayList<>();

        for (int i = all.size() - 1; i >= 0; i--) {
            PostInterimCardDistributionTemp autoDistributionCardTemp = all.get(i);
            boolean check = autoDistributionCardSamTemps
                    .stream().anyMatch(a -> a.getCardAccountBasicInfo().getId().equals(autoDistributionCardTemp.getCardAccountBasicInfo().getId()));
            if (!check)
                autoDistributionCardSamTemps.add(autoDistributionCardTemp);
        }
        model.addAttribute("tempList", autoDistributionCardSamTemps);
        return "collection/automaticDistribution/samlist";
    }

    @GetMapping(value = "/list")
    public String getList(Model model) {
        List<AutoDistributionCardTemp> all = autoDistributionCardTempRepository.findAllByOrderByIdDesc();
        List<AutoDistributionCardTemp> autoDistributionCardTemps = new ArrayList<>();

        for (int i = all.size() - 1; i >= 0; i--) {
            AutoDistributionCardTemp autoDistributionCardTemp = all.get(i);
            boolean check = autoDistributionCardTemps
                    .stream().anyMatch(a -> a.getCardAccountBasicInfo().getId().equals(autoDistributionCardTemp.getCardAccountBasicInfo().getId()));
            if (!check)
                autoDistributionCardTemps.add(autoDistributionCardTemp);
        }

        model.addAttribute("tempList", autoDistributionCardTemps);
        return "collection/automaticDistribution/cardlist";
    }


    @ResponseBody
    @PostMapping(value = "/confirmCardSamDist")
    public boolean confirmSamDistribution(@Valid @RequestBody CardApiPayload cardApiPayload) {
        boolean success = false;
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<AutoDistributionCardSamTemp> autoDistributionCardSamTempList = new ArrayList<AutoDistributionCardSamTemp>();
        List<SamAccountHandoverInfo> samAccountHandoverInfoList = new ArrayList<SamAccountHandoverInfo>();

        for (String s : cardApiPayload.getList()) {
            CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicService.getByAccountNo(s);


            autoDistributionCardSamTempList.addAll(autoDistributionCardSamTempRepository.findByCardAccountBasicInfo(cardAccountBasicInfo));

            CardAccountDistributionInfo distributionInfo = cardAccountDistributionRepository.findFirstByCreatedDateLessThanAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), cardAccountBasicInfo, "1");
            if (distributionInfo != null) {
                distributionInfo.setLatest("0");
                cardAccountDistributionRepository.save(distributionInfo);

                if (distributionInfo.getMonitoringStatus().toUpperCase().equals("DUAL")) {
                    CardAgencyDistributionInfo cardAgencyDistributionInfo = cardAgencyDistributionRepository.findFirstByCreatedDateLessThanAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), cardAccountBasicInfo, "1");
                    cardAgencyDistributionInfo.setLatest("0");
                    cardAgencyDistributionRepository.save(cardAgencyDistributionInfo);
                }
                SamAccountHandoverInfo samAccountHandoverInfo = new SamAccountHandoverInfo();

                samAccountHandoverInfo.setSamAccount("1");
                samAccountHandoverInfo.setCardName(cardAccountBasicInfo.getCardName());
                samAccountHandoverInfo.setCardNo(cardAccountBasicInfo.getContractId());
                samAccountHandoverInfo.setAgeCode(distributionInfo.getAgeCode());
                samAccountHandoverInfo.setOutstandingAmount(distributionInfo.getOutstandingAmount());
                samAccountHandoverInfo.setSamRecievedDate(new Date());
                samAccountHandoverInfo.setCreatedDate(new Date());
                samAccountHandoverInfo.setCreatedBy(userPrincipal.getUsername());
                samAccountHandoverInfo.setCustomerId(cardAccountBasicInfo.getClientId());
                samAccountHandoverInfo.setProductGroup(distributionInfo.getProductGroup());
                samAccountHandoverInfo.setSecureCard(distributionInfo.getSecureCard());
                samAccountHandoverInfo.setPlasticCd(distributionInfo.getPlasticCd());
                samAccountHandoverInfo.setBillingCycle(distributionInfo.getProductGroup());
                samAccountHandoverInfo.setVvip(distributionInfo.getVvip());
                samAccountHandoverInfo.setStatusDate(distributionInfo.getStatusDate());
                samAccountHandoverInfo.setLatest("1");
                samAccountHandoverInfo.setProductUnit("Card");
                samAccountHandoverInfo.setCardAccountBasicInfo(cardAccountBasicInfo);

                samAccountHandoverInfoList.add(samAccountHandoverInfo);
            } else {
                CardAgencyDistributionInfo cardAgencyDistributionInfo = cardAgencyDistributionRepository.findFirstByCreatedDateLessThanAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), cardAccountBasicInfo, "1");
                cardAgencyDistributionInfo.setLatest("0");
                cardAgencyDistributionRepository.save(cardAgencyDistributionInfo);

                CardAccountDistributionInfo cardAccountDistributionInfo = cardAccountDistributionRepository.findFirstByCreatedDateLessThanAndCardAccountBasicInfoOrderByCreatedDateDesc(getStartDate(), cardAccountBasicInfo);

                SamAccountHandoverInfo samAccountHandoverInfo = new SamAccountHandoverInfo();

                if (cardAccountDistributionInfo != null) {
                    samAccountHandoverInfo.setProductGroup(cardAccountDistributionInfo.getProductGroup());
                    samAccountHandoverInfo.setOutstandingAmount(cardAccountDistributionInfo.getOutstandingAmount());
                    samAccountHandoverInfo.setAgeCode(cardAccountDistributionInfo.getAgeCode());
                }

                samAccountHandoverInfo.setSamAccount("1");
                samAccountHandoverInfo.setCardName(cardAccountBasicInfo.getCardName());
                samAccountHandoverInfo.setCardNo(cardAccountBasicInfo.getContractId());
                samAccountHandoverInfo.setSamRecievedDate(new Date());
                samAccountHandoverInfo.setCreatedDate(new Date());
                samAccountHandoverInfo.setCreatedBy(userPrincipal.getUsername());
                samAccountHandoverInfo.setCustomerId(cardAccountBasicInfo.getClientId());
                samAccountHandoverInfo.setSecureCard(distributionInfo.getSecureCard());
                samAccountHandoverInfo.setPlasticCd(distributionInfo.getPlasticCd());
                samAccountHandoverInfo.setBillingCycle(distributionInfo.getProductGroup());
                samAccountHandoverInfo.setVvip(distributionInfo.getVvip());
                samAccountHandoverInfo.setStatusDate(distributionInfo.getStatusDate());
                samAccountHandoverInfo.setLatest("1");
                samAccountHandoverInfo.setProductUnit("Card");
                samAccountHandoverInfo.setCardAccountBasicInfo(cardAccountBasicInfo);

                samAccountHandoverInfoList.add(samAccountHandoverInfo);
            }
        }

        try {
            samAccountHandoverRepository.saveAll(samAccountHandoverInfoList);
            autoDistributionCardSamTempRepository.deleteAll(autoDistributionCardSamTempList);
            success = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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
