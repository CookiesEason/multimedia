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
            axios({
                'url':'/api/admin/videos/'+thisEl.parent().data('id'),
                'method':'delete'
            }).then(function () {
                thisEl.closest('tr').remove();
                layer.msg('删除成功')
            })
		});
	})
    // 撤销按钮
    $(document).on('click','.btn-send-to',function(){
        let t = $(this);
        var deMsg=$('.detail-reason').val();
        if(deMsg==''){
            layer.msg("内容为空,不能提交");
        }
        else{
            axios({
                'url':'/api/admin/videos/enable/'+t.data('id'),
                'method':'post',
                'params':{
                	'enable':'false',
                    'reason':deMsg
                }
            }).then(function () {
                $('#revoke-reason').modal('hide');
                layer.msg("发送成功");
                window.location.reload()
            })
        }
    })
	// 播放器
	// var player =new TCPlayer("banner-video", { // player-container-id 为播放器容器ID，必须与html中一致
	// 		fileID: "5285890781646748172", // 请传入需要播放的视频filID 必须
	// 		appID: "1253231183", // 请传入点播账号的appID 必须
	// 		autoplay: false //是否自动播放
	// 		//其他参数请在开发文档中查看
	// });
    let player=null;
    $(".btn-browse").click(function () {
    	console.log($(this).parent().data('fileid'))
        if (player==null){
            player =new TCPlayer("banner-video", { // player-container-id 为播放器容器ID，必须与html中一致
                fileID: $(this).parent().data('fileid'), // 请传入需要播放的视频filID 必须
                appID: "1253231183", // 请传入点播账号的appID 必须
                autoplay: false //是否自动播放
                //其他参数请在开发文档中查看
            });
		}else {
            player.loadVideoByID({
                fileID: $(this).parent().data('fileid'), // 请传入需要播放的视频 filID 必须
                appID: '1253231183', // 请传入点播账号的 appID 必须
            })
		}
    })
})