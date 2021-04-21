$(function () {
    console.log( $.cookie("lang"));
    var lang = getUrlParam("lang") == null ? $.cookie("lang") : getUrlParam("lang");

    if (lang == undefined) {
        /* get browser default lang */
        if (navigator.userLanguage) {
            lang = navigator.userLanguage;
        } else {
            lang = navigator.language;
        }
        lang = lang.replace("-", "_");
    }

    $.i18n.properties({
        name: "messages", //资源文件名称
        path: '/i18n/', //资源文件路径
        mode: 'map', //用Map的方式使用资源文件中的值
        language: lang
    });

    if ($.cookie("lang") == undefined) {
        $.cookie('lang', lang, {expires: 30});
        location.href = "?lang=" + lang;
    }
});


function getUrlParam(name) {
    //构造一个含有目标参数的正则表达式对象
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //匹配目标参数
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

function changeLang(langType) {
    $.i18n.properties({
        name: "messages", //资源文件名称
        path: '/i18n/', //资源文件路径
        mode: 'map', //用Map的方式使用资源文件中的值
        language: langType,
        callback: function () {
            $.cookie('lang', langType, {expires: 30});
        }
    });

    location.href = "?lang=" + langType;
}