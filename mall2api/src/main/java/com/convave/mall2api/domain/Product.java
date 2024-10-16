package com.convave.mall2api.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "tbl_product")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "imageList")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pno;

	private String pname;

	private int price;

	private String pdesc;

	private boolean delFlag;

	// 컬렉션 객채임을 JPA가 알 수 있께 함
	@ElementCollection
	@Builder.Default
	private List<ProductImage> imageList = new ArrayList<>();


	public void setPrice(int price) {
		this.price = price;
	}

	public void setDesc(String desc) {
		this.pdesc = desc;
	}


	public void setName(String name) {
		this.pname = name;
	}

	public void isDelFlag(boolean delFlag){
		this.delFlag = delFlag;
	}

	public void addImgae(ProductImage image) {
		image.setOrd(imageList.size());
		imageList.add(image);
	}

	public void addImageString(String fileName){

		ProductImage productImage = ProductImage.builder()
				.fileName(fileName)
				.build();

		addImgae(productImage);
	}


	public void clearList() {
		this.imageList.clear();
	}

}
