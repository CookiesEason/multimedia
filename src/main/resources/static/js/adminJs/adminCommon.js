const user = JSON.parse( localStorage.user);
$(".head-picture img").attr('src',user.headUrl);
if(user.role==="ROLE_ADMIN"){
    $(".head-msg").replaceWith("<span class=\"head-msg\">\n" +
        "<i class=\"glyphicon glyphicon-unchecked\"></i>" +
        " 管理员" +
        "</span>")
}else if (user.role==="ROLE_VIDEO_ROLE") {
    $(".head-msg").replaceWith("<span class=\"head-msg\">\n" +
        "<i class=\"glyphicon glyphicon-unchecked\"></i>" +
        " 视频管理员" +
        "</span>")
}else {
    $(".head-msg").replaceWith("<span class=\"head-msg\">\n" +
        "<i class=\"glyphicon glyphicon-unchecked\"></i>" +
        " 图文管理员" +
        "</span>")
}
$(".login-out").click(function () {
    localStorage.user = null;
    axios.get('/api/logout').then(function () {
        location.href = "/adminLogin"
    });
});