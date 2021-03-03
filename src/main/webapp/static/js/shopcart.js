/*计算总钱数*/
function total(){
	setTimeout(function(){
		var S=0;
	    $.each($('.total'), function() {
	    	var $ul_total=$(this).prev('ul').find("input[type='checkbox']");
	    	var s=0;
	        var n1=0;
	    	$.each($(this).prev('ul').find(".number"), function(i) {
		if($ul_total.eq(i).attr("checked")=="checked"){
			s=s+parseInt($(this).html())*parseInt($(this).parent().prev().html().replace("￥",""));
			n1=n1+parseInt($(this).html());
		}
	});
	$(this).children("span").html("￥"+s.toFixed(1));
	$(this).children("number").html(n1);
	S=S+s;
	    });
	$(".bottom span").html(S.toFixed(1));
	},100)
}
/*计算总钱数*/
/*判断有无数据*/
function hide(){
	if ($(".content").length==0) {
		$(".bottom").hide();
		$(".no").css("display","-webkit-box");
		return;
	}else{
		$(".bottom").eq(0).show();
		$(".no").css("display","none");
	}
}
/*判断有无数据*/
/*判断是否全选*/
function sum(){
	if ($("ul input[checked='checked']").length==$("li").length) {
		$(".bottom input[type=checkbox]").attr("checked","checked");
		$(".bottom input[type=checkbox]").next("img").attr("src","../static/img/c_checkbox_on.png");
	}else{
		$(".bottom input[type=checkbox]").removeAttr("checked");
		$(".bottom input[type=checkbox]").next("img").attr("src","../static/img/c_checkbox_off.png");
	}
}
/*判断是否全选*/
/*给单选框或复选框添加样式*/
function checkbox($this){
	if($this.attr('type')=="checkbox"){
		   if ($this.attr('checked')=="checked") {
		   	$this.removeAttr("checked");
		   	 $this.next('img').attr("src","../static/img/c_checkbox_off.png");
		   }else{
		   	 $this.attr("checked","checked");
		   	$this.next('img').attr("src","../static/img/c_checkbox_on.png");
		   }
		}
		/*计算总钱数*/
		total();
		/*计算总钱数*/
}
/*给单选框或复选框添加样式*/
$(function(){
	//加载购物车
	var getShopCartURL='/o2o/shopcart/queryShopcartByuserId'
	$.getJSON(getShopCartURL,function(data){
		if(data.success){
			var liShopCartHtml='';
			data.queryShopCartsService.map(function(item,index){
				liShopCartHtml+='<li class="clearfix product" shop-id="'+
				item.shopcartShopId+'" product-name="'+
				item.shopcartProductName+'"><div class="label fl"><label><input type="checkbox" checked="checked"><img src="../static/img/c_checkbox_on.png"></label></div><div class="img fl"><img src="'+
				item.shopcartProductImg+'"></div><div class="text fl"><p class="overflow">'+
				item.shopcartProductDesc+'</p><p class="clearfix"><span class="fl red">￥'+
				item.shopcartProductPromotion+'</span><span class="fr"><input type="button" value="-" class="btn1"><span class="number">1</span><input type="button" value="+" class="btn2"></span></p></div></li>';
			});
			$('#datacart').html('');
			$('#datacart').html(liShopCartHtml);
			hide();
			total();
		}else{
			$.toast("购物车获取失败");
		}
	})
/*编辑*/
$("header span").click(function(){
       if ($(this).html()=="编辑") {
       	$(this).html("完成");
       	$(".bottom").eq(1).show();
       }else{
       	$(this).html("编辑");
       	$(".bottom").eq(1).hide();
       }
       hide();   
});
/*编辑*/
/*底部全选*/
$('.bottom-label input').change(function(){
		if ($(this).attr("checked")=="checked") {
			$(".con input[type='checkbox']").removeAttr("checked");
			$(".con input[type='checkbox']").next('img').attr("src","../static/img/c_checkbox_off.png");
		}else{
			$(".con input[type='checkbox']").attr("checked","checked");
			$(".con input[type='checkbox']").next('img').attr("src","../static/img/c_checkbox_on.png");
		}
		checkbox($(this));
})
/*底部全选*/
/*子项全选*/
$('.list input').change(function(){
	var $list_input=$(this).parents('.list').next('ul').find('input[type=checkbox]');
		if ($(this).attr("checked")==undefined) {
			$list_input.attr("checked","checked");
			$list_input.next('img').attr("src","../static/img/c_checkbox_on.png");
		}else{
			$list_input.removeAttr("checked");
			$list_input.next('img').attr("src","../static/img/c_checkbox_off.png");
		} 
		checkbox($(this));
		sum();
})
/*子项全选*/
$(document).on('change',"ul input[type='checkbox']",function(){
	checkbox($(this));
	var $ul_input=$(this).parents('ul').prev('.list').find('input');
	if($(this).parents('ul').find("input[checked='checked']").length==$(this).parents("ul").children('li').length){	
		$ul_input.attr("checked","checked");
		$ul_input.next('img').attr("src","../static/img/c_checkbox_on.png");
	}else{
		$ul_input.removeAttr("checked");
		$ul_input.next('img').attr("src","../static/img/c_checkbox_off.png");
	} 
	sum();
})
/*点击加一*/
$(document).on('click','.btn2',function(){
	$(this).prev('.number').html(parseInt($(this).prev('.number').html())+1);
	/*计算总钱数*/
	total();
	/*计算总钱数*/
})
/*点击加一*/
/*点击减一*/
$(document).on('click','.btn1',function(){
	if($(this).next('.number').html()==0)
		$(this).next('.number').html(0);
	else
	$(this).next('.number').html(parseInt($(this).next('.number').html())-1);	
	/*计算总钱数*/
	total();
	/*计算总钱数*/
})
		/*点击减一*/
		$(".number").click(function(){
			$('.text1').css({"display":"flex","-webkit-display":"flex"}).attr({'ind':$(this).parents('li').index(),"ind_1":$(this).parents("ul").attr("ind")});
			$('.text1 input[type=number]').val($(this).html());
		})
		$('.text1 input[type="button"]').click(function(){
			if($('.text1 input[type=number]').val()==""){
				$('.alert').show().html('请输入数量！');
				 setTimeout(function(){$('.alert').hide();},2000);
				return false;
			}
			if ($('.text1 input[type=number]').val()>100) {
				$('.alert').show().html('超出库存了！');
				 setTimeout(function(){$('.alert').hide();},2000);
				return false;
			}
			$("ul").eq($('.text1').attr('ind_1')).find(".number").eq($('.text1').attr('ind')).html($('.text1 input[type=number]').val());
			$('.text1').css({"display":"none","-webkit-display":"none"});
			total();
		})
/*结算*/
$('.sett').click(function(){
	var length=$('.clearfix.product').length;
	//所有商品名
	var ShopCartProductName=new Array();
	var ShopCartShopId=new Array();
	for(var i=0;i<length;i++){
		ShopCartProductName.push($('.clearfix.product')[i].getAttribute('product-name'));
	}
	for(var i=0;i<length;i++){
		ShopCartShopId.push($('.clearfix.product')[i].getAttribute('shop-id'));
	}
	length=$('.clearfix.product').children('div:nth-child(3)').children('p:nth-child(2)').children('span:first-child').length;
	//所有商品价格
	var ShopCartProductPrice=new Array();
	for(var i=0;i<length;i++){
		ShopCartProductPrice.push($('.clearfix.product').children('div:nth-child(3)').children('p:nth-child(2)').children('span:first-child')[i].innerHTML.slice(1));
	}
	var $deleteImg=$('img[src="../static/img/c_checkbox_on.png"]').parent().parent().next().children("img");
	//获取购物车中的商品地址进行删除
	var ShopCartImgAddr=new Array();
	for(var i=0;i<$deleteImg.length;i++){
		ShopCartImgAddr.push($deleteImg[i].outerHTML.substring(10,$deleteImg[i].outerHTML.length-2));
	}
	var TotalPrice=$(".sett").prev()[0].innerHTML.substring(0,$(".sett").prev()[0].innerHTML.length-2);
	ShopCartImgAddr.push(TotalPrice);
	var shopCartDeleteHtml='/o2o/shopcart/deleteShopcartByImg'
		
		var formValue=new FormData();
	formValue.append("ShopCartProductName",JSON.stringify(ShopCartProductName));
	formValue.append("ShopCartProductPrice",JSON.stringify(ShopCartProductPrice));
	formValue.append("ShopCartShopId",JSON.stringify(ShopCartShopId));
	$.ajax({
		url : '/o2o/user/batchaddOrderBydirect',
		type : "POST",
		data:formValue,
		dataType : "json",
		contentType: false,
		processData: false,
		success : function(data) {
			if(data.success){
				document.location.href=data.url;
			}
			$.toast("购买成功！");
		},
		error:function(data){
			console.log(data);
		}
	})
	$.ajax({
		url:shopCartDeleteHtml,
		type:'POST',
		data:JSON.stringify(ShopCartImgAddr),
		dataType:'json',
		contentType:'application/json',
		processData:false,
		cache:false,
		success:function(data){
			if(data.success){
//				document.location.href='/o2o/shopcart/goShopCart';
			}
		},
	})
});
/*结算*/
/*删除*/
$('.delete').click(function(){
	$.each($('li'), function() {
		if ($(this).find("input[type=checkbox]").attr("checked")=="checked") {
			$(this).remove();
		}
	});
	$('input[type=checkbox]').attr("checked","checked");
	$('input[type=checkbox]').next("img").attr("src","../static/img/c_checkbox_on.png");
	$.each($(".content"), function() {
		if ($(this).find("li").length==0) {
			$(this).remove();
		}
	});
	hide();
	total();
});
/*删除*/

})
