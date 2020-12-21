<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="baseTitle.jsp"%>
<html>
<head>
<%@ include file="baseJsCss.jsp"%>
<script type="text/javascript" src="/registerCodeJs/registerCodeInfo.js"></script>
<style>
.button {
    width: 60px;
    height: 27px;
    }
</style>
<title>注册码信息</title>
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
                        <input id="search" type="text"  placeholder="请输入注册码、机构" style="text-align: start; padding: 0px 3px 0px 2px; margin: 0px; height: 25px; line-height: 25px; width: 243px;">
                    </div>
                </div>
                <div class="search-button" style="margin-left:10px;">
                	<input class="button" type="button" onclick="queryRegisterCodeList()" value="搜索" />
                </div>
             </div>
             
             <div class="table">
            	<div class="opt ue-clear">
                	<span class="optarea">
                         <a onclick="add();" class="add">
                            <i class="icon"></i>
                            <span class="text">生成注册码</span>
                        </a>
                    </span>
                </div>
                <table id="registerCodeList"></table>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
$(function(){
	queryRegisterCodeList();
});

</script>
</html>
