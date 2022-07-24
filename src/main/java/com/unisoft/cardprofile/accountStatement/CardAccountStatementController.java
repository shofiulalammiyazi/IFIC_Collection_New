package com.unisoft.cardprofile.accountStatement;
/*
  Created by MR on 9/30/2021
*/


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/collection/card/statements")
public class CardAccountStatementController {

    @GetMapping("")
    public ResponseEntity<List<CardAccountStatement>> findAll(@RequestParam("contractId") String contractId,
                                                              @RequestParam(value = "fromDate", required = false)
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
                                                              @RequestParam(value = "toDate", required = false)
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
        List<CardAccountStatement> statements = new ArrayList<>();
        return ResponseEntity.ok(statements);
    }

    @GetMapping("link-account")
    public ResponseEntity<List<CardAccountStatement>> findAllByLinkAccount(@RequestParam("linkAcc") String linkAccount,
                                                                           @RequestParam(value = "fromDate", required = false)
                                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
                                                                           @RequestParam(value = "toDate", required = false)
                                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
        List<CardAccountStatement> statements = new ArrayList<>();
        return ResponseEntity.ok(statements);
    }
}
