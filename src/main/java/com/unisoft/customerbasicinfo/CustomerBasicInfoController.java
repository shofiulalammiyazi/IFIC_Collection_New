package com.unisoft.customerbasicinfo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customerbasicinfo/")
public class CustomerBasicInfoController {

    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;

    @GetMapping("list")
    public String viewList(Model model) {
        model.addAttribute("customerbasicinfolist", customerBasicInfoService.getCustomerBasicInfoList());

        return "collection/customerBasicInfo/customerbasicinfo";
    }

    @GetMapping("create")
    public String addList(Model model) {
        model.addAttribute("customerbasicinfo", new CustomerBasicInfoEntity());

        return "collection/customerBasicInfo/create";
    }

    @PostMapping(value = "create", consumes = (MediaType.APPLICATION_FORM_URLENCODED_VALUE))
    public String createCustomerBasicInfo(CustomerBasicInfoEntity customerBasicInfoEntity) {

        if (customerBasicInfoEntity.getId() == null) {
            customerBasicInfoService.saveOrUpdate(customerBasicInfoEntity);
        }
        return "redirect:/customerbasicinfo/list";
    }

    @GetMapping("/update-cus")
    public void upCus() {
        customerBasicInfoService.updateCustomer();
    }
}
