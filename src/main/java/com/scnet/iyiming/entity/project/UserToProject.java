package com.scnet.iyiming.entity.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.scnet.iyiming.constants.F;
import com.scnet.iyiming.entity.system.VersionedAuditableIdEntity;
import com.scnet.iyiming.entity.user.User;

//项目和会员关注的关系
@Entity
@Table(name = "IY_USER_TO_PROJECT")
public class UserToProject extends VersionedAuditableIdEntity {

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

	private User user;
	private Project project;
	private String flowId = F.Attention.Attention;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "PROJECT_ID")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
}
