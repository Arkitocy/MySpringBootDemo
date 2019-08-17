$(function () {
    $("#btn").attr('disabled', true);
    $("#namep").hide();
    $("#pwd").blur(function () {
        var pwd = $("#pwd").val();
        if (pwd.length == 0) {
            $("#pwdp").show();
            $("#btn").attr('disabled', true);
        } else {
            $("#pwdp").hide();
            $("#btn").attr('disabled', false);
        }
    });
    $("#name").blur(function () {
        var name = $("#name").val();
        if (name.length == 0) {
            $("#namepp").show();
            $("#btn").attr('disabled', true);
        } else if (name.length > 0) {
            $("#namepp").hide();
            $("#btn").attr('disabled', false);
        }
        if (/^[0-9a-zA-Z_]{1,30}$/.test(name)) {
            $("#namep").hide();
            $("#btn").attr('disabled', false);
        } else if (name.length > 30) {
            $("#namep").show();
            $("#btn").attr('disabled', true);
        }
    });

    $("button[name='btn']").click(function () {

        var adata = {
            "username": $("#name").val(),
            "password": $("#pwd").val()
        }
        var data = JSON.stringify(adata);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            data: data,
            url: "login",
            success: function (res) {
                if (res != null) {
                    $.getJSON("setId", {"id": res.id, "username": res.username,"type":res.type}, function (data) {
                        window.location.href = "index.html";
                    }, "json");

                } else {
                    window.location.href = "login.html";
                }
            },
            error: function () {
                window.location.href = "login.html";
            }
        });
    })

})
