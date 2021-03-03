//管理员登录
$(function(){
	//验证账号
	$('.isLogin').bind('click',function(){
		var adminName=$('.LoginUserInputadminName').val();
		var adminPassword=$('.LoginUserInputadminPassword').val();
		var Formvalue=new FormData();
		Formvalue.append('adminName',adminName);
		Formvalue.append('adminPassword',adminPassword);
		$.ajax({
			url:'/o2o/superadmin/Login',
			type:'POST',
			data:Formvalue,
			dataType:'json',
			contentType:false,
			cache:false,
			processData:false,
			success:function(data){
				if(data.success){
					document.location.href=data.url;
				}else{
					//2020.12.5
					//可以使用jQuery的弹窗,时间够在完善
					alert(data.errMsg);
				}
			},
			error:function(data){
				//查看什么错误
				console.log(data);
			}
		})
	})
})