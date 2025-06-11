package  edu.kh.todo.todoList.service;

import java.util.List;
import java.util.Map;

import edu.kh.todo.model.dto.Todo;

public interface TodoListService {

	List<Todo> getTodoList();

	int changeComplete(Todo todo);

	int addTodo(Todo todo);

	int deleteTodo(Todo todo);

}
