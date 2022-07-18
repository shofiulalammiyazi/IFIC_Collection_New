package com.csinfotechbd.collection.finalSattlementAmount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/collection/final-sattlement-amount")
public class FinalSattlementAmountController {

    @Autowired
    private FinalSattlementAmountService finalSattlementAmountService;

    @PostMapping("/save")
    public Map<String, Object> save(@RequestBody FinalSattlementAmount entity) {
        Map<String, Object> response = finalSattlementAmountService.storeData(entity);
        return response;
    }

    @GetMapping("/get-by-customerid")
    public FinalSattlementAmount getByCustomerId(@RequestParam("customerId") long customerId) {
        FinalSattlementAmount amount = finalSattlementAmountService.getByCustomerId(customerId);
        return amount == null ? new FinalSattlementAmount() : amount;
    }
}
