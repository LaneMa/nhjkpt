<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>水表主页</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body >

<iframe src="meterController.do?waterDiagram&roomid=${room.id}" width="100%" height="99%" frameborder="0" ></iframe>

</body>