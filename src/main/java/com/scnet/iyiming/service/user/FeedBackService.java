package com.scnet.iyiming.service.user;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scnet.iyiming.entity.user.FeedBack;
import com.scnet.iyiming.entity.user.User;
import com.scnet.iyiming.repository.user.FeedBackRepository;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.request.FeedBackReq;
import com.scnet.iyiming.vo.web.QueryFeedbackVo;

@Service
@Transactional
public class FeedBackService {

	@Autowired
	private FeedBackRepository feedBackRepository;
	@Autowired
	private UserService userService;

	private static final Mapper mapper = new DozerBeanMapper();

	public ResponseBody feedBack(FeedBackReq feedBackReq, Long id) {
		FeedBack feedBack = new FeedBack();
		mapper.map(feedBackReq, feedBack);
		feedBack.setUser(userService.findOne(id));
		feedBackRepository.save(feedBack);
		return ResponseBody.createResponseBody("成功吐槽");
	}

	public Page<FeedBack> queryFeedbackList(final QueryFeedbackVo vo) {
		Page<FeedBack> page = feedBackRepository.findAll(new Specification<FeedBack>() {
			@Override
			public Predicate toPredicate(Root<FeedBack> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate predicate = cb.conjunction();
				List<Expression<Boolean>> expressions = predicate.getExpressions();

				if (!StringUtils.isEmpty(vo.getUsername())) {
					expressions.add(cb.like(root.<User> get("user").<String> get("username"), "%" + vo.getUsername() + "%"));
				}
				if (!StringUtils.isEmpty(vo.getSearchTime())) {
					expressions.add(cb.greaterThanOrEqualTo(root.<Date> get("createdDate"), vo.getSearchDate()));
				}
				if (!StringUtils.isEmpty(vo.getContent())) {
					expressions.add(cb.like(root.<String> get("content"), "%" + vo.getContent() + "%"));
				}

				return predicate;
			}
		}, vo);

		return page;

	}

}
