package com.scnet.iyiming.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.scnet.iyiming.entity.project.Project;
import com.scnet.iyiming.service.project.ProjectService;
import com.scnet.iyiming.vo.ResultVo;
import com.scnet.iyiming.vo.web.ProjectVo;
import com.scnet.iyiming.vo.web.QueryProjectVo;

@Controller
@RequestMapping(value = "/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@RequestMapping(method = RequestMethod.GET)
	public String project() {
		return "/project";
	}

	@RequestMapping(value = "/projectList", method = RequestMethod.POST)
	@ResponseBody
	public Page<Project> projectList(QueryProjectVo vo) {
		return projectService.queryProjectList(vo);
	}
	
	@RequestMapping(value = "/attentionCustomer", method = RequestMethod.GET)
	public String attentionCustomerList(Long projectId,Model model) {
		model.addAttribute("data", projectService.findOne(projectId));
		return "/attentionCustomer";
	}
	
	
	

	@RequestMapping(value = "/projectDetail", method = RequestMethod.GET)
	public String projectDetail() {
		return "/projectDetail";
	}

	@RequestMapping(value = "/editProject", method = RequestMethod.GET)
	public String editProject(Long id, Model model) {
		model.addAttribute("data", projectService.findOne(id));
		return "/projectDetail";
	}

	@RequestMapping(value = "/saveProject", method = RequestMethod.POST)
	public @ResponseBody
	ResultVo saveProject(MultipartHttpServletRequest request, @Valid ProjectVo projectVo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResultVo(1, bindingResult.getFieldError());
		}
		return projectService.uploadProjectImage(request, projectVo);

	}

	@RequestMapping(value = "/image/{id}", method = { RequestMethod.GET })
	public void getProjectImage(@PathVariable Long id, HttpServletResponse httpServletResponse) throws IOException, InterruptedException {
		httpServletResponse.setContentType("image/png");
		OutputStream outputStream = new BufferedOutputStream(httpServletResponse.getOutputStream());
		outputStream.write(projectService.getProjectImage(id));
		outputStream.flush();
		outputStream.close();
	}

	@RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
	public @ResponseBody
	ResultVo deleteImage(Long id) {
		return projectService.deleteImage(id);

	}
	
	@RequestMapping(value = "/topProject", method = RequestMethod.POST)
	public @ResponseBody
	ResultVo topProject(Long id) {
		return projectService.topProject(id);

	}
	
	@RequestMapping(value = "/cancelTopProject", method = RequestMethod.POST)
	public @ResponseBody
	ResultVo cancelTopProject(Long id) {
		return projectService.cancelTopProject(id);

	}
	
	@RequestMapping(value = "/releaseProject", method = RequestMethod.POST)
	public @ResponseBody
	ResultVo releaseProject(Long id) {
		return projectService.releaseProject(id);

	}

	@RequestMapping(value = "/finishProject", method = RequestMethod.POST)
	public @ResponseBody
	ResultVo finishProject(Long id) {
		return projectService.finishProject(id);

	}

}
