$(document).ready(function () {
    var loginname;
    var logintype;
    var loginid;
    $.post("showId", function (data) {
        loginname = data.username;
        logintype = data.type;
        loginid = data.id;
        if (loginname == null) {
            window.location.href = "login.html";
        }
        if (logintype == "student") {
            $("#index3").css("display", "none");
        }
        $("#username").text(loginname);
    }, "json");

    $.post("homework/showByType/结对", {"type": "结对"}, function (json) {
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
