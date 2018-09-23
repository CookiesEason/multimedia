$(function(){
	$(document).on('click','#power-top-bottom',function(){
		$(this).removeAttr('id');
		$(this).attr('id','power-bottom-top');
		$(this).text('按权限从低到高');
	})
	$(document).on('click','#power-bottom-top',function(){
		$(this).removeAttr('id');
		$(this).attr('id','power-top-bottom');
		$(this).text('按权限从高到低');
	})
	$(document).on('click','.btn-delete',function(){
		var this_El=$(this);
		layer.confirm('是否删除此用户信息？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			axios({
				'url':'/api/admin/users/'+this_El.parent().data('id'),
				'method':'delete'
			}).then(function () {
                this_El.closest('tr').remove();
                layer.msg('删除成功')
            });
		});
	})
	$('#power-control .btn-save').click(function(){
			let role = $(this).closest('#power-control').find('select').val();
			if (role==="开发者"){
				role = "ROLE_ADMIN"
			} else if (role==="图文管理员"){
				role = "ROLE_IMGTXT_ADMIN"
			}else if (role==="影像管理员") {
				role = "ROLE_VIDEO_ADMIN"
			}else {
				role = "ROLE_SENIOR_USER"
			}
			let f = new FormData();
			f.append('userId',$(this).data('id'));
			f.append('role',role);
			axios({
				'url':'/api/admin/users/changeRole',
				'method':'post',
				'data':f
			}).then(function () {
                layer.msg('设置权限成功');
                $('#power-control').modal('hide');
                window.location.reload()
            });
	});

	$(document).on('click','.btn-kick',function(){
		var thisEl=$(this);
		layer.confirm('是否封号此用户', {
			btn: ['确定','取消'] //按钮
		}, function(){
			axios.post('/api/admin/users/enable/'+thisEl.parent().data('id')).then(function () {
                thisEl.removeClass('btn-click glyphicon-ban-circle').addClass('btn-check glyphicon-share-alt');
                layer.msg('封号成功', {icon: 1});
            })
		});
	})
	$(document).on('click','.btn-check',function(){
		var thisEl=$(this);
		layer.confirm('是否解封该用户？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
            axios.post('/api/admin/users/enable/'+thisEl.parent().data('id')).then(function () {
                layer.msg('解封成功');
                thisEl.removeClass('btn-check glyphicon-share-alt').addClass('btn-kick glyphicon-ban-circle');
            })
		});
	})
    $('#addUser .btn-save').click(function(){
    	var account=$('#account').val();
    	var password=$('#password').val();
		console.log(account+password)
    	axios({
			'url':'/api/admin/users/addAdmin',
			'method':'post',
			'data':{
				'username':account,
				'password':password
			}
		}).then(function (res) {
			if (res.data.code=="1"){
                layer.msg('注册用户成功');
                $('#addUser').modal('hide');
                window.location.reload()
			} else {
                layer.msg(res.data.msg);
			}

        })
    })
    /* 账号密码格式判断 */
    var emailCheck = /^[a-zA-Z0-9_-]+((@(yeah)+(\.(net)))|(@(163|126|qq|sina)+(\.(cn|com)+)))+$/
    var phoneCheck = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    var passwordCheck = /^[0-9a-zA-Z_]{6,18}$/;
    /* 账号判断 */
    $('#account').blur(function(){
    	var accountStr = $(this).val();
    	var parentEl=$(this).closest('.form-group')
    	if (phoneCheck.test(accountStr) || emailCheck.test(accountStr)) {
    		parentEl.addClass('has-feedback').addClass('has-success');
    		$('#account').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span><label class="control-label col-sm-12 prompt" for="account">账号格式正确</label>')
    	}
    	else{
    		parentEl.addClass('has-feedback').addClass('has-error');
    		$('#account').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span><label class="control-label col-sm-12 prompt" for="password">账号格式错误</label><span id="inputSuccess3Status" class="sr-only">(error)</span>')
    	}
    })
    $('#account').focus(function(){
    	var parentEl=$(this).closest('.form-group');
    	parentEl.removeClass('has-success').removeClass('has-feedback').removeClass('has-error');
    	parentEl.find('span').remove();
    	parentEl.find('.prompt').remove();
    	parentEl.find('.only-success').remove();
    })
    /* 密码判断 */
    $('#password').blur(function(){
    	var passwordStr = $(this).val();
    	var parentEl=$(this).closest('.form-group')
    	if (passwordCheck.test(passwordStr)) {
    		parentEl.addClass('has-feedback').addClass('has-success');
    		$('#password').after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span><label class="control-label col-sm-12 prompt" for="account">密码格式正确</label>')
    	}
    	else{
    		parentEl.addClass('has-feedback').addClass('has-error');
    		$('#password').after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span><label class="control-label col-sm-12 prompt" for="password">密码格式错误</label><span id="inputSuccess3Status" class="sr-only">(error)</span>')
    	}
    })
    $('#password').focus(function(){
    	var parentEl=$(this).closest('.form-group');
    	parentEl.removeClass('has-success').removeClass('has-feedback').removeClass('has-error');
    	parentEl.find('span').remove();
    	parentEl.find('.prompt').remove();
    	parentEl.find('.only-success').remove();
    	parentEl.find('.only-error').remove();
    })
    /* 添加用户标签 */
    $('.add-tag-btn').click(function(){
    	var parentEl=$('#account').closest('.form-group');
    	var parentEl2=$('#password').closest('.form-group');
    	parentEl.removeClass('has-success').removeClass('has-feedback').removeClass('has-error');
    	parentEl.find('span').remove();
    	parentEl.find('.prompt').remove();
    	parentEl.find('.only-success').remove();
    	parentEl.find('.only-error').remove();

    	parentEl2.removeClass('has-success').removeClass('has-feedback').removeClass('has-error');
    	parentEl2.find('span').remove();
    	parentEl2.find('.prompt').remove();
    	parentEl2.find('.only-success').remove();
    	parentEl2.find('.only-error').remove();
    	$('#account').val('');
    	$('#password').val('');
    })
})