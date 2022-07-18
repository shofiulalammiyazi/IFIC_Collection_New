package com.csinfotechbd.collection.automaticDistribution.distributionExceptionCard;
/*
Created by Monirul Islam at 8/22/2019
*/

import com.csinfotechbd.collection.automaticDistribution.distributionExceptionCardModel.ProductGroupAgeCode;
import com.csinfotechbd.collection.samd.setup.riskCategories.RiskCategory;
import com.csinfotechbd.collection.samd.setup.riskCategories.RiskCategoryServiceImpl;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.collection.settings.productGroup.ProductGroupService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoService;
import com.csinfotechbd.user.UserPrincipal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/collection/distribution/exception/")
public class DistributionExceptionController {

    @Autowired
    private DistributionExceptionRepository distributionExceptionRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;

    @Autowired
    private AgeCodeService ageCodeService;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private RiskCategoryServiceImpl riskCategoryService;

    @GetMapping(value = "create")
    public String addpage(Model model) {

        List<DistributionException> postInterimException = distributionExceptionRepository.findAll();
        DistributionException distributionException = new DistributionException();

        if (postInterimException.size() == 0) distributionException.getProductGroupAgeCodes().add(new ProductGroupAgeCode());

        if (postInterimException.size() > 0) {
            distributionException = postInterimException.get(0);

            int size = distributionException.getProductGroupAgeCodes().size();
            if (size == 0) distributionException.getProductGroupAgeCodes().add(new ProductGroupAgeCode());
            else
                model.addAttribute("length", size - 1);
        }
        model.addAttribute("postpeople", distributionException);

        Gson gson = new Gson();
        Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        List<String> samList = new ArrayList<String>() {{
            add("Yes");
            add("No");
        }};
        List<String> writeOffList = new ArrayList<String>() {{
            add("Yes");
            add("No");
        }};
        List<String> vvip = new ArrayList<String>() {{
            add("Yes");
            add("No");
        }};
        List<String> secureCard = new ArrayList<String>() {{
            add("Yes");
            add("No");
        }};

        List<String> isMultiProductDistribute = new ArrayList<String>() {{
            add("Yes");
            add("No");
        }};


        model.addAttribute("samValues", samList);
        model.addAttribute("writeOffValues", writeOffList);
        model.addAttribute("vvipvalues", vvip);
        model.addAttribute("secureCardValues", secureCard);
        model.addAttribute("isMultiProducts", isMultiProductDistribute);

        model.addAttribute("riskCategoryOption", gson.toJson(riskCategoryService.findByAllCard("Card")));
        model.addAttribute("dealerOptons", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("customerOptions", gson.toJson(customerBasicInfoService.getCustomerBasicInfoList()));


        /// Need to fix code here
        model.addAttribute("productOptions", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Card")));
        model.addAttribute("productOptionsAnd", productTypeRepository.findByProductGroupEntityCardAccount("Card"));

        model.addAttribute("ageCodeOptions", ageCodeService.getAll());

        if (distributionException.getId() != null) {
            model.addAttribute("selectedDealer", gson.toJson(distributionException.getEmployeeInfos()));
            model.addAttribute("selectedRiskCategory", gson.toJson(distributionException.getRiskCategoryList()));
            model.addAttribute("selectedCustomer", gson.toJson(distributionException.getCustomerList()));
            model.addAttribute("selectedProductOptions", gson.toJson(distributionException.getProductsList()));
        }

        return "collection/automaticDistribution/distributionExceptionCard/create";
    }

    @PostMapping(value = "create")
    public String save(Model model, DistributionException distributionException) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<EmployeeInfoEntity> delaerList = new ArrayList<>();
        List<RiskCategory> riskCategoryList = new ArrayList<>();
        List<CustomerBasicInfoEntity> customerBasicInfoEntities = new ArrayList<>();
        List<ProductTypeEntity> productGroupEntities = new ArrayList<>();

        if (distributionException.getId() == null) {
            for (String id : distributionException.getCustomerIds()) {
                CustomerBasicInfoEntity customerInfoEntity = customerBasicInfoService.findById(Long.parseLong(id));
                customerBasicInfoEntities.add(customerInfoEntity);
            }

            distributionException.setCustomerList(customerBasicInfoEntities);

            for (String id : distributionException.getDealerIds()) {
                EmployeeInfoEntity dealer = employeeService.getById(Long.parseLong(id));
                delaerList.add(dealer);
            }

            distributionException.setEmployeeInfos(delaerList);

            for (String id : distributionException.getRiskCategoryIds()) {
                RiskCategory riskCategory = riskCategoryService.getById(Long.parseLong(id));
                riskCategoryList.add(riskCategory);
            }

            distributionException.setRiskCategoryList(riskCategoryList);

            for (String id : distributionException.getProductIds()) {
                ProductTypeEntity product = productTypeRepository.findById(Long.parseLong(id)).get();
                productGroupEntities.add(product);
            }

            distributionException.setProductsList(productGroupEntities);

            distributionException.setCreatedBy(user.getUsername());
            distributionException.setCreatedDate(new Date());
            distributionExceptionRepository.save(distributionException);
        } else {
            for (String id : distributionException.getCustomerIds()) {
                CustomerBasicInfoEntity customerInfoEntity = customerBasicInfoService.findById(Long.parseLong(id));
                customerBasicInfoEntities.add(customerInfoEntity);
            }

            distributionException.setCustomerList(customerBasicInfoEntities);

            for (String id : distributionException.getDealerIds()) {
                EmployeeInfoEntity dealer = employeeService.getById(Long.parseLong(id));
                delaerList.add(dealer);
            }

            distributionException.setEmployeeInfos(delaerList);

            for (String id : distributionException.getRiskCategoryIds()) {
                RiskCategory riskCategory = riskCategoryService.getById(Long.parseLong(id));
                riskCategoryList.add(riskCategory);
            }

            distributionException.setRiskCategoryList(riskCategoryList);

            for (String id : distributionException.getProductIds()) {
                ProductTypeEntity product = productTypeRepository.findById(Long.parseLong(id)).get();
                productGroupEntities.add(product);
            }

            distributionException.setProductsList(productGroupEntities);
            distributionException.setModifiedBy(user.getUsername());
            distributionException.setModifiedDate(new Date());
            distributionExceptionRepository.save(distributionException);
        }
        return "redirect:/collection/distribution/exception/create";
    }
}
