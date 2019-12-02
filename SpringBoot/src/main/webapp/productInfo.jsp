<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="baseTitle.jsp"%>
<html>
<head>
<%@ include file="baseJsCss.jsp"%>
<script type="text/javascript" src="/productJs/productInfo.js"></script>
<title>产品信息</title>
<style>
.button {
    width: 60px;
    height: 27px;
    }
</style>
</head>
<body>
<div id="container">
	<div id="hd"></div>
    <div id="bd">
    	<div id="main">
        	<div class="search-box ue-clear">
            	<div class="search-area">
                    <div class="kv-item ue-clear">
                        <label>查询条件：</label>
                        <input id="search" type="text"  placeholder="请输入产品编号" style="text-align: start; padding: 0px 3px 0px 2px; margin: 0px; height: 25px; line-height: 25px; width: 243px;">
                    </div>
                </div>
                <div class="search-button" style="margin-left:10px;">
                	<input class="button" type="button" onclick="queryUserList()" value="搜索" />
                </div>
             </div>
             
             <div class="table">
            	<div class="opt ue-clear">
                	<span class="optarea">
                        <a id="printQRFrame"  class="erweima">
                            <i class="icon"></i>
                            <span class="text">打印二维码</span>
                        </a> 
                        <span id="addProduct" class="optarea">
                         <a onclick="add();" class="add">
                            <i class="icon"></i>
                            <span class="text">生成二维码</span>
                        </a>
                    	</span>
                    </span>
                </div>
                <table id="productList"></table>
            </div>
            <div id="dlgdiv" class="easyui-dialog"
		     style="width: 500px; height: 400px; padding: 10px 10px" closed="true"
		     buttons="#dlgdiv-buttons">
		    <div id="divlarge"></div>
			</div>
        </div>
    </div>
</div>
<iframe id="print" stc=""  style="width: 500px; height: 500px; display: none;"></iframe>
</body>
<script type="text/javascript">
$(function(){
	queryUserList();
	$("body").on("click","#printQRFrame",function(){
		var product = $("#productList").datagrid('getSelected');
		if (product == null || product.qrcodeUrl == null) {
			$.messager.alert("提示", "请选择一条记录！", "info");
			return false;
		} else {
			$.messager.confirm('打印', '您确认打印吗？', function(r){
				if (r){
					var url ="/toPrint?qrcodeUrl="+product.qrcodeUrl;
					beforePrint(url); 
				}
			});
			printStatus(product.productId,product.printStatus);
		}
		
	 });
	var org='${sessionScope.user.org}';
	if(org=='管理员' ){
		$("#addProduct").attr("style","display:none;");//隐藏div
	}
});
//打印之前
function beforePrint(url){
    $("#container").hide();
    $("#print").show();
    $("#print").attr("src",url);
}
 //打印之后回调
function printCallBack(){
    $("#container").show();
    $("#print").hide();
    $("#print").attr("src","");
    $("#productList").datagrid('reload');
}

 //改变打印状态
function printStatus(productId,printStatus) {
	$.ajax({  
		type: "POST",
		url:"/updateProduct",
		async: false,
		data: {"productId":productId,"printStatus":printStatus},
   		success: function(data){  
   			if(data.returnCode =="success")
   			{
   				$("#productList").datagrid('reload');
   			}
		}                                            
      }); 
}
</script>
</html>