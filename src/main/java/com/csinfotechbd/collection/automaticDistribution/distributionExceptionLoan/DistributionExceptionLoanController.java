package com.csinfotechbd.collection.automaticDistribution.distributionExceptionLoan;
/*
Created by Monirul Islam at 9/4/2019
*/


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.automaticDistribution.distributionExceptionLoanModel.ProductGroupDpdBucket;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;
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
@RequestMapping(value = "/collection/distribution/loan/exception/")
public class DistributionExceptionLoanController {

    @Autowired
    private DistributionExceptionLoanRepository distributionExceptionLoanRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private DPDBucketService dpdBucketService;
    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "create")
    public String addpage(Model model) {

        List<DistributionExceptionLoan> postInterimException = distributionExceptionLoanRepository.findAll();
        DistributionExceptionLoan distributionExceptionLoan = new DistributionExceptionLoan();
        distributionExceptionLoan.setInterim("Yes");

        if (postInterimException.size() == 0) distributionExceptionLoan.getProductGroupDpdBuckets().add(new ProductGroupDpdBucket());

        if (postInterimException.size() > 0) {
            distributionExceptionLoan = postInterimException.get(0);

            int size = distributionExceptionLoan.getProductGroupDpdBuckets().size();
            if (size == 0) distributionExceptionLoan.getProductGroupDpdBuckets().add(new ProductGroupDpdBucket());
            else
                model.addAttribute("length", size - 1);
        }
        model.addAttribute("postpeople", distributionExceptionLoan);

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
        List<String> interim = new ArrayList<String>() {{
            add("Yes");
        }};

        model.addAttribute("samValues", samList);
        model.addAttribute("writeOffValues", writeOffList);
        model.addAttribute("interimvalues", interim);

        model.addAttribute("dealerOptons", gson1.toJson(employeeService.getDealerList()));
        model.addAttribute("productOptions", gson.toJson(productTypeRepository.findByProductGroupEntityCardAccount("Loan")));
        model.addAttribute("productOptionsAnd", productTypeRepository.findByProductGroupEntityCardAccount("Loan"));
        model.addAttribute("dpdBucketOptions", dpdBucketService.getAll());


        if (distributionExceptionLoan.getId() != null) {
            model.addAttribute("selectedDealer", gson1.toJson(distributionExceptionLoan.getEmployeeInfos()));
            model.addAttribute("selectedProductOptions", gson.toJson(distributionExceptionLoan.getProductsList()));
        }
        return "collection/automaticDistribution/distributionExceptionLoan/create";
    }

    @PostMapping(value = "create")
    public String save(Model model, DistributionExceptionLoan distributionExceptionLoan) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<EmployeeInfoEntity> delaerList = new ArrayList<>();
        List<ProductTypeEntity> productGroupEntities = new ArrayList<>();

        List<ProductTypeEntity> productGroupEntitiesAnd = new ArrayList<>();
        List<DPDBucketEntity> dpdBucketEntities = new ArrayList<>();
        DistributionExceptionLoan previous = new DistributionExceptionLoan();

        if(distributionExceptionLoan.getId()==null)
        {
            for(ProductGroupDpdBucket p: distributionExceptionLoan.getProductGroupDpdBuckets()){
                if(p.getDpdBucket() == null || p.getProductGroup() == null) distributionExceptionLoan.getProductGroupDpdBuckets().remove(p);
            }

            for (String id : distributionExceptionLoan.getDealerIds()) {
                EmployeeInfoEntity dealer = employeeService.getById(Long.parseLong(id));
                delaerList.add(dealer);
            }

            distributionExceptionLoan.setEmployeeInfos(delaerList);

            for (String id : distributionExceptionLoan.getProductIds()) {
                ProductTypeEntity product = productTypeRepository.findById(Long.parseLong(id)).get();
                productGroupEntities.add(product);
            }

            distributionExceptionLoan.setProductsList(productGroupEntities);

            distributionExceptionLoan.setCreatedBy(user.getUsername());
            distributionExceptionLoan.setCreatedDate(new Date());
            distributionExceptionLoanRepository.save(distributionExceptionLoan);
            auditTrailService.saveCreatedData("Allocated Account List (Loan)", distributionExceptionLoan);
        }else {

            for (String id : distributionExceptionLoan.getDealerIds()) {
                EmployeeInfoEntity dealer = employeeService.getById(Long.parseLong(id));
                delaerList.add(dealer);
            }

            distributionExceptionLoan.setEmployeeInfos(delaerList);

            for (String id : distributionExceptionLoan.getProductIds()) {
                ProductTypeEntity product = productTypeRepository.findById(Long.parseLong(id)).get();
                productGroupEntities.add(product);
            }

            distributionExceptionLoan.setProductsList(productGroupEntities);

            distributionExceptionLoan.setModifiedBy(user.getUsername());
            distributionExceptionLoan.setModifiedDate(new Date());
            distributionExceptionLoanRepository.save(distributionExceptionLoan);
            auditTrailService.saveUpdatedData("Allocated Account List (Loan)", previous,distributionExceptionLoan);

        }
        return "redirect:/collection/distribution/loan/exception/create";
    }
}
