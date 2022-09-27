//Updated By   Islam on 05/07/2019

package com.unisoft.collection.settings.branch;


import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.district.DistrictService;
import com.unisoft.utillity.ExcelFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchDao branchDao;
    private final BranchRepository branchRepository;
    private final DistrictService districtService;
    private final ExcelFileUtils excelFileUtils;
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


}
