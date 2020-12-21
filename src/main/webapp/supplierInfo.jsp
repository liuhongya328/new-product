<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="baseTitle.jsp"%>
<html>
<head>
<%@ include file="baseJsCss.jsp"%>
<script type="text/javascript" src="/supplierJs/supplierInfo.js"></script>
<style>
.button {
    width: 60px;
    height: 27px;
    }
</style>
<title>厂商信息</title>
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
                        <input id="search" type="text"  placeholder="请输入厂商名" style="text-align: start; padding: 0px 3px 0px 2px; margin: 0px; height: 25px; line-height: 25px; width: 243px;">
                    </div>
                </div>
                <div class="search-button" style="margin-left:10px;">
                	<input class="button" type="button" onclick="querySuppliersList()" value="搜索" />
                </div>
             </div>
             
            <div class="table">
            	<div class="opt ue-clear">
                	<span class="optarea">
                        <a onclick="add();" class="add">
                            <i class="icon"></i>
                            <span class="text">添加</span>
                        </a>
                        <a onclick="dele();" class="delete">
                            <i class="icon"></i>
                            <span class="text">删除</span>
                        </a>
                        
                        <a onclick="update();" class="statistics">
                            <i class="icon"></i>
                            <span class="text">编辑</span>
                        </a>
                    </span>
                </div>
                <table id="supplierList"></table>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
$(function(){
	querySuppliersList();
});

</script>
</html>
