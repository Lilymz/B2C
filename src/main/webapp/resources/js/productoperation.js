$(function() {
	// 从URL获取ProductId参数值
	var productId = getQueryString("productId");
	// 通过编辑productId获取商品信息URL
	var infoUrl = '/o2o/shopadmin/getproductByid?productId=' + productId;
	// 获取当前店铺设定的商品类别列表的URL
	var productTypeyUrl = '/o2o/shopadmin/getproductTypelist';
	// 更新商品信息的URL
	var productPostUrl = '/o2o/shopadmin/modifyproduct';
	// 因为该商品添加和编辑是同一个界面,该标识符用来确定编辑还是添加;
	var isEdit = false;
	if (productId) {
		// 若有productId则为编辑操作
		getInfo(productId);
		isEdit = true;
	} else {
		getCategory();
		productPostUrl = '/o2o/shopadmin/addproduct';
	}
	function getInfo(id) {
		$.getJSON(infoUrl,function(data) {
			if (data.success) {
			// 从返回的JSON当中获取product对象的信息,并给与表单
				var product = data.Product;
				$("#product-name").val(product.productName);
				$("#product-desc").val(product.productDesc);
				$("#priority").val(product.priority);
				$("#normal-price").val(product.normalPrice);
				$("#promotion-price").val(
				product.promotionPrice);
				// 获取原本的商品类别以及该店铺的所有商品类别列表
				var optionHtml = "";
				var optionBeforeType = data.productTypeList;
				console.log(typeof optionBeforeType[0]);
				console.log(optionBeforeType[0]);
				var optionSelected = product.productType.productTypeId;
				// 生成前端的HTML商品列表，并默认选择编辑前的商品类别
				optionBeforeType.map(function(item,index) {
					var isSelect = optionSelected === item.productTypeId ? 'selected': '';
					optionHtml += '<option data-value="'
					    + item.productTypeId +'"'
						+ isSelect + '>'
						+ item.productTypeName
						+ '</option>'
				});
				$('#category').html(optionHtml);
			}
		});
	}
	//为商品添加操作提供该店铺下的所有商品类别列表
	function getCategory() {
		$.getJSON(productTypeyUrl,function(data){
			if (data.success) {
				var productCategoryList=data.ProductTypeList;
				var optionHtml = "";
				productCategoryList.map(function(item,index) {
					optionHtml += '<option data-value='
						+ item.productTypeId + '>'
						+ item.productTypeName+ '</option>'
				});
				$('#category').html(optionHtml);
			}
		});
	}
	//控件的监听以及添加（实现）
	$('.detail-img-div').on('change','.detail-img:last-child',function(){
		if($('.detail-img').length<6){
			$('#detail-img').append('<input type="file" class="detail-img">');
		}
	})
	//submit
	$("#submit").click(function() {
		var product={};
		//把所有在表单中填写的数据提交到后台
		product.productId=productId;
		product.productName=$("#product-name").val();
		product.productDesc=$("#product-desc").val();
		product.priority=$("#priority").val();
		product.normalPrice=$("#normal-price").val();
		product.promotionPrice=$("#promotion-price").val();
		//获取选定的商品类别值
		product.productType={
				productTypeId:$("#category").find('option').not(function() {
					return !this.selected;
				}).data('value')//这个用来取数据的值
		};
		var thumbnail=$("#small-img")[0].files[0];
		//生成表单对象
		var formData=new FormData();
		formData.append('thumbnail',thumbnail);
		
		var clearImgs=$(".detail-img");
		for (var i = 0; i < clearImgs.length; i++) {
			if((clearImgs[i].files[0]!=null)){
				formData.append('productImg'+i,clearImgs[i].files[0]);
			}
		}
		/*$("#detail-img").map(function(index,item) {
			
			if($(".detail-img")[index].files[0].length>0){
			formData.append('productImg'+index,$(".detail-img")[index].files[0]);
			}
		})*/
		formData.append('productStr',JSON.stringify(product));
		var verifyCodeActual=$("#l-kaptcha").val();
		if(!verifyCodeActual){
			$.toast("请输入验证码！");
			return;
		}
		formData.append('verifyCodeActual',verifyCodeActual);
		$.ajax({
			url:productPostUrl,
			type:'POST',
			data:formData,
			contentType:false,
			processData:false,
			cache:false,
			success:function(data){
				if(data.success){
					$.toast("提交成功！");
					document.location.href=data.url;
				}else{
					$.toast("提交失败！");
					console.log(data.errMsg);
					$('#kaptcha-img').click();
				}
			}
		})
	})
})