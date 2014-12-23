package com.scnet.iyiming.service.handle.impl;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scnet.iyiming.service.handle.AppHandler;
import com.scnet.iyiming.service.user.UserService;
import com.scnet.iyiming.vo.RequestBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.request.LoginReq;

@Service
@Transactional
public class LoginImpl implements AppHandler {
	@Autowired
	private UserService userService;

	@Override
	public ResponseBody handle(HttpServletRequest httpServletRequest, RequestBody requestBody) {
		LoginReq loginReq = (LoginReq) requestBody;

		return userService.login(httpServletRequest, loginReq);
	}
}
