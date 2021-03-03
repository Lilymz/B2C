$(document).on("click", ".my-btn", function() {
  $.openPanel("#panel-js-demo");
});
var shopId=getQueryString("shopId");
var userInfo=null;
//点击购买按钮出现选择直接购买和加入购物车业务逻辑
//全部商品
var productAll=null;
$(document).on("click", ".my-btn", function() {
	var verifyLoginUser='/o2o/user/isLogin';
	$.getJSON(verifyLoginUser,function(data){
			if(data.success){
				MyHtml='<div class="content-block"><p><h3>余额：</h3><h4 style="position: relative; top: -40px; left: 53px; ">'+data.userInfo.money+'</h4></p><a href="/o2o/shopcart/goShopCart"><p><span class="icon icon-cart"></span>&nbsp;&nbsp;<div style="position: relative; top: -33px; left: 33px;">购物车</div></a></p><a href="/o2o/user/goUserOrder?userId='+data.userInfo.userId+'"><p style="margin-bottom: 2rem;"><span class="icon icon-menu"></span><div style="position: relative;top: -59px; left: 28px;">我的订单</div></p></a><p><a href="#" class="close-panel">关闭</a></p></div>';
				$("#panel-js-demo").html(MyHtml);
			}else{
				alert(data.errMsg);
				document.location.href='/o2o/user/user_login';
				
			}
	})
});
function UserPurcharse(tagcontent,tagcontentImg,money,id){
	var buttons1 = [
	      {
	        text: '请选择',
	        label: true
	      },
	      {
	        text: '直接购买',
	        bold: true,
	        onClick: function() {
	        	//商品名
	        	var productName=tagcontent.children('.item-title-row').children('.item-link.item-content').children().html();
	        	//获取当前商品价
	        	var promotionPrice=tagcontent[0].getElementsByClassName("pro")[0].innerText.substring(4);
	        	
	        	var UpdateUserURL='/o2o/user/UpdateUser';
	        	//判断用户余额与该商品价大小
	        	if(money!=0&&id!=null){
	        	money-=promotionPrice;
	        	}
	        	var formValue=new FormData();
	        	formValue.append("money",money);
	        	formValue.append("id",id);
	        	//更新用户数据,有余额进行更新
	        	$.ajax({
	        		url : UpdateUserURL,
					type : "POST",
					data:formValue,
					dataType : "json",
					contentType: false,
					processData: false,
					success : function(data) {
						if(data.success){
							if(data.userInfo==null){
				        		  alert("请先登录！")
				        		  document.location.href='/o2o/user/user_login';
				        	  }
						}
						$.toast("购买成功！");
					}
	        	})
	        	//加入用户订单
	        	var formValue1=new FormData();
	        	formValue1.append("productName",productName);
	        	formValue1.append("promotionPrice",promotionPrice);
	        	formValue1.append("shopId",shopId);
	        	$.ajax({
	        		url : '/o2o/user/addOrderBydirect',
					type : "POST",
					data:formValue1,
					dataType : "json",
					contentType: false,
					processData: false,
					success : function(data) {
						if(data.success){
							document.location.href=data.url;
						}
						$.toast("购买成功！");
					}
	        	})
	        }
	      },
	      {
	        text: '加入购物车',
	        onClick: function() {
	        UserIsLogin();
	          var shopcartInsertURL='/o2o/shopcart/insertShopCart'
	        	  //商品名
	        	  var productName=tagcontent.children('.item-title-row').children('.item-link.item-content').children().html();
	        	  //图片
	        	  var productImg=tagcontentImg[0].getElementsByTagName("img")[0].src.substring(21);
	        	  
	        	  //描述
	          	  var  productDesc=tagcontent[0].getElementsByClassName("item-subtitle")[0].innerText;
	        	  //价格
	          	  var promotionPrice=tagcontent[0].getElementsByClassName("pro")[0].innerText.substring(4);
	        	  //userId
	        	  var userId=id;//id为空即可跳到登录界面
	        	  var 	formValue=new FormData();
	        	  		formValue.append("productName",productName);
	          			formValue.append("productImg",productImg);
	          			formValue.append("productDesc",productDesc);
	          			formValue.append("promotionPrice",promotionPrice);
	          			formValue.append("userId",userId);
	          			formValue.append("shopId",shopId);
	        	  $.ajax({
	          		url : shopcartInsertURL,
	  				type : "POST",
	  				data:formValue,
	  				dataType : "json",
	  				contentType: false,
	  				processData: false,
	  				success : function(data) {
	  					if(data.success){
	  						$.toast("购物车加入成功！");
	  					}else{
	  						$.toast(data.errMsg);
	  					}
	  					
	  				}
	          	})
	        }
	      }
	    ];
	    var buttons2 = [
	      {
	        text: '取消',
	        bg: 'danger'
	      }
	    ];
	    var groups = [buttons1, buttons2];
	    $.actions(groups);
}
$(document).on('click','.button.button-fill.buy', function () {
	//获取直接购买的标签
	var tagcontent=$(this).parent().parent().parent();
	var tagcontentImg=$(this).parent().parent().parent().parent();
	UserPurcharse(tagcontent,tagcontentImg,userInfo.money,userInfo.userId)
}); 
$(function(){
	var shopId=getQueryString("shopId");
	var shopURL='/o2o/shopadmin/getshopByShopId?shopId='+shopId;
	var getProductTypesURL='/o2o/shopadmin/getproductTypesByshopId?shopId='+shopId;
	var getProductsURL='/o2o/shopadmin/getproductsByShopIdIndex?shopId='+shopId;
	//shopURL无问题12.09
	$.getJSON(shopURL,function(data){
		if(data.success){
			UserIsLogin();
			//获取session中的用户
			userInfo=data.userInfo;
			$('.title').html(data.Shop.shopName);
			$('.shopdesc>p').html("店铺描述："+data.Shop.shopDesc);
			$('.shopHome p').html(data.Shop.shopAddr);
			$('.shopImg').html('<img src="'+data.Shop.shopImg+'" style="width: 128px;height: 128px;">');
			$('.createshopTime>h3').html("店铺成立时间："+getTime(data.Shop.createTime));
			$('.adminAdvice p').html("web管理员："+data.Shop.advice);
			$('.phone-icon').click(function(){
				$('.phoneValue').css({
					"position": "relative",
					"left": "75px",
					"font-size": "medium",
					"top": "-38px"
				});
				$('.phoneValue').html(data.Shop.phone);
			})
			
		}else{
//			document.location.href='/o2o/Page/shop';
			$.toast(data.Msg);
		}
	});
	//加载商品分类
	$.getJSON(getProductTypesURL,function(data){
		if(data.success){
			var  productTpyeHtml='<li class="productTypes"><a href="#" class="productType" id="productALL">全部</a></li>';
			data.ProductTypeList.map(function(item,index){
				productTpyeHtml+='<li class="productTypes"><a href="#" class="productType" id="pro '+item.productTypeId+'">'+item.productTypeName+'</a></li>';
			});
			$('.docs-side-menu').html(productTpyeHtml);
			$('.productType').on('click',function(){
				//获取商品Id传值到后台
				var productTypeId=$(this).attr('id').slice(3);
				
				if(productTypeId=="ductALL"){
					//全部商品类别
					if(productAll!=null){
					$('.content-padded.grid-demo').html('');
					$('.content-padded.grid-demo').html(productAll);
					}
				}else{
				//根据商品类型获取商品URL
				var getProductByProductTypeIdURL='/o2o/shopadmin/getProductByProductTypeId';
				var formValue = new FormData();
				formValue.append("productTypeId",productTypeId);
				$.ajax({
				url : getProductByProductTypeIdURL,
				type : "POST",
				data:formValue,
				dataType : "json",
				contentType: false,
				processData: false,
				success : function(data) {
					if(data.success){
						$('.content-padded.grid-demo').html('');
						ProductTypeProductHTML='';
						data.products.map(function(item,index){
							if(index%2==0){
								ProductTypeProductHTML+='<div class="row no-gutter">'
							}
							ProductTypeProductHTML+='<div class="col-50"><div class="list-block media-list"><ul><li><div class="item-media"><img src="'+
							item.imgAddr+'" style="height:109px;position: relative;left: 32px;"></div><div class="item-inner"><div class="item-title-row"><a href="#" class="item-link item-content"><div class="item-title">'+
							item.productName+'</div></a><div class="item-after"><a href="#" class="item-link item-content"></a><a href="#" class="button button-fill buy">购买</a></div></div><div class="item-subtitle">'+
							item.productDesc+'</div><div class="item-text"><div>原价:'+
							item.normalPrice+'</div><div class="pro">折扣价:'+
							item.promotionPrice+'</div></div></div></li></ul></div></div>';
							if(index%2==1){
								ProductTypeProductHTML+='</div>';
							}
						});
						$('.content-padded.grid-demo').html(ProductTypeProductHTML);
					}
				}
				});
			}
			});
		}else{
//			document.location.href='/o2o/Page/shop';
			$.toast(data.errMsg);
		}
	});
	//店铺加载商品(初始加载)
	$.getJSON(getProductsURL,function(data){
		if(data.success){
			var  productsHtml='';
			data.products.map(function(item,index){
				if(index%2==0){
					productsHtml+='<div class="row no-gutter">'
				}
				productsHtml+='<div class="col-50"><div class="list-block media-list"><ul><li><div class="item-media"><img src="'+
				item.imgAddr+'" style="height:109px;position: relative;left: 32px;"></div><div class="item-inner"><div class="item-title-row"><a href="#" class="item-link item-content"><div class="item-title">'+
				item.productName+'</div></a><div class="item-after"><a href="#" class="item-link item-content"></a><a href="#" class="button button-fill buy">购买</a></div></div><div class="item-subtitle">'+
				item.productDesc+'</div><div class="item-text"><div>原价:'+
				item.normalPrice+'</div><div class="pro">折扣价:'+
				item.promotionPrice+'</div></div></div></li></ul></div></div>';
				if(index%2==1){
					productsHtml+='</div>';
				}
			});
			productAll=productsHtml;
			$('.content-padded.grid-demo').html(productsHtml);
		}else{
//			document.location.href='/o2o/Page/shop';
			$.toast(data.errMsg);
		}
	});
	//获取当前店铺的评论
	$.getJSON('/o2o/shopadmin/getCommentsByShopId',function(data){
		if(data.success){
			var commentHtml='';
			console.log(data.comments);
			data.comments.map(function(item,index){
				commentHtml+='<div class="card"><div class="card-header">匿名</div><div class="card-content"><div class="card-content-inner">'+
				item.commentContent+'</div></div><div class="card-footer">'+
				getTimeUp(item.createTime)+'</div></div>';
			})
			$('.comment-container').html(commentHtml);
		}else{
			$.toast(data.errMsg);
		}
	})
	//添加当前店铺评论
	$('.comment-submit').click(function(){
		var commentContent=$('.text-area-input').val();
		$.ajax({
			url:'/o2o/shopadmin/insertComment',
			type:'POST',
			dataType:'json',
			data:JSON.stringify(commentContent),
			contentType:false,
			cache:false,
			success:function(data){
				if(data.success){
					$.toast("评论成功");
					setTimeout(function(){document.location.href=data.url},700);
				}else{
					$.toast(data.errMsg);
				}
			},
			error:function(data){
				console.log(data);
			}
		})
	})
	
	
})