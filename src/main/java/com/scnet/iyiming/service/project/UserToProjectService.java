package com.scnet.iyiming.service.project;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.constants.F;
import com.scnet.iyiming.entity.project.Project;
import com.scnet.iyiming.entity.project.UserToProject;
import com.scnet.iyiming.entity.user.User;
import com.scnet.iyiming.repository.project.ProjectRepository;
import com.scnet.iyiming.repository.project.UserToProjectRepository;
import com.scnet.iyiming.repository.user.UserRepository;
import com.scnet.iyiming.vo.ErrorResponseBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.request.AttentionOrCancelReq;
import com.scnet.iyiming.vo.web.QueryUserVo;

@Service
@Transactional
public class UserToProjectService {

	@Autowired
	private UserToProjectRepository userToProjectRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProjectRepository projectRepository;

	public ResponseBody attentionOrCancelReq(AttentionOrCancelReq attentionOrCancelReq, Long userId) {
		String type = attentionOrCancelReq.getType();
		if (type.equals("A"))
			return attention(Long.parseLong(attentionOrCancelReq.getId()), userId);
		else if (type.equals("C"))
			return cancel(Long.parseLong(attentionOrCancelReq.getId()), userId);
		else
			return ErrorResponseBody.createErrorResponseBody("操作类型不存在!");

	}

	public ResponseBody attention(Long projectId, Long userId) {
		UserToProject userToProject = userToProjectRepository.findOneByProjectIdAndUserId(projectId, userId);
		if (null == userToProject) {
			userToProject = new UserToProject();
			userToProject.setUser(userRepository.findOne(userId));
			Project project = projectRepository.findOne(projectId);
			if (null == project)
				return ErrorResponseBody.createErrorResponseBody("项目不存在");
			userToProject.setProject(projectRepository.findOne(projectId));
			userToProjectRepository.save(userToProject);
			return ResponseBody.createResponseBody("关注成功");
		} else if (userToProject.getFlowId().equals(F.Attention.Cancel)) {
			userToProject.setFlowId(F.Attention.Attention);
			return ResponseBody.createResponseBody("关注成功");
		} else
			return ErrorResponseBody.createErrorResponseBody("此项目已被关注");

	}

	public ResponseBody cancel(Long projectId, Long userId) {
		UserToProject userToProject = userToProjectRepository.findOneByProjectIdAndUserId(projectId, userId);
		if (null == userToProject) {
			return ErrorResponseBody.createErrorResponseBody("此项目尚未关注");
		} else if (userToProject.getFlowId().equals(F.Attention.Attention)) {
			userToProject.setFlowId(F.Attention.Cancel);
			return ResponseBody.createResponseBody("取消关注成功");
		} else
			return ErrorResponseBody.createErrorResponseBody("此项目已取消关注");

	}

	public UserToProject findOneByProjectAndUserId(Long projectId, Long userId) {
		return userToProjectRepository.findOneByProjectIdAndUserId(projectId, userId);
	}

	public Long countByProjectAndFlowId(Project project) {
		return userToProjectRepository.countByProjectAndFlowId(project, F.Attention.Attention);
	}

	public Page<UserToProject> queryAttentionCustomerList(final QueryUserVo vo) {
		Page<UserToProject> page = userToProjectRepository.findAll(new Specification<UserToProject>() {
			@Override
			public Predicate toPredicate(Root<UserToProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();

				if (!StringUtils.isEmpty(vo.getUsername())) {
					expressions.add(cb.like(root.<User> get("user").<String> get("username"), "%" + vo.getUsername() + "%"));
				}
				if (!StringUtils.isEmpty(vo.getSearchTime())) {
					expressions.add(cb.greaterThanOrEqualTo(root.<User> get("user").<Date> get("createdDate"), vo.getSearchDate()));
				}
				if (vo.getProjectId() != null) {
					expressions.add(cb.equal(root.<Project> get("project").<Long> get("id"), vo.getProjectId()));
				}
				expressions.add(cb.equal(root.<User> get("user").<String> get("flowId"), F.User.Common));
				expressions.add(cb.equal(root.<String> get("flowId"), F.Attention.Attention));
				return predicate;
			}
		}, vo);
		return page;
	}
}
