<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>403 - 用户权限不足</title>
</head>

<body>
	<h2>403 - 对不起，您的权限不足！</h2>
	<div>
		<p>
			<a href="<c:url value="/"/>">点我返回首页吧</a>
		</p>
	</div>
</body>
</html>
