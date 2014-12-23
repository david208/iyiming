package com.scnet.iyiming.service.handle.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.service.handle.AppHandler;
import com.scnet.iyiming.service.user.FeedBackService;
import com.scnet.iyiming.vo.RequestBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.request.FeedBackReq;

@Service
@Transactional
public class FeedBack implements AppHandler {

	@Autowired
	private FeedBackService feedBackService;

	@Override
	public ResponseBody handle(HttpServletRequest httpServletRequest, RequestBody requestBody) {
		Long id = (Long) httpServletRequest.getSession().getAttribute(IYiMingConstants.SESSION_USER_ID);
		return feedBackService.feedBack((FeedBackReq) requestBody, id);
	}

}
