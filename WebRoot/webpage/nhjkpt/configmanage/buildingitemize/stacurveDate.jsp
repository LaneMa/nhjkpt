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
			var url="schoolItemizeController.do?queryBroswerBarDate";
			//loadDataSum(url);
			loadDataByAjax(url,"buildingdateline","建筑物的用能对比曲线","日期","用能","折线图");
		});
	});
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
						renderTo : 'buildingdateline',
						plotBackgroundColor : null,
						plotBorderWidth : null,
						plotShadow : false
					},
					title : {
						text : '建筑物的用能对比曲线'
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
	var suburl="";
	var dates="";
	function doSubmitSum(){
		if($("#itemizeidhidden").val()==""){
			tip("请选择分类");
			return;
		}
		if($("#buildingid").val()=="-1"){
			suburl="schoolItemizeController.do?queryBroswerBarDate";
		}else{
			suburl="buildingItemizeController.do?queryBroswerBarDate&buildingid="+$("#buildingid").val();
		}
		suburl+="&itemizeid="+$("#itemizeidhidden").val()+"&type="+$("#datetype").val();
		if($("#datestartDate").val()!=""&&$("#dateendDate").val()!=""){
			if(dates==""||dates.indexOf("&startDate="+$("#datestartDate").val())==-1){
				dates+="&startDate="+$("#datestartDate").val()+"&endDate="+$("#dateendDate").val();
			}
		}
		suburl+=dates;
		//loadDataSum(suburl);
		loadDataByAjax(suburl,"buildingdateline","建筑物的用能对比曲线","日期","用能","折线图");
	}
	
	function buildNameSearch(){
		var buildName = $("#buildName").val();
		if (buildName==''){
			$("#buildName").val('输入大楼名称');
		}
		
		var url="buildinginfoController.do?querybuildingsbyname";
		$.ajax({
			type : "POST",
			url : url,
			data : {buildName : buildName},
			success : function(jsondata) {
				var data=eval(jsondata);
				if(data){
					var str = "<select name='buildingid' id='buildingid'><option value='-1'>--请选择--</option>";
					for(var i=0;i<data.length;i++){
						str += "<option value='"+data[i].id+"'>"+data[i].buildingname+"</option>";
					}
					str += "</select>";
					$("#op_buildList").html(str);
				}
			}
		});
	}
</script>
</head>
<table style="width: 700px;" cellpadding="0" cellspacing="1" class="formtable" style="overflow-y: hidden" scroll="no">
	<tr>
		<td align="right"><label class="Validform_label">分类:</label></td>
		<td class="value" >
			<t:comboTree url="itemizeController.do?itemizeTree" name="itemizeid" id="itemizeid"></t:comboTree> 
			<span class="Validform_checktip"></span>
		</td>
		<td align="right">
			<label class="Validform_label">
				大楼:
			</label>
		</td>
		<td class="value">
			<span id="op_buildList">
				<select name="buildingid" id="buildingid">
					<option value="-1">--请选择--</option>
						<c:forEach items="${buildinglist}" var="building">
							<option value="${building.buildingid}">
								${building.buildingname}
							</option>
						</c:forEach>
				</select>
			</span>
			
			<input type="text" id="buildName" value="输入大楼名称" style="width:100px" 
			  onblur="buildNameSearch();"
			  onfocus="if (this.value=='输入大楼名称') this.value=''"/>
			<span class="Validform_checktip"></span>
			
		</td>
		<td align="right">
			<label class="Validform_label">
				查看方式:
			</label>
		</td>
		<td class="value">
			<select name="type" id="datetype">
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
		<td class="value" colspan="3">
			<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" id="datestartDate" name="startDate" datatype="*">
			<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" id="dateendDate" name="endDate" datatype="*">
		</td>
		<td align="right" colspan="2">
			<input value="查询" type="button" onclick="doSubmitSum();">
		</td>
	</tr>
</table>
<div id="buildingdateline" style="width:100%; height:80%"></div>
</html>