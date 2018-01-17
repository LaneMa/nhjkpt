<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学校基本信息配置</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script charset="utf-8" type="text/javascript" src="plug-in/kindeditor/kindeditor.js"></script>
<script charset="utf-8" type="text/javascript" src="plug-in/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript">
        KindEditor.ready(function(K) {
                window.editor = K.create('#editor_id',{
                	afterBlur:function(){ 
                        this.sync(); 
                  }
                });
        });
</script>

</head>
<body style="overflow-y: hidden" scroll=no>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="schoolinfoController.do?save">
		<input id="id" name="id" type="hidden" value="${schoolinfoPage.id }">
		<table style="width: 600px" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">学校标志:</label>
				</td>
				<td class="value"><input class="inputxt" id="schoolid"
					name="schoolid" value="${schoolinfoPage.schoolid}" datatype="s1-16"
					ajaxurl="schoolinfoController.do?checkSchoolId&id=${schoolinfoPage.id }">
					<span class="Validform_checktip">输入正确的学校标志</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">学校名称:</label>
				</td>
				<td class="value"><input class="inputxt" id="schoolname"
					name="schoolname" value="${schoolinfoPage.schoolname}" datatype="*"
					ajaxurl="schoolinfoController.do?checkSchoolname&id=${schoolinfoPage.id }">
					<span class="Validform_checktip">输入正确的学校名称</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">超时时间类型:</label>
				</td>
				<td class="value">
					<select style="width: 150px" id="alarmTimeType" name="alarmTimeType">
						<option value="5" ${schoolinfoPage.alarmTimeType==5?'selected="selected"':'' }>天</option>
						<option value="11" ${schoolinfoPage.alarmTimeType==11?'selected="selected"':'' }>小时</option>
						<option value="12" ${schoolinfoPage.alarmTimeType==12?'selected="selected"':'' }>分钟</option>
					</select>
					<span class="Validform_checktip">选择超时时间类型</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">超时时间:</label>
				</td>
				<td class="value"><input class="inputxt" id="alarmTime"
					name="alarmTime" value="${schoolinfoPage.alarmTime}" datatype="n">
					<span class="Validform_checktip">输入正确的超时时间</span></td>
			</tr>
			
			
			<tr>
				<td align="right"><label class="Validform_label">用水配额(吨):</label>
				</td>
				<td class="value"><input class="inputxt" id="waterlimit"
					name="waterlimit" value="${schoolinfoPage.waterlimit}" datatype="*">
					<span class="Validform_checktip">输入正确的用水配额</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">用电配额(度)</label>
				</td>
				<td class="value"><input class="inputxt" id="electriclimit"
					name="electriclimit" value="${schoolinfoPage.electriclimit}" datatype="*">
					<span class="Validform_checktip">输入正确的用电配额</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">用气配额(立方):</label>
				</td>
				<td class="value"><input class="inputxt" id="gaslimit"
					name="gaslimit" value="${schoolinfoPage.gaslimit}" datatype="*">
					<span class="Validform_checktip">输入正确的用气配额</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">用热配额(焦耳):</label>
				</td>
				<td class="value"><input class="inputxt" id="heatlimit"
					name="heatlimit" value="${schoolinfoPage.heatlimit}" datatype="*">
					<span class="Validform_checktip">输入正确的用热配额</span></td>
			</tr>
			
			
			
			<tr>
				<td align="right"><label class="Validform_label">学校描述:</label>
				</td>
				<td class="value">
					<textarea id="editor_id" name="schooltext" style="width:500px;height:50px;">${schoolinfoPage.schooltext}</textarea>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>