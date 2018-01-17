<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>瞬时基本信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //初始化下标
	function resetTrNum(tableId) {
		$tbody = $("#"+tableId+"");
		$tbody.find('>tr').each(function(i){
			$(':input, select', this).each(function(){
				var $this = $(this), name = $this.attr('name'), val = $this.val();
				if(name!=null){
					if (name.indexOf("#index#") >= 0){
						$this.attr("name",name.replace('#index#',i));
					}else{
						var s = name.indexOf("[");
						var e = name.indexOf("]");
						var new_name = name.substring(s+1,e);
						$this.attr("name",name.replace(new_name,i));
					}
				}
			});
		});
	}
 </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="meterShowHistoryController.do?save">
			<input id="id" name="id" type="hidden" value="${meterShowHistoryPage.id }">
			<table cellpadding="0" cellspacing="1" class="formtable">
			<tr>
			<td align="right"><label class="Validform_label">大楼id:</label></td>
			<td class="value">
				<input nullmsg="请填写大楼id" errormsg="大楼id格式不对" class="inputxt" id="buildingid" name="buildingid" ignore="ignore"
									   value="${meterShowHistoryPage.buildingid}">
								<span class="Validform_checktip"></span>
			</td>
			<td align="right"><label class="Validform_label">gateway:</label></td>
			<td class="value">
				<input nullmsg="请填写gateway" errormsg="gateway格式不对" class="inputxt" id="gatewayid" name="gatewayid" ignore="ignore"
									   value="${meterShowHistoryPage.gatewayid}">
								<span class="Validform_checktip"></span>
			</td>
			</tr>
			<tr>
			<td align="right"><label class="Validform_label">包类型:</label></td>
			<td class="value">
				<input nullmsg="请填写包类型" errormsg="包类型格式不对" class="inputxt" id="datatype" name="datatype" ignore="ignore"
									   value="${meterShowHistoryPage.datatype}">
								<span class="Validform_checktip"></span>
			</td>
			<td align="right"><label class="Validform_label">采集时间:</label></td>
			<td class="value">
				<input nullmsg="请填写采集时间" errormsg="采集时间格式不对" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="senddate" name="senddate" ignore="ignore"
									     value="<fmt:formatDate value='${meterShowHistoryPage.senddate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
								<span class="Validform_checktip"></span>
			</td>
			</tr>
			<tr>
			<td align="right"><label class="Validform_label">sequenc:</label></td>
			<td class="value">
				<input nullmsg="请填写sequenc" errormsg="sequenc格式不对" class="inputxt" id="sequence" name="sequence" ignore="ignore"
									   value="${meterShowHistoryPage.sequence}" datatype="n">
								<span class="Validform_checktip"></span>
			</td>
			<td align="right"><label class="Validform_label">电表:</label></td>
			<td class="value">
				<input nullmsg="请填写电表" errormsg="电表格式不对" class="inputxt" id="meterid" name="meterid" ignore="ignore"
									   value="${meterShowHistoryPage.meterid}">
								<span class="Validform_checktip"></span>
			</td>
			</tr>
			</table>
			<div style="width: auto;height: 200px;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:690px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="meterShowHistoryController.do?meterFuncShowHistoryList&id=${meterShowHistoryPage.id}" icon="icon-search" title="瞬时基本信息" id="meterFuncShowHistory"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 产品明细 模版 -->
		<table style="display:none">
		<tbody id="add_meterFuncShowHistory_table_template">
			<tr>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td align="left"><input name="meterFuncShowHistoryList[#index#].funcid" maxlength="32" type="text" style="width:120px;"></td>
				  <td align="left"><input name="meterFuncShowHistoryList[#index#].showdata" maxlength="" type="text" style="width:120px;"></td>
			</tr>
		 </tbody>
		</table>
 </body>