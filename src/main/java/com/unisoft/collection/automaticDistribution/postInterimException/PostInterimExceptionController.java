package com.unisoft.collection.automaticDistribution.postInterimException;
/*
Created by   Islam at 8/21/2019
*/

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.user.UserPrincipal;
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
@RequestMapping("/collection/postinterim/exception/")
public class PostInterimExceptionController {

    @Autowired
    private PostInterimExceptionRepository postInterimExceptionRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;

    @GetMapping(value = "create")
    public String addpage(Model model) {
        List<PostInterimException> postInterimException = postInterimExceptionRepository.findAll();
        PostInterimException interimException = new PostInterimException();

        if (postInterimException.size() > 0) {
            interimException = postInterimException.get(0);
        }
        model.addAttribute("postpeople", interimException);
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
        List<String> plasticCd = new ArrayList<String>() {{
            add("Yes");
            add("No");
        }};

        model.addAttribute("samValues", samList);
        model.addAttribute("writeOffValues", writeOffList);
        model.addAttribute("vvipvalues", vvip);
        model.addAttribute("plasticValues", plasticCd);

        List<EmployeeInfoEntity> dealterList = employeeService.getDealerList();
        model.addAttribute("dealerOptons", gson1.toJson(dealterList));
        model.addAttribute("customerOptions",
                gson1.toJson(customerBasicInfoService.getCustomerBasicInfoList()));
        if (interimException.getId() != null) {
            model.addAttribute("selectedDealer",
                    gson1.toJson(interimException.getEmployeeInfoEntities()));

            List<CustomerBasicInfoEntity> customerBasicInfoEntityList =
                    interimException.getCustomerBasicInfoEntityList();

            model.addAttribute("selectedCustomer",
                    gson1.toJson(customerBasicInfoEntityList));
        }

        return "collection/automaticDistribution/postInterimException/create";
    }

    @PostMapping(value = "create")
    public String create(Model model, PostInterimException postInterimException) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<EmployeeInfoEntity> employeeInfoEntities = new ArrayList<>();
        List<CustomerBasicInfoEntity> customerBasicInfoEntities = new ArrayList<>();

        if (postInterimException.getId() == null) {
            for (String id : postInterimException.getCustomerIds()) {
                CustomerBasicInfoEntity customerInfoEntity = customerBasicInfoService.findById(Long.parseLong(id));
                customerBasicInfoEntities.add(customerInfoEntity);
            }

            postInterimException.setCustomerBasicInfoEntityList(customerBasicInfoEntities);

            for (String id : postInterimException.getEmployeeIds()) {
                EmployeeInfoEntity dealer = employeeService.getById(Long.parseLong(id));
                employeeInfoEntities.add(dealer);
            }

            postInterimException.setEmployeeInfoEntities(employeeInfoEntities);

            postInterimException.setCreatedBy(user.getUsername());
            postInterimException.setCreatedDate(new Date());
            postInterimExceptionRepository.save(postInterimException);
        } else {
            for (String id : postInterimException.getCustomerIds()) {
                CustomerBasicInfoEntity customerInfoEntity = customerBasicInfoService.findById(Long.parseLong(id));
                customerBasicInfoEntities.add(customerInfoEntity);
            }

            postInterimException.setCustomerBasicInfoEntityList(customerBasicInfoEntities);

            for (String id : postInterimException.getEmployeeIds()) {
                EmployeeInfoEntity dealer = employeeService.getById(Long.parseLong(id));
                employeeInfoEntities.add(dealer);
            }

            postInterimException.setEmployeeInfoEntities(employeeInfoEntities);
            postInterimException.setModifiedBy(user.getUsername());
            postInterimException.setModifiedDate(new Date());
            postInterimExceptionRepository.save(postInterimException);
        }
        return "redirect:/collection/postinterim/exception/create";
    }
}
