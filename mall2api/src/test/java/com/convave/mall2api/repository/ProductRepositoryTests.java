package com.convave.mall2api.repository;

import com.convave.mall2api.domain.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

}
