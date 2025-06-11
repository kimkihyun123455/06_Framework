package edu.kh.todo.todoList.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.todoList.service.TodoListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins="http://localhost:5173" /*, allowCredentials = "true"*/)
// allowCredentials = "true" 클라이언트로부터 들어오는 쿠키 허용
@RequestMapping("todo")
@Slf4j
@RequiredArgsConstructor
public class TodoListController {
	
	private final TodoListService service;

	@GetMapping("todo")
	public ResponseEntity<Object> todoList(){
		
		try {
			
			List<Todo> todo = new ArrayList();
			
			todo = service.getTodoList();
			
			return ResponseEntity.status(HttpStatus.OK) // 200
				   .body(todo); 
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				   .body("불러오는 중 예외 발생 : " + e.getMessage());
			
		}
	}
	
	@PostMapping("changeComplete")
	public ResponseEntity<Object> changeComplete(@RequestBody Todo todo){
		
		try {
			
			int changeComplete = service.changeComplete(todo);
			
			if(changeComplete > 0) {
				
				List<Todo> todoList = new ArrayList();
				
				todoList = service.getTodoList();
				
				return ResponseEntity.status(HttpStatus.OK) // 200
						.body(todoList); 
				
			}else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 200
						.body("불러오는 중 예외 발생"); 
			}
			
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				   .body("불러오는 중 예외 발생 : " + e.getMessage());
			
		}
		
	}
	
	@PostMapping("addTodo")
	public ResponseEntity<Object> addTodo(@RequestBody Todo todo){
		
		try {
			
			int addTodo = service.addTodo(todo);
			
			if(addTodo > 0) {
				
				List<Todo> todoList = new ArrayList();
				
				todoList = service.getTodoList();
				
				System.out.println(todo);
				
				return ResponseEntity.status(HttpStatus.OK) // 200
						.body(todoList); 
				
			}else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 200
						.body("불러오는 중 예외 발생"); 
			}
			
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				   .body("불러오는 중 예외 발생 : " + e.getMessage());
			
		}
		
	}
	
	@PostMapping("deleteTodo")
	public ResponseEntity<Object> deleteTodo(@RequestBody Todo todo){
		
		try {
			
			int deleteTodo = service.deleteTodo(todo);
			
			if(deleteTodo > 0) {
				
				List<Todo> todoList = new ArrayList();
				
				todoList = service.getTodoList();
				
				return ResponseEntity.status(HttpStatus.OK) // 200
						.body(todoList); 
				
			}else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 200
						.body("불러오는 중 예외 발생"); 
			}
			
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				   .body("불러오는 중 예외 발생 : " + e.getMessage());
			
		}
		
	}
	
}
