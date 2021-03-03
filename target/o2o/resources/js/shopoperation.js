/**
 * 1.从后台获取到区域信息和分类信息,之后填写好相应参数，传参使用ajax到后台
 * $.getJSON(url,data, success(data, textStatus, jqXHR))
 * url是必选参数，表示json数据的地址;data是可选参数，用于请求数据时发送数据参数;
 * success是可参数，这是一个回调函数，用于处理请求到的数据
 */
//获取页面参数
$(function() {
	var ShopId=getQueryString("ShopId");
	var id=(ShopId!=null)?ShopId:'';
	var initUrl='';
	var modifyUrl='/o2o/shopadmin/modifyshop?ShopId'+ShopId;
	var RegisterUrl='/o2o/shopadmin/registershop';
	if(id==''){
		initUrl= '/o2o/shopadmin/getshopinitinfo'
		getShopInitInfo();
	}else{
		initUrl = '/o2o/shopadmin/getshopById?ShopId='+ShopId;
		getShopModify();
	}
	function getShopModify() {
		//这里从后台获取到区域信息和分类信息
		console.log(initUrl);
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '<option data-id="' + data.shopTypeList[0].shoptypeId + '">'+ data.shopTypeList[0].shoptypeName + '</option>';
				var shopname=data.shop.shopName;
				var shopaddr=data.shop.shopAddr;
				var phone=data.shop.phone;
				var shopdesc=data.shop.shopDesc;
				var tempAreaHtml = '';
				console.log(data.shopTypeList[0])
				data.arealist.map(function(item,index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
					+ item.areaName + '</option>';
				})
				//获取到数据写入到页面
				$("#shop-type").attr("disabled","disabled");
				$("#shop-type").html(tempHtml)
				$("#area").html(tempAreaHtml);
				$("#shop-name").val(shopname);
				$("#shop-addr").val(shopaddr);
				$("#shop-phone").val(phone);
				$("#shop-desc").val(shopdesc);
				
			}
		});
		//提交数据
		$("#submit").click(function() {
			var shop = {};//这里创建一个明为shop的json对象，以下是传值
			shop.shopName = $("#shop-name").val();
			shop.shopAddr = $("#shop-addr").val();
			shop.phone = $("#shop-phone").val();
			shop.shopDesc = $("#shop-desc").val();
			shop.shopType = {
				shoptypeId : $("#shop-type").find('option').not(function() {
					//jQuer方法：先获取到shop类型然后找到option标签,find(expr|obj是jQuery对象|DOMele) not(expr|ele|fn)删除匹配的元素
					return !this.selected;//这里的this是所有option标签
				}).data("id")//data()为存入一个数据
			};
			shop.area = {
				areaId : $("#area").find('option').not(function() {
					return !this.selected;
				}).data("id")
			};
			//上传的文件类型
			var shopImg = $("#shop-img")[0].files[0];
			var formValue = new FormData();//这里是创建一个存放Form表单数据的对象
			formValue.append("showImg", shopImg);//前台接受到的数据接受进对象里
			formValue.append("shopStr", JSON.stringify(shop));//前台接受到的数据接受进对象里
			var verifyCodeActual=$("#l-kaptcha").val();
			if(!verifyCodeActual){
				$.toast("请输入验证码！");
				return ;
			}
			formValue.append("verifyCodeActual", verifyCodeActual);
			formValue.append("ShopId", ShopId);
			
			$.ajax({
				url : modifyUrl,
				type : 'POST',
				data : formValue,
				contentType : false,
				processData : false,
				cache : false,
				success : function(data) {
					if (data.success) {
						$.toast('提交成功！');
						console.log(data.url);
						setTimeout(function(){
							document.location.href=data.url;
						},700);
					} else {
						$.toast(data.errMsg);
					}
				},
				error: function(){
					$("#kaptcha-img").click();
				}
			})
		})
	}
	function getShopInitInfo() {
		//这里从后台获取到区域信息和分类信息
		console.log(initUrl);
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopTypeList.map(function(item, index) {//map是遍历所有数据
					tempHtml += '<option data-id="' + item.shoptypeId + '">'
							+ item.shoptypeName + '</option>';
				});
				data.areaList.map(function(item, index) {//map是遍历所有数据
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				//获取到数据写入到页面
				$("#shop-type").html(tempHtml);
				$("#area").html(tempAreaHtml);
			}
		});
		//提交数据
		$("#submit").click(function() {
			var shop = {};//这里创建一个明为shop的json对象，以下是传值
			shop.shopName = $("#shop-name").val();
			shop.shopAddr = $("#shop-addr").val();
			shop.phone = $("#shop-phone").val();
			shop.shopDesc = $("#shop-desc").val();
			shop.shopType = {
				shoptypeId : $("#shop-type").find('option').not(function() {
					//jQuer方法：先获取到shop类型然后找到option标签,find(expr|obj是jQuery对象|DOMele) not(expr|ele|fn)删除匹配的元素
					return !this.selected;//这里的this是所有option标签
				}).data("id")//data()为存入一个数据
			};
			shop.area = {
				areaId : $("#area").find('option').not(function() {
					return !this.selected;
				}).data("id")
			};
			//上传的文件类型
			var shopImg = $("#shop-img")[0].files[0];
			var formValue = new FormData();//这里是创建一个存放Form表单数据的对象
			formValue.append("showImg", shopImg);//前台接受到的数据接受进对象里
			formValue.append("shopStr", JSON.stringify(shop));//前台接受到的数据接受进对象里
			var verifyCodeActual=$("#l-kaptcha").val();
			if(!verifyCodeActual){
				$.toast("请输入验证码！");
				return ;
			}
			formValue.append("verifyCodeActual", verifyCodeActual);
			console.log(formValue.get("verifyCodeActual"));
			$.ajax({
				url : RegisterUrl,
				type : 'POST',
				data : formValue,
				contentType : false,
				processData : false,
				cache : false,
				success : function(data) {
					if (data.success) {
						$.toast('提交成功！');
						setTimeout(function(){
							document.location.href=data.url;
						},700);
					} else {
						$.toast('提交失败！' + data.errMsg);
					}
				},
				error: function(){
					$("#kaptcha-img").click();
				}
			})
		})
	}
})