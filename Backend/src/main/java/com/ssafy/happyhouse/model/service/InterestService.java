package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.InterestAreaDto;

public interface InterestService {
	public int insertInterestArea(int useridx, String code);
	public int deleteInterestArea(int useridx, String dong);
	public List<Map<String,Object>> getInterestArea(int useridx);
	public String getDongCode(String dong);
	public Map<String,Object> getCenterLocation(int code);
}
