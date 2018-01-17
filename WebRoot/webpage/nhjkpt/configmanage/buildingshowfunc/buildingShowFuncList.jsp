<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="buildingShowFuncList" title="大楼需要展示的瞬时功能" actionUrl="buildingShowFuncController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="大楼" field="buildingid"> </t:dgCol>
   <t:dgCol title="唯一标识" field="showid" ></t:dgCol>
   <t:dgCol title="瞬时描述" field="showtext" ></t:dgCol>
   <t:dgCol title="表具" field="meterid"> </t:dgCol>
   <t:dgCol title="功能" field="funcid"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="buildingShowFuncController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="buildingShowFuncController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="buildingShowFuncController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="buildingShowFuncController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>