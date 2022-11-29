package com.unisoft.collection.lettertemplate;

import com.unisoft.config.XSSRequestWrapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/collection/retail/operation/letter-template")
public class LetterTemplateController {
    private final LetterTemplateService letterTemplateService;

    @GetMapping("/create")
    public String create(Model model, @RequestParam(required = false) Long id) {
        LetterTemplate template = id == null ? null : letterTemplateService.getById(id);
        template = template == null ? new LetterTemplate() : template;

        template.setTemplate(StringEscapeUtils.unescapeHtml(XSSRequestWrapper.sanitizeHTML(template.getTemplate())));
        model.addAttribute("template", template);
        return "collection/letter_template/create";
    }

    @PostMapping("/create")
    public String create(Model model, LetterTemplate letterTemplate) {
        Map<String, Object> response = letterTemplateService.insertData(letterTemplate);
        if (response.get("outcome").equals("success"))
            return "redirect:/collection/retail/operation/letter-template/list";
        else{
            model.addAttribute("template", letterTemplate);
            return "collection/letter_template/create";
        }
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam Long id) {
        LetterTemplate template = letterTemplateService.getById(id);

        List<Map<String, Object>> accounts = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
        String currentDate = format.format(new Date());

        Map<String, Object> info = new HashMap<>();
        info.put("reference", "BBL/HO/CR/X/2021/06/0001");
        info.put("currentDate", currentDate);
        info.put("concernName", "Name of Concern");
        info.put("concernAddress", "Address of Concern");

        info.put("accountNo", "12345678901234");
        info.put("cardNo", "12345678901234");
        info.put("customerId", "123456");
        info.put("productType", "Product Type");

        info.put("installmentAmount", 12345678.12);
        info.put("overdueAmount", 12345678.12);
        info.put("outstandingAmount", 12345678.12);
        info.put("amountDue", 12345678.12);

        info.put("bblHoMobile", "01755596028");
        info.put("bblHoAddress", "Sepal Platinum Tower, Holding # 247/248 (Level-6), Tejgaon I/A, Bir Uttam Mir Showkat Road, Dhaka-1208");

        accounts.add(info);

        model.addAttribute("accounts", accounts);
        model.addAttribute("template", template);
        model.addAttribute("templateBody", (XSSRequestWrapper.sanitizeHTML(template.getTemplate())));
        return "collection/letter_template/view";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<LetterTemplate> templates = letterTemplateService.getAllTemplates();
        model.addAttribute("templates", templates);
        return "collection/letter_template/list";
    }
}
