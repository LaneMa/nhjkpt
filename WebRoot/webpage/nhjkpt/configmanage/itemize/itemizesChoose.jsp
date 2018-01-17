<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
  <title>分类集合</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:datagrid name="itemizeList" title="按分类选择一个" actionUrl="itemizeController.do?datagridItemize" idField="id" checkbox="false" >
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="分类标识" field="itemizeid" width="50" ></t:dgCol>
   <t:dgCol title="分类描述" field="itemizetext" width="50" query="true" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   
   <t:dgToolBar title="查看" icon="icon-search" url="itemizeController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>

 </body>
</html>
