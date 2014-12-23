function Console() {
	this._dialog = $("<div style='text-align:center;overflow:hidden'></div>").appendTo("body");
	this._textarea = $("<textarea style='border:0px' readOnly></textarea>").appendTo(this._dialog);
	this._input = $("<input/>").appendTo(this._dialog);
	this._content = "Debug Console is started.\n";

	var self = this;
	this._input.keydown(function(e) {
		if (e.keyCode == 13) {
			var text = self._input.val();
			if (text.length > 0) {
				self._input.val("");
				if (text == "cls") {
					self._content = "";
				} else {
					try {
						var result = eval(text);
						if (result != null) {
							text += "\n  " + result;
						}
					} catch (e) {
						text += "\n  " + e.toString();
					}
				}
				self.trace(text);
			}
		}
	});

	var width = $(document).width() * 0.33;
	var height = $(document).height() * 0.7;
	this._dialog.window({
		width : width,
		height : height,
		left : $(document).width() - width - 150,
		top : 30,
		title : "调试控制台",
		minimizable : false,
		resizable : false,
		shadow : false,
		modal : false,
		zIndex : 99999,
		onResize : function() {
			var container = self._textarea.parent();
			self._textarea.width(container.width());
			self._textarea.height(container.height() - 40);
			self._input.width(container.width() - 10);
			self._input.height(30);
		}
	});

	this.trace = function(text) {
		this._content = this._content + text + "\n> ";
		this._textarea.val(this._content);
		this._textarea[0].scrollTop = this._textarea[0].scrollHeight;
	}

	this.trace("");
}