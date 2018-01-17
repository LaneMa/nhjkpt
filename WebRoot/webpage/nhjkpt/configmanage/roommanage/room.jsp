<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>配电房管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="roomController.do?save">
			<input id="id" name="id" type="hidden" value="${roomPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							配电房标识:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="roomid" name="roomid" 
							   value="${roomPage.roomid}" ajaxurl="roomController.do?checkRoomid&id=${roomPage.id }" datatype="n">
						<span class="Validform_checktip">配电房标识</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							配电房描述:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="roomtext" name="roomtext" 
							   value="${roomPage.roomtext}" datatype="*1-200" ajaxurl="roomController.do?checkRoomname&id=${roomPage.id }">
						<span class="Validform_checktip">配电房描述</span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>