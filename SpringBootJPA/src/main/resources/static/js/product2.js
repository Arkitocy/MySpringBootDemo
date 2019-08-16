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


    function SimpleDateFormat(pattern) {
        var fmt = new Object();
        fmt.pattern = pattern;

        fmt.parse = function (source) {
            try {
                return new Date(source);
            } catch (e) {
                console.log("字符串 " + source + " 转时间格式失败！");
                return null;
            }
        };

        fmt.format = function (date) {
            if (typeof (date) == "undefined" || date == null || date == "") {
                return "";
            }

            try {
                date = new Date(date);
            } catch (e) {
                console.log("时间 " + date + " 格式化失败！");
                return "";
            }

            var strTime = this.pattern;//时间表达式的正则

            var o = {
                "M+": date.getMonth() + 1, //月份
                "d+": date.getDate(), //日
                "H+": date.getHours(), //小时
                "m+": date.getMinutes(), //分
                "s+": date.getSeconds(), //秒
                "q+": Math.floor((date.getMonth() + 3) / 3), //季度
                "S": date.getMilliseconds() //毫秒
            };

            if (/(y+)/.test(strTime)) {
                strTime = strTime
                    .replace(RegExp.$1, (date.getFullYear() + "")
                        .substr(4 - RegExp.$1.length));
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(strTime)) {
                    strTime = strTime.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                }
            }

            return strTime;
        };
        return fmt;
    }

    $("button[name='searchbtn']").click(function () {
        var name = $("#searchproductname").val();
        console.log(name);
        $.getJSON("product/showByName", {productname: name}, function (json) {
            console.log(json);
            $("#tbodymainbtn").empty();
            for (var i = 0; i < json.length; i++) {
                $("#tbodymainbtn").append(
                    "<tr id='tridval" + i + "'>"
                    + "<td>" + json[i].id
                    + "</td>"
                    + "<td>" + json[i].name
                    + "</td>"
                    + "<td>" + json[i].category
                    + "</td>"
                    + "<td>" + json[i].productiondate
                    + "</td>"
                    + "<td>" + json[i].outdate
                    + "</td>"
                    + "<td>" + json[i].price
                    + "</td>"
                    + "<td>" + json[i].amount
                    + "</td>"
                    + "<td><button type='button' class='btn btn-primary' name='changebtn' id='" + json[i].id + "'>编辑</button></td>"
                    + "<td><button type='button' class='btn btn-primary' name='deletebtn' id='" + json[i].id + "'>删除</button></td>"
                    + "</tr>"
                );
            }

            $("button[name='changebtn']").click(function () {
                console.log(this.id);
                var id = this.id
                var fmt = SimpleDateFormat("yyyy-MM-dd");
                $.getJSON("product/findById", {id: id}, function (json) {
                    $("#changebtn").empty();
                    console.log(json);
                    console.log(json.name);

                    $("#id").attr("value", json.id);
                    $("#productcategory").attr("value", json.category);
                    $("#productname").attr("value", json.name);
                    $("#productiondate").attr("value", fmt.format(json.productiondate));
                    $("#outdate").attr("value", fmt.format(json.outdate));
                    $("#price").attr("value", json.price);
                    $("#amount").attr("value", json.amount);

                    $('#modalhwdetail').modal("show");
                    $("button[name='btnn']").click(function () {
                        var adata = {
                            "id": $("#id").val(),
                            "name": $("#productname").val(),
                            "category": $("#productcategory").val(),
                            "productiondate": $("#productiondate").val(),
                            "outdate": $("#outdate").val(),
                            "price": $("#price").val(),
                            "amount": $("#amount").val()
                        }
                        var data = JSON.stringify(adata);
                        $.ajax({
                            type: "POST",
                            contentType: "application/json",
                            data: data,
                            url: "product/update",
                            success: function (res) {
                                console.log(res);
                                if (res != "" && res == "success") {
                                    alert("修改成功");
                                    window.location.href = "index2.html";
                                } else {
                                    alert("修改失败");
                                    window.location.href = "index2.html";
                                }
                            },
                            error: function () {
                                alert("失败");
                                window.location.href = "index2.html";
                            }
                        });
                    })
                })
            })

            $("button[name='deletebtn']").click(function () {
                $.getJSON("product/deleteById", {id: this.id}, function () {
                    window.location.href = "index2.html";
                })
            })
        })


    })


})
