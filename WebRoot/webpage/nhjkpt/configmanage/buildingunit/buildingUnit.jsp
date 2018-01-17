<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>建筑单位用能统计计算表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="buildingUnitController.do?save">
			<input id="id" name="id" type="hidden" value="${buildingUnitPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							大楼:
						</label>
					</td>
					<td class="value">
						<select name="buildingid">
							<c:forEach items="${buildinglist}" var="building">
								<option value="${building.buildingid}"
									<c:if test="${building.buildingid==buildingUnitPage.buildingid }">selected="selected"</c:if>>
									${building.buildingname}
								</option>
							</c:forEach>
						</select>
						<span class="Validform_checktip">请选择大楼</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单位标识:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="unitid" name="unitid" value="${buildingUnitPage.unitid}" datatype="n">
						<span class="Validform_checktip">请输入唯一单位标识</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单位名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="unittext" name="unittext" value="${buildingUnitPage.unittext}" datatype="s1-16">
						<span class="Validform_checktip">请输入单位名</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单位总量:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="unitsum" name="unitsum" value="${buildingUnitPage.unitsum}" datatype="n">
						<span class="Validform_checktip">请输入单位总量</span>
					</td>
				</tr>
				
				
				<tr>
					<td align="right"><label class="Validform_label">用水配额(吨):</label>
					</td>
					<td class="value"><input class="inputxt" id="waterlimit"
						name="waterlimit" value="${buildingUnitPage.waterlimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用水配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用电配额(度)</label>
					</td>
					<td class="value"><input class="inputxt" id="electriclimit"
						name="electriclimit" value="${buildingUnitPage.electriclimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用电配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用气配额(立方):</label>
					</td>
					<td class="value"><input class="inputxt" id="gaslimit"
						name="gaslimit" value="${buildingUnitPage.gaslimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用气配额</span></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">用热配额(焦耳):</label>
					</td>
					<td class="value"><input class="inputxt" id="heatlimit"
						name="heatlimit" value="${buildingUnitPage.heatlimit}" datatype="*">
						<span class="Validform_checktip">输入正确的用热配额</span></td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>