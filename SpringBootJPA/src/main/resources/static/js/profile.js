$(document).ready(function () {
    var loginname;
    var loginid;
    var headname;
    $.post("product/showId", function (data) {
        loginname = data.username;

        if (loginname == null) {
            window.location.href = "login.html";
        }
        $("#username").text(loginname);
        $("#showusername").text(loginname);
        $("#name").attr("placeholder",loginname);
        $.getJSON("findUserID/" + loginname, function (data) {
            loginid = data.id;
            headname = data.head;
            $("#headimg1").attr("src","http://localhost:8080/SpringBootJPA/image/"+headname);
            $("#headimg2").attr("src","http://localhost:8080/SpringBootJPA/image/"+headname);
        })

        $("#headimg2").click(function () {
            $('#uploadmodal').modal("show");
            $("button[name='uploadbtn2']").click(function () {
                var formData = new FormData(document.getElementById("upload-form"));
                $.ajax({
                    url: "upload",
                    method: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function (resp) {
                        if (resp.result != null) {
                            headname = resp.result;
                            $('#uploadmodal').modal('hide');
                        } else {
                            alert("上传失败");
                        }
                    }
                });

            })
        })

        $("button[name='btn']").click(function () {

            var adata = {
                "id": loginid,
                "username": $("#name").val(),
                "password": $("#pwd").val(),
                "head": headname
            }
            var data = JSON.stringify(adata);
            $.ajax({
                type: "POST",
                contentType: "application/json",
                data: data,
                url: "edit",
                success: function (res) {
                    console.log(res);
                    if (res != "" && res == "success") {
                        alert("修改成功");
                        $.getJSON("product/setId", {"username": $("#name").val()}, function (data) {
                            window.location.href = "index.html";
                        }, "json");
                    } else {
                        alert("修改失败");
                        window.location.href = "index.html";
                    }
                },
                error: function () {
                    alert("失败");
                    window.location.href = "profile.html";
                }
            });
        })

    }, "json");


    $("#btn").attr('disabled', true);
    $("#namep").hide();
    $("#pwd").blur(function () {
        var pwd = $("#pwd").val();
        if (/^[0-9a-zA-Z_]{6,15}$/.test(pwd)) {
            $("#pwdp").hide();
            $("#btn").attr('disabled', false);

        } else {
            $("#pwdp").show();
            $("#btn").attr('disabled', true);

        }
    });
    $("#pwd2").blur(function () {
        var pwd2 = $("#pwd2").val();
        if (pwd2 != $("#pwd").val()) {
            $("#pwd3p").show();
            $("#btn").attr('disabled', true);
        } else if (pwd2 == $("#pwd").val()) {
            $("#pwd3p").hide();
            $("#btn").attr('disabled', false);
        }
        if (pwd2.length == 0) {
            $("#pwd2p").show();
            $("#btn").attr('disabled', true);
        } else if (pwd2.length > 0) {
            $("#pwd2p").hide();
            $("#btn").attr('disabled', false);
        }
    });
    $("#name").blur(function () {
        var name = $("#name").val();
        if (this.value.length == 0) {
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
        $.getJSON("checkName", {username: name}, function (json) {
            console.log(json);
            var rs = json.result + "";
            console.log(rs);
            if (rs == 'false') {
                $("#nameppp").hide();
                $("#btn").attr('disabled', false);
            } else {
                $("#nameppp").show();
                $("#btn").attr('disabled', true);
            }
        });
    });


})