package com.unisoft.collection.chequeManagement;

import com.unisoft.common.CommonController;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collection/loan/cheque-management")
public class ChequeManagementController implements CommonController<ChequeManagement> {

    @Autowired
    private ChequeManagementService chequeManagementService;

    @Override
    public String create(Model model) {
        return null;
    }

    @Override
    public String create(Model model, ChequeManagement entity) {
        Map<String, Object> response = chequeManagementService.storeData(entity);
        return new Gson().toJson(response);
    }

    @PostMapping("/create")
    public ChequeManagement create(ChequeManagement chequeManagement, @RequestPart MultipartFile file) {
        ChequeManagement chequeManagement1 = chequeManagementService.saveData(chequeManagement, file);
        return chequeManagement1;
    }

    @Override
    public String view(Model model, Long id) {
        return null;
    }

    @Override
    public String list(Model model) {
        return null;
    }

    @Override
    public String delete() {
        return null;
    }

    @GetMapping("/get-by-customer-id")
    public List<ChequeManagementDto> getByCustomerId(@RequestParam(value = "customerId") String customerId) {
        List<ChequeManagementDto> chequeManagementDtos = chequeManagementService.getAllDataByCustomerId(customerId);
        return chequeManagementDtos;
    }

}
