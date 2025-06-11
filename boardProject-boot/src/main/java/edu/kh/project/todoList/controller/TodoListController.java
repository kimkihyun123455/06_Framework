package edu.kh.project.todoList.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.project.admin.controller.AdminController;
import edu.kh.project.admin.model.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins="http://localhost:5173" /*, allowCredentials = "true"*/)
// allowCredentials = "true" 클라이언트로부터 들어오는 쿠키 허용
@RequestMapping("todo")
@Slf4j
@RequiredArgsConstructor
public class TodoListController {

	@GetMapping("todo")
	public ResponseEntity<String> todoList(){
		
		try {
			return ResponseEntity.status(HttpStatus.OK) // 200
				   .body("로그아웃이 완료되었습니다."); 
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				   .body("불러오는 중 예외 발생 : " + e.getMessage());
			
		}
	}
}
