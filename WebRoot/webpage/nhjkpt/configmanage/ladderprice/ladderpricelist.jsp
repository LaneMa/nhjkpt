<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="ladderPriceList" title="阶梯价格" actionUrl="ladderpricecontroller.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="名称" field="name" ></t:dgCol>
   <t:dgCol title="类型" field="priceType" ></t:dgCol>
   <t:dgCol title="单价" field="price" ></t:dgCol>
   <t:dgDelOpt title="删除" url="ladderpricecontroller.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="ladderpricecontroller.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="ladderpricecontroller.do?addorupdate" funname="update"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>