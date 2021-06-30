package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

public interface AnalysisAreaMapper {
	
	public String getGugunCode(String dong);
	public List<Map<String, Object>> getTop10Apart(String dong);
	public List<Map<String, Object>> getAvgOfPrice(String dong);

}
