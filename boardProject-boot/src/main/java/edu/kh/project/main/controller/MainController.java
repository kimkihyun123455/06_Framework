package edu.kh.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // 요청과 응답 제어 역할 + Bean 등록
public class MainController {

	@RequestMapping("/") // 최상위주소 "/"로 온 요청을 인터샙트
	public String mainPage() {
		
		// src/main/resources/templates/common/main.html
		return "common/main";
	}

	// LoginFilter -> 로그인 안되어 있을 때 loginError 리다이렉트
	// -> message 만들어서 메인페이지로 리다이렉트
	@GetMapping("loginError")
	public String loginError(RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", "로그인 후 이용해주세요.");
		
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
