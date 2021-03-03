/**
* 
* 
*/
function changeVerifyCode(img) {
	img.src="../GooglCodeKaptcha?"+Math.floor(Math.random()*100);
}
function getQueryString(name){
	var reg=new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
	var r=window.location.search.substr(1).match(reg);
	if(r!=null){
		return decodeURIComponent(r[2]);
	}
	return '';
}
//时间格式函数
function appendZero(obj) {
    obj = Number(obj);
    if (obj < 10) return "0" + "" + obj;
    else return obj;
}
//时间转换
function getTime(dateTime)  {
	let date=new Date();
	date.setTime(dateTime)
    return date.getFullYear() + '-' + appendZero(date.getMonth() + 1) + '-' + appendZero(date.getDate());
}
function getTimeUp(dateTime)  {
	let date=new Date();
	date.setTime(dateTime)
    return date.getFullYear() + '-' + appendZero(date.getMonth() + 1) + '-' + appendZero(date.getDate())+'  '+appendZero(date.getHours())+':'+appendZero(date.getMinutes());
}