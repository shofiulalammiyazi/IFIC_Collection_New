package com.unisoft.collection.samd.setup.hrPosition;

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
@RequestMapping("/collection/samd/setup/hr-position")
public class HrPositionController implements CommonController<HrPosition> {

    private final HrPositionService hrPositionService;

    @Override
    @GetMapping("/create")
    public String create(Model model) {
        Map<String, Object> response = new HashMap<>();
        response.put("outcome", "success");

        model.addAttribute("response", response);
        model.addAttribute("position", new HrPosition());

        return "samd/setup/hrPosition/create";
    }

    @Override
    @PostMapping("/create")
    public String create(Model model, HrPosition entity) {
        Map<String, Object> response = hrPositionService.storeData(entity);
        if (response.get("outcome").toString().equals("success"))
            return "redirect:/collection/samd/setup/hr-position/list";
        else {
            model.addAttribute("response", response);
            model.addAttribute("position", entity);
            return "samd/setup/hrPosition/create";
        }
    }

    @GetMapping("/edit")
    public String edit(Model model, Long id) {
        Map<String, Object> response = new HashMap<>();
        HrPosition position = hrPositionService.findDataById(id);
        response.put("outcome", "success");

        model.addAttribute("response", response);
        model.addAttribute("position", position);

        return "samd/setup/hrPosition/create";
    }

    @Override
    public String view(Model model, Long id) {
        return null;
    }

    @Override
    @GetMapping("/list")
    public String list(Model model) {
        List<HrPosition> positions = hrPositionService.findAllData();
        model.addAttribute("positions", positions);
        return "samd/setup/hrPosition/list";
    }

    @Override
    public String delete() {
        return null;
    }
}
