package com.scnet.iyiming.filter.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scnet.iyiming.filter.AppFilter;
import com.scnet.iyiming.filter.AppFilterChain;
import com.scnet.iyiming.vo.RequestBody;

public class AppFilterChainImp implements AppFilterChain {

	private List<? extends AppFilter> filters = new ArrayList<AppFilter>();

	public void setFilters(List<? extends AppFilter> filters) {
		this.filters = new ArrayList<AppFilter>(filters);
	}

	@Override
	public void doFilter(RequestBody requestBody, HttpServletRequest httpServletRequest) {
		new VirtualFilterChain(filters).doFilter(requestBody, httpServletRequest);

	}

	private static class VirtualFilterChain implements AppFilterChain {

		private List<? extends AppFilter> filters = new ArrayList<AppFilter>();

		private int currentPosition = 0;

		private VirtualFilterChain(List<? extends AppFilter> additionalFilters) {
			this.filters = additionalFilters;
		}

		@Override
		public void doFilter(RequestBody requestBody, HttpServletRequest httpServletRequest) {

			if (currentPosition == filters.size()) {
				return;
			} else {
				currentPosition++;
				AppFilter nextFilter = filters.get(currentPosition - 1);
				nextFilter.doFilter(requestBody, httpServletRequest, this);
			}

		}
	}
}
