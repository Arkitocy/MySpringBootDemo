$(function () {
    $("#btn").attr('disabled',true);
    $("#namep").hide();
    $("#pwd").blur(function () {
        var pwd = $("#pwd").val();
        if (/^[0-9a-zA-Z_]{6,15}$/.test(pwd)) {
            $("#pwdp").hide();
            $("#btn").attr('disabled',false);

        } else  {
            $("#pwdp").show();
            $("#btn").attr('disabled',true);

        }
    });
    $("#pwd2").blur(function () {
        var pwd2 = $("#pwd2").val();
        if (pwd2 != $("#pwd").val()) {
            $("#pwd3p").show();
            $("#btn").attr('disabled',true);
        } else if (pwd2 == $("#pwd").val()) {
            $("#pwd3p").hide();
            $("#btn").attr('disabled',false);
        }
        if (pwd2.length == 0) {
            $("#pwd2p").show();
            $("#btn").attr('disabled',true);
        } else if (pwd2.length > 0) {
            $("#pwd2p").hide();
            $("#btn").attr('disabled',false);
        }
    });
    $("#name").blur(function () {
        var name = $("#name").val();
        if (this.value.length == 0) {
            $("#namepp").show();
            $("#btn").attr('disabled',true);
        } else if (name.length > 0) {
            $("#namepp").hide();
            $("#btn").attr('disabled',false);
        }
        if (/^[0-9a-zA-Z_]{1,30}$/.test(name)) {
            $("#namep").hide();
            $("#btn").attr('disabled',false);
        } else if (name.length > 30) {
            $("#namep").show();
            $("#btn").attr('disabled',true);
        }
        $.getJSON("CheckUserNameServlet",{username:name},function (json) {
            console.log(json.rs);
            var rs=json.rs+"";
            if(rs=='false'){
                $("#nameppp").hide();
                $("#btn").attr('disabled',false);
            }else {
                $("#nameppp").show();
                $("#btn").attr('disabled',true);
            }
        });
    });
})
