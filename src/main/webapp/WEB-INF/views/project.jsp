<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>项目列表</title>
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
							url : '${ctx}/project/projectList',
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
										title : '项目编号',
										align : 'center'
									},
									{
										field : 'name',
										title : '项目名',
										align : 'center'
									},
									/* {
										field : 'intro',
										title : '项目详情',
										align : 'center',
										formatter : function(value, rec) {
											return "<div style='white-space:pre-wrap;'>"
													+ value + "</div>";
										}

									}, */
									{
										field : 'amt',
										title : '金额',
										formatter : function(value, rec) {
											var prefix = '￥';
											if (rec.amtType == "2")
												prefix = '$';
											else if (rec.amtType == "3")
												prefix = '€';
											return prefix
													+ formatMoney(value, 2);
										},
										align : 'center'
									},
									{
										field : 'type',
										title : '类型',
										align : 'center'
									},
									{
										field : 'top',
										title : '置顶',
										align : 'center',
										formatter : function(value, rec) {
											if (value == '2')
												return "√";
											else
												return "";
										}
									},
									{
										field : 'attentionCount',
										title : '关注数',
										align : 'center'
									},
									{
										field : 'flowId',
										title : '状态',
										align : 'center'
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
										field : 'finishDate',
										title : '下架时间',
										formatter : function(value, rec) {
											return dateformat(rec.finishDate);
										},
										align : 'center'
									},
									{
										field : 'fix',
										title : '操作',
										formatter : function(value, rec) {
											var link = "";
											if (rec.flowId == '新建'
													|| rec.flowId == '发布') {
												link += '<a href="javascript:edit('
														+ rec.id
														+ ');">修改项目</a>|<a href="javascript:finish('
														+ rec.id
														+ ');">下架项目</a>';
											}
											if (rec.flowId == '新建') {
												link += "|";
												link += '<a href="javascript:release('
														+ rec.id
														+ ');">发布项目</a>';
											}
											if (rec.flowId == '发布') {
												if (rec.top == '2') {
													link += "|";
													link += '<a href="javascript:cTop('
															+ rec.id
															+ ');">取消置顶</a>';
												} else {
													link += "|";
													link += '<a href="javascript:topUp('
															+ rec.id
															+ ');">置顶</a>';
												}
											}
											if (rec.attentionCount > 0) {
												if (link != "")
													link += "|";

												link += '<a href="javascript:acl('
														+ rec.id
														+ ');">关注客户列表</a>';
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
		window.location.href = '${ctx}/project/editProject?id=' + id;
	}

	function acl(projectId) {
		window.location.href = '${ctx}/project/attentionCustomer?projectId='
				+ projectId;
	}
	function release(id) {
		$.messager.confirm('确认对话框', '您确认要发布吗？', function(r) {
			if (r) {
				$.post("${ctx}/project/releaseProject", {
					id : id
				}, function(data, textStatus) {
					if (data.code == 0) {
						$.messager.alert("消息", "发布项目成功。", "info", function() {
							window.location = "${ctx}/project";
						});
						$(".panel-tool-close").css("display", "none");

					} else {
						$.messager.alert("警告", "发布项目失败。", "warn");
					}
				}, "json");
			}
		});
	}
	function finish(id) {
		$.messager.confirm('确认对话框', '您确认要下架吗？', function(r) {
			if (r) {
				$.post("${ctx}/project/finishProject", {
					id : id
				}, function(data, textStatus) {
					if (data.code == 0) {
						$.messager.alert("消息", "下架项目成功。", "info", function() {
							window.location = "${ctx}/project";
						});
						$(".panel-tool-close").css("display", "none");

					} else {
						$.messager.alert("警告", "下架项目失败。", "warn");
					}
				}, "json");
			}
		});
	}

	function topUp(id) {
		$.messager.confirm('确认对话框', '您确认要置顶吗？', function(r) {
			if (r) {
				$.post("${ctx}/project/topProject", {
					id : id
				}, function(data, textStatus) {
					if (data.code == 0) {
						$.messager.alert("消息", "置顶项目成功。", "info", function() {
							window.location = "${ctx}/project";
						});
						$(".panel-tool-close").css("display", "none");

					} else {
						$.messager.alert("警告", "置顶项目失败。", "warn");
					}
				}, "json");
			}
		});
	}
	function cTop(id) {
		$.messager.confirm('确认对话框', '您确认要取消置顶吗？', function(r) {
			if (r) {
				$.post("${ctx}/project/cancelTopProject", {
					id : id
				}, function(data, textStatus) {
					if (data.code == 0) {
						$.messager.alert("消息", "取消置顶项目成功。", "info", function() {
							window.location = "${ctx}/project";
						});
						$(".panel-tool-close").css("display", "none");

					} else {
						$.messager.alert("警告", "取消置顶项目失败。", "warn");
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


					<td class="title">项目名：</td>
					<td><input id="projectname" name="projectname" class="textbox"
						maxlength=20 style="height: 20px; line-height: 20px;" /></td>

					<td class="title">项目类型：</td>
					<td><select class="easyui-combobox" id="projectType"
						data-options="editable:false" name="projectType"
						style="width: 148px;">
							<option value="" selected="selected">全部</option>
							<option value="移民">移民</option>
							<option value="留学">留学</option>
							<option value="签证">签证</option>
							<option value="生子">生子</option>
							<option value="房产">房产</option>
							<option value="税务">税务</option>
							<option value="商业">商业</option>
							<option value="银行">银行</option>
							<option value="投资">投资</option>
							<option value="讲座">讲座</option>
							<option value="游记">游记</option>
					</select></td>

					<td class="title"><a href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" style="width: 80px"
						onclick="complexFind()">查询</a></td>
				</tr>


			</table>
		</form>
		<div>
			<a id="add" href="javascript:void(0)" class="easyui-linkbutton"
				onclick='javascript:location.href = "${ctx}/project/projectDetail"'
				data-options="iconCls:'icon-add'">新增项目</a>
		</div>
	</div>
	<table id="dataGrid"></table>
</body>
</html>
