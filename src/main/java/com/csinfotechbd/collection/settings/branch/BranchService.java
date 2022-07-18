//Updated By Monirul Islam on 05/07/2019

package com.csinfotechbd.collection.settings.branch;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.district.DistrictEntity;
import com.csinfotechbd.collection.settings.district.DistrictService;
import com.csinfotechbd.legal.setup.litigationZone.LitigationZoneEntity;
import com.csinfotechbd.legal.setup.litigationZone.LitigationZoneRepository;
import com.csinfotechbd.utillity.ExcelFileUtils;
import com.csinfotechbd.utillity.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchDao branchDao;
    private final BranchRepository branchRepository;
    private final DistrictService districtService;
    private final ExcelFileUtils excelFileUtils;
    private final LitigationZoneRepository litigationZoneRepository;
    private final AuditTrailService auditTrailService;

    public List<Branch> getList() {
        return branchDao.getList();
    }

    public Branch findById(int branchId) {
        return branchDao.findById(branchId);
    }

    public List<Branch> getByDistrict(Long districtId) {
        return branchDao.getByDistrict(districtId);
    }

    public void saveOrUpdate(Branch branch, String userId) {

        try {

            if (branch.getBranchId() == null) {
                //branch.setEnabled(true);
                branch.setCreatedBy(userId);
                branch.setCreatedDate(new Date());
                branchDao.save(branch);

                auditTrailService.saveCreatedData("Branch", branch);
            } else {
                //branch.setEnabled(true);
                Branch tempBranch = branchDao.getBranchById(branch.getBranchId().toString());
                Branch previousEntity = new Branch();
                BeanUtils.copyProperties(tempBranch, previousEntity);

                branch.setCreatedDate(tempBranch.getCreatedDate());
                branch.setCreatedBy(tempBranch.getCreatedBy());

                branch.setModifiedBy(userId);
                branch.setModifiedDate(new Date());
                branchDao.update(branch);
                auditTrailService.saveUpdatedData("Branch", previousEntity, branch);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    public Branch getBranchById(String id)
//    {
//        return branchDao.getBranchById(id);
//    }

    public List<Branch> getActiveList() {
        return branchDao.getActiveList();
    }

    public List<Branch> getByBranchCode(List<String> branchCodes) {
        return branchRepository.findByBranchCodeIn(branchCodes);
    }

    public List<BranchDto> getBranchesContainingCases() {
        return branchRepository.getBranchesContainingCases().stream().map(BranchDto::new).collect(Collectors.toList());
    }

    public Branch getByBranchCode(String branchCode) {
        return branchRepository.findFirstByBranchCode(branchCode);
    }

    public Branch getByNumericBranchCode(String branchCode) {
        int branchCodeNum = -1;
        try {
            branchCodeNum = Integer.parseInt(branchCode);
        } catch (Exception ignored) {
        }
        return branchRepository.findByNumericBranchCode(branchCodeNum);
    }

    public Branch getByBranchName(String branchName) {
        return branchRepository.findFirstByBranchNameIgnoreCase(branchName);
    }

    public List<String> saveFromExcel(MultipartFile file) {
        Map<String, Object> cache = new HashMap<>();
        List<Sheet> sheets = excelFileUtils.getExcelSheetsFromMultipartFile(file);
        for (Sheet sheet : sheets) {
            extractXssfSheet(sheet, cache);
        }
        return (List<String>) cache.get("errors");
    }

    private void extractXssfSheet(Sheet table, Map<String, Object> cache) {

        Map<String, LitigationZoneEntity> zones = new HashMap<>();
        List<String> errors = (List<String>) cache.getOrDefault("errors", new LinkedList<>());

        Branch branch;
        Iterator<Row> rows = table.iterator();

        // Skip rows untill the header row found and collect cell headers and indices
        Map<Integer, String> headerColumns = new HashMap<Integer, String>() {{
            put(0, "Branch Code");
            put(1, "Branch Name");
        }};
        Map<String, Integer> headers = excelFileUtils.getColumnHeadersAndIndices(rows, headerColumns);

        while (rows.hasNext()) {
            try {
                Row row = rows.next();

                // Find the identifier of a row first
                String branchCode = excelFileUtils.getTextCellValue(row, headers, "Branch Code", errors);

                // Escape the empty rows(No identifier)
                if (branchCode.isEmpty()) continue;

                // Read other column values
                String branchName = excelFileUtils.getTextCellValue(row, headers, "Branch Name", errors);
                String routingNo = excelFileUtils.getTextCellValue(row, headers, "Routing No", errors);
//                String division = excelFileUtils.getTextCellValue(row, headers, "Division", errors);
                String districtName = excelFileUtils.getTextCellValue(row, headers, "District", errors);

                // Prepare relational data
                DistrictEntity district = districtService.getByName(districtName);

                branch = Optional.ofNullable(branchRepository.findFirstByBranchCode(branchCode)).orElse(new Branch());

                // Set all collected data
                branch.setBranchCode(branchCode);
                branch.setBranchName(branchName);
                branch.setRoutingNo(routingNo);
                branch.setDistrict(district);
                branch.setCreatedBy("System");
                branch.setCreatedDate(new Date());

                // Save information for previous date
                branchRepository.save(branch);

                // Additional part of litigation zone entry
                String zoneName = excelFileUtils.getTextCellValue(row, headers, "Zone Name", errors);
                if (StringUtils.hasText(zoneName)) {
                    LitigationZoneEntity zone = zones.getOrDefault(zoneName, new LitigationZoneEntity());
                    List<Branch> branches = zone.getBranches();
                    if (!StringUtils.hasText(zone.getName())) zone.setName(zoneName);
                    branches.add(branch);
                    zones.put(zoneName, zone);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (LitigationZoneEntity zone : zones.values()) {
            litigationZoneRepository.save(zone);
        }
        cache.put("errors", errors);
    }
}
