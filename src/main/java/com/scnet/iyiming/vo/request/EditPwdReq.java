package com.scnet.iyiming.vo.request;

import org.hibernate.validator.constraints.NotBlank;

import com.scnet.iyiming.vo.RequestBody;

public class EditPwdReq extends RequestBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String oldPassword;
	private String newPassword;
	private String newPassword2;

	@NotBlank
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@NotBlank
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@NotBlank
	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}

}
