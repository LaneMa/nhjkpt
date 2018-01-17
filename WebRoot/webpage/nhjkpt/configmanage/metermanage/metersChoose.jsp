<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
  <title>表具集合</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:datagrid name="meterList" title="按表具选择一个" actionUrl="meterController.do?datagridMeter" idField="id" checkbox="false" >
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="表具标识号" field="meterid" width="50" query="true" ></t:dgCol>
   <t:dgCol title="表具名称" field="metertext" width="50" query="true" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   
   <t:dgToolBar title="查看" icon="icon-search" url="meterController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>

 </body>
</html>
