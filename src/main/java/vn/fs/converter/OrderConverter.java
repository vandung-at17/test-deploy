package vn.fs.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import vn.fs.entities.OrderEntity;
import vn.fs.model.dto.OrderDto;

@Component
public class OrderConverter {
	public OrderDto toDto(OrderEntity orderEntity) {
		OrderDto orderDto = new OrderDto();
		orderDto.setUser(orderEntity.getUser());
		BeanUtils.copyProperties(orderEntity, orderDto);
		return orderDto;
	}
}
