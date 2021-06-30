package com.ssafy.happyhouse.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.happyhouse.model.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		
		// 옵션 요청 바로 통과
		if(request.getMethod().equals("OPTIONS")) {
			return true;
		}else {
			// request의 auth_token으로 넘어온 녀석을 찾아본다.
			// String token = request.getParameter("auth_token");
			String token = request.getHeader("jwt-auth-token");
			if(token != null && token.length()>0) {
				// 유효한 토큰 -> 진행
				jwtService.checkValid(token);
				return true;
			}else {
				throw new RuntimeException("인증 토큰 없음");
			}
		}
	}
}
