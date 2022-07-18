package com.csinfotechbd.legal.dataEntry.fileFollowUp;

import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.branch.BranchService;
import com.csinfotechbd.collection.settings.district.DistrictDto;
import com.csinfotechbd.collection.settings.district.DistrictEntity;
import com.csinfotechbd.collection.settings.district.DistrictService;
import com.csinfotechbd.collection.settings.division.DivisionEntity;
import com.csinfotechbd.collection.settings.division.DivisionService;
import com.csinfotechbd.legal.setup.collateralSecurity.CollateralSecurity;
import com.csinfotechbd.legal.setup.collateralSecurity.CollateralSecurityService;
import com.csinfotechbd.legal.setup.litigationZone.LitigationZoneEntity;
import com.csinfotechbd.legal.setup.litigationZone.LitigationZoneService;
import com.csinfotechbd.utillity.PageUtils;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/legal/data-entry/litigation-file-follow-up/rest/")
public class LitigationFileFollowUpRestController {

    private final PageUtils pageUtils;
    private final LitigationFileFollowUpService litigationFileFollowUpService;
    private final BranchService branchService;
    private final DistrictService districtService;
    private final DivisionService divisionService;
    private final LitigationZoneService litigationZoneService;
    private final CollateralSecurityService collateralSecurityService;

    @GetMapping("list")
    public JSONObject list(@RequestParam("accountNo") String customerAccountNumber, HttpServletRequest request) {
        JSONObject jsonObject = history(customerAccountNumber, request);
        List<LitigationFileFollowUp> list = litigationFileFollowUpService.getByAccountNumber(customerAccountNumber);
        LitigationFileFollowUpDto fileFollowUpDto = list.isEmpty() ? new LitigationFileFollowUpDto() : new LitigationFileFollowUpDto(list.get(0));
        jsonObject.put("lastEntry", fileFollowUpDto);
        return jsonObject;
    }

    @PostMapping("create")
    public ResponseEntity<JSONObject> save(@Valid @RequestBody LitigationFileFollowUp litigationFileFollowUp, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String output = litigationFileFollowUpService.save(litigationFileFollowUp);
        switch (output) {
            case "1":
                jsonObject.put("success", true);
                break;
            default:
                jsonObject.put("success", false);
        }
        return ResponseEntity.ok(jsonObject);
    }

    @GetMapping("history")
    public JSONObject history(@RequestParam(value = "customerAccountNumber") String customerAccountNumber, HttpServletRequest request) {
        Pageable pageable = pageUtils.getPageableFromRequest(request, true);
        Page<LitigationFileFollowUp> page = litigationFileFollowUpService.getLitigationFileFollowUpByAccountNumber(customerAccountNumber, pageable);
        return pageUtils.pageToJson(page);
    }

    @GetMapping("/account-info")
    public LitigationFileFollowUpDto getAccountInfo(@RequestParam("accountNo") String accountNo) {
        return litigationFileFollowUpService.getAccountInfoFromApi(accountNo);
    }

    @GetMapping("/revisions")
    public List<LitigationFileFollowUpDto> getRevisions(@RequestParam("id") Long id) {
        return litigationFileFollowUpService.getRevisions(id).stream()
                .map(LitigationFileFollowUpDto::new).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @GetMapping("/zones")
    public List<LitigationZoneEntity> getZoneNames() {

        return litigationZoneService.findByEnabled(true);
    }

    @GetMapping("/districts")
    public List<DistrictDto> getDistrictName() {
        List<DistrictEntity> districts = districtService.getActiveOnly();
        List<DistrictDto> districtList = districts.stream()
                .map(DistrictDto::new).collect(Collectors.toList());

        return districtList;
    }

    @GetMapping("/branches")
    public List<Branch> getBranchName() {
        List<Branch> branches = branchService.getActiveList();

        return branches;
    }

    @GetMapping("/divisions")
    public List<String> getDivisionName() {
        List<DivisionEntity> divisions = divisionService.getActiveList();
        List<String> divisionList = divisions.stream()
                .map(DivisionEntity::getDivName).collect(Collectors.toList());

        return divisionList;
    }

    @GetMapping("/collateral-security")
    public List<CollateralSecurity> getCollateralSecurities() {
        return collateralSecurityService.findByEnabled(true);
    }

}
