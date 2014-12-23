package com.scnet.iyiming.entity.project;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scnet.iyiming.constants.F;
import com.scnet.iyiming.entity.system.VersionedAuditableIdEntity;

@Entity
@Table(name = "IY_PROJECT")
public class Project extends VersionedAuditableIdEntity {

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

	private String name;// 名字
	private String intro;// 简介
	private BigDecimal amt;// 金额
	private String type;// 类型
	private String country;// 国家
	private Date releaseDate;// 发布日期
	private String imageUrl;// 图片url
	private String flowId = F.Project.New;// 状态
	private Date finishDate;// 下架日期
	private String amtType;// 货币类型
	private Long attentionCount;

	private Set<UserToProject> userToProjects = new HashSet<UserToProject>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 1800)
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	@JsonIgnore
	public Set<UserToProject> getUserToProjects() {
		return userToProjects;
	}

	public void setUserToProjects(Set<UserToProject> userToProjects) {
		this.userToProjects = userToProjects;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getAmtType() {
		return amtType;
	}

	public void setAmtType(String amtType) {
		this.amtType = amtType;
	}

	@Transient
	public Long getAttentionCount() {
		return attentionCount;
	}

	public void setAttentionCount(Long attentionCount) {
		this.attentionCount = attentionCount;
	}

}
