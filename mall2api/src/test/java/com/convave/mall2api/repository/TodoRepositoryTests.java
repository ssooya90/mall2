package com.convave.mall2api.repository;


import com.convave.mall2api.domain.Todo;
import com.convave.mall2api.dto.PageRequestDTO;
import com.convave.mall2api.dto.PageResponseDTO;
import com.convave.mall2api.dto.TodoDTO;
import com.convave.mall2api.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private TodoService todoService;

	@Test
	public void test1() {
		log.info("===========================");
		log.info("===========================");
		log.info(todoRepository);
	}

	@Test
	public void testInsert() {


		for (int i = 0; i < 100; i++) {
			Todo todo = Todo.builder()
					.title("title" + i)
					.dueDate(LocalDate.of(2024,10,15))
					.writer("user00")
					.build();

			todoRepository.save(todo);

		}


	}

	@Test
	public void testRead() {

		Long tno = 33L;
		Optional<Todo> result = todoRepository.findById(tno);

		Todo todo = result.orElseThrow();

		log.info(todo);
	}

	@Test
	public void testModify() {

		Long tno = 33L;
		Optional<Todo> result = todoRepository.findById(tno);

		Todo todo = result.orElseThrow(()
				-> new IllegalArgumentException("데이터 없음"));

		todo.update("modify " + tno,"작성자 수정",null,true);

		todoRepository.save(todo);
	}

	@Test
	public void testDelete() {

		Long tno = 33L;
		todoRepository.deleteById(tno);

	}


	@Test
	public void testPaging () {

		Pageable pageable = PageRequest.of(0,10, Sort.by("tno").descending());

		Page<Todo> result = todoRepository.findAll(pageable);

		log.info(result.getTotalElements());

		result.getContent().stream().forEach(todo -> log.info(todo));

	}

	@Test
	public void testRegister () {

		TodoDTO todoDTO = TodoDTO.builder()
				.title("테스트 title")
				.writer("테스트 writer")
				.complete(true)
				.dueDate(LocalDate.of(2024,10,15))
				.build();


		Long tno = todoService.register(todoDTO);

		log.info("tno : " + tno);

	}


	@Test
	public void testGetTodo() {

		Long tno = 100L;

		TodoDTO todoDTO = todoService.get(tno);

		log.info("==================");
		log.info(todoDTO);


	}

	@Test
	public void testList () {

		PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
				.page(2)
				.size(10)
				.build();

		PageResponseDTO<TodoDTO> response = todoService.list(pageRequestDTO);

		log.info(response);

	}


}
