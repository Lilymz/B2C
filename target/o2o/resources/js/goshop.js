$(function() {
	var shopId=getQueryString("ShopId");
	var shopmanagmentUrl='/o2o/shopadmin/getshopById?ShopId='+shopId;
	$.getJSON(shopmanagmentUrl,function(data){
		if(data.success){
			$("#shopInfo").attr('href','/o2o/shopadmin/shopoperation?ShopId='+shopId);
			$("#productType").attr('href','/o2o/shopadmin/producttype?ShopId='+shopId);
			$("#productManage").attr('href','/o2o/shopadmin/productManage?ShopId='+shopId);
			$("#orderManage").attr('href','/o2o/shopadmin/orderManagement?ShopId='+shopId);
		}else{
			alert(data.errMsg);
			document.location.href=data.url;
		}
	})
})