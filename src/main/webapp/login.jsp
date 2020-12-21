<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=emulateIE7" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/skin_/login.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.select.js"></script>

<title>睿谟汽配云平台</title>
</head>
<body>
	<div id="container">
		<div id="bd">
			<div id="main">
				<div class="login-box">
					<div id="logo"></div>
					<h1></h1>
					<div class="input username" id="username" style="margin: 0 auto">
						<label for="userName">手机号</label> <span></span> <input type="text"
							name="userName" id="userName" />
					</div>
					<div class="input psw" id="psw">
						<label for="password">密&nbsp;&nbsp;&nbsp;&nbsp;码</label> <span></span>
						<input type="passWord" id="passWord" name="passWord" />
					</div>
					
					<div class="input validate" id="validate">
						<input id="register" type="text" class="button" value="没有账号？去注册一个吧" readonly="readonly" />
					</div>
					<div id="btn" class="loginButton">
						<input type="button" class="button" value="登录" />
					</div>
				</div>
			</div>
		</div>

	</div>

</body>
<script type="text/javascript">
	var height = $(window).height() > 445 ? $(window).height() : 445;
	$("#container").height(height);
	var bdheight = ($(window).height() - $('#bd').height()) / 2 - 20;
	$('#bd').css('padding-top', bdheight);
	$(window).resize(function(e) {
        var height = $(window).height() > 445 ? $(window).height() : 445;
		$("#container").height(height);
		var bdheight = ($(window).height() - $('#bd').height()) / 2 - 20;
		$('#bd').css('padding-top', bdheight);
    });
	$('select').select();
	$("#register").click(function(){ 
		/* $(this).css({'color':'red'})} */
		document.location.href = "register";
		}
	);
	$('.loginButton').click(function(e) {
		var userName = $('#userName').val();// 登陆账号，区分大小写
		var password = $('#passWord').val();
		if (userName == "") {
			//$("#login-error").text("用户名不能为空");
			alert("用户名不能为空");
			$("#userName").focus();
			return;
		}
		if (password == "") {
			//$("#login-error").text("密码不能为空");
			alert("密码不能为空");
			$("#passWord").focus();
			return;
		}
		//用户名密码不为空的话，执行登陆方法
    	$.ajax({
			type : 'post',
			url : '/user/login',
			data : {
				'userName':userName,
				'passWord':password,
			},
			asycn : false,
			success : function(data) {
				//判断是否登陆成功，如果登陆成功则跳转页面
				if(data.success==true){
					document.location.href = "toMain";
				}else{
					alert("帐号或者密码错误，请重新输入！"); 
					$('#passWord').val("");
				}
			},
			error:function(data){
				console.log("error");
			}
		});
    });
	
</script>

</html>
