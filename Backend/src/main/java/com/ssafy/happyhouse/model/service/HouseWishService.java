package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.HouseDetailDto;

public interface HouseWishService {

	public int isWishHouse(int useridx, int apartno);
	public List<Map<String, Object>> getWishHouseList(int useridx);
	public int insertWishHouse(int useridx, int apartno);
	public int deleteWishHouse(int useridx, int apartno);
	
	public String refinedList(List<Map<String, Object>> list);
	
}
