<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="buildingDayItemizeList" title="建筑用电分类分天统计表" actionUrl="buildingDayItemizeController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="大楼" field="buildingid" ></t:dgCol>
   <t:dgCol title="分类" field="itemizeid" ></t:dgCol>
   <t:dgCol title="时间" field="receivetime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="用电量" field="data" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="buildingDayItemizeController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="buildingDayItemizeController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="buildingDayItemizeController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="buildingDayItemizeController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>