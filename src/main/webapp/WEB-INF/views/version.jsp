<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>版本列表</title>
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
							url : '${ctx}/version/versionList',
							method : 'POST',
							pagination : true,
							pageSize : 10,
							queryParams : scene.getParams('#paramForm'),
							fit : true,
							rownumbers : true,
							toolbar : '#queryParam',
							singleSelect : true,
							fitColumns : true,
							striped : true,
							columns : [ [
									{
										field : 'id',
										title : '记录编号',
										align : 'center'
									},
									{
										field : 'versionName',
										title : '版本名',
										align : 'center'
									},
									{
										field : 'code',
										title : '版本号',
										align : 'center'
									},
									{
										field : 'type',
										title : '类型',
										align : 'center',
										formatter : function(value, rec) {
											if (value == 'I')
												return 'IOS';
											if (value == 'A')
												return '安卓';
											return '';
										}
									},
									{
										field : 'flowId',
										title : '状态',
										align : 'center'
									},
									{
										field : 'content',
										title : '升级详情',
										align : 'center',
										formatter : function(value, rec) {
											return "<div style='white-space:pre-wrap;'>"
													+ value + "</div>";
										}
									},
									{
										field : 'releaseDate',
										title : '发布时间',
										formatter : function(value, rec) {
											return dateformat(rec.releaseDate);
										},
										align : 'center'
									},
									{
										field : 'fix',
										title : '操作',
										formatter : function(value, rec) {
											var link = "";
											if (rec.flowId == '新建') {
												link += '<a href="javascript:edit('
														+ rec.id
														+ ');">修改版本</a>|<a href="javascript:finish('
														+ rec.id
														+ ');">下架版本</a>';
											}
											if (rec.flowId == '新建') {
												link += "|";
												link += '<a href="javascript:release('
														+ rec.id
														+ ');">发布版本</a>';
											}
											return link;
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

	function edit(id) {
		window.location.href = '${ctx}/version/editVersion?id=' + id;
	}
	function release(id) {
		$.messager.confirm('确认对话框', '之前的版本会下架,您确认要发布吗？', function(r) {
			if (r) {
				$.post("${ctx}/version/releaseVersion", {
					id : id
				}, function(data, textStatus) {
					if (data.code == 0) {
						$.messager.alert("消息", "发布版本成功。", "info", function() {
							window.location = "${ctx}/version";
						});
						$(".panel-tool-close").css("display", "none");

					} else {
						$.messager.alert("警告", "发布版本失败。", "warn");
					}
				}, "json");
			}
		});
	}
	function finish(id) {
		$.messager.confirm('确认对话框', '您确认要下架吗？', function(r) {
			if (r) {
				$.post("${ctx}/version/finishVersion", {
					id : id
				}, function(data, textStatus) {
					if (data.code == 0) {
						$.messager.alert("消息", "下架版本成功。", "info", function() {
							window.location = "${ctx}/version";
						});
						$(".panel-tool-close").css("display", "none");

					} else {
						$.messager.alert("警告", "下架版本失败。", "warn");
					}
				}, "json");
			}
		});
	}
</script>
</head>
<body>
	<div id="queryParam">
		<form id="paramForm">
			<table style="margin-top: 20px;">
				<tr>
					<td class="title">发布日期：</td>
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


					<td class="title">版本名：</td>
					<td><input id="versionName" name="versionName" class="textbox"
						maxlength=20 style="height: 20px; line-height: 20px;" /></td>

					<td class="title">项目类型：</td>
					<td><select class="easyui-combobox" id="type"
						data-options="editable:false" name="type" style="width: 148px;">
							<option value="" selected="selected">全部</option>
							<option value="I">IOS</option>
							<option value="A">安卓</option>
					</select></td>

					<td class="title"><a href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" style="width: 80px"
						onclick="complexFind()">查询</a></td>
				</tr>


			</table>
		</form>
		<div>
			<a id="add" href="javascript:void(0)" class="easyui-linkbutton"
				onclick='javascript:location.href = "${ctx}/version/versionDetail"'
				data-options="iconCls:'icon-add'">新增版本</a>
		</div>
	</div>
	<table id="dataGrid"></table>
</body>
</html>
