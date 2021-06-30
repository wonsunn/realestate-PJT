package com.ssafy.happyhouse.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.HouseDetailDto;
import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.service.HouseDetailService;
import com.ssafy.happyhouse.model.service.HouseDetailServiceImpl;
import com.ssafy.happyhouse.model.service.HouseWishService;
import com.ssafy.util.CalculateUtil;

@RestController
@RequestMapping("/house")
public class HouseDetailController extends HttpServlet {
	
	private Logger logger = (Logger) LoggerFactory.getLogger(HouseDetailController.class);
	
	@Autowired
	private HouseDetailService houseDetailService;
	
	@Autowired
	private HouseWishService houseWishService;
	
	@GetMapping("/{houseno}")
	public String houseDetailInfo(@PathVariable("houseno") int no) {
		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();
		
		List<HouseDetailDto> houses = houseDetailService.getHouseDetailInfo(no);
		int maxPrice = Integer.MIN_VALUE, minPrice = Integer.MAX_VALUE;
		for (HouseDetailDto h : houses) {
			int tmpPrice = Integer.parseInt(h.getDealAmount().trim().replace(",", ""));
			maxPrice = Math.max(maxPrice, tmpPrice);
			minPrice = Math.min(minPrice, tmpPrice);
		}
		
		for (HouseDetailDto dto : houses) {
			obj = new JSONObject();
			obj.put("dealAmount", dto.getDealAmount());
			obj.put("dealAmount2", CalculateUtil.convertPrice(Integer.parseInt(dto.getDealAmount().trim().replace(",", ""))));
			obj.put("area", dto.getArea());
			obj.put("floor", dto.getFloor());
			obj.put("dealYear", dto.getDealYear());
			obj.put("dealMonth", dto.getDealMonth());
			obj.put("dealDay", dto.getDealDay());
			arr.add(obj);
		}
		
		obj = new JSONObject();

//		else obj.put("wish_img", "unlike");
		
		obj.put("apartno", houses.get(0).getApartno());
		obj.put("maxPrice", CalculateUtil.convertPrice(maxPrice));
		obj.put("minPrice", CalculateUtil.convertPrice(minPrice));
		obj.put("buildYear", houses.get(0).getBuildYear());
		obj.put("gugun", houses.get(0).getGugun());
		obj.put("name", houses.get(0).getAptName());
		obj.put("houses", arr);
	
		
		return obj.toJSONString();
	}

}
