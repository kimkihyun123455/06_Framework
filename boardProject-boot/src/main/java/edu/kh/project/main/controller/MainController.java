package edu.kh.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 요청과 응답 제어 역할 + Bean 등록
public class MainController {

	@RequestMapping("/") // 최상위주소 "/"로 온 요청을 인터샙트
	public String mainPage() {
		
		// src/main/resources/templates/common/main.html
		return "common/main";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
