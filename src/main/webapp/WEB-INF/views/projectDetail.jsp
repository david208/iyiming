<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>项目明细</title>
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
		<c:if
		test="${not empty data.country}">
		$("#country option[value='${data.country}']").attr("selected", true);
		</c:if>
		<c:if
		test="${not empty data.amtType}">
		$("#amtType option[value='${data.amtType}']").attr("selected", true);
		</c:if>

		setPrefix();

		$("#amtType").change(function() {
			setPrefix();
		});
	});

	function setPrefix() {
		if ($("#amtType").val() == "1")
			$("#amt").numberbox({
				prefix : "￥"
			});
		else if ($("#amtType").val() == "2")
			$("#amt").numberbox({
				prefix : "$"
			});
		else if ($("#amtType").val() == "3")
			$("#amt").numberbox({
				prefix : "€"
			});
	}
	
	var count = 1;
	$(function() {
		$("#add")
				.click(
						function() {
							var c = count;
							count = count + 1;
							$("#imageLi" + c + "")
									.after(
											'<li id="imageLi'+count+'"><input class="fl txtnone ml20px" accept="image/*" type="file" value="" name=image'+count+'"  style="width: 200px; height: 30px;" /></li>');
						});
	});
	
	function deleteImage(id){
		$.messager.confirm('确认对话框', '您确认删除此图吗？', function(r){
			if (r){
			  $.post("${ctx}/project/deleteImage",{id:id},function(data,textStatus){
				  if (data.code == 0) {
						$.messager.alert('信息', '提交成功', "info", function() {
							$("#d"+id).parent().hide();
						});
					} else {
						$.messager.alert('警告', '提交失败', "error");
					}
			  },"json")
			}
		});


	}

	function save() {
		$(".easyui-validatebox").validatebox("enableValidation");
		$(".easyui-numberbox").validatebox("enableValidation");
		if ($('#form').form("validate")) {
			$('#reqForm').form('submit', {
				success : function(result1) {
					var result = eval('(' + result1 + ')');
					if (result.code == 0) {
						$.messager.alert('信息', '提交成功', "info", function() {
							window.location = "${ctx}/project";
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
	<form action="${ctx}/project/saveProject" method="post" id="reqForm"
		style="display: inline-block" enctype="multipart/form-data">
		<input type="hidden" id="pid" name="pid"
			value="<c:out value="${data.id}"/>" />
		<h3>移民项目信息</h3>
		<div class="content_bd">
			<div class="content_mid">
				<div class="detail">
					<div class="member">
						<ul>
							<li><span class="lt_label">项目名称：</span><span
								class="lt_label_left "><input id="name" name="name"
									type="text" class="easyui-validatebox" value="${data.name}"
									data-options="required:true,novalidate:'true'"
									validType='length[1,20]' /></span></li>

							<li><span class="lt_label">币种：&nbsp&nbsp&nbsp&nbsp</span><span
								class="lt_label_left"><select name="amtType" id="amtType"
									editable=false>
										<option value="1" selected="selected">人民币</option>
										<option value="2">美元</option>
										<option value="3">欧元</option>
								</select> </span></li>
							<li><span class="lt_label">金额：&nbsp&nbsp&nbsp&nbsp</span><span
								class="lt_label_left"><input id="amt" name="amt"
									type="text" class="easyui-numberbox" value="${data.amt}"
									style="width: 200px; height: 30px;" prefix='￥'
									groupSeparator=','
									data-options="required:true,novalidate:'true',min:0,precision:2,max:999999999"
									validType='length[1,50]' /></span></li>
							<li><span class="lt_label">类型：&nbsp&nbsp&nbsp&nbsp</span><span
								class="lt_label_left"><select name="type" id="type"
									editable=false>
										<option value="移民" selected="selected">移民</option>
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
								</select> </span></li>
							<li><span class="lt_label">国家：&nbsp&nbsp&nbsp</span><span
								class="lt_label_left"> <select name="country"
									id="country" editable=false>
										<option value="美国" selected>美国</option>
										<option value="加拿大">加拿大</option>
										<option value="大洋洲地区">大洋洲地区</option>
										<option value="欧洲">欧洲</option>
										<option value="其他">其他</option>
								</select>
							</span></li>
							<%-- 	<li><span class="lt_lab_left fl clearfix">项目图片：</span> <c:if
									test="${not empty data.imageUrl}">
									<a href="${ctx}/project/image/${data.id}" class="fl"
										title="${data.name}" target="_blank"><img
										src="${ctx}/project/image/${data.id}" alt="项目图" height=100
										width=100 /></a>
								</c:if> <input class="fl txtnone ml20px" accept="image/*" type="file"
								value="" name="image" id="image"
								style="width: 200px; height: 30px;" /></li> --%>

							<c:forEach items="${data.images}" var="item" varStatus="i">
								<li><span class="lt_lab_left fl clearfix">图片：</span> <c:if
										test="${not empty item.imageUrl}">
										<a href="${ctx}/project/image/${item.id}" class="fl"
											title="${data.name}" target="_blank"><img
											src="${ctx}/project/image/${item.id}" alt="项目图" height=100
											width=100 /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<a onclick="deleteImage(${item.id})" href="#" id="d${item.id}"
											class="easyui-linkbutton"
											data-options="iconCls:'icon-remove'">删除</a>
									</c:if></li>

							</c:forEach>
							<li id="imageLi1"><input class="fl txtnone ml20px"
								accept="image/*" type="file" value="" name="image"
								style="width: 200px; height: 30px;" /><a id="add"
								href="javascript('void(0)')" class="easyui-linkbutton"
								data-options="iconCls:'icon-add'">增加图片</a></li>
							<li><span class="lt_label">项目详情：</span><span
								class="lt_label_left"><textarea
										class="easyui-validatebox" id="intro" name="intro" cols=60
										rows=10 data-options="required:true,novalidate:'true'"
										validType='length[1,600]'>${data.intro}</textarea> </span></li>
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
				onclick="window.location.href='${ctx}/project'">返回</a>
		</div>
	</form>
</body>
</html>