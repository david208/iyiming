/**
 * 
 */
var stringUtil = {};

/**
 * 得到字符串的真实长度（双字节换算为两个单字节）
 */
stringUtil.getStrActualLen = function(sChars) {
	return sChars.replace(/[^\x00-\xff]/g, "xx").length;
};

/**
 * 截取固定长度子字符串 sSource为字符串iLen为长度
 */
stringUtil.getInterceptedStr = function(sSource, iLen) {
	if (sSource.replace(/[^\x00-\xff]/g, "xx").length <= iLen) {
		return sSource;
	}

	var str = "";
	var l = 0;
	var schar;
	for (var i = 0; schar = sSource.charAt(i); i++) {
		str += schar;
		l += (schar.match(/[^\x00-\xff]/) != null ? 2 : 1);
		if (l >= iLen) {
			break;
		}
	}

	return str + "...";
};
