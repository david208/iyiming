package com.scnet.iyiming.vo.web;

import org.hibernate.validator.constraints.NotBlank;

public class UserVo {

	private String pid;
	private String username;
	private String password;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@NotBlank
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotBlank
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
