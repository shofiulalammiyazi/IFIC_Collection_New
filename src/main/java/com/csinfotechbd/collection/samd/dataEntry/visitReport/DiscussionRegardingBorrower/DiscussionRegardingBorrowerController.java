package com.csinfotechbd.collection.samd.dataEntry.visitReport.DiscussionRegardingBorrower;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/collection/samd/data-entry/visit-report/discussion-regarding-borrower")
public class DiscussionRegardingBorrowerController {


    @Autowired
    private DiscussionRegardingBorrowerService discussionRegardingBorrowerService;


    @PostMapping("/save")
    @ResponseBody
    public DiscussionRegardingBorrower createDiscussionRegardingBorrower(DiscussionRegardingBorrower discussionRegardingBorrower){
        DiscussionRegardingBorrower discussionRegardingBorrower1 = discussionRegardingBorrowerService.save(discussionRegardingBorrower);
        return discussionRegardingBorrower1;
    }


    @GetMapping("/find")
    @ResponseBody
    public DiscussionRegardingBorrower getDiscussionRegardingBorrowerByCustomerId(@RequestParam String customerId){
        DiscussionRegardingBorrower discussionRegardingBorrower = discussionRegardingBorrowerService.getDiscussionRegardingBorrowerByCustomerId(customerId);
        System.out.println("test");
        return discussionRegardingBorrower;
    }
}
