package vn.fs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.converter.OrderConverter;
import vn.fs.entities.OrderEntity;
import vn.fs.model.dto.OrderDto;
import vn.fs.repository.OrderRepository;
import vn.fs.service.IOrderService;

@Service
public class OrderService implements IOrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderConverter orderConverter;
	
	@Override
	public int sumOrder() {
		// TODO Auto-generated method stub
		int sum = orderRepository.sumOrder();
		return sum;
	}

	@Override
	public List<OrderDto> findAll() {
		// TODO Auto-generated method stub
		List<OrderDto> orderDtos = new ArrayList<>();
		List<OrderEntity> orderEntities = orderRepository.findAll();
		for (OrderEntity orderEntity : orderEntities) {
			OrderDto orderDto = orderConverter.toDto(orderEntity);
			orderDtos.add(orderDto);
		}
		return orderDtos;
	}

	@Override
	public OrderDto findById(Long id) {
		// TODO Auto-generated method stub
		OrderDto orderDto = orderConverter.toDto(orderRepository.getById(id));
		return orderDto;
	}
}
