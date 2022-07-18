package com.csinfotechbd.collection.settings.interestSetting;

import com.csinfotechbd.legal.setup.artharinCourtReport.ArtharinCourtReportEntity;
import com.csinfotechbd.user.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/setup/interest")
public class InterestSettingController {

    public final InterestSettingRepository repository;

    @GetMapping("/create")
    public String save(Model model) {
        InterestSettingEntity interestSettingEntity = new InterestSettingEntity();

        List<InterestSettingEntity> interestSettingEntities = repository.findAll();

        if (interestSettingEntities.size() >0){
            interestSettingEntity = interestSettingEntities.get(0);
        }

        model.addAttribute("interest", interestSettingEntity);

        if (interestSettingEntity.getId() != null) {
            model.addAttribute("interest",interestSettingEntity);
        }

        return "collection/settings/interestSetting/create";
    }

    @PostMapping("/create")
    public String save(@Valid InterestSettingEntity interestSettingEntity, BindingResult result, Model model) {
        if (result.hasErrors()) return "/collection/setup/interest/create";

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (interestSettingEntity.getId() == null) {
            interestSettingEntity.setCreatedBy(user.getUsername());
            interestSettingEntity.setCreatedDate(new Date());
            repository.save(interestSettingEntity);
        } else {
            InterestSettingEntity oldEntity = repository.getOne(interestSettingEntity.getId());
            InterestSettingEntity previousEntity = new InterestSettingEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            interestSettingEntity.setCreatedBy(oldEntity.getCreatedBy());
            interestSettingEntity.setCreatedDate(oldEntity.getCreatedDate());
            interestSettingEntity.setModifiedBy(user.getUsername());
            interestSettingEntity.setModifiedDate(new Date());
            repository.save(interestSettingEntity);

        }

        return "redirect:/collection/setup/interest/create";
    }

    @GetMapping("/interest-data")
    @ResponseBody
    public InterestSettingEntity getInterest() {
        List<InterestSettingEntity> interestSettingEntities = repository.findAll();
        InterestSettingEntity data = interestSettingEntities.get(0);

        return data;
    }
}
