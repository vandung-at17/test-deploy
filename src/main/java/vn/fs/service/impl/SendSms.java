package vn.fs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import vn.fs.model.request.SmsRequest;
import vn.fs.service.ISmSSenderService;

@Service
public class SendSms {
	private final ISmSSenderService iSmSSenderService;
	
	@Autowired
	public SendSms (@Qualifier("twilio") SmSSenderService senderService) {
		this.iSmSSenderService = senderService;
	}
	
	public void sendSms (SmsRequest smsRequest) {
		iSmSSenderService.sendSms(smsRequest);
	}
}
