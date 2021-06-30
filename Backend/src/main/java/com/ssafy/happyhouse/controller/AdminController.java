package com.ssafy.happyhouse.controller;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.service.JwtService;
import com.ssafy.happyhouse.model.service.MemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/happyhouse/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "회원 목록")
	@GetMapping(value = "/")
	public ResponseEntity<List<MemberDto>> memberList() {
		System.out.println("회원목록 가져오기");
		List<MemberDto> list = memberService.memberList();
		if (list != null && !list.isEmpty()) {
			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "로그인되나", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody MemberDto memberDto, HttpServletResponse response)
			throws Exception {
		System.out.println("로그인 가능????????????");
		Map<String, Object> resultMap = new HashMap<>();
		MemberDto loginUser = memberService.login(memberDto);
		// 로그인 성공하면 토큰 생성
		if (loginUser != null) {
			String token = jwtService.create(loginUser);
			// 토큰 정보 response의 헤더? 에 보내고 나머지는 Map에 담기
			response.setHeader("jwt-auth-token", token);
			resultMap.put("auth_token", token);
			resultMap.put("status", true);
			resultMap.put("data", loginUser);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원 등록")
	@PostMapping(value = "/")
	public ResponseEntity<List<MemberDto>> memberRegister(
			@RequestBody @ApiParam(value = "등록할 회원정보", required = true) MemberDto memberDto) {
		System.out.println("회원등록 가져오기");
		int cnt = memberService.memberRegister(memberDto);
		if (cnt != 0) {
			List<MemberDto> list = memberService.memberList();
			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<MemberDto>>(HttpStatus.NO_CONTENT);
		}
	}
	@ApiOperation(value = "회원 수정")
	@PutMapping(value = "/")
	public ResponseEntity<List<MemberDto>> memberModify(
			@RequestBody @ApiParam(value = "수정할 회원정보", required = true) MemberDto memberDto) {
		System.out.println("회원수정 가져오기");
		memberService.memberModify(memberDto);
		List<MemberDto> list = memberService.memberList();
		return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
	}
	@ApiOperation(value = "회원정보검색")
	@GetMapping(value = "/{userid}")
	public ResponseEntity<MemberDto> memberInfo(@PathVariable("userid") @ApiParam(value = "검색할 아이디") String userid) {
		System.out.println("회원검색 가져오기");
		MemberDto memberDto = memberService.memberInfo(userid);
		if (memberDto != null) {
			return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<MemberDto>(HttpStatus.NO_CONTENT);
		}
	}
	
}
