<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  
  <t:datagrid name="itemizeList" title="分类配置" actionUrl="itemizeController.do?treeGrid" idField="id"  treegrid="true" pagination="false">
   <t:dgCol title="编号" field="id" treefield="id" hidden="false"></t:dgCol>
   <t:dgCol title="分类名称" field="itemizetext" treefield="text" ></t:dgCol>
   <t:dgCol title="分类id" field="itemizeid" treefield="src" ></t:dgCol>
   <t:dgCol title="分类级别" field="level" treefield="order"  ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="itemizeController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="itemizeController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="itemizeController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="itemizeController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>