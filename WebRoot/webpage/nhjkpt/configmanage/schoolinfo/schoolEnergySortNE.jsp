<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>

<head>
<title>本日用水柱状图</title>
<script type="text/javascript" src="plug-in/Highcharts-2.3.3/js/highcharts.src.js"></script>
<script type="text/javascript" src="plug-in/Highcharts-2.3.3/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="plug-in/tools/common.js"></script>
</head>
<div id="schoolEnergySortNE" style="width:100%; height:100%"></div>
<script type="text/javascript">
loadDataByAjax("schoolhoursumController.do?queryEnergySortNEBar","schoolEnergySortNE","本日用水柱状图","时间","用能","柱状图");
</script>
</html>