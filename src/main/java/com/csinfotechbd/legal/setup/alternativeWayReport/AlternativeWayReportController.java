package com.csinfotechbd.legal.setup.alternativeWayReport;

import com.csinfotechbd.legal.setup.legalExpense.LegalExpenseEntity;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/legal/setup/alternativewayreport")
public class AlternativeWayReportController {

    private final AlternativeWayReportRepository repository;

    @GetMapping("/create")
    public String save(Model model) {

        AlternativeWayReportEntity alternativeWayReportEntity = new AlternativeWayReportEntity();

        List<AlternativeWayReportEntity> alternativeWayReportEntities = repository.findAll();

        if (alternativeWayReportEntities.size() >0){
            alternativeWayReportEntity = alternativeWayReportEntities.get(0);
        }

        model.addAttribute("alternativeWay", alternativeWayReportEntity);

        if (alternativeWayReportEntity.getId() != null) {
            model.addAttribute("alternativeWay",alternativeWayReportEntity);
        }

        return "legal/setup/alternativewayreport/create";
    }

    @PostMapping("create")
    public String save(@Valid AlternativeWayReportEntity alternativeWayReportEntity, BindingResult result, Model model) {
        if (result.hasErrors()) return "legal/setup/alternativewayreport/create";

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (alternativeWayReportEntity.getId() == null) {
            alternativeWayReportEntity.setCreatedBy(user.getUsername());
            alternativeWayReportEntity.setCreatedDate(new Date());
            repository.save(alternativeWayReportEntity);
        } else {
            AlternativeWayReportEntity oldEntity = repository.getOne(alternativeWayReportEntity.getId());
            AlternativeWayReportEntity previousEntity = new AlternativeWayReportEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            alternativeWayReportEntity.setCreatedBy(oldEntity.getCreatedBy());
            alternativeWayReportEntity.setCreatedDate(oldEntity.getCreatedDate());
            alternativeWayReportEntity.setModifiedBy(user.getUsername());
            alternativeWayReportEntity.setModifiedDate(new Date());
            repository.save(alternativeWayReportEntity);

        }

        return "redirect:/legal/setup/alternativewayreport/create";
    }

}
