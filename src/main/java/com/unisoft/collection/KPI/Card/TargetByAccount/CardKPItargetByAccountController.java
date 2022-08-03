package com.unisoft.collection.KPI.Card.TargetByAccount;
/*
  Created by Md.   Islam on 9/2/2019
*/

import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.collection.settings.statuscdmanagement.StatusCd;
import com.unisoft.collection.settings.statuscdmanagement.StatusCdManagementService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/collection/kpi/card/byAccount/")
public class CardKPItargetByAccountController {

    private CardKPITargetByAccountService cardKPITargetByAccountService;
    private ProductTypeService productTypeService;
    private LocationService locationService;;
    private AgeCodeService ageCodeService;
    private EmployeeService employeeService;
    private UserService userService;
    private StatusCdManagementService statusCdManager;
    private ProductTypeRepository productTypeRepository;
    private AssetClassificationLoanService assetClassificationLoanService;


    @GetMapping("list")

    public String viewList(Model model){

        List<CardKPITargetByAccountEntity> cardKPITargetByAccountEntities= cardKPITargetByAccountService.getAll();
        model.addAttribute("targetByAccList",cardKPITargetByAccountEntities);

        return "collection/kpi/card/targetByAccount/listView";

    }

    @GetMapping("create")
    public String addNewPage(Model model)
    {
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("dealerNameList", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManager.list()));
        model.addAttribute("assetClassificationList",gson.toJson(assetClassificationLoanService.getAll()));
        model.addAttribute("cardKPIByAccount",new CardKPITargetByAccountEntity());
        return "collection/kpi/card/targetByAccount/create";
    }

    @PostMapping(value = "create")
    public String addNewPage( CardKPITargetByAccountEntity cardKPITargetByAccountEntity)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId=userService.findById(user.getId()).getEmployeeId();

        if(cardKPITargetByAccountEntity.getId() == null)
        {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<AgeCodeEntity> ageCodeEntities= new ArrayList<>();
            List<EmployeeInfoEntity> employeeInfoEntities= new ArrayList<>();
            List<StatusCd> statusCds= new ArrayList<>();


            for(String s: cardKPITargetByAccountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeRepository.findById(new Long(s)).get();
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: cardKPITargetByAccountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: cardKPITargetByAccountEntity.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity= ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            for(String s: cardKPITargetByAccountEntity.getDealerNameIds()){
                EmployeeInfoEntity employeeInfoEntity= employeeService.getById(new Long(s));
                employeeInfoEntities.add(employeeInfoEntity);
            }

            for(String s: cardKPITargetByAccountEntity.getStatusCdIds()){
                StatusCd statusCd= statusCdManager.findbyId(new Long(s));
                statusCds.add(statusCd);
            }

            cardKPITargetByAccountEntity.setProductType(productTypeEntities);
            cardKPITargetByAccountEntity.setLocation(locationEntities);
            cardKPITargetByAccountEntity.setAgeCode(ageCodeEntities);
            cardKPITargetByAccountEntity.setDealerName(employeeInfoEntities);
            cardKPITargetByAccountEntity.setStatusCd(statusCds);

            cardKPITargetByAccountEntity.setCreatedBy(empId);
            cardKPITargetByAccountEntity.setCreatedDate(new Date());
            boolean save = cardKPITargetByAccountService.addNew(cardKPITargetByAccountEntity);
        }
        else {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<AgeCodeEntity> ageCodeEntities= new ArrayList<>();
            List<EmployeeInfoEntity> employeeInfoEntities= new ArrayList<>();
            List<StatusCd> statusCds= new ArrayList<>();

            for(String s: cardKPITargetByAccountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: cardKPITargetByAccountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: cardKPITargetByAccountEntity.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity= ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            for(String s: cardKPITargetByAccountEntity.getDealerNameIds()){
                EmployeeInfoEntity employeeInfoEntity= employeeService.getById(new Long(s));
                employeeInfoEntities.add(employeeInfoEntity);
            }

            for(String s: cardKPITargetByAccountEntity.getStatusCdIds()){
                StatusCd statusCd= statusCdManager.findbyId(new Long(s));
                statusCds.add(statusCd);
            }


            cardKPITargetByAccountEntity.setProductType(productTypeEntities);
            cardKPITargetByAccountEntity.setLocation(locationEntities);
            cardKPITargetByAccountEntity.setAgeCode(ageCodeEntities);
            cardKPITargetByAccountEntity.setDealerName(employeeInfoEntities);
            cardKPITargetByAccountEntity.setStatusCd(statusCds);

            cardKPITargetByAccountEntity.setModifiedBy(empId);
            cardKPITargetByAccountEntity.setModifiedDate(new Date());
            boolean update=cardKPITargetByAccountService.updateData(cardKPITargetByAccountEntity);
        }
        return "redirect:/collection/kpi/card/byAccount/list";
    }

    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long findId, Model model)
    {
        CardKPITargetByAccountEntity cardKPITargetByAccountEntity= cardKPITargetByAccountService.getByid(findId);
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("dealerNameList", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManager.list()));
        model.addAttribute("assetClassificationList",gson.toJson(assetClassificationLoanService.getAll()));
        model.addAttribute("selectedProductTypeList", gson.toJson(cardKPITargetByAccountEntity.getProductType()));
        model.addAttribute("selectedLocationList", gson.toJson(cardKPITargetByAccountEntity.getLocation()));
        model.addAttribute("selectedAgeCodeList", gson.toJson(cardKPITargetByAccountEntity.getAgeCode()));
        model.addAttribute("selectedDealerNameList", gson1.toJson(cardKPITargetByAccountEntity.getDealerName()));
        model.addAttribute("selectedStatusCdList", gson.toJson(cardKPITargetByAccountEntity.getStatusCd()));
        model.addAttribute("selectedCLStatus",gson.toJson(cardKPITargetByAccountEntity.getClStatus()));
        model.addAttribute("cardKPIByAccount", cardKPITargetByAccountEntity);
        return "collection/kpi/card/targetByAccount/create";
    }

    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long findId,Model model)
    {
        model.addAttribute("cardKPIByAccount",cardKPITargetByAccountService.getByid(findId));
        return "collection/kpi/card/targetByAccount/view";
    }

}
