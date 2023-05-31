package vn.fs.service;

import java.util.List;

import vn.fs.model.dto.OrderDto;

public interface IOrderService {
	public int sumOrder ();
	public List<OrderDto> findAll(); 
	public OrderDto findById(Long id);
}
