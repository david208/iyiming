package com.scnet.iyiming.filter;

import javax.servlet.http.HttpServletRequest;

import com.scnet.iyiming.vo.RequestBody;

public interface AppFilterChain {

	public void doFilter(RequestBody requestBody, HttpServletRequest httpServletRequest);

}
