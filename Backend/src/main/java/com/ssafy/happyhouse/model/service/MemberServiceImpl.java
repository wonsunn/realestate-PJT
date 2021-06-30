package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;
	
	
	// 로그인 계정 고정값을 이용
		@Override
		public MemberDto login(MemberDto memberDto) throws SQLException {
			if(memberDto.getUserid() == null || memberDto.getUserpwd()==null) {
				return null;
			}
			return sqlSession.getMapper(MemberMapper.class).login(memberDto);
		}
	
		@Override
		public List<MemberDto> memberList(){
			List<MemberDto> list = sqlSession.getMapper(MemberMapper.class).memberList();
			System.out.println("회원 목록 Service 쪽 : "+list);
			return list;
		}
		@Override
		public MemberDto memberInfo(String userid) {
			return sqlSession.getMapper(MemberMapper.class).memberInfo(userid);
		}
		@Override
		public int memberModify(MemberDto memberDto) {
			
			return sqlSession.getMapper(MemberMapper.class).memberModify(memberDto);
		}
		@Override
		public int memberRegister(MemberDto memberDto) {
			int cnt = sqlSession.getMapper(MemberMapper.class).memberRegister(memberDto);
			System.out.println("아이디 타입: "+memberDto.getUserid().getClass().getName());
			return cnt;
		}
		@Override
		public int memberDelete(String userid) throws Exception{
			return sqlSession.getMapper(MemberMapper.class).memberDelete(userid);
		}
		
		@Override
		public int idCheck(String userid) {
			return sqlSession.getMapper(MemberMapper.class).idCheck(userid);
		}
}
