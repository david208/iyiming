function Home(context, args) {
    var CONSOLE_URL = "static/frames/console.js";
    this._context = context; // 应用程序域
    this._menu = null; // 左侧主菜单
    this._tab = null; // 右侧标签栏
    this._frame = null; // 右侧标签页
    this._menuData = {}; // 左侧主菜单数据
    this._tabData = {}; // 右侧标签数据
    this._currTabTitle = null; // 右侧当前标签
    this._userRights = {}; // 用户权限表
    this._urlParams = args; // 启动参数
    this._frameScene = null; // 标签页
    this._console = null; // 调试控制台

    if (args["console"] == "true") {
        var self = this;
        $.get(CONSOLE_URL, function(data, status) {
            self._console = new Console();
        });
    }

    // 初始化
    this.initialize = function(menuId, tabId, frameId) {
        var self = this;
        var menuHandler = function(node) {
            var text = node.text;
            if (node.attributes && node.attributes.url) {
                self.addTab(text, node.attributes.url, true);
            }
        };
        self._menu = $(menuId);
        self._tab = $(tabId);
        self._frame = $(frameId);
        self._menu.accordion({
            fit: true,
            border: false,
            onSelect: function(title, index) {
                var data = self._menuData[title];
                if (data == null) {
                    return;
                }
                delete self._menuData[title];
                setTimeout(function() {
                    $("#tree_" + title).tree({
                        data: data,
                        onClick: menuHandler
                    });
                }, 100);
            }
        });

        self._tab.tabs({
            border: false,
            onSelect: function(title, index) {
                var url = self._tabData[title];
                self._currTabTitle = title;
                self.updateFrame(url);
            },
            onClose: function(title, index) {
                delete self._tabData[title];
            }
        });
    };

    // 添加左侧菜单项
    this.addMenu = function(title, data) {
        var self = this;
        self._menuData[title] = data;
        self._menu.accordion("add", {
            title: title,
            content: "<ul id='tree_" + title + "'></ul>",
            selected: false
        });
    };

    // 添加标签页
    this.addTab = function(title, url, closable) {
        var self = this;
        var isNew = self._tabData[title] == null;
        self._tabData[title] = url;
        if (isNew) {
            self._tab.tabs("add", {
                title: title,
                closable: closable
            });
        } else if (self._currTabTitle == title) {
            self.updateFrame(url);
        } else {
            self._tab.tabs("select", title);
        }
    }

    // 刷新标签页
    this.updateFrame = function(url) {
        var self = this;
        self._frame.attr("src", url);
    }

    // 注册用户权限
    this.registerRight = function(code, item) {
        var self = this;
        self._userRights[code] = item;
    }

    /**
     * 判断用户权限
     *
     * @param code
     *            权限代码
     */
    this.hasRight = function(code) {
        var self = this;
        return self._userRights[code] != null;
    }

    // 获取应用程序域
    this.getContext = function() {
        var self = this;
        return self._context;
    }

    this.isDebug = function() {
        var self = this;
        return self._urlParams["debug"] == "true";
    }

    this.log = function(message) {
        var self = this;
        if (self._console == null) {
            alert(message);
        } else {
            self._console.trace(message);
        }
    }

    this.updateScene = function(scene) {
        var self = this;
        self._frameScene = scene;
        scene.onReady(self);
    }
}

var home = null;
$(function() {
    var path = window.location.pathname;
    var index = path.indexOf("/", 1);
    var context = path.substr(0, index + 1);
    var search = window.location.search;
    var urlParams = {};
    if (search.length > 0) {
        var params = search.substr(1).split("&");
        for (var i = 0; i < params.length; i++) {
            var kv = params[i].split("=");
            urlParams[kv[0]] = kv[1];
        }
    }
    home = new Home(context, urlParams);
});
