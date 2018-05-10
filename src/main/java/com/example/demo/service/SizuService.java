package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.Foods;

public interface SizuService {

	Foods getfood(int foodId);

	List<Foods> getFoodList(Map<String, Object> map);

	Integer seleFoodCount();

}
