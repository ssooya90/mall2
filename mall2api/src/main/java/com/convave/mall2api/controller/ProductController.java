package com.convave.mall2api.controller;

import com.convave.mall2api.dto.PageRequestDTO;
import com.convave.mall2api.dto.PageResponseDTO;
import com.convave.mall2api.dto.ProductDTO;
import com.convave.mall2api.service.ProductService;
import com.convave.mall2api.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Log4j2
public class ProductController {


	private final ProductService productService;

	private final CustomFileUtil fileUtil;


	@GetMapping("/list")
	public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO) {

		return productService.getList(pageRequestDTO);

	}


	// 파일데이터는 json으로 못 받음
	@PostMapping("/")
	public Map<String, Long> register(ProductDTO productDTO) throws Exception{

		log.info(productDTO);

		List<MultipartFile> files = productDTO.getFiles();

		List<String> uploadedFileNames = fileUtil.saveFiles(files);
		productDTO.setUploadFileNames(uploadedFileNames);

		Long pno = productService.register(productDTO);

		Thread.sleep(2000);

		return Map.of("RESULT",pno);
	}


	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFileGet(@PathVariable String fileName) throws Exception {

		return fileUtil.getFile(fileName);

	}

	@GetMapping("/{pno}")
	public ProductDTO read(@PathVariable(name="pno") Long pno) throws Exception {

		return productService.get(pno);

	}

	@PutMapping("{pno}")
	public Map<String, String> update(@PathVariable(name="pno") Long pno, ProductDTO productDTO) throws Exception {

		productDTO.setPno(pno);

		// DB에 저장된 entity를 가져와서 dto 변환
		ProductDTO oldProductDTO = productService.get(pno);

		// DB에 존재하는 파일들을 저장
		List<String> oldFileNames = oldProductDTO.getUploadFileNames();

		// 새로 업로드해야 하는 파일
		List<MultipartFile> files = productDTO.getFiles();

		// 새로 업로드된 파일
		List<String> currentUploadFileNames = fileUtil.saveFiles(files);


		// 화면 변화 없이 유지된 파일
		List<String> uploadedFileNames = productDTO.getUploadFileNames();

		// 유지되는 파일들 + 새로 업로드된 파일

		if(currentUploadFileNames != null && currentUploadFileNames.size() > 0){

			uploadedFileNames.addAll(currentUploadFileNames);

		}

		// 수정작업
		productService.modify(productDTO);

		if(oldFileNames != null && oldFileNames.size() > 0){

			// 지워야 하는 파일 목록 찾기
			// 예전 파일들 중 지워져야 하는 파일들

			List<String> removeFiles = oldFileNames.stream().filter(fileName -> uploadedFileNames.indexOf(fileName) == -1).toList();

			// 실제 파일 삭제
			fileUtil.deleteFiles(removeFiles);

		}


		return Map.of("RESULT","SUCCESS");
	}


	@DeleteMapping("/{pno}")
	Map<String, String> remove (@PathVariable(name="pno") Long pno) throws Exception {

		List<String> oldFileNames = productService.get(pno).getUploadFileNames();

		fileUtil.deleteFiles(oldFileNames);

		productService.remove(pno);

		return Map.of("RESULT","SUCCESS");


	}



}
