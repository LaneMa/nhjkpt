<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>表具配置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="funcController.do?save">
			<input id="id" name="id" type="hidden" value="${funcPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							功能标号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="funcid" name="funcid" 
							   value="${funcPage.funcid}" ajaxurl="funcController.do?checkFuncid&id=${funcPage.id }" datatype="n">
						<span class="Validform_checktip">功能标号（数字）</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							功能名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="funcname" name="funcname" 
							   value="${funcPage.funcname}" ajaxurl="funcController.do?checkFuncname&id=${funcPage.id }" datatype="*">
						<span class="Validform_checktip">功能名称</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							类型:
						</label>
					</td>
					<td class="value">
						<select id="ischeck" name="ischeck">
							<option value="0" <c:if test="${funcPage.ischeck=='0' }">selected="selected"</c:if>>瞬时</option>
							<option value="1" <c:if test="${funcPage.ischeck=='1' }">selected="selected"</c:if>>统计</option>
							<option value="2" <c:if test="${funcPage.ischeck=='2' }">selected="selected"</c:if>>电流</option>
						</select>
						<span class="Validform_checktip">功能类型</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							显示名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="showtext" name="showtext" value="${funcPage.showtext}" datatype="*">
						<span class="Validform_checktip">显示名称</span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>