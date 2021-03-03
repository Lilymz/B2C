$(function() {
	// 根据网页地址shopId字段获取ShopId值
	var shopId = getQueryString('ShopId');
	var GetUrl = '/o2o/shopadmin/getproducttype?ShopId=' + shopId;
	var deleteUrl = '/o2o/shopadmin/deleteproducttype?productTypeId=';
	var addUrl= '/o2o/shopadmin/batchproducttype'
	getproducts();
	function getproducts() {
		$.ajax({
			url : GetUrl,
			type : "get",
			dataType : "json",
			success : function(data) {
				if(data.success){
					HandleList(data.ProductTypeList);
				}else{
					$.toast("请至少添加一个商品类别");
				}
			}
		})
	}
	function HandleList(data) {
		var Html = '';
		data.map(function(item, index) {
					Html += '<div class="row productType now"> <div class="col-33">'
							+ item.productTypeName
							+ '</div><div class="col-33">'
							+ item.priority
							+ '</div><div class="col-33"><a  data-id='
							+ item.productTypeId
							+ ' href="#" class="button button-round active">删除</a></div></div>'
				})
		$(".append").html(Html);
	}
	$(".append").on('click', ".row a", function(e) {
		let target = e.currentTarget;
		$.confirm('确定吗', function() {
			$.ajax({
				url : deleteUrl + target.dataset.id,
				type : "get",
				dataType : "json",
				success : function(data) {
					$.toast(data.Msg);
					getproducts();//重新获取新数据
				}
			})
		})
	})
	$("#new").click(function() {
		var newProductType='';
		newProductType='<div class="row productType temp"> <div class="col-33">'+
		'<input style="width:100%;height:32px;background:#eee;border:0px;text-align: center;" class="productType-input typeNmae" type="text" placeholder="类别名">'+'</div>'+
		'<div class="col-33">'+'<input style="width:100%;height:32px;background:#eee;border:0px;text-align: center;" class="productType-input priority" type="number" placeholder="优先级">'+
		'</div>'+'<div class="col-33"><a style="width:100%;height:32px" data-id="0" href="#" class="button button-round active">删除</a></div></div>';
		$(".append").append(newProductType);
	})
	$("#newsubmit").click(function() {
		var tempName=$(".temp");
		var productTypeList=[];
		tempName.map(function(index,item) {
			var tempObj={};
			tempObj.productTypeName=$(item).find(".typeNmae").val();
			tempObj.priority=$(item).find(".priority").val();
			if(tempObj.productTypeName&&tempObj.priority){
				productTypeList.push(tempObj);
			}
		})
		$.ajax({
			url:addUrl,
			type:'POST',
			data:JSON.stringify(productTypeList),
			contentType:'application/json',
			success:function(data){
				if(data.success){
					$.toast("提交成功");
					getproducts();
				}else{
					$.toast("提交失败");
					console.log(data.errMsg)
				}
			}
		})
	})
})