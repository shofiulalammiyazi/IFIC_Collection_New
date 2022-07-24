package com.unisoft.collection.samd.setup.proposedRescheduleTimes;


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

@Controller
@RequestMapping("/collection/samd/setup/proposed-reschedule-times")
public class ProposedRescheduleTimesController {



    @Autowired
    private ProposedRescheduleTimesService proposedRescheduleTimesService;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private ProposedRescheduleTimesRepository proposedRescheduleTimesRepository;



    @GetMapping("/list")
    public String getProposedRescheduleList(Model model){
        model.addAttribute("rescheduleTimesList", proposedRescheduleTimesService.getAll());
        return "samd/setup/proposalRescheduleTimes/list";
    }


    @GetMapping("/save")
    public String ProposedRescheduleTimesView(Model model){
        return prepareEditViewModel(new ProposedRescheduleTimes(), model);
    }

    @PostMapping("/save")
    public String createProposedRescheduleTimes(Model model, @Valid ProposedRescheduleTimes proposedRescheduleTimes, BindingResult result){
//        if (!result.hasErrors()){
//            ProposedRescheduleTimes times = proposedRescheduleTimesService.save(proposedRescheduleTimes);
//            model.addAttribute("success", true);
//            return "redirect:/collection/samd/setup/proposed-reschedule-times/list";
//        }else {
//            model.addAttribute("error", true);
//        }
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(proposedRescheduleTimes.getId()==null){
            proposedRescheduleTimes.setCreatedBy(user.getUsername());
            proposedRescheduleTimes.setCreatedDate(new Date());
            proposedRescheduleTimesService.save(proposedRescheduleTimes);
            auditTrailService.saveCreatedData("Proposed Reschedule Times", proposedRescheduleTimes);
        }else {
            ProposedRescheduleTimes oldEntity = proposedRescheduleTimesRepository.getOne(proposedRescheduleTimes.getId());
            ProposedRescheduleTimes previewEntity = new ProposedRescheduleTimes();
            BeanUtils.copyProperties(oldEntity,previewEntity);

            proposedRescheduleTimes.setModifiedBy(user.getUsername());
            proposedRescheduleTimes.setModifiedDate(new Date());
            proposedRescheduleTimesRepository.save(proposedRescheduleTimes);
            auditTrailService.saveUpdatedData("Proposed Reschedule Times", previewEntity, proposedRescheduleTimes);
        }

        return "redirect:/collection/samd/setup/proposed-reschedule-times/list";

//        return prepareEditViewModel(proposedRescheduleTimes, model);
    }

    @GetMapping("/edit")
    public String editProposedTimes(@RequestParam Long id, Model model){
        ProposedRescheduleTimes proposedRescheduleTimes = proposedRescheduleTimesService.findProposedRescheduleTimesById(id);
        return prepareEditViewModel(proposedRescheduleTimes, model);
    }

    private String prepareEditViewModel(ProposedRescheduleTimes proposedRescheduleTimes, Model model) {
        model.addAttribute("proposedRescheduleTimes", proposedRescheduleTimes);
        return "samd/setup/proposalRescheduleTimes/create";
    }
}
