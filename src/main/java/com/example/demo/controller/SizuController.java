package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.config.PageUtil;
import com.example.demo.entity.Foods;
import com.example.demo.service.SizuService;

@Controller
@MapperScan("com.example.demo.mapper")
public class SizuController {

	@Autowired
	private SizuService sizuService ;
	
	//注入solr 
	@Autowired
	private SolrClient solrClient ;
	
	@RequestMapping("/index")
	public String index(Map<String,Object> root , String currentPage, HttpServletRequest request) throws SolrServerException, IOException{
		Integer count = sizuService.seleFoodCount();
		//工具类调用 当前页  每页记录条数  总条数
		PageUtil page = new PageUtil(currentPage, 5, count);
		
		//创建map集合存放查询数据
		Map<String,Object> map = new HashMap<String, Object>();
		//记录
		map.put("PageRecord", page.getPageRecord());
		//每页条数
		map.put("PageSize", page.getPageSize());
	
		List<Foods> list = sizuService.getFoodList(map);
		//生命绝对路径传送前台引用
		String path1 = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
		//生成静态页打包数据
		root.put("list", list);
		root.put("page", page);
		root.put("basePath", basePath);
		return "index" ;
	}
	
	@RequestMapping("/food")
	public String food(Foods foods, String currentPage , Map<String,Object> root ,HttpServletRequest request) throws SolrServerException, IOException{
		System.out.println("solr----------"+foods.getFoodName());
		String q = "" ;
		//判断美食是否为空
		if (foods.getFoodName()!=null && !foods.getFoodName().equals("")) {
			q = "foodName:"+foods.getFoodName();
		}else {
			return "index" ;
		}
		//创建solr连接去查询美食
		SolrQuery query = new SolrQuery();
		query.set("q", q);
		
		//添加高亮字段属性   通过属性域
		query.setHighlight(true);
		query.addHighlightField("foodName");
		query.setHighlightSimplePre("<span style=\"color:red\">");
		query.setHighlightSimplePost("</span>");
		
		//创建空集合  空对象 用于接受存放发送前台
		List<Foods> list = new ArrayList<Foods>();
		Foods foods2 = new Foods();
		
		//通过查询条件获取值
		QueryResponse response = solrClient.query(query);
		//开启获得高亮字段
		Map<String, Map<String, List<String>>> map = response.getHighlighting();
		//查询获得结果集
		SolrDocumentList results = response.getResults();
		//便利结果集
		for (SolrDocument solrDocument : results) {
			System.out.println("solr:=="+solrDocument.getFieldValue("id"));
			System.out.println("solr:=="+solrDocument.getFieldValue("foodName"));
			System.out.println("solr:=="+solrDocument.getFieldValue("foodMessage"));
			
			foods2.setFoodId( Integer.parseInt((String) solrDocument.getFieldValue("id")) );
			//通过solr的唯一主键获得需要高亮的字段
			Map<String, List<String>> map2 = map.get(solrDocument.getFieldValue("id"));
			//通过获取的属性去获取高亮的属性 
			List<String> list2 = map2.get("foodName");
			//判断不为空
			if (foods != null && !foods.getFoodName().equals("")) {
				//获取下标为0 的第一条数据
				foods2.setFoodName(list2.get(0));
			}
			foods2.setFoodPrice(Integer.parseInt((String) solrDocument.getFieldValue("foodPrice")) );
			foods2.setFoodMessage((String) solrDocument.getFieldValue("foodMessage"));
			list.add(foods2);
		}
		//生命绝对路径传送前台引用
		String path1 = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
		Integer count = sizuService.seleFoodCount();
		//工具类调用 当前页  每页记录条数  总条数
		PageUtil page = new PageUtil(currentPage, 5, count);
		//把前台需要展示的数据放到map中发送到前台
		root.put("list", list);
		root.put("page", page);
		root.put("foodName", foods.getFoodName());
		root.put("basePath", basePath);
		return "index" ;
	}
}
