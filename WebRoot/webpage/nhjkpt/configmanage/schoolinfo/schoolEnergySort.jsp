<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>学校用能总量的统计曲线</title>
<script type="text/javascript" src="plug-in/Highcharts-2.3.3/js/highcharts.src.js"></script>
<script type="text/javascript" src="plug-in/Highcharts-2.3.3/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="plug-in/tools/common.js"></script>
<script type="text/javascript">
$(function() {
	$(document).ready(function() {
		
	});
});
</script>
</head>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',split:true,border:false" style="height:200px">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'east',split:true,border:false" style="width:550px" href="schoolinfoController.do?schoolEnergySortNE">
			</div>
			<div data-options="region:'center',border:false" href="schoolinfoController.do?schoolEnergySortNC">
			</div>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'east',split:true,border:false" style="width:550px" href="schoolinfoController.do?schoolEnergySortCE">
			</div>
			<div data-options="region:'center',border:false" href="schoolinfoController.do?schoolEnergySortCC">
			</div>
		</div>
	</div>
</div>
</html>