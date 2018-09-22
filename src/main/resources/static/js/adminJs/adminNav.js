$(function(){
	$('.main-menu li a').click(function(){
		$('.main-menu li a').removeClass('nav-active');
		$(this).addClass('nav-active');
	})	
	$(document).on('click','.nav-user-btn',function(){
		var ulHeight = $(this).find('ul').height()+45;
		$(this).stop(true,true).animate({
			'height':ulHeight
		},150,function(){
			$(this).removeClass('nav-user-btn');
			$(this).addClass('nav-user-btn-active');
			$(this).find('.glyphicon-plus').removeClass('glyphicon-plus').addClass('glyphicon-minus');
		})
	})
	/* 点击后列表高度计算*/
	$(document).on('click','.nav-user-btn-active',function(){
		$(this).stop(true,true).animate({
			'height':'45px'
		},150,function(){
			$(this).removeClass('nav-user-btn-active');
			$(this).addClass('nav-user-btn');
			$(this).find('.glyphicon-minus').removeClass('glyphicon-minus').addClass('glyphicon-plus');
		})
	})
	$('.user ul li a').click(function(event){
		event.stopPropagation();
	})
	$('.remind-ul').click(function(event){
		event.stopPropagation();
	})
	$('.msg-bell').click(function(event){
		event.stopPropagation();
		$('.remind-ul').removeClass('ul-active');
		$(this).find('ul').addClass('ul-active');
	})
	$('.msg-email').click(function(event){
		event.stopPropagation();
		$('.remind-ul').removeClass('ul-active');
		$(this).find('ul').addClass('ul-active');
	})
	$(document).click(function(){
		$('.remind-ul').removeClass('ul-active');
	})
	/* 筛选按钮功能 */
	/* 按热度 */
	$(document).on('click','#hot-top-bottom',function(){
		$(this).removeAttr('id');
		$(this).attr('id','hot-bottom-top');
		$(this).text('按热度从低到高');
	})
	$(document).on('click','#hot-bottom-top',function(){
		$(this).removeAttr('id');
		$(this).attr('id','hot-top-bottom');
		$(this).text('按热度从高到低');
	})
	/* 按发布时间 */
	$(document).on('click','#time-sooner-later',function(){
		$(this).removeAttr('id');
		$(this).attr('id','time-later-sooner');
		$(this).text('按发布时间从晚到早');
	})
	$(document).on('click','#time-later-sooner',function(){
		$(this).removeAttr('id');
		$(this).attr('id','time-sooner-later');
		$(this).text('按发布时间从早到晚');
	})
	/* 按ID */
	$(document).on('click','#ID-small-big',function(){
		$(this).removeAttr('id');
		$(this).attr('id','ID-big-small');
		$(this).text('按ID从大到小');
	})
	$(document).on('click','#ID-big-small',function(){
		$(this).removeAttr('id');
		$(this).attr('id','ID-small-big');
		$(this).text('按ID从小到大');
	})
})