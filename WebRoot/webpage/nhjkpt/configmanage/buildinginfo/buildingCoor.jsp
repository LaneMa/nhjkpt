<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>建筑物配置</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	function doSave(){
		$.ajax({
			type : "POST",
			url : 'buildinginfoController.do?saveBuildingCoor&id='+$("#buildingid").val()+"&x="+$("#x").val()+"&y="+$("#y").val(),
			success : function(jsondata) {
				var data=eval('(' + jsondata + ')');
				if(data){
					tip("保存成功");
					parent.document.getElementById("buildingMap").contentWindow.updateBuilding();
				}else{
					tip("保存失败");
				}
			}
		});
	};	
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="div" action="buildinginfoController.do?save">
		<table style="width: 300px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">建筑物:</label></td>
				<td class="value"><select name="buildingid" id="buildingid">
						<c:forEach items="${listbuilding}" var="building">
							<option value="${building.id}">${building.buildingname}
							</option>
						</c:forEach>
				</select> <span class="Validform_checktip">请选择建筑物</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">x坐标:</label></td>
				<td class="value"><input class="inputxt" id="x" name="x"
					datatype="n" value="${x}"> <span class="Validform_checktip">请输入x坐标</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">y坐标:</label></td>
				<td class="value"><input class="inputxt" id="y" name="y"
					datatype="n" value="${y}"> <span class="Validform_checktip">请输入y坐标</span></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" value="保存"
					onclick="doSave();"></td>
			</tr>
		</table>
	</t:formvalid>
</body>