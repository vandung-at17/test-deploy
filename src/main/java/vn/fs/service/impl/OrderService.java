package vn.fs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.repository.OrderRepository;
import vn.fs.service.IOrderService;

@Service
public class OrderService implements IOrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public int sumOrder() {
		// TODO Auto-generated method stub
		int sum = orderRepository.sumOrder();
		return sum;
	}
}
