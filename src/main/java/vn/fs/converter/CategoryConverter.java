package vn.fs.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import vn.fs.entities.CategoryEntity;
import vn.fs.model.dto.CategoryDto;
@Component
public class CategoryConverter {
	public CategoryDto toDto (CategoryEntity categoryEntity) {
		CategoryDto categoryDto = new CategoryDto();
		BeanUtils.copyProperties(categoryEntity, categoryDto);
		return categoryDto;
	}
	
	public CategoryEntity toEntity (CategoryDto categoryDto) {
		CategoryEntity categoryEntity = new CategoryEntity();
		BeanUtils.copyProperties(categoryDto, categoryEntity);
		return categoryEntity;
	}
}
