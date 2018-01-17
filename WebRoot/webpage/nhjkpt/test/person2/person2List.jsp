<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="person2List" title="人员管理" actionUrl="person2Controller.do?datagrid" idField="personid" fit="true">
   <t:dgCol title="编号" field="id" ></t:dgCol>
   <t:dgCol title="name" field="name" ></t:dgCol>
   <t:dgCol title="remark" field="remark" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="person2Controller.do?del&id={personid}" />
   <t:dgToolBar title="录入" icon="icon-add" url="person2Controller.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="person2Controller.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="person2Controller.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>