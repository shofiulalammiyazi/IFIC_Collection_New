package com.unisoft.collection.samd.setup.proposalStatus;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/collection/samd/setup/proposal-status")
public class ProposalStatusController {

    @Autowired
    private ProposalStatusService proposalStatusService;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private ProposalStatusRepository proposalStatusRepository;


    @GetMapping("/list")
    public String getProposalList(Model model){
        model.addAttribute("proposalList", proposalStatusService.getAllProposalStatus());
        return "samd/setup/proposalStatus/list";
    }


    @GetMapping("/save")
    public String proposalStatusView(Model model){
        return prepareEditPageModel(new ProposalStatus(), model);
    }




    @PostMapping("/save")
    public String crateProposalStatus(Model model, @Valid ProposalStatus proposalStatus, BindingResult result){
//        if (!result.hasErrors()){
//            ProposalStatus proposalStatus1 = proposalStatusService.save(proposalStatus);
//            model.addAttribute("success", true);
//            return "redirect:/collection/samd/setup/proposal-status/list";
//        }else {
//            model.addAttribute("error", true);
//        }

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(proposalStatus.getId()==null){
            proposalStatus.setCreatedBy(user.getUsername());
            proposalStatus.setCreatedDate(new Date());
            proposalStatusService.save(proposalStatus);
            auditTrailService.saveCreatedData("Proposal Status", proposalStatus);
        }else {
            ProposalStatus oldEntity = proposalStatusRepository.getOne(proposalStatus.getId());
            ProposalStatus previewEntity = new ProposalStatus();
            BeanUtils.copyProperties(oldEntity,previewEntity);

            proposalStatus.setModifiedBy(user.getUsername());
            proposalStatus.setModifiedDate(new Date());
            proposalStatusRepository.save(proposalStatus);
            auditTrailService.saveUpdatedData("Proposal Status", previewEntity, proposalStatus);
        }

            return "redirect:/collection/samd/setup/proposal-status/list";

//        return prepareEditPageModel(proposalStatus,model);
    }


    @GetMapping("/edit")
    public String editProposalStatus(Model model,@RequestParam Long id){
        ProposalStatus proposalStatus = proposalStatusService.findProposalStatusById(id);
        model.addAttribute("proposalStatus",proposalStatus);
        return "samd/setup/proposalStatus/create";
    }



    private String prepareEditPageModel(ProposalStatus proposalStatus, Model model) {
        model.addAttribute("proposalSatus", proposalStatus);
        return "samd/setup/proposalStatus/create";
    }

}
