<%@ page contentType="application/json;charset=UTF-8" isErrorPage="true"%>
<%
	response.setStatus(400);
%>
<%
	response.getWriter().append("{\"status\":\"999\",\"memo\":\"请求url不正确\"}");
%>