package com.scnet.iyiming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scnet.iyiming.entity.user.FeedBack;
import com.scnet.iyiming.service.user.FeedBackService;
import com.scnet.iyiming.vo.web.QueryFeedbackVo;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController {

	@Autowired
	private FeedBackService feedBackService;

	@RequestMapping(method = RequestMethod.GET)
	public String feedback() {
		return "/feedback";
	}

	@RequestMapping(value = "/feedbackList", method = RequestMethod.POST)
	@ResponseBody
	public Page<FeedBack> feedbackList(QueryFeedbackVo vo) {
		return feedBackService.queryFeedbackList(vo);
	}

}
