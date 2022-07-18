package com.csinfotechbd.collection.KPI.Card.TargetExceptionRule;
/*
  Created by Md. Monirul Islam on 9/4/2019
*/

import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.collection.settings.location.LocationEntity;
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
@RequestMapping(value = "/collection/kpi/card/exceptionRule/")
public class CardKPITargetExceptionRuleController {

    private CardKPITargetExceptionRuleService cardKPITargetExceptionRuleService;
    private ProductTypeService productTypeService;
    private EmployeeService employeeService;
    private UserService userService;
    private StatusCdManagementService statusCdManager;
    private ProductTypeRepository productTypeRepository;

    @GetMapping("list")

    public String viewList(Model model){

        List<CardKPITargetExceptionRuleEntity> cardKPITargetExceptionRuleEntities= cardKPITargetExceptionRuleService.getAll();
        model.addAttribute("exceptionRuleList",cardKPITargetExceptionRuleEntities);

        return "collection/kpi/card/exceptionRule/listView";

    }

    @GetMapping("create")
    public String addNewPage(Model model)
    {
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("dealerNameList", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManager.list()));

        model.addAttribute("exceptionRuleCard",new CardKPITargetExceptionRuleEntity());
        return "collection/kpi/card/exceptionRule/create";
    }

    @PostMapping(value = "create")
    public String addNewPage( CardKPITargetExceptionRuleEntity cardKPITargetExceptionRuleEntity)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId=userService.findById(user.getId()).getEmployeeId();

        if(cardKPITargetExceptionRuleEntity.getId() == null)
        {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<EmployeeInfoEntity> employeeInfoEntities= new ArrayList<>();
            List<StatusCd> statusCds= new ArrayList<>();


            for(String s: cardKPITargetExceptionRuleEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeRepository.findById(new Long(s)).get();
                productTypeEntities.add(productTypeEntity);
            }


            for(String s: cardKPITargetExceptionRuleEntity.getDealerNameIds()){
                EmployeeInfoEntity employeeInfoEntity= employeeService.getById(new Long(s));
                employeeInfoEntities.add(employeeInfoEntity);
            }

            for(String s: cardKPITargetExceptionRuleEntity.getStatusCdIds()){
                StatusCd statusCd= statusCdManager.findbyId(new Long(s));
                statusCds.add(statusCd);
            }

            cardKPITargetExceptionRuleEntity.setProductType(productTypeEntities);
            cardKPITargetExceptionRuleEntity.setDealerName(employeeInfoEntities);
            cardKPITargetExceptionRuleEntity.setStatusCd(statusCds);

            cardKPITargetExceptionRuleEntity.setCreatedBy(empId);
            cardKPITargetExceptionRuleEntity.setCreatedDate(new Date());
            boolean save = cardKPITargetExceptionRuleService.addNew(cardKPITargetExceptionRuleEntity);
        }
        else {
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
            List<LocationEntity> locationEntities = new ArrayList<>();
            List<AgeCodeEntity> ageCodeEntities= new ArrayList<>();
            List<EmployeeInfoEntity> employeeInfoEntities= new ArrayList<>();
            List<StatusCd> statusCds= new ArrayList<>();

            for(String s: cardKPITargetExceptionRuleEntity.getProductTypeIds()){
                ProductTypeEntity productTypeEntity= productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            for(String s: cardKPITargetExceptionRuleEntity.getDealerNameIds()){
                EmployeeInfoEntity employeeInfoEntity= employeeService.getById(new Long(s));
                employeeInfoEntities.add(employeeInfoEntity);
            }

            for(String s: cardKPITargetExceptionRuleEntity.getStatusCdIds()){
                StatusCd statusCd= statusCdManager.findbyId(new Long(s));
                statusCds.add(statusCd);
            }


            cardKPITargetExceptionRuleEntity.setProductType(productTypeEntities);
            cardKPITargetExceptionRuleEntity.setDealerName(employeeInfoEntities);
            cardKPITargetExceptionRuleEntity.setStatusCd(statusCds);

            cardKPITargetExceptionRuleEntity.setModifiedBy(empId);
            cardKPITargetExceptionRuleEntity.setModifiedDate(new Date());
            boolean update=cardKPITargetExceptionRuleService.updateData(cardKPITargetExceptionRuleEntity);
        }
        return "redirect:/collection/kpi/card/exceptionRule/list";
    }


    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long findId, Model model)
    {
        CardKPITargetExceptionRuleEntity cardKPITargetExceptionRuleEntity= cardKPITargetExceptionRuleService.getByid(findId);
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("dealerNameList", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManager.list()));
        model.addAttribute("selectedProductTypeList", gson.toJson(cardKPITargetExceptionRuleEntity.getProductType()));
        model.addAttribute("selectedDealerNameList", gson1.toJson(cardKPITargetExceptionRuleEntity.getDealerName()));
        model.addAttribute("selectedStatusCdList", gson.toJson(cardKPITargetExceptionRuleEntity.getStatusCd()));

        model.addAttribute("exceptionRuleCard", cardKPITargetExceptionRuleEntity);
        return "collection/kpi/card/exceptionRule/create";
    }

    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long findId,Model model) {
        model.addAttribute("exceptionRuleCard",cardKPITargetExceptionRuleService.getByid(findId));
        return "collection/kpi/card/exceptionRule/view";
    }

}
