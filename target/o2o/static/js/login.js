function showCheck(a){
	var c = document.getElementById("myCanvas");
  var ctx = c.getContext("2d");
	ctx.clearRect(0,0,1000,1000);
	ctx.font = "80px 'Microsoft Yahei'";
	ctx.fillText(a,0,100);
	ctx.fillStyle = "rgba(255,255,255,.9)";
}
var code ;    
function createCode(){       
    code = "";      
    var codeLength = 4;
    var selectChar = new Array(1,2,3,4,5,6,7,8,9,'a','b','c','d','e','f','g','h','j','k','l','m','n','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');      
    for(var i=0;i<codeLength;i++) {
       var charIndex = Math.floor(Math.random()*60);
      code +=selectChar[charIndex];
    }      
    if(code.length != codeLength){
      createCode();      
    }
    showCheck(code);
}
  //验证验证码是否一致        
function validate () {
    var inputCode = document.getElementById("J_codetext").value.toUpperCase();
    var codeToUp=code.toUpperCase();
    if(inputCode.length <=0) {
      document.getElementById("J_codetext").setAttribute("placeholder","输入验证码");
      createCode();
      return false;
    }
    else if(inputCode != codeToUp ){
      document.getElementById("J_codetext").value="";
      document.getElementById("J_codetext").setAttribute("placeholder","验证码错误");
      createCode();
      return false;
    }
    else {
      document.getElementById("J_codetext").value="";
      createCode();
      return true;
    }
}
function VerifyLogin(){
		if(!validate()){
		return ;
		}
		var userName=$('.userName').val();
		var userPassword=$('.userPassword').val();
		var verifyUserURL='/o2o/user/verifyUser';
		
		if(userName==""||userPassword==""){
			alert("用户名或密码为空！");
		}else{
			var Formvalue=new FormData();
			Formvalue.append("userName",userName);
			Formvalue.append("userPassword",userPassword);
			//登录验证
			$.ajax({
				url:verifyUserURL,
				type:'POST',
				data:Formvalue,
				processData:false,
				contentType:false,
				cache:false,
				success:function(data){
					if(data.success){
						document.location.href=data.url;;
						
					}else{
						alert(data.errMsg);
					}
				},
				error:function(data){
					$.toast("登录失败！");
					console.log(data.errMsg);
				}
			})
		}
}
function AdminVerifyLogin(){
	var adminName=$('.adminName').val();
	var password=$('.password').val();
	var verifyAdminURL='/o2o/shopadmin/verifyShopAdmin';
	
	if(adminName==""||password==""){
		alert("用户名或密码为空！");
	}else{
		var Formvalue=new FormData();
		Formvalue.append("adminName",adminName);
		Formvalue.append("password",password);
		//登录验证
		$.ajax({
			url:verifyAdminURL,
			type:'POST',
			data:Formvalue,
			processData:false,
			contentType:false,
			cache:false,
			success:function(data){
				if(data.success){
					document.location.href=data.url;
				}else{
					alert(data.errMsg)
					window.location.reload();
				}
			},
			error:function(data){
				alert("登录失败！");
				console.log(data.errMsg);
			}
		})
	}
}