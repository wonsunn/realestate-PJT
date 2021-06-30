package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.HouseDetailDto;

public interface HouseDetailService {

	public List<HouseDetailDto> getHouseDetailInfo(int no);
}
