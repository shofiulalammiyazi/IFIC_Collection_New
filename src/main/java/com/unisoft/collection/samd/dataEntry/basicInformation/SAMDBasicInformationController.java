package com.unisoft.collection.samd.dataEntry.basicInformation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/samd/data-entry/basic-information")
public class SAMDBasicInformationController {

    @Autowired
    private SAMDBasicInformationService samdBasicInformationService;



    @PostMapping("/save")
    public SAMDBasicInformation saveInformation(@Valid SAMDBasicInformation samdBasicInformation){
         SAMDBasicInformation samdBasicInformation1 = samdBasicInformationService.saveInformation(samdBasicInformation);
        return samdBasicInformation1;
    }


    @GetMapping("/find")
    public SAMDBasicInformation findbyStatus(){
        SAMDBasicInformation informations = samdBasicInformationService.findByStatusAndCreatedDate();
        return informations;
    }


}
