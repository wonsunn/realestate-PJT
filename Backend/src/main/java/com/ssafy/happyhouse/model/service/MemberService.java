package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	
	public List<MemberDto> memberList();
	public MemberDto memberInfo(String userid);
	public int memberModify(MemberDto memberDto);
	public int memberRegister(MemberDto memberDto);
	public int memberDelete(String userid) throws Exception;
	
	public int idCheck(String userid);
	
}
