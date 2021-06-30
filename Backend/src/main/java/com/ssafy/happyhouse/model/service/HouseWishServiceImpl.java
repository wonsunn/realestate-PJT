package com.ssafy.happyhouse.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.HouseDetailDto;
import com.ssafy.happyhouse.model.mapper.HouseWishMapper;
import com.ssafy.util.CalculateUtil;

@Service
public class HouseWishServiceImpl implements HouseWishService {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(HouseWishServiceImpl.class);

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private HouseDetailService houseDetailService;
	
	@Override
	public int isWishHouse(int useridx, int apartno) {
		Map<String, Object> param = new HashMap<>();
		param.put("useridx", useridx);
		param.put("apartno", apartno);
		System.out.println(param.get("useridx") + " " + param.get("apartno"));
		return sqlSession.getMapper(HouseWishMapper.class).isWishHouse(param);
	}

	@Override
	public List<Map<String, Object>> getWishHouseList(int useridx) {
		return sqlSession.getMapper(HouseWishMapper.class).getWishHouseList(useridx);
	}
	
	@Override
	public int insertWishHouse(int useridx, int apartno) {
		Map<String, Object> param = new HashMap<>();
		param.put("useridx", useridx);
		param.put("apartno", apartno);
		return sqlSession.getMapper(HouseWishMapper.class).insertWishHouse(useridx, apartno);
	}
	
	@Override
	public int deleteWishHouse(int useridx, int apartno) {
		Map<String, Object> param = new HashMap<>();
		param.put("useridx", useridx);
		param.put("apartno", apartno);
		return sqlSession.getMapper(HouseWishMapper.class).deleteWishHouse(useridx, apartno);
	}

	@Override
	public String refinedList(List<Map<String, Object>> list) {
		JSONArray arr = new JSONArray();
		
		for (Map<String, Object> m : list) {
			JSONObject obj = new JSONObject();
			
			List<HouseDetailDto> houses = houseDetailService.getHouseDetailInfo((int) m.get("apartno"));
			int maxPrice = Integer.MIN_VALUE, minPrice = Integer.MAX_VALUE;
			for (HouseDetailDto h : houses) {
				int tmpPrice = Integer.parseInt(h.getDealAmount().trim().replace(",", ""));
				maxPrice = Math.max(maxPrice, tmpPrice);
				minPrice = Math.min(minPrice, tmpPrice);
			}
			
			obj.put("maxPrice", CalculateUtil.convertPrice(maxPrice));
			obj.put("minPrice", CalculateUtil.convertPrice(minPrice));
			obj.put("name", houses.get(0).getAptName());
			obj.put("addr", m.get("city") + " " + m.get("gugun") + " " + m.get("dong"));
			obj.put("no", m.get("apartno"));
			obj.put("dong", m.get("dong"));
			obj.put("jibun", m.get("jibun"));
			
			arr.add(obj);
		}
		
		return arr.toJSONString();
	}

}
