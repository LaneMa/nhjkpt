<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>大楼主页</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',split:true,border:false" style="height:60px">
		${buildinginfo.buildingname }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
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
		
		<span id="buildingshowdata">
			<c:forEach items="${listshow }" var="show">
				${show }&nbsp;&nbsp;&nbsp;&nbsp;
			</c:forEach>
		</span>
	</div>
	<div data-options="region:'center',border:false">
		<t:tabs id="buildinghometab" iframe="false" tabPosition="bottom" heigth="90%">
		  <t:tab href="buildingSumController.do?stacurve&buildingid=${buildinginfo.id }" icon="icon-search" title="用能总量" id="pnode" ></t:tab>
		  <t:tab href="buildingItemizeController.do?stacurveItemizeByBuildingid&buildingid=${buildinginfo.id }" icon="icon-search" title="用能分类" id="pnode"></t:tab>
		  <t:tab href="buildingUnitController.do?stacurveItemize&buildingid=${buildinginfo.id }" icon="icon-search" title="单位用能" id="pnode"></t:tab>
		  <t:tab href="buildingItemizeController.do?stacurveItemizePie&buildingid=${buildinginfo.id }" icon="icon-search" title="饼状图" id="pnode"></t:tab>
		</t:tabs>
	</div>
</div>
<script type="text/javascript">
$(function() {
	$(document).ready(function() {
		loaddata();
	});
});
function loaddata(){
	setInterval(function(){
		$("#buildingshowdata").html("正在加载...");
		$.ajax({
			type : "POST",
			url : "buildinginfoController.do?loaddata&id=${buildinginfo.id }",
			success : function(jsondata) {
				var data=eval(jsondata);
				var res="";
				for(var i=0;i<data.length;i++){
					res+=data[i];
					res+="&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				$("#buildingshowdata").html(res);
			}
		});
	},300000);
}
</script>
</body>