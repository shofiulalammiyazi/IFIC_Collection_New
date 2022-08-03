package com.unisoft.collection.settings.dpdBucket;
/*
Created by   Islam on 7/7/2019
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/collection/dpdBucket/")
public class DPDBucketController {

    @Autowired
    private DPDBucketService dpdBucketService;

    @GetMapping(value = "list")
    public String viewAll(Model model)
    {
        model.addAttribute("bucketList", dpdBucketService.getAll());
        return "collection/settings/dpdBucket/bucket";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        model.addAttribute("bucket", new DPDBucketEntity());
        return "collection/settings/dpdBucket/create";
    }

    @PostMapping(value = "create")
    public String addNew(DPDBucketEntity dpdBucket, Model model) {
        String output = dpdBucketService.save(dpdBucket);
        switch (output) {
            case "1":
                return "redirect:/collection/dpdBucket/list";
            default:
                model.addAttribute("error", output);
        }
        model.addAttribute("bucket", dpdBucket);
        return "collection/settings/dpdBucket/create";
    }

    @GetMapping(value = "edit")
    public String editPage(@RequestParam(value = "id") Long id, Model model) {
        model.addAttribute("bucket", dpdBucketService.getById(id));
        return "collection/settings/dpdBucket/create";
    }
}
