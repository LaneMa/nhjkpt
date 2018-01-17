<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="buildingSumList" title="大楼水电气总量计算表" actionUrl="buildingSumController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="大楼" field="buildingid"> </t:dgCol>
   <t:dgCol title="功能名称" field="funcid"></t:dgCol>
   <t:dgCol title="表具" field="meterid"> </t:dgCol>
   <t:dgCol title="拆分系数" field="factor" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="buildingSumController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="buildingSumController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="buildingSumController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="buildingSumController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>