package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.*;

public interface HouseMapService {
	
	public List<HouseInfoDto> getAptInDong(String dong);
	public List<SearchResponseDto> getDongFromSearchWord(String keyword);
	public List<SearchResponseDto> getAptFromSearchWord(String keyword);
	public List<HouseInfoDto> getAptFromLoc(HouseRequestDto dto);
	
}
