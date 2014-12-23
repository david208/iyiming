package com.scnet.iyiming.entity.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.scnet.iyiming.entity.system.VersionedAuditableIdEntity;

@Entity
@Table(name = "IY_AUTHORITY_ROLE")
public class AuthorityRole extends VersionedAuditableIdEntity implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String authority;// 授权

	private User user;// 用户

	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static AuthorityRole adminRole(User user) {
		AuthorityRole role = new AuthorityRole();
		role.setAuthority("admin");
		role.setUser(user);
		user.getAuthorities().add(role);
		return role;
	}

}
