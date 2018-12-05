package com.agliean.lessons.druid.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;



@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass=true)
@PropertySource("classpath:/common.properties")
@EnableJpaRepositories(basePackages="com.agilean.lessons.druid.hibernate.jpa.repository")
@ComponentScan(value= {"com.agilean.lessons.druid.config","com.agilean.lessons.druid.hibernate.jpa.repository","com.agilean.lessons.druid.hibernate.jpa.entity","com.agilean.lessons.druid.service","com.agilean.lessons.druid.controller"})
public class CommonConfig {
	@Autowired
	Environment env;
	@Bean
	DataSource dataSource() {
		DruidDataSource dataSource=new DruidDataSource();
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
		dataSource.setInitialSize(Integer.valueOf(env.getProperty("druid.initialSize")));
		dataSource.setMinIdle(Integer.valueOf(env.getProperty("druid.minIdle")));
		dataSource.setMaxActive(Integer.valueOf(env.getProperty("druid.maxActive")));
		dataSource.setMaxWait(Integer.valueOf(env.getProperty("druid.maxWait")));
		dataSource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(env.getProperty("druid.timeBetweenEvictionRunsMillis")));
		dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(env.getProperty("druid.minEvictableIdleTimeMillis")));
		dataSource.setValidationQuery(env.getProperty("druid.validationQuery"));
		dataSource.setTestWhileIdle(new Boolean(env.getProperty("druid.testWhileIdle")));
		dataSource.setTestOnBorrow(new Boolean(env.getProperty("druid.testOnBorrow")));
		dataSource.setTestOnReturn(new Boolean(env.getProperty("druid.testOnReturn")));
		dataSource.setPoolPreparedStatements(new Boolean(env.getProperty("druid.poolPreparedStatements")));
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.valueOf(env.getProperty("druid.maxPoolPreparedStatementPerConnectionSize")));
		try {
			dataSource.setFilters(env.getProperty("druid.filters"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataSource;
		
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(final  DataSource dataSource) throws Exception{
		
		 return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
		
		HibernateJpaVendorAdapter adaptor=new HibernateJpaVendorAdapter();
		adaptor.setDatabase(Database.POSTGRESQL);
		adaptor.setGenerateDdl(new Boolean(env.getProperty("jdbc.gen.ddl").trim()));
		LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(adaptor);
		factory.setPackagesToScan("com.agilean.lessons.druid.hibernate.jpa.entity");
		factory.setDataSource(dataSource);
		return factory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory)
	{
		 JpaTransactionManager txManager=new JpaTransactionManager();
		 txManager.setEntityManagerFactory(entityManagerFactory.getObject());
		 return txManager;
	}
	
}
