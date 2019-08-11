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
            window.location.href = "index.html";
        }
        $("#username").text(loginname);
    }, "json");

    $("button[name='btnn']").click(function () {
        var adata = {
            "title": $("#title").val(),
            "type": $("#type").val(),
            "finishTime": $("#finishTime").val(),
            "details": $("#details").val()
        }
        var data = JSON.stringify(adata);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            data: data,
            url: "homework/save",
            success: function (res) {
                if (res != "") {
                    alert("添加成功");
                    window.location.href = "index3.html";
                } else {
                    alert("添加失败");
                    window.location.href = "index3.html";
                }
            },
            error: function () {
                alert("失败");
                window.location.href = "index3.html";
            }
        });

    })


})
