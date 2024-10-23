package com.convave.mall2api.repository;

import com.convave.mall2api.domain.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

	@Autowired
	ProductRepository productRepository;

	@Test
	public void testInsert() {

		for (int i = 0; i < 10; i++) {

			Product product = Product.builder()
					.pname("상품"+i)
					.pdesc("설명"+i)
					.price(100*i)
					.delFlag(false)
					.build();

			product.addImageString(UUID.randomUUID()+"_"+"IMAGE1.jpg");
			product.addImageString(UUID.randomUUID()+"_"+"IMAGE2.jpg");

			productRepository.save(product);

		}

	}

	@Test
	@Transactional
	public void testRead() {

		Long pno = 1L;

		Optional<Product> result = productRepository.findById(pno);

		Product product = result.orElseThrow();

		log.info("========= product ========");
		log.info(product);
		log.info(product.getImageList());

	}

	@Test
	public void testRead2() {

		Long pno = 1L;

		Optional<Product> result = productRepository.selectOne(pno);

		Product product = result.orElseThrow();

		log.info("========= product ========");
		log.info(product);
		log.info(product.getImageList());

	}


	@Test
	public void testUpdate () {

		Long pno = 1L;

		Optional<Product> result = productRepository.selectOne(pno);

		Product product = result.orElseThrow();

		product.setName("이름 변경");
		product.setDesc("설명 변경");
		product.setPrice(999);

		// 첨부파일 수정

		product.clearList();

		product.addImageString(UUID.randomUUID()+"_"+"PIMAGE1.jpg");
		product.addImageString(UUID.randomUUID()+"_"+"PIMAGE2.jpg");
		product.addImageString(UUID.randomUUID()+"_"+"PIMAGE3.jpg");

		productRepository.save(product);

	}


	@Test
	public void testList () {

		Pageable pageable = PageRequest.of(0,10, Sort.by("pno").descending());

		Page<Object[]> result = productRepository.selectList(pageable);

		result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));

	}

}
