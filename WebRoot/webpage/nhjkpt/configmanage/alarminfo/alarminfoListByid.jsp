<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="alarminfoList" title="报警信息列表" actionUrl="alarminfoController.do?datagridByid&id=${id }" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="表具名称" field="meterid"></t:dgCol>
   <t:dgCol title="报警时间" field="alarmtime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="报警信息" field="info" ></t:dgCol>
   <t:dgCol title="报警类型" field="type"></t:dgCol>
  </t:datagrid>
  </div>
 </div>