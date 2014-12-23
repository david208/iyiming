<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>爱移民</title>
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/styles/style.css" />
<link type="text/css" rel="stylesheet"
	href="${ctx}/static/styles/public.css" />
<link rel="Stylesheet" type="text/css"
	href="${ctx}/static/jquery-easyui/themes/icon.css" />
<link rel="Stylesheet" type="text/css"
	href="${ctx}/static/jquery-easyui/themes/default/easyui.css" />
<link rel="Stylesheet" type="text/css"
	href="${ctx}/static/styles/common.css" />
<script type="text/javascript"
	src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

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

.m_lg_r input.input1 {
	width: 14px;
	height: 14px;
	font-size: 0;
}
</style>
<script type="text/javascript">
	$(function() {
		reset();
		$("#name").focus();
		$("#submit").click(function() {
			$(".easyui-validatebox").validatebox("enableValidation");
			if ($("#loginForm").form("validate")) {
				$("#loginForm").submit();
			}
		});

		$("#picCode").keypress(function(e) {
			var key = e.which;
			if (key == 13) {
				$("#submit").click();
			}
		});

	});
	function reset() {
		if (self != top) {
			top.location.href = location.href;
		}
	}
	function changeCode() {
		$("#codeImage").attr("src", "${ctx}/get-validatepic");
	}
</script>
</head>

<body class="easyui-layout">
	<div data-options="region:'north', border:false, height:65">
		<div id="top_container">
			<div id="logo"></div>
		</div>
	</div>
	<div data-options="region:'center', border:'false',fit:'true'">

		<form id="loginForm" method="post"
			action="<c:url value='j_spring_security_check'/>"
			class="form-horizontal">
			<div class="mid">
				<div class="m_con m_lgl clearfix">
					<div class="m_lg_l fl">
						<img src="${ctx}/static/images/logo.png" />
					</div>
					<div class="m_lg_r m_lgl fr">
						<div class="m_item">
							<h3>登录</h3>
						</div>
						<div class="m_item  msg_ht">
							<span class="mv_msg"></span>
						</div>
						<div class="m_item">
							<input id="name" class="easyui-validatebox" type="text"
								maxlength=16 name="j_username"
								data-options="required:true,novalidate:'true'"
								style="width: 216px; height: 30px; padding: 5px 0 5px 42px" />
							<span class="user_icon"> <img
								src="${ctx}/static/images/profile.png" width="30" height="30" />
							</span>
						</div>
						<div class="m_item  msg_ht">
							<span class="mv_msg" id="name_tip"></span>
						</div>
						<div class="m_item">
							<input id="password" class="easyui-validatebox" type="password"
								maxlength=16 name="j_password"
								data-options="'required':'true',validType:'password',novalidate:'true'"
								style="width: 216px; height: 30px; padding: 5px 0 5px 42px" />
							<span class="user_icon"> <img
								src="${ctx}/static/images/lock.png" width="30" height="30" />
							</span>
						</div>
						<div class="m_item  msg_ht">
							<span class="mv_msg" id="name_tip"></span>
						</div>
						<div class="m_item">
							<span> <label class="w_wd07 txt_right">验证码</label> <input
								id="picCode" class="easyui-validatebox" type="text" maxlength=4
								name="picCode" data-options="required:true,novalidate:'true'"
								validType="length[4,4]"
								style="width: 90px; height: 30px; padding: 0px 0 0px 0px; vertical-align: middle;" />
								<img class="vertical-align:middle;" src="${ctx}/get-validatepic"
								id="codeImage" onClick="changeCode()" width="76" height="30"
								style="vertical-align: middle;" /> <a class="line_blue"
								href="javascript:void(0);" onClick="changeCode()">更换</a></span>
						</div>
						<div class="m_item mt5px">
							<span class="mv_msg" id="password_tip">${errorMsg}</span>
						</div>
						<div class="m_item">
							<input class="input1" id="remember_me"
								name="_spring_security_remember_me" type="checkbox" /> <label
								for="remember_me">一周内免登录</label>
						</div>
						<div class="m_item">
							<a href="javascript:void(0)" class="m_btn1 m_bg1" id="submit">登录</a>
						</div>
					</div>
				</div>

			</div>
		</form>


	</div>

	<div data-options="region:'south', border:false, height:24">
		<div id="bottom_container">
			<span id="bottom_left"></span> <span id="bottom_center">安移融网络科技有限公司</span>
			<span id="bottom_right"></span>
		</div>
	</div>
</body>

</html>
