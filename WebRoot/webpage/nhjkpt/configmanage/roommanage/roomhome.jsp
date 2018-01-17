<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>配电房主页</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
本日用电：${eUse }度，全年用电：${eUseYear }度，分支电表：${meternum }块，正常采集：${meterOknum }块，故障：${meterErrornum }块。
<iframe src="roomController.do?circuitDiagram&roomid=${room.id}" width="100%" height="99%" frameborder="0" ></iframe>

</body>