package vn.fs.service;

import vn.fs.model.request.SmsRequest;

public interface ISmSSenderService {
	public void sendSms (SmsRequest smsRequest);
}
