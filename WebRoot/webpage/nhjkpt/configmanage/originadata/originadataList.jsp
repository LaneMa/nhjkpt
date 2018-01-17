<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="originadataList" title="数据库连接" actionUrl="originadataController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="数据库" field="dbtype" ></t:dgCol>
   <t:dgCol title="数据库IP" field="dbip" ></t:dgCol>
   <t:dgCol title="数据库名称" field="dbname" ></t:dgCol>
   <t:dgCol title="用户名" field="dbuser" ></t:dgCol>
   <t:dgCol title="密码" field="dbpwd" ></t:dgCol>
   <t:dgCol title="表名" field="tbname" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="originadataController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="originadataController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="originadataController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="originadataController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>