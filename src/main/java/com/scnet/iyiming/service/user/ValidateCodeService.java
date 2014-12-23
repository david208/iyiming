package com.scnet.iyiming.service.user;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.entity.user.ValidateCode;
import com.scnet.iyiming.repository.user.ValidateCodeRepository;
import com.scnet.iyiming.service.SmsService;
import com.scnet.iyiming.util.ValidateCodeUtil;
import com.scnet.iyiming.vo.ErrorResponseBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.request.CreateVCReq;
import com.scnet.iyiming.vo.request.RegisterReq;

@Service
@Transactional
public class ValidateCodeService {

	@Autowired
	private ValidateCodeRepository validateCodeRepository;
	@Autowired
	private SmsService smsService;

	@Value(value = "${vcTimeOut}")
	private int vcTimeOut;

	/**
	 * @author SM
	 * @description 新建手机验证码
	 */
	public ResponseBody createValidateCode(CreateVCReq createVCReq) {
		ValidateCode validateCode = validateCodeRepository.findOneByMobileAndExpireTimeGreaterThan(createVCReq.getMobile(), DateTime.now().toDate());
		// 是否已经生成
		if (null != validateCode) {
			return ErrorResponseBody.createErrorResponseBody("验证码" + vcTimeOut + "分钟内不可重复申请!");
		} else {
			String code = ValidateCodeUtil.generate6RandomNum();
			validateCode = new ValidateCode();
			validateCode.setCode(code);
			validateCode.setExpireTime(DateTime.now().plusMinutes(vcTimeOut).toDate());
			validateCode.setMobile(createVCReq.getMobile());
			validateCodeRepository.save(validateCode);
			smsService.sendSms(code, createVCReq.getMobile());
			return ResponseBody.createResponseBody("创建验证码成功");
		}

	}

	public boolean validataCodeCheck(RegisterReq registerReq) {
		return validateCodeRepository.countByMobileAndCodeAndExpireTimeGreaterThan(registerReq.getMobile(), registerReq.getValidateCode(), DateTime.now().toDate()) > 0 ? true : false;

	}
}
