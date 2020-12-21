<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="baseTitle.jsp"%>
<html>
<head>
<title>二维码打印页面</title>
<%@ include file="baseJsCss.jsp"%>
<script type="text/javascript" src="print/jquery-migrate-1.1.0.js"></script>
<script type="text/javascript" src="print/jquery.jqprint-0.3.js"></script>
<link href="print/print.css" rel="stylesheet" type="text/css"
	media="print" />
<style type="text/css" media="print">
div.page {
	page-break-after: always;
	page-break-inside: avoid;
}

@media print {
	.noprint {
		display: none
	}
}

@page {
	size: auto;
	margin: 0mm;
}
</style>
<script type="text/javascript">
	$(function(){
		var qrcodeUrl='${qrcodeUrl}';//获取二维码图片路径
		$("#qrcodeUrl").attr("src",qrcodeUrl);
	    var callBack = window.parent.printCallBack;
	    $("body").jqprint({
	        callback:callBack
	    });
	});
</script>
</head>
<body>
	<img id="qrcodeUrl" src="" >
</body>