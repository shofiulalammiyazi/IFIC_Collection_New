package com.unisoft.collection.settings.ptpPromisor;



import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/collection/ptp_promisor/")
public class PTPPromisorController {

    @Autowired
    private PTPPromisorService ptpPromisorService;
    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("ptpProList",ptpPromisorService.getAll());
        return "collection/settings/ptpPromisor/ptp_promisor";
    }

    @GetMapping(value = "view")
    public String viewPage(@RequestParam(value = "id")Long id,Model model)
    {
        model.addAttribute("promisor",ptpPromisorService.getById(id));
        return "collection/settings/ptpPromisor/view";
    }

    @GetMapping(value = "create")
    public String addPage(Model model)
    {
        model.addAttribute("promisor",new PTPPromisorEntity());
        return "collection/settings/ptpPromisor/create";
    }

    @PostMapping(value = "create")
    public String create(PTPPromisorEntity promisor)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(promisor.getId() == null)
        {
            promisor.setCreatedBy(user.getUsername());
            promisor.setCreatedDate(new Date());
            boolean save=ptpPromisorService.savePtpPro(promisor);
            auditTrailService.saveCreatedData("PTP Promisor", promisor);

        }else {
            PTPPromisorEntity oldEntity = ptpPromisorService.getById(promisor.getId());
            PTPPromisorEntity previousEntity = new PTPPromisorEntity();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            promisor.setModifiedBy(user.getUsername());
            promisor.setModifiedDate(new Date());
            boolean update=ptpPromisorService.updatePromisor(promisor);
            auditTrailService.saveUpdatedData("PTP Promisor",previousEntity,promisor);
        }
        return "redirect:/collection/ptp_promisor/list";
    }

    @GetMapping(value = "edit")
    public String editpage(@RequestParam(value = "id")Long id,Model model)
    {
        model.addAttribute("promisor",ptpPromisorService.getById(id));
        return "collection/settings/ptpPromisor/create";
    }


    @GetMapping(value="listall")
    public ResponseEntity<Map<String, Object>> list() throws IOException {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<PTPPromisorEntity> promisor =ptpPromisorService.getActiveList();
        resultMap.put("promisor", promisor);
        resultMap.put("successMsg", "Promisor Successfully Received!");
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }
}
