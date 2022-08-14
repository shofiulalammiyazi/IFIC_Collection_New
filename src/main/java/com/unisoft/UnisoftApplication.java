package com.unisoft;

import com.unisoft.collection.dashboard.DashboardDao;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@Slf4j
@SpringBootApplication
public class UnisoftApplication implements CommandLineRunner {


	@Autowired
	private DashboardDao dashboardDao;

	public static void main(String[] args) {
		SpringApplication.run(UnisoftApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {

	}
}