package com.scnet.iyiming.vo.response;

import com.scnet.iyiming.vo.ResponseBody;

public class GetVersionResp extends ResponseBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5926239747084222993L;

	private String version;
	private String content;
	private String code;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
