<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>教室照明管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" action="roomlightController.do?save">
		<input id="id" name="id" type="hidden" value="${roomlightPage.id }">
		<fieldset class="step">
			<div class="form">
		      <label class="Validform_label">buildingid:</label>
		      <input class="inputxt" id="buildingid" name="buildingid" ignore="ignore"
					   value="${roomlightPage.buildingid}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">buildingname:</label>
		      <input class="inputxt" id="buildingname" name="buildingname" ignore="ignore"
					   value="${roomlightPage.buildingname}">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">layerid:</label>
		      <input class="inputxt" id="layerid" name="layerid" ignore="ignore"
					   value="${roomlightPage.layerid}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">roomid:</label>
		      <input class="inputxt" id="roomid" name="roomid" ignore="ignore"
					   value="${roomlightPage.roomid}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">controltype:</label>
		      <input class="inputxt" id="controltype" name="controltype" ignore="ignore"
					   value="${roomlightPage.controltype}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">controldata1:</label>
		      <input class="inputxt" id="controldata1" name="controldata1" ignore="ignore"
					   value="${roomlightPage.controldata1}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">controldata2:</label>
		      <input class="inputxt" id="controldata2" name="controldata2" ignore="ignore"
					   value="${roomlightPage.controldata2}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">lightdata:</label>
		      <input class="inputxt" id="lightdata" name="lightdata" ignore="ignore"
					   value="${roomlightPage.lightdata}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">mannumber:</label>
		      <input class="inputxt" id="mannumber" name="mannumber" ignore="ignore"
					   value="${roomlightPage.mannumber}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">lightnumber:</label>
		      <input class="inputxt" id="lightnumber" name="lightnumber" ignore="ignore"
					   value="${roomlightPage.lightnumber}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">lightstate:</label>
		      <input class="inputxt" id="lightstate" name="lightstate" ignore="ignore"
					   value="${roomlightPage.lightstate}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">mark1:</label>
		      <input class="inputxt" id="mark1" name="mark1" ignore="ignore"
					   value="${roomlightPage.mark1}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
			<div class="form">
		      <label class="Validform_label">mark2:</label>
		      <input class="inputxt" id="mark2" name="mark2" ignore="ignore"
					   value="${roomlightPage.mark2}" datatype="n">
		      <span class="Validform_checktip"></span>
		    </div>
	    </fieldset>
  </t:formvalid>
 </body>