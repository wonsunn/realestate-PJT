package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.HouseDetailDto;
import com.ssafy.happyhouse.model.mapper.HouseDetailMapper;

@Service
public class HouseDetailServiceImpl implements HouseDetailService {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(HouseDetailServiceImpl.class);

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<HouseDetailDto> getHouseDetailInfo(int no) {
		return sqlSession.getMapper(HouseDetailMapper.class).getHouseDetailInfo(no);
	}

}
