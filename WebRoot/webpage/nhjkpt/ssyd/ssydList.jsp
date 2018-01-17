<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:base type="jquery,easyui,tools"></t:base>
  <style type="text/css">
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #c4d7e6;
	border-collapse: collapse;
	width:100%;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #c4d7e6;
	background-color: #efefef;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #c4d7e6;
	background-color: #ffffff;
}
</style>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:1px;">
<table class="gridtable" >
	<tr>
		<th>宿舍号</th>
		<th>抄表时间</th>
		<th>已用电量</th>
		<th>剩余电量</th>
	</tr>

 <c:forEach items="${ssList}" var="ssyd">
	 <tr>
	  	<td>${ssyd.ssname}</td>
		<td>${ssyd.cbdate}</td>
		<td>${ssyd.useyd}</td>
		<td>${ssyd.nouseyd}</td>
	</tr>
 </c:forEach>

</table>


</div>
</div>

