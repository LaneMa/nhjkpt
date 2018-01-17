<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>四大分项饼状图</title>

</head>
<div id="schoolEnergySortCC" style="width:100%; height:100%"></div>
</html>

<script type="text/javascript">
loadDataByAjax("schoolSumController.do?queryEnergySortCCBar","schoolEnergySortCC","四大分项饼状图","日期","用能","饼状图");
</script>