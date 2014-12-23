package com.scnet.iyiming.service.handle.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.service.handle.AppHandler;
import com.scnet.iyiming.service.user.ValidateCodeService;
import com.scnet.iyiming.vo.RequestBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.request.CreateVCReq;

//创建验证码
@Service
@Transactional
public class CreateVC implements AppHandler {

	@Autowired
	private ValidateCodeService validateCodeService;

	@Override
	public ResponseBody handle(HttpServletRequest httpServletRequest, RequestBody requestBody) {

		return validateCodeService.createValidateCode((CreateVCReq) requestBody);

	}
}
