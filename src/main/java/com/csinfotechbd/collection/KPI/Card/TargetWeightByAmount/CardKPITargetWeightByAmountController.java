package com.csinfotechbd.collection.KPI.Card.TargetWeightByAmount;
/*
  Created by Md. Monirul Islam on 9/3/2019
*/

import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.location.LocationService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;
import com.csinfotechbd.collection.settings.productType.ProductTypeService;
import com.csinfotechbd.collection.settings.statuscdmanagement.StatusCd;
import com.csinfotechbd.collection.settings.statuscdmanagement.StatusCdManagementService;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.user.UserService;
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
@RequestMapping(value = "/collection/kpi/card/weightByAmount/")
public class CardKPITargetWeightByAmountController {

    private CardKPITargetWeightByAmountService cardKPITargetWeightByAmountService;
    private ProductTypeService productTypeService;
    private LocationService locationService;;
    private AgeCodeService ageCodeService;
    private EmployeeService employeeService;
    private UserService userService;
    private StatusCdManagementService statusCdManager;
    private ProductTypeRepository productTypeRepository;


    @GetMapping("list")
    public String viewList(Model model){

        List<CardKPITargetWeightByAmountEntity> cardKPITargetWeightByAmountEntities= cardKPITargetWeightByAmountService.getAll();
        model.addAttribute("cardKPIByAmountList",cardKPITargetWeightByAmountEntities);
        return "collection/kpi/card/weightByAmount/listView";

    }


    @GetMapping("create")
    public String addNewPage(Model model) {
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("dealerNameList", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManager.list()));

        model.addAttribute("cardKPIByAmount",new CardKPITargetWeightByAmountEntity());
        return "collection/kpi/card/weightByAmount/create";
    }


    @PostMapping(value = "create")
    public String addNewPage( CardKPITargetWeightByAmountEntity cardKPITargetWeightByAmountEntity) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId=userService.findById(user.getId()).getEmployeeId();

        if(cardKPITargetWeightByAmountEntity.getId() == null)
        {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<AgeCodeEntity> ageCodeEntities= new ArrayList<>();
            List<EmployeeInfoEntity> employeeInfoEntities= new ArrayList<>();
            List<StatusCd> statusCds= new ArrayList<>();


            for(String s: cardKPITargetWeightByAmountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeRepository.findById(new Long(s)).get();
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: cardKPITargetWeightByAmountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: cardKPITargetWeightByAmountEntity.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity= ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            for(String s: cardKPITargetWeightByAmountEntity.getDealerNameIds()){
                EmployeeInfoEntity employeeInfoEntity= employeeService.getById(new Long(s));
                employeeInfoEntities.add(employeeInfoEntity);
            }

            for(String s: cardKPITargetWeightByAmountEntity.getStatusCdIds()){
                StatusCd statusCd= statusCdManager.findbyId(new Long(s));
                statusCds.add(statusCd);
            }

            cardKPITargetWeightByAmountEntity.setProductType(productTypeEntities);
            cardKPITargetWeightByAmountEntity.setLocation(locationEntities);
            cardKPITargetWeightByAmountEntity.setAgeCode(ageCodeEntities);
            cardKPITargetWeightByAmountEntity.setDealerName(employeeInfoEntities);
            cardKPITargetWeightByAmountEntity.setStatusCd(statusCds);

            cardKPITargetWeightByAmountEntity.setCreatedBy(empId);
            cardKPITargetWeightByAmountEntity.setCreatedDate(new Date());
            boolean save = cardKPITargetWeightByAmountService.addNew(cardKPITargetWeightByAmountEntity);
        }
        else {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<AgeCodeEntity> ageCodeEntities= new ArrayList<>();
            List<EmployeeInfoEntity> employeeInfoEntities= new ArrayList<>();
            List<StatusCd> statusCds= new ArrayList<>();

            for(String s: cardKPITargetWeightByAmountEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: cardKPITargetWeightByAmountEntity.getLocationIds()){
                LocationEntity locationEntity= locationService.getById(new Long(s));
                locationEntities.add(locationEntity);
            }

            for(String s: cardKPITargetWeightByAmountEntity.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity= ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            for(String s: cardKPITargetWeightByAmountEntity.getDealerNameIds()){
                EmployeeInfoEntity employeeInfoEntity= employeeService.getById(new Long(s));
                employeeInfoEntities.add(employeeInfoEntity);
            }

            for(String s: cardKPITargetWeightByAmountEntity.getStatusCdIds()){
                StatusCd statusCd= statusCdManager.findbyId(new Long(s));
                statusCds.add(statusCd);
            }


            cardKPITargetWeightByAmountEntity.setProductType(productTypeEntities);
            cardKPITargetWeightByAmountEntity.setLocation(locationEntities);
            cardKPITargetWeightByAmountEntity.setAgeCode(ageCodeEntities);
            cardKPITargetWeightByAmountEntity.setDealerName(employeeInfoEntities);
            cardKPITargetWeightByAmountEntity.setStatusCd(statusCds);

            cardKPITargetWeightByAmountEntity.setModifiedBy(empId);
            cardKPITargetWeightByAmountEntity.setModifiedDate(new Date());
            boolean update=cardKPITargetWeightByAmountService.updateData(cardKPITargetWeightByAmountEntity);
        }
        return "redirect:/collection/kpi/card/weightByAmount/list";
    }


    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long findId, Model model) {
        CardKPITargetWeightByAmountEntity cardKPITargetWeightByAmountEntity= cardKPITargetWeightByAmountService.getByid(findId);
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("dealerNameList", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManager.list()));
        model.addAttribute("selectedProductTypeList", gson.toJson(cardKPITargetWeightByAmountEntity.getProductType()));
        model.addAttribute("selectedLocationList", gson.toJson(cardKPITargetWeightByAmountEntity.getLocation()));
        model.addAttribute("selectedAgeCodeList", gson.toJson(cardKPITargetWeightByAmountEntity.getAgeCode()));
        model.addAttribute("selectedDealerNameList", gson1.toJson(cardKPITargetWeightByAmountEntity.getDealerName()));
        model.addAttribute("selectedStatusCdList", gson.toJson(cardKPITargetWeightByAmountEntity.getStatusCd()));

        model.addAttribute("cardKPIByAmount", cardKPITargetWeightByAmountEntity);
        return "collection/kpi/card/weightByAmount/create";
    }


    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long findId,Model model) {
        model.addAttribute("cardKPIByAmount",cardKPITargetWeightByAmountService.getByid(findId));
        return "collection/kpi/card/weightByAmount/view";
    }
}
