package com.scnet.iyiming.util;

import java.util.Random;

public class ValidateCodeUtil {

	/**
	 * 生成六位随机验证码
	 * 
	 * @return
	 */
	public static String generate6RandomNum() {
		Random r = new Random();
		int tmp = r.nextInt(900000) + 100000;
		return String.valueOf(tmp);
	}

}
