package com.scnet.iyiming.vo.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.scnet.iyiming.vo.ResponseBody;

public class GetProjectListResp extends ResponseBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ProjectDetail> projectDetails;

	public List<ProjectDetail> getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(List<ProjectDetail> projectDetails) {
		this.projectDetails = projectDetails;
	}

	public static class ProjectDetail {
		private String id;// 编号
		private String name;// 名字
		private String intro;// 简介
		private BigDecimal amt;// 金额
		private String type;// 类型
		private String country;// 国家
		private Date releaseDate;// 发布日期
		private String imageUrl;// 图片url
		private String flowId;// 状态
		private Long attentionCount;// 关注数
		private String attentionFlag = "N";// 是否已关注 Y N
		private String amtType;//货币类型
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

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

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Long getAttentionCount() {
			return attentionCount;
		}

		public void setAttentionCount(Long attentionCount) {
			this.attentionCount = attentionCount;
		}

		public String getAttentionFlag() {
			return attentionFlag;
		}

		public void setAttentionFlag(String attentionFlag) {
			this.attentionFlag = attentionFlag;
		}

		public String getAmtType() {
			return amtType;
		}

		public void setAmtType(String amtType) {
			this.amtType = amtType;
		}

	}

}
