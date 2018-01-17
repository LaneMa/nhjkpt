<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>系基本信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="departmentinfoController.do?save">
			<input id="id" name="id" type="hidden" value="${departmentinfoPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							系名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="departmentname" name="departmentname" ignore="ignore"
							   value="${departmentinfoPage.departmentname}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							系编号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="departmentid" name="departmentid" ignore="ignore"
							   value="${departmentinfoPage.departmentid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							系描述:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="departmenttext" name="departmenttext" ignore="ignore"
							   value="${departmentinfoPage.departmenttext}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				
				<tr>
					<td align="right"><label class="Validform_label">用水配额(吨):</label>
					</td>
					<td class="value"><input class="inputxt" id="waterlimit"
						name="waterlimit" value="${departmentinfoPage.waterlimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用水配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用电配额(度)</label>
					</td>
					<td class="value"><input class="inputxt" id="electriclimit"
						name="electriclimit" value="${departmentinfoPage.electriclimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用电配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用气配额(立方):</label>
					</td>
					<td class="value"><input class="inputxt" id="gaslimit"
						name="gaslimit" value="${departmentinfoPage.gaslimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用气配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用热配额(焦耳):</label>
					</td>
					<td class="value"><input class="inputxt" id="heatlimit"
						name="heatlimit" value="${departmentinfoPage.heatlimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用热配额</span></td>
				</tr>
			</table>
		</t:formvalid>
 </body>