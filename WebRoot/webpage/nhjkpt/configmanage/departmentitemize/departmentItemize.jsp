<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>系分类用能计算用表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="departmentItemizeController.do?save">
			<input id="id" name="id" type="hidden" value="${departmentItemizePage.id }">
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
									<c:if test="${department.departmentid==departmentItemizePage.departmentid }">selected="selected"</c:if>>
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
							分类:
						</label>
					</td>
					<td class="value">
						<input name="itemizeid" id="itemizeid" type="hidden" value="${departmentItemizePage.itemizeid}"> 
						 <input name="itemizetext" id="itemizetext" class="inputxt" value="${itemizeName }" readonly="readonly" datatype="*" /> 
						 <t:choose hiddenName="itemizeid" hiddenid="itemizeid" url="itemizeController.do?itemizes" name="itemizeList" icon="icon-choose" title="分类列表" textname="itemizetext" fun="isChooseOneitemize"></t:choose> 
						 <span class="Validform_checktip">请选择分类</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							测量表具:
						</label>
					</td>
					<td class="value">
						<input name="meterid" id="meterid" type="hidden" value="${departmentItemizePage.meterid}"> 
						 <input name="metertext" id="metertext" class="inputxt" value="${meterName }" readonly="readonly" datatype="*" /> 
						 <t:choose hiddenName="meterid" hiddenid="meterid" url="metershowfuncController.do?meters" name="meterList" icon="icon-choose" title="表具列表" textname="metertext" fun="isChooseOne"></t:choose> 
						 <span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							功能:
						</label>
					</td>
					<td class="value">
						<select name="funcid">
						<c:forEach items="${funclist}" var="func">
							<option value="${func.funcid}"
								<c:if test="${fn:trim(func.funcid)==departmentItemizePage.funcid }">selected="selected"</c:if>>
								${func.funcname}
							</option>
						</c:forEach>
						</select>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							拆分系数:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="factor" name="factor" ignore="ignore"
							   value="${departmentItemizePage.factor}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>