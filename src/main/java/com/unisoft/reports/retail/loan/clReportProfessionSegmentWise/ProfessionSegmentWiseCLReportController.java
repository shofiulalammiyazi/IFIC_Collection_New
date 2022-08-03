package com.unisoft.reports.retail.loan.clReportProfessionSegmentWise;

import com.unisoft.collection.settings.productGroup.ProductGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection/report/retail/loan/profession-segment-wise-cl")
public class ProfessionSegmentWiseCLReportController {

    private final ProfessionSegmentWiseCLReportService service;
    private final ProductGroupService productGroupService;


    @GetMapping(value = "/get")
    @ResponseBody
    public List<ProfessionSegmentReportDto> getPSReport(String productGroup){
        return service.getReport(productGroup);
    }


    @GetMapping("/view")
    public String view(Model model) {


        model.addAttribute("reportTitle", "Profession Segment wise CL report");
        model.addAttribute("productList", productGroupService.getList());

        return "collection/reports/retail/loan/clReportProfessionSegmentWise/view";
    }

//    @GetMapping(value = "/get")
//    @ResponseBody
//    public List<ProfessionSegmentWiseCLReportDto> getPSReport(@RequestParam(value = "name") List<String> products){
//        List<ProfessionSegmentReport> list = service.getReport(products);
//        Map<String,ProfessionSegmentWiseCLReportDto> mapList = new HashMap<>();
//        list.stream().forEach(item-> {
//            String name = item.getPRODUCT_NAME();
//            if(mapList.containsKey(name)){
//                ProfessionSegmentWiseCLReportDto data = mapList.get(name);
//                data.getGetProductList().add(item);
//                mapList.put(name,data);
//            }else {
//                ProfessionSegmentWiseCLReportDto data = new ProfessionSegmentWiseCLReportDto();
//                data.setPrdName(name);
//                data.getGetProductList().add(item);
//                mapList.put(name, data);
//            }
//        });
//
//        List<ProfessionSegmentWiseCLReportDto> professionSegmentWiseCLReportDtos = new ArrayList<>(mapList.values());
//        return  professionSegmentWiseCLReportDtos;
//    }




}
