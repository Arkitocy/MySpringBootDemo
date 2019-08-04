$(document).ready(function () {
    $.getJSON("getAll", function (json) {
        console.log(json);
        $("#tbodymainbtn").empty();
        for (var i = 0; i < json.length; i++) {
            $("#tbodymainbtn").append(
                "<tr id='tridval" + i + "'>"
                + "<td>" + json[i].id
                + "</td>"
                + "<td>" + json[i].username
                + "</td>"
                + "<td>" + json[i].password
                + "</td>"
                + "<td><button type='button' name='btn001' class='btn btn-info btn-sm' id='btn1" + i + "'>修改</button>" + "&nbsp&nbsp<button type='button' name='btn003' class='btn btn-danger btn-sm' id='btn3" + json[i].id + "'>删除</button>"
                + "</td></tr>"
            );
            $("#tbodymainbtn").append(
                "<tr style='display:none' id='tridval2" + i + "'><form>"
                + "<td><input type='text'  value='" + json[i].id
                + "'/></td>"
                + "<td><input type='text' id='name2" + i + "'  value='" + json[i].username
                + "'/></td>"
                + "<td><input type='text' id='password2" + i + "' value='" + json[i].password
                + "'/></td>"
                + "<td><button type='button' name='btn002'  class='btn btn-primary btn-sm' id='btn2" + i + "'>保存</button>"
                + "</td></form></tr>"
            );
        }


        //jquery 样式查找 “点+样式名字”
        $("button[name='btn001']").click(function () {
            var id = this.id;
            //从最后面开始，截取一位
            var numb = id.slice(4);
            console.log("****************" + id);
            $("#tridval" + numb).hide();
            $("#tridval2" + numb).show();

        });


        $("button[name='btn002']").click(function () {
            var id = this.id;
            //从最后面开始，截取一位
            var numb = id.slice(4);
            console.log("****************" + id);
            var nval = $("#name2" + numb).val();
            var aval = $("#password2" + numb).val();
            console.log("********name2********" + nval);
            console.log("********age2********" + aval);

            $("#tridval" + numb).show();
            $("#tridval2" + numb).hide();


        });

        $("button[name='btn003']").click(function () {
            var uid =this.id;
            var numb = uid.slice(4);
            console.log(numb);
            $.getJSON("delete",{id:numb},function (json) {
                console.log("delete-----",json);
            })
        })


    });

});
