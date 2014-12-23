package com.scnet.iyiming.vo.response;

import com.scnet.iyiming.vo.ResponseBody;

public class CaptchaResp extends ResponseBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private byte[] image;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
