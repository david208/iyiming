package com.scnet.iyiming.service.handle.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.scnet.iyiming.service.handle.AppHandler;
import com.scnet.iyiming.service.user.FileService;
import com.scnet.iyiming.vo.RequestBody;
import com.scnet.iyiming.vo.ResponseBody;

@Transactional
@Service
public class UploadAvatar implements AppHandler {

	@Autowired
	private FileService fileService;

	@Override
	public ResponseBody handle(HttpServletRequest httpServletRequest, RequestBody requestBody) {

		return fileService.uploadAvatar((MultipartHttpServletRequest) httpServletRequest);
	}

}
