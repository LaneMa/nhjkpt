<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>电表需要展示的功能</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="metershowfuncController.do?save">
		<input id="id" name="id" type="hidden"
			value="${metershowfuncPage.id }">
		<table style="width: 600px; cellpadding="0"
			cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">表具:</label></td>
				<td class="value">
					<!-- <select name="meterid">
					<c:forEach items="${meterlist}" var="meter">
						<option value="${meter.meterid}"
							<c:if test="${meter.meterid==metershowfuncPage.meterid }">selected="selected"</c:if>>
							${meter.metertext}
						</option>
					</c:forEach>
				</select>
			 --> 
			 <input name="meterid" id="meterid" type="hidden" value="${metershowfuncPage.meterid}"> 
			 <input name="metertext" id="metertext" class="inputxt" value="${meterName }" readonly="readonly" datatype="*" /> 
			 <t:choose hiddenName="meterid" hiddenid="meterid" url="metershowfuncController.do?meters" name="meterList" icon="icon-choose" title="表具列表" textname="metertext" fun="isChooseOne"></t:choose> 
			 <span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">功能号:</label>
				</td>
				<td class="value"><select name="funcid">
						<c:forEach items="${funclist}" var="func">
							<option value="${func.funcid}"
								<c:if test="${fn:trim(func.funcid)==metershowfuncPage.funcid }">selected="selected"</c:if>>
								${func.funcname}</option>
						</c:forEach>
				</select> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">上限:</label></td>
				<td class="value"><input class="inputxt" id="top" name="top"
					ignore="ignore" value="${metershowfuncPage.top}" datatype="d">
					<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">下限:</label></td>
				<td class="value"><input class="inputxt" id="floor"
					name="floor" ignore="ignore" value="${metershowfuncPage.floor}"
					datatype="d"> <span class="Validform_checktip"></span></td>
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