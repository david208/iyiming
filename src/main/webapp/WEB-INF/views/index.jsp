<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix='sec'
	uri='http://www.springframework.org/security/tags'%>

<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>爱移民</title>
<link rel="Stylesheet" type="text/css"
	href="${ctx}/static/jquery-easyui/themes/icon.css" />
<link rel="Stylesheet" type="text/css"
	href="${ctx}/static/jquery-easyui/themes/default/easyui.css" />

<script type="text/javascript"
	src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/frames/home.js"></script>
<script type="text/javascript" src="${ctx}/static/frames/scene.js"></script>
<style type="text/css">
html,body {
	width: 100%;
	height: 100%;
}

#top_container {
	width: 100%;
	height: 100%;
	background: url(${ctx}/static/images/top_back.jpg) repeat-x left top;
}

#logo {
	width: 1024px;
	height: 100%;
	background: url(${ctx}/static/images/top_back.jpg) no-repeat left top;
}

#btn_bar {
	position: absolute;
	top: 20px;
	right: 50px;
}

#left_container {
	width: 100%;
	height: 100%;
	background: url(${ctx}/static/images/left_bg.jpg) no-repeat bottom left;
}

#tabs_content {
	width: 100%;
	height: 100%;
	border: 0px;
}

#bottom_container {
	width: 100%;
	height: 100%;
	background: url(${ctx}/static/images/foot_center.jpg) repeat-x;
	text-align: center;
	line-height: 24px;
	color: #153F93;
}

#bottom_left {
	width: 46px;
	height: 24px;
	float: left;
	background: url(${ctx}/static/images/foot_left.jpg) no-repeat;
}

#bottom_right {
	width: 49px;
	height: 24px;
	float: right;
	background: url(${ctx}/static/images/foot_right.jpg) no-repeat;
}

.accordion-body {
	background-color: transparent;
}
</style>
<script type="text/javascript">
	$(function() {
		home.initialize("#left_container", "#tabs_bar", "#tabs_content");

		var data = [ {
			"id" : 1,
			"text" : "会员管理",
			"attributes" : {
				"url" : "user"
			}
		}, {
			"id" : 2,
			"text" : "反馈列表",
			"attributes" : {
				"url" : "feedback"
			}
		}, {
			"id" : 3,
			"text" : "安全设置",
			"attributes" : {
				"url" : "securitySet"
			}
		} ];
		home.addMenu("会员管理", data);

		var data = [ {
			"id" : 1,
			"text" : "项目管理",
			"attributes" : {
				"url" : "project"
			}
		}, {
			"id" : 2,
			"text" : "版本列表",
			"attributes" : {
				"url" : "version"
			}
		} ];
		home.addMenu("项目管理", data);

		home.addTab("我的门户", "welcome", false);

		$("#logout").click(function() {
			scene.showConfirm("确定退出吗?", "退出", function(yes) {
				if (yes) {
					window.location = "${ctx}/j_spring_security_logout";
				}
			});

		});

	});
</script>
</head>

<body class="easyui-layout">
	<div data-options="region:'north', border:false, height:65">
		<div id="top_container">
			<div id="logo"></div>
			<div id="btn_bar">
				<span><sec:authentication property="principal.username" />,您好!
				</span> <a id="logout" href="javascript:void(0)" class="easyui-linkbutton">退出</a>
			</div>
		</div>
	</div>
	<div
		data-options="region:'west', border:false, split:true, width:150, maxWidth:200">
		<div id="left_container"></div>
	</div>
	<div data-options="region:'center', border:false">
		<div class="easyui-layout" data-options="fit:'true'">
			<div data-options="region:'north', border:true, height:31">
				<div id="tabs_bar"></div>
			</div>
			<div data-options="region:'center', border:false"
				style="overflow: hidden">
				<iframe id="tabs_content"
					data-options='frameborder:"0",marginwidth:"0",
 					marginheight="0"'></iframe>
			</div>
		</div>
	</div>
	<div data-options="region:'south', border:false, height:24">
		<div id="bottom_container">
			<span id="bottom_left"></span> <span id="bottom_center">安移融网络科技有限公司</span>
			<span id="bottom_right"></span>
		</div>
	</div>
</body>

</html>
