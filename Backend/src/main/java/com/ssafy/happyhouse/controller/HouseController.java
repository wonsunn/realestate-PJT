package com.ssafy.happyhouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HouseController {
	@GetMapping("/")
	public String home() {
		return "redirect:index.html"; 
	}
}
