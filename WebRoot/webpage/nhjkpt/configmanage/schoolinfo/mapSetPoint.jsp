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
			url : 'schoolinfoController.do?saveMapPoint&roomid='+$("#roomid").val()+'&joinroomid='+$("#joinroomid").val()+'&meterid='+$("#meterid").val()+'&joinmeterid='+$("#joinmeterid").val()+'&lng='+$("#lng").val()+'&lat='+$("#lat").val(),
			success : function(jsondata) {
				var data=eval('(' + jsondata + ')');
				if(data){
					tip("保存成功");
					parent.document.getElementById("mapHome").contentWindow.loadOverlays();
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
		layout="div" action="schoolinfoController.do?save">
		<table style="width: 500px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">配电室:</label></td>
				<td class="value"><select name="roomid" id="roomid">
					<c:forEach items="${listRoom}" var="room">
						<c:choose>
						   <c:when test="${room.id==setRoom.id }">    <option value="${room.id}" selected>${room.roomtext}</option>
						   </c:when>			   
						   <c:otherwise>  <option value="${room.id}">${room.roomtext}</option>
						   </c:otherwise> 
						</c:choose>		
					</c:forEach>
				</select> <span class="Validform_checktip">请选择配电室</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">连接到配电室:</label></td>
				<td class="value"><select name="joinroomid" id="joinroomid">
						<c:forEach items="${listRoom}" var="room">
							<c:choose>
							   <c:when test="${room.roomid==setRoom.joinroomid }">    <option value="${room.roomid}" selected>${room.roomtext}</option>
							   </c:when>
							   <c:otherwise>  <option value="${room.roomid}">${room.roomtext}</option>
							   </c:otherwise>
							</c:choose>
						</c:forEach>
				</select> <span class="Validform_checktip">请选择要连接的配电室</span></td>
			</tr>
			
			
			<tr>
				<td align="right"><label class="Validform_label">经度:</label></td>
				<td class="value"><input class="inputxt" id="lng" 
					datatype="n" value="${lng}"> <span class="Validform_checktip">请输入经度</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">纬度:</label></td>
				<td class="value"><input class="inputxt" id="lat" 
					datatype="n" value="${lat}"> <span class="Validform_checktip">请输入纬度</span></td>
			</tr>
			
			
			
			
			<tr>
				<td align="right"><label class="Validform_label">关联表具:</label></td>
				<td class="value"><select name="meterid" id="meterid">
					<c:forEach items="${listMeter}" var="meter">
						<c:choose>
						   <c:when test="${meter.id==setMeter.id }">    <option value="${meter.id}" selected>${meter.meterid}-${meter.metertext}</option>
						   </c:when>			   
						   <c:otherwise>  <option value="${meter.id}">${meter.meterid}-${meter.metertext}</option>
						   </c:otherwise> 
						</c:choose>		
					</c:forEach>
				</select> <span class="Validform_checktip">请选择关联表具</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">连接到表具:</label></td>
				<td class="value"><select name="joinmeterid" id="joinmeterid">
						<c:forEach items="${listMeter}" var="meter">
							<c:choose>
							   <c:when test="${meter.meterid==setMeter.joinmeterid }">    <option value="${meter.meterid}" selected>${meter.meterid}-${meter.metertext}</option>
							   </c:when>
							   <c:otherwise>  <option value="${meter.meterid}">${meter.meterid}-${meter.metertext}</option>
							   </c:otherwise>
							</c:choose>
						</c:forEach>
				</select> <span class="Validform_checktip">请选择要连接的表具</span></td>
			</tr>
			
			
			
			
			
			
			<tr>
				<td colspan="2" align="center"><input type="button" value="保存"
					onclick="doSave();"></td>
			</tr>
			
			
			
			
			
		</table>
	</t:formvalid>
</body>