package com.csinfotechbd.collection.samd.dataEntry.writtenOffExecution;

import com.csinfotechbd.common.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/collection/samd/written-off-execution")
public class SamWrittenOffExecutionController implements CommonController<SamWrittenOffExecution> {

    @Autowired
    private SamWrittenOffExecutionService samWrittenOffExecutionService;

    @Override
    @GetMapping("/create")
    public String create(Model model) {
        Map<String, Object> response = new HashMap<>();
        response.put("outcome", "success");
        model.addAttribute("response", response);
        return "samd/dataEntry/writtenOffExecution/manual";
    }

    @Override
    public String create(Model model, SamWrittenOffExecution entity) {
        return null;
    }

    @PostMapping("/create")
    public String create(Model model, @RequestParam("file") MultipartFile file) {
        Map<String, Object> response = samWrittenOffExecutionService.storeData(file);

        if (response.get("outcome").equals("failure")) {
            model.addAttribute("response", response);
            return "samd/dataEntry/writtenOffExecution/manual";
        }
        else
            return "redirect:/collection/samd/written-off-execution/list";
    }

    @Override
    public String view(Model model, Long id) {
        return null;
    }

    @Override
    @GetMapping("/list")
    public String list(Model model) {
        List<SamWrittenOffExecution> allExecutions = samWrittenOffExecutionService.findAllData();

        model.addAttribute("executions", allExecutions);
        return "samd/dataEntry/writtenOffExecution/list";
    }

    @Override
    public String delete() {
        return null;
    }
}
