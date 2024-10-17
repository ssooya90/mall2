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




}
