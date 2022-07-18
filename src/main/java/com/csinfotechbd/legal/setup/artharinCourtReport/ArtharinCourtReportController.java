package com.csinfotechbd.legal.setup.artharinCourtReport;

import com.csinfotechbd.legal.setup.alternativeWayReport.AlternativeWayReportEntity;
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
@RequestMapping("/legal/setup/artharincourtreport")
public class ArtharinCourtReportController {

    private final ArtharinCourtReportRepository repository;

    @GetMapping("/create")
    public String save(Model model) {

        ArtharinCourtReportEntity artharinCourtReportEntity = new ArtharinCourtReportEntity();

        List<ArtharinCourtReportEntity> artharinCourtReportEntities = repository.findAll();

        if (artharinCourtReportEntities.size() >0){
            artharinCourtReportEntity = artharinCourtReportEntities.get(0);
        }

        model.addAttribute("artharinCourt", artharinCourtReportEntity);

        if (artharinCourtReportEntity.getId() != null) {
            model.addAttribute("artharinCourt",artharinCourtReportEntity);
        }

        return "legal/setup/artharincourtreport/create";
    }

    @PostMapping("create")
    public String save(@Valid ArtharinCourtReportEntity artharinCourtReportEntity, BindingResult result, Model model) {
        if (result.hasErrors()) return "legal/setup/artharincourtreport/create";

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (artharinCourtReportEntity.getId() == null) {
            artharinCourtReportEntity.setCreatedBy(user.getUsername());
            artharinCourtReportEntity.setCreatedDate(new Date());
            repository.save(artharinCourtReportEntity);
        } else {
            ArtharinCourtReportEntity oldEntity = repository.getOne(artharinCourtReportEntity.getId());
            ArtharinCourtReportEntity previousEntity = new ArtharinCourtReportEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            artharinCourtReportEntity.setCreatedBy(oldEntity.getCreatedBy());
            artharinCourtReportEntity.setCreatedDate(oldEntity.getCreatedDate());
            artharinCourtReportEntity.setModifiedBy(user.getUsername());
            artharinCourtReportEntity.setModifiedDate(new Date());
            repository.save(artharinCourtReportEntity);

        }

        return "redirect:/legal/setup/artharincourtreport/create";
    }
}
