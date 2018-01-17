<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>分户主页</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',split:true,border:false" style="height:40px">
		${departmentinfo.departmentname }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<div id="showpe">
		
			<c:if test="${waterLimit > 0}" >
		　　		本年度用水${waterUse}吨，已消耗本年度配额的${waterRatio}%.
		　　    </c:if>
			
			<c:if test="${electricLimit > 0}" >
		　　		本年度用电${electricUse}度，已消耗本年度配额的${electricRatio}%，造成二氧化碳排放${co2}千克.
		　　    </c:if>
		
			<c:if test="${electricLimit==null || electricLimit <= 0}" >
		　　		本年度用电${electricUse}度，造成二氧化碳排放${co2}千克.
		　　    </c:if>
		
			<c:if test="${gasLimit>0}" >
		　　		本年度用气${gasUse}立方，已消耗本年度配额的${gasRatio}%.
		　　    </c:if>
		
			<c:if test="${heatLimit>0}" >
		　　		本年度用热${heatUse}焦耳，已消耗本年度配额的${heatRatio}%.
		　　    </c:if>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<t:tabs id="buildinghometab" iframe="false" tabPosition="bottom" heigth="90%">
		  <t:tab href="departmentSumController.do?stacurve&departmentid=${departmentinfo.departmentid }" icon="icon-search" title="用能总量" id="pnode" ></t:tab>
		  <t:tab href="departmentItemizeController.do?stacurveItemizeByDepartmentid&departmentid=${departmentinfo.departmentid }" icon="icon-search" title="用能分类" id="pnode"></t:tab>
		  <t:tab href="departmentUnitController.do?stacurveItemize&departmentid=${departmentinfo.departmentid }" icon="icon-search" title="单位用能" id="pnode"></t:tab>
		  <t:tab href="departmentItemizeController.do?stacurveItemizePie&departmentid=${departmentinfo.departmentid }" icon="icon-search" title="饼状图" id="pnode"></t:tab>
		</t:tabs>
	</div>
</div>
</body>