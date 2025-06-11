package  edu.kh.todo.todoList.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.todoList.model.mapper.TodoListMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class TodoListServiceImpl implements TodoListService{

	private final TodoListMapper mapper;

	
	@Override
	public List<Todo> getTodoList() {
		return mapper.getTodoList();
	}
	
	@Override
	public int changeComplete(Todo todo) {
		
		if(todo.getComplete().equals("N")) {
			todo.setComplete("Y");
		}else {
			todo.setComplete("N");
		}
		
		return mapper.changeComplete(todo);
	}
	
	@Override
	public int addTodo(Todo todo) {
		return mapper.addTodo(todo);
	}
	
	@Override
	public int deleteTodo(Todo todo) {
		return mapper.deleteTodo(todo);
	}
}
