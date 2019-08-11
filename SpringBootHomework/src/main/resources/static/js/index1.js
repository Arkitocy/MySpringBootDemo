$(document).ready(function () {
    $.post("showId", function (data) {
        var loginname = data.username;
        if(loginname==null){
            window.location.href = "login.html";
        }
        $("#username").text(loginname);
    }, "json");

    $.post("homework/showByType", {"type": "竞赛"}, function (json) {
        console.log(json);
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
