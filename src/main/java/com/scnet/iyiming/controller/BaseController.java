package com.scnet.iyiming.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.service.BaseService;
import com.scnet.iyiming.util.BusinessException;
import com.scnet.iyiming.util.PropertiesReader;
import com.scnet.iyiming.vo.ErrorResponseBody;

/**
 * 
 * @description: 统一入口
 * @author: SM
 */
@RestController
@RequestMapping(value = "/rest")
public class BaseController {

	@Autowired
	private BaseService baseService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public com.scnet.iyiming.vo.ResponseBody handle(HttpServletRequest httpServletRequest, String method) throws IOException, InterruptedException {
		return baseService.handle(httpServletRequest, method);
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public com.scnet.iyiming.vo.ResponseBody handleIOException(BusinessException e) {
		e.printStackTrace();
		ErrorResponseBody responseBody = ErrorResponseBody.createErrorResponseBody(e.getCode(), e.getMessage());
		return responseBody;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public com.scnet.iyiming.vo.ResponseBody handleIOException(Exception e) {
		e.printStackTrace();
		String key = IYiMingConstants.CODE_999;
		String value = PropertiesReader.readAsString(key);
		ErrorResponseBody responseBody = ErrorResponseBody.createErrorResponseBody(key, value);
		return responseBody;
	}
}
