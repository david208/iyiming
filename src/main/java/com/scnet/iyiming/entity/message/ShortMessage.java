package com.scnet.iyiming.entity.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scnet.iyiming.constants.F;
import com.scnet.iyiming.entity.system.VersionedAuditableIdEntity;

@Entity
@Table(name = "IY_SHORT_MESSAGE")
public class ShortMessage extends VersionedAuditableIdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	private String content;// 短信信息
	private String type;// 短信类型
	private String flowId = F.ShortMessage.SUCCESS;// 结果
	private String respContent;// 返回结果

	@Column(length = 3000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	@Column(length = 3000)
	public String getRespContent() {
		return respContent;
	}

	public void setRespContent(String respContent) {
		this.respContent = respContent;
	}

}
