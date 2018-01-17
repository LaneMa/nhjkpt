<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="roomlightList" title="教室照明管理" actionUrl="roomlightController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="buildingid" field="buildingid" ></t:dgCol>
   <t:dgCol title="buildingname" field="buildingname" ></t:dgCol>
   <t:dgCol title="layerid" field="layerid" ></t:dgCol>
   <t:dgCol title="roomid" field="roomid" ></t:dgCol>
   <t:dgCol title="controltype" field="controltype" ></t:dgCol>
   <t:dgCol title="controldata1" field="controldata1" ></t:dgCol>
   <t:dgCol title="controldata2" field="controldata2" ></t:dgCol>
   <t:dgCol title="lightdata" field="lightdata" ></t:dgCol>
   <t:dgCol title="mannumber" field="mannumber" ></t:dgCol>
   <t:dgCol title="lightnumber" field="lightnumber" ></t:dgCol>
   <t:dgCol title="lightstate" field="lightstate" ></t:dgCol>
   <t:dgCol title="mark1" field="mark1" ></t:dgCol>
   <t:dgCol title="mark2" field="mark2" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="roomlightController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="roomlightController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="roomlightController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="roomlightController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>