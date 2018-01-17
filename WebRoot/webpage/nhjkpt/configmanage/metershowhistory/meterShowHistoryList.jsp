<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="meterShowHistoryList" fitColumns="true" title="瞬时基本信息" actionUrl="meterFuncShowHistoryController.do?datagrid&id=${id }" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="功能" field="funcid" replace="${funcReplace}"></t:dgCol>
   <t:dgCol title="发送时间" field="senddate" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="数据" field="showdata" ></t:dgCol>
  </t:datagrid>
  </div>
 </div>