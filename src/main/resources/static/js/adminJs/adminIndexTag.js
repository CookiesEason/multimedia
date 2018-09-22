$(function(){
	/* 日期选择 插件 */
	$('.dataTime').datetimepicker({
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
	$('.image-upload').hover(function(){
		$(this).find('a').css({
			'display':'block'
		})
	},function(){
		$(this).find('a').css({
			'display':'none'
		})
	})
	/*  修改图片 */
	var base64_file=[];
	var photoLength=-1;
	var photoTest=/\.(gif|jpg|jpeg|png|gif|jpg|png)$/i;
	$('.image-upload input').change(function(){
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
	            thisElement.closest('.image-upload').css({
	            	'background':'url('+this.result+') center'
	           })	
	        }
		}
	})
	$('.send-btn').click(function(){
			layer.msg('修改成功');
			base64_file=[];
			photoLength=-1;
	})
})