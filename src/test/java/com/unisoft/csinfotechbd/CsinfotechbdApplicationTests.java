package com.unisoft.csinfotechbd;

import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CsinfotechbdApplicationTests {

	@Autowired
	private CustomerBasicInfoService customerBasicInfoService;


	@Test
	public void dataSave() {
		System.out.println(customerBasicInfoService.toString());
	}

}
