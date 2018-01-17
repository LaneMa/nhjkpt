<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
  <title>学校集合</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:datagrid name="schoolList" title="按学校选择一个" actionUrl="schoolinfoController.do?datagridSchool" idField="id" checkbox="false" >
   <t:dgCol title="学校id" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="学校标志" field="schoolid" width="50" ></t:dgCol>
   <t:dgCol title="学校名称" field="schoolname" width="50" query="true" ></t:dgCol>
  </t:datagrid>
 </body>
</html>
