<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="meterList" title="表具管理" actionUrl="meterController.do?treeGrid" idField="id"  treegrid="true" pagination="false">
   <t:dgCol title="编号" field="id" treefield="id" hidden="false"></t:dgCol>
   <t:dgCol title="表具描述" field="metertext" treefield="text" ></t:dgCol>
   <t:dgCol title="配电房号" field="roomid" treefield="src" replace="${roomReplace}" ></t:dgCol>
   <t:dgCol title="表具标识号" field="meterid" treefield="order"  ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="meterController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="meterController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="meterController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="meterController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
 
