<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="baseTitle.jsp"%>
<html>
<head>
<%@ include file="baseJsCss.jsp"%>
<script type="text/javascript" src="/registerCodeJs/addRegisterCode.js"></script>
<title>生成注册码</title>
</head>
<body>
<form id="addRegisterForm" enctype="multipart/form-data"  class="easyui-form" method="post">
    	<div id="main">
            <div class="subfild-content base-info">
               <div class="kv-item ue-clear">
               <label>角  色：</label>
                <!-- <span class="choose">
                   <span class="checkboxouter">
                       <input type="radio" name="orgId" value="用户" checked="checked"/>
                       <span class="radio"></span>
                   </span>
                   <span class="text">用户</span>
               </span> -->
	           	<span class="choose" id="orgId">
	                   <span class="checkboxouter">
	                       <input type="radio" name="orgId"  value="管理员" checked="checked"/>
	                       <span class="radio"></span>
	                   </span>
	                   <span class="text">管理员</span>
	             </span>
                        
                </div>
            	<div class="kv-item ue-clear">
                	<label>厂商名：</label>
                	<select id="supplierId" class="easyui-combobox" style = "width:150px;" name="supplierId" 
			    		data-options="editable:false,valueField:'suppliername',textField:'suppliername',panelWidth:150,url:'/findAllSupplier'"
			    		></select>
                </div>
            </div>
    </div>
</form>
<script type="text/javascript">
$(function(){
	var org='${sessionScope.user.org}';
	if(org!='管理员'){
		$("#orgId").attr("style","display:none;");//隐藏div
	}
});
</script>
</body>
</html>
