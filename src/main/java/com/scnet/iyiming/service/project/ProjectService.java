package com.scnet.iyiming.service.project;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.scnet.iyiming.constants.F;
import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.entity.project.Project;
import com.scnet.iyiming.entity.project.UserToProject;
import com.scnet.iyiming.entity.user.User;
import com.scnet.iyiming.repository.project.ProjectRepository;
import com.scnet.iyiming.service.user.FileService;
import com.scnet.iyiming.vo.ErrorResponseBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.ResultVo;
import com.scnet.iyiming.vo.request.GetProjectDetailReq;
import com.scnet.iyiming.vo.request.GetProjectListReq;
import com.scnet.iyiming.vo.response.GetProjectDetailResp;
import com.scnet.iyiming.vo.response.GetProjectListResp;
import com.scnet.iyiming.vo.web.ProjectVo;
import com.scnet.iyiming.vo.web.QueryProjectVo;

@Service
@Transactional
public class ProjectService {

	private static final Mapper mapper = new DozerBeanMapper();

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private FileService fileService;

	private static List<String> projectFlowIds = Arrays.asList(F.Project.Release, F.Project.Finish);

	@Autowired
	private UserToProjectService userToProjectService;

	public Project findOne(Long id) {
		return projectRepository.findOne(id);
	}

	public Page<Project> queryProjectList(final QueryProjectVo vo) {
		Page<Project> page = projectRepository.findAll(new Specification<Project>() {
			@Override
			public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();

				if (!StringUtils.isEmpty(vo.getProjectname())) {
					expressions.add(cb.like(root.<String> get("name"), "%" + vo.getProjectname() + "%"));
				}
				if (!StringUtils.isEmpty(vo.getSearchTime())) {
					expressions.add(cb.greaterThanOrEqualTo(root.<Date> get("releaseDate"), vo.getSearchDate()));
				}
				if (!StringUtils.isEmpty(vo.getProjectType())) {
					expressions.add(cb.equal(root.<String> get("type"), vo.getProjectType()));
				}

				return predicate;
			}
		}, vo);
		for (Project project : page.getContent()) {
			project.setAttentionCount(userToProjectService.countByProjectAndFlowId(project));
		}
		return page;
	}

	public ResultVo uploadProjectImage(MultipartHttpServletRequest request, ProjectVo projectVo) {
		try {
			Project project = (null == projectVo.getPid()) ? new Project() : projectRepository.findOne(projectVo.getPid());
			mapper.map(projectVo, project);
			projectRepository.save(project);
			return fileService.uploadProjectImage(request, project.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(1, "新建项目失败");
		}

	}

	public void uploadImage(Long id, String imageUrl) {
		Project project = projectRepository.findOne(id);
		project.setImageUrl(imageUrl);
	}

	public byte[] getProjectImage(Long id) throws IOException {
		return fileService.getProjectImage(id);

	}

	public ResponseBody getProjectList(final GetProjectListReq getProjectListReq, String contextPath, final Long id) {
		Sort sort;
		if (StringUtils.isEmpty(getProjectListReq.getAmtOrder()))
			sort = new Sort(Direction.fromString("desc"), "id");
		else if (getProjectListReq.getAmtOrder().equals("A"))
			sort = new Sort(Direction.fromString("asc"), "amt");
		else
			sort = new Sort(Direction.fromString("desc"), "amt");

		Page<Project> page = projectRepository.findAll(new Specification<Project>() {
			@Override
			public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();

				if (!StringUtils.isEmpty(getProjectListReq.getCountry())) {
					expressions.add(cb.equal(root.<String> get("country"), getProjectListReq.getCountry()));
				}
				if (!StringUtils.isEmpty(getProjectListReq.getAmtMin())) {
					expressions.add(cb.greaterThanOrEqualTo(root.<BigDecimal> get("amt"), new BigDecimal(getProjectListReq.getAmtMin())));
				}
				if (!StringUtils.isEmpty(getProjectListReq.getAmtMax())) {
					expressions.add(cb.lessThanOrEqualTo(root.<BigDecimal> get("amt"), new BigDecimal(getProjectListReq.getAmtMax())));
				}
				if (!StringUtils.isEmpty(getProjectListReq.getReleaseDateMin())) {
					expressions.add(cb.greaterThanOrEqualTo(root.<Date> get("releaseDate"), DateTime.parse(getProjectListReq.getReleaseDateMin()).toDate()));
				}
				if (!StringUtils.isEmpty(getProjectListReq.getReleaseDateMax())) {
					expressions.add(cb.lessThanOrEqualTo(root.<Date> get("releaseDate"), DateTime.parse(getProjectListReq.getReleaseDateMax()).toDate()));
				}
				if (!StringUtils.isEmpty(getProjectListReq.getType())) {
					expressions.add(cb.equal(root.<String> get("type"), getProjectListReq.getType()));
				}
				if (!StringUtils.isEmpty(getProjectListReq.getName())) {
					expressions.add(cb.like(root.<String> get("name"), "%" + getProjectListReq.getName() + "%"));
				}
				if (!StringUtils.isEmpty(getProjectListReq.getDateType())) {
					Date releaseDate;
					switch (getProjectListReq.getDateType()) {
					case "1":
						releaseDate = DateTime.now().plusDays(-1).toDate();
						break;
					case "2":
						releaseDate = DateTime.now().plusDays(-3).toDate();
						break;
					case "3":
						releaseDate = DateTime.now().plusDays(-7).toDate();
						break;
					default:
						releaseDate = DateTime.now().plusYears(-10).toDate();
						break;
					}
					expressions.add(cb.greaterThanOrEqualTo(root.<Date> get("releaseDate"), releaseDate));
				}

				if (!StringUtils.isEmpty(getProjectListReq.getAttention()) && getProjectListReq.getAttention().equals("Y")) {
					Subquery<UserToProject> subquery = query.subquery(UserToProject.class);
					Root<UserToProject> userToProjectRoot = subquery.from(UserToProject.class);
					subquery.select(userToProjectRoot);
					List<Predicate> subQueryPredicates = new ArrayList<Predicate>();
					subQueryPredicates.add(cb.equal(userToProjectRoot.<Project> get("project").<Long> get("id"), root.<Long> get("id")));
					subQueryPredicates.add(cb.equal(userToProjectRoot.<User> get("user").<Long> get("id"), id));
					subQueryPredicates.add(cb.equal(userToProjectRoot.<String> get("flowId"), F.Attention.Attention));
					subquery.where(subQueryPredicates.toArray(new Predicate[] {}));

					query.where(cb.and(predicate, root.<String> get("flowId").in(projectFlowIds), cb.exists(subquery)));
				} else
					query.where(cb.and(predicate, cb.equal(root.<String> get("flowId"), F.Project.Release)));

				return query.getRestriction();
			}
		}, new PageRequest(Integer.valueOf(getProjectListReq.getPageNo()) - 1, Integer.valueOf(getProjectListReq.getPageSize()), sort));
		List<GetProjectListResp.ProjectDetail> projectList = new ArrayList<GetProjectListResp.ProjectDetail>();
		for (Project project : page.getContent()) {
			GetProjectListResp.ProjectDetail projectDetail = new GetProjectListResp.ProjectDetail();
			mapper.map(project, projectDetail);
			projectDetail.setAttentionCount(userToProjectService.countByProjectAndFlowId(project));
			projectDetail.setImageUrl(contextPath + "/file/getProjectImage/" + project.getId() + "/" + project.getVersion());
			projectList.add(projectDetail);
		}
		GetProjectListResp getProjectListResp = new GetProjectListResp();
		getProjectListResp.setStatus(IYiMingConstants.CODE_000);
		getProjectListResp.setProjectDetails(projectList);
		return getProjectListResp;

	}

	public ResponseBody getProjectDetail(GetProjectDetailReq getProjectDetailReq, String contextPath, Long userId) {
		Project project = projectRepository.findOneByIdAndFlowIdIn(Long.parseLong(getProjectDetailReq.getId()), projectFlowIds);
		try {
			Assert.notNull(project);
		} catch (IllegalArgumentException e) {
			return ErrorResponseBody.createErrorResponseBody("项目不存在");
		}

		String attentionFlag = "N";
		if (null != userId) {
			UserToProject userToProject = userToProjectService.findOneByProjectAndUserId(project.getId(), userId);
			if (null == userToProject || userToProject.getFlowId().equals(F.Attention.Cancel))
				attentionFlag = "N";
			else
				attentionFlag = "Y";
		}
		GetProjectDetailResp getProjectDetailResp = new GetProjectDetailResp();
		GetProjectListResp.ProjectDetail projectDetail = new GetProjectListResp.ProjectDetail();
		mapper.map(project, projectDetail);
		projectDetail.setAttentionCount(userToProjectService.countByProjectAndFlowId(project));
		projectDetail.setAttentionFlag(attentionFlag);
		projectDetail.setImageUrl(contextPath + "/file/getProjectImage/" + project.getId() + "/" + project.getVersion());
		getProjectDetailResp.setProjectDetail(projectDetail);
		return getProjectDetailResp;

	}

	public ResultVo releaseProject(Long id) {
		Project project = projectRepository.findOne(id);
		if (project.getFlowId().equals(F.Project.Release))
			return new ResultVo(1, "已发布");
		else {
			project.setFlowId(F.Project.Release);
			project.setReleaseDate(DateTime.now().toDate());
			return new ResultVo(0, "发布成功");

		}

	}

	public ResultVo finishProject(Long id) {
		Project project = projectRepository.findOne(id);
		if (project.getFlowId().equals(F.Project.Finish))
			return new ResultVo(1, "已下架");
		else {
			project.setFlowId(F.Project.Finish);
			project.setFinishDate(DateTime.now().toDate());
			return new ResultVo(0, "下架成功");

		}

	}
}
