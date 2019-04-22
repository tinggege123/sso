$(function () {
    $("#loginEnjoy").click(function () {
        post("/main", null);
    });
    $("#logout").click(function () {
        eduPostUrl("/ssoClient/logout", null);

    });
});


/**
 * 同步请求，跨域加载
 * @param url
 * @param data
 * @returns {*}
 */
function eduPostUrl(url, data) {
    var lastResult = "";
    $.ajax(
        {
            type: "POST",
            url: url,
            data: data,
            // async: true,
            xhrFields: {
                withCredentials: true
            },
            // crossDomain: true,
            success: function (result) {
                lastResult = result;
                alert(lastResult.msg);
                // alert("退出成功，跳转回欢迎页面");
                post("/welcome",null);

            },
            error: function (err) {
                console.log(err);
                console.log(err.status);
                if (err.status == 302) {
                    lastResult = '跳转登录'
                    console.log(url + '需要登录');
                    var resultStr = err.responseText.toString();
                    // var resultData = eval(resultStr);
                    var parse = eval('(' + resultStr + ')');
                    parse.loginSuccessHtml = window.location.href;
                    post(parse.toUrl, parse);

                }
                console.log(url + '请求失败');
            }
        }
    )
    return lastResult;
}

/**
 * 同步请求，跨域加载
 * @param url
 * @param data
 * @returns {*}
 */
function eduCommonUrl(url, data) {
    var lastResult;
    console.log(url);
    $.ajax(
        {
            type: "POST",
            url: url,
            data: data,
            async: false,
            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },
            success: function (result) {
                lastResult = result;
            },
            error: function () {
                console.log(url + '请求失败');
            }
        }
    )
    return lastResult;
}

/**
 *
 *
 * @param URL
 * @param PARAMS
 * @returns {HTMLFormElement}
 */
function post(URL, PARAMS) {
    var temp = document.createElement("form");
    temp.action = URL;
    temp.method = "post";
    temp.style.display = "none";
    for (var x in PARAMS) {
        var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = PARAMS[x];
        temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
    return temp;
}
