<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>历史数据</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="pointdataController.do?save">
			<input id="id" name="id" type="hidden" value="${pointdataPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							测量表具的唯一标识:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="meterid" name="meterid" ignore="ignore"
							   value="${pointdataPage.meterid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							功能id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="funcid" name="funcid" ignore="ignore"
							   value="${pointdataPage.funcid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							接收数据时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker()"  style="width: 150px" id="receivetime" name="receivetime" ignore="ignore"
							   value="<fmt:formatDate value='${pointdataPage.receivetime}' type="date" pattern="yyyy-MM-dd"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							表数:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="data" name="data" ignore="ignore"
							   value="${pointdataPage.data}" datatype="d">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>