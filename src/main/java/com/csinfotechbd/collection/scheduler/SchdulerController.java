package com.csinfotechbd.collection.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schduler")
public class SchdulerController {


    @Autowired
    private ScheduledCornJobCard scheduledCornJobCard;


    @GetMapping("/run-83")
    public void runAllMethod(){
        System.out.println("start 83 methods...........");
        scheduledCornJobCard.get83TextFileData();
    }
}
