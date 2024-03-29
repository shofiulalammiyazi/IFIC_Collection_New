package com.unisoft.collection.settings.employee;
/*
Created by   Islam at 7/7/2019
*/

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.branch.BranchRepository;
import com.unisoft.collection.settings.department.DepartmentEntity;
import com.unisoft.collection.settings.department.DepartmentRepository;
import com.unisoft.collection.settings.designation.DesignationEntity;
import com.unisoft.collection.settings.designation.DesignationService;
import com.unisoft.collection.settings.employeeStatus.EmployeeStatusEntity;
import com.unisoft.collection.settings.employeeStatus.EmployeeStatusRepository;
import com.unisoft.collection.settings.employeeStatusManagement.EmployeeStatusManagerEntity;
import com.unisoft.collection.settings.employeeStatusManagement.EmployeeStatusManagerRepository;
import com.unisoft.collection.settings.employeeStatusManagement.EmployeeStatusmanagerService;
import com.unisoft.user.User;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserRepository;
import com.unisoft.user.UserService;
import com.unisoft.utillity.ExcelFileUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final UserService userService;
    private final EmployeeStatusmanagerService employeeStatusmanagerService;
    private final EmployeeRepository repository;
    private final AuditTrailService auditTrailService;
    private final EmployeeDao employeeDao;
    private final ExcelFileUtils excelFileUtils;
    private final DesignationService designationService;
    private final DepartmentRepository departmentRepository;
    private final BranchRepository branchRepository;
    private final EmployeeStatusRepository employeeStatusRepository;

    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public List<EmployeeInfoEntity> getAll() {
        return repository.findAll();
    }

    public EmployeeInfoEntity saveEmp(EmployeeInfoEntity employeeInfoEntity){

        EmployeeInfoEntity employeeInfoEntity1 = repository.saveAndFlush(employeeInfoEntity);
        return employeeInfoEntity1;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeStatusManagerRepository employeeStatusManagerRepository;

    public String SaveEmp(EmployeeInfoEntity employeeInfoEntity){
        EmployeeStatusManagerEntity employeeStatusManager;
        userRepository.save(employeeInfoEntity.getUser());

        if (employeeInfoEntity.getUser().getUserId() == null)
            return "Something went wrong. Couldn't create user";

        employeeRepository.save(employeeInfoEntity);

        employeeStatusManager = employeeStatusmanagerService.getByUserId(employeeInfoEntity.getUser().getUserId());
        if(employeeStatusManager == null)
            employeeStatusManager = new EmployeeStatusManagerEntity();
        employeeStatusManager.setEmployeeInfo(employeeInfoEntity);
        employeeStatusManager.setUserId(employeeInfoEntity.getUser().getUserId());
        employeeStatusManager.setEmployeeStatus(employeeInfoEntity.getEmployeeStatus());
        employeeStatusManager.setStartDate(employeeInfoEntity.getJoiningDate());
        employeeStatusManagerRepository.save(employeeStatusManager);

        auditTrailService.saveCreatedData("Employee", employeeInfoEntity);

        return "1";
    }

    public String save(EmployeeInfoEntity entity) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeStatusManagerEntity employeeStatusManager;
        if (entity.getId() == null) {
            entity.setCreatedBy(userPrincipal.getUsername());
            entity.setCreatedDate(new Date());
            entity.setEnabled(true);

            // Save user
            userService.saveOrUpdate(entity.getUser(), entity);
            if (entity.getUser().getUserId() == null)
                return "Something went wrong. Couldn't create user";

            //logger.debug("role back:   "+ TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());

            // Save employee
            saveEmp(entity);

            // Keep audit trail
            auditTrailService.saveCreatedData("Employee", entity);

            // Mark the employee with given status
            employeeStatusManager = new EmployeeStatusManagerEntity();
            employeeStatusManager.setEmployeeStatus(entity.getEmployeeStatus());
            employeeStatusManager.setEmployeeInfo(entity);
            employeeStatusManager.setStartDate(entity.getJoiningDate());
            employeeStatusManager.setUserId(entity.getUser().getUserId());
            //employeeStatusManager.setStartDate(entity.getJoiningDate());

            employeeStatusmanagerService.saveNew(employeeStatusManager);

        } else {
            EmployeeInfoEntity oldEntity = repository.findByPin(entity.getPin());
            EmployeeInfoEntity previousEntity = new EmployeeInfoEntity();
            if (oldEntity !=null)   // new condition
                BeanUtils.copyProperties(oldEntity, previousEntity);

            entity.setModifiedBy(userPrincipal.getUsername());
            entity.setModifiedDate(new Date());

            userService.saveOrUpdate(entity.getUser(), entity);

            repository.save(entity);
            auditTrailService.saveUpdatedData("Employee", previousEntity, entity);

            // Update employee status
            employeeStatusManager = employeeStatusmanagerService.getByUserId(entity.getUser().getUserId());
            employeeStatusManager.setEmployeeInfo(entity);
            employeeStatusManager.setUserId(entity.getUser().getUserId());
            employeeStatusManager.setEmployeeStatus(entity.getEmployeeStatus());
            employeeStatusManager.setStartDate(entity.getJoiningDate());
            employeeStatusManager.setStartDate(entity.getJoiningDate());
            employeeStatusmanagerService.updateSts(employeeStatusManager);
        }
        return "1";
    }

    public List<EmployeeInfoEntity> getDealerFromDao() {
        return employeeDao.getDealerList();
    }

    public List<EmployeeInfoEntity> getActiveList() {
        return repository.findByEnabled(true);
    }

    public List<EmployeeInfoDto> findEmployeePinsByUnitAndDesignation(List<String> designations, String unit, List<String> exceptionUnits) {
        return repository.findEmployeePinsByUnitAndDesignation(designations, unit, exceptionUnits)
                .stream()
                .map(EmployeeInfoDto::new)
                .collect(Collectors.toList());
    }

    public EmployeeInfoEntity getById(Long id) {
        return repository.findById(id).orElse(new EmployeeInfoEntity());
    }

    public EmployeeInfoEntity getByPin(String pin) {
        return Optional.ofNullable(repository.findByPin(pin)).orElse(new EmployeeInfoEntity());
    }

    public EmployeeInfoEntity findByEmail1(String email){
        return  repository.findByEmail(email);
    }

    public List<Long> getIdList() {
        return repository.findIdList();
    }

    public boolean existsByPin(String pin) {
        return repository.existsByPin(pin);
    }

    public boolean existsByEmail(String email) {
        boolean status =  repository.existsByEmail(email);
        return status;
    }

    public List<EmployeeInfoEntity> getListByDesignation(String designation) {
        return repository.findByDesignationNameOrderByUserFirstNameAsc(designation);
    }

    public List<EmployeeInfoEntity> getDealerList() {
        return repository.findByDesignationNameOrderByUserFirstNameAsc("Dealer");
    }

    public List<EmployeeInfoEntity> getManagerList() {
        return repository.findByDesignationNameOrderByUserFirstNameAsc("Manager");
    }


    public List<EmployeeInfoEntity> getTeamLeaderList() {
        return repository.findByDesignationNameOrderByUserFirstNameAsc("Team Leader");
    }

    public List<EmployeeInfoEntity> getSuperVisorList() {
        return repository.findByDesignationNameOrderByUserFirstNameAsc("Supervisor");
    }

//    public boolean checkEmployeeAllField(EmployeeInfoEntity employeeInfoEntity){
//        if(employeeInfoEntity.getLocation().getId() == null
//                || employeeInfoEntity.getHomePhone() == null
//                || employeeInfoEntity.getJobNature() == null
//                /* || employeeInfoEntity.getJobRole().getId() == null*/
//                || employeeInfoEntity.getJoiningDate() == null
//                || employeeInfoEntity.getGender() == null
//                || employeeInfoEntity.getDOB() == null
//                || employeeInfoEntity.getBloodGroup() == null
//                || employeeInfoEntity.getEmergencyContactPerson() == null
//                || employeeInfoEntity.getEmergencyContactNo() == null
//                || employeeInfoEntity.getEmergencyContactRel() == null
//                || employeeInfoEntity.getPresentAddress() == null
//                || employeeInfoEntity.getPermanentAddress() == null
//                || employeeInfoEntity.getOfficeAddress() == null
//                || employeeInfoEntity.getIpAddress() == null
//                || employeeInfoEntity.getIpPhoneNo() == null
//                || employeeInfoEntity.getMobileLimit() == 0.0
//                || employeeInfoEntity.getDivision().getDivId() == null
//                || employeeInfoEntity.getEmployeeStatus().getId() == null
//                || employeeInfoEntity.getAdviceLetter() == null
//                || employeeInfoEntity.getAdviceLetterDate() == null
//                || employeeInfoEntity.getDomainId() == null
//                || employeeInfoEntity.getLoanAccountNo() == null
//                || employeeInfoEntity.getCreditCardNo() == null
//                || employeeInfoEntity.getClientId() == null
//                || employeeInfoEntity.getAccountNo() == null
//                || employeeInfoEntity.getCif() == null
//                || employeeInfoEntity.getFatherName() == null
//                || employeeInfoEntity.getMotherName() == null
//                || employeeInfoEntity.getServiceTag()== null
//                || employeeInfoEntity.getAssetTag()== null
//                || employeeInfoEntity.getSpouseName()==null
//                || employeeInfoEntity.getSpousePhone()==null
//                || employeeInfoEntity.getLastEducation()==null
//                || employeeInfoEntity.getDomicileAddress()==null
//                || employeeInfoEntity.getPcBrand()==null
//                || employeeInfoEntity.getPcModel()==null
//                || employeeInfoEntity.getTrainingDetails()==null
//                || employeeInfoEntity.getHostName()==null
//                || employeeInfoEntity.getPemail()==null
//                || employeeInfoEntity.getNid()==null
//                || employeeInfoEntity.getETin()==null
//                || employeeInfoEntity.getMaritalStatus()==null){
//            return false;
//        }else {
//            return true;
//        }
//    }

    public boolean isValidPhone(String phoneNo) {
        //validating phone number with -, . or spaces
        if (phoneNo.matches("[+\\s]\\d{2}\\d{5}[-\\ \\s]\\d{6}")) return true;
            //validating phone number with extension length from 3 to 5
        else if (phoneNo.matches("\\d{2}\\d{5}[-\\ \\s]\\d{6}")) return true;
            //validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\d{5}[-\\ \\s]\\d{6}")) return true;
        else if (phoneNo.matches("\\d{5}\\d{6}")) return true;
            //return false if nothing matches the input
        else return false;
    }

    public EmployeeInfoEntity findByEmail(String email) {
        EmployeeInfoEntity employeeInfoEntity = repository.findEmployeeInfoEntityByEmail(email);
        return employeeInfoEntity;
    }

    public EmployeeInfoEntity getByUserId(Long userId) {
        EmployeeInfoEntity employeeInfoEntity = repository.findByUserId(userId);
        return employeeInfoEntity;
    }

    public List<EmployeeInfoEntity> getUnitWiseDealer(String dealer, String unit) {
        List<EmployeeInfoEntity> unitWiseDealer = repository.findByDesignationNameAndUnitOrderByUserFirstNameAsc(dealer, unit);
        return unitWiseDealer;
    }

    public List<EmployeeInfoDto> getByDesignationAndUnit(Long id){
        List<EmployeeInfoEntity> employeeInfoEntities = repository.findByDesignationIdAndUnit(id);

        List<EmployeeInfoDto> employeeInfoDtos = new ArrayList<>();
        for(EmployeeInfoEntity emp : employeeInfoEntities){
            EmployeeInfoDto employeeInfoDto = new EmployeeInfoDto(emp);

            employeeInfoDtos.add(employeeInfoDto);
        }
        return employeeInfoDtos;
    }

    public List<String> saveEmployeeFromExcel(MultipartFile file) {
        Map<String, Object> cache = new HashMap<>();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            int totalSheets = xssfWorkbook.getNumberOfSheets();

            for (int i = 0; i < totalSheets; i++)
                extractXssfSheet(xssfWorkbook.getSheetAt(i), cache);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (List<String>) cache.get("errors");
    }


    @SuppressWarnings("unchecked")
    private void extractXssfSheet(XSSFSheet table, Map<String, Object> cache) {

        List<String> errors = (List<String>) cache.getOrDefault("errors", new LinkedList<>());

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeStatusManagerEntity employeeStatusManager;
        EmployeeInfoEntity employee;
        DesignationEntity designationEntity;
        DepartmentEntity departmentEntity;
        EmployeeStatusEntity employeeStatusEntity;
        Branch branch;
        User user;
        Iterator<Row> rows = table.iterator();

        // Skip rows untill the header row found and collect cell headers and indices
        Map<Integer, String> headerColumns = new HashMap<Integer, String>() {{
            put(0, "Employee Id");
        }};
        Map<String, Integer> headers = excelFileUtils.getColumnHeadersAndIndices(rows, headerColumns);

        while(rows.hasNext()){
            try{
                Row row = rows.next();
                employee = new EmployeeInfoEntity();

                String employeeId = excelFileUtils.getTextCellValue(row, headers, "Employee Id", errors);
                String firstName = excelFileUtils.getTextCellValue(row, headers, "First Name", errors);
                String lastName = excelFileUtils.getTextCellValue(row, headers, "Last Name", errors);
                String designation = excelFileUtils.getTextCellValue(row, headers, "Designation", errors);
                String officePhone = excelFileUtils.getTextCellValue(row, headers, "Office Phone", errors);
                String emergencyContactPerson = excelFileUtils.getTextCellValue(row, headers, "Emergency Contact Person", errors);
                String unit = excelFileUtils.getTextCellValue(row, headers, "Unit", errors);
                String department = excelFileUtils.getTextCellValue(row, headers, "Department", errors);
                String branchName = excelFileUtils.getTextCellValue(row, headers, "Branch Name", errors);
                String officeEmail = excelFileUtils.getTextCellValue(row, headers, "Office Email Address", errors);
                String nid = excelFileUtils.getTextCellValue(row, headers, "NID", errors);
                String eTin = excelFileUtils.getTextCellValue(row, headers, "E-TIN", errors);

                designationEntity = designationService.findByName(designation);
                departmentEntity = departmentRepository.findByName(department);
                branch = branchRepository.findFirstByBranchNameIgnoreCase(branchName);
                employeeStatusEntity = employeeStatusRepository.findByName("Working");

                employee.setPin(employeeId);
                employee.setDesignation(designationEntity);
//                employee.setDepartment(departmentEntity);
                employee.setBranch(branch);
                employee.setUnit(unit);
                employee.setOfficePhone(officePhone);
//                employee.setEmergencyContactNo(emergencyContactPerson);
                employee.setEmail(officeEmail);
//                employee.setNid(nid);
//                employee.setETin(eTin);
                employee.setEmployeeStatus(employeeStatusEntity);

                employee.setCreatedBy(userPrincipal.getUsername());
                employee.setCreatedDate(new Date());
                employee.setEnabled(true);

                user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setIsAgency(false);

                // Save User
                userService.saveOrUpdate(user, employee);

                User user1 = userService.getUserByUsername(employeeId);

                employee.setUser(user1);

                // Save Employee
                EmployeeInfoEntity employee1 = repository.save(employee);

                // Keep audit trail
                auditTrailService.saveCreatedData("Employee", employee);

                // Mark the employee with given status

                employeeStatusManager = new EmployeeStatusManagerEntity();
                employeeStatusManager.setEmployeeStatus(employee1.getEmployeeStatus());
                employeeStatusManager.setEmployeeInfo(employee1);
                employeeStatusManager.setStartDate(employee1.getJoiningDate() == null ? null : employee.getJoiningDate());
                employeeStatusManager.setUserId(user1.getUserId());

                employeeStatusmanagerService.saveNew(employeeStatusManager);

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}


