package com.scnet.iyiming.entity.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.scnet.iyiming.entity.system.VersionedAuditableIdEntity;

@Entity
@Table(name = "IY_VALIDATE_CODE")
public class ValidateCode extends VersionedAuditableIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2356113147454990168L;

	@Override
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	private Date expireTime;// 验证码有效时间
	private String mobile;// 手机号
	private String code;// 验证码

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

}
