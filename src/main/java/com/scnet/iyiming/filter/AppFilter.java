package com.scnet.iyiming.filter;

import javax.servlet.http.HttpServletRequest;

import com.scnet.iyiming.vo.RequestBody;

/**
 * @Description:过滤器
 * 
 * @author:SM
 * 
 */
public interface AppFilter {

	void doFilter(RequestBody requestBody, HttpServletRequest httpServletRequest, AppFilterChain appFilterChain);

}
