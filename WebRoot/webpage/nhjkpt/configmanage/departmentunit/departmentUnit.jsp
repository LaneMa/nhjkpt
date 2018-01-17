<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>分户单位用能</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="departmentUnitController.do?save">
			<input id="id" name="id" type="hidden" value="${departmentUnitPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							系:
						</label>
					</td>
					<td class="value">
						<select name="departmentid">
							<c:forEach items="${departmentlist}" var="department">
								<option value="${department.departmentid}"
									<c:if test="${department.departmentid==departmentUnitPage.departmentid }">selected="selected"</c:if>>
									${department.departmentname}
								</option>
							</c:forEach>
						</select>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单位标识:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="unitid" name="unitid" ignore="ignore"
							   value="${departmentUnitPage.unitid}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单位名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="unittext" name="unittext" ignore="ignore"
							   value="${departmentUnitPage.unittext}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单位总量:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="unitsum" name="unitsum" ignore="ignore"
							   value="${departmentUnitPage.unitsum}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				
				
				<tr>
					<td align="right"><label class="Validform_label">用水配额(吨):</label>
					</td>
					<td class="value"><input class="inputxt" id="waterlimit"
						name="waterlimit" value="${departmentUnitPage.waterlimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用水配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用电配额(度)</label>
					</td>
					<td class="value"><input class="inputxt" id="electriclimit"
						name="electriclimit" value="${departmentUnitPage.electriclimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用电配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用气配额(立方):</label>
					</td>
					<td class="value"><input class="inputxt" id="gaslimit"
						name="gaslimit" value="${departmentUnitPage.gaslimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用气配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用热配额(焦耳):</label>
					</td>
					<td class="value"><input class="inputxt" id="heatlimit"
						name="heatlimit" value="${departmentUnitPage.heatlimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用热配额</span></td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>