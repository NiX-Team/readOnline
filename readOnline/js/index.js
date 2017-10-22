

function start() {
    $.ajax({
        type: 'get',
        url: "/kiss/all" ,
        dataType:'json',
        success: function (backData) {
            var content = "";
            for (var i = 0;i < backData.length;i ++) {
                content += "<div class='txt_box col-sm-6 col-md-4'>" +
                    "<div class='thumbnail'>" +
                    "<a href='/kiss/chapter/" + backData[i].sn + "'><img class='txt_log' style='height: 330px' src='/image/" + backData[i].coverName + "'/>" +
                    "<div class='txt_name caption'>" + backData[i].name + "</div>" +
                    "<input name='txtSn' type='text' value='" + backData[0].sn + "' hidden/>" +
                    "</a></div></div>";
            }
            var txtTable = $("#show_txt_table");
            txtTable.html(content);
            console.log(content);
        } ,
        error:function() {
        }
    });
}
start();