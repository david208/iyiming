package com.scnet.iyiming.vo.web;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class QueryProjectVo extends PageFormVo {

	private String searchTime;
	private String projectname;
	private String projectType;

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

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

}
