package com.scnet.iyiming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scnet.iyiming.entity.user.User;
import com.scnet.iyiming.service.UserDetailService;
import com.scnet.iyiming.service.user.UserService;
import com.scnet.iyiming.vo.ResultVo;

/**
 * 
 * @author SM
 * @description 安全设置
 */

@RequestMapping(value = "/securitySet")
@Controller
public class SecuritySetController {

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	private UserService userService;

	@RequestMapping
	public String securitySet(Model model) {
		User user = userService.findByUsername(userDetailService.getUsername());
		model.addAttribute("username", user.getUsername());
		return "/editPassword";
	}

	@RequestMapping(value = "/editPassword", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo editPassword(Model model, String oldPassword, String newPassword, String newPassword2) {
		return userDetailService.changePassword(oldPassword, newPassword, newPassword2);

	}

}
