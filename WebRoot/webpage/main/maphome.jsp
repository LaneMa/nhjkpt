<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>建筑物地图</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>


</head>
<body >

<div class="easyui-layout" data-options="fit:true">
	
	<div data-options="region:'center',border:false"   >
	<iframe id="mapHome" src="schoolinfoController.do?maphomeIframe"  width="100%" height="98%"  frameborder="0" scrolling="no"  ></iframe>

	</div>
	<div data-options="region:'east',split:true,border:false" style="width:650px;height:100%"  href="schoolinfoController.do?tongji" >
		
	</div>
</div>

</body>