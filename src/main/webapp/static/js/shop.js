$(document).on("pageInit", function() {
		$(".my-btn").click(function(){
			 $.openPanel("#panel-js-demo");
		})
		var loading = false;
        // 每次加载添加多少条目
        var itemsPerLoad = 5;
        // 最多可加载的条目
        var lastIndex = $('.item-content.data').length-1;
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
      					TempShopsHtml+='<li class="item-content data"><div class="item-inner"><div class="item-title"><div class="card shopcard"><div class="card-content"><div class="card-content-inner"><div class="list-block media-list"><ul><li><div class="item-content"><div class="item-media"><img src="'+
      					shopes[i].shopImg+'" style="width: 2.2rem;"></div><div class="item-inner"><div class="item-title-row"><div class="item-title">'+
      					shopes[i].shopName+'</div></div><div class="item-subtitle"><div class="area-type-storein"><span class="area">位置:'+
      					shopes[i].area.areaName+'</span><span class="shop-type" style="position: relative;left: 20%;">'+
      					shopes[i].shopType.shoptypeName+'</span><span class="storein" style="position: relative;left: 40%;"><a id="ordinary-store" href="/o2o/shop/shopindex?shopId='+shopes[i].shopId+'">进店</a></span></div></div></div></div></li></ul></div></div></div></div></div></div></li>';
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
                lastIndex = $('.item-content.data').length;
                $.refreshScroller();
            }, 1000);
        });
        //提交area数据
        $(".search-area").on('click',function(event){
        	
        	var shop = {};//这里创建一个明为shop的json对象，以下是传值
			shop.area = {areaName :$("#picker").val()};
			var formValue = new FormData();//这里是创建一个存放Form表单数据的对象
			formValue.append("shopStr", JSON.stringify(shop));//前台接受到的数据接受进对象里
			$.ajax({
				url : GetShopsByAreaURL,
				type : 'POST',
				data : formValue,
				contentType : false,
				processData : false,
				cache : false,
				success:function(data){
					if(data.success){
	      			$('.infinite-scroll .list-container>li').remove();
	      			function getShops(number,lastIndex) {
	      			var TempShopsHtml='';
      				var shopes=data.shopList;
      				for (var i = lastIndex + 1; i <= lastIndex + number;i++) {
      					if(shopes[i]==null) {
      						flag=true;
      						$('.infinite-scroll .list-container').append(TempShopsHtml);
      						return;
      					}
      					TempShopsHtml+='<li class="item-content data"><div class="item-inner"><div class="item-title"><div class="card shopcard"><div class="card-content"><div class="card-content-inner"><div class="list-block media-list"><ul><li><div class="item-content"><div class="item-media"><img src="'+
      					shopes[i].shopImg+'" style="width: 2.2rem;"></div><div class="item-inner"><div class="item-title-row"><div class="item-title">'+
      					shopes[i].shopName+'</div></div><div class="item-subtitle"><div class="area-type-storein"><span class="area">位置:'+
      					shopes[i].area.areaName+'</span><span class="shop-type" style="position: relative;left: 20%;">'+
      					shopes[i].shopType.shoptypeName+'</span><span class="storein" style="position: relative;left: 40%;"><a id="ordinary-store" href="/o2o/shop/shopindex?shopId='+shopes[i].shopId+'">进店</a></span></div></div></div></div></li></ul></div></div></div></div></div></div></li>';
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
	                    lastIndex = $('.item-content.data').length;
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
        $("#picker").picker({
            toolbarTemplate: '<header class="bar bar-nav">'+
             '<h1 class="title">选择区域</h1>'+
            '</header>',
            cols:[
                {
               textAlign: 'center',
               values: ['闽南师范大学东门', '闽南师范大学瑞京路', '闽南师范大学延安北路', '闽南师范大学万科里',]
             }
            ]
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
      			$('.infinite-scroll .list-container>li').remove();
      			function getShops(number,lastIndex) {
      			var TempShopsHtml='';
  				var shopes=data.shopList;
  				for (var i = lastIndex + 1; i <= lastIndex + number;i++) {
  					if(shopes[i]==null) {
  						flag=true;
  						$('.infinite-scroll .list-container').append(TempShopsHtml);
  						return;
  					}
  					TempShopsHtml+='<li class="item-content data"><div class="item-inner"><div class="item-title"><div class="card shopcard"><div class="card-content"><div class="card-content-inner"><div class="list-block media-list"><ul><li><div class="item-content"><div class="item-media"><img src="'+
  					shopes[i].shopImg+'" style="width: 2.2rem;"></div><div class="item-inner"><div class="item-title-row"><div class="item-title">'+
  					shopes[i].shopName+'</div></div><div class="item-subtitle"><div class="area-type-storein"><span class="area">位置:'+
  					shopes[i].area.areaName+'</span><span class="shop-type" style="position: relative;left: 20%;">'+
  					shopes[i].shopType.shoptypeName+'</span><span class="storein" style="position: relative;left: 40%;"><a id="ordinary-store" href="/o2o/shop/shopindex?shopId='+shopes[i].shopId+'">进店</a></span></div></div></div></div></li></ul></div></div></div></div></div></div></li>';
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
                    lastIndex = $('.item-content.data').length;
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
		//1.点击'我的'登录后台检查用户是否登录
		UserIsLogin();
	});

});
$.init();