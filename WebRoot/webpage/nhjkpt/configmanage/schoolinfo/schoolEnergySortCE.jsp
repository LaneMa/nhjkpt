<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<head>
<title>用能排名柱状图</title>
<script type="text/javascript">

</script>
</head>
<div id="schoolEnergySortCE" style="width:100%; height:100%"></div>
</html>

<script type="text/javascript">
loadDataByAjax("buildingDaySumController.do?queryEnergySortCEBar","schoolEnergySortCE","用能排名","日期","用能","柱状图");
</script>