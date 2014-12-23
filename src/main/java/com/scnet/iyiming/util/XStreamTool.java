package com.scnet.iyiming.util;

import java.io.Serializable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class XStreamTool {

	private static XStream xstream = new XStream(new XppDriver(new NoNameCoder()));
	private static XStream xstream1 = new XStream(new DomDriver());
	static {
		xstream.autodetectAnnotations(true);
	}

	/**
	 * 创建加盖签章的xml
	 * 
	 * @return
	 * @throws Exception_Exception
	 */
	public static String createSealXml(Serializable serializable) {

		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		// 使用xstream自带的NoNameCoder构造xstream，该方式将所有特殊字符都不转义，修复xstream bug
		String xmlStr = xstream.toXML(serializable);
		sbuilder.append(xmlStr);
		return sbuilder.toString();
	}

	public static <T> T toBean(String xmlStr, Class<T> cls) {
		xstream1.processAnnotations(cls);
		@SuppressWarnings("unchecked")
		T obj = (T) xstream1.fromXML(xmlStr);
		return obj;
	}

}
