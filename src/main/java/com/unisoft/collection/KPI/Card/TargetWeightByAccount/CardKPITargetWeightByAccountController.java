package com.unisoft.collection.KPI.Card.TargetWeightByAccount;
/*
  Created by Md.   Islam on 9/3/2019
*/

import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
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
@RequestMapping(value = "/collection/kpi/card/weightByAccount/")
public class CardKPITargetWeightByAccountController {


    private CardKPITargetWeightByAccountService cardKPITargetWeightByAccountService;
    private ProductTypeService productTypeService;
    private LocationService locationService;;
    private AgeCodeService ageCodeService;
    private EmployeeService employeeService;
    private UserService userService;
    private StatusCdManagementService statusCdManager;
    private ProductTypeRepository productTypeRepository;


    @GetMapping("list")
    public String viewList(Model model){

        List<CardKPITargetWeightByAccountEntity> cardKPITargetWeightByAccountEntities= cardKPITargetWeightByAccountService.getAll();
        model.addAttribute("cardTargetWeightByAccList",cardKPITargetWeightByAccountEntities);

        return "collection/kpi/card/weightByAccount/listView";

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

        model.addAttribute("cardTargetWeightByAcc",new CardKPITargetWeightByAccountEntity());
        return "collection/kpi/card/weightByAccount/create";
    }


    @PostMapping(value = "create")
    public String addNewPage( CardKPITargetWeightByAccountEntity cardKPITargetWeightByAccountEntity)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId=userService.findById(user.getId()).getEmployeeId();

        if(cardKPITargetWeightByAccountEntity.getId() == null)
        {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<AgeCodeEntity> ageCodeEntities= new ArrayList<>();
            List<EmployeeInfoEntity> employeeInfoEntities= new ArrayList<>();
            List<StatusCd> statusCds= new ArrayList<>();


            for(String s: cardKPITargetWeightByAccountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeRepository.findById(new Long(s)).get();
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: cardKPITargetWeightByAccountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: cardKPITargetWeightByAccountEntity.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity= ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            for(String s: cardKPITargetWeightByAccountEntity.getDealerNameIds()){
                EmployeeInfoEntity employeeInfoEntity= employeeService.getById(new Long(s));
                employeeInfoEntities.add(employeeInfoEntity);
            }

            for(String s: cardKPITargetWeightByAccountEntity.getStatusCdIds()){
                StatusCd statusCd= statusCdManager.findbyId(new Long(s));
                statusCds.add(statusCd);
            }

            cardKPITargetWeightByAccountEntity.setProductType(productTypeEntities);
            cardKPITargetWeightByAccountEntity.setLocation(locationEntities);
            cardKPITargetWeightByAccountEntity.setAgeCode(ageCodeEntities);
            cardKPITargetWeightByAccountEntity.setDealerName(employeeInfoEntities);
            cardKPITargetWeightByAccountEntity.setStatusCd(statusCds);

            cardKPITargetWeightByAccountEntity.setCreatedBy(empId);
            cardKPITargetWeightByAccountEntity.setCreatedDate(new Date());
            boolean save = cardKPITargetWeightByAccountService.addNew(cardKPITargetWeightByAccountEntity);
        }
        else {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<AgeCodeEntity> ageCodeEntities= new ArrayList<>();
            List<EmployeeInfoEntity> employeeInfoEntities= new ArrayList<>();
            List<StatusCd> statusCds= new ArrayList<>();

            for(String s: cardKPITargetWeightByAccountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: cardKPITargetWeightByAccountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: cardKPITargetWeightByAccountEntity.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity= ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            for(String s: cardKPITargetWeightByAccountEntity.getDealerNameIds()){
                EmployeeInfoEntity employeeInfoEntity= employeeService.getById(new Long(s));
                employeeInfoEntities.add(employeeInfoEntity);
            }

            for(String s: cardKPITargetWeightByAccountEntity.getStatusCdIds()){
                StatusCd statusCd= statusCdManager.findbyId(new Long(s));
                statusCds.add(statusCd);
            }


            cardKPITargetWeightByAccountEntity.setProductType(productTypeEntities);
            cardKPITargetWeightByAccountEntity.setLocation(locationEntities);
            cardKPITargetWeightByAccountEntity.setAgeCode(ageCodeEntities);
            cardKPITargetWeightByAccountEntity.setDealerName(employeeInfoEntities);
            cardKPITargetWeightByAccountEntity.setStatusCd(statusCds);

            cardKPITargetWeightByAccountEntity.setModifiedBy(empId);
            cardKPITargetWeightByAccountEntity.setModifiedDate(new Date());
            boolean update=cardKPITargetWeightByAccountService.updateData(cardKPITargetWeightByAccountEntity);
        }
        return "redirect:/collection/kpi/card/weightByAccount/list";
    }


    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long findId, Model model)
    {
        CardKPITargetWeightByAccountEntity cardKPITargetWeightByAccountEntity= cardKPITargetWeightByAccountService.getByid(findId);
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("dealerNameList", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManager.list()));
        model.addAttribute("selectedProductTypeList", gson.toJson(cardKPITargetWeightByAccountEntity.getProductType()));
        model.addAttribute("selectedLocationList", gson.toJson(cardKPITargetWeightByAccountEntity.getLocation()));
        model.addAttribute("selectedAgeCodeList", gson.toJson(cardKPITargetWeightByAccountEntity.getAgeCode()));
        model.addAttribute("selectedDealerNameList", gson1.toJson(cardKPITargetWeightByAccountEntity.getDealerName()));
        model.addAttribute("selectedStatusCdList", gson.toJson(cardKPITargetWeightByAccountEntity.getStatusCd()));

        model.addAttribute("cardTargetWeightByAcc", cardKPITargetWeightByAccountEntity);
        return "collection/kpi/card/weightByAccount/create";
    }

    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long findId,Model model)
    {
        model.addAttribute("cardTargetWeightByAcc",cardKPITargetWeightByAccountService.getByid(findId));
        return "collection/kpi/card/weightByAccount/view";
    }
}
