package com.unisoft.collection.samd.setup.approvalLevel;

import com.unisoft.common.CommonController;
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
@RequestMapping("/collection/samd/setup/approval-level")
public class ApprovalLevelController implements CommonController<ApprovalLevel> {

    private final ApprovalLevelService approvalLevelService;

    @Override
    @GetMapping("/create")
    public String create(Model model) {
        Map<String, Object> response = new HashMap<>();
        response.put("outcome", "success");

        model.addAttribute("response", response);
        model.addAttribute("level", new ApprovalLevel());

        return "samd/setup/approvalLevel/create";
    }

    @Override
    @PostMapping("/create")
    public String create(Model model, ApprovalLevel entity) {
        Map<String, Object> response = approvalLevelService.storeData(entity);
        if (response.get("outcome").toString().equals("success"))
            return "redirect:/collection/samd/setup/approval-level/list";
        else {
            model.addAttribute("response", response);
            model.addAttribute("level", entity);
            return "samd/setup/approvalLevel/create";
        }
    }

    @GetMapping("/edit")
    public String edit(Model model, Long id) {
        Map<String, Object> response = new HashMap<>();
        ApprovalLevel level = approvalLevelService.findDataById(id);
        response.put("outcome", "success");

        model.addAttribute("response", response);
        model.addAttribute("level", level);

        return "samd/setup/approvalLevel/create";
    }


    @Override
    public String view(Model model, Long id) {

        return null;
    }


    @Override
    @GetMapping("/list")
    public String list(Model model) {
        List<ApprovalLevel> levels = approvalLevelService.findAllData();
        model.addAttribute("levels", levels);
        return "samd/setup/approvalLevel/list";
    }

    @Override
    public String delete() {
        return null;
    }
}
