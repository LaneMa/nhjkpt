<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>瞬时值</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
	<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',split:true,border:false" style="height:60px">
		<span id="buildingshowdata">
		
		<c:set var="b" value="0"/>
		<c:set var="c" value="0"/>
		
		<table border="0"   cellspacing="0" cellpadding="0" ><tr>
			<c:forEach items="${listshow }" var="show">
				
				<c:if test='${ fn:contains(show, "B") && b==0 }'>
					<c:set var="b" value="1"/>
					</tr><tr>
				</c:if>
				
				<c:if test='${ fn:contains(show, "C") && c==0 }'>
					<c:set var="c" value="1"/>
					</tr><t r>
				</c:if>
				<td align="left">	
				
				${show }
				
				<c:if test='${ fn:contains(show, "有功功率") }'>
					w
				</c:if>
				<c:if test='${ fn:contains(show, "电压") }'>
					v
				</c:if>
				
				<c:if test='${ fn:contains(show, "电流") }'>
					A
				</c:if>
				
				&nbsp;&nbsp;&nbsp;&nbsp;
				
				</td>	
			</c:forEach>
		</tr></table>
		</span>
	</div>
	<div data-options="region:'center',border:false">
		<t:tabs id="buildinghometab" iframe="false" tabPosition="bottom" heigth="90%">
		  <t:tab href="meterFuncShowHistoryController.do?stacurveCurrent&id=${id }" icon="icon-search" title="电流曲线图" id="pnode"></t:tab>
		</t:tabs>
	</div>
</div>
 </body>