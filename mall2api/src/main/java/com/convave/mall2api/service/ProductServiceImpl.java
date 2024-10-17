package com.convave.mall2api.service;

import com.convave.mall2api.domain.Product;
import com.convave.mall2api.domain.ProductImage;
import com.convave.mall2api.dto.PageRequestDTO;
import com.convave.mall2api.dto.PageResponseDTO;
import com.convave.mall2api.dto.ProductDTO;
import com.convave.mall2api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {

		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("pno").descending());


		Page<Object[]> result = productRepository.selectList(pageable);

		List<ProductDTO> dtoList = result.get().map(arr -> {

			Product product = (Product) arr[0];
			ProductImage productImage = (ProductImage) arr[1];

			ProductDTO productDTO = ProductDTO.builder()
					.pno(product.getPno())
					.pname(product.getPname())
					.pdesc(product.getPdesc())
					.price(product.getPrice())
					.build();

			String imageStr = productImage.getFileName();
			productDTO.setUploadFileNames(List.of(imageStr));

			return productDTO;

		}).collect(Collectors.toList());

		long totalCount = result.getTotalElements();

		return PageResponseDTO.<ProductDTO>withAll()
				.dtoList(dtoList)
				.totalCount(totalCount)
				.pageRequestDTO(pageRequestDTO)
				.build();
	}


	@Override
	public Long register(ProductDTO productDTO) {

		Product product = dtoToEntity(productDTO);

		Product result = productRepository.save(product);

		return result.getPno();
	}

	public Product dtoToEntity(ProductDTO productDTO) {

		Product product = Product.builder()
				.pno(productDTO.getPno())
				.pname(productDTO.getPname())
				.pdesc(productDTO.getPdesc())
				.price(productDTO.getPrice())
				.build();

		List<String> uploadFileNames = productDTO.getUploadFileNames();


		if(uploadFileNames == null) {
			return product;
		}

		uploadFileNames.stream().forEach(uploadName -> {
			product.addImageString(uploadName);
		});

		return product;

	}

	@Override
	public ProductDTO get(Long pno) {

		Optional<Product> result = productRepository.selectOne(pno);

		Product product = result.orElseThrow();

		return entityToDTO(product);
	}


	public ProductDTO entityToDTO(Product product) {


		ProductDTO productDTO = ProductDTO.builder()
				.pno(product.getPno())
				.pname(product.getPname())
				.pdesc(product.getPdesc())
				.price(product.getPrice())
				.build();


		List<ProductImage> productImageList = product.getImageList();

		if(productImageList == null || productImageList.size() == 0) {
			return productDTO;
		}

		List<String> fileNameList = productImageList.stream().map(ProductImage::getFileName).toList();

		productDTO.setUploadFileNames(fileNameList);


		return productDTO;
	}
}