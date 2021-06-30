package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.InterestAreaDto;
import com.ssafy.happyhouse.model.mapper.InterestMapper;

@Service
public class InterestServiceImpl implements InterestService {

	private static final Logger logger = LoggerFactory.getLogger(InterestServiceImpl.class);

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int insertInterestArea(int useridx, String code) {	
		int isExist = sqlSession.getMapper(InterestMapper.class).isExistedArea(useridx, code);
		if(isExist>0) {
			return 0;
		}			
		int cnt = sqlSession.getMapper(InterestMapper.class).insertInterestArea(useridx, code);	
		System.out.println("추가 되었나 : "+cnt);
		return cnt;
	}

	@Override
	public List<Map<String, Object>> getInterestArea(int useridx) {

		List<Map<String, Object>> list = sqlSession.getMapper(InterestMapper.class).interestAreaList(useridx);
		System.out.println(list.size());
		System.out.println("서비스 리스트 : " + list);
		return list;
	}

	@Override
	public int deleteInterestArea(int useridx, String dong) {
		String code = getDongCode(dong);
		return sqlSession.getMapper(InterestMapper.class).deleteInterestArea(useridx, code);
	}

	@Override
	public String getDongCode(String dong){
		System.out.println("서비스 에서 : "+dong);
		String str = sqlSession.getMapper(InterestMapper.class).getDongCode(dong);

		return str;
	}
	
	@Override
	public Map<String,Object> getCenterLocation(int code){
		return sqlSession.getMapper(InterestMapper.class).getCenterLocation(code);
	}
}
