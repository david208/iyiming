function Scene() {
	this._home = null;
	this._dialogMap = {};
	this._cache = {};
	/**
	 * ajax请求
	 * 
	 * @param async
	 *            是否异步
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数表
	 * @param success
	 *            回调函数
	 * @param error
	 *            发生错误时回调函数
	 * @param method
	 *            请求方式(get/post)
	 * @param dataType
	 *            数据格式(text/json)
	 */
	this._sendAjaxRequest = function(async, url, params, success, error,
			method, dataType) {
		$.ajax({
			type : method,
			async : async,
			url : url,
			data : params,
			success : success,
			error : error,
			dataType : dataType,
			cache : false
		});
	};

	// 包装Ajax回调函数
	this._onAjaxSuccess = function(onSuccess, onFault) {
		var self = this;
		return function(data) {
			if (data.code == 0) {
				if (onSuccess != null) {
					onSuccess(data.attachment);
				}
			} else if (data.code == -1) {
				var dialog = $("<div/>").appendTo("body");
				var content = self._wrapException(data.exception,
						data.attachment);
				var width = $(document).width() * 0.33;
				var height = $(document).height() * 0.7;
				dialog.window({
					width : width,
					height : height,
					left : $(document).width() - width - 150,
					top : 30,
					title : "系统错误",
					content : content,
					collapsible : false,
					minimizable : false,
					resizable : false,
					draggable : false,
					shadow : false,
					modal : true,
					zIndex : 999999,
					onClose : function() {
						dialog.window("destroy");
					}
				});
			} else {
				if (onFault == null) {
					alert("请求失败，请检查网络状况！");
				} else {
					onFault(data.code, data.attachment);
				}
			}
		};
	}

	// 网络异常或其他事故
	this._onAjaxError = function(options, xhr, error) {
		alert("请求失败，请检查网络状况！");
	}

	// 包装后端Java异常
	this._wrapException = function(name, e) {
		var html = "<div class='e_message'>系统内部发生异常<br>" + name + ": "
				+ e.message + "</div>";
		var size = e.stackTrace.length;
		for (var i = 0; i < size; i++) {
			var stack = e.stackTrace[i];
			var classPath = "<span class='e_class'>at " + stack.className + "."
					+ stack.methodName + "</span>";
			if (stack.nativeMethod) {
				var filePath = "<span class='e_file'>(Native Method)</span>";
			} else {
				var filePath = "<span class='e_file'>(" + stack.fileName + ":"
						+ stack.lineNumber + ")</span>";
			}
			html += "<div class='e_stack'>" + classPath + filePath + "</div>";
		}
		return html;
	}

	// 包装表单回调函数
	this._onFormSuccess = function(callback) {
		return function(data) {
			try {
				data = eval('(' + data + ')');
			} catch (e) {
				alert(data);
				return;
			}
			if (callback != null) {
				callback(data);
			}
		}
	}

	// 包装Easyui组件远程加载数据方式
	this._wrapEasyuiLoader = function(type, filterFunc, onFault) {
		var self = this;
		return function(params, success, error) {
			var ui = $(this);
			var options = {};
			switch (type) {
			case "datagrid":
				options = ui.datagrid("options");
				break;
			case "combobox":
				options = ui.combobox("options");
				break;
			case "tree":
				options = ui.tree("options");
				break;
			}
			if (!options.url) {
				return false;
			}
			if (filterFunc == null) {
				self.post(true, options.url, success, params);
			} else {
				var successHandler = function(data) {
					success(filterFunc(data));
				};
				self.post(true, options.url, successHandler, params, onFault);
			}
		};
	}

	this._validId = function(id) {
		var self = this;
		if (id.indexOf("#") != 0) {
			var message = "ID not valid: " + id;
			self.log(message);
			throw new Error(message);
		}
		var len = $(id).length;
		if (len == 0) {
			var message = "ID not found: " + id;
			self.log(message);
			throw new Error(message);
		}
	}

	this.onReady = function(home) {
		var self = this;
		self._home = home;
	}

	this.isDebug = function() {
		var self = this;
		return self._home.isDebug();
	}

	/**
	 * 记录LOG
	 */
	this.log = function(message) {
		var self = this;
		self._home.log(message);
	}

	/**
	 * 判断用户权限 code 权限代码
	 */
	this.hasRight = function(code) {
		var self = this;
		return self._userRights[code] != null;
	}

	// 获取应用程序域
	this.getContext = function() {
		var self = this;
		return self._home.getContext();
	}

	this.get = function(id) {
		return $(id);
	}

	this.getCache = function(key) {
		return this._cache[key];
	}
	this.setCache = function(key, value) {
		if (value) {
			this._cache[key] = value;
		} else {
			delete this._cache[key];
		}
	}

	/**
	 * 提交post请求
	 * 
	 * @param async
	 *            是否异步
	 * @param url
	 *            请求地址
	 * @param onSuccess
	 *            成功时回调函数
	 * @param params
	 *            请求参数表
	 * @param onFault
	 *            失败时回调
	 */
	this.post = function(async, url, onSuccess, params, onFault) {
		var self = this;
		var onSuccess = self._onAjaxSuccess(onSuccess, onFault);
		self._sendAjaxRequest(async, url, params, onSuccess, self._onAjaxError,
				"post", "json");
	}

	/**
	 * 提交表单
	 * 
	 * @param id
	 *            表单ID
	 * @param url
	 *            请求地址
	 * @param onSuccess
	 *            成功时回调函数
	 * @param onSubmit
	 *            提交前调用的函数
	 * @param onFault
	 *            失败时回调函数
	 */
	this.postForm = function(id, url, onSuccess, onSubmit, onFault) {
		this._validId(id);
		var self = this;
		var onSuccess = self._onFormSuccess(self._onAjaxSuccess(onSuccess,
				onFault));
		var options = {
			url : url,
			success : onSuccess
		};
		if (onSubmit != null) {
			options["onSubmit"] = onSubmit;
		}
		$(id).form("submit", options);
	}

	/**
	 * 显示提示框
	 * 
	 * @param content
	 *            内容文本
	 * @param title
	 *            标题
	 * @param icon
	 *            图标(error/question/info/warning)
	 */
	this.showAlert = function(content, title, icon) {
		if (icon == null) {
			icon = "info";
		}
		$.messager.alert(title, content, icon);
	}

	/**
	 * 显示选择框
	 * 
	 * @param content
	 *            内容文本
	 * @param title
	 *            标题
	 * @param callback
	 *            回调方式
	 */
	this.showConfirm = function(content, title, callback) {
		$.messager.confirm(title, content, callback);
	}

	/**
	 * 显示询问框
	 * 
	 * @param content
	 *            内容文本
	 * @param title
	 *            标题
	 * @param callback
	 *            回调方式
	 */
	this.showPrompt = function(content, title, callback) {
		$.messager.prompt(title, content, callback);
	}

	/**
	 * 显示提示框
	 * 
	 * @param content
	 *            内容文本
	 * @param title
	 *            标题
	 * @param style
	 *            样式
	 */
	this.showMessage = function(content, title, style) {
		$.messager.show({
			title : title,
			msg : content,
			timeout : 10000,
			style : style
		});
	}

	this.showDialog = function(title, url, dialogId, params, width, height,
			onClose) {
		var self = this;
		var dialog = $("<div/>").appendTo("body");
		var options = {};
		options["id"] = dialogId;
		options["title"] = title;
		options["href"] = url;
		options["collapsible"] = false;
		options["minimizable"] = false;
		options["maximizable"] = false;
		options["resizable"] = false;
		options["shadow"] = false;
		options["modal"] = true;
		if (width == null || height == null || width == 0 || height == 0) {
			options["fit"] = true;
			options["draggable"] = false;
		} else {
			options["width"] = width;
			options["height"] = height;
		}
		options["onClose"] = function() {
			if (onClose) {
				onClose();
			}
			dialog.window("destroy");
			delete self._dialogMap[dialogId];
		};
		dialog.window(options);
		self._dialogMap["#" + dialogId] = dialog;
		dialog["params"] = params;
		return dialog;
	}
	this.getDialogParams = function(dialogId) {
		var self = this;
		self._validId(dialogId);
		var dialog = self._dialogMap[dialogId];
		return dialog["params"];
	}

	this.closeDialog = function(dialogId) {
		var self = this;
		self._validId(dialogId);
		$(dialogId).window("close");
	}

	/**
	 * 创建表格
	 */
	this.newDataGrid = function(id, options, searchFormId, searchButtonId) {
		var self = this;
		self._validId(id);
		options["loader"] = self._wrapEasyuiLoader("datagrid", null,
				options["onFault"]);
		$(id).datagrid(options);
		if (searchFormId && searchButtonId) {
			self._validId(searchFormId);
			self._validId(searchButtonId);
			$(searchButtonId).click(function() {
				if ($(searchFormId).form("validate")) {
					var params = self.getParams(searchFormId);
					$(id).datagrid("load", params);
				} else {
					self.showAlert("请正确填写查询参数。");
				}
			});
			var inputList = $(searchFormId).find("input");
			var onkeyupHandler = function(event) {
				if (event.keyCode == 13) {
					$(searchButtonId).click();
				}
			};
			$(searchFormId).keyup(onkeyupHandler);
		}
	}

	/**
	 * 创建下拉框(默认输入框禁用编辑)
	 */
	this.newCombobox = function(id, options, withAll, allLabel) {
		this._validId(id);
		if (options == null) {
			options = {};
		}
		if (options["editable"] == null) {
			options["editable"] = false;
		}
		if (withAll == null) {
			withAll = true;
		}
		var filterFunc = null;
		if (withAll) {
			filterFunc = function(data) {
				if (!allLabel) {
					allLabel = "全部";
				}
				var key = options["valueField"];
				var text = options["textField"];
				var allOption = {};
				allOption[key] = "";
				allOption[text] = allLabel;
				data.unshift(allOption);
				return data;
			};
		}
		options["loader"] = this._wrapEasyuiLoader("combobox", filterFunc,
				options["onFault"]);
		$(id).combobox(options);
	}

	this.newLinkCombobox = function(idList, data, options, withAll, allLabel) {
		var self = this;
		for (var i = 0; i < idList.length; i++) {
			var id = idList[i];
			self._validId(id);
		}
		if (withAll) {
			if (!allLabel) {
				allLabel = "全部";
			}
			var key = options["valueField"];
			var text = options["textField"];
			var insertAllOption = function(list) {
				var keyValue = "";
				for (var i = 0; i < list.length; i++) {
					var optionItem = list[i];
					if (i > 0) {
						keyValue = keyValue + "," + optionItem[key];
					} else {
						keyValue = optionItem[key];
					}
					if (optionItem.children) {
						insertAllOption(optionItem.children);
					}
				}
				var allOption = {};
				allOption[key] = keyValue;
				allOption[text] = allLabel;
				allOption["children"] = [];
				list.unshift(allOption);
			};
			insertAllOption(data);
		}

		options["onSelect"] = function(record) {
			var comboboxId = "#" + $(this).attr("id");
			var index = idList.indexOf(comboboxId);
			if (index < idList.length - 1) {
				var nextId = idList[index + 1];
				var nextCombobox = $(nextId);
				nextCombobox.combobox("clear");
				nextCombobox.combobox("loadData", record.children);
				if (withAll) {
					if (record.children.length > 0) {
						nextCombobox.combobox("setValue",
								record.children[0][key]);
					}
				}
				for (var i = index + 2; i < idList.length; i++) {
					var otherId = idList[index];
					$(otherId).combobox("clear");
				}
			}
		};
		for (var i = 0; i < idList.length; i++) {
			var id = idList[i];
			self.newCombobox(id, options, false);
		}
		var first = idList[0];
		$(first).combobox("loadData", data);
		if (withAll) {
			$(first).combobox("setValue", data[0][key]);
		}
	};

	/**
	 * 创建树
	 */
	this.newTree = function(id, options) {
		this._validId(id);
		options["loader"] = this._wrapEasyuiLoader("tree", null,
				options["onFault"]);
		$(id).tree(options);
	};

	this.getParams = function(searchFormId) {
		this._validId(searchFormId);
		var params = {};
		var inputList = $(searchFormId).find("input");
		for (var i = 0; i < inputList.length; i++) {
			var input = inputList[i];
			var name = input.name;
			var type = input.type;
			var checked = input.checked;
			if (type == "checkbox" && checked == false) {
				continue;
			}
			if (type == "radio" && checked == false) {
				continue;
			}
			if (name != "") {
				var value = input.value;
				params[name] = value;
			}
		}
		var selectList = $(searchFormId).find("select");
		for (var i = 0; i < selectList.length; i++) {
			var select = selectList[i];
			var name = select.name;
			if (name != "") {
				value = select.value;
				params[name] = value;
			}
		}
		return params;
	};
}

var scene = null;
$(function() {
	scene = new Scene();
	window.top.home.updateScene(scene);
});
