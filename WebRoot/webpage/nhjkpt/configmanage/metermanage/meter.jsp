<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>表具管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="meterController.do?save">
			<input id="id" name="id" type="hidden" value="${meterPage.id }">
			<table style="width: 600px;height:250px" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							父表具:
						</label>
					</td>
					<td class="value">
						<t:comboTree url="meterController.do?meterTree" name="meter.id" id="meter" value="${meterPage.meter.id}"></t:comboTree>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							表具标识:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="meterid" name="meterid" 
							   value="${meterPage.meterid}" datatype="*"  ajaxurl="meterController.do?checkMeterid&id=${meterPage.id }">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							表具描述:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="metertext" name="metertext" 
							   value="${meterPage.metertext}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							配电房id:
						</label>
					</td>
					<td class="value">
						<select name="roomid">
							<c:forEach items="${roomlist}" var="room">
								<option value="${room.roomid}" <c:if test="${fn:trim(room.roomid)==meterPage.roomid }">selected="selected"</c:if>>
									${room.roomtext}
								</option>
							</c:forEach>
						</select>
							
						
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							级别:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="level" name="level" 
							   value="${meterPage.level}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>