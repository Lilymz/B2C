$(function(){
	//存下初始的标签
	var tr=null;
	//状态转化函数
	function UserEnableStatus(data){
		if(data==0){
			return '待审核';
		}else if(data==1){
			return '通过审核';
		}else if(data==2){
			return '禁用';
		}else{
			return '不规范用户';
		}
	}
	//用户是否登录验证
	$.getJSON('/o2o/superadmin/isLogin',function(data){
		if(data.success){
			alert(data.errMsg);
			document.location.href=data.url;
		}
	})
	//退出操作
	$('.logout').bind('click',function(){
		$.getJSON('/o2o/superadmin/logout',function(data){
			if(data.success){
				alert("成功退出！");
				document.location.href=data.url;
			}
		})
	})
	//默认加载所有用户
	$.getJSON('/o2o/superadmin/getAllShopAdmin',function(data){
		if(data.success){
			$('.UserName').html('你好,'+data.adminInfo.adminName);
			tdHtml='';
			data.ShopAdmins.map(function(item,index){
				tdHtml+='<tr><td class="userId">'+
				item.userId+'</td><td>'+
				item.userName+'</td><td>'+
				item.gender+'</td><td>'+
				item.email+'</td><td>'+
				getTime(item.createTime)+'</td><td><div class="status" style="background: #35A097;color: white;">'+
				UserEnableStatus(item.enableStatus)+'</div><div class="PublicTableBtnIcon Color3Btn Js_edit"><i class="iconfont icon-tubiaozhizuomobanyihuifu-"></i><span>编辑</span></div></td><td><div class="PublicTableBtnIcon Color4Btn Js_delete"><i class="iconfont icon-shanchu"></i><span>删除</span></div></td></tr>';
			});
			tr=tdHtml;
			$('tbody').html(tdHtml);
			}
		})
	//搜索用户
	$('.Color1Btn ').bind('click',function(){
		//获取输入值	
		var shopName=$('.shopNameInput').val();
		if(shopName==""){
			$('tbody').html(tdHtml);
		}else{
			$('tbody').html('');
			$.ajax({
				url:'/o2o/superadmin/shopSearchByName',
				type:'POST',
				data:JSON.stringify(shopName),
				dataType:'json',
				contentType:false,
				cache:false,
				processData:false,
				success:function(data){
					var tempHtml='';
					if(data.success){
						data.ShopAdmins.map(function(item,index){
							tempHtml+='<tr><td class="userId">'+
							item.userId+'</td><td>'+
							item.userName+'</td><td>'+
							item.gender+'</td><td>'+
							item.email+'</td><td>'+
							getTime(item.createTime)+'</td><td><div class="status" style="background: #35A097;color: white;">'+
							UserEnableStatus(item.enableStatus)+'</div><div class="PublicTableBtnIcon Color3Btn Js_edit"><i class="iconfont icon-tubiaozhizuomobanyihuifu-"></i><span>编辑</span></div></td><td><div class="PublicTableBtnIcon Color4Btn Js_delete"><i class="iconfont icon-shanchu"></i><span>删除</span></div></td></tr>';
						});
						$('tbody').html(tempHtml);
					}else{
						alert(data.errMsg);
					}
				},
				error:function(data){
					//查看什么错误
					console.log(data);
				}
			})
		}
	})
	//将加载出来的组件委派给父类来执行点击操作
	$('tbody').delegate('.PublicTableBtnIcon','click',function(){
		$(".Js_closeBtn").click(function () {
			   $(".adduser,.f_delete").fadeOut(200);
		});
		$(".Js_edit").click(function () {
			   $(".adduser").fadeIn(200);
			   //确定需要修改的用户id
			   var currentId=$(this).parent().prev().prev().prev().prev().prev().html();
			   
			   $('.publicf_btn1').bind('click',function(){	
					//获取输入状态值
					var shopEnableStatus=$('.f_p_input').val();
					var Formvalue=new FormData();
					Formvalue.append('currentId',currentId);
					Formvalue.append('shopEnableStatus',shopEnableStatus);
					$.ajax({
							url:'/o2o/superadmin/updateShopByEnableStatus',
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
									alert(data.errMsg);
								}
							},
							error:function(data){
								console.log(data);
							}
							
					})
				})
		});
		$(".Js_delete").click(function () {
		       $(".f_delete").fadeIn(200);
		       //确定当前需要删除的用户ID
		       var currentId=$(this).parent().prev().prev().prev().prev().prev().prev().html();
		       $('.f_pormatBtn1 ').bind('click',function(){
		    	   $.ajax({
						url:'/o2o/superadmin/deleteShopAdminByuserId',
						type:'POST',
						data:JSON.stringify(currentId),
						dataType:'json',
						contentType:false,
						cache:false,
						processData:false,
						success:function(data){
							if(data.success){
								document.location.href=data.url;
							}else{
								alert(data.errMsg);
							}
						},
						error:function(data){
							console.log(data);
						}
			       })
		       })
		});
	})
	
})