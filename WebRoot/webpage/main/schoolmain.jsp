
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
  <title>高校能耗监控分析平台</title>
    <link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="js/png.js" ></script>
<script type="text/javascript" src="js/jquery.js" ></script>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <LINK rel=stylesheet type=text/css href="webpage/main/images/CSS.css">
  <link rel="shortcut icon" href="images/favicon.ico">

  <style type="text/css">
a {
	color: Black;
	text-decoration: none;
}

a:hover {
	color: black;
	text-decoration: none;
}
</style>
  <SCRIPT type="text/javascript">
  var buildingTip;
	$(function() {
		$('#layout_east_calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});
	});
	function showdata(id){
		openwindow('瞬时信息','alarminfoController.do?openAlarm&id='+id,"",800,500);
	}
	function systemlink(url,type){
		if(type==0){
			window.open(url);
		}else{
			window.location.href=url;
		}
	}
	function loadMapHome(){
		
			var divnow=$("a.hover");
			divnow.removeClass("hover");
			$("#menu11").addClass("hover");
			
		$('#buju').layout('collapse','west');
		addOnlyOneTab("首页","schoolinfoController.do?maphome");
	}
</SCRIPT>
 </head>
 <body id="buju" class="easyui-layout" style="overflow-y: hidden" scroll="no">
  <!-- 顶部-->
  <div region="north" border="false" title="" style="BACKGROUND:#E6E6FA;height: 139px; padding: 1px; overflow: hidden;">
   
   <div class="header">
	<div class="header_bg">${schoolinfo.schoolname }能耗管理分析平台</div>
</div>
<div class="main">
	<ul class="nav">
	<li><A id="menu11" class="hover" onclick="javascript:loadMapHome();" href="#">首页</A></li>
    <li><A id="menu0"  onclick="javascript:$('#buju').layout('expand','west');loadHome('itemizeController.do?treeItemize','itemizeController.do?schoolitemize','menu0')" href="#">学校</A></li>
    <li><A id="menu1" onclick="javascript:loadBuilding('buildinginfoController.do?treeBuilding','buildinginfoController.do?buildingMapShell','menu1');" href="#">建筑物管理</A></li>
    <li><A id="menu7" onclick="javascript:loadRoom('departmentinfoController.do?treeDepartment','departmentinfoController.do?departmenthome','menu7');" href="#">分户管理</A></li>
    <c:if test="${waterSwitch!=null && waterSwitch=='on'}">
		　　		
　　   
	 <li><A id=menu5 onclick="javascript:loadRoom('meterController.do?treeWater','meterController.do?waterhome','menu5');" href="#">供水管网管理</A></li>

	</c:if>
	
	<c:if test="${heatSwitch!=null && heatSwitch=='on'}">
		<li>
			<A id=menu6 onclick="javascript:loadRoom('meterController.do?treeCalorie','meterController.do?caloriehome','menu6');" href="#">供热管网管理</A>
		</li>
	</c:if>
	
	
		<li>
			<A id=menu2 onclick="javascript:loadRoom('roomController.do?treeRoom','roomController.do?roomhome','menu2');" href="#">配电房管理</A>
		</li>
		<li>
			<A id=menu3  onclick="javascript:loadtree('buildinginfoController.do?treeLeftReport','buildinginfoController.do?reportManage','menu3');addOnlyOneTab('建筑物','buildinginfoController.do?reportManage&id=building');" href="#">报表管理</A>
		</li>
		
		<li>
			<A id=menu4 onclick="javascript:loadtree('buildingItemizeController.do?treeContrast','buildingItemizeController.do?contrasthome','menu4');addOnlyOneTab('同分类同大楼不通时间','buildingItemizeController.do?contrasthome&id=conrastdate','');" href="#">能耗比较</A>
		</li>
		
	<c:if test="${ssydSwitch!=null && ssydSwitch=='on'}">
		<li>
			<A id=menu8  onclick="javascript:loadSsyd('ssydinfoController.do?treeSsyd','ssydinfoController.do?layerTabs','menu8');" href="#">宿舍用电</A>
		</li>
	</c:if>
	
	<c:if test="${yplxjSwitch!=null && yplxjSwitch=='on'}">
		<li>
			<A id=menu9  href="http://172.16.116.244/doc/page/login.asp" target="_blank">硬盘录像机</A>
		</li>
	</c:if>
	
	<c:if test="${jszmSwitch!=null && jszmSwitch=='on'}">
		<li >
			<A id=menu10  onclick="javascript:loadJszm('roomlightController.do?treeRoomlight','roomlightController.do?roomlightControl','menu10');" href="#">教室照明</A>
		</li>
	</c:if>
	
	
	
    <div style="position: absolute; right: 0px; bottom: 0px;">
         <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-help" >控制面板</a>
        </div>
        <div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
         <div onclick="add('修改密码','userController.do?changepassword')">
          修改密码
         </div>
          <div onclick="exit('schoolinfoController.do?schoollogin','确定退出该系统吗 ?',1);">
          退出登录
         </div>
         <div onclick="openwindow('用户信息','userController.do?userinfo')">
          用户信息
         </div>
         
         <div onclick="add('修改时间','userController.do?changetime')">
          修改系统时间
         </div>
         
         <div class="menu-sep"></div>
         <c:forEach items="${systemlink }" var="link">
         	<div onclick="systemlink('${link.url}','${link.type }')">${link.name }</div>
         </c:forEach>
        </div>
    </ul>
</div>

  </div>
  <!-- 左侧-->
  <div id="west" region="west" split="true" href="loginController.do?schoolleft" title="导航菜单" style="width: 150px; padding: 1px;">
  
  
  </div>
  <!-- 中间-->
  <div id="mainPanle" region="center" style="overflow: hidden;">
   <div id="maintabs" class="easyui-tabs" fit="true" border="false">
    <div class="easyui-tab" title="首页" href="schoolinfoController.do?maphome" style="padding:2px; overflow: hidden;">
   				 
    </div>
    <c:if test="${map=='1'}">
     <div class="easyui-tab" title="地图" style="padding: 1px; overflow: hidden;">
      <iframe name="myMap" id="myMap" scrolling="no" frameborder="0" src="mapController.do?map" style="width: 100%; height: 99.5%;"></iframe>
     </div>
    </c:if>
   </div>
  </div>
  
  <!-- 底部 -->
  <div region="south" border="false" style="height: 30px; overflow: hidden">
  	<table width="100%">
  		<tr>
  			<td>
  			<marquee>
  				<c:forEach items="${alarms }" var="alarm">
					<a href="#" onclick="showdata('${alarm.meterid}');">${alarm.info }</a>
				</c:forEach>
			</marquee>
  			</td>
  			<td width="30%">&nbsp;</td>
  		</tr>
  	</table>
  </div>
 </body>
</html>

<SCRIPT type="text/javascript">

$(document).ready(function () {
	$('#buju').layout('collapse','west');
});

</SCRIPT>