$(function(){
	/* 下架按钮 */
	$(document).on('click','.off-shelf-btn',function(){
		var thisElement = $(this).closest('tr');
		var className=thisElement.attr('class');	
		layer.confirm('是否下架此热门内容？', {
		  btn: ['确定','取消'] //按钮
		}, function(){	
		  thisElement.remove();
		  $('.'+className).removeClass('success');
		  $('.'+className).find('.btn-success').attr('disabled',false)
		  layer.msg('操作成功');
		});
	})
	/* 图片展示 */
	$('.show-img-box li img').click(function(){
		var con = $(this).closest('li');
		layer.photos({
			photos:con,
			anim:5
		})
	})
	/* 上架按钮 */
	$(document).on('click','.up-shelf-btn',function(){
		var afterClass ='hot-active-'+$(this).closest('tr').find('td').eq(0).text();
		var btnEl=$(this);
		var trEl=$(this).closest('tr');
		var tdEl=$(this).closest('tr').find('td');
		var td_1=tdEl.eq(0).text();
		var td_2=tdEl.eq(1).text();
		var td_3=tdEl.eq(2).text();
		var td_4=tdEl.eq(3).text();
		var td_5=tdEl.eq(4).text();	
		layer.confirm('是否将此内容放上热门？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			var hotLength = $('.box-container-hot').find('table').find('tbody').find('tr').length;
			if (hotLength<3) {
				btnEl.attr('disabled','disabled')
				trEl.addClass(afterClass)
				trEl.addClass('success');
				$('.box-container-hot').find('tbody').append('<tr class='+afterClass+'><td>'+td_1+'</td><td>'+td_2+'</td><td>'+td_3+'</td><td>'+td_4+'</td><td>'+td_5+'</td><td><button type="button" class="btn btn-info btn-sm">浏览</button> <button type="button" class="btn btn-danger btn-sm off-shelf-btn">下架</button></td></tr>');
				layer.msg('操作成功');

			}
			else{
				layer.msg('热门内容达到上限')
			}
		});
	})
})