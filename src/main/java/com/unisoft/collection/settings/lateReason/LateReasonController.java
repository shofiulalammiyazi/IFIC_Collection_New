package com.unisoft.collection.settings.lateReason;

/*
Created by   Islam on 7/6/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeRepository;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainInfo;
import com.unisoft.collection.settings.lateReasonExplain.LateReasonExplainRepository;
import com.unisoft.user.User;
import com.unisoft.user.UserDao;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/collection/lateReason/")
public class LateReasonController {

    @Autowired
    private LateReasonService lateReasonService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private LateReasonExplainRepository lateReasonExplainRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        model.addAttribute("reasonList",lateReasonService.getAll());
        return "collection/settings/lateReason/reason";
    }

    @PostMapping(value = "create")
    public String add(LateReasonEntity  reason) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(reason.getId() == null)
        {
            reason.setCreatedBy(user.getUsername());
            reason.setCreatedDate(new Date());
            boolean save=lateReasonService.saveLateReas(reason);
            auditTrailService.saveCreatedData("Late Reason", reason);
        }else {
            LateReasonEntity oldEntity = lateReasonService.getById(reason.getId());
            LateReasonEntity previousEntity = new LateReasonEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            reason.setModifiedBy(user.getUsername());
            reason.setModifiedDate(new Date());
            boolean update=lateReasonService.updateReas(reason);
            auditTrailService.saveUpdatedData("Late Reason", previousEntity, reason);
        }
        return "redirect:/collection/lateReason/list";
    }

    @GetMapping(value = "create")
    public String addpage(Model model) {
        model.addAttribute("reason",new LateReasonEntity());
        return "collection/settings/lateReason/create";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id,Model model) {
        model.addAttribute("reason",lateReasonService.getById(id));
        return "collection/settings/lateReason/create";
    }

    @GetMapping(value = "latereason")
    public String add(@RequestParam(value = "reason", required = false) String reason) {

        if(reason == null || reason.isEmpty()){
            return "redirect:/";
        }
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findUserByUsername(userPrincipal.getUsername());

        Date loginTime = null;
        try {
            loginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(LocalDate.now().toString()+ " 06:00 AM");
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        LateReasonExplainInfo lateReasonExplainInfo = lateReasonExplainRepository.findByUserAndCreatedDateGreaterThan(user, loginTime);
        if(lateReasonExplainInfo != null){
            lateReasonExplainInfo.setLogoutReason(reason);
            lateReasonExplainRepository.save(lateReasonExplainInfo);
        }
        return "redirect:/";
    }

    @GetMapping(value = "loginreason")
    @ResponseBody
    public boolean addLoginReason(@RequestParam(value = "lan") String lan) {
        EmployeeInfoEntity byPin = employeeRepository.findByPin(lan);

        Date loginTime = null;
        try {
            loginTime = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(LocalDate.now().toString()+ " 06:00 AM");
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        LateReasonExplainInfo lateReasonExplainInfo = lateReasonExplainRepository.findByUserAndCreatedDateGreaterThan(byPin.getUser(), loginTime);
        return lateReasonExplainInfo != null ? true : false;
    }

}
