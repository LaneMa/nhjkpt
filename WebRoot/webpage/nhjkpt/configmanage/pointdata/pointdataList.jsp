<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="pointdataList" title="数据导入" actionUrl="pointdataController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="开始时间" field="startDate"></t:dgCol>
   <t:dgCol title="结束时间" field="endDate" ></t:dgCol>
   <t:dgCol title="当前导入" field="nowDate" ></t:dgCol>
   <t:dgToolBar title="数据导入" icon="icon-add" url="pointdataController.do?importPointData" funname="add"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>