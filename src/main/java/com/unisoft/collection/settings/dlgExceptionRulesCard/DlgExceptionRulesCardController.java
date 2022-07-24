package com.unisoft.collection.settings.dlgExceptionRulesCard;


import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.user.UserPrincipal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/collection/settings/dlgexceptionrulescard/")

public class DlgExceptionRulesCardController {

    private DlgExceptionRulesCardService dlgExceptionRulesCardService;
    private AgeCodeService ageCodeService;
    private CustomerBasicInfoService customerBasicInfoService;
    private ProductTypeService productTypeService;
    private ProductTypeRepository productTypeRepository;

    @GetMapping("create")
    public String addDlgExceptionRulesCard(Model model){

        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        DlgExceptionRulesCardInfo tempdlgExceptionRulesCardInfo= dlgExceptionRulesCardService.getDlgExceptionRulesCardInfo();
        if(tempdlgExceptionRulesCardInfo == null){
            model.addAttribute("productTypeList",gson.toJson(productTypeRepository.findAll()));
            model.addAttribute("customerIdList", gson1.toJson(customerBasicInfoService.getCustomerBasicInfoList()));
            model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
            model.addAttribute("dlgerc",new DlgExceptionRulesCardInfo());
        }
        else {
            model.addAttribute("productTypeList",gson.toJson(productTypeRepository.findAll()));
            model.addAttribute("customerIdList", gson1.toJson(customerBasicInfoService.getCustomerBasicInfoList()));
            model.addAttribute("ageCodeList", gson.toJson(ageCodeService.getAll()));
            model.addAttribute("selectedProductTypeList",gson.toJson(tempdlgExceptionRulesCardInfo.getProductType()));
            model.addAttribute("selectedCustomerIdList", gson1.toJson(tempdlgExceptionRulesCardInfo.getCustomerId()));
            model.addAttribute("selectedAgeCodeList", gson.toJson(tempdlgExceptionRulesCardInfo.getAgeCode()));
            model.addAttribute("dlgerc",tempdlgExceptionRulesCardInfo);
        }
        return "collection/settings/dlgexceptionrulescard/dlgexceptionrulescard";
    }

    @PostMapping(value = "create",consumes = (MediaType.APPLICATION_FORM_URLENCODED_VALUE))
    public String addNewDiv(DlgExceptionRulesCardInfo dlgExceptionRulesCardInfo)
    {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(dlgExceptionRulesCardInfo.getId() == null) {

            List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();
            List<CustomerBasicInfoEntity> customerBasicInfoEntities = new ArrayList<>();
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();

            for(String s: dlgExceptionRulesCardInfo.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity = ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            for(String s: dlgExceptionRulesCardInfo.getCustomerIdIds() ){
                CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoService.findById(new Long(s));
                customerBasicInfoEntities.add(customerBasicInfoEntity);
            }

            for(String s: dlgExceptionRulesCardInfo.getProductTypeIds()){
                ProductTypeEntity productTypeEntity = productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);

            }

            dlgExceptionRulesCardInfo.setProductType(productTypeEntities);
            dlgExceptionRulesCardInfo.setAgeCode(ageCodeEntities);
            dlgExceptionRulesCardInfo.setCustomerId(customerBasicInfoEntities);
            dlgExceptionRulesCardInfo.setCreatedBy(user.getUsername());
            dlgExceptionRulesCardInfo.setCreatedDate(new Date());
            boolean save= dlgExceptionRulesCardService.saveDlgExceptionRulesCard(dlgExceptionRulesCardInfo);
        }
        else {
            List<AgeCodeEntity> ageCodeEntities = new ArrayList<>();
            List<CustomerBasicInfoEntity> customerBasicInfoEntities = new ArrayList<>();
            List<ProductTypeEntity> productTypeEntities = new ArrayList<>();

            for(String s: dlgExceptionRulesCardInfo.getAgeCodeIds()){
                AgeCodeEntity ageCodeEntity = ageCodeService.getById(new Long(s));
                ageCodeEntities.add(ageCodeEntity);
            }

            for(String s: dlgExceptionRulesCardInfo.getCustomerIdIds() ){
                CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoService.findById(new Long(s));
                customerBasicInfoEntities.add(customerBasicInfoEntity);
            }

            for(String s: dlgExceptionRulesCardInfo.getProductTypeIds()){
                ProductTypeEntity productTypeEntity = productTypeService.getById(new Long(s));
                productTypeEntities.add(productTypeEntity);

            }

            dlgExceptionRulesCardInfo.setProductType(productTypeEntities);
            dlgExceptionRulesCardInfo.setAgeCode(ageCodeEntities);
            dlgExceptionRulesCardInfo.setCustomerId(customerBasicInfoEntities);
            dlgExceptionRulesCardInfo.setModifiedBy(user.getUsername());
            dlgExceptionRulesCardInfo.setModifiedDate(new Date());
            boolean update=dlgExceptionRulesCardService.updateDlgExceptionRulesCard(dlgExceptionRulesCardInfo);
        }
        return "redirect:/collection/settings/dlgexceptionrulescard/create";
    }
}
