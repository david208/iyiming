package com.scnet.iyiming.service.user;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.service.project.ProjectService;
import com.scnet.iyiming.util.FileUtil;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.ResultVo;

@Service
@Transactional
public class FileService {

	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projectService;

	public ResponseBody uploadAvatar(MultipartHttpServletRequest request) {
		Long id = (Long) request.getSession().getAttribute(IYiMingConstants.SESSION_USER_ID);
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf;
		while (itr.hasNext()) {
			String enterpriseCertificateType = itr.next();
			mpf = request.getFile(enterpriseCertificateType);
			if (StringUtils.isEmpty(mpf.getOriginalFilename()))
				continue;
			try {
				String imageUrl = FileUtil.upload(FileUtil.builderUploadPath("avatar", id), FileUtil.builderNewFileName(mpf.getOriginalFilename()), mpf.getInputStream());
				userService.uploadImage(id, imageUrl);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return ResponseBody.createResponseBody("上传成功");

	}

	public byte[] getAvatar(HttpServletRequest httpServletRequest) throws IOException {
		Long id = (Long) httpServletRequest.getSession().getAttribute(IYiMingConstants.SESSION_USER_ID);
		return getAvatar(id);

	}

	public byte[] getProjectImage(Long id) throws IOException {
		if (StringUtils.isEmpty(projectService.findOne(id).getImageUrl()))
			return new byte[0];
		else
			return FileUtil.download(projectService.findOne(id).getImageUrl());

	}

	public ResultVo uploadProjectImage(MultipartHttpServletRequest request, Long id) {
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf;
		while (itr.hasNext()) {
			String name = itr.next();
			mpf = request.getFile(name);
			if (StringUtils.isEmpty(mpf.getOriginalFilename()))
				continue;
			try {
				String imageUrl = FileUtil.upload(FileUtil.builderUploadPath("projectImage", id), FileUtil.builderNewFileName(mpf.getOriginalFilename()), mpf.getInputStream());
				projectService.uploadImage(id, imageUrl);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return new ResultVo(0);

	}

	public byte[] getAvatar(Long id) throws IOException {
		if (StringUtils.isEmpty(userService.findOne(id).getImageUrl()))
			return new byte[0];
		else
			return FileUtil.download(userService.findOne(id).getImageUrl());

	}
}
