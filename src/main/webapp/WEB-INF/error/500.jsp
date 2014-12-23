<%@ page contentType="application/json;charset=UTF-8" isErrorPage="true"%>
<%
	//设置返回码400，避免浏览器自带的错误页面
	response.setStatus(400);
%>
<%
	response.getWriter().append("{\"status\":\"999\",\"memo\":\"系统出错\"}");
%>

