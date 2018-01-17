<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<head>
<title>主页</title>

<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="js/png.js" ></script>
<script type="text/javascript" src="js/jquery.js" ></script>


<t:base type="jquery,easyui,tools,DatePicker"></t:base>



</head>
<body style="overflow-y: hidden" scroll="no">
<div style="height:200px">

	
		<div class="easyui-layout" data-options="fit:true">
		
			<div data-options="region:'west',border:false"  style="width:180px;height:137px"  >
			<dl class="in_m_r_zzt" style="padding-top:20px;padding-left:15px">
			
		        <dt><i><img src="images/iocn_01.png" /></i>当日用电的柱状图</dt>
		        
		        </dl>
			</div>
			
			
			<div data-options="region:'center',split:true,border:false,href:'schoolinfoController.do?schoolEnergySortNC'" style="height:137px"    />
				
		  		
		  	
		</div>
</div>

<div style="height:200px">

	
		<div class="easyui-layout" data-options="fit:true">
		
			<div data-options="region:'west',border:false"  style="width:180px;height:137px"  >
			<dl class="in_m_r_zzt" style="padding-top:20px;padding-left:15px">
			
		        <dt><i><img src="images/iocn_02.png" /></i>当日用水的柱状图</dt>
		        
		        </dl>
			</div>
			<div data-options="region:'center',split:true,border:false,href:'schoolinfoController.do?schoolEnergySortNE'" style="height:137px"    >
			 
				
			</div>
			
		</div>
</div>

<ul class="in_tongji_lie">
        <li class="jinri">今日用电统计：${eUseToday }千瓦时</li>
        <li class="jinri">今日用水统计：${wUseToday }吨</li>
        <li class="jinri">今日用热统计：${hUseToday }焦耳</li>
        <li  class="jinri">今日能耗费用：<fmt:formatNumber value="${(eUseToday*0.5+wUseToday*4+hUseToday*23)/10000 }" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>万元</li>

		<li>今年用电统计：<fmt:formatNumber value="${electricUse/10000}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>  万千瓦时</li>
        <li>今年用电配额：<fmt:formatNumber value="${electricLimit/10000}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>万千瓦时</li>
        <li>今年用水统计：${waterUse }吨</li>
        <li>今年用水配额：${waterLimit }吨</li>
        <li>今年用热统计：${heatUse }焦耳</li>
        <li>今年用热配额：${heatLimit }焦耳</li>
        <li>今年能耗费用：<fmt:formatNumber value="${useMoneyYear/10000}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>万元</li>
        <li>今年费用配额：<fmt:formatNumber value="${(electricLimit*0.5+waterLimit*4+heatLimit*23)/10000 }" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>万元</li>
        </ul>


</body>
</html>


