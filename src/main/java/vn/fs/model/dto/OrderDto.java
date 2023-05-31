package vn.fs.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.fs.entities.UserEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDto {

	private Long orderId;
	private Date orderDate;
	private Double amount;
	private UserEntity user;
	private String name;
	private String address;
	private String phone;
	private int status;
}
