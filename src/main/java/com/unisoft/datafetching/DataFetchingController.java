package com.unisoft.datafetching;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/datafetching")
public class DataFetchingController {

    @GetMapping(value = "/dashboard")
    public String viewAll(Model model) {

        return "collection/datafetching/datafetchingdashboard";
    }

}
