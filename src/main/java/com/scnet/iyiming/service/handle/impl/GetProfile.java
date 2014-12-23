package com.scnet.iyiming.service.handle.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.service.handle.AppHandler;
import com.scnet.iyiming.service.user.UserService;
import com.scnet.iyiming.vo.RequestBody;
import com.scnet.iyiming.vo.ResponseBody;

@Service
@Transactional(readOnly = true)
public class GetProfile implements AppHandler {

	@Autowired
	private UserService userService;

	@Override
	public ResponseBody handle(HttpServletRequest httpServletRequest, RequestBody requestBody) {
		Long id = (Long) httpServletRequest.getSession().getAttribute(IYiMingConstants.SESSION_USER_ID);
		return userService.getProfile(id, httpServletRequest.getContextPath());
	}
}
