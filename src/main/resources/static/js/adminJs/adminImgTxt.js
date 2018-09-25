$(function(){
	$(document).on('click','.btn-delete',function(){
		var this_El=$(this);
		layer.confirm('是否删除此图文内容？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			axios({
				'url':'/api/admin/articles/'+this_El.parent().data('id'),
				'method':'delete'
			}).then(function () {
                this_El.closest('tr').remove();
                layer.msg('删除成功')
            })
		});
	})
	$(document).on('click','.btn-revoke',function(){
		var this_El=$(this);
		layer.confirm('是否撤销此条图文？', {
			btn: ['确定','取消'] //按钮
		}, function(){
			this_El.closest('tr').remove();
			layer.msg('该图文已被撤销', {icon: 1});
		}); 
	})
	/* 发布时间 */
	$('.dateTime').datetimepicker({
		format:'yyyy-mm-dd hh:ii:ss',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,   
        showMeridian: 1,
        pickerPosition: "bottom-right"
	})
	
})