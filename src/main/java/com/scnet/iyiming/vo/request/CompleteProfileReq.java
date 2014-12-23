package com.scnet.iyiming.vo.request;

import org.hibernate.validator.constraints.Length;
import com.scnet.iyiming.vo.RequestBody;

public class CompleteProfileReq extends RequestBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nickName;// 昵称
	private String city;// 城市
	private String sex;// 性别
	private String realName;// 真实姓名
	private String address;// 地址

	@Length(max = 20)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Length(max = 20)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Length(max = 1)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Length(max = 20)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Length(max = 50)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
