package com.scnet.iyiming.entity.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.scnet.iyiming.entity.system.VersionedAuditableIdEntity;

@Entity
@Table(name = "IY_IMAGE")
public class Image extends VersionedAuditableIdEntity {

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@ManyToOne
	@JoinColumn(name = "PROJECT_ID")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Transient
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String imageUrl;// 图片url
	private Project project;
	
	private String url;//实际url
	

}
