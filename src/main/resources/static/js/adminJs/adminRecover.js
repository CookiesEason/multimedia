$(function(){
    // 播放器
	// var player =new TCPlayer("banner-video", { // player-container-id 为播放器容器ID，必须与html中一致
    //     fileID: "5285890781646748172", // 请传入需要播放的视频filID 必须
    //     appID: "1253231183", // 请传入点播账号的appID 必须
    //     autoplay: false //是否自动播放
    //     //其他参数请在开发文档中查看
    // });
    let player=null;
    $(".btn-browse").click(function () {
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
    $('.btn-delete').click(function(){
		var thisEl=$(this);
		layer.confirm('是否删除此条内容？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
		axios({
			'url':'/api/admin/videos/'+thisEl.parent().data('id'),
			'method':'delete'
		}).then(function () {
			layer.msg('删除成功');
			thisEl.closest('tr').remove();
		})
		});
    })
    $('.btn-repeat').click(function(){
		var thisEl=$(this);
		layer.confirm('是否恢复本条内容？（执行此操作前请仔细确认未通过原因）', {
		  btn: ['确定','取消'] //按钮
		}, function(){
		 axios({
			 'url':'/api/admin/videos/enable/'+thisEl.parent().data('id'),
			 'method':'post',
			 'params':{
			 	'enable':'true'
			 }
		 }).then(function () {
             layer.msg('恢复成功');
             thisEl.closest('tr').remove();
         })
		});
	})
})