package com.ssafy.util;

public class CalculateUtil {
	
	public static String convertPrice(int price) {
		int front = price / 10000;
		int back = price % 10000;
		
		String newBack = "";
		if (String.valueOf(back).length() == 4) {
			for (int i = 0; i < 4; i++) {
				newBack += String.valueOf(back).charAt(i);
				if (i == 0)
					newBack += ',';
			}
		} else {
			newBack = String.valueOf(back);
		}
		
		if (front != 0 && back != 0) return String.valueOf(front) + "억 " + String.valueOf(newBack) + "만";
		else if (front != 0 && back == 0) return String.valueOf(front) + "억";
		else return String.valueOf(newBack) + "만";
	}

}
