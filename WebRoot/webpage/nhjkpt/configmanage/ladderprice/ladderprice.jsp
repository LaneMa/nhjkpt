<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>阶梯价格</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="ladderpricecontroller.do?save">
			<input id="id" name="id" type="hidden" value="${lightingPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							楼层id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="floorid" name="floorid" ignore="ignore"
							   value="${lightingPage.floorid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							楼id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="buildingid" name="buildingid" ignore="ignore"
							   value="${lightingPage.buildingid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							教室id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="roomid" name="roomid" ignore="ignore"
							   value="${lightingPage.roomid}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							控制类型:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="controlType" name="controlType" ignore="ignore"
							   value="${lightingPage.controlType}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							控制数据:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="controlData" name="controlData" ignore="ignore"
							   value="${lightingPage.controlData}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							光照度:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="lightFalls" name="lightFalls" ignore="ignore"
							   value="${lightingPage.lightFalls}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							人数:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="userNum" name="userNum" ignore="ignore"
							   value="${lightingPage.userNum}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							灯数:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="lightNum" name="lightNum" ignore="ignore"
							   value="${lightingPage.lightNum}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							灯状态:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="lightType" name="lightType" ignore="ignore"
							   value="${lightingPage.lightType}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							保留1:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="retain1" name="retain1" ignore="ignore"
							   value="${lightingPage.retain1}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							保留2:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="retain2" name="retain2" ignore="ignore"
							   value="${lightingPage.retain2}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>