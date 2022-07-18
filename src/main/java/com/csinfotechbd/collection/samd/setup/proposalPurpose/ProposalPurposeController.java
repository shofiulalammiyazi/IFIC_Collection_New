package com.csinfotechbd.collection.samd.setup.proposalPurpose;

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
@RequestMapping("/collection/samd/setup/proposal-purpose")
public class ProposalPurposeController implements CommonController<ProposalPurpose> {

    private final ProposalPurposeService proposalPurposeService;

    @Override
    @GetMapping("/create")
    public String create(Model model) {
        Map<String, Object> response = new HashMap<>();
        response.put("outcome", "success");

        model.addAttribute("response", response);
        model.addAttribute("purpose", new ProposalPurpose());

        return "samd/setup/proposalPurpose/create";
    }

    @Override
    @PostMapping("/create")
    public String create(Model model, ProposalPurpose entity) {
        Map<String, Object> response = proposalPurposeService.storeData(entity);
        if (response.get("outcome").toString().equals("success"))
            return "redirect:/collection/samd/setup/proposal-purpose/list";
        else {
            model.addAttribute("response", response);
            model.addAttribute("purpose", entity);
            return "samd/setup/proposalPurpose/create";
        }
    }

    @GetMapping("/edit")
    public String edit(Model model, Long id) {
        Map<String, Object> response = new HashMap<>();
        ProposalPurpose purpose = proposalPurposeService.findDataById(id);
        response.put("outcome", "success");

        model.addAttribute("response", response);
        model.addAttribute("purpose", purpose);

        return "samd/setup/proposalPurpose/create";
    }

    @Override
    public String view(Model model, Long id) {
        return null;
    }

    @Override
    @GetMapping("/list")
    public String list(Model model) {
        List<ProposalPurpose> purposes = proposalPurposeService.findAllData();
        model.addAttribute("purposes", purposes);
        return "samd/setup/proposalPurpose/list";
    }

    @Override
    public String delete() {
        return null;
    }
}
