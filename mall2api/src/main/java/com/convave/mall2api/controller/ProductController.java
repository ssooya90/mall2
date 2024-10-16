package com.convave.mall2api.controller;

import com.convave.mall2api.dto.ProductDTO;
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


	private final CustomFileUtil fileUtil;



	@PostMapping("/")
	public Map<String, String> register(ProductDTO productDTO) throws Exception{


		log.info(productDTO);

		List<MultipartFile> files = productDTO.getFiles();

		List<String> uploadedFileNames = fileUtil.saveFiles(files);
		productDTO.setUploadFileNames(uploadedFileNames);

		log.info(uploadedFileNames);

		return Map.of("RESULT","SUCCESS");
	}


	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFileGet(@PathVariable String fileName) throws Exception {

		return fileUtil.getFile(fileName);

	}




}
