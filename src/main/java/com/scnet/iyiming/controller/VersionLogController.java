package com.scnet.iyiming.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scnet.iyiming.entity.system.VersionLog;
import com.scnet.iyiming.service.VersionLogService;
import com.scnet.iyiming.vo.ResultVo;
import com.scnet.iyiming.vo.web.QueryVersionVo;
import com.scnet.iyiming.vo.web.VersionVo;

@Controller
@RequestMapping(value = "/version")
public class VersionLogController {

	@Autowired
	private VersionLogService versionLogService;

	@RequestMapping(method = RequestMethod.GET)
	public String version() {
		return "/version";
	}

	@RequestMapping(value = "/versionList", method = RequestMethod.POST)
	@ResponseBody
	public Page<VersionLog> versionList(QueryVersionVo vo) {
		return versionLogService.queryVersionList(vo);
	}

	@RequestMapping(value = "/versionDetail", method = RequestMethod.GET)
	public String versionDetail() {
		return "/versionDetail";
	}

	@RequestMapping(value = "/editVersion", method = RequestMethod.GET)
	public String editVersion(Long id, Model model) {
		model.addAttribute("data", versionLogService.findOne(id));
		return "/versionDetail";
	}

	@RequestMapping(value = "/saveVersion", method = RequestMethod.POST)
	public @ResponseBody
	ResultVo saveUser(@Valid VersionVo versionVo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResultVo(1, bindingResult.getFieldError());
		}
		return versionLogService.createVersion(versionVo);

	}

	@RequestMapping(value = "/finishVersion", method = RequestMethod.POST)
	public @ResponseBody
	ResultVo finishVersion(Long id) {
		return versionLogService.finishVersion(id);
	}

	@RequestMapping(value = "/releaseVersion", method = RequestMethod.POST)
	public @ResponseBody
	ResultVo releaseVersion(Long id) {
		return versionLogService.releaseVersion(id);
	}

}
