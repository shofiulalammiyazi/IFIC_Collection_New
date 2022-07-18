package com.csinfotechbd.retail.card.customerProfile;
/*
  Created by MR on 9/28/2021
*/

import com.csinfotechbd.utillity.InputSanitizer;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("collection/card/sms-push-pulls/")
public class SmsPushPullController {

    private SmsPushPullService pushPullService;
    private InputSanitizer inputSanitizer;

    SmsPushPullController(SmsPushPullService pushPullService, InputSanitizer inputSanitizer) {
        this.pushPullService = pushPullService;
        this.inputSanitizer = inputSanitizer;
    }

    @GetMapping("by-mobile")
    public ResponseEntity<List<SmsPushPull>> findByContractNo(@RequestParam("contractNo") String contractNo,
                                                              @RequestParam("mobileNo") String mobileNo) {
        if (!inputSanitizer.isSafe(new ArrayList(Arrays.asList(contractNo, mobileNo)))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(pushPullService.findByContractNoOrMobileNo(contractNo, mobileNo));
    }

    @GetMapping("")
    public ResponseEntity<List<SmsPushPull>> findAll() {
        return ResponseEntity.ok(pushPullService.findAll());
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<SmsPushPull>> findFilteredList(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
                                                              @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
                                                              @RequestParam("contractNo") String contractNo) {
        if (!inputSanitizer.isSafe(new ArrayList(Arrays.asList(fromDate.toString(), toDate.toString(), contractNo)))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(pushPullService.findByDateRange(fromDate, toDate, contractNo));
    }

    @GetMapping("/by-alert-type")
    public ResponseEntity<List<SmsPushPull>> findFilteredList(@RequestParam(value = "alertType") String alertType,
                                                              @RequestParam("contractNo") String contractNo) {
        if (!inputSanitizer.isSafe(new ArrayList(Arrays.asList(alertType, contractNo)))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(pushPullService.findByAlertType(alertType, contractNo));
    }

    @GetMapping("/by-delivery-status")
    public ResponseEntity<List<SmsPushPull>> findByDeliveryStatus(@RequestParam(value = "deliveryStatus") String deliveryStatus,
                                                                  @RequestParam("contractNo") String contractNo) {
        if (!inputSanitizer.isSafe(new ArrayList(Arrays.asList(deliveryStatus, contractNo)))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(pushPullService.findByDeliveryStatus(deliveryStatus, contractNo));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<SmsPushPull>> findFilteredList(@RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
                                                              @RequestParam(value = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
                                                              @RequestParam(value = "alertType", required = false) String alertType,
                                                              @RequestParam(value = "deliveryStatus", required = false) String deliveryStatus,
                                                              @RequestParam(value = "mobileNo", required = false) String mobileNo,
                                                              @RequestParam("accOrCardNo") String contractNo) {
        ArrayList strList = new ArrayList(Arrays.asList(fromDate.toString(), toDate.toString(), alertType, deliveryStatus, mobileNo, contractNo));


        if (!inputSanitizer.isSafe(strList)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<SmsPushPull> smsPushPulls = pushPullService.findByDateRange(fromDate, toDate, contractNo);

        if (mobileNo != null) {
            smsPushPulls = smsPushPulls.stream().filter(spp -> spp.getMobileNumber().equals(mobileNo)).collect(Collectors.toList());
        }

        if (deliveryStatus != null && deliveryStatus.trim().length() > 0) {
            smsPushPulls = smsPushPulls.stream().filter(spp -> spp.getDeliveryStatus().equalsIgnoreCase(deliveryStatus)).collect(Collectors.toList());
        }

        if (alertType != null && alertType.trim().length() > 0) {
            smsPushPulls = smsPushPulls.stream().filter(spp -> spp.getAlertType().equalsIgnoreCase(alertType)).collect(Collectors.toList());
        }

//        List<SmsPushPull> smsPushPulls = pushPullService.findByFilters(fromDate, toDate, alertType, deliveryStatus, mobileNo, contractNo);
        return ResponseEntity.ok(smsPushPulls);
    }
}
