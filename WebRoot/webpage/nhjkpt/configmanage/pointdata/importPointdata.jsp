<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>导入历史数据</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  	var msg='${message}';
  	if(msg=="start"){
  		tip("开始同步数据");
  	}else if(msg=="has"){
  		tip("已经有正在同步的数据");
  	}
  </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="pointdataController.do?doImport">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					开始时间:
				</label>
			</td>
			<td class="value">
				<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" id="startDate" name="startDate" datatype="*">
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					结束时间:
				</label>
			</td>
			<td class="value">
				<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" id="endDate" name="endDate" datatype="*">
				<span class="Validform_checktip"></span>
			</td>
		</tr>
	</table>
</t:formvalid>
 </body>