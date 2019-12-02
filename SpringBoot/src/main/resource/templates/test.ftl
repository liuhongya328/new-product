<!DOCTYPE html>
<head>
    <meta charset="UTF-8" />
    <title>图片上传Demo</title>
</head>
<body>
<h1 >上传图片</h1>
<form action="fileUpload" method="post" enctype="multipart/form-data">
    <p>选择文件: <input type="file" name="fileName"/></p>
    <p><input type="submit" value="提交"/></p>
</form>
<#--判断是否上传文件-->
<#if msg??>
    <span>${msg}</span><br>
<#else >
    <span>${msg!("文件未上传")}</span><br>
</#if>
<#--显示图片，一定要在img中的src发请求给controller，否则直接跳转是乱码-->
<#if fileName??>
	<img src="/show?fileName=inpaint_0.png" style="width: 200px"/>
	<img src="/show?fileName=inpaint_1.png" style="width: 200px"/>
	<img src="/show?fileName=inpaint_2.png" style="width: 200px"/>
	<img src="/show?fileName=inpaint_3.png" style="width: 200px"/>
	<img src="/show?fileName=inpaint_4.png" style="width: 200px"/>
	<img src="/show?fileName=inpaint_5.png" style="width: 200px"/>
	<img src="/show?fileName=inpaint_6.png" style="width: 200px"/>
	<img src="/show?fileName=inpaint_7.png" style="width: 200px"/>
	<img src="/show?fileName=inpaint_8.png" style="width: 200px"/>
	<img src="/show?fileName=inpaint_9.png" style="width: 200px"/>
	<img src="/show?fileName=inpaint_10.png" style="width: 200px"/>
<#else>
	<img src="/show" style="width: 100px"/>
</#if>
</body>
</html>