package com.scnet.iyiming.service.handle.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import com.google.code.kaptcha.Producer;
import com.scnet.iyiming.constants.IYiMingConstants;
import com.scnet.iyiming.service.handle.AppHandler;
import com.scnet.iyiming.vo.RequestBody;
import com.scnet.iyiming.vo.ResponseBody;
import com.scnet.iyiming.vo.response.CaptchaResp;

/*@Service
@Transactional*/
public class CreateCaptcha implements AppHandler {

	@Autowired
	private Producer captchaProducer;

	@Override
	public ResponseBody handle(HttpServletRequest httpServletRequest, RequestBody requestBody) {
		CaptchaResp captchaResp = new CaptchaResp();
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		String text = captchaProducer.createText();
		BufferedImage bufferedImage = captchaProducer.createImage(text);
		try {
			ImageIO.write(bufferedImage, "png", os);
			os.flush();
			captchaResp.setImage(os.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		captchaResp.setStatus(IYiMingConstants.CODE_000);
		httpServletRequest.getSession().setAttribute(IYiMingConstants.SESSION_CAP_TEXT, text);
		captchaResp.setMemo(text);

		return captchaResp;

	}

}
