<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="baseTitle.jsp"%>
<html>
<head>
<%@ include file="baseJsCss.jsp"%>
<script type="text/javascript" src="/productJs/addProduct.js"></script>
<title>新增产品二维码信息</title>
</head>
<body>
<form id="addProductForm" enctype="multipart/form-data"  class="easyui-form" method="post">
<div id="container">
    <div id="bd">
    	<div id="main">
            <div class="subfild-content base-info">
            	<div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>厂家备注：</label>
                	<div class="kv-item-content">
                    	<input type="text" id="description" name="description" placeholder="厂家备注"/>
                    </div>
                </div>
                <div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>保修月数：</label>
                	<div class="kv-item-content">
                    	<input type="text" id="monthNum" name="monthNum"  placeholder="保修月数" />
                    </div>
                </div>
                <div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>生成个数：</label>
                	<div class="kv-item-content">
                    	<input type="text" id="num" name="num"  placeholder="生成个数"/>
                    </div>
                </div>
                <input type="hidden" id="orgId" name="orgId"  value="${user.org}"/>
                <input type="hidden" id="openId" name="openId"  value="${user.unionid}"/>
            </div>
        </div>
    </div>
</div>
</form>
</body>
</html>
