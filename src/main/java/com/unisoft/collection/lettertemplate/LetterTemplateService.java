package com.unisoft.collection.lettertemplate;

import com.unisoft.config.XSSRequestWrapper;
import com.unisoft.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class LetterTemplateService {
    private final LetterTemplateRepository letterTemplateRepository;

    public Map<String, Object> insertData(LetterTemplate template){
        Map<String, Object> response = new HashMap<>();

        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        template.setCreatedDate(new Date());
        template.setCreatedBy(principal.getUsername());
        template.setModifiedDate(new Date());
        template.setModifiedBy(principal.getUsername());

        template.setTemplate(StringEscapeUtils.escapeHtml(XSSRequestWrapper.sanitizeHTML(template.getTemplate())));
        letterTemplateRepository.save(template);

        if (template.getId() != null || template.getId() != 0)
            response.put("outcome", "success");
        else
            response.put("outcome", "failure");

        return response;
    }

    public List<LetterTemplate> getAllTemplates() {
        List<LetterTemplate> templates = letterTemplateRepository.findAll();
        return templates;
    }

    public LetterTemplate getById(Long id) {
        LetterTemplate template = letterTemplateRepository.getOne(id);
        return template;
    }

    public List<LetterTemplate> getAllByUnit(String unit) {
        List<LetterTemplate> templates = letterTemplateRepository.findAllByUnit(unit);

        List<LetterTemplate> letterTemplates = new ArrayList<>();

        for(LetterTemplate lt : templates){
            letterTemplates.add(new LetterTemplate(lt.getId(),lt.getName()));
        }

        return letterTemplates;
    }
}
