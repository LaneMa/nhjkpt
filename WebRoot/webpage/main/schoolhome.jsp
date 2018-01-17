<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<head>
<title>大楼主页</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',split:true,border:false" style="height:40px">
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
		<div id="showdata">
		
			<c:forEach items="${listshow }" var="show">
				${show }&nbsp;&nbsp;&nbsp;&nbsp;
			</c:forEach>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<t:tabs id="schoolhometab" iframe="false" tabPosition="bottom" heigth="90%">
		  <t:tab href="schoolinfoController.do?schoolEnergySort" icon="icon-search" title="用能排名" id="sort"></t:tab>
		  <t:tab href="schoolSumController.do?stacurve" icon="icon-search" title="用能总量" id="ynzl"></t:tab>
		  <t:tab href="schoolUnitController.do?stacurve" icon="icon-search" title="单位用能" id="dwyn"></t:tab>
		  <t:tab href="schoolItemizeController.do?stacurveSchoolItemizePie" icon="icon-search" title="饼状图" id="pnode"></t:tab>
		</t:tabs>
	</div>
	<div data-options="region:'south',border:false" style="height:40px">
		${schoolinfo.schooltext }
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
		if($("#showdata").is(":visible")){
			$("#showdata").html("正在加载...");
			$.ajax({
				type : "POST",
				url : "schoolinfoController.do?loaddata",
				success : function(jsondata) {
					var data=eval(jsondata);
					var res="";
					for(var i=0;i<data.length;i++){
						res+=data[i];
						res+="&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					$("#showdata").html(res);
				}
			});
		}
	},300000);
}
</script>
</body>
</html>