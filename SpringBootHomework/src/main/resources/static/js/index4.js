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
            $("button[name='changebtn']").attr("style", "display:none;");
            $("button[name='deletebtn']").attr("style", "display:none;");
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

    function getdata(pagen,type,finishTime) {
        $.getJSON("homework/showByTypeAndFinishTime/" + pagen, {type: type, finishTime: finishTime}, function (json) {
            if (pagen <= json.totalPages) {
                $("#tbodymainbtn").empty();
                for (var i = 0; i < json.content.length; i++) {
                    $("#tbodymainbtn").append(
                        "<tr id='tridval" + i + "'>"
                        + "<td>" + json.content[i].id
                        + "</td>"
                        + "<td>" + json.content[i].title
                        + "</td>"
                        + "<td>" + json.content[i].type
                        + "</td>"
                        + "<td>" + json.content[i].updateTime
                        + "</td>"
                        + "<td>" + json.content[i].finishTime
                        + "</td>"
                        + "<td><button type='button' class='btn btn-primary' name='changebtn' id='" + json.content[i].id + "'>编辑</button></td>"
                        + "<td><button type='button' class='btn btn-primary' name='checkbtn' id='" + json.content[i].id + "'>查看</button></td>"
                        + "<td><button type='button' class='btn btn-primary' name='deletebtn' id='" + json.content[i].id + "'>删除</button></td>"
                        + "<td><button type='button' class='btn btn-primary' name='uploadbtn' id='" + json.content[i].id + "'>上传</button></td>"
                        + "</tr>"
                    );
                }
                var pagenum = json.totalPages;
                $(".pagination").empty();
                $(".pagination").append('<li class="" ><a class="page-link" href="#" id="firstpage">首页</a></li>');
                $(".pagination").append('<li class="" ><a class="page-link" href="#" id="previosepage">上一页</a></li>');
                for (var j = 0; j < pagenum; j++) {
                    $(".pagination").append('<li class="page-item" id="page' + j + '"><a class="page-link" href="#">' + (j + 1) + '</a></li>');
                }
                $(".pagination").append('<li class="" ><a class="page-link" href="#" id="nextpage">下一页</a></li>');
                $(".pagination").append('<li class="" ><a class="page-link" href="#" id="lastpage">尾页</a></li>');
                $(".page-item").removeClass("active");
                $("#page" + pagen).addClass("active");
                $("#nextpage").click(function () {
                    var pagenum1 = Number(pagen) + Number(1);
                    if (pagen < pagenum - 1) {
                        getdata(pagenum1,type,finishTime);
                    }
                });
                $("#previosepage").click(function () {
                    var pagenum2 = Number(pagen) - Number(1);
                    if (pagen > 0) {
                        getdata(pagenum2,type,finishTime);
                    }
                });
                $("#lastpage").click(function () {
                    console.log(1);
                    getdata(pagenum-1,type,finishTime);
                });
                $("#firstpage").click(function () {
                    console.log(1);
                    getdata(0,type,finishTime);
                });


                $(".page-item").click(function () {
                    page = this.id.substr(4);
                    getdata(page,type,finishTime);
                });
                if (logintype == "student") {
                    $("button[name='changebtn']").attr("style", "display:none;");
                    $("button[name='deletebtn']").attr("style", "display:none;");
                }
                $("button[name='changebtn']").click(function () {
                    var id = this.id
                    var fmt = SimpleDateFormat("yyyy-MM-dd");
                    $.getJSON("homework/findAllById/" + id, {id: id}, function (js) {
                        $("#changebtn").empty();
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
                                "type": $("#hwtype").val(),
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
                    var id = this.id;
                    console.log(id);
                    $.getJSON("homework/deleteById/" + id, {id: id}, function (rs) {
                        if (rs.rs == 'success') {
                            window.location.href = "index4.html";
                        } else {
                            alert("删除失败");
                            window.location.href = "index4.html";
                        }
                    });
                })

                $("button[name='checkbtn']").click(function () {
                    $.getJSON("homework/showdetails/" + this.id, {"id": this.id}, function (json) {
                        $("#dtbodybtn").empty();
                        for (var i = 0; i < json.length; i++) {
                                $("#dtbodybtn").append(
                                    "<tr id='tridval" + i + "'>"
                                    + "<td>" + json[i].id
                                    + "</td>"
                                    + "<td>" + json[i].username
                                    + "</td>"
                                    + "<td>" + json[i].completeTime
                                    + "</td>"
                                    + "<td>" + json[i].status
                                    + "</td>"
                                    + "<td><button type='button' class='btn btn-primary' name='detailmodalbtn' id='" + json[i].homeworkid + "'>查看</button></td>"
                                    +   "<td><a id='hwdw' href='"+"http://localhost:9084/SpringBootHomework/homeworkrs/"+json[i].homeworkid+"'>下载</a></td>"
                                    + "</tr>"
                                )
                            if (logintype == "student") {
                                $("button[name='detailmodalbtn']").attr("style", "display:none;");
                                $("#hwdw").attr("style", "display:none;");
                            }
                            $("button[name='detailmodalbtn']").click(function () {
                                $("#downloadbody").empty();
                                $('#modalhwdetail2').modal("hide");
                                $('#detailmodal').modal("show");
                                console.log(this.id);
                                $.get("homework/download/"+this.id,function (json) {
                                    $("#downloadbody").append(json);
                                })
                            })
                        }

                    })
                    $('#modalhwdetail2').modal("show");



                })



                $("button[name='uploadbtn']").click(function () {
                    var hid = this.id;
                    var uid = loginid;
                    $('#uploadmodal').modal("show");
                    $("button[name='uploadbtn2']").click(function () {
                        var formData = new FormData(document.getElementById("upload-form"));
                        $.ajax({
                            url: "homework/upload",
                            method: 'POST',
                            data: formData,
                            contentType: false,
                            processData: false,
                            success: function (resp) {
                                if (resp.result != null) {
                                    $.getJSON("homework/saveDetails/" + uid + "/" + hid + "/" + resp.result, function (json) {
                                        if ("outtime" == json.rs) {
                                        } else if ("fail" == json.rs) {
                                        } else {
                                            alert("上传成功");
                                            window.location.href = "index4.html";
                                        }
                                    })
                                } else {
                                    alert("上传失败");
                                }
                            }
                        });

                    })
                })
            }


        })
    };

    $("button[name='searchbtn']").click(function () {
        var type = $("#searchtype").val();
        var finishTime = $("#searchfinishTime").val();
        getdata(0,type,finishTime);
    })



        

})
