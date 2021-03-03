//把订单状态码转化为状态值 type为1则是支付方式，type为0则是订单状态
function codetoValue(code,type){
	if(type==0){
		if(code==1){
			return '钱包支付';
		}else if(code==0){
			return '现金支付';
		}
	}else if(type=1){
		if(code==1){
			return '已到货';
		}else if(code==0){
			return '未到货';
		}
	}
}
function valuetoCode(value,type){
	if(type==0){
		if(value=="钱包支付"){
			return "1";
		}else if(value=="现金支付"){
			return "0";
		}
	}else if(type=1){
		if(value=="已到货"){
			return "1";
		}else if(value=="未到货"){
			return "0";
		}
	}
}
//把状态值转化为状态码
//验证是否生成订单
function verifyDATE(date){
	if(date=="1970-01-01  08:00"){
		return '订单未成交'
	}
	return date;
}
var HtmlTemp=null;
//封装需要多次使用的函数
$(function(){
	//获取当前店铺Id
	var ShopId=getQueryString('ShopId');
	//获取该店铺下的所有订单
	$.getJSON('/o2o/shopadmin/WholeOrder?ShopId='+ShopId,function(data){
		if(data.success){
			console.log(data.orders);
			var OrderHtml='';
			data.orders.map(function(item,index){
				OrderHtml+='<tr><td data-id="'+
				item.orderId+'"><input type="text" disabled="disabled" value="'+
				item.orderNumber+'"></td><td><input type="text" disabled="disabled" data-id="'+
				item.shippingAddr.goodsId+'"value="'+
				item.shippingAddr.goodsName+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
				item.shippingAddr.goodsPhone+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
				item.shippingAddr.goodsAddr+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
				item.shop.shopName+'"></td> <td><input type="text" disabled="disabled" value="'+
				item.orderProductName+'"></td> <td><input type="text" disabled="disabled" value="'+
				item.orderPrice+'"></td> <td><input type="text" disabled="disabled" value="'+
				codetoValue(item.orderMethod,0)+'"></td> <td><input type="text" disabled="disabled" value="'+
				codetoValue(item.orderEnableStatus,1)+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
				getTimeUp(item.createTime)+'"></td> <td><input type="text" disabled="disabled" value="'+
				verifyDATE(getTimeUp(item.dealTime))+'"></td> <td><p><a href="#" class="button button-fill modify">修改</a></p><p><a href="#" class="button button-fill button-danger submit">确认</a></p></td></tr>';
			})
			HtmlTemp=OrderHtml;
			$("tbody").html(OrderHtml);
			$('input').attr("style","width:auto;height:auto;text-align: center;border: none;background: white;")
			$('.modify').bind('click',function(){
				$(".inputContent").removeAttr("disabled");
			})
			var OrderElement=[];
			$('.submit').bind('click',function(){
				//可修改的属性：姓名，手机号，地址，订单状态。
				//基值DOM
				var baseDOM=$(this).parent().parent().parent();
				//总循环次数
				var length=$('.submit').parent().parent().children().length/$('.submit').parent().parent().length;
				//orderId
				var orderId=baseDOM.children('td:nth-child(1)').attr('data-id');
				OrderElement.push(orderId);
				for(var i=1;i<12;i++){
					OrderElement.push(baseDOM.children('td:nth-child('+i+')').children().val());
				}
				//转化为json对象
				var order={};
				order.orderId=OrderElement[0];
				order.goodsName=OrderElement[2];
				order.goodsPhone=OrderElement[3];
				order.goodsAddr=OrderElement[4];
				order.orderEnableStatus=valuetoCode(OrderElement[9],1);//OrderElement[9];
				order.goodsId=$(this).parent().parent().parent().children('td:nth-child(2)').children().attr('data-id');
				$.ajax({
					url:'/o2o/shopadmin/updateOrder',
					type:'POST',
					data:JSON.stringify(order),
					dataType:'json',
					contentType:false,
					processData:false,
					cache:false,
					success:function(data){
						if(data.success){
							document.location.href=data.url;
						}else{
							$.toast("更新失败:"+data.errMsg);
						}
					},
					error:function(data){
						console.log(data);
					}
				})
				$(".inputContent").attr("disabled","disabled");
			})
			$('.Color1Btn').bind('click',function(){
				//获取输入的订单值
				var OrderNumber=$('.phoneInput').val();
				if(OrderNumber==""){
					$("tbody").html(OrderHtml);
					$('input').attr("style","width:auto;height:auto;text-align: center;border: none;background: white;")
					$('.modify').bind('click',function(){
						$(".inputContent").removeAttr("disabled");
					})
					var OrderElement=[];
					$('.submit').bind('click',function(){
						//可修改的属性：姓名，手机号，地址，订单状态。
						//基值DOM
						var baseDOM=$(this).parent().parent().parent();
						//总循环次数
						var length=$('.submit').parent().parent().children().length/$('.submit').parent().parent().length;
						//orderId
						var orderId=baseDOM.children('td:nth-child(1)').attr('data-id');
						OrderElement.push(orderId);
						for(var i=1;i<12;i++){
							OrderElement.push(baseDOM.children('td:nth-child('+i+')').children().val());
						}
						//转化为json对象
						var order={};
						order.orderId=OrderElement[0];
						order.goodsName=OrderElement[2];
						order.goodsPhone=OrderElement[3];
						order.goodsAddr=OrderElement[4];
						order.orderEnableStatus=valuetoCode(OrderElement[9],1);//OrderElement[9];
						order.goodsId=$(this).parent().parent().parent().children('td:nth-child(2)').children().attr('data-id');
						$.ajax({
							url:'/o2o/shopadmin/updateOrder',
							type:'POST',
							data:JSON.stringify(order),
							dataType:'json',
							contentType:false,
							processData:false,
							cache:false,
							success:function(data){
								if(data.success){
									document.location.href=data.url;
								}else{
									$.toast("更新失败:"+data.errMsg);
								}
							},
							error:function(data){
								console.log(data);
							}
						})
						$(".inputContent").attr("disabled","disabled");
					})
				}else{
				//查询订单
				$.ajax({
					url:'/o2o/shopadmin/queryOrder',
					type:'POST',
					data:JSON.stringify(OrderNumber),
					dataType:'json',
					contentType:false,
					processData:false,
					cache:false,
					success:function(data){
						if(data.success){
							var subOrderHtml='';
							data.orders.map(function(item,index){
								subOrderHtml+='<tr><td data-id="'+
								item.orderId+'"><input type="text" disabled="disabled" value="'+
								item.orderNumber+'"></td><td><input type="text" disabled="disabled" data-id="'+
								item.shippingAddr.goodsId+'"value="'+
								item.shippingAddr.goodsName+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
								item.shippingAddr.goodsPhone+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
								item.shippingAddr.goodsAddr+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
								item.shop.shopName+'"></td> <td><input type="text" disabled="disabled" value="'+
								item.orderProductName+'"></td> <td><input type="text" disabled="disabled" value="'+
								item.orderPrice+'"></td> <td><input type="text" disabled="disabled" value="'+
								codetoValue(item.orderMethod,0)+'"></td> <td><input type="text" disabled="disabled" value="'+
								codetoValue(item.orderEnableStatus,1)+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
								getTimeUp(item.createTime)+'"></td> <td><input type="text" disabled="disabled" value="'+
								verifyDATE(getTimeUp(item.dealTime))+'"></td> <td><p><a href="#" class="button button-fill modify">修改</a></p><p><a href="#" class="button button-fill button-danger submit">确认</a></p></td></tr>';
							})
							$("tbody").html('');
							$("tbody").html(subOrderHtml);
							$('input').attr("style","width:auto;height:auto;text-align: center;border: none;background: white;")
							$('.modify').bind('click',function(){
								$(".inputContent").removeAttr("disabled");
							})
							var OrderElement=[];
							$('.submit').bind('click',function(){
								//可修改的属性：姓名，手机号，地址，订单状态。
								//基值DOM
								var baseDOM=$(this).parent().parent().parent();
								//总循环次数
								var length=$('.submit').parent().parent().children().length/$('.submit').parent().parent().length;
								//orderId
								var orderId=baseDOM.children('td:nth-child(1)').attr('data-id');
								OrderElement.push(orderId);
								for(var i=1;i<12;i++){
									OrderElement.push(baseDOM.children('td:nth-child('+i+')').children().val());
								}
								//转化为json对象
								var order={};
								order.orderId=OrderElement[0];
								order.goodsName=OrderElement[2];
								order.goodsPhone=OrderElement[3];
								order.goodsAddr=OrderElement[4];
								order.orderEnableStatus=valuetoCode(OrderElement[9],1);//OrderElement[9];
								order.goodsId=$(this).parent().parent().parent().children('td:nth-child(2)').children().attr('data-id');
								$.ajax({
									url:'/o2o/shopadmin/updateOrder',
									type:'POST',
									data:JSON.stringify(order),
									dataType:'json',
									contentType:false,
									processData:false,
									cache:false,
									success:function(data){
										if(data.success){
											document.location.href=data.url;
										}else{
											$.toast("更新失败:"+data.errMsg);
										}
									},
									error:function(data){
										console.log(data);
									}
								})
								$(".inputContent").attr("disabled","disabled");
							})
						}else{
							$.toast("查询失败:"+data.errMsg);
							
						}
					},
					error:function(data){
						console.log(data);
					}
				})
				}
			})
		}
	})
})