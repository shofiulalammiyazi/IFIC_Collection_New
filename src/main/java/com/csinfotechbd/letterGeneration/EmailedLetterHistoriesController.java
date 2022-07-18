package com.csinfotechbd.letterGeneration;

import com.csinfotechbd.utillity.InputSanitizer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/collection/letter-via-email/histories")
public class EmailedLetterHistoriesController {

    private InputSanitizer inputSanitizer;

    private EmailedLetterHistoriesService emailedLetterHistoriesService;

    @GetMapping("/get-by-customer")
    public List<EmailedLetterHistories> getByCustomerId(Long customerId) {
        return emailedLetterHistoriesService.getByCustomerId(customerId);
    }

    @GetMapping("/by-customer-id")
    public ResponseEntity<List<EmailedLetterHistories>> getByContractNo(@RequestParam("customerId") String customerId) {

        if (!inputSanitizer.isSafe(customerId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(emailedLetterHistoriesService.getByCustomerId(Long.parseLong(customerId)));
    }
}
