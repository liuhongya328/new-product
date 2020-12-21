<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="baseTitle.jsp"%>
<html>
<head>
<%@ include file="baseJsCss.jsp"%>
<script type="text/javascript" src="/supplierJs/addSupplier.js"></script>
<title>新增厂商信息</title>
</head>
<body>
<form id="addSupplierForm" enctype="multipart/form-data"  class="easyui-form" method="post">
<div id="container">
    <div id="bd">
    	<div id="main">
            <div class="subfild-content base-info">
            	<div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>厂商名</label>
                	<div class="kv-item-content">
                    	<input type="text" id="suppliername" name="suppliername" />
                    </div>
                </div>
                <div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>联系人</label>
                	<div class="kv-item-content">
                    	<input type="text" id="contact" name="contact"   />
                    </div>
                </div>
                <div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>电话</label>
                	<div class="kv-item-content">
                    	<input type="text" id="phonenumber" name="phonenumber"  />
                    </div>
                </div>
                 <div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>地址</label>
                	<div class="kv-item-content">
                        <input type="text" id="address" name="address" />
                    </div>
                </div>
                
             
            </div>
        </div>
    </div>
</div>
</form>
</body>
</html>
