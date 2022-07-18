package com.csinfotechbd.collection.samd.setup.borrowerPresentStatus;


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
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
@RequestMapping("/collection/samd/setup/borrower-present-status")
public class BorrowerPresentStatusController {

    @Autowired
    private BorrowerPresentStatusService borrowerPresentStatusService;

    @Autowired
    private BorrowerPresentStatusRepository borrowerPresentStatusRepository;

    @Autowired
    private AuditTrailService auditTrailService;


    @GetMapping("/list")
    public String borrowerPresentStatusList(Model model){
        List<BorrowerPresentStatus> borrowerPresentStatusList = borrowerPresentStatusService.findAll();
        model.addAttribute("borrowerPresentStatusList", borrowerPresentStatusList);
        return "samd/setup/borrowerPresentStatus/list";
    }


    @GetMapping("/create")
    public String borrowerPresentStatusView(Model model){
        return prepareEditViewModel(model, new BorrowerPresentStatus());
    }


    @PostMapping("/create")
    public String createBorrowerPresentStatus(Model model, @Valid BorrowerPresentStatus borrowerPresentStatus, BindingResult result){
//        if (!result.hasErrors()){
//            borrowerPresentStatusService.save(borrowerPresentStatus);
//            model.addAttribute("success", true);
//            return "redirect:/collection/samd/setup/borrower-present-status/list";
//        }
//        model.addAttribute("error", true);
//        return prepareEditViewModel(model, borrowerPresentStatus);

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(borrowerPresentStatus.getId()==null){
            borrowerPresentStatus.setCreatedBy(user.getUsername());
            borrowerPresentStatus.setCreatedDate(new Date());
            borrowerPresentStatusService.save(borrowerPresentStatus);
            auditTrailService.saveCreatedData("Borrower Present Status", borrowerPresentStatus);
        }else{
            BorrowerPresentStatus oldEntity = borrowerPresentStatusRepository.getOne(borrowerPresentStatus.getId());
            BorrowerPresentStatus previouseEntity = new BorrowerPresentStatus();
            BeanUtils.copyProperties(oldEntity,previouseEntity);

            borrowerPresentStatus.setModifiedBy(user.getUsername());
            borrowerPresentStatus.setModifiedDate(new Date());
            borrowerPresentStatusRepository.save(borrowerPresentStatus);
            auditTrailService.saveUpdatedData("Borrower Present Status",previouseEntity,borrowerPresentStatus);
        }

        return "redirect:/collection/samd/setup/borrower-present-status/list";
    }



    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id){
        BorrowerPresentStatus borrowerPresentStatus = borrowerPresentStatusService.findBorrowerPresentStatusById(id);
        return prepareEditViewModel(model, borrowerPresentStatus);
    }



    private String prepareEditViewModel(Model model, BorrowerPresentStatus borrowerPresentStatus) {
        model.addAttribute("borrowerPresentStatus", borrowerPresentStatus);
        return "samd/setup/borrowerPresentStatus/create";
    }
}
