package edu.kh.project.todoList.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.todoList.model.mapper.TodoListMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class TodoListServiceImpl {

	private final TodoListMapper mapper;

}
