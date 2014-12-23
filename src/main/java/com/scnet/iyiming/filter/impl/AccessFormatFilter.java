package com.scnet.iyiming.filter.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.filter.AppFilter;
import com.scnet.iyiming.filter.AppFilterChain;
import com.scnet.iyiming.session.IYiMingSession;
import com.scnet.iyiming.util.BusinessException;
import com.scnet.iyiming.util.MapUtil;
import com.scnet.iyiming.util.PropertiesReader;
import com.scnet.iyiming.vo.RequestBody;

/**
 * 
 * @author wujinsong
 * 
 */
@Service
public class AccessFormatFilter implements AppFilter {

	@Override
	public void doFilter(RequestBody requestBody, HttpServletRequest httpServletRequest, AppFilterChain appFilterChain) {
		RequestBody newBody;
		try {
			Map<String, String[]> paramters = httpServletRequest.getParameterMap();
			// 参数map转化为bean
			newBody = (RequestBody) MapUtil.convertMap(IYiMingConstants.APP_VO.get(requestBody.getMethod()), paramters);
			IYiMingSession.put("requestBody", newBody);
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new BusinessException(IYiMingConstants.CODE_115, PropertiesReader.readAsString(IYiMingConstants.CODE_115));
		}
		appFilterChain.doFilter(newBody, httpServletRequest);
	}
}
