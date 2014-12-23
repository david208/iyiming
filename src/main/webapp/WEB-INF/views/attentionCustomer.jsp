<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>关注客户列表</title>
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
							url : '${ctx}/user/attentionCustomerList',
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
										title : '用户编号',
										align : 'center',
										formatter : function(value, rec) {
											return rec.user.id;
										}
									},
									{
										field : 'projectName',
										title : '项目名称',
										align : 'center',
										formatter : function(value, rec) {
											return rec.project.name;
										}
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
										field : 'nickName',
										title : '昵称',
										align : 'center',
										formatter : function(value, rec) {
											return rec.user.nickName;
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
										field : 'city',
										title : '城市',
										align : 'center',
										formatter : function(value, rec) {
											return rec.user.city;
										}
									},
									{
										field : 'address',
										title : '地址',
										align : 'center',
										formatter : function(value, rec) {
											return rec.user.address;
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
										field : 'createdDate',
										title : '注册时间',
										formatter : function(value, rec) {
											return dateformat(rec.user.createdDate);
										},
										align : 'center'
									},
									{
										field : 'fix',
										title : '操作',
										formatter : function(value, rec) {
											if (rec.user.imageUrl == null)
												return "";
											else
												return "<a href='${ctx}/user/image/"+rec.user.id+"' target='_blank'>头像</a>";
										},
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
			<input id="projectId" name="projectId" type="hidden"
				value="${data.id}" />
			<table style="margin-top: 20px;">
				<tr>
					<td class="title">注册日期：</td>
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
					<td><input id="username" name="username" class="textbox"
						maxlength=20 style="height: 20px; line-height: 20px;" /></td>


					<td class="title"><a href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" style="width: 80px"
						onclick="complexFind()">查询</a></td>
				</tr>
			</table>
		</form>
		<div>
			<a id="back" href="javascript:void(0)" class="easyui-linkbutton"
				onclick='javascript:location.href = "${ctx}/project"'
				data-options="iconCls:'icon-redo'">返回</a>
		</div>
	</div>

	<table id="dataGrid"></table>
</body>
</html>
