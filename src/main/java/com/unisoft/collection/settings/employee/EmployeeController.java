package com.unisoft.collection.settings.employee;
/*
Created by   Islam at 7/7/2019
*/

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicService;
import com.unisoft.collection.settings.branch.Branch;
import com.unisoft.collection.settings.branch.BranchService;
import com.unisoft.collection.settings.department.DepartmentService;
import com.unisoft.collection.settings.designation.DesignationEntity;
import com.unisoft.collection.settings.designation.DesignationService;
import com.unisoft.collection.settings.division.DivisionService;
import com.unisoft.collection.settings.employee.API.EmployeeAPIService;
import com.unisoft.collection.settings.employee.API.EmployeeApiPayload;
import com.unisoft.collection.settings.employee.API.EmployeeDetails;
import com.unisoft.collection.settings.employeeStatus.EmployeeStatusService;
import com.unisoft.collection.settings.employeeStatusManagement.EmployeeStatusManagerEntity;
import com.unisoft.collection.settings.employeeStatusManagement.EmployeeStatusManagerRepository;
import com.unisoft.collection.settings.employeeStatusManagement.EmployeeStatusmanagerService;
import com.unisoft.collection.settings.ivrSetupAndConfig.IVRRepository;
import com.unisoft.collection.settings.ivrSetupAndConfig.IvrEntity;
import com.unisoft.collection.settings.jobRole.JobRoleService;
import com.unisoft.collection.settings.location.LocationEntity;
import com.unisoft.collection.settings.location.LocationService;
import com.unisoft.collection.settings.unit.UnitService;
import com.unisoft.role.Role;
import com.unisoft.role.RoleService;
import com.unisoft.user.User;
import com.unisoft.user.UserRepository;
import com.unisoft.userrole.UserRoleDao;
import com.unisoft.userrole.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//import com.ibm.icu.util.Calendar;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/employee/")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DesignationService designationService;
    private final LocationService locationService;
    private final JobRoleService jobRoleService;
    private final DepartmentService departmentService;
    private final DivisionService divisionService;
    private final UnitService unitService;
    private final EmployeeStatusService employeeStatusService;
    private final PeopleAllocationLogicService peopleAllocationLogicService;
    private final BranchService branchService;

    @Autowired
    private EmployeeAPIService employeeAPIService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleDao userRoleDao;

    @Value("${ific.employee.api.user}")
    private String employeeAPIUsername;

    @Value("${ific.employee.api.pass}")
    private String employeeAPIPass;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeStatusmanagerService employeeStatusmanagerService;

    @Autowired
    private IVRRepository ivrRepository;

    @GetMapping(value = "list")
    public String viewAll(Model model) {
        List<EmployeeInfoEntity> activeEmployees = employeeService.getAll().stream().filter(e -> e.getEmployeeStatus().getName().equalsIgnoreCase("WORKING")).collect(Collectors.toList());
        model.addAttribute("empList", employeeService.getAll());
        model.addAttribute("activeEmployees", activeEmployees);
        return "collection/settings/employee/employee";
    }

    @GetMapping(value = "create")
    public String addPage(Model model) {
        model.addAttribute("entity", new EmployeeInfoEntity());
        return populateDateFormModel(model);
    }

    @GetMapping(value = "edit")
    public String editPage(Model model, @RequestParam(value = "id") Long id) {
        EmployeeInfoEntity entity = employeeService.getById(id);
        Role role = entity.getUser().getRoles().get(0);
        model.addAttribute("roles", roleService.getActiveList());
        model.addAttribute("desList", designationService.getActiveList());
        model.addAttribute("locationList", locationService.getActiveList());
        model.addAttribute("roleList", jobRoleService.getActiveList());
        model.addAttribute("deptList", departmentService.getActiveList());
        model.addAttribute("divList", divisionService.getActiveList());
        model.addAttribute("unitList", unitService.getActiveList());
        model.addAttribute("statusList", employeeStatusService.getAllActive());
        model.addAttribute("branches", branchService.getActiveList());
        model.addAttribute("entity", entity);
        model.addAttribute("user", entity.getUser());
        model.addAttribute("role",role);
        model.addAttribute("email",entity.getPin().substring(0,entity.getPin().indexOf("@")));
        //return populateDateFormModel(model);
        return "card/contents/settings/userInformation/edit";
    }

    private String populateDateFormModel(Model model) {
        model.addAttribute("desList", designationService.getActiveList());
        model.addAttribute("locationList", locationService.getActiveList());
        model.addAttribute("roleList", jobRoleService.getActiveList());
        model.addAttribute("deptList", departmentService.getActiveList());
        model.addAttribute("divList", divisionService.getActiveList());
        model.addAttribute("unitList", unitService.getActiveList());
        model.addAttribute("statusList", employeeStatusService.getAllActive());
        model.addAttribute("branches", branchService.getActiveList());
        return "collection/settings/employee/create";
    }

    private String populateDateFormModel1(Model model) {
        model.addAttribute("roles", roleService.getActiveList());
        model.addAttribute("desList", designationService.getActiveList());
        model.addAttribute("locationList", locationService.getActiveList());
        model.addAttribute("roleList", jobRoleService.getActiveList());
        model.addAttribute("deptList", departmentService.getActiveList());
        model.addAttribute("divList", divisionService.getActiveList());
        model.addAttribute("unitList", unitService.getActiveList());
        model.addAttribute("statusList", employeeStatusService.getAllActive());
        model.addAttribute("branches", branchService.getActiveList());
        return "card/contents/settings/userInformation/create";
    }

    @PostMapping(value = "create")
    public String saveNew(@ModelAttribute("entity") @Valid EmployeeInfoEntity employee, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            boolean isValid = isValidEmployee(employee, model);
            boolean isExist = true;
            if (employee.getId() == null) {
                isExist = isEmailExist(employee.getEmail());
            }
            if (isExist == true) {
                if (isValid) {
                    String output = employeeService.save(employee);
                    switch (output) {
                        case "1":
                            return "redirect:/collection/employee/list";
                        default:
                            model.addAttribute("error", output);
                    }
                }
            } else
                model.addAttribute("emailExist", true);

        }
        model.addAttribute("entity", employee);
        return populateDateFormModel(model);

    }


    @PostMapping(value = "create-emp")
    public String saveNewEmpFromApi(@ModelAttribute("entity") @Valid EmployeeInfoEntity employee, BindingResult result, Model model) {


        EmployeeDetails employeeInfo = employeeAPIService.getEmployeeInfo(new EmployeeApiPayload(employeeAPIUsername,employeeAPIPass.substring(2,employeeAPIPass.length()-2),employee.getEmail()+"@ificbankbd.com","",""));


        if(employee.getRoles().equalsIgnoreCase("dealer"))
            if(employee.getAgentId().equalsIgnoreCase(""))
                result.rejectValue("agentId","","Agent Id must not be empty!");

        if(employee.getId() == null)
            if(employeeService.existsByEmail(employeeInfo.getEMAIL_ADDRESS()))
                result.rejectValue("email","","Employee Email already exist, Try different one...");

        if (result.hasErrors()) {
            model.addAttribute("entity", employee);
            return populateDateFormModel1(model);
        }



        List<Integer> rolesId = new ArrayList<>();
        Role role = roleService.findByName(employee.getRoles());
        rolesId.add(role.getRoleId());
        EmployeeStatusManagerEntity employeeStatusManager;
        if (employeeService.existsByEmail(employeeInfo.getEMAIL_ADDRESS())) {
            User dbDataModel = userRepository.findUserByUsername(employeeInfo.getEMAIL_ADDRESS());
            dbDataModel.setUsername(employeeInfo.getEMAIL_ADDRESS());
            dbDataModel.setFirstName(employeeInfo.getFULL_NAME().split("\\s")[0]);
            dbDataModel.setLastName(employeeInfo.getFULL_NAME().split("\\s")[1]);
            dbDataModel.setEmployeeId(employeeInfo.getEMPLOYEE_ID());
            dbDataModel.setIsAgency(false);

            // LocationEntity locationEntity = new LocationEntity(new Long(103));
            LocationEntity locationEntity = locationService.getById(new Long(103));

            Branch branch = branchService.getByBranchCode(employeeInfo.getCBS_BRANCH_CODE());


            User response = userRepository.save(dbDataModel);

            EmployeeInfoEntity dbEmployeeModel = employeeService.getByUserId(response.getUserId());

            DesignationEntity designationEntity = designationService.findByName(employee.getRoles());
            if(designationEntity == null)
                designationEntity = designationService.findByName("Manager");
            dbEmployeeModel.setDesignation(designationEntity);
            dbEmployeeModel.setJoiningDate(new Date());
            dbEmployeeModel.setUnit(employee.getUnit());
            dbEmployeeModel.setUser(response);
            dbEmployeeModel.setLocation(locationEntity);
            dbEmployeeModel.setBranch(branch);
            dbEmployeeModel.setFullName(employeeInfo.getFULL_NAME());
            dbEmployeeModel.setPin(employeeInfo.getEMAIL_ADDRESS());
            dbEmployeeModel.setEmployeeStatus(employee.getEmployeeStatus());
            dbEmployeeModel.setEmail(employeeInfo.getEMAIL_ADDRESS());
            dbEmployeeModel.setOfficePhone(employeeInfo.getMOBILE_NO());
            dbEmployeeModel.setMisysId(employeeInfo.getMISYS_ID());
            dbEmployeeModel.setAgentId(employee.getAgentId());
            EmployeeInfoEntity test = employeeService.saveEmp(dbEmployeeModel);
            System.out.println("Done");

            employeeStatusManager = employeeStatusmanagerService.getByUserId(test.getUser().getUserId());
            employeeStatusManager.setEmployeeInfo(test);
            employeeStatusManager.setUserId(test.getUser().getUserId());
            employeeStatusManager.setEmployeeStatus(test.getEmployeeStatus());
            employeeStatusManager.setStartDate(test.getJoiningDate());
            employeeStatusManager.setStartDate(test.getJoiningDate());
            employeeStatusmanagerService.updateSts(employeeStatusManager);

            userRoleDao.insert(test.getUser().getUserId(), rolesId);

            if(employee.getRoles().equalsIgnoreCase("dealer")) {
                IvrEntity ivrEntity = ivrRepository.findByDealerPin(employeeInfo.getEMAIL_ADDRESS());
                if(ivrEntity == null)
                    ivrEntity = new IvrEntity();
                ivrEntity.setIvrAccId("550158");
                ivrEntity.setSkillId("AG");
                ivrEntity.setIvrAgentId(employee.getAgentId());
                ivrEntity.setDealerPin(employeeInfo.getEMAIL_ADDRESS());

                ivrRepository.save(ivrEntity);
            }


        } else {
            User dbDataModel = new User();
            dbDataModel.setUsername(employeeInfo.getEMAIL_ADDRESS());
            dbDataModel.setFirstName(employeeInfo.getFULL_NAME().split("\\s")[0]);
            dbDataModel.setLastName(employeeInfo.getFULL_NAME().split("\\s")[1]);
            dbDataModel.setEmployeeId(employeeInfo.getEMPLOYEE_ID());
            dbDataModel.setIsAgency(false);

            LocationEntity locationEntity = locationService.getById(new Long(103));

            Branch branch = branchService.getByBranchCode(employeeInfo.getCBS_BRANCH_CODE());


            User response = userRepository.save(dbDataModel);

            EmployeeInfoEntity dbEmployeeModel = new EmployeeInfoEntity();

            DesignationEntity designationEntity = designationService.findByName(employee.getRoles());
            if(designationEntity == null)
                designationEntity = designationService.findByName("Manager");
            dbEmployeeModel.setDesignation(designationEntity);
            dbEmployeeModel.setJoiningDate(new Date());
            dbEmployeeModel.setUnit(employee.getUnit());
            dbEmployeeModel.setUser(response);
            dbEmployeeModel.setLocation(locationEntity);
            dbEmployeeModel.setBranch(branch);
            dbEmployeeModel.setPin(employeeInfo.getEMAIL_ADDRESS());
            dbEmployeeModel.setFullName(employeeInfo.getFULL_NAME());
            dbEmployeeModel.setEmployeeStatus(employee.getEmployeeStatus());
            dbEmployeeModel.setEmail(employeeInfo.getEMAIL_ADDRESS());
            dbEmployeeModel.setOfficePhone(employeeInfo.getMOBILE_NO());
            dbEmployeeModel.setMisysId(employeeInfo.getMISYS_ID());
            dbEmployeeModel.setAgentId(employee.getAgentId());
            EmployeeInfoEntity employeeInfoEntity = employeeService.saveEmp(dbEmployeeModel);

            employeeStatusManager = new EmployeeStatusManagerEntity();
            employeeStatusManager.setEmployeeStatus(employeeInfoEntity.getEmployeeStatus());
            employeeStatusManager.setEmployeeInfo(employeeInfoEntity);
            employeeStatusManager.setStartDate(employeeInfoEntity.getJoiningDate());
            employeeStatusManager.setUserId(employeeInfoEntity.getUser().getUserId());
            //employeeStatusManager.setStartDate(entity.getJoiningDate());

            employeeStatusmanagerService.saveNew(employeeStatusManager);
            userRoleDao.insert(employeeInfoEntity.getUser().getUserId(), rolesId);

            if(employee.getRoles().equalsIgnoreCase("dealer")) {
                IvrEntity ivrEntity = ivrRepository.findByDealerPin(employeeInfo.getEMAIL_ADDRESS());
                if(ivrEntity == null)
                    ivrEntity = new IvrEntity();
                ivrEntity.setIvrAccId("550158");
                ivrEntity.setSkillId("AG");
                ivrEntity.setIvrAgentId(employee.getAgentId());
                ivrEntity.setDealerPin(employeeInfo.getEMAIL_ADDRESS());

                ivrRepository.save(ivrEntity);
            }

        }


        return "redirect:/collection/employee/list";

    }


//    @PostMapping(value = "create-emp")
//    public String saveNewEmpFromApi( Model model, EmployeeInfoEntity employee, BindingResult result) {
//
//
//        //EmployeeDetails employeeInfo = employeeAPIService.getEmployeeInfo(new EmployeeApiPayload(employeeAPIUsername, employeeAPIPass.substring(2, employeeAPIPass.length() - 2), employee.getEmail(), "", ""));
//        EmployeeDetails employeeInfo = new EmployeeDetails();
//
//        employeeInfo.setEMAIL_ADDRESS("mohit@ificbankbd.com");
//        employeeInfo.setFULL_NAME("Mohit Lal");
//        employeeInfo.setCBS_BRANCH_NAME("Head Branch");
//        employeeInfo.setCBS_BRANCH_MNEMONIC("HEB");
//
//
//
//        DesignationEntity designationEntity = designationService.findByName(employee.getRoles());
//        Role role = roleService.findByName(employee.getRoles());
//        List<Role> roles = new ArrayList<>();
//        roles.add(role);
//
//        EmployeeInfoEntity employeeInfoEntity;
//        if(employeeService.existsByEmail(employeeInfo.getEMAIL_ADDRESS())) {
//            employeeInfoEntity = employeeService.findByEmail(employeeInfo.getEMAIL_ADDRESS());
//            employee.setId(employeeInfoEntity.getId());
//        }
//
//        if(designationEntity == null)
//            designationEntity = designationService.findByName("Manager");
//
//        employee.setDesignation(designationEntity);
//
//
//        User user = userRepository.findUserByUsername(employeeInfo.getEMAIL_ADDRESS());
//        if(user == null)
//            user = new User();
//        String []name = employeeInfo.getFULL_NAME().split("\\s+");
//        String firstName = name[0];
//        String lastName = name.length>1?name[1]:name.length>2?name[1]+" "+name[2]:" ";
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setUsername(employeeInfo.getEMAIL_ADDRESS());
//        user.setEmployeeId(employeeInfo.getEMPLOYEE_ID());
//        //user.setRoles(roles);
//        //user.setRoles(roles);
//
//        employee.setPin(employeeInfo.getEMAIL_ADDRESS());
//        employee.setEmail(employeeInfo.getEMAIL_ADDRESS());
//        employee.setJoiningDate(new Date());
//        //employee.setJoiningDate(new SimpleDateFormat("MMM").format(new Date()));
////        try {
////            employee.setJoiningDate(simpleDateFormat1.parse(simpleDateFormat.format(new Date())));
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
//        //employee.setEmployeeStatus(employeeStatusService.findByName("Working"));
//        employee.setUser(user);
//
//        if(employee.getRoles().equalsIgnoreCase("dealer"))
//            if(employee.getAgentId().equalsIgnoreCase(""))
//                result.rejectValue("agentId","","Agent Id must not be empty!");
//
//        if (!result.hasErrors()) {
//            boolean isValid = isValidEmployee(employee, model);
//            boolean isExist = true;
//            if (employee.getId() == null){
//                isExist = isEmailExist(employee.getEmail());
//            }
//            if (isExist == true){
//                if (isValid) {
//                    String output = employeeService.SaveEmp(employee);
//                    List<Integer> rolesId = new ArrayList<>();
//                    rolesId.add(role.getRoleId());
//                    userRoleDao.insert(employee.getUser().getUserId(),rolesId);
//                    switch (output) {
//                        case "1":
//                            return "redirect:/collection/employee/list";
//                        default:
//                            model.addAttribute("error", output);
//                    }
//                }
//            }else
//                model.addAttribute("emailExist", true);
//
//        }
//        model.addAttribute("entity", employee);
//        return populateDateFormModel1(model);
//
//    }

    private boolean isEmailExist(String email) {
        EmployeeInfoEntity employeeInfoEntity = employeeService.findByEmail(email);
        if (employeeInfoEntity == null) {
            return true;
        }
        return false;
    }

//    @GetMapping(value = "view")
//    public String viewPage(@RequestParam(value = "id") Long id, Model model) {
//        EmployeeInfoEntity employee = employeeService.getById(id);
//        String age = employee.getDOB() != null ? diffDate(employee.getDOB(), new Date()) : "Missing Date of Birth";
//        String workingYear = employee.getJoiningDate() != null ? diffDate(employee.getJoiningDate(), new Date()) : "Missing Joining Date";
//        String unit = employee.getUnit();
//        String[] units = (unit != null) ? employee.getUnit().split(",") : new String[1];
//        PeopleAllocationLogicInfo allocationLogicInfo = peopleAllocationLogicService.getByDealerId(employee, units[0]);
//        if (allocationLogicInfo != null) {
//            User user = null;
//            String designation = employee.getDesignation().getName().replaceAll("[^a-zA-Z]", " ").toLowerCase();
//            switch (designation) {
//                case "dealer":
//                    user = allocationLogicInfo.getTeamlead().getUser();
//                    model.addAttribute("teamLeader", user.getFirstName() + " " + user.getLastName());
//                case "teamleader":
//                    user = allocationLogicInfo.getSupervisor().getUser();
//                    model.addAttribute("supervisor", user.getFirstName() + " " + user.getLastName());
//                case "supervisor":
//                    user = allocationLogicInfo.getManager().getUser();
//                    model.addAttribute("manager", user.getFirstName() + " " + user.getLastName());
//            }
//        }
//        model.addAttribute("age", age);
//        model.addAttribute("workingYear", workingYear);
//        model.addAttribute("entity", employee);
//        return "collection/settings/employee/view";
//    }

    public String diffDate(Date start, Date end) {

        if (start == null) return "Not definable";
        if (end == null) end = new Date();

        Calendar calStart = Calendar.getInstance(Locale.US);
        calStart.setTime(start);

        Calendar calEnd = Calendar.getInstance(Locale.US);
        calEnd.setTime(end);

        int year = calEnd.get(Calendar.YEAR) - calStart.get(Calendar.YEAR);
        int month = 0;
        if (calEnd.get(Calendar.MONTH) < calStart.get(Calendar.MONTH)) {
            year = year - 1;
            month = 12 - calEnd.get(Calendar.MONTH) - 1;
            month = month + calStart.get(Calendar.MONTH) - 1;
            month = month + calStart.get(Calendar.MONTH);

        } else {
            month = calEnd.get(Calendar.MONTH) - calStart.get(Calendar.MONTH);
        }

        return year + " Years and " + month + " Months";
    }

    @ResponseBody
    @GetMapping(value = "check")
    public boolean checkRoom(@RequestParam("pin") String pin) {
        return employeeService.existsByPin(pin);
    }

    @ResponseBody
    @GetMapping(value = "checkByEmail")
    public boolean checkByEmail(@RequestParam("email") String email) {
        return employeeService.existsByEmail(email);
    }

    private boolean isValidEmployee(EmployeeInfoEntity employee, Model model) {
        boolean isValid = true;

//            if (!employeeService.isValidPhone(employee.getHomePhone())) {
//                model.addAttribute("invalidHomePhone", true);
//                isValid = false;
//            }

//            if (!employeeService.isValidPhone(employee.getOfficePhone())) {
//                model.addAttribute("invalidOfficePhone", true);
//                isValid = false;
//            }

        if (employee.getDesignation().getId() == null) {
            isValid = false;
            model.addAttribute("designationValidation", true);
        }
//        if (employee.getDepartment().getId() == null) {
//            isValid = false;
//            model.addAttribute("departmentValidation", true);
//        }
        return isValid;
    }


//    @GetMapping(value = "pending-profile")
//    public String pendingProfile(Model model) {
//        List<EmployeeInfoEntity>employeeInfoEntityList=employeeService.getAll();
//        List<EmployeeInfoEntity> empPendingProfileList = employeeInfoEntityList.stream()
//                .filter(employeeInfoPendingList -> employeeInfoPendingList.getLocation().getId() == null
//                        || employeeInfoPendingList.getHomePhone() == null
//                        || employeeInfoPendingList.getJobNature() == null
//                        /* || employeeInfoPendingList.getJobRole().getId() == null*/
//                        || employeeInfoPendingList.getJoiningDate() == null
//                        || employeeInfoPendingList.getGender() == null
//                        || employeeInfoPendingList.getDOB() == null
//                        || employeeInfoPendingList.getBloodGroup() == null
//                        || employeeInfoPendingList.getEmergencyContactPerson() == null
//                        || employeeInfoPendingList.getEmergencyContactNo() == null
//                        || employeeInfoPendingList.getEmergencyContactRel() == null
//                        || employeeInfoPendingList.getPresentAddress() == null
//                        || employeeInfoPendingList.getPermanentAddress() == null
//                        || employeeInfoPendingList.getOfficeAddress() == null
//                        || employeeInfoPendingList.getIpAddress() == null
//                        || employeeInfoPendingList.getIpPhoneNo() == null
//                        || employeeInfoPendingList.getMobileLimit() == 0.0
//                        || employeeInfoPendingList.getDivision().getDivId() == null
//                        || employeeInfoPendingList.getEmployeeStatus().getId() == null
//                        || employeeInfoPendingList.getSignature() == null
//                        || employeeInfoPendingList.getAdviceLetter() == null
//                        || employeeInfoPendingList.getAdviceLetterDate() == null
//                        || employeeInfoPendingList.getDomainId() == null
//                        || employeeInfoPendingList.getLoanAccountNo() == null
//                        || employeeInfoPendingList.getCreditCardNo() == null
//                        || employeeInfoPendingList.getClientId() == null
//                        || employeeInfoPendingList.getAccountNo() == null
//                        || employeeInfoPendingList.getCif() == null
//                        || employeeInfoPendingList.getFatherName() == null
//                        || employeeInfoPendingList.getMotherName() == null
//                        || employeeInfoPendingList.getServiceTag()== null
//                        || employeeInfoPendingList.getAssetTag()== null
//                        || employeeInfoPendingList.getSpouseName()==null
//                        || employeeInfoPendingList.getSpousePhone()==null
//                        || employeeInfoPendingList.getLastEducation()==null
//                        || employeeInfoPendingList.getDomicileAddress()==null
//                        || employeeInfoPendingList.getPcBrand()==null
//                        || employeeInfoPendingList.getPcModel()==null
//                        || employeeInfoPendingList.getTrainingDetails()==null
//                        || employeeInfoPendingList.getHostName()==null
//                        || employeeInfoPendingList.getPemail()==null
//                        || employeeInfoPendingList.getNid()==null
//                        || employeeInfoPendingList.getETin()==null
//                        || employeeInfoPendingList.getMaritalStatus()==null
//                        || employeeInfoPendingList.getPhoto()==null
//
//                )
//                .collect(Collectors.toList());
//
//        model.addAttribute("empList", empPendingProfileList);
//        return "collection/settings/employee/employeependingprofiles";
//    }


    @GetMapping(value = "edit-pending")
    public String editPendingPage(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("entity", employeeService.getById(id));

        model.addAttribute("desList", designationService.getActiveList());
        model.addAttribute("locationList", locationService.getActiveList());
        model.addAttribute("roleList", jobRoleService.getActiveList());
        model.addAttribute("deptList", departmentService.getActiveList());
        model.addAttribute("divList", divisionService.getActiveList());
        model.addAttribute("unitList", unitService.getActiveList());
        model.addAttribute("statusList", employeeStatusService.getAllActive());
        return "collection/settings/employee/editPending";
    }


//    @GetMapping(value = "pending-view")
//    public String pendingViewPage(@RequestParam(value = "id") Long id, Model model) {
//        EmployeeInfoEntity employee = employeeService.getById(id);
//        String age = employee.getDOB() != null ? diffDate(employee.getDOB(), new Date()) : "Missing Date of Birth";
//        String workingYear = employee.getJoiningDate() != null ? diffDate(employee.getJoiningDate(), new Date()) : "Missing Joining Date";
//        String unit = employee.getUnit();
//        String[] units = (unit != null) ? employee.getUnit().split(",") : new String[1];
//        PeopleAllocationLogicInfo allocationLogicInfo = peopleAllocationLogicService.getByDealerId(employee, units[0]);
//        if (allocationLogicInfo != null) {
//            User user = null;
//            String designation = employee.getDesignation().getName().replaceAll("[^a-zA-Z]", " ").toLowerCase();
//            switch (designation) {
//                case "dealer":
//                    user = allocationLogicInfo.getTeamlead().getUser();
//                    model.addAttribute("teamLeader", user.getFirstName() + " " + user.getLastName());
//                case "teamleader":
//                    user = allocationLogicInfo.getSupervisor().getUser();
//                    model.addAttribute("supervisor", user.getFirstName() + " " + user.getLastName());
//                case "supervisor":
//                    user = allocationLogicInfo.getManager().getUser();
//                    model.addAttribute("manager", user.getFirstName() + " " + user.getLastName());
//            }
//        }
//        model.addAttribute("age", age);
//        model.addAttribute("workingYear", workingYear);
//        model.addAttribute("entity", employee);
//        return "collection/settings/employee/pendingView";
//    }


    @GetMapping(value = "/findByDesignation")
    @ResponseBody
    public List<EmployeeInfoDto> getEmployeeByDesignation(@RequestParam Long id) {

        return employeeService.getByDesignationAndUnit(id);
    }

    @GetMapping("/upload-excel")
    public String employeeUpload() {
        return "collection/settings/employee/uploademployee";
    }

    @PostMapping("/upload-excel")
    public String employeeUpload(@RequestParam("file") MultipartFile multipartFile, HttpSession session) {
        List errors = employeeService.saveEmployeeFromExcel(multipartFile);
        session.setAttribute("errors", errors);
        return "redirect:list";
    }

}
