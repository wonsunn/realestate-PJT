package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.InterestAreaDto;


public interface InterestMapper {
	public int insertInterestArea(int useridx, String code);
	public int isExistedArea(int useridx, String code);
	public List<Map<String,Object>> interestAreaList(int useridx);
	public int deleteInterestArea(int useridx,String code);
	public String getDongCode(String dong);
	public Map<String,Object> getCenterLocation(int code);
	
}
