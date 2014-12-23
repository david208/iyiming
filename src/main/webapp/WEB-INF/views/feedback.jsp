<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>反馈列表</title>
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
<script type="text/javascript" src="${ctx}/static/frames/home.js"></script>
<script type="text/javascript" src="${ctx}/static/frames/scene.js"></script>
<script type="text/javascript" src="${ctx}/static/common/resource.js"></script>

<script type="text/javascript">
	$(function() {
		$('#dataGrid')
				.datagrid(
						{
							url : '${ctx}/feedback/feedbackList',
							method : 'POST',
							pagination : true,
							pageSize : 10,
							queryParams : scene.getParams('#paramForm'),
							fit : true,
							rownumbers : true,
							fitColumns : true,
							toolbar : '#queryParam',
							singleSelect : true,
							striped : true,
							columns : [ [
									{
										field : 'id',
										title : '反馈编号',
										align : 'center'
									},
									{
										field : 'username',
										title : '用户名',
										align : 'center',
										formatter : function(value, rec) {
											return rec.user.username;
										}
									},
									{
										field : 'mobile',
										title : '手机号',
										align : 'center',
										formatter : function(value, rec) {
											return rec.user.mobile;
										}
									},
									{
										field : 'realName',
										title : '真实姓名',
										align : 'center',
										formatter : function(value, rec) {
											return rec.user.realName;
										}
									},
									{
										field : 'sex',
										title : '性别',
										align : 'center',
										formatter : function(value, rec) {
											return rec.user.sex;
										}
									},
									{
										field : 'content',
										title : '反馈内容',
										align : 'center',
										formatter : function(value, rec) {
											return "<div style='white-space:pre-wrap;'>"
													+ rec.content + "</div>";
										}

									}, {
										field : 'type',
										title : '机型',
										align : 'center'

									}, {
										field : 'sysVersion',
										title : '系统版本',
										align : 'center'

									}, {
										field : 'appVersion',
										title : '软件版本',
										align : 'center'

									} ] ],
							onBeforeLoad : function(param) {
							},
							onLoadSuccess : function(data) {
							},
							onLoadError : function() {
							},
							onClickCell : function(rowIndex, field, value) {
							}
						});

	});

	/*复合查询*/
	function complexFind() {
		if ($('#paramForm').form("validate")) {
			$('#dataGrid').datagrid('options').queryParams = scene
					.getParams('#paramForm');
			$('#dataGrid').datagrid("reload");
		}
	}
</script>
</head>
<body>
	<div id="queryParam">
		<form id="paramForm">
			<table style="margin-top: 20px;">
				<tr>
					<td class="title">反馈日期：</td>
					<td><select class="easyui-combobox" id="searchTime"
						data-options="editable:false" name="searchTime"
						style="width: 148px;">
							<option value="" selected="selected">全部</option>
							<option value="week">最近一周</option>
							<option value="month">最近一个月</option>
							<option value="season">最近一个季度</option>
							<option value="half">最近半年</option>
							<option value="year">最近一年</option>
					</select></td>


					<td class="title">用户名：</td>
					<td><input id="username" name="username" maxlength=20
						style="height: 20px; line-height: 20px;" /></td>

					<td class="title">内容：</td>
					<td><input id="content" name="content" class="textbox"
						maxlength="300" style="height: 20px; line-height: 20px;" /></td>

					<td class="title"><a href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" style="width: 80px"
						onclick="complexFind()">查询</a></td>
				</tr>
			</table>
		</form>
	</div>

	<table id="dataGrid"></table>
</body>
</html>
