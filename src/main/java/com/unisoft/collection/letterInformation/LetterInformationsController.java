package com.unisoft.collection.letterInformation;

import com.unisoft.common.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collection/loan/letter-informations")
public class LetterInformationsController implements CommonController<LetterInformations> {

    @Autowired
    private LetterInformationsService letterInformationsService;

    @Override
    public String create(Model model) {
        return null;
    }

    @Override
    public String create(Model model, LetterInformations entity) {
        Map<String, Object> response = letterInformationsService.storeData(entity);
       return new Gson().toJson(response);
    }

    @PostMapping("/create")
    public LetterInformations create(LetterInformations letterInformations, @RequestPart MultipartFile file) {
        LetterInformations letterInformation1 = letterInformationsService.saveData(letterInformations, file);
        return letterInformation1;
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
    public List<LetterInformationsDto> getByCustomerId(@RequestParam(value = "customerId") String customerId) {
        List<LetterInformationsDto> letterInformationsDto = letterInformationsService.getAllDataByCustomerId(customerId);
        return letterInformationsDto;
    }
}
