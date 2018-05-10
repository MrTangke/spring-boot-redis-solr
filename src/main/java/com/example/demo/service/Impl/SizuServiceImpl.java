package com.example.demo.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Foods;
import com.example.demo.mapper.FoodsMapper;
import com.example.demo.service.SizuService;

@Service
public class SizuServiceImpl implements SizuService {

	@Autowired
	private FoodsMapper foodsMapper ;

	@Override
	public Foods getfood(int foodId) {
		// TODO Auto-generated method stub
		return foodsMapper.selectByPrimaryKey(foodId);
	}

	@Override
	public List<Foods> getFoodList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return foodsMapper.getFoodList(map);
	}

	@Override
	public Integer seleFoodCount() {
		// TODO Auto-generated method stub
		return foodsMapper.selectFoodsCount();
	}
}
