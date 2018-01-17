<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>能耗对比</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:tabs id="reporthometab" iframe="false" tabPosition="bottom">
<c:if test="${id=='buildingsum' }">
	<t:tab href="buildingSumController.do?stacurve" icon="icon-search" title="用能总量" id="pnode"></t:tab>
</c:if>
	<c:if test="${id=='buildingitemize' }">
<t:tab href="buildingItemizeController.do?stacurve" icon="icon-search" title="分类总量" id="pnode"></t:tab>
</c:if>
<c:if test="${id=='buildingunit' }">
	<t:tab href="buildingUnitController.do?stacurve" icon="icon-search" title="平均总量" id="pnode"></t:tab>
</c:if>
 </t:tabs>
</body>