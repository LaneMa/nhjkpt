<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>人员管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="person2Controller.do?save">
		<input id="personid" name="personid" type="hidden" value="${person2Page.personid }">
		<fieldset class="step">
			<div class="form">
		      <label class="Validform_label">personid:</label>
		      <input class="inputxt" id="personid" name="personid" 
					   value="${person2Page.personid}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">name:</label>
		      <input class="inputxt" id="name" name="name" 
					   value="${person2Page.name}" datatype="*">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">remark:</label>
		      <input class="inputxt" id="remark" name="remark" ignore="ignore"
					   value="${person2Page.remark}">
		      <span class="Validform_checktip"></span>
		    </div>
	    </fieldset>
  </t:formvalid>
 </body>