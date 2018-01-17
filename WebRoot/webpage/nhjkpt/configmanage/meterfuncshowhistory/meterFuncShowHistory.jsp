<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>瞬时值保存两个月记录</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="meterFuncShowHistoryController.do?save">
			<input id="id" name="id" type="hidden" value="${meterFuncShowHistoryPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							meterid:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="meterid" name="meterid" 
							   value="${meterFuncShowHistoryPage.meterid}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							funcid:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="funcid" name="funcid" 
							   value="${meterFuncShowHistoryPage.funcid}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							showdata:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="showdata" name="showdata" 
							   value="${meterFuncShowHistoryPage.showdata}" datatype="d">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							showtime:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="showtime" name="showtime" 
							     value="<fmt:formatDate value='${meterFuncShowHistoryPage.showtime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>