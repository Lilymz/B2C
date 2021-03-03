$(function() {
	var shopId=getQueryString("ShopId");
	var shopmanagmentUrl='/o2o/shopadmin/getshopById?ShopId='+shopId;
	//给该店铺进行商品添加
	$.getJSON(shopmanagmentUrl,function(data){
		if(data.success){
			$("#productAdd").attr('href','/o2o/shop/product?ShopId='+shopId);
		}
	})
	//状态转化函数
	function productEnableStatus(data){
		if(data==0){
			return '已下架';
		}else if(data==1){
			return '在架';
		}
	}
	//默认取出属于该店铺的所有商品
	$.getJSON('/o2o/shopadmin/getproductsByShopId?shopId='+shopId,function(data){
		if(data.success){
			var productsHtml='';
			data.products.map(function(item,index){
				productsHtml+='<tr class="relative" data-id="'+
				item.productId+'"><td>'+
				item.productType.productTypeName+'</td><td><img src="'+
				item.imgAddr+'" width="60px" height="60px"></td><td>'+
				item.productName+'</td><td>'+
				item.normalPrice+'</td><td>'+
				item.promotionPrice+'</td><td>'+
				item.productDesc+'</td><td class="quantity" style="width:100px;"><span style="position: relative; left: 38px;">'+
				item.priority+'</span><span class="name_status  plf10 btn btn-danger reduce" style="position: relative; left: -21px;">-1</span><span class="name_status  plf10 btn btn-green add" style="position: relative; left: 7px;">+1</span></td><td style="width:84px;"><span class="quantity pro-prompt">'+
				productEnableStatus(item.enableStatus)+'</span><span class="name_status  plf10 btn btn-green enableStatusOn">上架</span><span class="name_status  plf10 btn btn-blue enableStatusOff">下架</span></td><td>'+
				getTime(item.createTime)+'</td><td><button class="btn btn-danger padding5 deleteProduct">删除</button></td></tr>';
			})
			$("#productlist").html(productsHtml);
			//降低商品优先级
			$(".reduce").on('click',function(){
				//获取当前商品id
				var productId=$(this).parent().parent().attr("data-id");
				 var priority=$(this).prev().html(($(this).prev().html())-1).html();
				 var Formvalue=new FormData();
					Formvalue.append('productId',productId);
					Formvalue.append('priority',priority);
					$.ajax({
						url:'/o2o/shopadmin/updateProductPriorityByProductId',
						type:'POST',
						data:Formvalue,
						dataType:'json',
						contentType:false,
						cache:false,
						processData:false,
						success:function(data){
							if(data.success){
								$.toast(data.Msg);
							}else{
								alert(data.errMsg);
							}
						},
						error:function(data){
							console.log(data);
						}
					})
			 })
			 //增加商品优先级
			 $(".add").on('click',function(){
				//获取当前商品id
				 var productId=$(this).parent().parent().attr("data-id");
				 var add_1=parseInt($(this).prev().prev().html());
				 var priority=$(this).prev().prev().html(add_1+1).html();
				//未传入数据库
				 var Formvalue=new FormData();
					Formvalue.append('productId',productId);
					Formvalue.append('priority',priority);
					$.ajax({
						url:'/o2o/shopadmin/updateProductPriorityByProductId',
						type:'POST',
						data:Formvalue,
						dataType:'json',
						contentType:false,
						cache:false,
						processData:false,
						success:function(data){
							if(data.success){
								$.toast(data.Msg);
							}else{
								alert(data.errMsg);
							}
						},
						error:function(data){
							console.log(data);
						}
					})
			 })
			 //商品上架
			 $(".enableStatusOn").on('click',function(){
				 var productId=$(this).parent().parent().attr("data-id");
				 if($(this).prev().html()=="已下架"){
				 var enableStatus=1;
				 var Formvalue=new FormData();
					Formvalue.append('productId',productId);
					Formvalue.append('enableStatus',enableStatus);
					$.ajax({
						url:'/o2o/shopadmin/updateProductEnableStatusByProductId',
						type:'POST',
						data:Formvalue,
						dataType:'json',
						contentType:false,
						cache:false,
						processData:false,
						success:function(data){
							if(data.success){
								$.toast(data.Msg);
							}else{
								alert(data.errMsg);
							}
						},
						error:function(data){
							console.log(data);
						}
					})
				 $(this).prev().html('在架');
				 }
				 console.log($(this).prev().html());
			 })
			//商品下架
			$(".enableStatusOff").on('click',function(){
				//获取当前商品id
				 var productId=$(this).parent().parent().attr("data-id");
				if($(this).prev().prev().html()=="在架"){
					//传入当前状态值
					var enableStatus=0;
					var Formvalue=new FormData();
					Formvalue.append('productId',productId);
					Formvalue.append('enableStatus',enableStatus);
					$.ajax({
						url:'/o2o/shopadmin/updateProductEnableStatusByProductId',
						type:'POST',
						data:Formvalue,
						dataType:'json',
						contentType:false,
						cache:false,
						processData:false,
						success:function(data){
							if(data.success){
								$.toast(data.Msg);
							}else{
								alert(data.errMsg);
							}
						},
						error:function(data){
							console.log(data);
						}
					})
					$(this).prev().prev().html('已下架');
				}
			 })
			 //商品删除
			 $(".deleteProduct").on('click',function(){
				 	//获取当前商品id
					var productId=$(this).parent().parent().attr("data-id");
					console.log(productId);
					$.ajax({
						url:'/o2o/shopadmin/deletProductByProductId',
						type:'POST',
						data:JSON.stringify(productId),
						dataType:'json',
						contentType:'application/json;charset=utf-8',
						cache:false,
						processData:false,
						success:function(data){
							if(data.success){
								$.toast(data.Msg);
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
		}else{
			alert(data.errMsg);
			document.location.href=data.url;
		}
	})
		 
})