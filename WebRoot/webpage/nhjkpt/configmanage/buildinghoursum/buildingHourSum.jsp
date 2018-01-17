<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>建筑用电总量分时统计表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="buildingHourSumController.do?save">
			<input id="id" name="id" type="hidden" value="${buildingHourSumPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							大楼:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="buildingid" name="buildingid" ignore="ignore"
							   value="${buildingHourSumPage.buildingid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							功能:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="funcid" name="funcid" ignore="ignore"
							   value="${buildingHourSumPage.funcid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="receivetime" name="receivetime" ignore="ignore"
							     value="<fmt:formatDate value='${buildingHourSumPage.receivetime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用电量:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="data" name="data" ignore="ignore"
							   value="${buildingHourSumPage.data}" datatype="d">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>