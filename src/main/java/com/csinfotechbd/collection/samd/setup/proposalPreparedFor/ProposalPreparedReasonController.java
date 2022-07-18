package com.csinfotechbd.collection.samd.setup.proposalPreparedFor;

import com.csinfotechbd.common.CommonController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/samd/setup/proposal-prepared-reason")
public class ProposalPreparedReasonController implements CommonController<ProposalPreparedReason> {

    private final ProposalPreparedReasonService proposalPreparedReasonService;

    @Override
    @GetMapping("/create")
    public String create(Model model) {
        Map<String, Object> response = new HashMap<>();
        response.put("outcome", "success");

        model.addAttribute("response", response);
        model.addAttribute("reason", new ProposalPreparedReason());

        return "samd/setup/proposalPreparedFor/create";
    }

    @Override
    @PostMapping("/create")
    public String create(Model model, ProposalPreparedReason entity) {
        Map<String, Object> response = proposalPreparedReasonService.storeData(entity);
        if (response.get("outcome").toString().equals("success"))
            return "redirect:/collection/samd/setup/proposal-prepared-reason/list";
        else {
            model.addAttribute("response", response);
            model.addAttribute("reason", entity);
            return "samd/setup/proposalPreparedFor/create";
        }
    }

    @GetMapping("/edit")
    public String edit(Model model, Long id) {
        Map<String, Object> response = new HashMap<>();
        ProposalPreparedReason reason = proposalPreparedReasonService.findDataById(id);
        response.put("outcome", "success");

        model.addAttribute("response", response);
        model.addAttribute("reason", reason);

        return "samd/setup/proposalPreparedFor/create";
    }

    @Override
    public String view(Model model, Long id) {
        return null;
    }

    @Override
    @GetMapping("/list")
    public String list(Model model) {
        List<ProposalPreparedReason> reasons = proposalPreparedReasonService.findAllData();
        model.addAttribute("reasons", reasons);
        return "samd/setup/proposalPreparedFor/list";
    }

    @Override
    public String delete() {
        return null;
    }
}
