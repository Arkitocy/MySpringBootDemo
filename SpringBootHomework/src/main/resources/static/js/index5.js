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


    $("#btn1").click(function () {
        $.getJSON("homework/rank/" + $("#type").val(), {"type": $("#type").val()}, function (json) {
            $("#tbodymain").empty();
            for (var i = 0; i < json.length; i++) {
                var rank = i+1;
                $("#tbodymain").append(
                    "<tr id='tridval" + i + "'>"
                    + "<td>" + rank
                    + "</td>"
                    + "<td>" + json[i].username
                    + "</td>"
                    + "<td>" + json[i].ac
                    + "</td>"
                    + "</tr>"
                )
            }

        })
    })


})
