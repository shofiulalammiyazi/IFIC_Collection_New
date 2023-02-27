package com.unisoft;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.unisoft.auth.SpringApplicationContext;
import com.unisoft.collection.dashboard.DashboardDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@Slf4j
@SpringBootApplication
@EnableEncryptableProperties
public class UnisoftApplication implements CommandLineRunner {


	@Autowired
	private DashboardDao dashboardDao;

	public static void main(String[] args) {
		SpringApplication.run(UnisoftApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {

	}

	@Bean
	public SpringApplicationContext applicationContext(){

		return new SpringApplicationContext();
	}
}