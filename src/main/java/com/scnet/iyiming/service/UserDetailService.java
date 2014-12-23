package com.scnet.iyiming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.entity.user.User;
import com.scnet.iyiming.repository.user.UserRepository;
import com.scnet.iyiming.vo.ResultVo;

@Service
@Transactional
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	/**
	 * @author SM
	 * @description 取得用户信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDao.findOneByUsername(username);
	}

	/**
	 * @author SM
	 * @description 需要创建一个新的凭证Authentication
	 */
	public Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
		UserDetails user = loadUserByUsername(currentAuth.getName());

		UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		newAuthentication.setDetails(currentAuth.getDetails());

		return newAuthentication;
	}

	/**
	 * @author SM
	 * @description 修改密码
	 */
	public ResultVo changePassword(String oldPassword, String newPassword, String newPassword2) {

		if (null == newPassword || newPassword.length() == 0 || newPassword.equals(""))
			return new ResultVo(1, "新密码不能为空");

		if (!newPassword.equals(newPassword2))
			return new ResultVo(1, "新密码和重复密码必须一致");

		User user = (User) loadUserByUsername(getUsername());
		if (bcryptEncoder.matches(oldPassword, user.getPassword())) {
			String encodePassword = bcryptEncoder.encode(newPassword);
			user.setPassword(encodePassword);
			SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(getAuthentication(), encodePassword));
			return new ResultVo(0, "成功");
		} else {
			return new ResultVo(1, "原密码不正确!");
		}

	}

	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();

	}

	/**
	 * @author SM
	 * @description 取得用户名
	 */
	public String getUsername() {
		if (null == getUser())
			return "anonymousUser";
		return getUser().getUsername();
	}

	/**
	 * @author SM
	 * @description 取得现有用户，非数据库
	 */
	public User getUser() {
		Authentication currentuser = getAuthentication();

		if (currentuser == null || currentuser.getPrincipal().equals("anonymousUser")) {
			return null;
		}

		return (User) currentuser.getPrincipal();

	}

}
