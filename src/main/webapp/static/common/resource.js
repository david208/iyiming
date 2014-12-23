Number.prototype.toMoney = function(n) {
	var left = Math.floor(this).toString();
	var result = left.slice(-3) + "." + this.to_floor(n).toFixed(n).substr(-n);
	var index = -3;
	while (-index < left.length) {
		var node = left.slice(index - 3, index);
		result = node + "," + result;
		index -= 3;
	}
	while (-index < left.length)
		;
	return result;
};

function fixWidth(percent) {
	return (screen.width) * percent;
}

// 只舍不入

Number.prototype.to_floor = function(v) {
	var vv = Math.pow(10, v);
	return Math.floor(this * vv) / vv;
};

// 四舍五入
Number.prototype.to_round = function(v) {
	var vv = Math.pow(10, v);
	return Math.round(num * vv) / vv;
};

$
		.extend(
				$.fn.validatebox.defaults.rules,
				{
					pn_min : {
						validator : function(value, param) {
							var maxId = param[0];
							var maxValue = $(maxId).val();
							if (value == "" || maxValue == ""
									|| value <= maxValue) {
								return true;
							} else {
								return false;
							}
						},
						message : '最小值不可超过最大值。'
					},
					pn_max : {
						validator : function(value, param) {
							var minId = param[0];
							var minValue = $(minId).val();
							if (minValue == "" || value == ""
									|| minValue <= value) {
								return true;
							} else {
								return false;
							}
						},
						message : "最小值不可超过最大值。"
					},
					pd_min : {
						validator : function(value, param) {
							var min = parseDate(value);
							var maxId = param[0];
							var max = parseDate($(maxId).datebox("getValue"));
							if (min == null || max == null
									|| min.getTime() <= max.getTime()) {
								return true;
							} else {
								return false;
							}
						},
						message : '最小时期不可超过最大日期。'
					},
					pd_max : {
						validator : function(value, param) {
							var minId = param[0];
							var min = parseDate($(minId).datebox("getValue"));
							var max = parseDate(value);
							if (min == null || max == null
									|| min.getTime() <= max.getTime()) {
								return true;
							} else {
								return false;
							}
						},
						message : '最小时期不可超过最大日期。'
					},
					date_earlier_systemdate : {
						validator : function(value) {
							var myDate = new Date(new Date(value).valueOf() + 1
									* 24 * 60 * 60 * 1000);
							return myDate >= new Date();
						},
						message : '时期不可早于系统时间。'
					},
					max_value : {
						validator : function(value, param) {
							return value <= param[0];
						},
						message : '最大不能超过{0}。'
					},
					min_value : {
						validator : function(value, param) {
							return value >= param[0];
						},
						message : '不能小于{0}。'
					},
					// 正整数
					positive_integer : {
						validator : function(value) {
							var reg = /^[0-9]*[1-9][0-9]*$/;
							return reg.test(value);
						},
						message : '必须为正整数。'
					},
					// 非负整数
					nonnegative_integer : {
						validator : function(value) {
							var reg = /^\d+$/;
							return reg.test(value);
						},
						message : '必须为非负整数。'
					},
					// 非负浮点数（正浮点数 + 0）
					nonnegative_floating : {
						validator : function(value) {
							var reg = /^\d+(\.\d+)?$/;
							return reg.test(value);
						},
						message : '必须为非负浮点数。'
					},
					// 正浮点数
					positive_floating : {
						validator : function(value) {
							var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
							return reg.test(value);
						},
						message : '必须为正浮点数。'
					},
					two_decimal : {
						validator : function(value) {
							var reg = /^\d+[.]?\d{1,2}$/;
							return reg.test(value);
						},
						message : '小数点后只能填写两位。'
					}
				});

$.extend($.fn.validatebox.defaults.rules,
		{
			selectValueRequired : {
				validator : function(value, param) {
					return $(param[0]).find("option:contains('" + value + "')")
							.val() != '';
				},
				message : '请选择非空一项.'
			}
		});

function formatMoney(value, n) {
	if (value) {
		var num = Number(value);
		if (!isNaN(num)) {
			return num.toMoney(n);
		}
	}
	return "0";
}

function parseDate(date) {
	if (date == "") {
		return null;
	}
	var args = date.split("-");
	var date = new Date();
	date.setYear(args[0]);
	date.setMonth(args[1] - 1);
	date.setDate(args[2]);
	date.setHours(0);
	date.setMinutes(0);
	date.setSeconds(0);
	return date;
}

function loadDatagridLoanList(id, toolbar) {
	var options = {

	};
	if (toolbar != null) {
		options["toolbar"] = toolbar;
	}
	scene.newDataGrid(id, options);
}

function dateformat(value) {
	if (value == null)
		return "";
	else
		var createdDate = new Date(value);
	return createdDate.pattern("yyyy-MM-dd HH:mm:ss");

}

Date.prototype.pattern = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	var week = {
		"0" : "/u65e5",
		"1" : "/u4e00",
		"2" : "/u4e8c",
		"3" : "/u4e09",
		"4" : "/u56db",
		"5" : "/u4e94",
		"6" : "/u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt
				.replace(
						RegExp.$1,
						((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f"
								: "/u5468")
								: "")
								+ week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};

function acctStatus(acctStatus) {
	if (acctStatus == "1")
		return "正常";
	if (acctStatus == "2")
		return "失效";
	if (acctStatus == "3")
		return "违约";
	return "";
};

// 转换时间为正确格式
function transTime(value) {
	if (value == null)
		return "";
	var mystime = new Date(value);
	var addstime = mystime.getFullYear()
			+ '-'
			+ fullnum(Number(mystime.getMonth()) + 1)
			+ '-'
			+ fullnum(mystime.getDate())
			+ " "
			+ fullnum(mystime.getHours() + ":" + fullnum(mystime.getMinutes())
					+ ":" + fullnum(mystime.getSeconds()));
	return addstime;
};

/**
 * 处理时间戳转换成日期格式
 * 
 * @param {Object}
 *            obj 时间戳{10位的时间戳需要乘以1000；13位的时间戳不需要}
 * @return {TypeName} 返回日期格式 2013-09-16
 */
function fullnum(obj) {
	if (Number(obj) < 10) {
		return '0' + obj;
	} else {
		return obj;
	}
}

// 处理file input加载的图片文件,destination是显示div,imgUpload是上传input,e是imgUpload的change事件
function review(destination, imgUpload, e) {
	// 判断浏览器是否有FileReader接口

	for (var i = 0; i < e.target.files.length; i++) {
		var file = e.target.files.item(i);
		// 允许文件MIME类型 也可以在input标签中指定accept属性
		// console.log(/^image/.*$/i.test(file.type));
		if (!(/^image/.test(file.type))) {
			continue; // 不是图片 就跳出这一次循环
		}

		// 实例化FileReader API
		var freader = new FileReader();
		freader.readAsDataURL(file);
		freader.onload = function(e) {
			var img = '<img src="' + e.target.result
					+ '" width="200px" height="200px"/>';
			destination.empty().append(img);
		};
	}

	// 处理图片拖拽的代码
	var destDom = document.getElementById('destination');
	destDom.addEventListener('dragover', function(event) {
		event.stopPropagation();
		event.preventDefault();
	}, false);

	destDom.addEventListener('drop', function(event) {
		event.stopPropagation();
		event.preventDefault();
		var img_file = event.dataTransfer.files.item(0); // 获取拖拽过来的文件信息 暂时取一个
		// console.log(event.dataTransfer.files.item(0).type);
		if (!(/^image/.test(img_file.type))) {
			alert('您还未拖拽任何图片过来,或者您拖拽的不是图片文件');
			return false;
		}
		fReader = new FileReader();
		fReader.readAsDataURL(img_file);
		fReader.onload = function(event) {
			destDom.innerHTML = '';
			destDom.innerHTML = '<img src="' + event.target.result
					+ '" width="200px" height="200px"/>';
		};
	}, false);
}

function getPro() {
	var arr = new Array();
	$.each(pro, function(index, value) {
		var s = JSON.parse('{"id":"' + value + '","text":"' + value + '"}');
		arr.push(s);
	});
	return arr;
}

function getCitys(pro) {
	var citys = city[pro];
	var arr = new Array();
	$.each(citys, function(index, value) {
		var s = JSON.parse('{"id":"' + value + '","text":"' + value + '"}');
		arr.push(s);
	});

	return arr;
}

// 求两个日期天数之差
function daysBetween(DateOne, DateTwo) {
	var OneMonth = DateOne.substring(5, DateOne.lastIndexOf('-'));
	var OneDay = DateOne
			.substring(DateOne.length, DateOne.lastIndexOf('-') + 1);
	var OneYear = DateOne.substring(0, DateOne.indexOf('-'));

	var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf('-'));
	var TwoDay = DateTwo
			.substring(DateTwo.length, DateTwo.lastIndexOf('-') + 1);
	var TwoYear = DateTwo.substring(0, DateTwo.indexOf('-'));

	var cha = ((Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) - Date
			.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) / 86400000);
	return Math.abs(cha);
}

// 增加月份
function addMonths(date, value) {
	date.setDate(date.getDate() + value);
}

/** 时间戳=>本地时间 */
function getLocalTime(milliseconds) {
	var datetime = new Date();
	datetime.setTime(milliseconds);
	var year = datetime.getFullYear();
	// 月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1)
			: datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime
			.getDate();
	var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime
			.getHours();
	var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes()
			: datetime.getMinutes();
	var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds()
			: datetime.getSeconds();
	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":"
			+ second;
}

/** 格式化日期 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds(),
	// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
