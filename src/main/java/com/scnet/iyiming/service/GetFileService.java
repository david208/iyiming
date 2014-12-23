package com.scnet.iyiming.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scnet.iyiming.filter.AppFilterChain;
import com.scnet.iyiming.service.user.FileService;
import com.scnet.iyiming.vo.RequestBody;

@Service
public class GetFileService {

	@Autowired
	private AppFilterChain fileFilterChain;

	@Autowired
	private FileService fileService;

	public byte[] getAvatar(HttpServletRequest httpServletRequest) throws IOException {
		fileFilterChain.doFilter(new RequestBody(), httpServletRequest);
		return fileService.getAvatar(httpServletRequest);
	}

	public byte[] getProjectImage(HttpServletRequest httpServletRequest, Long id) throws IOException {
		return fileService.getProjectImage(id);
	}
}
