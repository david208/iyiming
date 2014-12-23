package com.scnet.iyiming.service.handle.impl;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scnet.iyiming.service.handle.AppHandler;
import com.scnet.iyiming.service.user.UserService;
import com.scnet.iyiming.vo.RequestBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.request.EditPwdReq;

@Service
@Transactional
public class EditPwd implements AppHandler {
	@Autowired
	private UserService userService;

	@Override
	public ResponseBody handle(HttpServletRequest httpServletRequest, RequestBody requestBody) {
		EditPwdReq editPwdReq = (EditPwdReq) requestBody;

		return userService.changePassword(httpServletRequest, editPwdReq.getOldPassword(), editPwdReq.getNewPassword(), editPwdReq.getNewPassword2());
	}
}
