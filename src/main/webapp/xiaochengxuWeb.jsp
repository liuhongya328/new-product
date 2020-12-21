<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
<head>
<meta name="viewport" content="width=device-width,minimum-scale=1.0, maximum-scale=2.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=emulateIE7" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/skin_/login.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.select.js"></script>   
<title>睿谟汽配云平台</title>
<style type="text/css">
 input{
 	    border: 1px solid #ccc;
 	   	padding: 7px 0px;                 
 	   	border-radius: 3px; /*css3属性IE不支持*/                 
 	   	padding-left:5px; 
            }
 </style>
</head>
<body>
	<div id="containerMobil">
		<div id="bd">
				<div style="text-align: center;font-size:16px;">
				<div style="margin-top:30px;">
						<label for="productName" >产品名称：</label>
						<input type="text" name="productName" id="productName" value="${product.description}" readonly="readonly"/>
					</div>
					<div style="margin-top:30px;">
						<label for="userName" >产品编号：</label>
						<input type="text" name="productId" id="productId" value="${product.productId}" readonly="readonly"/>
					</div>
					<div style="margin-top:30px;">
						<label for="password">保修月数：</label>
						<input type="text" id="mouthNum" name="mouthNum" value="${product.monthNum}" readonly="readonly"/>
					</div>
					
					<div style="margin-top:30px;">
                    <label for="enterTime">激活时间：</label>
                    <input type="text" id="enterTime" name="enterTime" value="${product.activateTime}" readonly="readonly"/>
                	</div>
					
					<div style="margin-top:30px;">
                    <label for="isoverTime">到期时间：</label>
                    <input type="text" id="isoverTime" name="isoverTime" value="${product.isoverTime}" readonly="readonly"/>
                	</div>
                	<div style="margin-top:30px;">
                    <label for="isoverTime">倒计时：</label>
                    <input type="text" id="daojishi" name="daojishi"  readonly="readonly"/>
                	</div>
                	
					<!-- <div id ="jihuo" style="margin-top:70px;">
						<input type="button"  value="立即激活" />
					</div> -->
				</div>
				<!-- <h1 style="text-align: center;"></h1> -->
		</div>
		<div id="ft" class="ue-clear">
    	<div class="ft1 ue-clear">
        	<i class="ft-icon1"></i> <span>河北睿谟网络科技有限公司</span>
        </div>
       
    </div>
	</div>
</body>
<script type="text/javascript">
	var height = $(window).height() > 445 ? $(window).height() : 445;
	$("#containerMobil").height(height);
	var bdheight = ($(window).height() - $('#bd').height()) / 2 - 20;
	$('#bd').css('padding-top', bdheight);
	$(window).resize(function(e) {
        var height = $(window).height() > 445 ? $(window).height() : 445;
		$("#containerMobil").height(height);
		var bdheight = ($(window).height() - $('#bd').height()) / 2 - 20;
		$('#bd').css('padding-top', bdheight);
    });	
	
	  var isoverTime = $("#isoverTime").val();//获取到期时间
	  if(isoverTime != ""){
		// 在HTML中写了一个h1标签，然后引入到js中
		   var h = document.getElementsByTagName("h1")[0];
		   
		   function daojiShi(storptimes) {
		   var newtime = new Date() //获取当前时间
		   var storptime = new Date(storptimes) //获取截止时间

		   var mistiming = storptime.getTime() - newtime.getTime() //   获取截止时间距离现在的毫秒差

		   var days = Math.floor(mistiming / 1000 / 60 / 60 / 24); //获取天数   
		   var hours = Math.floor(mistiming / 1000 / 60 / 60 % 24); // 获取小时
		   var minutes = Math.floor(mistiming / 1000 / 60 % 60); //获取分钟数
		   var seconds = Math.floor(mistiming / 1000 % 60); //获取秒数

		// 判断天、时、分、秒是不是两位数，如果是直接输出，如果不是在前边加个0;    
		   days <10 ? days = "0"+days : days;    
		   hours <10 ? hours = "0"+hours : hours;
		   minutes <10 ? minutes = "0"+minutes : minutes;
		   seconds <10 ? seconds = "0"+seconds : seconds;

		// 第一种连接方法
		    var rels = days + "天" + hours + "时" + minutes + "分" + seconds + "秒";   
		// 第二种连接方法
		   //var rels = `${days}天${hours}时${minutes}分${seconds}秒`
			
		// 判断时间差是不是正数，就是截止时间是不是比现在的时间晚
		   var mis = mistiming > 0? rels:"请输入正确的时间"
			   $("#daojishi").val(mis);
		   return  mis
		}  

		//用计时器去实现它
		   setInterval(function(){
		//把倒计时放在h1标签中
		  daojiShi($("#isoverTime").val())
		},1000)//时间我们设置1000毫秒，也就是1秒钟
		
	  }else{
		  $("#isoverTime").val("未激活");
		  $("#enterTime").val("未激活");
		  $("#daojishi").val("未激活");
	  }
	  
	
</script>
</html>