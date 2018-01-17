<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>建筑分类用能计算用表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="buildingItemizeController.do?save">
			<input id="id" name="id" type="hidden" value="${buildingItemizePage.id }">
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
									<c:if test="${building.buildingid==buildingItemizePage.buildingid }">selected="selected"</c:if>>
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
							分类:
						</label>
					</td>
					<td class="value">
						<input name="itemizeid" id="itemizeid" type="hidden" value="${buildingItemizePage.itemizeid}"> 
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
						<input name="meterid" id="meterid" type="hidden" value="${buildingItemizePage.meterid}"> 
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
								<c:if test="${fn:trim(func.funcid)==buildingItemizePage.funcid }">selected="selected"</c:if>>
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
							   value="${buildingItemizePage.factor}" datatype="n">
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
	function isChooseOneitemize() {
		var s = $("#itemizetext").val();
		if (s.indexOf(",") > 0) {
			clearAll();
			//tip("表具只能选择一个，请重新选择");
			$.dialog.tips('分类只能选择一个，请重新选择', 1);
		}

		return true;

	}
</script>