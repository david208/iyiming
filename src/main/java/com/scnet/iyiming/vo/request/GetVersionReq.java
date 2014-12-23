package com.scnet.iyiming.vo.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.scnet.iyiming.vo.RequestBody;

public class GetVersionReq extends RequestBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;

	@NotBlank
	@Length(max = 1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
