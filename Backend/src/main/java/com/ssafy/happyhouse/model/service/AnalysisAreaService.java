package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

public interface AnalysisAreaService {

	public String getGugunCode(String dong);
	public List<Map<String, Object>> getTop10Apart(String dong);
	public List<Map<String, Object>> getAvgOfPrice(String dong);
	
}
