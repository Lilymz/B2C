$(function() {
	$('.logout').click(function(){
		$.getJSON('/o2o/shopadmin/logout',function(data){
			if(data.success){
				$.toast("成功退出");
				setTimeout(function(){
					document.location.href=data.url
				},700);
			}
		})
	})
	getlist();
	function getlist() {
		$.ajax({
			url : "/o2o/shopadmin/getshoplist",
			type : "get",
			dataType : "json",
			success : function(data) {
				if(data.success){
					HandleList(data.shopList);
					HandleUser(data.Shopadmin);
				}else{
					alert(data.errMsg);
					document.location.href=data.url;
				}
			}

		});
	}
	function HandleUser(data) {
		$("#user-name").text(data.userName);
	}
	function HandleList(data) {
		var html = '';
		data.map(function(item, index) {
			html += '<div class="row no-gutter"><div class="col-40" name="shopname">'
					+ item.shopName + '</div><div class="col-40" name="shopstatus">'
					+ shopStatus(item.enableStatus)
					+ '</div><div class="col-20">'
					+ goShop(item.enableStatus, item.shopId) + '</div></div>';
		})
		$(".shop-wrap").html(html);
	}
	function shopStatus(status){
		if(status==0){
			return '审核中';
		}else if(status==-1){
			return '店铺非法';
		}else{
			return '审核通过';
		}
	}
	//生成管理店铺的链接
	function goShop(status,id){
		if(status==1){
			return '<a href="/o2o/shopadmin/goshop?ShopId='+id+'" class="external">&nbsp;&nbsp;进入</a>';
		}else {
			return '';
		}
	}
})