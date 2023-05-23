package vn.fs.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import vn.fs.config.TwilioConfig;
import vn.fs.model.request.SmsRequest;
import vn.fs.service.ISmSSenderService;

@Service("twilio")
public class SmSSenderService implements ISmSSenderService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmSSenderService.class);
	
	private final TwilioConfig twilioConfig;
	
	@Autowired
	public SmSSenderService(TwilioConfig twilioConfig) {
		this.twilioConfig = twilioConfig;
	}

	@Override
	public void sendSms(SmsRequest smsRequest) {
		// TODO Auto-generated method stub
		if (isPhoneNumberValid (smsRequest.getPhoneNumber())) {
			PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
			PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
			String message = smsRequest.getMessage();
			MessageCreator creator = Message.creator(to, from, message);
			creator.create();
			LOGGER.info("Send sms "+smsRequest);
		}else {
			throw new IllegalArgumentException(
                    "Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number"
            );
		}
		
	}

	private boolean isPhoneNumberValid(String phoneNumber) {
		// TODO Auto-generated method stub
		return true;
	}

}
