$(document).ready(function () {

    $.post("showId", function (data) {
        var loginname = data.username;
        var logintype = data.type;
        var loginid = data.id;
        if(loginname==null){
            window.location.href = "login.html";
        }
        if(logintype=="student"){
            $("#index3").css("display","none");
        }

        $("#username").text(loginname);
    }, "json");

    $.post("homework/showByType", {"type": "竞赛"}, function (json) {
        $("#hw").empty();
        for (var i = 0; i < json.length; i++) {
            $("#hw").append(
                "<h2 id='tridval" + i + "'>"
                + json[i].title +
                "</h2>" + "<li>" + json[i].details + "</li>"
            );
        }
    })


})
