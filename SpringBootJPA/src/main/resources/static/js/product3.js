$(document).ready(function () {
    var loginname;
    var loginid;
    var headname;
    $.post("product/showId",function (data) {
        loginname=data.username;
        if (loginname == null) {
            window.location.href = "login.html";
        }
        $("#username").text(loginname);
        $.getJSON("findUserID/" + loginname, function (data) {
            loginid = data.id;
            headname = data.head;
            $("#headimg").attr("src","http://localhost:8080/SpringBootJPA/image/"+headname);
        })
    },"json");

    $("button[name='btnn']").click(function () {
        var adata = {
            "name": $("#productname").val(),
            "category": $("#productcategory").val(),
            "productiondate": $("#productiondate").val(),
            "outdate": $("#outdate").val(),
            "price": $("#price").val(),
            "amount": $("#amount").val()
        }
        var data = JSON.stringify(adata);
        console.log(data);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            data: data,
            url: "product/save",
            success: function (res) {
                console.log(res);
                if (res != "" && res == "success") {
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
