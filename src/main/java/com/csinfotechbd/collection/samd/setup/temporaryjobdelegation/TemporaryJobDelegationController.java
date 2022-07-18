package com.csinfotechbd.collection.samd.setup.temporaryjobdelegation;


import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoDto;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/collection/samd/setup/temporary-job-delegation")
public class TemporaryJobDelegationController {


    @Autowired
    private TemporaryJobDelegationService temporaryJobDelegationService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/show-page")
    public String showTemporaryJobDelegationPage(Model model) {
        return prepareEditPageModel(new TemporaryJobDelegation(), model);
    }


    @PostMapping("/create")
    public String createTemporaryJobDelegation(Model model, @Valid TemporaryJobDelegation temporaryJobDelegation,
                                               BindingResult result) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!result.hasErrors()) {
            temporaryJobDelegationService.saveTemporaryJobDelegation(temporaryJobDelegation,userPrincipal);
            model.addAttribute("done", "Success.");
            return "redirect:/collection/samd/setup/temporary-job-delegation/list";
        }

        return prepareEditPageModel(new TemporaryJobDelegation(), model);
    }


    @GetMapping("/list")
    public String temporaryJobDelegationList(Model model){
        model.addAttribute("temporaryJobDelegationList", temporaryJobDelegationService.findAllTemporaryJobDelegation());
        return "samd/setup/temporary_job_delegation/list";
    }


    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id") Long id, Model model){
        TemporaryJobDelegation temporaryJobDelegation = temporaryJobDelegationService.findById(id);
        return prepareEditPageModel(temporaryJobDelegation,model);
    }


    private String prepareEditPageModel(TemporaryJobDelegation temporaryJobDelegation, Model model) {

        List<EmployeeInfoDto> selectedToUsers = temporaryJobDelegation.getToUser()
                .stream().map(EmployeeInfoDto::new).collect(Collectors.toList());

        List<EmployeeInfoEntity> employeeDealerList = employeeService.getDealerList();
        List<EmployeeInfoDto> dealerList = employeeDealerList
                .stream().map(EmployeeInfoDto::new).collect(Collectors.toList());

        EmployeeInfoEntity employeeInfoEntity = temporaryJobDelegation.getFromUser();

        EmployeeInfoDto selectedFromUser = new EmployeeInfoDto(employeeInfoEntity);

        model.addAttribute("dealerList", dealerList);
        model.addAttribute("temporaryJobDelegation", temporaryJobDelegation);
        model.addAttribute("selectedToUsers", selectedToUsers);
        model.addAttribute("selectedFromUser", selectedFromUser);
        model.addAttribute("enumList",TemporaryJobDelegationStatus.values());
        return "samd/setup/temporary_job_delegation/create";
    }
}
