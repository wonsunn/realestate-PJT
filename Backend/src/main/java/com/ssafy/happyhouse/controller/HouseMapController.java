package com.ssafy.happyhouse.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.*;
import com.ssafy.happyhouse.model.service.HouseMapService;
import com.ssafy.happyhouse.model.service.HouseMapServiceImpl;
import com.ssafy.util.CalculateUtil;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping(value = "/housemap")
public class HouseMapController {
	
	private Logger logger = (Logger) LoggerFactory.getLogger(HouseMapController.class);
	
	@Autowired
	private HouseMapService houseMapService;
	
	@GetMapping(value = "/{dong}")
	public List<HouseInfoDto> houseList(@PathVariable("dong") String dongname) {
		return houseMapService.getAptInDong(dongname);
	}
	
	@PostMapping(value = "/")
	public List<HouseInfoDto> houseList2(@RequestBody HouseRequestDto houseRequestDto) {
		return houseMapService.getAptFromLoc(houseRequestDto);
	}
	
	@GetMapping("/search/{keyword}")
	public String houseSearchResult(@PathVariable("keyword") String keyword) {
		JSONArray arr = new JSONArray();
		JSONObject obj = null;
		List<SearchResponseDto> searchList = houseMapService.getDongFromSearchWord(keyword);
		searchList.addAll(houseMapService.getAptFromSearchWord(keyword));
		
		for (SearchResponseDto dto : searchList) {
			obj = new JSONObject();
			if (dto.getAptName() != null) {
				obj.put("main_data", dto.getAptName());
				obj.put("no", dto.getNo());
				obj.put("jibun", dto.getJibun());
				obj.put("dongOfApt", dto.getDong());
				obj.put("isApart", true);
			}
			else {
				obj.put("main_data", dto.getDong());
				obj.put("isApart", false);
			}
			obj.put("sub_data", dto.getCity() + " " + dto.getGugun() + " " + dto.getDong());
			arr.add(obj);
		}
		
		return arr.toJSONString();
	}

}