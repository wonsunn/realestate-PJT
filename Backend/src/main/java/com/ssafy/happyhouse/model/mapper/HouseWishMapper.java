package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.HouseDetailDto;

public interface HouseWishMapper {
	
	public int isWishHouse(Map<String, Object> param);
	public List<Map<String, Object>> getWishHouseList(int useridx);
	public int insertWishHouse(int useridx, int apartno);
	public int deleteWishHouse(int useridx, int apartno);

}
