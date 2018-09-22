$(function(){
	/* 操作面板 */
	$(document).on('click','.btn-up',function(){
		var this_El=$(this);
		layer.confirm('是否将此专题内容上架首页？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
		  layer.msg('操作成功');
		  this_El.removeClass('btn-off glyphicon-remove').addClass('btn-up glyphicon-ok');
		});
	})

	$(document).on('click','.btn-off',function(){
		var this_El=$(this);
		layer.confirm('是否将此专题内容撤出首页轮播？', {
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
    /* 修改样式 */
    $('.btn-revise').click(function(){

    })
	/* 新建标题内容 */
    var base64_file=[];
	var photoLength=-1;
	var photoTest=/\.(gif|jpg|jpeg|png|gif|jpg|png)$/i;
	$('.upload-img').change(function(){
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
	            thisElement.closest('div').find('img').css({
	            	'display':'block'
	            })
	            thisElement.closest('div').find('img').attr('src',this.result)
	        }
		}
	})

	$('.send-msg').click(function(){
			var tagNameInput=$('.tag-name input').val();
			var tagTextInput=$('.tag-text textarea').val();
			var smallImgSrc=$('.tag-small-img .small img').attr('src');
			var bigImgSrc=$('.tag-small-big .big img').attr('src');
			if (tagNameInput=='' || tagTextInput=='' || smallImgSrc=='' || bigImgSrc=='') {
				layer.msg('所有内容必须填写')
			}
			else{
				layer.msg('新建成功');
				base64_file=[];
				photoLength=-1;
			}
	})
	$('.revise-msg').click(function(){
		layer.msg('修改成功');
		base64_file=[];
		photoLength=-1;
	})
	/* 展示图片 */
	$('.browse-show img').click(function(){
		parentEl=$(this).closest('.browse-show');
		layer.photos({
			photos: parentEl,
  		    anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
		})
	})
	/* 清空样式 */
	$('.add-tag-btn').click(function(){
		$('.add-tag-input').val('');
		$('.add-tag-textarea').val('');
		$('.upload-small-img').attr('src','').css({
			'display':'none'
		});
		$('.upload-big-img').attr('src','').css({
			'display':'none'
		});
	})
})