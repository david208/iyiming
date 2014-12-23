package com.scnet.iyiming.vo.response;

import com.scnet.iyiming.vo.ResponseBody;

public class GetProjectDetailResp extends ResponseBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GetProjectListResp.ProjectDetail projectDetail;

	public GetProjectListResp.ProjectDetail getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(GetProjectListResp.ProjectDetail projectDetail) {
		this.projectDetail = projectDetail;
	}

}
