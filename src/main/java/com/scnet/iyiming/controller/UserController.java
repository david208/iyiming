package com.scnet.iyiming.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scnet.iyiming.entity.project.UserToProject;
import com.scnet.iyiming.entity.user.User;
import com.scnet.iyiming.service.project.UserToProjectService;
import com.scnet.iyiming.service.user.UserService;
import com.scnet.iyiming.vo.ResultVo;
import com.scnet.iyiming.vo.web.QueryUserVo;
import com.scnet.iyiming.vo.web.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserToProjectService userToProjectService;

	@RequestMapping(method = RequestMethod.GET)
	public String user() {
		return "/user";
	}

	@RequestMapping(value = "/userList", method = RequestMethod.POST)
	@ResponseBody
	public Page<User> userList(QueryUserVo vo) {
		return userService.queryUserList(vo);
	}

	@RequestMapping(value = "/attentionCustomerList", method = RequestMethod.POST)
	@ResponseBody
	public Page<UserToProject> attentionCustomerList(QueryUserVo vo) {
		return userToProjectService.queryAttentionCustomerList(vo);
	}

	@RequestMapping(value = "/userDetail", method = RequestMethod.GET)
	public String userDetail() {
		return "/userDetail";
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public @ResponseBody
	ResultVo saveUser(@Valid UserVo userVo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResultVo(1, bindingResult.getFieldError());
		}
		return userService.saveUser(userVo);

	}

	@RequestMapping(value = "/image/{id}", method = { RequestMethod.GET })
	public void getAvatar(@PathVariable Long id, HttpServletResponse httpServletResponse) throws IOException, InterruptedException {
		httpServletResponse.setContentType("image/png");
		OutputStream outputStream = new BufferedOutputStream(httpServletResponse.getOutputStream());
		outputStream.write(userService.getAvatar(id));
		outputStream.flush();
		outputStream.close();
	}

}
