<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>大楼需要展示的瞬时功能</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="buildingShowFuncController.do?save">
			<input id="id" name="id" type="hidden" value="${buildingShowFuncPage.id }">
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
									<c:if test="${building.buildingid==buildingShowFuncPage.buildingid }">selected="selected"</c:if>>
									${building.buildingname}
								</option>
							</c:forEach>
						</select>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							唯一标识:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="showid" name="showid" ignore="ignore"
							   value="${buildingShowFuncPage.showid}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							瞬时描述:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="showtext" name="showtext" ignore="ignore"
							   value="${buildingShowFuncPage.showtext}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							表具:
						</label>
					</td>
					<td class="value">
						<input name="meterid" id="meterid" type="hidden" value="${buildingShowFuncPage.meterid}"> 
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
								<c:if test="${fn:trim(func.funcid)==buildingShowFuncPage.funcid }">selected="selected"</c:if>>
								${func.funcname}
							</option>
						</c:forEach>
						</select>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
 <script type="text/javascript">
	//检查表具是否多选
	function isChooseOne() {
		var s = $("#metertext").val();
		if (s.indexOf(",") > 0) {
			clearAll();
			//tip("表具只能选择一个，请重新选择");
			$.dialog.tips('表具只能选择一个，请重新选择', 1);
		}

		return true;

	}
</script>