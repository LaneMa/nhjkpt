<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>报警信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:tabs id="alarminfo" iframe="false" width="500" heigth="300">
  <t:tab href="meterController.do?showdata&id=${id }" icon="icon-search" title="瞬时值" id="pnode"></t:tab>
  <t:tab href="alarminfoController.do?alarminfoByid&id=${id }" icon="icon-search" title="报警信息" id="pnode"></t:tab>
  <t:tab href="meterShowHistoryControl.do?meterShowHistory&id=${id }" icon="icon-search" title="历史值" id="pnode"></t:tab>
</t:tabs>
</body>
</html>