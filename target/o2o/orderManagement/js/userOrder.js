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
			return '已收货';
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
		if(value=="已收货"){
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
	//获取当前用户Id
	var userId=getQueryString('userId');
	//获取该用户的所有订单
	$.getJSON('/o2o/user/getUserOrder?userId='+userId,function(data){
		if(data.success){
			var OrderHtml='';
			console.log(data.orders);
			data.orders.map(function(item,index){
				OrderHtml+='<tr><td data-id="'+
				item.orderId+'"><input type="text" disabled="disabled" value="'+
				item.orderNumber+'"></td><td><input type="text" disabled="disabled" data-id="'+
				item.shippingAddr.goodsId+'" value="'+
				item.shippingAddr.goodsPhone+'" class="inputContent"></td><td><input type="text" disabled="disabled" value="'+
				item.shippingAddr.goodsAddr+'" class="inputContent"></td><td><input type="text" disabled="disabled" value="'+
				item.shop.shopName+'"></td> <td><input type="text" disabled="disabled" value="'+
				item.orderProductName+'"></td> <td><input type="text" disabled="disabled" value="'+
				item.orderPrice+'"></td> <td><input type="text" disabled="disabled" value="'+
				codetoValue(item.orderMethod,0)+'"></td> <td><input type="text" disabled="disabled" value="'+
				codetoValue(item.orderEnableStatus,1)+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
				getTimeUp(item.createTime)+'"></td><td><p><a href="#" class="button button-fill button-danger submit" style="font-size: 12px; width: 67px;">确认收货</a></p></td></tr>';
			})
			HtmlTemp=OrderHtml;
			$("tbody").html(OrderHtml);
			$('input').attr("style","width:auto;height:auto;text-align: center;border: none;background: white;")
			$('.submit').bind('click',function(){
				//基值DOM
				var baseDOM=$(this).parent().parent().parent();
				//判断状态
				var enable=baseDOM.children('td:nth-child(8)').children().val();
				if(enable=="已收货"){
					$.toast("已确认收货，无须重复确认");
					return ;
				}
				//orderId
				var orderId=baseDOM.children('td:nth-child(1)').attr('data-id');
				$.ajax({
					url:'/o2o/user/submitOrder',
					type:'POST',
					data:JSON.stringify(orderId),
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
			})
			$('.Color1Btn').bind('click',function(){
				//获取输入的订单值
				var OrderNumber=$('.phoneInput').val();
				if(OrderNumber==""){
					$("tbody").html(HtmlTemp);
					$('input').attr("style","width:auto;height:auto;text-align: center;border: none;background: white;")
					$('.submit').bind('click',function(){
							//基值DOM
							var baseDOM=$(this).parent().parent().parent();
							//判断状态
							var enable=baseDOM.children('td:nth-child(8)').children().val();
							if(enable=="已收货"){
								$.toast("已确认收货，无须重复确认");
								return ;
							}
							//orderId
							var orderId=baseDOM.children('td:nth-child(1)').attr('data-id');
							$.ajax({
								url:'/o2o/user/submitOrder',
								type:'POST',
								data:JSON.stringify(orderId),
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
								item.shippingAddr.goodsId+'" value="'+
								item.shippingAddr.goodsPhone+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
								item.shippingAddr.goodsAddr+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
								item.shop.shopName+'"></td> <td><input type="text" disabled="disabled" value="'+
								item.orderProductName+'"></td> <td><input type="text" disabled="disabled" value="'+
								item.orderPrice+'"></td> <td><input type="text" disabled="disabled" value="'+
								codetoValue(item.orderMethod,0)+'"></td> <td><input type="text" disabled="disabled" value="'+
								codetoValue(item.orderEnableStatus,1)+'" class="inputContent"></td> <td><input type="text" disabled="disabled" value="'+
								getTimeUp(item.createTime)+'"></td><td><p><a href="#" class="button button-fill button-danger submit" style="font-size: 12px; width: 67px;">确认收货</a></p></td></tr>';
							})
							$("tbody").html('');
							$("tbody").html(subOrderHtml);
							$('input').attr("style","width:auto;height:auto;text-align: center;border: none;background: white;");
							$('.submit').bind('click',function(){
								//基值DOM
								var baseDOM=$(this).parent().parent().parent();
								//判断状态
								var enable=baseDOM.children('td:nth-child(8)').children().val();
								if(enable=="已收货"){
									$.toast("已确认收货，无须重复确认");
									return ;
								}
								//orderId
								var orderId=baseDOM.children('td:nth-child(1)').attr('data-id');
								$.ajax({
									url:'/o2o/user/submitOrder',
									type:'POST',
									data:JSON.stringify(orderId),
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