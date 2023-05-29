package vn.fs.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReCaptchResponseType {
	private boolean success;
	private String challenge_ts;
	private String hostname;
}
