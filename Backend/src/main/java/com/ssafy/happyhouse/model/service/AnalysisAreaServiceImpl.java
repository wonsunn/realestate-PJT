package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.mapper.AnalysisAreaMapper;

@Service
public class AnalysisAreaServiceImpl implements AnalysisAreaService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public String getGugunCode(String dong) {
		return sqlSession.getMapper(AnalysisAreaMapper.class).getGugunCode(dong);
	}

	@Override
	public List<Map<String, Object>> getTop10Apart(String dong) {
		return sqlSession.getMapper(AnalysisAreaMapper.class).getTop10Apart(dong);
	}

	@Override
	public List<Map<String, Object>> getAvgOfPrice(String dong) {
		return sqlSession.getMapper(AnalysisAreaMapper.class).getAvgOfPrice(dong);
	}

}