package com.csinfotechbd.legal.setup.lawyers;

import com.csinfotechbd.audittrail.AuditTrailService;
import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.branch.BranchService;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.user.UserService;
import com.csinfotechbd.utillity.ExcelFileUtils;
import com.csinfotechbd.utillity.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LawyerServiceImpl implements LawyerService {

    private final LawyerRepository repository;
    private final ExcelFileUtils excelFileUtils;
    private final BranchService branchService;
    private final AuditTrailService auditTrailService;

    @Override
    public String save(Lawyer lawyer) {
        String validContact = validateContact(lawyer);

        if (!validContact.equals("1")) return validContact;
        String alreadyExistStatus = alreadyExists(lawyer);

        if (!alreadyExistStatus.equals("1")) return alreadyExistStatus;
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (lawyer.getId() == null) {
            setLawyerId(lawyer);
            lawyer.setCreatedBy(user.getUsername());
            lawyer.setCreatedDate(new Date());
            repository.save(lawyer);
            auditTrailService.saveCreatedData("Lawyers", lawyer);
        } else {
            Lawyer oldEntity = repository.getOne(lawyer.getId());
            Lawyer previousEntity = new Lawyer();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            lawyer.setLawyerId(oldEntity.getLawyerId());
            lawyer.setCreatedBy(oldEntity.getCreatedBy());
            lawyer.setCreatedDate(oldEntity.getCreatedDate());
            lawyer.setModifiedBy(user.getUsername());
            lawyer.setModifiedDate(new Date());
            repository.save(lawyer);
            auditTrailService.saveUpdatedData("Lawyers", previousEntity, lawyer);
        }
        return "1";
    }

    private void setLawyerId(Lawyer lawyer) {
        if (lawyer.getLawyerId() == null) {
            Long lawyerId = repository.findMaxLawyerId();
            lawyerId = lawyerId != null && lawyerId > 0 ? lawyerId + 1 : 1001;
            lawyer.setLawyerId(lawyerId.toString());
        }
    }

    @Override
    public List<Lawyer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Lawyer> findByEnabled(boolean enabled) {
        return repository.findByEnabled(enabled);
    }

    @Override
    public Lawyer findById(Long id) {
        return repository.findById(id).orElse(new Lawyer());
    }

    @Override
    public Lawyer findByContactNo(String... contacts) {
        List<Lawyer> lawyers = new ArrayList<>();
        for (String contact : contacts) {
            lawyers = repository.findByContactNo(contact);
            if (!lawyers.isEmpty()) break;
        }
        return lawyers.isEmpty() ? null : lawyers.get(0);
    }

    @Override
    public Lawyer findByContactOrName(String contact, String name) {
        List<Lawyer> lawyers;
        lawyers = repository.findByContactOrNameLike(contact, name);
        return !lawyers.isEmpty() ? lawyers.get(0) : new Lawyer();
    }

    @Override
    public Lawyer findOrSave(Lawyer lawyers) {
        if (!StringUtils.hasText(lawyers.getPhoneNo()) && !StringUtils.hasText(lawyers.getMobileNo())) return null;
        List<Lawyer> lawyerFromDb = repository.findByContactNo(lawyers.getPhoneNo());
        if (lawyerFromDb == null)
            save(lawyers);
        else
            lawyers = lawyerFromDb.isEmpty() ? null : lawyerFromDb.get(0);
        return lawyers;

    }

    @Override
    public List<Lawyer> findById(List<Long> idList) {
        return repository.findByIdIn(idList);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private String alreadyExists(Lawyer lawyer) {
        if (lawyer.getId() == null) {
            return getDuplicateStatus(lawyer);
        } else {
            Lawyer oldEntry = repository.findById(lawyer.getId()).orElse(new Lawyer());
            if (StringUtils.hasText(lawyer.getPhoneNo()) && !lawyer.getPhoneNo().equals(oldEntry.getPhoneNo()) && repository.existsByPhoneNo(lawyer.getPhoneNo()))
                return "03";
            if (StringUtils.hasText(lawyer.getEmail()) && !lawyer.getEmail().equals(oldEntry.getEmail()) && repository.existsByEmail(lawyer.getEmail()))
                return "04";
            if (StringUtils.hasText(lawyer.getMobileNo()) && !lawyer.getMobileNo().equals(oldEntry.getMobileNo()) && repository.existsByMobileNo(lawyer.getMobileNo()))
                return "05";
            if (StringUtils.hasText(lawyer.getLawyerId()) && !lawyer.getLawyerId().equals(oldEntry.getLawyerId()) && repository.existsByLawyerId(lawyer.getLawyerId()))
                return "06";
            return "1";
        }
    }

    private String getDuplicateStatus(Lawyer lawyer) {
        if (StringUtils.hasText(lawyer.getPhoneNo()) && repository.existsByPhoneNo(lawyer.getPhoneNo()))
            return "03";
        if (StringUtils.hasText(lawyer.getEmail()) && repository.existsByEmail(lawyer.getEmail()))
            return "04";
        if (StringUtils.hasText(lawyer.getMobileNo()) && repository.existsByMobileNo(lawyer.getMobileNo()))
            return "05";
        if (StringUtils.hasText(lawyer.getLawyerId()) && repository.existsByLawyerId(lawyer.getLawyerId()))
            return "06";
        return "1";
    }

    private String validateContact(Lawyer lawyer) {
//        Pattern pattern = Pattern.compile("^((\\+|00)?88)?0(\\d{8,10})(-\\d)?$");
//        boolean validContact = lawyer.getPhoneNo() == null || lawyer.getPhoneNo().isEmpty() || pattern.matcher(lawyer.getPhoneNo()).matches();
//        if (!validContact) return "01";
//        pattern = Pattern.compile("^((\\+|00)?88)?[- ]?0[- ]?(1[3-9]\\d{2}[- ]?\\d{6})(-\\d)?$");
//        validContact = lawyer.getMobileNo() == null || lawyer.getMobileNo().isEmpty() || pattern.matcher(lawyer.getMobileNo()).matches();
//        if (!validContact) return "02";
        return "1";
    }


    @Override
    public List<String> saveFromExcel(MultipartFile file) {
        Map<String, Object> cache = new HashMap<>();
        List<Sheet> sheets = excelFileUtils.getExcelSheetsFromMultipartFile(file);
        for (Sheet sheet : sheets) {
            extractXssfSheet(sheet, cache);
        }
        return (List<String>) cache.get("errors");
    }

    private void extractXssfSheet(Sheet table, Map<String, Object> cache) {

        List<String> errors = (List<String>) cache.getOrDefault("errors", new LinkedList<>());

        Lawyer lawyer;
        Iterator<Row> rows = table.iterator();

        // Skip rows untill the header row found and collect cell headers and indices
        Map<Integer, String> headerColumns = new HashMap<Integer, String>() {{
            put(1, "Area/ District");
            put(2, "Name of the Advocate");
        }};
        Map<String, Integer> headers = excelFileUtils.getColumnHeadersAndIndices(rows, headerColumns);

        while (rows.hasNext()) {
            try {
                Row row = rows.next();

                // Find the identifier of a row first
                String lawyerName = excelFileUtils.getTextCellValue(row, headers, "Name of the Advocate", errors);

                // Escape the empty rows(No identifier)
                if (lawyerName.isEmpty())
                    lawyerName = "-";

                // Read other column values
                String phone = excelFileUtils.getTextCellValue(row, headers, "Cell", errors)
                        .replaceAll("\\n", ",").replaceAll(",{2,}", ",");
                if (phone.matches(".*[.][0-9]+.*")) {
                    // convert decimal number to natural number
                    Number cellNumber = excelFileUtils.getNumberCellValue(row, headers, "Cell", errors);
                    phone = "" + cellNumber.longValue();
                }
                String courtJurisdiction = excelFileUtils.getTextCellValue(row, headers, "Area/ District", errors);
                String address = excelFileUtils.getTextCellValue(row, headers, "Address", errors);
                String branchCodes = excelFileUtils.getTextCellValue(row, headers, "Branch  Code", errors);

                // Prepare relational data
                List<Branch> branches = null;
                if (branchCodes.equalsIgnoreCase("All"))
                    branches = branchService.getActiveList();
                else {
                    List<String> branchCodeList = Arrays.asList(branchCodes.split(","))
                            .stream().map(code -> code.split("\\.")[0]).collect(Collectors.toList());
                    branches = branchService.getByBranchCode(branchCodeList);
                }

                lawyer = new Lawyer();

                // Set all collected data
                lawyer.setName(lawyerName);
                lawyer.setMobileNo(phone);
                lawyer.setBranch(branches);
                lawyer.setCourtJurisdiction(courtJurisdiction);
                lawyer.setPresentAddress(address);
                lawyer.setChamberAddress(address);
                lawyer.setCreatedBy(UserService.getSessionUsername());
                lawyer.setCreatedDate(new Date());

                // Save information for previous date
                repository.save(lawyer);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cache.put("errors", errors);
    }

}
