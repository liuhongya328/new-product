<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="baseTitle.jsp"%>
<html>
<head>
<%@ include file="baseJsCss.jsp"%>
<script type="text/javascript" src="/supplierJs/updateSupplier.js"></script>
<title>修改厂商信息</title>
</head>
<body>
<form id="updateSupplierForm" enctype="multipart/form-data"  class="easyui-form" method="post">
<div id="container">
    <div id="bd">
    	<div id="main">
            <h2 class="subfild">
            	<span>基本信息</span>
            </h2>
            <div class="subfild-content base-info">
            	<div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>厂商名</label>
                	<div class="kv-item-content">
                    	<input type="text" id="suppliername" name="suppliername" value="${supplier.suppliername}" readonly="readonly"/>
                    </div>
                </div>
                <div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>联系人</label>
                	<div class="kv-item-content">
                    	<input type="text" id="contact" name="contact" value="${supplier.contact}"  />
                    </div>
                </div>
                <div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>电话</label>
                	<div class="kv-item-content">
                    	<input type="text" id="phonenumber" name="phonenumber" value="${supplier.phonenumber}" />
                    </div>
                </div>
                 <div class="kv-item ue-clear">
                	<label><span class="impInfo">*</span>地址</label>
                	<div class="kv-item-content">
                        <input type="text" id="address" name="address" value="${supplier.address}"/>
                    </div>
                </div>
                <input type="hidden" id="id" name="id" value="${supplier.id}"/>
                <input type="hidden" id="createtime" name="createtime" value="${supplier.createtime}"/>
                <input type="hidden" id="updatetime" name="updatetime" value="${supplier.updatetime}"/>
            </div>
        </div>
    </div>
</div>
</form>
</body>
</html>
