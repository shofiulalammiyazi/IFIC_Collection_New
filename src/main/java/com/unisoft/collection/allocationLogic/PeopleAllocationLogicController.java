package com.unisoft.collection.allocationLogic;
/*
Created by   Islam at 7/18/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.samd.dashboard.managerDashboard.monitoringTeamSummary.PeopleAllocationForMonitoringSummaryDto;
import com.unisoft.collection.samd.dashboard.managerDashboard.monitoringTeamSummary.PeopleAllocationForMonitoringTeamSummaryService;
import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.branch.BranchService;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntityDto1;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.producttypecard.ProductTypeCardEntity;
import com.unisoft.collection.settings.producttypecard.ProductTypeCardService;
import com.unisoft.collection.settings.sector.SectorEntity;
import com.unisoft.collection.settings.sector.SectorService;
import com.unisoft.collection.settings.unit.UnitService;
import com.unisoft.user.UserPrincipal;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/allocationlogic/")
public class PeopleAllocationLogicController {

    private final PeopleAllocationLogicRepository peopleAllocationLogicRepository;
    private final EmployeeService employeeService;
    private final DPDBucketService dpdBucketService;
    private final AgeCodeService ageCodeService;
    private final ProductTypeRepository productTypeRepository;
    private final SectorService sectorService;
    private final BranchService branchService;
    private final AuditTrailService auditTrailService;
    private final UnitService unitService;
    private final PeopleAllocationLogicService peopleAllocationLogicService;
    private final PeopleAllocationForMonitoringTeamSummaryService summaryService;
    private final AgencyService agencyService;
    @Autowired
    private  ProductTypeCardService productTypeCardService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        List<PeopleAllocationLogicInfo> all = peopleAllocationLogicRepository.findAll();

        all.stream().forEach( pl -> {
            Collections.sort(pl.getDpdBucketEntity(), (a,b) -> {
                if(Integer.parseInt(a.getBucketName()) > Integer.parseInt(b.getBucketName()))
                    return 1;
                else
                    return -1;
            });
        });

        List<EmployeeInfoEntity> managerList = employeeService.getManagerList();
        List<EmployeeInfoEntity> teamLeaderList = employeeService.getTeamLeaderList();
        List<EmployeeInfoEntity> superVisorList = employeeService.getSuperVisorList();
        model.addAttribute("managerList", managerList);
        model.addAttribute("teamleaderList", teamLeaderList);
        model.addAttribute("supervisorList", superVisorList);
        model.addAttribute("allocationlist", all);
        return "collection/allocationLogic/allocationlogic";
    }

    @GetMapping("create")
    public String addpage(Model model) {
        model.addAttribute("allocationLogic", new PeopleAllocationLogicInfo());

        List<EmployeeInfoEntity> dealerList = employeeService.getDealerList();
        List<EmployeeInfoEntity> managerList = employeeService.getManagerList();
        List<EmployeeInfoEntity> teamLeaderList = employeeService.getTeamLeaderList();
        List<EmployeeInfoEntity> superVisorList = employeeService.getSuperVisorList();
        List<Branch> branchList = branchService.getList();

        List<AgencyEntity> agencyList = agencyService.getActiveList();

        List<AgeCodeEntity> ageCodeEntities = ageCodeService.getAll();
        List<ProductTypeCardEntity> productTypeList = productTypeCardService.getAllActive();
        List<ProductTypeEntity> productTypeListLoan = productTypeRepository.findByProductGroupEntityCardAccount("Loan");
        List<SectorEntity> sectorServiceAll = sectorService.getAll();
        List<DPDBucketEntityDto1> activeList = dpdBucketService.getCustomActiveList();

        Gson gson = new Gson();

        String dpd = gson.toJson(activeList);
        String age = gson.toJson(ageCodeEntities);
        String productTypes = gson.toJson(getDistinctProductTypeCard(productTypeList));
        String productGroups = gson.toJson(getDistinctProductType(productTypeListLoan));
        String sectors = gson.toJson(sectorServiceAll);
        String branches = gson.toJson(branchList);

        model.addAttribute("dealerList", dealerList);
        model.addAttribute("managerList", managerList);
        model.addAttribute("teamleaderList", teamLeaderList);
        model.addAttribute("supervisorList", superVisorList);

        model.addAttribute("dpdBucketList", dpd);
        model.addAttribute("ageCodeList", age);
        model.addAttribute("productTypeList", productTypes);
        model.addAttribute("productGroupList", productGroups);
        model.addAttribute("sectorList", sectors);
        model.addAttribute("branchList", branches);
        model.addAttribute("units", unitService.getList());
        model.addAttribute("agencyList", agencyList);

        return "collection/allocationLogic/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, PeopleAllocationLogicInfo peopleAllocationLogic) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        if (peopleAllocationLogic.getSupervisor().getId() == null) peopleAllocationLogic.setSupervisor(null);
//        if (peopleAllocationLogic.getManager().getId() == null) peopleAllocationLogic.setManager(null);
//        if (peopleAllocationLogic.getDealer().getId() == null) peopleAllocationLogic.setDealer(null);
//        if (peopleAllocationLogic.getTeamlead().getId() == null) peopleAllocationLogic.setTeamlead(null);
//        if (peopleAllocationLogic.getAgency().getId() == null) peopleAllocationLogic.setAgency(null);
        if (peopleAllocationLogic.getLocation().getId() == 0) peopleAllocationLogic.setLocation(null);

        List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();
        List<ProductTypeEntity> productTypeEntites = new ArrayList<>();
        List<ProductTypeEntity> productTypeEntitiesLoan = new ArrayList<>();
        List<SectorEntity> sectorEntites = new ArrayList<>();
        List<DPDBucketEntity> dpdBucketEntities = new ArrayList<>();

        if (peopleAllocationLogic.getId() == null) {
            peopleAllocationLogic.setCreatedBy(user.getUsername());
            peopleAllocationLogic.setCreatedDate(new Date());

            getAgeCodeDpdBucket(peopleAllocationLogic, ageCodeEntities, productTypeEntites,
                    productTypeEntitiesLoan, sectorEntites, dpdBucketEntities);
            peopleAllocationLogicRepository.save(peopleAllocationLogic);
            auditTrailService.saveCreatedData("People Allocation Logic", peopleAllocationLogic);
        } else {
            PeopleAllocationLogicInfo oldEntity = peopleAllocationLogicRepository.getOne(peopleAllocationLogic.getId());
            PeopleAllocationLogicInfo previousEntity = new PeopleAllocationLogicInfo();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            peopleAllocationLogic.setModifiedBy(user.getUsername());
            peopleAllocationLogic.setModifiedDate(new Date());

            getAgeCodeDpdBucket(peopleAllocationLogic, ageCodeEntities, productTypeEntites,
                    productTypeEntitiesLoan, sectorEntites, dpdBucketEntities);

            peopleAllocationLogicRepository.save(peopleAllocationLogic);
            auditTrailService.saveUpdatedData("People Allocation Logic", previousEntity, peopleAllocationLogic);
        }

        return "redirect:/collection/allocationlogic/list";
    }

    private void getAgeCodeDpdBucket(PeopleAllocationLogicInfo peopleAllocationLogic,
                                     List<AgeCodeEntity> ageCodeEntities, List<ProductTypeEntity> productTypeEntites,
                                     List<ProductTypeEntity> productTypeEntitiesLoan, List<SectorEntity> sectorEntites,
                                     List<DPDBucketEntity> dpdBucketEntities) {
        if (peopleAllocationLogic.getUnit().equals("Card")) {
            List<ProductTypeCardEntity>productTypeCardEntity=new ArrayList<>();
            for (String id : peopleAllocationLogic.getSelectedAgeCode()) {
                AgeCodeEntity ageCode = ageCodeService.getById(Long.parseLong(id));
                ageCodeEntities.add(ageCode);
            }
            peopleAllocationLogic.setAgeCodeEntity(ageCodeEntities);

            for (String id : peopleAllocationLogic.getSelectedProductType()) {
                ProductTypeCardEntity productTypesCard = productTypeCardService.getById(Long.parseLong(id));
                List<ProductTypeCardEntity> byName = productTypeCardService.getByName(productTypesCard.getName());
                productTypeCardEntity.addAll(byName);
            }
            peopleAllocationLogic.setProductTypeEntityCard(productTypeCardEntity);

            for (String id : peopleAllocationLogic.getSelectedSector()) {
                SectorEntity sectorEntity = sectorService.getById(Long.parseLong(id));
                sectorEntites.add(sectorEntity);
            }

            peopleAllocationLogic.setSectorEntity(sectorEntites);

            peopleAllocationLogic.setDpdBucketEntity(dpdBucketEntities);

        } else {
            for (String id : peopleAllocationLogic.getSelectedDpdBucket()) {
                DPDBucketEntity bucketEntity = dpdBucketService.getById(Long.parseLong(id));
                dpdBucketEntities.add(bucketEntity);
            }
            peopleAllocationLogic.setDpdBucketEntity(dpdBucketEntities);

            for (String id : peopleAllocationLogic.getSelectedProductGroup()) {
                ProductTypeEntity productGroup = productTypeRepository.findById(Long.parseLong(id)).get();
                List<ProductTypeEntity> byName = productTypeRepository.findByName(productGroup.getName());
                productTypeEntitiesLoan.addAll(byName);
            }
            peopleAllocationLogic.setProductTypeEntityLoan(productTypeEntitiesLoan);

            for (String id : peopleAllocationLogic.getSelectedSector()) {
                SectorEntity sectorEntity = sectorService.getById(Long.parseLong(id));
                sectorEntites.add(sectorEntity);
            }

            peopleAllocationLogic.setSectorEntity(sectorEntites);

            peopleAllocationLogic.setAgeCodeEntity(ageCodeEntities);
        }

        if (peopleAllocationLogic.getSelectedBranch().size() > 0) {
            Set<Branch> branchSet = peopleAllocationLogic.getSelectedBranch().stream()
                    .map(b -> branchService.findById(Integer.parseInt(b))).collect(Collectors.toSet());
            peopleAllocationLogic.setBranches(branchSet);
        }
    }

    @PostMapping(value = "dealer-list")
    @ResponseBody
    public List<DealerViewModel> getDealerTeamLeadWise(@RequestBody TeamLeadPayload teamLeadPayload) {
        return peopleAllocationLogicRepository.findByMyTeamlead(teamLeadPayload.getTeamLeadPinList());
    }

    @GetMapping(value = "team-leader-list")
    @ResponseBody
    public List<DealerViewModel> getSupervisorWiseTeamLeaderList(String[] pinList) {
        return peopleAllocationLogicRepository.findTeamLeadBySupervisors(Arrays.asList(pinList))
                .stream().map(DealerViewModel::new).collect(Collectors.toList());
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        PeopleAllocationLogicInfo peopleAllocationLogic = peopleAllocationLogicRepository.findById(id).get();

        List<EmployeeInfoEntity> dealerList = employeeService.getDealerList();

        List<EmployeeInfoEntity> managerList = employeeService.getManagerList();
        List<EmployeeInfoEntity> teamLeaderList = employeeService.getTeamLeaderList();
        List<EmployeeInfoEntity> superVisorList = employeeService.getSuperVisorList();
//        dealerList.addAll(teamLeaderList);

        List<DPDBucketEntity> dpdBucketEntities = dpdBucketService.getAll();
        List<AgeCodeEntity> ageCodeEntities = ageCodeService.getAll();
        List<ProductTypeEntity> productTypeList = productTypeRepository.findByProductGroupEntityCardAccount("Card");
        List<ProductTypeEntity> productTypeListLoan = productTypeRepository.findByProductGroupEntityCardAccount("Loan");
        List<SectorEntity> sectorServiceAll = sectorService.getAll();
        List<Branch> branchList = branchService.getList();

        Gson gson = new Gson();

        String dpd = gson.toJson(dpdBucketEntities);
        String age = gson.toJson(ageCodeEntities);
        String productTypes = gson.toJson(getDistinctProductType(productTypeList));
        String productGroups = gson.toJson(getDistinctProductType(productTypeListLoan));
        String sectors = gson.toJson(sectorServiceAll);
        String branches = gson.toJson(branchList);

        model.addAttribute("dealerList", dealerList);
        model.addAttribute("managerList", managerList);
        model.addAttribute("teamleaderList", teamLeaderList);
        model.addAttribute("supervisorList", superVisorList);

        model.addAttribute("dpdBucketList", dpd);
        model.addAttribute("ageCodeList", age);
        model.addAttribute("productTypeList", productTypes);
        model.addAttribute("productGroupList", productGroups);
        model.addAttribute("sectorList", sectors);
        model.addAttribute("branchList", branches);

        model.addAttribute("selectedDpdBucketList",
                gson.toJson(peopleAllocationLogic.getDpdBucketEntity()));
        model.addAttribute("selectedAgetCodeList",
                gson.toJson(peopleAllocationLogic.getAgeCodeEntity()));
        model.addAttribute("selectedProductTypeList",
                gson.toJson(getDistinctProductType(peopleAllocationLogic.getProductTypeEntity())));
        model.addAttribute("selectedProductGroupList",
                gson.toJson(getDistinctProductType(peopleAllocationLogic.getProductTypeEntityLoan())));
        model.addAttribute("selectedSectorList", gson.toJson(peopleAllocationLogic.getSectorEntity()));
        model.addAttribute("selectedBranchList", gson.toJson(peopleAllocationLogic.getBranches()));


        model.addAttribute("dealerLocation", peopleAllocationLogic.getLocation().getCity());
        model.addAttribute("locationId", peopleAllocationLogic.getLocation().getId());
        model.addAttribute("dpdOrAgeCode", peopleAllocationLogic.getDpdBucketEntity().size() > 0 ? 1 : 2);
        model.addAttribute("productLoanOrCard", peopleAllocationLogic.getProductTypeEntityLoan().size() > 0 ? 1 : 2);
        model.addAttribute("allocationLogic", peopleAllocationLogic);
        model.addAttribute("units", unitService.getList());

        return "collection/allocationLogic/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("allocationLogic", peopleAllocationLogicRepository.findById(id).get());
        return "collection/allocationLogic/view";
    }

    private List<ProductTypeEntity> getDistinctProductType(List<ProductTypeEntity> productTypeList) {
        List<ProductTypeEntity> distinctProuductTypeLoanList = new ArrayList<>();
        for (ProductTypeEntity p : productTypeList) {
            boolean check = distinctProuductTypeLoanList.parallelStream()
                    .anyMatch(d -> d.getName().trim().toUpperCase().equals(p.getName().trim().toUpperCase()));
            if (!check) distinctProuductTypeLoanList.add(p);
        }
        return distinctProuductTypeLoanList;
    }

    private List<ProductTypeCardEntity> getDistinctProductTypeCard(List<ProductTypeCardEntity> productTypeList) {
        List<ProductTypeCardEntity> distinctProuductTypeLoanList = new ArrayList<>();
        for (ProductTypeCardEntity p : productTypeList) {
            boolean check = distinctProuductTypeLoanList.parallelStream()
                    .anyMatch(d -> d.getName().trim().toUpperCase().equals(p.getName().trim().toUpperCase()));
            if (!check) distinctProuductTypeLoanList.add(p);
        }
        return distinctProuductTypeLoanList;
    }



    @GetMapping("findBy-supervisor")
    @ResponseBody
    public List<EmployeeInfoEntity> getSupervisorWiseTeamleader(@RequestParam String supervisorPin, @RequestParam String unit){
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(supervisorPin);
        List<PeopleAllocationForMonitoringSummaryDto> peopleAllocationLogicInfoList = summaryService.findSupervisorWiseTeamleader(employeeInfoEntity.getId(), unit);
        List<EmployeeInfoEntity> entities = new ArrayList<>();
        for (PeopleAllocationForMonitoringSummaryDto peopleAllocationLogicInfo: peopleAllocationLogicInfoList){
            EmployeeInfoEntity entity = employeeService.getById(((Number) peopleAllocationLogicInfo.getTeamleaderId()).longValue());
            entities.add(entity);
        }

        return entities;

    }


    @GetMapping("findBy-teamleader")
    @ResponseBody
    public List<EmployeeInfoEntity> geteamleaderWisedealer(@RequestParam Long teamleaderId, @RequestParam String unit){
        EmployeeInfoEntity employeeInfoEntity = employeeService.getById(teamleaderId);
        List<PeopleAllocationForMonitoringSummaryDto> peopleAllocationLogicInfoList = summaryService.findTeamleaderWiseDealer(employeeInfoEntity.getId(), unit);
        List<EmployeeInfoEntity> entities = new ArrayList<>();
        for (PeopleAllocationForMonitoringSummaryDto peopleAllocationLogicInfo: peopleAllocationLogicInfoList){
            EmployeeInfoEntity entity = employeeService.getById(((Number) peopleAllocationLogicInfo.getDealerId()).longValue());
            entities.add(entity);
        }

        return entities;

    }



}
