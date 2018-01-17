<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>建筑物配置</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="div" action="buildinginfoController.do?save">
		<input id="id" name="id" type="hidden" value="${buildinginfoPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">建筑物标识号:</label></td>
				<td class="value"><input class="inputxt" id="buildingid"
					name="buildingid" value="${buildinginfoPage.buildingid}"
					datatype="s1-50"
					ajaxurl="buildinginfoController.do?checkBuildingid&id=${buildinginfoPage.id }">
					<span class="Validform_checktip">请输入建筑物标识号</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">建筑物名称:</label></td>
				<td class="value"><input class="inputxt" id="buildingname"
					name="buildingname" value="${buildinginfoPage.buildingname}"
					datatype="*"
					ajaxurl="buildinginfoController.do?checkBuildingname&id=${buildinginfoPage.id }">
					<span class="Validform_checktip">请输入建筑物名称</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">x坐标:</label></td>
				<td class="value">
					<input class="inputxt" id="x" name="x" datatype="*" value="${buildinginfoPage.x}"> <span
					class="Validform_checktip">请输入x坐标</span></td>
			</tr><tr>
				<td align="right"><label class="Validform_label">y坐标:</label></td>
				<td class="value">
					<input class="inputxt" id="y" name="y" datatype="*" value="${buildinginfoPage.y}"> <span
					class="Validform_checktip">请输入y坐标</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">建筑物描述:</label></td>
				<td class="value">
					<input class="inputxt" id="buildingtext" name="buildingtext" ignore="ignore" value="${buildinginfoPage.buildingtext}"> <span
					class="Validform_checktip">请输入建筑物描述</span></td>
			</tr>
			
			
			<tr>
				<td align="right"><label class="Validform_label">用水配额(吨):</label>
				</td>
				<td class="value"><input class="inputxt" id="waterlimit"
					name="waterlimit" value="${buildinginfoPage.waterlimit}" datatype="*">
					<span class="Validform_checktip">输入正确的用水配额</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">用电配额(度)</label>
				</td>
				<td class="value"><input class="inputxt" id="electriclimit"
					name="electriclimit" value="${buildinginfoPage.electriclimit}" datatype="*">
					<span class="Validform_checktip">输入正确的用电配额</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">用气配额(立方):</label>
				</td>
				<td class="value"><input class="inputxt" id="gaslimit"
					name="gaslimit" value="${buildinginfoPage.gaslimit}" datatype="*">
					<span class="Validform_checktip">输入正确的用气配额</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">用热配额(焦耳):</label>
				</td>
				<td class="value"><input class="inputxt" id="heatlimit"
					name="heatlimit" value="${buildinginfoPage.heatlimit}" datatype="*">
					<span class="Validform_checktip">输入正确的用热配额</span></td>
			</tr>
			
		</table>
	</t:formvalid>
</body>