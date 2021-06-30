package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.NoticeDto;

public interface NoticeService {

	public boolean regist(NoticeDto noticeDto) throws SQLException;
	public List<NoticeDto> list() throws SQLException;
	
	public NoticeDto getArticle(int articleno) throws SQLException;
	public boolean modifyArticle(NoticeDto noticeDto) throws SQLException;
	public boolean deleteArticle(int articleno) throws SQLException;
}
