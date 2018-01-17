<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>分户的用能分类统计图</title>
<script type="text/javascript">
	$(function() {
		$(document).ready(function() {
			//var url="departmentItemizeController.do?queryBroswerBarItemizePie&departmentid=${departmentid}";
			//loadDataSum(url);
			//loadDataByAjax(url,"departmentItemizepie","分户的用能饼状图","日期","用能","饼状图");
			doSubmitItemizePie();
		});
	});
	function doSubmitItemizePie(){
		var url="departmentItemizeController.do?queryBroswerBarItemizePie&departmentid=${departmentid}&itemizeid="+$("#departmentItemizeidPie").val()+"&type="+$("#pietype").val()+"&startDate="+$("#departmentItemizepiestartDate").val()+"&endDate="+$("#departmentItemizepieendDate").val();
		//loadDataSum(url);
		loadDataByAjax(url,"departmentItemizepie","分户的用能饼状图","日期","用能","饼状图");
	}
</script>
</head>

<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" style="overflow-y: hidden" scroll="no">
	<tr>
		<td align="right"><label class="Validform_label">分类:</label></td>
		<td class="value">
			<select name="itemizeid" id="departmentItemizeidPie">
				<c:forEach items="${list }" var="itemize">
					<option value="${itemize.id }">${itemize.itemizetext }</option>
				</c:forEach>
			</select>
			<span class="Validform_checktip"></span>
		</td>
		<td align="right">
			<label class="Validform_label">
				查看方式:
			</label>
		</td>
		<td class="value">
			<select name="type" id="pietype">
				<option value="-1">--请选择--</option>
				<option value="year">年</option>
				<option value="month" selected="selected">月</option>
				<option value="day">日</option>
				<option value="hour">小时</option>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">
			<label class="Validform_label">
				时间:
			</label>
		</td>
		<td class="value">
			<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" id="departmentItemizepiestartDate" name="startDate" datatype="*">
			<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" id="departmentItemizepieendDate" name="endDate" datatype="*">
		</td>
		<td align="right" colspan="2">
			<input value="查询" type="button" onclick="doSubmitItemizePie();">
		</td>
	</tr>
</table>
<div id="departmentItemizepie" style="width:100%; height:80%"></div>
</html>