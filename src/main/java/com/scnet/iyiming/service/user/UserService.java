package com.scnet.iyiming.service.user;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.scnet.iyiming.constants.F;
import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.entity.user.AuthorityRole;
import com.scnet.iyiming.entity.user.User;
import com.scnet.iyiming.repository.user.UserRepository;
import com.scnet.iyiming.vo.ErrorResponseBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.ResultVo;
import com.scnet.iyiming.vo.request.CompleteProfileReq;
import com.scnet.iyiming.vo.request.LoginReq;
import com.scnet.iyiming.vo.request.RegisterReq;
import com.scnet.iyiming.vo.response.GetProfileResp;
import com.scnet.iyiming.vo.response.LoginResp;
import com.scnet.iyiming.vo.web.QueryUserVo;
import com.scnet.iyiming.vo.web.UserVo;

import static org.dozer.loader.api.TypeMappingOptions.*;

/**
 * 用户Service
 * 
 * @author wujinsong
 * 
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private FileService fileService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final DozerBeanMapper mapper = new DozerBeanMapper();

	private static final DozerBeanMapper mapper2 = new DozerBeanMapper();

	static {
		BeanMappingBuilder builder = new BeanMappingBuilder() {
			@Override
			protected void configure() {
				mapping(CompleteProfileReq.class, User.class, mapNull(false));
			}
		};
		mapper2.addMapping(builder);
	}

	public User findByUsername(String name) {
		return userRepository.findOneByUsername(name);
	}

	public User findOne(Long id) {
		return userRepository.findOne(id);
	}

	/**
	 * 注册
	 * 
	 * @param user
	 * @return
	 */
	public ResponseBody register(RegisterReq registerReq) {

		if (userRepository.countByUsername(registerReq.getUsername()) > 0)
			return ErrorResponseBody.createErrorResponseBody("用户名已存在");
		if (userRepository.countByMobile(registerReq.getMobile()) > 0)
			return ErrorResponseBody.createErrorResponseBody("手机号已被绑定");
		else {
			User user = new User();
			mapper.map(registerReq, user);
			userRepository.save(user);
			return ResponseBody.createResponseBody("保存用户成功");
		}

	}

	public ResponseBody completeProfile(CompleteProfileReq completeProfileReq, Long id) {
		User user = userRepository.findOne(id);
		mapper2.map(completeProfileReq, user);
		return ResponseBody.createResponseBody("完善资料成功");

	}

	/**
	 * 用户登录
	 * 
	 * @param loginReq
	 * @return
	 */
	public ResponseBody login(HttpServletRequest httpServletRequest, LoginReq loginReq) {
		LoginResp loginResp = new LoginResp();

		/*
		 * int errorCount = 0; if (null !=
		 * httpServletRequest.getSession().getAttribute
		 * (IYiMingConstants.SESSION_ERROR_COUNT)) errorCount = (int)
		 * httpServletRequest
		 * .getSession().getAttribute(IYiMingConstants.SESSION_ERROR_COUNT); if
		 * (StringUtils.isEmpty(loginReq.getIvc())) { if (errorCount > 2) return
		 * ErrorResponseBody.createErrorResponseBody(IYiMingConstants.CODE_112,
		 * "登录失败次数连续大于3次,需输入验证码"); } else { if
		 * (!loginReq.getIvc().equalsIgnoreCase((String)
		 * httpServletRequest.getSession
		 * ().getAttribute(IYiMingConstants.SESSION_CAP_TEXT))) return
		 * ErrorResponseBody.createErrorResponseBody("验证码不正确"); }
		 */

		User user = findByUsername(loginReq.getUsername());
		logger.info(loginReq.getUsername() + ":" + loginReq.getPassword());
		if (validateUser(user)) {
			if (validatePwd(user, loginReq.getPassword())) {
				loginResp.setStatus(IYiMingConstants.CODE_000);
				loginResp.setMemo("登陆成功");
				httpServletRequest.getSession().setAttribute(IYiMingConstants.SESSION_CAP_TEXT, "");
				/*
				 * httpServletRequest.getSession().setAttribute(IYiMingConstants.
				 * SESSION_ERROR_COUNT, 0);
				 */
				httpServletRequest.getSession().setAttribute(IYiMingConstants.SESSION_USER_ID, user.getId());
			} else {
				loginResp.setStatus(IYiMingConstants.CODE_111);
				loginResp.setMemo("密码错误");
				/*
				 * httpServletRequest.getSession().setAttribute(IYiMingConstants.
				 * SESSION_ERROR_COUNT, errorCount + 1);
				 */
			}
		} else {
			loginResp.setStatus(IYiMingConstants.CODE_111);
			/*
			 * httpServletRequest.getSession().setAttribute(IYiMingConstants.
			 * SESSION_ERROR_COUNT, errorCount + 1);
			 */
			loginResp.setMemo("用户名错误");
		}
		return loginResp;

	}

	public boolean validateUser(User user) {
		return (null != user);
	}

	public boolean validatePwd(User user, String password) {
		return password.equals(user.getPassword());
	}

	/**
	 * @author SM
	 * @description 修改密码
	 */
	public ResponseBody changePassword(HttpServletRequest httpServletRequest, String oldPassword, String newPassword, String newPassword2) {

		if (StringUtils.isEmpty(newPassword))
			return ErrorResponseBody.createErrorResponseBody("新密码不能为空");

		if (!newPassword.equals(newPassword2))
			return ErrorResponseBody.createErrorResponseBody("新密码和重复密码必须一致");

		User user = userRepository.findOne((Long) httpServletRequest.getSession().getAttribute(IYiMingConstants.SESSION_USER_ID));

		if (oldPassword.equals(user.getPassword())) {
			user.setPassword(newPassword);
			return ResponseBody.createResponseBody("修改密码成功");
		} else {
			return ErrorResponseBody.createErrorResponseBody("原密码不正确!");
		}

	}

	/**
	 * @author SM
	 * @description 退出
	 */
	public ResponseBody logout(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();

		Iterator<String> iterator = CollectionUtils.toIterator(httpSession.getAttributeNames());
		while (iterator.hasNext()) {
			httpSession.removeAttribute(iterator.next());
		}
		httpSession.invalidate();
		return ResponseBody.createResponseBody("成功退出");

	}

	public void uploadImage(Long id, String imageUrl) {
		User user = userRepository.findOne(id);
		user.setImageUrl(imageUrl);
	}

	public ResponseBody getProfile(Long id, String contextPath) {
		User user = userRepository.findOne(id);
		GetProfileResp getProfileResp = new GetProfileResp();
		getProfileResp.setSex(IYiMingConstants.CODE_000);
		mapper.map(user, getProfileResp);
		getProfileResp.setImageUrl(contextPath + "/file/getAvatar");
		return getProfileResp;

	}

	public Page<User> queryUserList(final QueryUserVo vo) {
		Page<User> page = userRepository.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();

				if (!StringUtils.isEmpty(vo.getUsername())) {
					expressions.add(cb.like(root.<String> get("username"), "%" + vo.getUsername() + "%"));
				}
				if (!StringUtils.isEmpty(vo.getSearchTime())) {
					expressions.add(cb.greaterThanOrEqualTo(root.<Date> get("createdDate"), vo.getSearchDate()));
				}
				if (!StringUtils.isEmpty(vo.getUserType())) {
					expressions.add(cb.equal(root.<String> get("flowId"), vo.getUserType()));
				}

				return predicate;
			}
		}, vo);
		return page;
	}


	/**
	 * 注册
	 * 
	 * @param user
	 * @return
	 */
	public ResultVo saveUser(UserVo userVo) {

		if (userRepository.countByUsername(userVo.getUsername()) > 0)
			return new ResultVo(1, "已存在此用户");

		else {
			User user = new User();
			mapper.map(userVo, user);
			user.setPassword(bcryptEncoder.encode(userVo.getPassword()));
			user.setFlowId(F.User.Manage);
			AuthorityRole.adminRole(user);
			userRepository.save(user);
			return new ResultVo(0, "保存用户成功");
		}

	}

	public byte[] getAvatar(Long id) throws IOException {
		return fileService.getAvatar(id);

	}
}
