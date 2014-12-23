package com.scnet.iyiming.service.handle.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.service.handle.AppHandler;
import com.scnet.iyiming.service.project.UserToProjectService;
import com.scnet.iyiming.vo.RequestBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.request.AttentionOrCancelReq;

@Service
@Transactional
public class AttentionOrCancel implements AppHandler {

	@Autowired
	private UserToProjectService userToProjectService;

	@Override
	public ResponseBody handle(HttpServletRequest httpServletRequest, RequestBody requestBody) {
		Long id = (Long) httpServletRequest.getSession().getAttribute(IYiMingConstants.SESSION_USER_ID);
		return userToProjectService.attentionOrCancelReq((AttentionOrCancelReq) requestBody, id);
	}

}
