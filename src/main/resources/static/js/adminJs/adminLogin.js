$(function(){
	$('.btn-login').click(function(){
		var account=$('.account').val();
		var password=$('.password').val();
		if (account=='') {
			$('.account').addClass('warning-border');
			$('.account-msg').addClass('msg-active');
		}
		if (password=='') {
			$('.password').addClass('warning-border');
			$('.password-msg').addClass('msg-active');
		}
	})
	$('.account').blur(function(){
		if (!$(this).val()=='') {
			$(this).removeClass('warning-border');
			$('.account-msg').removeClass('msg-active');
		}
	})
	$('.password').blur(function(){
		if (!$(this).val()=='') {
			$(this).removeClass('warning-border');
			$('.password-msg').removeClass('msg-active');
		}
	})
	// $('.btn-login').click(function(){
	// 	$(window).attr('location','adminHomepage.html');
	// })
})