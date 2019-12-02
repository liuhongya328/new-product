<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="baseTitle.jsp"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=emulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/skin_/main.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.dialog.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<title>睿谟汽配云平台</title>
</head>

<body>
<div id="container">
	<div id="hd">
    	<div class="hd-top">
            <h1 class="logo"></h1>
            <div class="user-info">
                <a href="javascript:;" class="user-avatar"><span></span></a>
                <span class="user-name" id="userName"></span>
            </div>
            <div class="setting ue-clear">
                <ul class="setting-main ue-clear">
               		<li style="width: 150px"><a href="javascript:;">QQ:2591158822</a</li>
                    <li><a href="javascript:;" class="updatePwd">修改密码</a></li>
                    <li><a href="javascript:;" class="exit">退出</a></li>
                   <!--  <li><a href="javascript:;" class="close-btn exit"></a></li> -->
                </ul>
            </div>
        </div>
            <div class="nav-btn">
            	<a href="javascript:;" class="nav-prev-btn"></a>
                <a href="javascript:;" class="nav-next-btn"></a>
            </div>
        </div>
    </div>
    <div id="bd">
        <iframe width="100%" height="100%" id="mainIframe" src="nav.jsp" frameborder="0"></iframe>
    </div>
    <div id="ft" class="ue-clear">
    	<div class="ft1 ue-clear">
        	<i class="ft-icon1"></i> <span>河北睿谟网络科技有限公司</span>
            <em>Digital Pertal</em>
        </div>
        <div class="ft2 ue-clear">
        	<span>Call Center</span>
            <em>V1.0 2019</em>
            <i class="ft-icon2"></i>
        </div>
    </div>

 </div>

<div class="exitDialog">
	<div class="content">
    	<div class="ui-dialog-icon"></div>
        <div class="ui-dialog-text">
        	<p class="dialog-content">你确定要退出系统？</p>
            <p class="tips">如果是请点击“确定”，否则点“取消”</p>
            
            <div class="buttons">
                <input type="button" class="button long2 ok" value="确定" />
                <input type="button" class="button long2 normal" value="取消" />
            </div>
        </div>
        
    </div>
</div>

<div class="updatePwdDialog">
	<div class="content">
	
	 		<!-- 	<div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>电话</label>
                	<div class="kv-item-content">
                    	<input type="text" id="phonenumber" name="phonenumber"  />
                    </div>
                </div> -->
	
            <div style="text-align: center;margin-top:30px;" >
				<label for="password"><span class="impInfo">*</span>旧密码：</label>
				<input type="passWord" id="oldPwd" name="oldPwd" />
			</div>
			<div style="text-align: center;margin-top:20px;">
				<label for="password"><span class="impInfo">*</span>新密码：</label>
				<input type="passWord" id="newPwd" name="newWord" />
			</div>
            <div class="buttons" style="text-align: center;margin-top:50px;">
                <input id="submit" type="button" class="button long2" value="确定" />
                <input id="quxiao" type="button" class="button long2" value="取消" />
            </div>
    </div>
</div>

</body>
<script type="text/javascript" src="js/core.js"></script>
<script type="text/javascript" src="js/jquery.dialog.js"></script>
<script type="text/javascript">
$("#bd").height($(window).height()-$("#hd").outerHeight()-26);

$(window).resize(function(e) {
    $("#bd").height($(window).height()-$("#hd").outerHeight()-26);

});

$('.exitDialog').Dialog({
	title:'提示信息',
	autoOpen: false,
	width:400,
	height:200
});

$('.updatePwdDialog').Dialog({
	title:'修改密码',
	autoOpen: false,
	width:400,
	height:200
});


$('.exit').click(function(){
	$('.exitDialog').Dialog('open');
});

$('.updatePwd').click(function(){
	$('.updatePwdDialog').Dialog('open');
});

$('.exitDialog input[type=button]').click(function(e) {
    $('.exitDialog').Dialog('close');
	
	if($(this).hasClass('ok')){
		 window.location.href = "loginout"	; 
		/* window.location.href = "/findProductInfoById" */
	}
});

$("#submit").click(function(){
	debugger
    var oldPwd = $('#oldPwd').val();
	var newPwd = $('#newPwd').val();
	var userPaw="${user.userPaw}";//获取登录后的用户密码
	var userId="${user.userId}";
	
	if (oldPwd == "") {
		//$("#login-error").text("用户名不能为空");
		alert("旧密码不能为空");
		$("#oldPwd").focus();
		return;
	}
	if (newPwd == "") {
		//$("#login-error").text("密码不能为空");
		alert("新密码不能为空");
		$("#newPwd").focus();
		return;
	}
	
	if(oldPwd == userPaw){
		$.ajax({
			type : 'post',
			url : '/updatePwd',
			data : {
				'userId':userId,
				'newPwd':newPwd,
			},
			asycn : false,
			success : function(data) {
				if(data.success==true){
					alert("密码修改成功，请重新登陆！");
					document.location.href = "loginout";
				}else{
					alert("服务器异常，请联系管理员！"); 
				}
			},
			error:function(data){
				console.log("error");
			}
		});
	}else{
		alert("抱歉，您输入的旧密码不正确！");
	}
});
$("#quxiao").click(function(){
	$('.updatePwdDialog').Dialog('close');
});

(function(){
	var totalWidth = 0, current = 1;
	
	$.each($('.nav').find('li'), function(){
		totalWidth += $(this).outerWidth();
	});
	
	$('.nav').width(totalWidth);
	
	function currentLeft(){
		return -(current - 1) * 93;	
	}
	
	$('.nav-btn a').click(function(e) {
		var tempWidth = totalWidth - ( Math.abs($('.nav').css('left').split('p')[0]) + $('.nav-wrap').width() );
        if($(this).hasClass('nav-prev-btn')){
			if( parseInt($('.nav').css('left').split('p')[0])  < 0){
				current--;
				Math.abs($('.nav').css('left').split('p')[0]) > 93 ? $('.nav').animate({'left': currentLeft()}, 200) : $('.nav').animate({'left': 0}, 200);
			}
		}else{

			if(tempWidth  > 0)	{
				
			   	current++;
				tempWidth > 93 ? $('.nav').animate({'left': currentLeft()}, 200) : $('.nav').animate({'left': $('.nav').css('left').split('p')[0]-tempWidth}, 200);
			}
		}
    });
	
	
	
	$.each($('.skin-opt li'),function(index, element){
		if((index + 1) % 3 == 0){
			$(this).addClass('third');	
		}
		$(this).css('background',$(this).attr('attr-color'));
	});
	
	$('.setting-skin').click(function(e) {
        $('.skin-opt').show();
    });
	$('.skin-opt').click(function(e) {
        if($(e.target).is('li')){
			alert($(e.target).attr('attr-color'));	
		}
    });
	$('.hd-top .user-info .more-info').click(function(e) {
       $(this).toggleClass('active'); 
	   $('.user-opt').toggle();
    });
	hideElement($('.user-opt'), $('.more-info'), function(current, target){
		$('.more-info').removeClass('active'); 
	});
	hideElement($('.skin-opt'), $('.switch-bar'));
})();
$(function(){
	var userName="${user.userName}";//获取登录后的用户名
	$("#userName").html(userName);
});
</script>

</html>
