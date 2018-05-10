package com.example.demo.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 
 * solr 配置类
 *
 */
@Configuration
public class SolrConfig {

	@Value("${spring.data.solr.host}")
	private String host ;
	
	@Bean
	public SolrClient createSolrClient(){
		return new HttpSolrClient(host);
	}
}
