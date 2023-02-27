package com.unisoft.Company2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CompanyController {

//    @Value("${logo.location}")
//    private String LOGO_LOCATION;

    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/company/view")
    public String getCompany(Model model) {

        CompanyEntity companyEntity = companyService.getCompany();

        if (companyEntity != null)
            model.addAttribute("company", companyEntity);
        else
            model.addAttribute(new CompanyEntity());

        return "card/contents/settings/company/companyView";
    }



    @GetMapping(value = "/company/create")
    public String createCompany(Model model) {
        CompanyEntity companyEntity=new CompanyEntity();

//        System.err.println("LOGO LOC : "+LOGO_LOCATION);



        model.addAttribute("companyEntity",companyEntity);
        return "card/contents/settings/company/create";
    }

    @GetMapping(value = "/company/edit")
    public String getCompanys(Model model) {

        CompanyEntity companyEntity = companyService.getCompany();

        model.addAttribute("company", companyEntity);
        return "card/contents/settings/company/create";
    }


    @PostMapping(value = "/company/create")
    public String saveCompanyProfile(Model model,
                                     @Valid CompanyEntity companyEntity)
    {


//        String name=LOGO_LOCATION+"\\"+file.getOriginalFilename();



//        if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(new File(name)));
//                stream.write(bytes);
//                stream.close();



//                companyEntity.setLogoUrl(name);

//
//            } catch (Exception e) {
//
//            }
//        }
        companyService.saveCompany(companyEntity);
        return "redirect:/company/view";
    }

   /* @GetMapping(value = "/company/view")
    public String getCompany(Model model, HttpSession session, Principal principal) {
        CompanyEntity companyEntity = companyService.getCompany();
        String[] jsLinksHead = {"/js/logoscrpt.js"};
        if (companyEntity != null)
        {
            model.addAttribute("company", companyEntity);
        }
        else
            model.addAttribute(new CompanyEntity());
        return "card/contents/settings/company/view";
    }*/


  /*  @GetMapping(value = "/company/edit")
    public String getCompanys(Model model,HttpSession session,Principal principal) {
        CompanyEntity companyEntity = companyService.getCompany();
      *//*  String[] jsLinksHead = {"/js/logoscrpt.js"};
        model.addAttribute("jsLinksHead",jsLinksHead);*//*
        model.addAttribute("company", companyEntity);
       *//* model.addAttribute("title", "Update Company Setting");
        model.addAttribute("body", "company/update");
        //must add tasks to model otherwise inbox sub-menu won't be visible.*//*
        return "card/contents/settings/company/update";
    }*/


/*
    @PostMapping(value = "/company/create")
    public String saveCompanyProfile(Model model, @Valid CompanyEntity companyEntity, BindingResult result, HttpSession session, Principal principal)
    {
        *//*    if(result.hasErrors()){
            String[] jsLinksHead = {"/js/logoscrpt.js"};
            model.addAttribute("jsLinksHead",jsLinksHead);
            model.addAttribute("company", companyEntity);
            model.addAttribute("title", "Update Company Setting");
            //must add tasks to model otherwise inbox sub-menu won't be visible.
            return "card/contents/settings/company/update";
        }else{

            companyService.saveCompany(companyEntity);
        }*//*
        companyService.saveCompany(companyEntity);

		*//*if(!result.hasErrors()){

			//companyService.saveImage(companyEntity.getUploadLogo());


		}*//*

        return "redirect:/company/view";
    }*/


}
