package com.convave.mall2api.controller;

import com.convave.mall2api.dto.PageRequestDTO;
import com.convave.mall2api.dto.PageResponseDTO;
import com.convave.mall2api.dto.TodoDTO;
import com.convave.mall2api.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/todo")
public class TodoController {

	private final TodoService todoService;

	@GetMapping("/{tno}")
	public TodoDTO getTodo(@PathVariable Long tno) {
		return todoService.get(tno);
	}

	@GetMapping("/list")
	public PageResponseDTO<TodoDTO> getTodoList (PageRequestDTO pageRequestDTO){
		return todoService.list(pageRequestDTO);
	}

	@PostMapping("/")
	public Map<String, Long> register (@RequestBody TodoDTO todoDTO){

		Long tno = todoService.register(todoDTO);

		return Map.of("tno", tno);
	}

	@PutMapping("/{tno}")
	public Map<String, String> register (@PathVariable(name="tno") Long tno , @RequestBody TodoDTO todoDTO){

		todoDTO.setTno(tno);

		todoService.modify(todoDTO);

		return Map.of("result", "success");
	}

	@DeleteMapping("/{tno}")
	public Map<String, String> remove (@PathVariable(name="tno") Long tno){

		todoService.remove(tno);

		return Map.of("result", "success");

	}


}
