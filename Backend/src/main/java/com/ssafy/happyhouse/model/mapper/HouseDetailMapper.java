package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import com.ssafy.happyhouse.model.HouseDetailDto;

public interface HouseDetailMapper {

	public List<HouseDetailDto> getHouseDetailInfo(int no);
}
