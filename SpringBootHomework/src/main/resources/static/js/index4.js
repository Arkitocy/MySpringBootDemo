$(document).ready(function () {
    $.post("showId", function (data) {
        var loginname = data.username;
        if(loginname==null){
            window.location.href = "login.html";
        }
        $("#username").text(loginname);
    }, "json");

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
        var type = $("#searchtype").val();
        var finishTime = $("#searchfinishTime").val();
        $.getJSON("homework/showByTypeAndFinishTime", {type: type, finishTime: finishTime}, function (json) {
            $("#tbodymainbtn").empty();
            for (var i = 0; i < json.length; i++) {
                $("#tbodymainbtn").append(
                    "<tr id='tridval" + i + "'>"
                    + "<td>" + json[i].id
                    + "</td>"
                    + "<td>" + json[i].title
                    + "</td>"
                    + "<td>" + json[i].type
                    + "</td>"
                    + "<td>" + json[i].updateTime
                    + "</td>"
                    + "<td>" + json[i].finishTime
                    + "</td>"
                    + "<td><button type='button' class='btn btn-primary' name='changebtn' id='" + json[i].id + "'>编辑</button></td>"
                    + "<td><button type='button' class='btn btn-primary' name='checkbtn' id='" + json[i].id + "'>查看</button></td>"
                    + "<td><button type='button' class='btn btn-primary' name='deletebtn' id='" + json[i].id + "'>删除</button></td>"
                    + "</tr>"
                );
            }

            $("button[name='changebtn']").click(function () {
                console.log(this.id);
                var id = this.id
                var fmt = SimpleDateFormat("yyyy-MM-dd");
                $.getJSON("homework/findAllById", {id: id}, function (js) {
                    $("#changebtn").empty();
                    console.log(js);
                    console.log(js[0].details);
                    $("#id").attr("value", js[0].id);
                    $("#hwtitle").attr("value", js[0].title);
                    $("#hwtype").attr("value", js[0].type);
                    $("#hwupdateTime").attr("value", fmt.format(js[0].updateTime));
                    $("#hwfinishTime").attr("value", fmt.format(js[0].finishTime));
                    document.getElementById("hwdetails").innerHTML = js[0].details;
                    $('#modalhwdetail').modal("show");
                    $("button[name='btnn']").click(function () {
                        var adata = {
                            "id": $("#id").val(),
                            "title": $("#hwtitle").val(),
                            "typpe": $("#hwtype").val(),
                            "updateTime": $("#hwupdateTime").val(),
                            "finishTime": $("#hwfinishTime").val(),
                            "details": $("#hwdetails").val()
                        }
                        var data = JSON.stringify(adata);
                        $.ajax({
                            type: "POST",
                            contentType: "application/json",
                            data: data,
                            url: "homework/update",
                            success: function (res) {
                                console.log(res);
                                if (res != "") {
                                    alert("修改成功");
                                    window.location.href = "index4.html";
                                } else {
                                    alert("修改失败");
                                    window.location.href = "index4.html";
                                }
                            },
                            error: function () {
                                alert("失败");
                                window.location.href = "index4.html";
                            }
                        });
                    })
                })
            })

            $("button[name='deletebtn']").click(function () {
                $.getJSON("homework/deleteById", {id: this.id}, function (rs) {
                    console.log(rs);
                    alert(rs);
                    if ("success" == rs) {
                        window.location.href = "index4.html";
                    } else {
                        alert("删除失败");
                    }
                });
                window.location.href = "index4.html";
            })

            $("button[name='checkbtn']").click(function () {
                $('#modalhwdetail2').modal("show");
            })

        })
    })


})