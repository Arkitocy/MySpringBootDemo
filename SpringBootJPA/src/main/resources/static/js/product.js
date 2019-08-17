$(document).ready(function () {
    var loginname;
    var loginid;
    var headname;
    var page = 0;
    $.post("product/showId", function (data) {
        loginname = data.username;
        if (loginname == null) {
            window.location.href = "login.html";
        }
        $("#username").text(loginname);
        $.getJSON("findUserID/" + loginname, function (data) {
            loginid = data.id;
            headname = data.head;
            $("#headimg").attr("src", "http://localhost:8080/SpringBootJPA/image/" + headname);
        })
    }, "json");
    $("button[name='searchbtn']").click(function () {
        $.getJSON("product/showByName", $("input[name='productname']").val(), function (json) {
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
                    + "</td></tr>"
                );
            }
        })
    })
    getdata(page);

    function getdata(pagen) {
        $.getJSON("product/showall/" + pagen, function (json) {
            if (pagen < json.totalPages) {
                $("#tbodymainbtn").empty();
                for (var i = 0; i < json.content.length; i++) {
                    $("#tbodymainbtn").append(
                        "<tr id='tridval" + i + "'>"
                        + "<td>" + json.content[i].id
                        + "</td>"
                        + "<td>" + json.content[i].name
                        + "</td>"
                        + "<td>" + json.content[i].category
                        + "</td>"
                        + "<td>" + json.content[i].productiondate
                        + "</td>"
                        + "<td>" + json.content[i].outdate
                        + "</td>"
                        + "<td>" + json.content[i].price
                        + "</td>"
                        + "<td>" + json.content[i].amount
                        + "</td>"
                        + "</td></tr>"
                    );
                }
            }
            var pagenum = json.totalPages;
            $(".pagination").empty();
            $(".pagination").append('<li class=""><a class="page-link" href="#" id="firstpage">首页</a></li>');
            $(".pagination").append('<li class=""><a class="page-link" href="#" id="previosepage">Previous</a></li>');
            for (var j = 0; j < pagenum; j++) {
                $(".pagination").append(' <li class="page-item" id="page' + j + '"><a class="page-link" href="#">' + (j + 1) + '</a></li>');
            }
            $(".pagination").append(' <li class=""><a class="page-link" href="#" id="nextpage">Next</a></li>');
            $(".pagination").append('<li class=""><a class="page-link" href="#" id="lastpage">尾页</a></li>');

            $(".page-item").removeClass("active");
            $("#page" + pagen).addClass("active");
            $("#nextpage").click(function () {
                var pagenum1 = Number(pagen) + Number(1);
                if (pagen < pagenum - 1) {
                    getdata(pagenum1);
                }
            })
            $("#previosepage").click(function () {
                var pagenum2 = Number(pagen) - Number(1);
                if (pagen > 0) {
                    getdata(pagenum2);
                }
            })

            $("#firstpage").click(function () {
                getdata(0);
            })
            $("#lastpage").click(function () {
                getdata(pagenum);
            })

            $(".page-item").click(function () {
                page = this.id.substr(4);
                getdata(page);
            })
        })
    }
});
