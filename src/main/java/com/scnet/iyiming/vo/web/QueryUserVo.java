package com.scnet.iyiming.vo.web;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class QueryUserVo extends PageFormVo {

	private String searchTime;
	private String username;
	private String userType;
	private Long projectId;

	public String getSearchTime() {
		return searchTime;
	}

	public Date getSearchDate() {
		switch (this.searchTime) {
		case "week":
			return DateUtils.addDays(new Date(), -7);
		case "month":
			return DateUtils.addMonths(new Date(), -1);
		case "season":
			return DateUtils.addMonths(new Date(), -3);
		case "half":
			return DateUtils.addMonths(new Date(), -6);
		case "year":
			return DateUtils.addYears(new Date(), -1);
		default:
			return null;
		}

	}

	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

}
