package com.unisoft.collection.settings.NPLQueueAccRule;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DPDBucketService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/collection/npl_queue_acc_rule/")
public class NPLQueueAccRuleController {

    @Autowired
    private NPLQueueAccService nplQueueAccService;

    @Autowired
    private DPDBucketService dpdBucketService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private AuditTrailService auditTrailService;

    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("nplQueueList",nplQueueAccService.getAll());
        return "collection/settings/nplQueueRule/nplQueue";
    }

    @GetMapping(value = "create")
    public String addPage(Model model)
    {
        List<String> allowedBucketIdList=new ArrayList<>();
        List<String> allowedProdIdList=new ArrayList<>();
        allowedBucketIdList.add("");
        allowedProdIdList.add("");
        model.addAttribute("allowedBucketIdList",allowedBucketIdList);
        model.addAttribute("allowedProdIdList",allowedProdIdList);
        model.addAttribute("dpdBucketList",dpdBucketService.getCustomActiveList());
        model.addAttribute("prodTypeList",productTypeService.getAllActive());
        model.addAttribute("nplQueue",new NPLQueueAccRuleEntity());
        return "collection/settings/nplQueueRule/create";
    }


    @PostMapping(value = "create")
    public String create(NPLQueueAccRuleEntity  nplAccountRule, @RequestParam(value = "bucketIdList") List<Long> dpdBuckIdList, @RequestParam(value = "prodIdList") List<Long> prodTypeIdList)
    {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<DPDBucketEntity> bucketList=new ArrayList<>();
        List<ProductTypeEntity> productList=new ArrayList<>();

        for(Long id : dpdBuckIdList)
        {
            DPDBucketEntity bucket=new DPDBucketEntity();
            bucket.setId(id);
            bucketList.add(bucket);
        }

        for(Long id : prodTypeIdList)
        {
            ProductTypeEntity productType=new ProductTypeEntity();
            productType.setId(id);
            productList.add(productType);
        }

        nplAccountRule.setDpdBucketList(bucketList);
        nplAccountRule.setProductTypeList(productList);

        if(nplAccountRule.getId()==null)
        {
            nplAccountRule.setCreatedBy(user.getUsername());
            nplAccountRule.setCreatedDate(new Date());
            boolean save=nplQueueAccService.saveNew(nplAccountRule);
            auditTrailService.saveCreatedData("NPL Queue Account Rule - Loan", nplAccountRule);
        }else {
            NPLQueueAccRuleEntity oldEntity = nplQueueAccService.getById(nplAccountRule.getId());
            NPLQueueAccRuleEntity previousEntity = new NPLQueueAccRuleEntity();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            nplAccountRule.setModifiedBy(user.getUsername());
            nplAccountRule.setModifiedDate(new Date());
            boolean update=nplQueueAccService.updateNPLQueu(nplAccountRule);
            auditTrailService.saveUpdatedData("NPL Queue Account Rule - Loan", previousEntity, nplAccountRule);
        }
        return "redirect:/collection/npl_queue_acc_rule/list";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id")Long id,Model model)
    {
        NPLQueueAccRuleEntity nplAccountRule=nplQueueAccService.getById(id);

        List<String> allowedBucketIdList=new ArrayList<>();
        List<String> allowedProdIdList=new ArrayList<>();

        //System.err.println("nplAccountRule "+nplAccountRule);
        int sz = nplAccountRule.getDpdBucketList().size();
        for(int i=0;i < sz;i++)
        {
            allowedBucketIdList.add(nplAccountRule.getDpdBucketList().get(i).getId().toString());
        }

        for(ProductTypeEntity productType : nplAccountRule.getProductTypeList())
        {
            allowedProdIdList.add(productType.getId().toString());
        }

        model.addAttribute("dpdBucketList",dpdBucketService.getCustomActiveList());
        model.addAttribute("prodTypeList",productTypeService.getAllActive());

        model.addAttribute("allowedBucketIdList",allowedBucketIdList);
        model.addAttribute("allowedProdIdList",allowedProdIdList);
        model.addAttribute("nplQueue",nplQueueAccService.getById(id));
        return "collection/settings/nplQueueRule/create";
    }

}
