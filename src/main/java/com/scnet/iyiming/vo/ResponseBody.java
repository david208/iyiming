package com.scnet.iyiming.vo;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.util.JsonHelper;

public class ResponseBody implements Serializable {

	private String status = IYiMingConstants.CODE_000;// 状态 (000:正常)

	private String memo;// 备注

	public static ResponseBody createResponseBody(String memo) {
		ResponseBody responseBody = new ResponseBody();
		responseBody.setStatus(IYiMingConstants.CODE_000);
		responseBody.setMemo(memo);
		return responseBody;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		try {
			return JsonHelper.toJson(this);
		} catch (Exception e) {
			return "";
		}
	}

	@NotBlank
	@Size(max = 30)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Size(max = 3000)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
