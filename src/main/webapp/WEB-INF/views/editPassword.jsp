<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>安全设置</title>
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
<script type="text/javascript" src="${ctx}/static/frames/scene.js"></script>
<script type="text/javascript" src="${ctx}/static/common/resource.js"></script>
<style type="text/css">
html {
	width: 100%;
	height: 100%;
	margin: 0px;
	padding: 0px;
	overflow-y: hidden;
}

body {
	width: 100%;
	height: 100%;
	margin: 0px;
	padding: 0px;
	overflow-y: hidden;
}

#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	color: #666;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin: 5px;
	padding: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
	text-align: right;
}

.input {
	width: 200px;
	height: 30px;
}

.mini-fit {
	width: 100%;
	height: 100%;
	margin: 0px;
	padding: 0px;
}
</style>

<script type="text/javascript">
	$(function() {
		$("#submit_btn").click(function() {
			var result1 = validPass();
			var result2 = validPass2();
			var result3 = validOldPass();
			if (result1 && result2 && result3) {
				$.post("${ctx}/securitySet/editPassword", {
					oldPassword : $.trim($("#oldpassword").val()),
					newPassword : $.trim($("#newpassword").val()),
					newPassword2 : $.trim($("#newpassword2").val()),
				}, function(data, textStatus) {
					if (data.code == "0") {
						reset();
						$("#edit_btn").show();
						$.messager.show({
							title : '成功修改',
							msg : '密码修改成功。',
							timeout : 5000,
							showType : 'slide'
						});
					}
					if (data.code == "1") {

						$("#oldpassword").focus();
						$("#oldpass_tip").html(data.attachment);

					}
				}, "json");
			}
		});

		$("#cancle_btn").click(function() {
			reset();
		});
	});

	function reset() {
		$("#newpass_tip").html('');
		$("#oldpass_tip").html('');
		$("#newpass2_tip").html('');
		$("#oldpassword").val("");
		$("#newpassword").val("");
		$("#newpassword2").val("");
	}

	function validPass2() {
		var value = $.trim($("#newpassword2").val());
		var newpassword = $.trim($("#newpassword").val());
		if (value.length == 0) {
			$("#newpass2_tip").html('请输入确认密码');
			return;
		}
		if (value == newpassword || newpassword2.length == 0) {
			return validPass();
		} else {
			$("#newpass2_tip").html('确认新密码必须与新密码一致');
			return false;
		}
	}
	function validPass() {
		var result = false;
		var oldvalue = $.trim($("#oldpassword").val());
		var value = $.trim($("#newpassword").val());
		var newpassword2 = $.trim($("#newpassword2").val());
		if (value.length == 0) {
			$("#newpass_tip").html('请输入新密码');
			return;
		}
		if (value == oldvalue) {
			$("#newpass_tip").html('新密码不能与当前密码相等');
		}
		if (value == newpassword2 || newpassword2.length == 0) {
			if (value.length >= 6 && value.length <= 16) {
				if (/[\u4e00-\u9fa5]/.test(value)) {
					$("#newpass_tip").html('非法字符');
					return;
				}
				var c = /[A-Za-z]+/;
				var n = /[0-9]+/;
				var _ = /[_\W]+/;
				if ((c.test(value) && n.test(value))
						|| (c.test(value) && _.test(value))) {
					$("#newpass_tip").html("");
					result = true;
				} else {
					$("#newpass_tip").html(
							'新密码必须为6-16个字符，字母加数字或符号的组合，不能单独使用字母、数字或符号');
				}
			} else {
				$("#newpass_tip").html('新密码长度为6-16个字符之间');
			}
		} else {
			//$("#newpass_tip").html('新密码必须与确认新密码一致');
		}
		return result;
	}

	function validOldPass() {
		/* var result = false; */
		var value = $.trim($("#oldpassword").val());
		if (value.length == 0) {
			$("#oldpass_tip").html('请输入原密码');
			return;
		} else {
			$("#oldpass_tip").html("");
			return true;
		}

		return result;
	}
</script>
</head>
<body>
	<div class="brick_none bg_blue">
		<div class="ml_100 pd_20">
			<form action="${ctx}/securitySet/editPassword" name="" id=""
				method="post">
				<div class="fitem">
					<label>当前密码：</label> <input type="password" id="oldpassword"
						class="easyui-validatebox input" maxlength="10" value="" name="" />
					<span class="error_tip ml_10" id="oldpass_tip"></span>
				</div>
				<div class="fitem">
					<label>新密码：</label> <input type="password"
						class="easyui-validatebox input " maxlength='10' id="newpassword"
						maxlength="14" value="" name="" /> <span class="error_tip ml_10"
						id="newpass_tip"></span>
				</div>
				<div class="ml_180" id="pwd"
					style="margin-top: -10px; display: none;">
					<span class="low  mr_10"></span> <span class="middle  mr_10"></span>
					<span class="high"></span>
				</div>
				<div class="fitem">
					<label>确认密码：</label> <input type="password"
						class="easyui-validatebox input" maxlength="10" id="newpassword2"
						maxlength="14" value="" name="" /> <span class="error_tip ml_10"
						id="newpass2_tip"></span>
				</div>
				<div class="nm_item">
					<a id="submit_btn" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-ok">保存</a> <a
						id="cancle_btn" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-cancel">重置</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>