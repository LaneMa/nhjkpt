<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学校分类</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<c:forEach items="${listfunc }" var="func">
	${func }&nbsp;&nbsp;&nbsp;&nbsp;
</c:forEach>
 <t:tabs id="schoolitemizetab" iframe="false" tabPosition="bottom" heigth="90%">
  <t:tab href="schoolItemizeController.do?stacurveItemize&itemizeid=${id }" icon="icon-search" title="用能总量" id="pnode"></t:tab>
  <t:tab href="schoolUnitController.do?stacurveItemize&itemizeid=${id }" icon="icon-search" title="单位用能" id="pnode"></t:tab>
  <% //<t:tab href="schoolItemizeController.do?stacurvePie&itemizeid=${id }" icon="icon-search" title="饼状图" id="pnode"></t:tab> %>
  
 </t:tabs>
</body>