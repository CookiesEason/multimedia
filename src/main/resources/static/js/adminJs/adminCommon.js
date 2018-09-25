const user = JSON.parse( localStorage.user);
$(".head-picture img").attr('src',user.headUrl);
if(user.role==="ROLE_ADMIN"){
    $(".head-msg").replaceWith("<span class=\"head-msg\">\n" +
        "<i class=\"glyphicon glyphicon-unchecked\"></i>" +
        " 管理员" +
        "</span>")
    $(".welcome-msg").html("欢迎您 管理员")
}else if (user.role==="ROLE_VIDEO_ROLE") {
    $(".head-msg").replaceWith("<span class=\"head-msg\">\n" +
        "<i class=\"glyphicon glyphicon-unchecked\"></i>" +
        " 视频管理员" +
        "</span>")
    $(".welcome-msg").html("欢迎您 视频管理员")
}else {
    $(".head-msg").replaceWith("<span class=\"head-msg\">\n" +
        "<i class=\"glyphicon glyphicon-unchecked\"></i>" +
        " 图文管理员" +
        "</span>")
    $(".welcome-msg").html("欢迎您 图文管理员")
}
$(".login-out").click(function () {
    localStorage.user = null;
    axios.get('/api/logout').then(function () {
        location.href = "/adminLogin"
    });
});
axios.get('/api/admin/messages/count').then(function (res) {
    $(".remind-msg-bubble").html(res.data.data)
});
$(".remind-msg").click(function () {
    axios.get('/api/admin/messages').then(function (res) {
        $(".remind-ul li").remove()
        for (let i=0;i<3;i++) {
            if (res.data.data.objectList[i].type=="confirm") {
                $(".remind-ul").append(" <li class=\"web-msg\">\n" +
                    "<a href=\"/admin/examine\">\n" +
                    "<i class=\"glyphicon glyphicon-cloud-upload\"></i>\n" +
                    "您有新的视频需要审核\n" +
                    "<span>\n" +
                    res.data.data.objectList[i].date +
                    "</span>  \n" +
                    "</a>   \n" +
                    "</li>");
            }else {
                $(".remind-ul").append(" <li class=\"web-msg\">\n" +
                    "<a href=\"/admin/report\">\n" +
                    "<i class=\"glyphicon glyphicon-cloud-upload\"></i>\n" +
                    "您收到了新的举报信息\n" +
                    "<span>\n" +
                    res.data.data.objectList[i].date +
                    "</span>  \n" +
                    "</a>   \n" +
                    "</li>");
            }
        }
    });
});