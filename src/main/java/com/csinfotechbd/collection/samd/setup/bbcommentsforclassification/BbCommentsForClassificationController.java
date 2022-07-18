package com.csinfotechbd.collection.samd.setup.bbcommentsforclassification;


import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/collection/samd/setup/bb-comments-for-classification")
public class BbCommentsForClassificationController {
    @Autowired
    private BbCommentsForClassificationService service ;

    @Autowired
    private BbCommentsForClassificationRepository bbCommentsForClassificationRepository;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value="/view")
    public String view(Model model, @RequestParam(value = "id", required = true) String id){
        return "samd/setup/bbcommentsforclassification/view";
    }

    @GetMapping(value="/create")
    public String create(){
        return "samd/setup/bbcommentsforclassification/create";
    }

    @PostMapping(value = "/create")
    public String save(Model model, @Valid BbCommentsForClassification obj, BindingResult result) {
//        if (!result.hasErrors()) {
//            service.saveOrUpdate(obj);
//        } else {
//            return "samd/setup/bbcommentsforclassification/create";
//        }

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(obj.getId()==null){
            obj.setCreatedBy(user.getUsername());
            obj.setCreatedDate(new Date());
            service.saveOrUpdate(obj);
            auditTrailService.saveCreatedData("BB Comments for Classification",obj);
        }
        else{
            BbCommentsForClassification oldEntity = bbCommentsForClassificationRepository.getOne(obj.getId());
            BbCommentsForClassification previousEntity = new BbCommentsForClassification();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            obj.setCreatedBy(user.getUsername());
            obj.setCreatedDate(new Date());
            bbCommentsForClassificationRepository.save(obj);
            auditTrailService.saveUpdatedData("BB Comments for Classification",previousEntity,obj);
        }

        return "redirect:/collection/samd/setup/bb-comments-for-classification/list";
    }

    @GetMapping(value="/list")
    public String objectsList(Model model){
        List<BbCommentsForClassification> list=service.objectList();
        model.addAttribute("bbCommentsClassificationList" ,list);
        return "samd/setup/bbcommentsforclassification/list";
    }


    @GetMapping(value="/edit")
    public String edit(Model model, @RequestParam(value = "id", required = true) String id){
        model.addAttribute("bbCommentsClassification",service.findByid(id));
        return "samd/setup/bbcommentsforclassification/create";
    }

    @GetMapping(value="/remove")
    public String remove( @RequestParam(value = "id", required = true) String id){
        service.remove(id);
        return "redirect:/collection/samd/setup/bb-comments-for-classification/list";
    }



}
