<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="schoolUnitList" title="学校单位用能统计计算表" actionUrl="schoolUnitController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="单位编码" field="unitid" ></t:dgCol>
   <t:dgCol title="单位名" field="unittext" ></t:dgCol>
   <t:dgCol title="单位总量" field="unitsum" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="schoolUnitController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="schoolUnitController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="schoolUnitController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="schoolUnitController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>