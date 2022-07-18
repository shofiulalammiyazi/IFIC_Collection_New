package com.csinfotechbd.legal.dataEntry.blaAttendanceHistory;

import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("collection/legal/bla-attendance-history")
public class BLAAttendanceHistoryController {
    private final BLAAttendanceHistoryService blaAttendanceHistoryService;

    @GetMapping("/get-by-litigation-case-info-id")
    public List<BLAAttendanceHistory> getByLitigationCaseInfoId(@RequestParam(value = "caseId") Long id) {
        List<BLAAttendanceHistory> histories = blaAttendanceHistoryService.getAllByLitigationCaseInfoId(id);
        return histories;
    }
}
