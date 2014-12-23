package com.scnet.iyiming.vo;

import org.joda.time.DateTime;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.util.MapUtil;

public class ShortMessageVo implements HttpVo {

	private MultiValueMap<String, Object> entity;

	public ShortMessageVo() {
	}

	public ShortMessageVo(String userid, String account, String password, String mobile, String content) {
		this.userid = userid;
		this.account = account;
		this.password = password;
		this.mobile = mobile;
		this.content = content;
		setEntity(MapUtil.convertHttpBean(this));
	}

	private String action = "send";
	private String userid;// id
	private String account;// 发送账户
	private String password;// 账户密码
	private String mobile;// 全部被叫号码
	private String content;// 发送内容
	private String sendTime = IYiMingConstants.DF.DF2.format(DateTime.now().toDate());// 发送时间

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "类型：" + action + "用户ID:" + userid + "发送账户:" + account + ",账户密码:" + password + "全部被叫号码:" + mobile + "发送内容:" + content + "发送时间" + sendTime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public MultiValueMap<String, Object> getEntity() {
		return entity;
	}

	public void setEntity(MultiValueMap<String, Object> entity) {
		this.entity = entity;
	}

	@Override
	public HttpEntity<MultiValueMap<String, Object>> createEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return new HttpEntity<MultiValueMap<String, Object>>(getEntity(), headers);

	}

}
