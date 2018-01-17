<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="buildinginfoList" title="建筑物配置" actionUrl="buildinginfoController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="建筑物标识号" field="buildingid" ></t:dgCol>
   <t:dgCol title="建筑物名称" field="buildingname" ></t:dgCol>
   <t:dgCol title="建筑物描述" field="buildingtext" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="buildinginfoController.do?del&id={id}" />
   <t:dgOpenOpt title="用能总量统计" url="buildingSumController.do?stacurve&buildingid={id}&type=open" width="800px" height="500px"></t:dgOpenOpt>
   <t:dgOpenOpt title="用能分类统计" url="buildingItemizeController.do?stacurve&buildingid={id}&type=open" width="800px" height="500px"></t:dgOpenOpt>
   <t:dgOpenOpt title="用能平均统计" url="buildingUnitController.do?stacurve&buildingid={id}&type=open" width="800px" height="500px"></t:dgOpenOpt>
   <t:dgToolBar title="录入" icon="icon-add" url="buildinginfoController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="buildinginfoController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="buildinginfoController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>