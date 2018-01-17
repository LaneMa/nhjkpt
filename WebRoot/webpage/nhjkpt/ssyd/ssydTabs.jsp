<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:tabs id="ssydTabs" iframe="false"  tabPosition="bottom">
 <c:forEach items="${layerList}" var="layer">
  <t:tab iframe="ssydinfoController.do?ssList&layername=${buildingname}-${layer.layername}" icon="icon-add" title="${layer.layername}层" id="${layer.layername}"></t:tab>
 </c:forEach>
</t:tabs>
