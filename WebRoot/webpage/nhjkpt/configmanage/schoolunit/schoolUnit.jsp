<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>学校单位用能统计计算表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="schoolUnitController.do?save">
			<input id="id" name="id" type="hidden" value="${schoolUnitPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							单位编码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="unitid" name="unitid" ajaxurl="schoolUnitController.do?checkunitid&code=${schoolUnitPage.unitid }" value="${schoolUnitPage.unitid}" datatype="n">
						<span class="Validform_checktip">请输入正确的单位编码</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单位名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="unittext" name="unittext" value="${schoolUnitPage.unittext}" datatype="s1-16">
						<span class="Validform_checktip">请输入正确的单位名</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单位总量:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="unitsum" name="unitsum" value="${schoolUnitPage.unitsum}" datatype="n">
						<span class="Validform_checktip">请输入单位总量</span>
					</td>
				</tr>
				
				
				<tr>
					<td align="right"><label class="Validform_label">用水配额(吨):</label>
					</td>
					<td class="value"><input class="inputxt" id="waterlimit"
						name="waterlimit" value="${schoolUnitPage.waterlimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用水配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用电配额(度)</label>
					</td>
					<td class="value"><input class="inputxt" id="electriclimit"
						name="electriclimit" value="${schoolUnitPage.electriclimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用电配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用气配额(立方):</label>
					</td>
					<td class="value"><input class="inputxt" id="gaslimit"
						name="gaslimit" value="${schoolUnitPage.gaslimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用气配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用热配额(焦耳):</label>
					</td>
					<td class="value"><input class="inputxt" id="heatlimit"
						name="heatlimit" value="${schoolUnitPage.heatlimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用热配额</span></td>
				</tr>
					
				
			</table>
		</t:formvalid>
 </body>