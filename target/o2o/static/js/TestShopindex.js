$(document).on("pageInit", function() {
		$(".my-btn").click(function(){
			 $.openPanel("#panel-js-demo");
		})
		var loading = false;
        // 每次加载添加多少条目
        var itemsPerLoad = 5;
        // 最多可加载的条目
        var lastIndex = $('.aui-flex.b-line').length-1;
        var GetShopsByAreaURL='/o2o/shopadmin/getshoplistByArea';
        //ShopsURL
        var ShopsURL="/o2o/shopadmin/getshoplistUserInfo";
        //判断无限滚动按钮是否删除
        var flag=false;
        //获取Shops
        function getShops(number,lastIndex) {
      		$.getJSON(ShopsURL,function(data){
      			if(data.success){
      				var TempShopsHtml='';
      				var shopes=data.shopList;
      				for (var i = lastIndex + 1; i <= lastIndex + number;i++) {
      					if(shopes[i]==null) {
      						flag=true;
      						$('.infinite-scroll .list-container').append(TempShopsHtml);
      						return;
      					}
      					TempShopsHtml+='<a href="/o2o/shop/shopindex?shopId='+shopes[i].shopId+'" class="aui-flex b-line"><div class="aui-goods-img"><img src="'+
      					shopes[i].shopImg+'" alt=""></div><div class="aui-flex-box"><h2>'+
      					shopes[i].shopName+'</h2><h3><em><img src="../static/img/star1.png" alt=""><img src="../static/img/star1.png" alt=""><img src="../static/img/star1.png" alt=""><img src="../static/img/star1.png" alt=""><img src="../static/img/star2.png" alt=""></em><i>'+
      					shopes[i].shopType.shoptypeName+'</i></h3><p><em>位</em>'+
      					shopes[i].area.areaName+'</p><p><em>描述</em>'+
      					shopes[i].shopDesc+'</p></div></a>';
      				}
      				$('.infinite-scroll .list-container').append(TempShopsHtml);
      			}else{
      				$.toast(data.errMsg);
      				document.location.href='/o2o/user/user_login';
      			}
      		});
      	}
       getShops(itemsPerLoad,-1);
       var maxItems = 100;
        $(document).on('infinite', function () {
            // 如果正在加载，则退出
            if (loading)
                return;
            // 设置flag
            loading = true;
            // 模拟1s的加载过程
          setTimeout(function () {
                // 重置加载flag
                loading = false;
                if (lastIndex >= maxItems||flag) {
                    // 加载完毕，则注销无限加载事件，以防不必要的加载
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    // 删除加载提示符
                    $('.infinite-scroll-preloader').remove();
                    return;
                }
                lastIndex=lastIndex+itemsPerLoad;
                getShops(itemsPerLoad, lastIndex);
                // 更新最后加载的序号
                lastIndex = $('.aui-flex.b-line').length;
                $.refreshScroller();
            }, 1000);
        });
     
	$(".button-primary").on('click',function(){
		var GetShopsByShoplikeURL='/o2o/shopadmin/getshoplistByShopLike';
		var shop = {};//这里创建一个明为shop的json对象，以下是传值
		shop.shopName = $("#search").val();
		var formValue = new FormData();//这里是创建一个存放Form表单数据的对象
		formValue.append("shopStr", JSON.stringify(shop));//前台接受到的数据接受进对象里
		$.ajax({
			url : GetShopsByShoplikeURL,
			type : 'POST',
			data : formValue,
			contentType : false,
			processData : false,
			cache : false,
			success:function(data){
				if(data.success){
      			$('.infinite-scroll .list-container>a').remove();
      			function getShops(number,lastIndex) {
      			var TempShopsHtml='';
  				var shopes=data.shopList;
  				for (var i = lastIndex + 1; i <= lastIndex + number;i++) {
  					if(shopes[i]==null) {
  						flag=true;
  						$('.infinite-scroll .list-container').append(TempShopsHtml);
  						return;
  					}
  					TempShopsHtml+='<a href="/o2o/shop/shopindex?shopId='+shopes[i].shopId+'" class="aui-flex b-line"><div class="aui-goods-img"><img src="'+
  					shopes[i].shopImg+'" alt=""></div><div class="aui-flex-box"><h2>'+
  					shopes[i].shopName+'</h2><h3><em><img src="../static/img/star1.png" alt=""><img src="../static/img/star1.png" alt=""><img src="../static/img/star1.png" alt=""><img src="../static/img/star1.png" alt=""><img src="../static/img/star2.png" alt=""></em><i>'+
  					shopes[i].shopType.shoptypeName+'</i></h3><p><em>位</em>'+
  					shopes[i].area.areaName+'</p><p><em>描述</em>'+
  					shopes[i].shopDesc+'</p></div></a>';
  					}
  				$('.infinite-scroll .list-container').append(TempShopsHtml);
			   }
      			getShops(itemsPerLoad,-1);
      			setTimeout(function () {
                    // 重置加载flag
                    loading = false;
                    if (lastIndex >= maxItems||flag) {
                        // 加载完毕，则注销无限加载事件，以防不必要的加载
                        $.detachInfiniteScroll($('.infinite-scroll'));
                        // 删除加载提示符
                        $('.infinite-scroll-preloader').remove();
                        return;
                    }
                    lastIndex=lastIndex+itemsPerLoad;
                    getShops(itemsPerLoad, lastIndex);
                    // 更新最后加载的序号
                    lastIndex = $('.aui-flex.b-line').length;
                    $.refreshScroller();
                }, 1000);
			 }else{
				 console.log(data.Msg);
			 }
			},
			error:function(){
				console.log("request error！search_area ajax");
			}
		});
	});
	$(document).on("click", ".my-btn", function() {
		var verifyLoginUser='/o2o/user/isLogin';
		$.getJSON(verifyLoginUser,function(data){
				if(data.success){
					MyHtml='<div class="content-block"><p><h3 style="color: wheat;">余额：</h3><h4 style="position: relative!important; top: -36px!important; left: 72px!important;color: wheat;">'+data.userInfo.money+'</h4></p><a href="/o2o/shopcart/goShopCart"><p><span class="icon icon-cart"></span>&nbsp;&nbsp;<div style="position: relative; top: -32px; left: 33px; color: wheat;">购物车</div></a></p><a href="/o2o/user/goUserOrder?userId='+data.userInfo.userId+'" style="position: relative; top: -44px;"><p style="margin-bottom: 1rem!important;"><span class="icon icon-menu"></span><div style="color:wheat;">我的订单</div></p></a><p><a href="#" class="close-panel" style="color:red">关闭</a></p></div>';
					$("#panel-js-demo").html(MyHtml);
				}else{
					alert(data.errMsg);
					document.location.href='/o2o/user/user_login';
					
				}
		})
	});
});
$.init();