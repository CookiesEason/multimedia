var StringUtil = (function () {
    function StringUtil() {
    }
    StringUtil.htmlEncode = function (str) {
        var s = '';
        if (str.length == 0)
            return '';
        s = str.replace(/&/g, '&gt;');
        s = s.replace(/</g, '&lt;');
        s = s.replace(/>/g, '&gt;');
        s = s.replace(/ /g, '&nbsp;');
        s = s.replace(/\'/g, '&#39;');
        s = s.replace(/\"/g, '&quot;');
        s = s.replace(/\n/g, '<br>');
        return s;
    };
    StringUtil.htmlDecode = function (str) {
        var s = '';
        if (str.length == 0)
            return '';
        s = str.replace(/&amp;/g, '&');
        s = s.replace(/&lt;/g, '<');
        s = s.replace(/&gt;/g, '>');
        s = s.replace(/&nbsp;/g, ' ');
        s = s.replace(/&#39;/g, '\'');
        s = s.replace(/&quot;/g, '\'');
        return s;
    };
    StringUtil.isEmptyOrNull = function (str) {
        if (str == "" || str == null)
            return true;
        return false;
    };
    StringUtil.getDoubleQuoteString = function (str) {
        var reg = /\"(.*)\"/;
        var resArr = reg.exec(str);
        if (resArr) {
            str = resArr[1];
        }
        return str;
    };
    StringUtil.getLastSlashContent = function (str) {
        var index = str.lastIndexOf("\/");
        return str.substring(index + 1, str.length);
    };
    StringUtil.isHasClass = function (e, c) {
        if (StringUtil.isEmptyOrNull(c) || !e)
            return false;
        return new RegExp(" " + c + " ").test(" " + e.className + " ");
    };
    StringUtil.addClass = function (e, c) {
        if (!StringUtil.isHasClass(e, c)) {
            e.className = StringUtil.isEmptyOrNull(e.className) ? c : e.className + " " + c;
        }
    };
    StringUtil.removeClass = function (e, c) {
        if (StringUtil.isHasClass(e, c)) {
            var newClass = " " + e.className.replace(/\f\n\r\t\v/g, "") + " ";
            while (newClass.indexOf(" " + c + " ") >= 0) {
                newClass = newClass.replace(" " + c + " ", " ");
            }
            e.className = newClass;
        }
    };
    StringUtil.css = function (e, pseudoElt) {
        return document.defaultView.getComputedStyle(e, pseudoElt);
    };
    return StringUtil;
}());
var DateUtil = (function () {
    function DateUtil() {
    }
    DateUtil.dateFormat = function (time) {
        var dt = new Date(time);
        return dt.getFullYear() + '年' + (dt.getMonth() + 1) + '月' + dt.getDate() + '日' + DateUtil.toDouble(dt.getHours()) + ':' + DateUtil.toDouble(dt.getMinutes()) + ':' + DateUtil.toDouble(dt.getSeconds());
    };
    DateUtil.toDouble = function (num) {
        if (num < 10)
            return '0' + num;
        else
            return '' + num;
    };
    DateUtil.getTime = function (str) {
        str = str.replace(/-/g, '/');
        var date = new Date(str);
        return date.getTime();
    };
    return DateUtil;
}());
var CookieUtil = (function () {
    function CookieUtil() {
    }
    CookieUtil.set = function (name, value, expires, domain, path, secure) {
        var cookieText = "";
        cookieText += encodeURIComponent(name) + "=" + encodeURIComponent(value);
        if (expires instanceof Date) {
            cookieText += "; expires=" + expires.toUTCString();
        }
        if (path) {
            cookieText += "; path=" + path;
        }
        if (domain) {
            cookieText += "; domain=" + domain;
        }
        if (secure) {
            cookieText += "; secure";
        }
        document.cookie = cookieText;
    };
    CookieUtil.get = function (name) {
        var cookieName = encodeURIComponent(name) + "=", cookieStart = document.cookie.indexOf(cookieName), cookieValue = "";
        if (cookieStart > -1) {
            var cookieEnd = document.cookie.indexOf(";", cookieStart);
            if (cookieEnd == -1) {
                cookieEnd = document.cookie.length;
            }
            cookieValue = decodeURIComponent(document.cookie.substring(cookieStart + cookieName.length, cookieEnd));
        }
        return cookieValue;
    };
    CookieUtil.unset = function (name, domain, path, secure) {
        this.set(name, "", new Date(), domain, path, secure);
    };
    return CookieUtil;
}());
var Pageable = (function () {
    function Pageable(totalPages, visiblePages, nowPage, parentElement) {
        this.firstTemplate = "\n    <a href=\"javascript:;\" class=\"" + PageableClass[6] + " " + PageableClass[0] + "\">\u9996\u9875</a>\n    <a href=\"javascript:;\" class=\"" + PageableClass[6] + " " + PageableClass[1] + "\">\u4E0A\u4E00\u9875</a>\n    ";
        this.endTemplate = "\n    <a href=\"javascript:;\" class=\"" + PageableClass[6] + " " + PageableClass[4] + "\">\u4E0B\u4E00\u9875</a>\n    <a href=\"javascript:;\" class=\"" + PageableClass[6] + " " + PageableClass[5] + "\">\u672B\u9875</a>\n    ";
        this.totalPages = totalPages;
        this.visiblePages = visiblePages;
        this.nowPage = nowPage;
        this.parentElement = parentElement;
    }
    Pageable.prototype.create = function () {
        var _this = this;
        this.parentElement.innerHTML = this.firstTemplate;
        var startPage = this.nowPage - Math.floor(this.visiblePages / 2);
        if (startPage > 1) {
            this.parentElement.appendChild(this.createEllipsisTemplate());
        }
        else {
            startPage = 1;
        }
        var endPage = startPage + this.visiblePages - 1;
        var isShowFinish = (endPage >= this.totalPages);
        if (isShowFinish) {
            endPage = this.totalPages;
        }
        for (var i = startPage; i <= endPage; i++) {
            var item = this.createPageNumTemplate(i);
            if (i == this.nowPage) {
                StringUtil.addClass(item, PageableClass[8]);
            }
            this.parentElement.appendChild(item);
        }
        if (!isShowFinish) {
            this.parentElement.appendChild(this.createEllipsisTemplate());
        }
        this.parentElement.innerHTML += this.endTemplate;
        if (this.nowPage == 1) {
            StringUtil.addClass(document.getElementsByClassName(PageableClass[0]).item(0), PageableClass[7]);
            StringUtil.addClass(document.getElementsByClassName(PageableClass[1]).item(0), PageableClass[7]);
        }
        else if (this.nowPage == this.totalPages) {
            StringUtil.addClass(document.getElementsByClassName(PageableClass[5]).item(0), PageableClass[7]);
            StringUtil.addClass(document.getElementsByClassName(PageableClass[4]).item(0), PageableClass[7]);
        }
        var items = this.parentElement.getElementsByClassName(PageableClass[6]);
        var _loop_1 = function (i) {
            var item = items.item(i);
            if (!StringUtil.isHasClass(item, PageableClass[2]) && !StringUtil.isHasClass(item, PageableClass[7]) && !StringUtil.isHasClass(item, PageableClass[8])) {
                item.addEventListener("click", function () { return _this.onClick(item); });
            }
        };
        for (var i = 0; i < items.length; i++) {
            _loop_1(i);
        }
    };
    Pageable.prototype.createTemplate = function () {
        var item = document.createElement("a");
        StringUtil.addClass(item, PageableClass[6]);
        item.href = "javascript:;";
        return item;
    };
    Pageable.prototype.createEllipsisTemplate = function () {
        var item = this.createTemplate();
        item.innerHTML = "...";
        StringUtil.addClass(item, PageableClass[2]);
        return item;
    };
    Pageable.prototype.createPageNumTemplate = function (num) {
        var item = this.createTemplate();
        item.innerHTML = num.toString();
        StringUtil.addClass(item, PageableClass[3]);
        return item;
    };
    Pageable.prototype.onClick = function (e) {
        var _i;
        for (var i = 0; i <= PageableClass["pageable-end"]; i++) {
            if (StringUtil.isHasClass(e, PageableClass[i])) {
                _i = i;
                break;
            }
        }
        switch (_i) {
            case 0:
                this.changePageNum(1);
                break;
            case 1:
                this.changePageNum(this.nowPage - 1);
                break;
            case 2:
                break;
            case 3:
                this.changePageNum(parseInt(e.innerHTML));
                break;
            case 4:
                this.changePageNum(this.nowPage + 1);
                break;
            case 5:
                this.changePageNum(this.totalPages);
                break;
            default:
                return;
        }
    };
    Pageable.prototype.pageChange = function () {
    };
    Pageable.prototype.changePageNum = function (num) {
        this.nowPage = num;
        this.pageChange();
        this.create();
    };
    return Pageable;
}());
var PageableClass;
(function (PageableClass) {
    PageableClass[PageableClass["pageable-first"] = 0] = "pageable-first";
    PageableClass[PageableClass["pageable-previous"] = 1] = "pageable-previous";
    PageableClass[PageableClass["pageable-ellipsis"] = 2] = "pageable-ellipsis";
    PageableClass[PageableClass["pageable-num"] = 3] = "pageable-num";
    PageableClass[PageableClass["pageable-next"] = 4] = "pageable-next";
    PageableClass[PageableClass["pageable-end"] = 5] = "pageable-end";
    PageableClass[PageableClass["pageable-item"] = 6] = "pageable-item";
    PageableClass[PageableClass["pageable-disable"] = 7] = "pageable-disable";
    PageableClass[PageableClass["pageable-active"] = 8] = "pageable-active";
})(PageableClass || (PageableClass = {}));
(function () {
    $.ajaxSetup({
        headers: {
            "token": CookieUtil.get("token")
        }, beforeSend: function () {
        }, complete: function () {
        }
    });
})();
var Code;
(function (Code) {
    Code[Code["SUCCESS"] = 0] = "SUCCESS";
    Code[Code["VIEW_EXIST"] = 1] = "VIEW_EXIST";
    Code[Code["VIEW_NOT_EXIST"] = 2] = "VIEW_NOT_EXIST";
    Code[Code["USER_EXIST"] = 3] = "USER_EXIST";
    Code[Code["USER_NOT_EXIST"] = 4] = "USER_NOT_EXIST";
    Code[Code["OVER_AUTHORITY"] = 5] = "OVER_AUTHORITY";
    Code[Code["PARAMETER_ERROR"] = 6] = "PARAMETER_ERROR";
})(Code || (Code = {}));
var UserRole;
(function (UserRole) {
    UserRole[UserRole["USER"] = 0] = "USER";
    UserRole[UserRole["ADMIN"] = 1] = "ADMIN";
})(UserRole || (UserRole = {}));
var ViewKeyWord;
(function (ViewKeyWord) {
    ViewKeyWord[ViewKeyWord["ALL"] = 0] = "ALL";
    ViewKeyWord[ViewKeyWord["POPULAR_RECOMMENDATION"] = 1] = "POPULAR_RECOMMENDATION";
    ViewKeyWord[ViewKeyWord["WEEKEND_TRAVEL"] = 2] = "WEEKEND_TRAVEL";
    ViewKeyWord[ViewKeyWord["THE_BESTS_EASON"] = 3] = "THE_BESTS_EASON";
    ViewKeyWord[ViewKeyWord["ANCIENT_VILLAGE_TOWN"] = 4] = "ANCIENT_VILLAGE_TOWN";
})(ViewKeyWord || (ViewKeyWord = {}));
var User = (function () {
    function User(name, password, role) {
        this.role = 0;
        this.name = name;
        this.password = password;
        if (role) {
            this.role = role;
        }
    }
    User.prototype.check = function () {
        var nameReg = /^[\u4E00-\uFA29\w]{2,8}$/;
        var passwordReg = /^([A-Za-z0-9]|[._]){6,14}$/;
        if (nameReg.test(this.name) && passwordReg.test(this.password)) {
            return true;
        }
        return false;
    };
    User.prototype.toString = function () {
        return "name=" + this.name + "&password=" + this.password + "&role=" + this.role;
    };
    return User;
}());
var View = (function () {
    function View(name, address, description, imgsUrl, suggestedPlay, keyWord) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.imgsUrl = imgsUrl;
        this.suggestedPlay = suggestedPlay;
        this.keyWord = keyWord;
    }
    View.prototype.toString = function () {
        return "name=" + this.name + "&address=" + this.address + "&description=" + this.description + "&imgsUrl=" + this.imgsUrl + "&suggestedPlay=" + this.suggestedPlay + "&keyWord=" + this.keyWord;
    };
    View.prototype.check = function () {
    };
    return View;
}());
var PREFIX = "http://localhost:8081/";
var UserApi = (function () {
    function UserApi() {
    }
    UserApi.login = function () {
        return this.prefix + "login";
    };
    UserApi.register = function () {
        return this.prefix + "register";
    };
    UserApi.refresh = function () {
        return this.prefix + "refresh";
    };
    UserApi.passUpdate = function () {
        return this.prefix + "pass-update";
    };
    UserApi.passForget = function () {
        return this.prefix + "pass-forget";
    };
    UserApi.list = function () {
        return this.prefix + "list";
    };
    UserApi.roleUpdate = function () {
        return this.prefix + "role-update";
    };
    UserApi.love = function (viewId) {
        return this.prefix + "love/" + viewId;
    };
    UserApi.collect = function (viewId) {
        return this.prefix + "collect/" + viewId;
    };
    UserApi.delete = function (name) {
        return "" + this.prefix + name;
    };
    UserApi.avatarUpdate = function () {
        return this.prefix + "avatar";
    };
    UserApi.prefix = PREFIX + "user/";
    return UserApi;
}());
var ContactApi = (function () {
    function ContactApi() {
    }
    ContactApi.add = function () {
        return this.prefix + "add";
    };
    ContactApi.list = function () {
        return this.prefix + "list";
    };
    ContactApi.update = function () {
        return this.prefix + "update";
    };
    ContactApi.prefix = PREFIX + "contact/";
    return ContactApi;
}());
var ViewApi = (function () {
    function ViewApi() {
    }
    ViewApi.commentAdd = function () {
        return this.prefix + "comment-add";
    };
    ViewApi.id = function (id) {
        return "" + this.prefix + id;
    };
    ViewApi.list = function () {
        return this.prefix + "list";
    };
    ViewApi.add = function () {
        return this.prefix + "add";
    };
    ViewApi.prefix = PREFIX + "view/";
    return ViewApi;
}());
var ExtranetApi = (function () {
    function ExtranetApi() {
    }
    ExtranetApi.qiniuUptoken = function () {
        return PREFIX + "qiniu/uptoken";
    };
    return ExtranetApi;
}());
var PlanApi = (function () {
    function PlanApi() {
    }
    PlanApi.list = function () {
        return this.prefix + "list";
    };
    PlanApi.add = function () {
        return this.prefix + "add";
    };
    PlanApi.id = function (id) {
        return "" + this.prefix + id;
    };
    PlanApi.prefix = PREFIX + "plan/";
    return PlanApi;
}());
