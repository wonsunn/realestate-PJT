package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.*;
import com.ssafy.happyhouse.model.mapper.HouseMapMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<HouseInfoDto> getAptInDong(String dong) {
		return sqlSession.getMapper(HouseMapMapper.class).getAptInDong(dong);
	}

	@Override
	public List<SearchResponseDto> getDongFromSearchWord(String keyword) {
		return sqlSession.getMapper(HouseMapMapper.class).getDongFromSearchWord(keyword);
	}
	
	@Override
	public List<SearchResponseDto> getAptFromSearchWord(String keyword) {
		return sqlSession.getMapper(HouseMapMapper.class).getAptFromSearchWord(keyword);
	}

	@Override 
	public List<HouseInfoDto> getAptFromLoc(HouseRequestDto dto) {	
		return sqlSession.getMapper(HouseMapMapper.class).getAptFromLoc(dto);
	}

}
