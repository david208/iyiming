package com.scnet.iyiming.service;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.constants.F;
import com.scnet.iyiming.entity.system.VersionLog;
import com.scnet.iyiming.repository.system.VersionLogRepository;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.ResultVo;
import com.scnet.iyiming.vo.request.GetVersionReq;
import com.scnet.iyiming.vo.response.GetVersionResp;
import com.scnet.iyiming.vo.web.QueryVersionVo;
import com.scnet.iyiming.vo.web.VersionVo;

@Service
@Transactional
public class VersionLogService {

	private static final Mapper mapper = new DozerBeanMapper();

	@Autowired
	private VersionLogRepository versionLogRepository;

	public VersionLog findOne(Long id) {
		return versionLogRepository.findOne(id);
	}

	public ResponseBody getVersion(GetVersionReq getVersionReq) {
		VersionLog versionLog = versionLogRepository.findOneByTypeAndFlowId(getVersionReq.getType(), F.VersionLog.Release);
		GetVersionResp getVersionResp = new GetVersionResp();
		getVersionResp.setVersion(versionLog.getVersionName());
		getVersionResp.setContent(versionLog.getContent());
		getVersionResp.setCode(versionLog.getCode().toString());
		return getVersionResp;

	}

	public Page<VersionLog> queryVersionList(final QueryVersionVo vo) {
		Page<VersionLog> page = versionLogRepository.findAll(new Specification<VersionLog>() {
			@Override
			public Predicate toPredicate(Root<VersionLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();

				if (!StringUtils.isEmpty(vo.getVersionName())) {
					expressions.add(cb.like(root.<String> get("versionName"), "%" + vo.getVersionName() + "%"));
				}
				if (!StringUtils.isEmpty(vo.getSearchTime())) {
					expressions.add(cb.greaterThanOrEqualTo(root.<Date> get("releaseDate"), vo.getSearchDate()));
				}
				if (!StringUtils.isEmpty(vo.getType())) {
					expressions.add(cb.equal(root.<String> get("type"), vo.getType()));
				}

				return predicate;
			}
		}, vo);
		return page;
	}

	public ResultVo releaseVersion(Long id) {
		VersionLog versionLog = versionLogRepository.findOne(id);
		if (versionLog.getFlowId().equals(F.VersionLog.Release))
			return new ResultVo(1, "已发布");
		else {
			VersionLog oldVersionLog = versionLogRepository.findOneByTypeAndFlowId(versionLog.getType(), F.VersionLog.Release);
			if (null != oldVersionLog) {
				oldVersionLog.setFlowId(F.VersionLog.Finish);
			}
			versionLog.setFlowId(F.VersionLog.Release);
			versionLog.setReleaseDate(DateTime.now().toDate());
			return new ResultVo(0, "发布成功");

		}

	}

	public ResultVo createVersion(VersionVo versionVo) {
		try {
			VersionLog versionLog = (null == versionVo.getVid()) ? new VersionLog() : versionLogRepository.findOne(versionVo.getVid());
			mapper.map(versionVo, versionLog);
			versionLogRepository.save(versionLog);
			return new ResultVo(0);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(1, "保存版本失败");
		}

	}

	public ResultVo finishVersion(Long id) {
		VersionLog versionLog = versionLogRepository.findOne(id);
		if (versionLog.getFlowId().equals(F.VersionLog.Finish))
			return new ResultVo(1, "已下架");
		else {
			versionLog.setFlowId(F.VersionLog.Finish);
			return new ResultVo(0, "下架成功");

		}

	}
}
