package com.scnet.iyiming.service.handle.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.service.VersionLogService;
import com.scnet.iyiming.service.handle.AppHandler;
import com.scnet.iyiming.vo.RequestBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.request.GetVersionReq;

@Service
@Transactional
public class GetVersion implements AppHandler {

	@Autowired
	private VersionLogService versionLogService;

	@Override
	public ResponseBody handle(HttpServletRequest httpServletRequest, RequestBody requestBody) {
		return versionLogService.getVersion((GetVersionReq) requestBody);
	}

}
