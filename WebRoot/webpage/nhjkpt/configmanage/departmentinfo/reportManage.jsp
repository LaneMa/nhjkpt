<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>分户报表管理</title>
<script type="text/javascript">
	function reportSubmit(){
		var departments=$("input[name='reportDepartmentid']");
		var departmentids="";
		for(var i=0,l=departments.length;i<l;i++){
			if(departments[i].value!='-1'){
				if(departmentids!=""){
					departmentids+=",";
				}
				departmentids+=departments[i].value;
			}
		}
		var url="departmentSumController.do?reportData&departmentid="+departmentids+"&funcid="+$("#reportfuncid").val()+"&startDate="+$("#reportstartDate").val()+"&endDate="+$("#reportendDate").val()+"&reportType="+$("#reportType").val()+"&tableType="+$("#tableType").val();
		$.ajax({
			type : "POST",
			url : url,
			success : function(jsondata) {
				var data=eval(jsondata);
				if(data[0]){
					var table=document.getElementById("table");
					table.border="1";
					table.innerHTML="";
					var tbody = document.createElement("tbody");
				    table.appendChild(tbody);
				    var tr;
				    var td;
				    var txt;
					for(var i=0,l=data.length;i<l;i++){
						tr=document.createElement("tr");
						for(var j=0,len=data[i].length;j<len;j++){
							td=document.createElement("td");
						    txt=document.createTextNode(data[i][j]);
						    td.appendChild(txt);
						    tr.appendChild(td);
						}
					 tbody.appendChild(tr);
					}
				}else{
					tip("没有数据!");
				}
			}
		});
	}
	function exportReport(){
		var departments=$("input[name='reportDepartmentid']");
		var departmentids="";
		for(var i=0,l=departments.length;i<l;i++){
			if(departments[i].value!='-1'){
				if(departmentids!=""){
					departmentids+=",";
				}
				departmentids+=departments[i].value;
			}
		}
		var url="departmentSumController.do?exportReport&departmentid="+departmentids+"&funcid="+$("#reportfuncid").val()+"&startDate="+$("#reportstartDate").val()+"&endDate="+$("#reportendDate").val()+"&reportType="+$("#reportType").val()+"&tableType="+$("#tableType").val();
		window.location.href=url;
	}
</script>
</head>
<table width="100%" cellpadding="0" cellspacing="1" class="formtable" style="overflow-y: hidden" scroll="no">
	<tr>
		<td align="right">分户:</td>
		<td>
			<t:comboTree url="departmentinfoController.do?departmentTree" name="reportDepartmentid" id="reportDepartmentid" multiple="true"></t:comboTree>
		</td>
		<td align="right">能耗类型:</td>
		<td>
			<select name="funcid" id="reportfuncid">
				<c:forEach items="${listfunc}" var="func">
					<option value="${func.funcid}">
						${func.showtext}
					</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">开始时间:</td>
		<td>
			<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="startDate" id="reportstartDate" datatype="*">
		</td>
		<td align="right">结束时间:</td>
		<td>
			<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="endDate" id="reportendDate" datatype="*">
		</td>
	</tr>
	<tr>
		<td align="right">报表类型:</td>
		<td>
			<select name="reportType" id="reportType">
				<option value="year">年</option>
				<option value="month">月</option>
				<option value="day">日</option>
			</select>
		</td>
		<td align="right">报表格式:</td>
		<td>
			<select name="tableType" id="tableType">
				<option value="0">横轴时间</option>
				<option value="1">纵轴时间</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="4" align="center">
			<input type="button" value="查询" onclick="reportSubmit();"/>
			<input type="button" value="导出" onclick="exportReport();"/>
		</td>
	</tr>
	
</table>


<div style="width:1100px; height:310px; overflow:auto;">
<table id="table">

</table>
</div>