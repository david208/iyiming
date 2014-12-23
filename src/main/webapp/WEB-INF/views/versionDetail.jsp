<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>版本明细</title>
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
	$(function() {
		<c:if
		test="${not empty data.type}">
		$("#type option[value='${data.type}']").attr("selected", true);
		</c:if>
	});

	function save() {
		$(".easyui-validatebox").validatebox("enableValidation");
		$(".easyui-numberbox").validatebox("enableValidation");
		if ($('#form').form("validate")) {
			$('#reqForm').form('submit', {
				success : function(result1) {
					var result = eval('(' + result1 + ')');
					if (result.code == 0) {
						$.messager.alert('信息', '提交成功', "info", function() {
							window.location = "${ctx}/version";
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
	<form action="${ctx}/version/saveVersion" method="post" id="reqForm"
		style="display: inline-block" enctype="multipart/form-data">
		<input type="hidden" id="vid" name="vid"
			value="<c:out value="${data.id}"/>" />
		<h3>版本信息</h3>
		<div class="content_bd">
			<div class="content_mid">
				<div class="detail">
					<div class="member">
						<ul>
							<li><span class="lt_label">版本名：&nbsp&nbsp</span><span
								class="lt_label_left "><input id="versionName"
									name="versionName" type="text" class="easyui-validatebox"
									value="${data.versionName}"
									data-options="required:true,novalidate:'true'"
									validType='length[1,20]' /></span></li>
							<li><span class="lt_label">版本号：&nbsp&nbsp</span><span
								class="lt_label_left "><input id="code" name="code"
									type="text" class="easyui-numberbox" value="${data.code}"
									style="height: 30px;"
									data-options="required:true,novalidate:'true',max:999999,min:1"
									validType='length[1,20]' /></span></li>
							<li><span class="lt_label">类型：&nbsp&nbsp&nbsp&nbsp</span><span
								class="lt_label_left"><select name="type" id="type"
									editable=false>
										<option value="I" selected="selected">IOS</option>
										<option value="A">安卓</option>
								</select> </span></li>

							<li><span class="lt_label">升级详情：</span><span
								class="lt_label_left"><textarea
										class="easyui-validatebox" id="content" name="content" cols=60
										rows=10 data-options="required:true,novalidate:'true'"
										validType='length[1,300]'>${data.content}</textarea></span></li>

						</ul>

					</div>
				</div>
			</div>
		</div>



		<div class="mt20px">
			<a href="#" class="easyui-linkbutton" id="submit"
				data-options="iconCls:'icon-save'"
				style="width: 80px; margin-left: 310px;" onclick="save()">保存</a> <a
				id="cancel" href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'" style="width: 80px;"
				onclick="window.location.href='${ctx}/version'">返回</a>
		</div>
	</form>
</body>
</html>