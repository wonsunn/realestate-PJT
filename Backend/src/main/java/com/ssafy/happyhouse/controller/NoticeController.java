package com.ssafy.happyhouse.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.NoticeDto;
import com.ssafy.happyhouse.model.service.*;



//http://localhost:9999/vue/swagger-ui.html
/**
 * Servlet implementation class MemberController
 */
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/notice")
public class NoticeController extends HttpServlet {
	
	private Logger logger = (Logger) LoggerFactory.getLogger(NoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	@Autowired
	NoticeService noticeService; 
	
	@GetMapping
	public ResponseEntity<List<NoticeDto>> list() throws Exception {	
		System.out.println("리스트 들어오나");
		return new ResponseEntity<List<NoticeDto>>(noticeService.list(),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> registNotice(@RequestBody NoticeDto noticeDto) throws Exception {
		System.out.println("등록?가능?");
		System.out.println(noticeDto.getUsername());
		if(noticeService.regist(noticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	@PutMapping("/update/{articleno}")
	public ResponseEntity<String> modify(@RequestBody NoticeDto noticeDto) throws Exception {
		//System.out.println(map.get("userId")+" "+map.get("userPwd"));
		System.out.println("수정하러 들어왔나");
		if(noticeService.modifyArticle(noticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> delete(@PathVariable("articleno") int articleno) throws Exception {
		if(noticeService.deleteArticle(articleno)) {
			return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
	}	
	@GetMapping("/read/{articleno}")
	public ResponseEntity<NoticeDto> content(@PathVariable("articleno") int articleno) throws Exception {
		System.out.println("읽어오기 들어옴?");
		return new ResponseEntity<NoticeDto>(noticeService.getArticle(articleno),HttpStatus.OK);
	}
	
	
	

	

}
