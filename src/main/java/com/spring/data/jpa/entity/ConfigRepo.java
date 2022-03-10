package com.spring.data.jpa.entity;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.spring.data.jpa.repository.ClientRepository;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.spring.data.jpa.repository" })
//@EnableJpaRepositories enables usage of JPA repositories
public class ConfigRepo {

	public static void main(String[] args) {

		try {

			System.out.println("Début ….");

			AnnotationConfigApplicationContext ctxt = new AnnotationConfigApplicationContext(ConfigRepo.class);

			ClientRepository clientRepository = ctxt.getBean(ClientRepository.class);

			Client client = clientRepository.findById(4).orElse(null);

			System.out.println(client);

			List<Client> clients = clientRepository.findAll();

			System.out.println(clients);

			ctxt.close();

			System.out.println("Done.");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Bean
	public DataSource dataSource() {

		System.out.println("**** getDataSource");

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setUrl("jdbc:mysql://localhost:3306/formation?autoReconnect=true&useSSL=false&serverTimezone=UTC");

		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

		dataSource.setUsername("root");

		return dataSource;

	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		System.out.println("*** getEntityManagerFactory");

		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

		emf.setDataSource(dataSource());

		emf.setPersistenceUnitName("hello-spring-data-jpa");

		emf.setPackagesToScan("com.spring.data.jpa.entity");

		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties jpaProperties = new Properties();

		jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

		emf.setJpaProperties(jpaProperties);

		emf.afterPropertiesSet();

		return emf.getObject();

	}

	@Bean
	public JpaTransactionManager transactionManager() {

		System.out.println("*** transactionManager");

		JpaTransactionManager transactionManager = new JpaTransactionManager();

		transactionManager.setEntityManagerFactory(entityManagerFactory());

		return transactionManager;
	}

}
