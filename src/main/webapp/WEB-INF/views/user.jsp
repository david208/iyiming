<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>会员列表</title>
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
							url : '${ctx}/user/userList',
							method : 'POST',
							pagination : true,
							pageSize : 10,
							queryParams : scene.getParams('#paramForm'),
							fit : true,
							rownumbers : true,
							fitColumns : true,
							toolbar : '#queryParam',
							singleSelect : true,
							striped:true,
							columns : [ [
									{
										field : 'id',
										title : '用户编号',
										align : 'center'
									},
									{
										field : 'username',
										title : '用户名',
										align : 'center'
									},
									{
										field : 'mobile',
										title : '手机号',
										align : 'center'
									},
									{
										field : 'nickName',
										title : '昵称',
										align : 'center'
									},
									{
										field : 'realName',
										title : '真实姓名',
										align : 'center'
									},
									{
										field : 'city',
										title : '城市',
										align : 'center'
									},
									{
										field : 'address',
										title : '地址',
										align : 'center'
									},
									{
										field : 'sex',
										title : '性别',
										align : 'center'
									},
									{
										field : 'flowId',
										title : '类型',
										align : 'center'
									},
									{
										field : 'createdDate',
										title : '注册时间',
										formatter : function(value, rec) {
											return dateformat(rec.createdDate);
										},
										align : 'center'
									},
									{
										field : 'fix',
										title : '操作',
										formatter : function(value, rec) {
											if (rec.imageUrl == null)
												return "";
											else
												return "<a href='${ctx}/user/image/"+rec.id+"' target='_blank'>头像</a>";
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

					<td class="title">用户类型：</td>
					<td><select class="easyui-combobox" id="userType"
						data-options="editable:false" name="userType"
						style="width: 148px;">
							<option value="" selected="selected">全部</option>
							<option value="普通">普通</option>
							<option value="管理">管理</option>

					</select></td>

					<td class="title"><a href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" style="width: 80px"
						onclick="complexFind()">查询</a></td>
				</tr>
			</table>
		</form>
		<div>
			<a id="add" href="javascript:void(0)" class="easyui-linkbutton"
				onclick='javascript:location.href = "${ctx}/user/userDetail"'
				data-options="iconCls:'icon-add'">新增用户</a>
		</div>
	</div>

	<table id="dataGrid"></table>
</body>
</html>
