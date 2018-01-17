<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>报警信息列表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="alarminfoController.do?save">
		<input id="id" name="id" type="hidden" value="${alarminfoPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">表具名称:</label></td>
				<td class="value">
					<input name="meterid" id="meterid" type="hidden" value="${alarminfoPage.meterid}"> 
					<input name="metertext" id="metertext" class="inputxt" value="${meterName }" readonly="readonly" ignore="ignore" datatype="*" /> 
					<t:choose hiddenName="meterid" hiddenid="meterid" url="meterController.do?metersChoose" name="meterList"
						icon="icon-choose" title="表具列表" textname="metertext"  fun="isChooseOne"></t:choose> 
					<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">报警时间:</label></td>
				<td class="value"><input class="Wdate"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					style="width: 150px" id="alarmtime" name="alarmtime"
					value="<fmt:formatDate value='${alarminfoPage.alarmtime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>"
					datatype="*"> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">报警信息:</label></td>
				<td class="value">
				<textarea id="info" name="info" style="width:500px;height:50px;"
					ignore="ignore">${alarminfoPage.info}</textarea></td>
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
</html>