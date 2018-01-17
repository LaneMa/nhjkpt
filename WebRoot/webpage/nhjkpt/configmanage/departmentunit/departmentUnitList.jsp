<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="departmentUnitList" title="分户单位用能" actionUrl="departmentUnitController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="系" field="departmentid" ></t:dgCol>
   <t:dgCol title="单位标识" field="unitid" ></t:dgCol>
   <t:dgCol title="单位名" field="unittext" ></t:dgCol>
   <t:dgCol title="单位总量" field="unitsum" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="departmentUnitController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="departmentUnitController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="departmentUnitController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="departmentUnitController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>