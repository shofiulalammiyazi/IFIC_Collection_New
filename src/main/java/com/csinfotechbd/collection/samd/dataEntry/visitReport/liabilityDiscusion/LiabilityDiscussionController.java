package com.csinfotechbd.collection.samd.dataEntry.visitReport.liabilityDiscusion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/collection/samd/data-entry/visit-report/liability-discussion")
public class LiabilityDiscussionController {

    @Autowired
    private LiabilityDiscussionService liabilityDiscussionService;


    @PostMapping("/save")
    @ResponseBody
    public LiabilityDiscussion createLiability(LiabilityDiscussion liabilityDiscussion){
        LiabilityDiscussion discussion = liabilityDiscussionService.save(liabilityDiscussion);
        return discussion;
    }


    @GetMapping("/list")
    @ResponseBody
    public List<LiabilityDiscussion> getLiabilityDiscussionList(@RequestParam String customerId){
        List<LiabilityDiscussion>liabilityDiscussions = liabilityDiscussionService.findAllLiabilityDiscussion(customerId);
        return liabilityDiscussions;
    }


    @GetMapping("/find")
    @ResponseBody
    public LiabilityDiscussion editLiabilityDiscussion(@RequestParam Long id){
        LiabilityDiscussion liabilityDiscussion = liabilityDiscussionService.findLiabilityDiscussionById(id);
        return liabilityDiscussion;
    }
}
