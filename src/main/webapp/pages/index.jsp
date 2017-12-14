<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="x5-orientation" content="portrait">
    <link rel="Shortcut Icon" href="../lingmoulogo.ico" >
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/weui.min.css">
    <title>灵眸科技微信公众号支付</title>
</head>
<body>
<div class="container" id="container">
    <a href="${payURL}" class="weui-btn weui-btn_primary" style="font-size:40px; 100px;">
    	立即支付
    </a>
    <br>
    <a href="http://lingmouwxpay.ailingmou.com/lingmouwxpay/m/weChat/refund" class="weui-btn weui-btn_primary" style="font-size:40px; 100px;">
    	申请退款
    </a>
</div>
</body>
</html>