$(function(){
	$('.show-img-box li .mask').hover(function(){
		$(this).stop().animate({
			'opacity':1
		},150)
	},function(){
		$(this).stop().animate({
			'opacity':0
		},150)
	})
	$('.btn-delete').click(function(){
		var thisEl=$(this);
		layer.confirm('是否删除本条影像内容？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
		  layer.msg('删除成功');
		  thisEl.closest('tr').remove();
		});
	})
	// 播放器
	var player =new TCPlayer("banner-video", { // player-container-id 为播放器容器ID，必须与html中一致
			fileID: "5285890781646748172", // 请传入需要播放的视频filID 必须
			appID: "1253231183", // 请传入点播账号的appID 必须
			autoplay: false //是否自动播放
			//其他参数请在开发文档中查看
	});
})