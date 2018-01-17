<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>数据库连接</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="originadataController.do?save">
			<input id="id" name="id" type="hidden" value="${originadataPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							数据库:
						</label>
					</td>
					<td class="value">
						<select name="dbtype">
							<c:forEach items="${databaseList}" var="db">
								<option value="${db.dbtype}"
									<c:if test="${db.dbtype==originadataPage.dbtype }">selected="selected"</c:if>>
									${db.dbtype}
								</option>
							</c:forEach>
						</select>
						<span class="Validform_checktip">请选择正确的数据库类型</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							数据库IP:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="dbip" name="dbip" 
							   value="${originadataPage.dbip}" datatype="s1-30">
						<span class="Validform_checktip">请输入正确的数据库IP</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							数据库名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="dbname" name="dbname" 
							   value="${originadataPage.dbname}" datatype="s1-30">
						<span class="Validform_checktip">请输入正确的数据库名称</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="dbuser" name="dbuser" datatype="s1-30" 
							   value="${originadataPage.dbuser}">
						<span class="Validform_checktip">请输入正确的用户名</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							密码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="dbpwd" name="dbpwd" datatype="s1-30"
							   value="${originadataPage.dbpwd}">
						<span class="Validform_checktip">请输入正确的密码</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							表名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="tbname" name="tbname" datatype="s1-30"
							   value="${originadataPage.tbname}">
						<span class="Validform_checktip">请输入正确的表名</span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>