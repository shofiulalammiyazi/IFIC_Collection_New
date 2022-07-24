package com.unisoft.collection.samd.setup.logicInTerms;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/collection/samd/setup/logic-in-terms")
public class LogicInTermsController {


    @Autowired
    private LogicInTermsService logicInTermsService;

    @Autowired
    private LogicInTermsRepository logicInTermsRepository;

    @Autowired
    private AuditTrailService auditTrailService;


    @GetMapping("/list")
    public String list(Model model){
        List<LogicInTerms> logicInTermsList = logicInTermsService.findAll();
        model.addAttribute("logicInTermsList", logicInTermsList);
        return "samd/setup/logicInTerms/list";
    }


    @GetMapping("/create")
    public String createLogicIntermsView(Model model){
        return prepareEditModelView(model, new LogicInTerms());
    }


    @PostMapping("/create")
    public String createLogicInterms(Model model, @Valid LogicInTerms logicInTerms, BindingResult result){
//        if (!result.hasErrors()){
//            logicInTermsService.save(logicInTerms);
//            model.addAttribute("success", true);
//            return "redirect:/collection/samd/setup/logic-in-terms/list";
//        }
//        model.addAttribute("error", true);
//        return prepareEditModelView(model, logicInTerms);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(logicInTerms.getId()==null){
            logicInTerms.setCreatedBy(user.getUsername());
            logicInTerms.setCreatedDate(new Date());
            logicInTermsService.save(logicInTerms);
            auditTrailService.saveCreatedData("Logic in Terms", logicInTerms);
        }else{
            LogicInTerms oldEntity = logicInTermsRepository.getOne(logicInTerms.getId());
            LogicInTerms previousEntity = new LogicInTerms();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            logicInTerms.setModifiedBy(user.getUsername());
            logicInTerms.setModifiedDate(new Date());
            logicInTermsRepository.save(logicInTerms);
            auditTrailService.saveUpdatedData("Logic in Terms",previousEntity,logicInTerms);
        }

        return "redirect:/collection/samd/setup/logic-in-terms/list";

    }



    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id){
        LogicInTerms logicInTerms = logicInTermsService.findLogicInTermsById(id);
        return prepareEditModelView(model, logicInTerms);
    }


    private String prepareEditModelView(Model model, LogicInTerms logicInTerms) {
        model.addAttribute("logicInTerms", logicInTerms);
        return "samd/setup/logicInTerms/create";
    }
}
