var serverUrl = "";


$(function () {
    $("#loginButton").click(function () {
        // $("form").submit();
        var object;
        post("login", formdataChangeToJson("loginForm"));
    });
});
function formdataChangeToJson(str) {
    var queryArray = $("#"+str).serializeArray();
    var jsonString= '{';
    for (var i = 0; i < queryArray.length; i++) {
        jsonString+= JSON.stringify(queryArray[i].name) + ':' + JSON.stringify(queryArray[i].value) + ',';
    }
    jsonString= jsonString.substring(0, (jsonString.length - 1));
    jsonString+= '}';
    return JSON.parse(jsonString)
}
/**
 * 同步请求，跨域加载
 * @param url
 * @param data
 * @returns {*}
 */
function eduPostUrl(url, data) {
    var lastResult;
    $.ajax(
        {
            type: "POST",
            url: serverUrl + url,
            data: data,
            xhrFields:{
                withCredentials:true
            },
            async: false,
            crossDomain: true,
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
    temp.action = serverUrl+URL;
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