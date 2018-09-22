$(function(){
	/* 图片展示 */
	$('.show-img-box li img').click(function(){
		var con = $(this).closest('li');
		layer.photos({
			photos:con,
			anim:5
		})
	})
	$('.btn-delete').click(function(){
		var thisEL=$(this);
		layer.confirm('是否删除被举报内容？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
		  thisEL.closest('tr').remove();
		  layer.msg('删除成功');
		});
	})
})