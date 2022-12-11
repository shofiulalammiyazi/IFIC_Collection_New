package com.unisoft.collection.settings.ivrSetupAndConfig;


import com.unisoft.collection.samd.setup.riskCategories.RiskCategory;
import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/collection/settings/ivr-setup-Config")
public class IVRController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private IVRRepository ivrRepository;

    @Autowired
    private IVRService ivrService;


    @GetMapping("/list")
    public String showList(Model model) {

        model.addAttribute("ivrEntityList",ivrRepository.findAll());

        return "collection/settings/ivrSetup/list";
    }


    @GetMapping("/create")
    public String createPage(Model model) {
        return createUpdateIvr(new IvrEntity(), model);
    }

    @PostMapping("/create")
    public String create(IvrEntity ivrEntity, Model model, BindingResult result) {

        ivrRepository.save(ivrEntity);

        return createUpdateIvr(ivrEntity, model);
    }

    @GetMapping("/edit")
    public String editView(Model model, @RequestParam Long id) {
        IvrEntity ivrEntity = ivrRepository.getOne(id);
        List<String> dealerPinList = employeeService.getDealerList().stream().map(t -> t.getPin()).collect(Collectors.toList());
        model.addAttribute("ivrEntity", ivrEntity);
        model.addAttribute("dealerPinList", dealerPinList);

        return "collection/settings/ivrSetup/create";
    }

    @GetMapping("/call")
    @ResponseBody
    public String call(@RequestParam String mobileNo){
        String res = "400";
        try {
            res = ivrService.call(mobileNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private String createUpdateIvr(IvrEntity ivrEntity, Model model) {

        List<String> dealerPinList = employeeService.getDealerList().stream().map(t -> t.getPin()).collect(Collectors.toList());
        model.addAttribute("ivrEntity", ivrEntity);
        model.addAttribute("dealerPinList", dealerPinList);
        return "collection/settings/ivrSetup/create";
    }

}
