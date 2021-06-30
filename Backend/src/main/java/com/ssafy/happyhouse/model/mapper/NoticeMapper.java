package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.NoticeDto;

public interface NoticeMapper {

	
	public int regist(NoticeDto noticeDto) throws SQLException;
	public List<NoticeDto> list() throws SQLException;
	public void updateview(int articleno) throws SQLException;
	public NoticeDto getArticle(int articleno) throws SQLException;
	public int modifyArticle(NoticeDto noticeDto) throws SQLException;
	public int deleteArticle(int articleno) throws SQLException;
}
