package com.convave.mall2api.service;

import com.convave.mall2api.dto.PageRequestDTO;
import com.convave.mall2api.dto.PageResponseDTO;
import com.convave.mall2api.dto.ProductDTO;

public interface ProductService {

	PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

	Long register(ProductDTO productDTO);

	ProductDTO get(Long pno);

}
