package  edu.kh.todo.todoList.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.todo.model.dto.Todo;

@Mapper
public interface TodoListMapper {

	List<Todo> getTodoList();

	int changeComplete(Todo todo);

	int addTodo(Todo todo);

	int deleteTodo(Todo todo);

}
