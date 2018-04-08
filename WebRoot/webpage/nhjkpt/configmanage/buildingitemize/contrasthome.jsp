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
<t:tabs id="contrasthometab" iframe="false" tabPosition="bottom">
<c:if test="${id=='conrastdate' }">
	<t:tab href="buildingItemizeController.do?stacurveDate" icon="icon-search" title="能耗比较" id="pnode"></t:tab>
</c:if>
<c:if test="${id=='conrastitemize' }">
	<t:tab href="buildingItemizeController.do?stacurveItemize" icon="icon-search" title="能耗比较" id="pnode"></t:tab>
</c:if>
<c:if test="${id=='conrastbuilding' }">
	<t:tab href="buildingItemizeController.do?stacurveBuilding" icon="icon-search" title="能耗比较" id="pnode"></t:tab>
</c:if>
<c:if test="${id=='conrastbuildingsum' }">
	<t:tab href="buildingItemizeController.do?stacurveBuildingsum" icon="icon-search" title="能耗比较" id="pnode"></t:tab>
</c:if>
 </t:tabs>
</body>