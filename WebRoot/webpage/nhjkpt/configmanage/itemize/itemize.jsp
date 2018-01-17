<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>分类配置</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="div" action="itemizeController.do?save">
		<input id="id" name="id" type="hidden" value="${itemizePage.id }">
		<table style="width: 600px" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">父分类:</label></td>
				<td class="value"><t:comboTree 
						url="itemizeController.do?itemizeTree" name="itemize.id"
						id="itemize" value="${itemizePage.itemize.id}"></t:comboTree><a href=""
					class="easyui-linkbutton" plain="true" icon="icon-redo"
					onClick="clearAll();">清空</a> <span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">分类标识号:</label></td>
				<td class="value"><input class="inputxt" id="itemizeid"
					name="itemizeid" value="${itemizePage.itemizeid}" datatype="n"
					ajaxurl="itemizeController.do?checkItemizeid&id=${itemizePage.id }">
					<span class="Validform_checktip">输入分类标识号</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">分类名称:</label></td>
				<td class="value"><input class="inputxt" id="itemizetext"
					name="itemizetext" value="${itemizePage.itemizetext}" datatype="*"
					ajaxurl="itemizeController.do?checkItemizetext&id=${itemizePage.id }">
					<span class="Validform_checktip">输入分类名称</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">分类级别:</label></td>
				<td class="value"><input class="inputxt" id="level"
					name="level" value="${itemizePage.level}" datatype="n"> <span
					class="Validform_checktip">输入分类级别 </span></td>
			</tr>
			<tr height="100px">
				<td colspan="2" class="value"></td>
			</tr>
		</table>
	</t:formvalid>
</body>

<script type="text/javascript">
	function clearAll() {
		itemize.value = '';
	}
</script>