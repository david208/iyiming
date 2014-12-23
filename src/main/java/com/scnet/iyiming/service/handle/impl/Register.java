package com.scnet.iyiming.service.handle.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.service.handle.AppHandler;
import com.scnet.iyiming.service.user.UserService;
import com.scnet.iyiming.service.user.ValidateCodeService;
import com.scnet.iyiming.vo.ErrorResponseBody;
import com.scnet.iyiming.vo.RequestBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.request.RegisterReq;

@Service
@Transactional
public class Register implements AppHandler {

	@Autowired
	private UserService userService;

	@Autowired
	private ValidateCodeService validateCodeService;

	@Override
	public ResponseBody handle(HttpServletRequest httpServletRequest, RequestBody requestBody) {
		RegisterReq registerReq = (RegisterReq) requestBody;
		if (validateCodeService.validataCodeCheck(registerReq))
			return userService.register(registerReq);
		else
			return ErrorResponseBody.createErrorResponseBody("手机号和验证码不匹配!");
	}
}
