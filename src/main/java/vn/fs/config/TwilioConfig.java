package vn.fs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties("twilio")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class TwilioConfig {
	private String accountSid;
	private String authToken;
	private String trialNumber;
}
