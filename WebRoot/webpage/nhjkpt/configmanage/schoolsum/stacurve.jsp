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
	

	function loadDataSum(url){
		var chart;
		$.ajax({
			type : "POST",
			url : url,
			success : function(jsondata) {
				data = eval(jsondata);
				var arr=new Array();
				if(data[0]){
					for(var i=0,l=data[0].data.length;i<l;i++){
						arr.push(data[0].data[i].name);
					}
				}
				chart = new Highcharts.Chart({
					chart : {
						renderTo : 'schoolhomeline',
						plotBackgroundColor : null,
						plotBorderWidth : null,
						plotShadow : false
					},
					title : {
						text : '学校用能统计曲线'
					},
					xAxis : {
						title:{
							text:'日期'
						},
						categories : arr
					},
					yAxis:{
						title:{
							text:'用能'
						}
					},
					tooltip : {
						pointFormat : '{series.name}: <b>{point.y}</b>',
						percentageDecimals : 1

					},
					exporting:{  
		                filename:'折线图',  
		                 url:'${ctxPath}/logController.do?export'  
		            }, 
					plotOptions : {
						pie : {
							allowPointSelect : true,
							cursor : 'pointer',
							showInLegend : true,
							dataLabels : {
								enabled : true,
								color : '#000000',
								connectorColor : '#000000',
								formatter : function() {
									return '<b>' + this.point.name + '</b>: ' + this.point.y;
								}
							}
						}
					},
					series : data
				});
			}
		});
	}
	function doSubmitSum(){
		var url="schoolSumController.do?queryBroswerBar&funcid="+$("#schoolhomefuncid").val()+"&type="+$("#schoolhometype").val()+"&startDate="+$("#schoolhomestartDate").val()+"&endDate="+$("#schoolhomeendDate").val();
		//loadDataSum(url);
		//查看获取电量还是水量
		//var type=$("#schoolhomefuncid").find("option:selected").text();
		
		//if(type.indexOf("水量")>0){
		//	loadDataByAjax(url,"schoolhomeline","学校用能统计曲线","日期","用能(吨)","折线图");
		//}
		//else {
		//	loadDataByAjax(url,"schoolhomeline","学校用能统计曲线","日期","用能(千瓦时)","折线图");
		//}
		loadDataByAjax(url,"schoolhomeline","学校用能统计曲线","日期","用能","折线图");
		
	}
</script>
</head>

<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" style="overflow-y: hidden" scroll="no">
	<tr>
		<td align="right">
			<label class="Validform_label">
				能耗类型:
			</label>
		</td>
		<td class="value">
			<select name="funcid" id="schoolhomefuncid">
				<c:forEach items="${funclist}" var="func">
					<option value="${func.id}"
						<c:if test="${func.id==schoolSumPage.funcid }">selected="selected"</c:if>>
						${func.showtext}
					</option>
				</c:forEach>
			</select>
		</td>
		<td align="right">
			<label class="Validform_label">
				查看方式:
			</label>
		</td>
		<td class="value">
			<select name="type" id="schoolhometype">
				<option value="-1">--请选择--</option>
				<option value="year">年</option>
				<option value="month">月</option>
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
			<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" name="startDate" id="schoolhomestartDate" datatype="*">
			<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" name="endDate" id="schoolhomeendDate" datatype="*">
		</td>
		<td align="right" colspan="2">
			<input value="查询" type="button" onclick="doSubmitSum();">
		</td>
	</tr>
</table>
<div id="schoolhomeline" style="width:100%; height:80%"></div>
</html>

<script type="text/javascript">
doSubmitSum();
</script>

