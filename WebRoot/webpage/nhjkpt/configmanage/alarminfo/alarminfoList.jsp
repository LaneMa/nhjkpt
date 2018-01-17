<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="alarminfoList" title="报警信息列表" actionUrl="alarminfoController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="表具名称" field="meterid"></t:dgCol>
   <t:dgCol title="报警时间" field="alarmtime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="报警信息" field="info" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="alarminfoController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="alarminfoController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="alarminfoController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="alarminfoController.do?addorupdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="清空" icon="icon-remove" url="alarminfoController.do?deleteAll" funname="deleteAll"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>