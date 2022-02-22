/**
 *
 *
 * Object FwGlobal()
 * @augments Object
 * @constructor
 * @extends Object
 */
function FwGlobal(){};
FwGlobal.prototype=new Object();

/**
 * アクションパスプレフィックス.
 *
 * @memberOf FwGlobal
 * @type String
 */
FwGlobal.APP_URL_PREFIX = '.';

/**
 * アクションパスサフィックス.
 *
 * @memberOf FwGlobal
 * @type String
 */
FwGlobal.APP_URL_SUFFIX = '.do';

/**
 * 子ウィンドウ名のセパレータ.
 *
 * @memberOf FwGlobal
 * @type String
 */
FwGlobal.SUB_WINDOW_NAME_SEP = '_';

/**
 * 子ウィンドを起動した数.
 *
 * @memberOf FwGlobal
 * @type Number
 */
FwGlobal.subWindowCount = 0;

/**
 * フォームを送信します.
 *
 * 第3引数のsubwindowに、trueもしくは文字列を指定した場合はサブウィンドウにて起動します.
 * またtrueを指定した場合は、サブウィンドウ名は一意のウィンドウ名を自動で作成するため
 * 複数の同一画面を起動できます.
 * 多重起動したくない場合はsubwindowに文字列で指定してください.
 * ある親ウィンドウから起動するサブウィンドウの多重起動を禁止できます.
 * 第5引数のargsを指定した場合、formを送信する際のmethodはGET方式に切り替えます.
 *
 * @memberOf FwGlobal
 * @param {HTMLFormElement} form
 * @param {String} path
 * @param {Object} subWindow {Boolean}または{String}
 * @param {FwWindowOption} windowOption
 * @param {Array} args
 * @returns {Boolean}
 */
FwGlobal.submitForm = function(form, path, subWindow, windowOption, args) {
	var targetWindow = self;
	var backupTarget = form.target;
	var backupMethod = form.method;
	if (subWindow) {
		var freeWindow = true;
		var subWindowName = null;
		if (typeof subWindow == "string") {
			freeWindow = false;
			subWindowName = subWindow;
		}
		targetWindow = FwGlobal.openSubWindow(windowOption, freeWindow, subWindowName);
	}
	if (!targetWindow) {
		return false;
	}
	var params = FwGlobal.createGetParameter(args);
	var action = FwGlobal.APP_URL_PREFIX.concat(path).concat(FwGlobal.APP_URL_SUFFIX);
	if (params.length > 0) {
		action = action + "?" + params;
		form.method = "get";
	}
	form.action = action;
	form.target = targetWindow.name;
	form.submit();
	form.method = backupMethod;
	form.target = backupTarget;
	return true;
};

/**
 * Get用のURLパラメータを作成します.
 *
 * @memberOf FwGlobal
 * @param {Array} args
 * @returns {String}
 */
FwGlobal.createGetParameter = function(args) {
	if (args == null) {
		return "";
	}
	if (args.length == 0) {
		return "";
	}
	var params = "";
	for (var key in args) {
		var val = args[key];
		if (params.length > 0) {
			params = params + "&";
		}
		params = params + key + "=" + encodeURI(val);
	}
	return params;
};

/**
 * サブウィンドウを起動します.
 *
 * @memberOf FwGlobal
 * @param {FwWindowOption} windowOption ウィンドウオプション
 * @param {Boolean} freeWindow true:起動自由/false:多重起動禁止
 * @param {String} subWindowName 多重起動禁止時のウィンドウ名
 * @returns {Window}
 */
FwGlobal.openSubWindow=function(windowOption, freeWindow, subWindowName) {

	var windowName = self.name;
	if (windowName == "") {
		windowName = "defaultName";
	}
	windowName = windowName.concat(FwGlobal.SUB_WINDOW_NAME_SEP);
	if (freeWindow) {
		FwGlobal.subWindowCount++;
		windowName = windowName.concat(FwGlobal.subWindowCount);
	} else {
		windowName = windowName.concat(new String(subWindowName));
	}

	var options = "";
	if (!windowOption) {
		windowOption = new FwWindowOption();
	}

	options = windowOption.toString();
	return self.open("./html/progress.html", windowName, options);
};

/**
 *
 *
 * Object FwWindowOption()
 * @augments Object
 * @constructor
 * @extends Object
 */
function FwWindowOption(){};
FwWindowOption.prototype=new Object();

/**
 * Property width
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.width = 0;

/**
 * Property height
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.height = 0;

/**
 * Property top
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.top = 0;

/**
 * Property left
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.left = 0;

/**
 * Property titlebar
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.titlebar = 1;

/**
 * Property menubar
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.menubar = 1;

/**
 * Property toolbar
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.toolbar = 1;

/**
 * Property location
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.location = 1;

/**
 * Property scrollbars
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.scrollbars = 1;

/**
 * Property status
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.status = 1;

/**
 * Property directories
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.directories = 0;

/**
 * Property fullscreen
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.fullscreen = 0;

/**
 * Property resizable
 * @type Number
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.resizable = 1;

/**
 * To String option word.
 * @type String
 * @memberOf FwWindowOption
 */
FwWindowOption.prototype.toString = function() {
	var options = "";
	options = options.concat("width=").concat(this.width);
	options = options.concat(", height=").concat(this.height);
	options = options.concat(", top=").concat(this.top);
	options = options.concat(", left=").concat(this.left);
	options = options.concat(", titlebar=").concat(this.titlebar);
	options = options.concat(", menubar=").concat(this.menubar);
	options = options.concat(", toolbar=").concat(this.toolbar);
	options = options.concat(", location=").concat(this.location);
	options = options.concat(", scrollbars=").concat(this.scrollbars);
	options = options.concat(", status=").concat(this.status);
	options = options.concat(", directories=").concat(this.directories);
	options = options.concat(", fullscreen=").concat(this.fullscreen);
	options = options.concat(", resizable=").concat(this.resizable);
	return options;
};

