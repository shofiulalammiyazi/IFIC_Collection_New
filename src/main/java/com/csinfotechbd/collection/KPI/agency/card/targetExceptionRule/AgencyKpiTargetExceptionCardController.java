package com.csinfotechbd.collection.KPI.agency.card.targetExceptionRule;
/*
Created by Monirul Islam at 9/23/2019 
*/

import com.csinfotechbd.collection.KPI.Card.TargetByManager.DealerTargetCardManager;
import com.csinfotechbd.collection.KPI.Card.TargetByManager.DealerTargetCardMangerRepository;
import com.csinfotechbd.collection.KPI.agency.card.targetByCommision.AgencyKpiCommisionRateCardSetup;
import com.csinfotechbd.collection.KPI.agency.card.targetByCommision.AgencyKpiCommisionRateCardSetupRepository;
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
@RequestMapping(value = "/collection/kpi/card/agency/exception")
public class AgencyKpiTargetExceptionCardController {
    private AgencyKpiTargetExceptionCardRepository agencyKpiTargetExceptionCardRepository;
    private ProductTypeService productTypeService;
    private LocationService locationService;
    private AgeCodeService ageCodeService;
    private EmployeeService employeeService;
    private StatusCdManagementService statusCdManagementService;
    private ProductTypeRepository productTypeRepository;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("agencyList",agencyKpiTargetExceptionCardRepository.findAll());
        return "collection/kpi/agency/card/exception/list";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("employeeList", gson1.toJson(employeeService.getSuperVisorList()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManagementService.list()));

        model.addAttribute("agency",new AgencyKpiTargetExceptionCard());
        return "collection/kpi/agency/card/exception/create";
    }

    @PostMapping(value = "create")
    public String addnew(Model model, AgencyKpiTargetExceptionCard agency) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<EmployeeInfoEntity> employeeInfoEntityList = new ArrayList<>();
        List<ProductTypeEntity> productTypeEntityList = new ArrayList<>();
        List<StatusCd> statusCdList = new ArrayList<>();

        agency.getEmployeeIds().forEach(s -> {
            EmployeeInfoEntity employeeInfoEntity = employeeService.getById(Long.parseLong(s));
            employeeInfoEntityList.add(employeeInfoEntity);
        });

        agency.getProductIds().forEach(s -> {
            ProductTypeEntity productTypeEntity = productTypeService.getById(Long.parseLong(s));
            productTypeEntityList.add(productTypeEntity);
        });

        agency.getStatusCdIds().forEach(s -> {
            StatusCd statusCd = statusCdManagementService.findbyId(Long.parseLong(s));
            statusCdList.add(statusCd);
        });

        agency.setEmployeeInfoEntityList(employeeInfoEntityList);
        agency.setProductTypeEntityList(productTypeEntityList);
        agency.setStatusCdList(statusCdList);

        if(agency.getId()==null) {
            agency.setCreatedBy(user.getUsername());
            agency.setCreatedDate(new Date());
            agencyKpiTargetExceptionCardRepository.save(agency);
        }else {
            agency.setModifiedBy(user.getUsername());
            agency.setModifiedDate(new Date());
            agencyKpiTargetExceptionCardRepository.save(agency);
        }
        return "redirect:/collection/kpi/card/agency/exception/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id, Model model) {
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        AgencyKpiTargetExceptionCard agencyKpiTargetAmountSetup = agencyKpiTargetExceptionCardRepository.findById(id).get();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("employeeList", gson1.toJson(employeeService.getSuperVisorList()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManagementService.list()));

        model.addAttribute("selectedProductTypeList", gson.toJson(agencyKpiTargetAmountSetup.getProductTypeEntityList()));
        model.addAttribute("selectedStatusCdList", gson.toJson(agencyKpiTargetAmountSetup.getStatusCdList()));
        model.addAttribute("selectedEmployeeList", gson1.toJson(agencyKpiTargetAmountSetup.getEmployeeInfoEntityList()));


        model.addAttribute("agency", agencyKpiTargetAmountSetup);
        return "collection/kpi/agency/card/exception/create";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id, Model model) {
        model.addAttribute("agency",agencyKpiTargetExceptionCardRepository.findById(id).get());
        return "collection/kpi/agency/card/exception/view";
    }
}
