package vn.fs.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import vn.fs.entities.ProductEntity;
import vn.fs.model.dto.ProductDto;

@Component
public class ProductConverter {
	public ProductDto toDto(ProductEntity productEntity) {
		ProductDto productDto = new ProductDto();
		productDto.setCategory(productEntity.getCategory());
		BeanUtils.copyProperties(productEntity,productDto);
		return productDto;
	}
	
	public ProductEntity toEntity (ProductDto productDto) {
		ProductEntity productEntity = new ProductEntity();
		productEntity.setCategory(productEntity.getCategory());
		BeanUtils.copyProperties(productDto, productEntity);
		return productEntity;
	}
}
