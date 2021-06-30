package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.mapper.MemberMapper;
import com.ssafy.happyhouse.model.mapper.NoticeMapper;


@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<NoticeDto> list() throws SQLException{
		return sqlSession.getMapper(NoticeMapper.class).list();
	}
	
	@Override
	public boolean regist(NoticeDto noticeDto) throws SQLException {
		
		return sqlSession.getMapper(NoticeMapper.class).regist(noticeDto)==1;
		
	}

	@Override
	public NoticeDto getArticle(int articleno) throws SQLException {
		sqlSession.getMapper(NoticeMapper.class).updateview(articleno);
		return sqlSession.getMapper(NoticeMapper.class).getArticle(articleno);
	}

	@Override
	public boolean modifyArticle(NoticeDto noticeDto) throws SQLException {
		return sqlSession.getMapper(NoticeMapper.class).modifyArticle(noticeDto) == 1;
		
	}

	@Override
	public boolean deleteArticle(int articleno) throws SQLException {
		return sqlSession.getMapper(NoticeMapper.class).deleteArticle(articleno) == 1;
		
	}

}
