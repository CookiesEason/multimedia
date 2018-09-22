$(function(){
    /* 新建标签 */
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
	$('.add-tag-btn').click(function(){
		$('.tag-name input').val('');
		$('.tag-small-img .small img').css({
			'display':'none'
		}).attr('src','');
		base64_file=[];
		photoLength=-1;
	})
	$('.send-msg').click(function(){
			var tagNameInput=$('.tag-name input').val();
			var smallImgSrc=$('.tag-small-img .small img').attr('src');
			if (tagNameInput=='' || smallImgSrc=='') {
				layer.msg('所有内容必须填写')
			}
			else{
				layer.msg('新建成功');
				base64_file=[];
				photoLength=-1;
				$('.tag-name input').val('');
				$('.tag-small-img .small img').css({
					'display':'none'
				}).attr('src','')
			}
	})
	/* 图片放大 */
	$('.tag-small-img .browse-show img').click(function(){
		var con=$(this).closest('.browse-show');
		layer.photos({
		  photos: con,
		  anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
		}); 
	})
	/* 删除标签 */
	$('.btn-delete').click(function(){
		var thisEl=$(this).closest('tr');
		layer.confirm('是否删除此条标签？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
		  	thisEl.remove();
		  	layer.msg('删除成功');
		});
		
	})
})