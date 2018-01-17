<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>同一时间段建筑物的用能分类统计曲线</title>
<script type="text/javascript" src="plug-in/Highcharts-2.3.3/js/highcharts.src.js"></script>
<script type="text/javascript" src="plug-in/Highcharts-2.3.3/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="plug-in/tools/common.js"></script>
<script type="text/javascript">
	$(function() {
		$(document).ready(function() {
			var url="buildingItemizeController.do?queryBroswerBarBuilding";
			//loadDataSum(url);
			loadDataByAjax(url,"stacurveBuildingline","建筑物的用能分类统计曲线","日期","用能","折线图");
		});
	});
	var suburl="";
	function doSubmitSum(){
		suburl="buildingItemizeController.do?queryBroswerBarBuilding&itemizeid="+$("#stacurveitemizeidhidden").val();
		var buildingids=$("input[name='stacurvebuildingid']");
		var buildingid='';
		for(var i=0,l=buildingids.length;i<l;i++){
			if(buildingids[i].value!='-1'){
				if(buildingid!=''){
					buildingid+=',';
				}
				buildingid+=buildingids[i].value;
			}
		}
		suburl+="&buildingid="+buildingid+"&type="+$("#stacurveItemizetype").val()+"&startDate="+$("#stacurveItemizestartDate").val()+"&endDate="+$("#stacurveItemizeendDate").val();
		//loadDataSum(suburl);
		loadDataByAjax(suburl,"stacurveBuildingline","建筑物的用能分类统计曲线","日期","用能","折线图");
	}
</script>
</head>

<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" style="overflow-y: hidden" scroll="no">
	<tr>
		<td align="right">
			<label class="Validform_label">
				时间:
			</label>
		</td>
		<td class="value" colspan="3">
			<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" id="stacurveItemizestartDate" name="startDate" datatype="*">
			<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" id="stacurveItemizeendDate" name="endDate" datatype="*">
		</td>
		<td align="right"><label class="Validform_label">分类:</label></td>
		<td class="value">
			<t:comboTree url="itemizeController.do?itemizeTree" name="stacurveitemizeid" id="stacurveitemizeid" multiple="false"></t:comboTree> 
			<span class="Validform_checktip"></span>
		</td>
		
	</tr>
	<tr>
		<td align="right">
			<label class="Validform_label">
				查看方式:
			</label>
		</td>
		<td class="value">
			<select name="type" id="stacurveItemizetype">
				<option value="-1">--请选择--</option>
				<option value="year">年</option>
				<option value="month">月</option>
				<option value="day">日</option>
				<option value="hour">小时</option>
			</select>
		</td>
		<td align="right">
			<label class="Validform_label">
				大楼:
			</label>
		</td>
		<td class="value">
			<t:comboTree url="buildinginfoController.do?buildingTree" name="stacurvebuildingid" id="stacurvebuildingid" multiple="true"></t:comboTree> 
			<span class="Validform_checktip"></span>
		</td>
		
		<td align="right" colspan="2">
			<input value="查询" type="button" onclick="doSubmitSum();">
		</td>
	</tr>
</table>
<div id="stacurveBuildingline" style="width:100%; height:80%"></div>
</html>