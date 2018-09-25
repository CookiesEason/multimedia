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
	// 撤销按钮
    $(document).on('click','.btn-send-to',function(){
    	let t = $(this)
        var deMsg=$('.detail-reason').val();
        if(deMsg==''){
            layer.msg("内容为空,不能提交");
        }
        else{
        	axios({
				'url':'/api/admin/articles/enable/'+t.data('id'),
				'method':'post',
				'params':{
					'reason':deMsg
				}
			}).then(function () {
                $('#revoke-reason').modal('hide');
                layer.msg("发送成功");
                window.location.reload()
            })
        }
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