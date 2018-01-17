<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="lightingList" title="教室照明" actionUrl="lightingController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="楼层id" field="floorid" ></t:dgCol>
   <t:dgCol title="楼id" field="buildingid" ></t:dgCol>
   <t:dgCol title="教室id" field="roomid" ></t:dgCol>
   <t:dgCol title="控制类型" field="controlType" ></t:dgCol>
   <t:dgCol title="控制数据" field="controlData" ></t:dgCol>
   <t:dgCol title="光照度" field="lightFalls" ></t:dgCol>
   <t:dgCol title="人数" field="userNum" ></t:dgCol>
   <t:dgCol title="灯数" field="lightNum" ></t:dgCol>
   <t:dgCol title="灯状态" field="lightType" ></t:dgCol>
   <t:dgCol title="保留1" field="retain1" ></t:dgCol>
   <t:dgCol title="保留2" field="retain2" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="lightingController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="lightingController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="lightingController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="lightingController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>