<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<html>
<head>
<script type="text/javascript" src="plug-in/Highcharts-2.3.3/js/highcharts.src.js"></script>
<script type="text/javascript" src="plug-in/Highcharts-2.3.3/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="plug-in/tools/common.js"></script>


</head>


<div id="stacurveCurrentline" style="width:1200px"></div>


</html>

<script type="text/javascript">
	
		var url="meterFuncShowHistoryController.do?queryBroswerBarCurrent&id=${id}";
		loadDataByAjax(url,"stacurveCurrentline","电流数据曲线","小时","用能","折线图");

</script>