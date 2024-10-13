package com.convave.mall2api.service;

import com.convave.mall2api.domain.Todo;
import com.convave.mall2api.dto.PageRequestDTO;
import com.convave.mall2api.dto.PageResponseDTO;
import com.convave.mall2api.dto.TodoDTO;

public interface TodoService {

	Long register(TodoDTO todoDTO);

	TodoDTO get(Long id);

	void modify(TodoDTO todoDTO);

	void remove(Long tno);

	PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);

//	Pageable list(PageRequestDTO pageRequestDTO)


	default TodoDTO entityToDTO(Todo todo){
		return TodoDTO.builder()
				.tno(todo.getTno())
				.title(todo.getTitle())
				.writer(todo.getWriter())
				.complete(todo.isComplete())
				.dueDate(todo.getDueDate())
				.build();
	}

	default Todo dtoToEntity(TodoDTO todoDTO){
		return Todo.builder()
				.tno(todoDTO.getTno())
				.title(todoDTO.getTitle())
				.writer(todoDTO.getWriter())
				.complete(todoDTO.isComplete())
				.dueDate(todoDTO.getDueDate())
				.build();
	}


}
