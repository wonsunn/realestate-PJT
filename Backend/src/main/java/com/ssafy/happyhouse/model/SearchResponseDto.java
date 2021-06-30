package com.ssafy.happyhouse.model;

public class SearchResponseDto {

	private String city;
	private String gugun;
	private String dong;
	private String jibun;
	private String no;
	private String aptName;
	private boolean isApart;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGugun() {
		return gugun;
	}
	public void setGugun(String gugun) {
		this.gugun = gugun;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getAptName() {
		return aptName;
	}
	public void setAptName(String aptName) {
		this.aptName = aptName;
	}
	public boolean isApart() {
		return isApart;
	}
	public void setApart(boolean isApart) {
		this.isApart = isApart;
	}
	
}
