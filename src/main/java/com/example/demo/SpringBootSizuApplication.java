package com.example.demo;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

//核心注解
@SpringBootApplication
public class SpringBootSizuApplication {
	
	//配置链接数据库   db 
	@Bean 
	@ConfigurationProperties(prefix="db")
	public DataSource dateSource(){
		return new DataSource() ;
	}
	
	//通过工程创建sqlSession
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception{
		// sql类工厂   bean工厂
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 数据源方法 set进去创建的工厂内
		sqlSessionFactoryBean.setDataSource(dateSource());
		// 路径加载扫描器 　根据配置路径自动加载符合路径规则的xml文件、类文件
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		// 获取mybatis连接数据库查询的xml文件      注：在mybatis文件夹下的所有xml文件
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	//配置事物的管理器
	@Bean
	public PlatformTransactionManager transactionManager(){
		// PlatformTransactionManager  会自动把事务加载到你的mapper层  （也就是持久层）
		return new DataSourceTransactionManager(dateSource());
	}
	
	//spring boot 启动加载
	public static void main(String[] args) {
		SpringApplication.run(SpringBootSizuApplication.class, args);
	}
}
