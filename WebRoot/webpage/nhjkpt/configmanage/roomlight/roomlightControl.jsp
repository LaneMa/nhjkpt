<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>报表管理</title>
<script type="text/javascript">

	function roomlightChange(){
		var url="roomlightController.do?roomlightChange&id="+$("#id").val()+"&buildingname="+$("#buildingname").val()+"&controltype="+$("#controltype").val()+"&controldata1="+$("#controldata1").val()+"&controldata2="+$("#controldata2").val();
		
		$.ajax({
			type : "POST",
			url : url,
			success : function(data) {			
				var d = $.parseJSON(data);
				tip(d.msg);		
			}
		});
	
	}
	
	function roomlightReload(){
		addOnlyOneTab('教室照明','roomlightController.do?roomlightControl&id=2::'+$("#id").val(),'');
	
	}
	
	function reportSubmit(){
		var buildings=$("input[name='reportBuildingid']");
		var buildingids="";
		for(var i=0,l=buildings.length;i<l;i++){
			if(buildings[i].value!='-1'){
				if(buildingids!=""){
					buildingids+=",";
				}
				buildingids+=buildings[i].value;
			}
		}
		var url="buildingSumController.do?reportData&buildingid="+buildingids+"&funcid="+$("#reportfuncid").val()+"&startDate="+$("#reportstartDate").val()+"&endDate="+$("#reportendDate").val()+"&reportType="+$("#reportType").val()+"&tableType="+$("#tableType").val();
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
		var buildings=$("input[name='reportBuildingid']");
		var buildingids="";
		for(var i=0,l=buildings.length;i<l;i++){
			if(buildings[i].value!='-1'){
				if(buildingids!=""){
					buildingids+=",";
				}
				buildingids+=buildings[i].value;
			}
		}
		var url="buildingSumController.do?exportReport&buildingid="+buildingids+"&funcid="+$("#reportfuncid").val()+"&startDate="+$("#reportstartDate").val()+"&endDate="+$("#reportendDate").val()+"&reportType="+$("#reportType").val()+"&tableType="+$("#tableType").val();
		window.location.href=url;
	}
</script>


<style type="text/css">
table.altrowstable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #a9c6c9;
	border-collapse: collapse;
	width:100%;
}
table.altrowstable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
table.altrowstable td {

	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
.oddrowcolor{
	background-color:#d4e3e5;
}
.evenrowcolor{
	background-color:#c3dde0;
}
</style>

</head>


<input id="id" name="id" type="hidden" value="${roomlightPage.id }">
<table  width="100%">
<tr>

<td width="50%" height="100%" valign="middle"  >
<br><br>
		<table width="100%" cellpadding="5" class="altrowstable"   style="font-size:15pt"  >
			<tr>
				<td  align="right" style="font-size:13pt">楼名称：</td>
				<td style="font-size:13pt"> <input class="inputxt" id="buildingname" name="buildingname" ignore="ignore"
					   value="${roomlightPage.buildingname}" datatype="*"></td>
			</tr>
			
			<tr>
				<td align="right" width="150" style="font-size:13pt" >控制方式：</td>
				<td style="font-size:13pt">
					<select name="controltype" id="controltype" style="font-size:10pt"  >
						<option value="1" <c:if test="${roomlightPage.controltype eq 1}">selected="selected"</c:if>  >自动</option>
						<option value="2" <c:if test="${roomlightPage.controltype eq 2}">selected="selected"</c:if> >手动</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td  align="right" style="font-size:13pt">控制数据1：</td>
				<td style="font-size:13pt"> <input class="inputxt" id="controldata1" name="controldata1" ignore="ignore"
					   value="${roomlightPage.controldata1}" datatype="n"></td>
			</tr>
			
			<tr>
				<td  align="right" style="font-size:13pt">控制数据2：</td>
				<td> <input class="inputxt" id="controldata2" name="controldata2" ignore="ignore"
					   value="${roomlightPage.controldata2}" datatype="n"></td>
			</tr>
			
			<tr>
				<td  align="right" style="font-size:13pt">光照度：</td>
				<td style="font-size:13pt">${roomlightPage.lightdata}</td>
			</tr>
			
			<tr>
				<td  align="right" style="font-size:13pt">人数：</td>
				<td style="font-size:13pt">${roomlightPage.mannumber}</td>
			</tr>
			
			<tr>
				<td  align="right" style="font-size:13pt">灯数：</td>
				<td style="font-size:13pt">${roomlightPage.lightnumber}</td>
			</tr>
			
			<tr style="display:''">
				<td  align="right" style="font-size:13pt">灯状态：</td>
				<td style="font-size:13pt">${roomlightPage.lightstate}</td>
			</tr>
			
			<tr>
				<td  align="center" colspan="2" >
				
					<input type="button" value="保存" onclick="roomlightChange();"/>
					<input type="button" value="刷新" onclick="roomlightReload();"/>
				</td>
				
			</tr>
		
		</table>

			
<br><br>
</td>


<td width="50%">

<table width="100%" cellspacing=10>
	<c:forEach begin="1" end="${roomlightPage.lightnumber}" var="i">
		<tr>
			<td width="50%" align="right" style="font-size:13pt">第${i}排灯状态:</td>
			<td>&nbsp;&nbsp;
				<c:if test="${fn:substring(lightstate_bin,i-1,i) == '0' }">
					<IMG src="webpage/main/images/lighton.jpg" width=18 height=25 />
				</c:if>
				
				<c:if test="${fn:substring(lightstate_bin,i-1,i) == '1' }">
					<IMG src="webpage/main/images/lightoff.jpg" width=18 height=25 />
				</c:if>
				
				
			</td>
		</tr>
					
	</c:forEach>




</table>



</td>

</tr>
</table>




