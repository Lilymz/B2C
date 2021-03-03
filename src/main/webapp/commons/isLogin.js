function UserIsLogin(){
	var verifyLoginUser='/o2o/user/isLogin';
	$.getJSON(verifyLoginUser,function(data){
			if(data.success){
				MyHtml='<div class="content-block"><p><h3>余额：</h3><h4 style="position: relative!important;top: -64px!important;left: 116px!important;">'+data.userInfo.money+'</h4></p><a href="/o2o/shopcart/goShopCart"><p><span class="icon icon-cart"></span>&nbsp;&nbsp;<div style="position: relative!important; top: -51px!important; left: 43px!important;">购物车</div></a></p><a href="/o2o/user/goUserOrder?userId='+data.userInfo.userId+'"><p style="margin-bottom: 2rem!important;"><span class="icon icon-menu"></span><div style="position: relative!important;top: -81px!important;left: 42px!important;">我的订单</div></p></a><p><a href="#" class="close-panel">关闭</a></p></div>';
				$("#panel-js-demo").html(MyHtml);
			}else{
				alert(data.errMsg);
				document.location.href='/o2o/user/user_login';
				
			}
	})
}
function LoginIndexURL(){
	var verifyLoginUser='/o2o/user/isLogin';
	$.getJSON(verifyLoginUser,function(data){
			if(data.success){
				alert("已成功登录无须重复登录");
				document.location.href='/o2o/Page/index';
			}else{
				document.location.href='/o2o/user/user_login';
				
			}
	})
}
function LoginAdminIndexURL(){
	var verifyLoginAdmin='/o2o/admin/isLogin';
	$.getJSON(verifyLoginAdmin,function(data){
			if(data.success){
				alert("已成功登录无须重复登录");
				document.location.href=data.url;
			}else{
				document.location.href=data.url;
				
			}
	})
}

//用户注册or店家注册 1为用户二为店家
function registerUserORShopAdmin(number){
		var registerURL='';
		if(number==1){
			//用户注册
			registerURL='/o2o/user/userRegister';
		}else if(number==2){
			//店家注册
			registerURL='/o2o/shopadmin/shopAdminRegister';
		}
		var user={};
		user.email=$('#email').val();
		user.gender=$('#gender').val();
		user.userName=$('#userName').val();
		user.userPassword=$('#password').val()
		//提交表单
		$.ajax({
			url:registerURL,
			type:'POST',
			data:JSON.stringify(user),
			dataType:'json',
			contentType:"application/json;charset=utf-8",
			cache:false,
			success:function(data){
				if(data.success){
					alert(data.Msg);
					document.location.href=data.url;
				}else{
					alert(data.errMsg);
				}
			},
			error:function(data){
				console.log(data);
			}
		})
}