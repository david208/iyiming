<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>用户明细</title>
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
<script type="text/javascript" src="${ctx}/static/common/resource.js"></script>
<script type="text/javascript" src="${ctx}/static/common/selectvalue.js"></script>
<style type="text/css">
li {
	margin: 5px;
	padding: 5px;
}

input,select {
	width: 200px;
	height: 30px;
}
</style>
<script>
	function save() {
		$(".easyui-validatebox").validatebox("enableValidation");
		$(".easyui-numberbox").validatebox("enableValidation");
		if ($('#form').form("validate")) {
			$('#reqForm').form('submit', {
				success : function(result1) {
					var result = eval('(' + result1 + ')');
					if (result.code == 0) {
						$.messager.alert('信息', '提交成功', "info", function() {
							window.location = "${ctx}/user";
						});
					} else {
						$.messager.alert('警告', '提交失败', "error");
					}

				}
			});
		}
	}
</script>
</head>
<body>
	<form action="${ctx}/user/saveUser" method="post" id="reqForm"
		style="display: inline-block">
		<input type="hidden" id="pid" name="pid"
			value="<c:out value="${data.id}"/>" />
		<h3>用户信息</h3>
		<div class="content_bd">
			<div class="content_mid">
				<div class="detail">
					<div class="member">
						<ul>
							<li><span class="lt_label">用户名：</span><span
								class="lt_label_left "><input id="username"
									name="username" type="text" class="easyui-validatebox"
									value="${data.name}"
									data-options="required:true,novalidate:'true'"
									validType='length[1,20]' /></span></li>
							<li><span class="lt_label">密码：&nbsp</span><span
								class="lt_label_left"> <input type="password"
									name="password" id="password" class="easyui-validatebox input"
									maxlength="10" data-options="required:true,novalidate:'true'"
									validType='length[6,10]' />
							</span></li>

						</ul>

					</div>
				</div>
			</div>
		</div>



		<div class="mt20px">
			<a href="#" class="easyui-linkbutton" id="submit"
				data-options="iconCls:'icon-save'" style="width: 80px;"
				onclick="save()">保存</a> <a id="cancel" href="#"
				class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
				style="width: 80px;" onclick="window.location.href='${ctx}/user'">返回</a>
		</div>
	</form>
</body>
</html>