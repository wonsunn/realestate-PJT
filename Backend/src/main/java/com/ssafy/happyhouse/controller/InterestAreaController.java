package com.ssafy.happyhouse.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.InterestAreaDto;
import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.service.InterestService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/happyhouse/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/interest")
public class InterestAreaController {

	private static final Logger logger = LoggerFactory.getLogger(InterestAreaController.class);

	@Autowired
	private InterestService interestService;
	
	@ApiOperation(value="관심지역리스트")
	@GetMapping("/{useridx}")
	public ResponseEntity<List<Map<String,Object>>> interestList(@PathVariable("useridx") @ApiParam(value = "검색할 회원 번호") int useridx) {
		System.out.println("리스트");
		List<Map<String,Object>> interestList = interestService.getInterestArea(useridx);
		System.out.println(interestList);
		return new ResponseEntity<List<Map<String,Object>>>(interestList,HttpStatus.OK);
	}
	
	@ApiOperation(value="관심지역등록")
	@PostMapping("/{useridx}/{dong}")
	public ResponseEntity<String> insertInterestArea(@PathVariable("useridx") @ApiParam(value="회원 번호") int useridx,
			@PathVariable("dong") @ApiParam(value="추가할 지역이름") String dong){
		System.out.println("사용자 번호 : "+useridx);
		String dongCode = interestService.getDongCode(dong);
		System.out.println("여기까지????????"+dongCode);
		
		System.out.println(dongCode.getClass().getName());
		
		int cnt = interestService.insertInterestArea(useridx,dongCode);
		System.out.println("CNT 가 무엇일까 ? "+cnt);
		if(cnt!=0)
			return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		return new ResponseEntity<String>("FAIL",HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="관심지역삭제")
	@DeleteMapping("/{useridx}/{dong}")
	public ResponseEntity<String> deleteInterestArea(@PathVariable("useridx") @ApiParam(value="삭제할 회원번호") int useridx,
			@PathVariable("dong") @ApiParam(value="삭제할 동코드") String dong){
		System.out.println("삭제?????");
		int cnt = interestService.deleteInterestArea(useridx,dong);
		if(cnt!=0)
			return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		return new ResponseEntity<String>("FAIL",HttpStatus.NO_CONTENT);
	}
}
