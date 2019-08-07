$(document).ready(function () {
    $.getJSON("product/showall", function (json) {
            console.log("1");
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
        }
    )
});
