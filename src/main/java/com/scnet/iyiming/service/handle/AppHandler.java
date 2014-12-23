package com.scnet.iyiming.service.handle;

import javax.servlet.http.HttpServletRequest;

import com.scnet.iyiming.vo.RequestBody;
import com.scnet.iyiming.vo.ResponseBody;

/**
 * 
 * @description: 接口处理器
 * @author: SM
 */
public interface AppHandler {

	/* 处理接口请求 */
	ResponseBody handle(HttpServletRequest httpServletRequest, RequestBody requestBody);
}
