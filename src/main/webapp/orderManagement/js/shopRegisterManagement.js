$(function(){
	//状态转化函数
	function NotNUll(data,flag){
		if(data==0&&flag==1){
			return '0';
		}else if(flag==2){
			return '这个管理员很懒，无任何建议';
		}
		return data;
	}
	function enable(data){
		if(data==0){
			return '待审核...';
		}else if(data==1){
			return '审核通过';
		}else{
			return '禁用';
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
	$.getJSON('/o2o/superadmin/getshoplistEnable',function(data){
		if(data.success){
			console.log(data.shopList);
			tdHtml='';
			data.shopList.map(function(item,index){
				tdHtml+='<tr><td shopId="'+
				item.shopId+'">'+
				item.shopName+'</td><td>'+
				item.shopDesc+'</td><td>'+
				item.shopAddr+'</td><td>'+
				item.phone+'</td><td>'+
				getTime(item.createTime)+'</td><td><div class="priority" style="background: #35A097;color: white;">'+
				NotNUll(item.priority,1)+'</div><div class="PublicTableBtnIcon Color3Btn Js_edit_priority"><i class="iconfont icon-tubiaozhizuomobanyihuifu-"></i><span>编辑</span></div></td><td><div class="status" style="background: #35A097;color: white;">'+
				enable(item.enableStatus)+'</div><div class="PublicTableBtnIcon Color3Btn Js_edit_enable"><i class="iconfont icon-tubiaozhizuomobanyihuifu-"></i><span>编辑</span></div></td><td><div class="status" style="background: #35A097;color: white;">'+
				NotNUll(item.advice,2)+'</div><div class="PublicTableBtnIcon Color3Btn Js_edit_advice"><i class="iconfont icon-tubiaozhizuomobanyihuifu-"></i><span>编辑</span></div></td></tr>';
			});
			$('tbody').html(tdHtml);
			}
		})
	//将加载出来的组件委派给父类来执行点击操作
	$('tbody').delegate('.PublicTableBtnIcon','click',function(){
		//关闭按钮
		$(".Js_closeBtn").click(function () {
			   $(".adduser,.f_delete").fadeOut(200);
			   $('.f_p_input').val('');
		});
		//编辑优先级
		$(".Js_edit_priority").click(function () {
			$('.f_Head').html('设置店铺优先级');
			$('.f_alonediv>.f_alone_name').html("范围1-1000");
			   $(".adduser").fadeIn(200);
			   //确定需要修改的用户id
			   var currentId=$(this).parent().prev().prev().prev().prev().prev().attr('shopId');
			   
			   $('.publicf_btn1').bind('click',function(){	
					//获取输入优先级
					var updateData=$('.f_p_input').val();
					var flag=1;
					var Formvalue=new FormData();
					Formvalue.append('currentId',currentId);
					Formvalue.append('updateData',updateData);
					Formvalue.append('flag',flag);
					$.ajax({
							url:'/o2o/superadmin/updateShopByShopId',
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
		//编辑店铺状态
		$(".Js_edit_enable").click(function () {
			$('.f_Head').html('设置店铺状态');
			$('.f_alonediv>.f_alone_name').html("状态值0-2");
			   $(".adduser").fadeIn(200);
			   //确定需要修改的用户id
			   var currentId=$(this).parent().prev().prev().prev().prev().prev().prev().attr('shopId');
			   
			   $('.publicf_btn1').bind('click',function(){	
					//获取输入状态值
				   	var updateData=$('.f_p_input').val();
					var flag=2;
					var Formvalue=new FormData();
					Formvalue.append('currentId',currentId);
					Formvalue.append('updateData',updateData);
					Formvalue.append('flag',flag);
					$.ajax({
							url:'/o2o/superadmin/updateShopByShopId',
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
		//编辑店铺建议
		$(".Js_edit_advice").click(function () {
			$('.f_Head').html('给定当前店铺意见');
			$('.f_alonediv>.f_alone_name').html("店铺建议");
			   $(".adduser").fadeIn(200);
			   //确定需要修改的用户id
			   var currentId=$(this).parent().prev().prev().prev().prev().prev().prev().prev().attr('shopId');
			   
			   $('.publicf_btn1').bind('click',function(){	
					//获取输入意见
				   	var updateData=$('.f_p_input').val();
					var flag=3;
					var Formvalue=new FormData();
					Formvalue.append('currentId',currentId);
					Formvalue.append('updateData',updateData);
					Formvalue.append('flag',flag);
					$.ajax({
							url:'/o2o/superadmin/updateShopByShopId',
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
						url:'/o2o/superadmin/deleteShopByShopId',
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