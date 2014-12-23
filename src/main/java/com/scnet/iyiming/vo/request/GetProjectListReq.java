package com.scnet.iyiming.vo.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.scnet.iyiming.annotation.DateTimeFormat;
import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.vo.RequestBody;

public class GetProjectListReq extends RequestBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pageSize = "10";
	private String pageNo = "1";

	private String amtMin;// 金额
	private String amtMax;// 金额
	private String country;// 国家
	private String releaseDateMin;// 发布日期
	private String releaseDateMax;// 发布日期

	private String attention;// 是否关注
	private String type;// 类型

	private String amtOrder;// 排序方式 A,B
	private String dateType;// 日期过滤类型,1,2,3 最新发布／3天前／7天前／

	private String name;// 名字

	@Length(max = 20)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@NotBlank
	@Length(max = 3)
	@Pattern(message = "必须是自然数", regexp = "^[1-9]\\d*$")
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	@NotBlank
	@Size(max = 3)
	@Pattern(message = "必须是自然数", regexp = "^[1-9]\\d*$")
	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	@Pattern(message = "需要为##.00格式", regexp = "^(([1-9]\\d{1,14})|0)(\\.\\d{1,2})?$")
	public String getAmtMin() {
		return amtMin;
	}

	public void setAmtMin(String amtMin) {
		this.amtMin = amtMin;
	}

	@Pattern(message = "需要为##.00格式", regexp = "^(([1-9]\\d{1,14})|0)(\\.\\d{1,2})?$")
	public String getAmtMax() {
		return amtMax;
	}

	public void setAmtMax(String amtMax) {
		this.amtMax = amtMax;
	}

	@DateTimeFormat(pattern = IYiMingConstants.YMD, allowBlank = true)
	public String getReleaseDateMin() {
		return releaseDateMin;
	}

	public void setReleaseDateMin(String releaseDateMin) {
		this.releaseDateMin = releaseDateMin;
	}

	@DateTimeFormat(pattern = IYiMingConstants.YMD, allowBlank = true)
	public String getReleaseDateMax() {
		return releaseDateMax;
	}

	public void setReleaseDateMax(String releaseDateMax) {
		this.releaseDateMax = releaseDateMax;
	}

	@Length(max = 1)
	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	@Length(max = 10)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(max = 1)
	public String getAmtOrder() {
		return amtOrder;
	}

	public void setAmtOrder(String amtOrder) {
		this.amtOrder = amtOrder;
	}

	@Length(max = 1)
	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	@Length(max = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
