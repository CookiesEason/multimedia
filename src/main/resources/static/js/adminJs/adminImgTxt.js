$(function(){
	$(document).on('click','.btn-off',function(){
		var this_El=$(this);
		layer.confirm('是否将此图文内容撤出热门？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
		  layer.msg('操作成功');
		  this_El.removeClass('btn-off glyphicon-ok').addClass('btn-up glyphicon-remove');
		});
	})
	$(document).on('click','.btn-delete',function(){
		var this_El=$(this);
		layer.confirm('是否删除此图文内容？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			this_El.closest('tr').remove();
			layer.msg('删除成功')
		});
	})
	$(document).on('click','.btn-up',function(){
		$('#hot-selected').modal('show');
		$('#hot-selected').find('img').css({
			'display':'none'
		});
		thisElement=$(this);
		$('#hot-selected').data('clickBtn',thisElement);
		$('.graph-input').val('');
		$('.dateTime').val('');
	})
    /* 图片处理 */
    var base64_file=[];
	var photoLength=-1;
	var photoTest=/\.(gif|jpg|jpeg|png|gif|jpg|png)$/i;
	$('.img-upload-box input').change(function(){
		var uploadfile = $(this).val();
		var thisElement=$(this);
		if (!photoTest.test(uploadfile)) {
			layer.msg('请上传正确的文件');
		}
		else{
			photoLength++;
			var file = this.files[0];
       		var reader = new FileReader(); 
       		reader.readAsDataURL(file); 
	        reader.onload = function(e){ 
	            base64_file[photoLength] = this.result;
	            var image = new Image();
	            image.src = base64_file[photoLength];
	            var width = image.width;
	            var height = image.height
	            thisElement.closest('.img-upload-box').find('img').css({
	            	'display':'block'
	            })
	            thisElement.closest('.img-upload-box').find('img').attr('src',this.result)
	        }
		}
	})

	$('.send-msg').click(function(){
			var graphInput=$('.graph-input').val();
			var timeInput=$('.dateTime').val();
			var imgSrc=$('.img-upload-box').find('img').attr('src');
			if (graphInput=='' || timeInput=='' || imgSrc=='') {
				layer.msg('所有内容必须填写')
			}
			else{
				layer.msg('修改成功');
				base64_file=[];
				photoLength=-1;
				$('#hot-selected').data('clickBtn').removeClass('glyphicon-remove').addClass('glyphicon-ok');
				$('#hot-selected').data('clickBtn').removeClass('btn-up').addClass('btn-off');
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