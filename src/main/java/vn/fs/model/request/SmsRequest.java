package vn.fs.model.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Data
@Getter
@Setter
@ToString
public class SmsRequest {
	@NotBlank
	private final String phoneNumber; // Số điện thoại đích cần gửi đến
	
	@NotBlank
	private final String message; //Message muốn gửi
}
