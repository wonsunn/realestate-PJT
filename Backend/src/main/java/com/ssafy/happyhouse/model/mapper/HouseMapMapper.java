package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.HouseRequestDto;
import com.ssafy.happyhouse.model.SearchResponseDto;

public interface HouseMapMapper {

	public List<HouseInfoDto> getAptInDong(String dong);
	public List<SearchResponseDto> getDongFromSearchWord(String keyword);
	public List<SearchResponseDto> getAptFromSearchWord(String keyword);
	public List<HouseInfoDto> getAptFromLoc(HouseRequestDto dto);
	
}
