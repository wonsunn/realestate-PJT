package com.ssafy.happyhouse.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.HouseDetailDto;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.service.HouseDetailService;
import com.ssafy.happyhouse.model.service.HouseWishService;
import com.ssafy.util.CalculateUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/happyhouse/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/wish")
public class HouseWishController {

	private Logger logger = (Logger) LoggerFactory.getLogger(HouseWishController.class);
	
	@Autowired
	private HouseWishService houseWishService;
	
	@ApiOperation(value = "회원의 관심매물 등록 여부")
	@GetMapping(value = "/{useridx}/{apartno}")
	public ResponseEntity<?> isWishedHouse(@PathVariable("useridx") @ApiParam(value = "검색할 회원 번호") int useridx,
			@PathVariable("apartno") @ApiParam(value = "검색할 아파트 번호") int apartno) {
		int cnt = houseWishService.isWishHouse(useridx, apartno);
		if (cnt == 1)  
			return new ResponseEntity<String>("like", HttpStatus.OK); 
		else 
			return new ResponseEntity<String>("unlike", HttpStatus.OK);
	}
	
	@ApiOperation(value = "회원의 관심매물 리스트")
	@GetMapping("/{useridx}")
	public ResponseEntity<?> getWishHouseList(@PathVariable("useridx") @ApiParam(value = "검색할 회원 번호") int useridx) {	
		List<Map<String, Object>> wishlist = houseWishService.getWishHouseList(useridx); 
		logger.info("wishlist : {}", wishlist);
		String res = houseWishService.refinedList(wishlist);
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	@ApiOperation(value = "관심매물 등록")
	@PostMapping("/{useridx}/{apartno}")
	public ResponseEntity<?> insertWishHouse(@PathVariable("useridx") @ApiParam(value = "회원 번호") int useridx,
			@PathVariable("apartno") @ApiParam(value = "등록할 아파트 번호") int apartno) {
		
		logger.info("apartno : {}", apartno);
		int cnt = houseWishService.insertWishHouse(useridx, apartno);
		if (cnt > 0)
			return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		else
			return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);		
	}
	
	@ApiOperation(value = "관심매물 삭제")
	@DeleteMapping("/{useridx}/{apartno}")
	public ResponseEntity<?> deleteWishHouse(@PathVariable("useridx") @ApiParam(value = "회원 번호") int useridx,
			@PathVariable("apartno") @ApiParam(value = "삭제할 아파트 번호") int apartno) {
		
		logger.info("apartno : {}", apartno);
		int cnt = houseWishService.deleteWishHouse(useridx, apartno);
		
		if (cnt > 0)
			return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		else
			return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
	}
	
}