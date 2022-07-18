package com.csinfotechbd.collection.KPI.Card.TargetByAmount;
/*
  Created by Md. Monirul Islam on 8/28/2019
*/

import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.collection.settings.assetClassificationLoan.AssetClassificationLoanService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.collection.settings.location.LocationEntity;
import com.csinfotechbd.collection.settings.location.LocationService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;
import com.csinfotechbd.collection.settings.productType.ProductTypeService;
import com.csinfotechbd.collection.settings.statuscdmanagement.StatusCd;
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
import com.csinfotechbd.collection.settings.statuscdmanagement.StatusCdManagementService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/collection/kpi/card/byAmount/")
public class CardKPITargetByAmountController {

    private CardKPITargetByAmountService cardKPITargetByAmountService;
    private ProductTypeService productTypeService;
    private LocationService locationService;
    ;
    private AgeCodeService ageCodeService;
    private EmployeeService employeeService;
    private UserService userService;
    private StatusCdManagementService statusCdManager;
    private ProductTypeRepository productTypeRepository;
    private AssetClassificationLoanService assetClassificationLoanService;


    @GetMapping("list")

    public String viewList(Model model) {

        List<CardKPITargetByAmountEntity> cardKPITargetByAmountEntities = cardKPITargetByAmountService.getAll();
        model.addAttribute("cardKPIByAmountList", cardKPITargetByAmountEntities);

        return "collection/kpi/card/targetByAmount/listView";

    }

    @GetMapping("create")
    public String addNewPage(Model model) {
        List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
        List<LocationEntity> locationEntities = new ArrayList<>();
        List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();
        List<EmployeeInfoEntity> employeeInfoEntities = new ArrayList<>();
        List<StatusCd> statusCds = new ArrayList<>();
        Gson gson = new Gson();Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("dealerNameList", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManager.list()));
        model.addAttribute("assetClassificationList", gson.toJson(assetClassificationLoanService.getAll()));
        model.addAttribute("cardKPIByAmount", new CardKPITargetByAmountEntity());
        return "collection/kpi/card/targetByAmount/create";
    }

    @PostMapping(value = "create")
    public String addNewPage(CardKPITargetByAmountEntity cardKPITargetByAmountEntity) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String empId = userService.findById(user.getId()).getEmployeeId();

        List<ProductTypeEntity> productTypeEntities = new ArrayList<>();
        List<LocationEntity> locationEntities = new ArrayList<>();
        List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();
        List<EmployeeInfoEntity> employeeInfoEntities = new ArrayList<>();
        List<StatusCd> statusCds = new ArrayList<>();

        for (String s : cardKPITargetByAmountEntity.getLocationIds()) {
            LocationEntity locationEntity = locationService.getById(new Long(s));
            locationEntities.add(locationEntity);
        }

        for (String s : cardKPITargetByAmountEntity.getAgeCodeIds()) {
            AgeCodeEntity ageCodeEntity = ageCodeService.getById(new Long(s));
            ageCodeEntities.add(ageCodeEntity);
        }

        for (String s : cardKPITargetByAmountEntity.getDealerNameIds()) {
            EmployeeInfoEntity employeeInfoEntity = employeeService.getById(new Long(s));
            employeeInfoEntities.add(employeeInfoEntity);
        }

        for (String s : cardKPITargetByAmountEntity.getStatusCdIds()) {
            StatusCd statusCd = statusCdManager.findbyId(new Long(s));
            statusCds.add(statusCd);
        }

        cardKPITargetByAmountEntity.setProductType(productTypeEntities);
        cardKPITargetByAmountEntity.setLocation(locationEntities);
        cardKPITargetByAmountEntity.setAgeCode(ageCodeEntities);
        cardKPITargetByAmountEntity.setDealerName(employeeInfoEntities);
        cardKPITargetByAmountEntity.setStatusCd(statusCds);

        if (cardKPITargetByAmountEntity.getId() == null) {

            for (String s : cardKPITargetByAmountEntity.getProductTypeIds()) {
                ProductTypeEntity productTypeEntity = productTypeRepository.findById(new Long(s)).get();
                productTypeEntities.add(productTypeEntity);
            }

            cardKPITargetByAmountEntity.setCreatedBy(empId);
            cardKPITargetByAmountEntity.setCreatedDate(new Date());
            boolean save = cardKPITargetByAmountService.addNew(cardKPITargetByAmountEntity);
        } else {

            for (String s : cardKPITargetByAmountEntity.getProductTypeIds()) {
                ProductTypeEntity productTypeEntity = productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);
            }

            cardKPITargetByAmountEntity.setModifiedBy(empId);
            cardKPITargetByAmountEntity.setModifiedDate(new Date());
            boolean update = cardKPITargetByAmountService.updateData(cardKPITargetByAmountEntity);
        }
        return "redirect:/collection/kpi/card/byAmount/list";
    }

    @GetMapping("edit")
    public String editPage(@RequestParam(value = "id") Long findId, Model model) {
        CardKPITargetByAmountEntity cardKPITargetByAmountEntity = cardKPITargetByAmountService.getByid(findId);
        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        model.addAttribute("productTypeList", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("locationList", gson.toJson(locationService.getLocList()));
        model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
        model.addAttribute("dealerNameList", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("statusCdList", gson.toJson(statusCdManager.list()));
        model.addAttribute("selectedProductTypeList", gson.toJson(cardKPITargetByAmountEntity.getProductType()));
        model.addAttribute("selectedLocationList", gson.toJson(cardKPITargetByAmountEntity.getLocation()));
        model.addAttribute("selectedAgeCodeList", gson.toJson(cardKPITargetByAmountEntity.getAgeCode()));
        model.addAttribute("selectedDealerNameList", gson1.toJson(cardKPITargetByAmountEntity.getDealerName()));
        model.addAttribute("selectedStatusCdList", gson.toJson(cardKPITargetByAmountEntity.getStatusCd()));
        model.addAttribute("assetClassificationList", gson.toJson(assetClassificationLoanService.getAll()));
        model.addAttribute("selectedAssetClassificationList", gson.toJson(cardKPITargetByAmountEntity.getClStatus()));
        model.addAttribute("cardKPIByAmount", cardKPITargetByAmountEntity);
        return "collection/kpi/card/targetByAmount/create";
    }

    @GetMapping("view")
    public String viewPage(@RequestParam(value = "id") Long findId, Model model) {
        model.addAttribute("cardKPIByAmount", cardKPITargetByAmountService.getByid(findId));
        return "collection/kpi/card/targetByAmount/view";
    }
}
