package com.ssafy.happyhouse.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.service.AnalysisAreaService;
import com.ssafy.happyhouse.model.service.HouseWishService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/happyhouse/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/analysis")
public class AnalysisAreaController {
	
	private Logger logger = (Logger) LoggerFactory.getLogger(AnalysisAreaController.class);
	
	@Autowired
	private AnalysisAreaService analysisAreaService;
	
	@ApiOperation(value = "선택된 동의 구군코드 조회")
	@GetMapping("/gugun/{dong}")
	public ResponseEntity<?> getGugunCode(@PathVariable("dong")  @ApiParam(value = "검색할 동 이름") String dong) {
		return new ResponseEntity<String>(analysisAreaService.getGugunCode(dong), HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택된 동의 가격순 상위 10개 아파트 조회")
	@GetMapping("/top/{dong}")
	public ResponseEntity<?> getTop10Apart(@PathVariable("dong") String dong) {
		List<Map<String, Object>> apartlist = analysisAreaService.getTop10Apart(dong);
		return new ResponseEntity<List<Map<String, Object>>>(apartlist, HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택된 동의 1년간 월별 평균 집값 추이 조회")
	@GetMapping("/avg/{dong}")
	public ResponseEntity<?> getAvgOfPrice(@PathVariable("dong") String dong) {
		List<Map<String, Object>> avgPrice = analysisAreaService.getAvgOfPrice(dong);
		return new ResponseEntity<List<Map<String, Object>>>(avgPrice, HttpStatus.OK);
	}

}