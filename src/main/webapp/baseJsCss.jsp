<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="baseTitle.jsp"%>
<html>
<meta http-equiv="X-UA-Compatible" content="IE=emulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/skin_/table.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<link href="js/jquery-easyui-theme/metro-standard/easyui.css" rel="stylesheet" type="text/css"/>
<script>
//url：窗口调用地址，title：窗口标题，width：宽度，height：高度，shadow：是否显示背景阴影罩层  
function showApproveDialog(url, title, width, height, shadow, text) {  
	var content = '<iframe id="open-age" name="open-page" src="' + url + '" width="100%" height="99%" frameborder="0"></iframe>';  
	var boarddiv = '<div id="msgDialog" title="' + title + '" style="overflow:hidden;"></div>'//style="overflow:hidden;"可以去掉滚动条  
	$(document.body).append(boarddiv);  
	var win = $('#msgDialog').dialog({  
    	content: content,  
    	width: width,  
    	height: height,  
    	modal: shadow,  
    	title: title,  
    	onClose: function () {  
        $(this).dialog('destroy');//后面可以关闭后的事件  
    	} ,
     	buttons: [{
                text:text,
               // iconCls:'fa fa-save',
                handler:function(){
                	window.frames["open-page"].window.approve();                      
                }
            },{
                text:'取消',
                //iconCls:'icon-cancel',
                handler:function(){
                	 $('#msgDialog').dialog('destroy');//后面可以关闭后的事件  
                }
            }]   
		});  
	win.dialog('open');  
} 

function showDialog(url, title, width, height, shadow) {  
	var content = '<iframe id="open-age" name="open-page" src="' + url + '" width="100%" height="99%" frameborder="0"></iframe>';  
	var boarddiv = '<div id="msgDialog" title="' + title + '"></div>'//style="overflow:hidden;"可以去掉滚动条  
	$(document.body).append(boarddiv);  
	var win = $('#msgDialog').dialog({  
    	content: content,  
    	width: width,  
    	height: height,  
    	modal: shadow,  
    	title: title,  
    	onClose: function () {  
        $(this).dialog('destroy');//后面可以关闭后的事件  
    	}   
		});  
	win.dialog('open');  
} 
</script>
