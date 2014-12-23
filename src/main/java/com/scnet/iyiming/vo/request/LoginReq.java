package com.scnet.iyiming.vo.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.scnet.iyiming.vo.RequestBody;

/**
 * 
 * @author wujinsong
 * 
 */
public class LoginReq extends RequestBody {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1313695554811588484L;

	private String username;

	private String password;

	private String ivc;// 图片验证码

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

	@Length(max = 4)
	public String getIvc() {
		return ivc;
	}

	public void setIvc(String ivc) {
		this.ivc = ivc;
	}
}
