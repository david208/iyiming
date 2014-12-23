package com.scnet.iyiming.vo.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.scnet.iyiming.vo.RequestBody;

//创建验证码
public class CreateVCReq extends RequestBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mobile;// 手机号

	@NotBlank
	@Length(min = 11, max = 11)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
