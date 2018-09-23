$(function(){
	$('.add-tag-btn').click(function(){
		$('.tag-name input').val('');
	})
	$('.send-msg').click(function(){
		var tagNameInput=$('.tag-name input').val();
		var tagBelong=$('.tag-belong').val();
		if (tagNameInput=='') {
			layer.msg('所有内容必须填写')
		}
		else{
			axios({
				'url':'/api/admin/addSmallTags',
				'method':'post',
				'params':{
					'tag':tagBelong,
					'smallTag':tagNameInput
				}
			}).then(function (res) {
				if (res.data.code=="0"){
                    layer.msg(res.data.msg)
					return
				}
                layer.msg('新建成功');
                window.location.reload()
            })

			//$('table tbody').append('<tr><td>1</td><td>'+tagNameInput+'</td><td>1320</td><td>'+tagBelong+'</td><td>2017-09-10 21:18:37</td><td><button class="btn btn-primary btn-browse glyphicon glyphicon-eye-open btn-xs" data-toggle="modal" data-target="#browseBox"></button> <button class="btn btn-danger btn-delete glyphicon glyphicon-trash btn-xs"></button></td>')
		} 
	})
	/* 删除标签 */
	$(document).on('click','.btn-delete',function(){
		let t = $(this)
		var thisEl=$(this).closest('tr');
		layer.confirm('是否删除此条标签？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			axios({
				'url':'/api/admin/smallTags/'+t.data('id'),
				'method':'delete'
			}).then(function () {
                thisEl.remove();
                layer.msg('删除成功');
            })
		});
	})
})