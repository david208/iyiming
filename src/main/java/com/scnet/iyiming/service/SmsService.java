package com.scnet.iyiming.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;

import com.scnet.iyiming.constants.F;
import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.entity.message.ShortMessage;
import com.scnet.iyiming.service.user.ShortMessageService;
import com.scnet.iyiming.util.XStreamTool;
import com.scnet.iyiming.vo.ShortMessageResp;
import com.scnet.iyiming.vo.ShortMessageVo;

@Service
@Transactional
public class SmsService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestOperations restClient;

	@Autowired
	private ShortMessageService shortMessageService;

	@Value(value = "${userid}")
	private String userid;
	@Value(value = "${account}")
	private String account;
	@Value(value = "${password}")
	private String password;

	@Value(value = "${sms_url}")
	private String url;

	@Value(value = "${vcTimeOut}")
	private int vcTimeOut;

	@Async
	public void sendSms(String code, String mobile) {
		String nr = "尊敬的用户，感谢您的注册。您的验证码是【" + code + "】，" + vcTimeOut + "分钟内有效，请妥善保管！【安移融网络科技】";
		ShortMessageVo shortMessageVo = new ShortMessageVo(userid, account, password, mobile, nr);

		String result = restClient.postForObject(url, shortMessageVo.createEntity(), String.class);
		ShortMessageResp shortMessageResp = XStreamTool.toBean(result, ShortMessageResp.class);

		ShortMessage shortMessage = new ShortMessage();

		shortMessage.setContent(shortMessageVo.toString());
		shortMessage.setType(IYiMingConstants.MESSAGE_VC);
		if (!shortMessageResp.getReturnstatus().equals("Success")) {
			logger.warn("短信发送失败," + shortMessageVo.toString());
			shortMessage.setFlowId(F.ShortMessage.FAILED);
		}
		shortMessage.setRespContent(result);
		shortMessageService.save(shortMessage);
	}

}
