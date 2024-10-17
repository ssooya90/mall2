package com.convave.mall2api.service;

import com.convave.mall2api.dto.PageRequestDTO;
import com.convave.mall2api.dto.PageResponseDTO;
import com.convave.mall2api.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductServiceTests {

	@Autowired
	private ProductService productService;


	@Test
	public void testList (){

		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

		PageResponseDTO<ProductDTO> result = productService.getList(pageRequestDTO);

		log.info(result.getDtoList());

	}

	@Test
	public void register () {

		ProductDTO productDTO = ProductDTO.builder()
				.pname("테스트 상품")
				.pdesc("테스트 설명")
				.price(5555)
				.build();

		//uuid가 있어야 함
		productDTO.setUploadFileNames(
				List.of(
						UUID.randomUUID()+"_"+"Test1.jpg",
						UUID.randomUUID()+"_"+"Test2.jpg")
		);

		Long pno = productService.register(productDTO);


		log.info("========");
		log.info(pno);



	}

	@Test
	public void read(){

		Long pno = 2L;

		ProductDTO productDTO = productService.get(pno);

		log.info(productDTO);
		log.info(productDTO.getUploadFileNames());


	}

}
