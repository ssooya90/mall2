package com.convave.mall2api.service;


import com.convave.mall2api.domain.Todo;
import com.convave.mall2api.dto.PageRequestDTO;
import com.convave.mall2api.dto.PageResponseDTO;
import com.convave.mall2api.dto.TodoDTO;
import com.convave.mall2api.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@Log4j2
@RequiredArgsConstructor // 생성자 자동 주입
public class TodoServiceImpl implements TodoService {

	private final TodoRepository todoRepository;


	@Override
	public Long register(TodoDTO todoDTO) {

		Todo todo = dtoToEntity(todoDTO);
		Todo result = todoRepository.save(todo);

		return result.getTno();

//		log.info("Register todoDTO: " + todoDTO);
//		return null;
	}


	@Override
	public TodoDTO get(Long id) {

		Optional<Todo> result = todoRepository.findById(id);
		Todo todo = result.orElseThrow();

		return entityToDTO(todo);
	}

	@Override
	public void modify(TodoDTO todoDTO) {

		Optional<Todo> result = todoRepository.findById(todoDTO.getTno());
		Todo todo = result.orElseThrow(() -> new IllegalArgumentException("데이터 없음"));

		todo.update("수정" + todo.getTno(),"수정자",true);

		todoRepository.save(todo);

	}

	@Override
	public void remove(Long tno) {
		todoRepository.deleteById(tno);

	}


	@Override
	public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {

		// 페이지네이션 객체 생성
		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("tno").descending());

		// 페이지네이션 객체를 활용하여 리스트(Page 형태) 가져옴
		Page<Todo> result = todoRepository.findAll(pageable);

		List<TodoDTO> dtoList = result
				.get()
				.map(todo -> entityToDTO(todo)).collect(Collectors.toList());

		System.out.println("dtoList");
		System.out.println(dtoList);

		long total = result.getTotalElements();

		PageResponseDTO<TodoDTO> responseDTO =
				PageResponseDTO.<TodoDTO>withAll()
				.dtoList(dtoList)
				.pageRequestDTO(pageRequestDTO)
				.totalCount(total)
				.build();


//		PageResponseDTO<TodoDTO> responseDTO
//				= PageResponseDTO.<TodoDTO>withAll()
//				.dtoList(dtoList)
//				.pageRequestDTO(pageRequestDTO)
//				.totalCount(result.getTotalElements())
//				.build();








		return responseDTO;
	}
}
