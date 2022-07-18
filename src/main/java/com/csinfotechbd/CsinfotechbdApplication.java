package com.csinfotechbd;

import com.csinfotechbd.collection.dashboard.DashboardDao;
import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@Slf4j
@SpringBootApplication
public class CsinfotechbdApplication implements CommandLineRunner {

//	@Autowired
//	private EmployeeService employeeService;
//	@Autowired
//	private DealerPerformanceRepository dealerPerformanceRepository;
//	@Autowired
//	private RoleService roleService;
//	@Autowired
//	private RoleToUrlRepository roleToUrlRepository;
//	@Autowired
//	private CardAccountBasicRepository cardAccountBasicRepository;
//	@Autowired
//	private ProductTypeRepository productTypeRepository;
//	@Autowired
//	private ConnectPlusRepository connectPlusRepository;
//	@Autowired
//	private CardAccountDistributionRepository cardAccountDistributionRepository;
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private EmployeeStatusmanagerService employeeStatusmanagerService;
//	@Autowired
//	private DesignationService designationService;
//	@Autowired
//	private DepartmentService departmentService;
//	@Autowired
//	private EmployeeStatusService employeeStatusService;
//	@Autowired
//	private LoanPaymentDao loanPaymentDao;
//	@Autowired
//	private LoanPaymentRepository loanPaymentRepository;
//	@Autowired
//	private TextToUrlBaseRepository textToUrlBaseRepository;
//	@Autowired
//	private LoanAccountBasicRepository loanAccountBasicRepository;
//	@Autowired
//	private LoanAccountDistributionRepository loanAccountDistributionRepository;
//	@Autowired
//	private LoanAccountOtherService loanAccountOtherService;
//	@Autowired
//	private LoanAccountRepository loanAccountRepository;
//	@Autowired
//	private ProductGroupRepository productGroupRepository;
//	@Autowired
//	private VechileRepository vechileRepository;
//	@Autowired
//	private ScheduledCornJobCardDistribution scheduledCornJobCardDistribution;
//	@Autowired
//	private EmployeeRepository employeeRepository;
//	@Autowired
//	private AgencyService agencyService;

//	public static final String ACCOUNT_SID = "ACe4e19a87caf97381ae88b2ff0b36b630";
//	public static final String AUTH_TOKEN = "8b134423e8b286a90c238899af138749";
//	public static final String TWILIO_NUMBER = "+12033500615";
	@Autowired
	private DashboardDao dashboardDao;

//	private static final Logger logger = LoggerFactory.getLogger(CsinfotechbdApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CsinfotechbdApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		//scheduledCornJobCardDistribution.calculateKpi();

//		List<Integer> idList = Arrays.asList(
//				3844,
//				3847,
//				3850,
//				3855,
//				3858,
//				3861,
//				3864,
//				3867,
//				3870,
//				3873
//		);
//
//		List<DealerPerformance> dealerPerformances = new ArrayList<>();
//
//		for(Integer id: idList){
//			EmployeeInfoEntity employeeInfoEntity = new EmployeeInfoEntity();
//			employeeInfoEntity.setId(new Long(id));
//
//			DealerPerformance dealerPerformance = new DealerPerformance();
//			dealerPerformance.setOverAllKpi(id);
//			dealerPerformance.setEmployeeInfoEntity(employeeInfoEntity);
//			dealerPerformances.add(dealerPerformance);
//		}
//		for(DealerPerformance d: dealerPerformances)
//			dealerPerformanceRepository.save(d);

//		try {
//			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
//
//			// Build a filter for the MessageList
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("Body", "Hello, Monir Vai! The ultimate support guy"));
//			params.add(new BasicNameValuePair("To", "+8801782719498")); //Add real number here
//			params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
//
//			MessageFactory messageFactory = client.getAccount().getMessageFactory();
//			Message message = messageFactory.create(params);
//			System.out.println(message.getSid());
//		}
//		catch (TwilioRestException e) {
//			System.out.println(e.getErrorMessage());
//		}


//		LocalDate lastWorkingDayOfMonth = LocalDate.now().withDayOfMonth(1);
//		logger.info(lastWorkingDayOfMonth.minusDays(1)+"");
//		Date date = java.sql.Date.valueOf(lastWorkingDayOfMonth.minusDays(1));
//		System.out.println(date);
//		Gson gson = new Gson();
//		Role role = roleService.findById(1);
//		RoleToUrl roleToUrl = roleToUrlRepository.findByRole(role);
//
//
//		String[] urls = gson.fromJson(roleToUrl.getUrl(), String[].class);
//		List<String> list = new LinkedList<String>(Arrays.asList(urls));
//		list.add("/profile/loan");
//		roleToUrl.setUrl(gson.toJson(list));
//		roleToUrlRepository.save(roleToUrl);
//		logger.info("Saved Successfully");
//		Optional<ConnectPlus> connectPlus = connectPlusRepository.findById("LL00000170");
//		if(connectPlus.isPresent()){
//			System.out.println(connectPlus.get());
//		}

//		DesignationEntity designationEntity = designationService.getById(new Long("40"));
//		DepartmentEntity departmentEntity = departmentService.getById(new Long("479226"));
//
//		EmployeeInfoEntity employee = new EmployeeInfoEntity();
//
//		employee.setCreatedDate(new Date());
//		employee.setEnabled(true);
//		employee.setDesignation(designationEntity);
//		employee.setDepartment(departmentEntity);
//		employee.setUnit("Loan, Card");
//		employee.setIpAddress("10.100.130.164 WiFi");
//		employee.setPin("25260");
//
//		User user = new User();
//		user.setFirstName("HASNAIN MOHAMMAD ABID ZAMAN");
//		user.setCreatedDate(new Date());
//		user.setPassword("A123");
//		user.setUsername("25260");
//		user.setEmployeeId("25260");
//		user.setStatus(true);
//		user.setLastName(".");
//
//		userService.saveOrUpdate(user, employee.getPin());
//		employee.setUser(user);
//		boolean save = employeeService.saveEmp(employee);
//
//		EmployeeStatusManagerEntity employeeStatusManager = new EmployeeStatusManagerEntity();
//
//		employeeStatusManager.setEmployeeStatus(null);
//		employeeStatusManager.setEmployeeInfo(employee);
//		employeeStatusManager.setStartDate(new Date());
//		employeeStatusManager.setUserId((userService.getUserByUsername(employee.getUser().getUsername())).getUserId());
//
//		employeeStatusmanagerService.saveNew(employeeStatusManager);


//		List<LoanPayment> loanPayments = loanPaymentRepository.findAll();
//		for(LoanPayment l: loanPayments){
//			Double loanPayment = loanPaymentDao.getLoanPayment(l.getAccountNo());
//			if(loanPayment == l.getAmount()){
//				double payment = loanPayment - l.getAmount();
//				LoanPaymentDetails loanPaymentDetails = new LoanPaymentDetails(new Date(), l.getAccountNo(), payment);
//				l.getLoanPaymentDetails().add(loanPaymentDetails);
//				loanPaymentRepository.save(l);
//			}
//		}

//		List<TextToUrlBase> textToUrlBases = textToUrlBaseRepository.findAll();
//		for(TextToUrlBase t: textToUrlBases){
//			List<TextToUrlChild> textToUrlChildren = t.getTextToUrlChildren();
//			for(TextToUrlChild c: textToUrlChildren){
//				if(c.getText().equals("Checker")) c.setUrl(t.getText()+"-Checker");
//			}
//		}
//		textToUrlBaseRepository.saveAll(textToUrlBases);

//		List<DealerPerformance> dealerPerformances = new ArrayList<>();
//		List<EmployeeInfoEntity> dealterList = employeeService.getDealerList();
//
//		for(int i=0;i<dealterList.size(); i++){
//			DealerPerformance dealerPerformance = new DealerPerformance();
//			dealerPerformance.setOverAllKpi(i);
//			dealerPerformance.setEmployeeInfoEntity(dealterList.get(i));
//			dealerPerformances.add(dealerPerformance);
//		}
//
//		dealerPerformanceRepository.saveAll(dealerPerformances);

//		List<ProductTypeEntity> productTypeEntities = productTypeRepository.findByProductGroupEntityCardAccount("Card");
//		productTypeRepository.deleteAll(productTypeEntities);
//		List<ProductGroupEntity> productGroupEntities = productGroupRepository.findByCardAccount("Card");
//		productGroupRepository.deleteAll(productGroupEntities);
//		Vehcile vehcile = new Vehcile("1507200386319001", "SAHADAT HOSSAIN", "Y", new Date(), "1234", "1234", "1234", new Date(), "2014", "CAR");
//		vechileRepository.save(vehcile);
//		final String secretKey = "ssshhhhhhhhhhh!!!!";
//		String encrypt = AES.encrypt("1507200386319001", secretKey);
//		System.out.println(encrypt);
//		String decrypt = AES.decrypt(encrypt, secretKey);
//		System.out.println(decrypt);
//		String encrypt1 = AES.encrypt("1507200386319001", secretKey);
//		System.out.println(encrypt1);
//		String decrypt1 = AES.decrypt(encrypt1, secretKey);
//		System.out.println(decrypt1);
//
//		if(encrypt.equals(encrypt1)) System.out.println(true);
//		else System.out.println(false);
//		loanPaymentDao.getDelenquentAccount();
//		loanPaymentDao.deleteReportTable();
	}
}