package com.scnet.iyiming.filter.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.filter.AppFilter;
import com.scnet.iyiming.filter.AppFilterChain;
import com.scnet.iyiming.util.BusinessException;
import com.scnet.iyiming.util.CommonUtil;
import com.scnet.iyiming.util.MapUtil;
import com.scnet.iyiming.util.PropertiesReader;
import com.scnet.iyiming.vo.RequestBody;

@Service
public class SignFilter implements AppFilter {

	@Override
	public void doFilter(RequestBody requestBody, HttpServletRequest httpServletRequest, AppFilterChain appFilterChain) {

		try {
			Map<String, Object> paramters = MapUtil.changeGetParameterMap(httpServletRequest);
			String sign = CommonUtil.getSequenceString(paramters);
			if (!requestBody.getSign().equals(sign)) {
				String key = IYiMingConstants.CODE_114;
				throw new BusinessException(key, PropertiesReader.readAsString(key));
			}
		} catch (Exception e) {
			throw e;
		}
		appFilterChain.doFilter(requestBody, httpServletRequest);
	}
}
