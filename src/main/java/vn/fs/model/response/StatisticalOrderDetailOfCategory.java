package vn.fs.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatisticalOrderDetailOfCategory {
	private Boolean status;
	private String categoryName;
	private int quantity;// Tổng số lượng sản phẩm bán ra mỗi loại
	private double sumPrice; // Tổng tiền thu về của mỗi loại sản phẩm
	private double averagePrice;// Mức giá trung bình của sản phẩm
	private double minimumPrice;// Mức giá tối thiểu hay là mức giá nhỏ nhất 
	private double maximumPrice;
}
