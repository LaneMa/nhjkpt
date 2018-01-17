
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
  <title>高校能耗监控分析平台</title>
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
</SCRIPT>
 </head>
 <body class="easyui-layout" style="overflow-y: hidden" scroll="no">
  <!-- 顶部-->
  <div region="north" border="false" title="" style="BACKGROUND:#E6E6FA;height: 95px; padding: 1px; overflow: hidden;">
   <table width="100%" border="0" cellpadding="0" cellspacing="0" style="background-image: url('webpage/main/images/banner.jpg');">
    <tr>
     <td align="left" style="vertical-align:text-bottom">
      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD height=65 width=511>
							
							<span style="font-size: 23pt; color: blue; font-weight: bold; font-family: '微软雅黑';">&nbsp;&nbsp;&nbsp;${schoolinfo.schoolname }能耗管理分析平台</span></TD>
							<TD rowspan="2" height="100">
								<TABLE border=0 cellSpacing=0 cellPadding=0 align=right>
									<TBODY>
										<TR>
											<TD>
												<A style="cusor: hand" href="#" onclick="openwindow('用户信息','userController.do?userinfo')">
													<IMG src="webpage/main/images/icon01.gif" width=88 height=23/>
												</A>
											</TD>
											<TD width=12>&nbsp;</TD>
											<TD>
												<A style="cusor: hand" href="#" onclick="exit('schoolinfoController.do?schoollogin','确定退出该系统吗 ?',1);">
													<IMG src="webpage/main/images/icon02.gif" width=88 height=23>
												</A>
											</TD>
										</TR>
									</TBODY>
								</TABLE>
							</TD>
						</TR>
						<tr>
							<td height="35" width="100%">
								<TABLE class=mar01 border=0 cellSpacing=0 cellPadding=0 width="100%">
								<TBODY>
									<TR>
										<TD>
											<DIV id=nav class=slidingBlock>
												<DIV id=menu0 class=dao01>
													<A onclick="javascript:loadHome('itemizeController.do?treeItemize','itemizeController.do?schoolitemize','menu0')" href="#">首页</A>
												</DIV>
												<DIV id=menu1 class=dao02>
													<A onclick="javascript:loadBuilding('buildinginfoController.do?treeBuilding','buildinginfoController.do?buildingMapShell','menu1');" href="#">建筑物管理</A>
												</DIV>
												<DIV id=menu7 class=dao02>
													<A onclick="javascript:loadRoom('departmentinfoController.do?treeDepartment','departmentinfoController.do?departmenthome','menu7');" href="#">分户管理</A>
												</DIV>
												
											<c:if test="${waterSwitch!=null && waterSwitch=='on'}">
		　　		
		　　   
												<DIV id=menu5 class=dao02>
													<A onclick="javascript:loadRoom('meterController.do?treeWater','meterController.do?waterhome','menu5');" href="#">供水管网管理</A>
												</DIV>
											</c:if>
											
											<c:if test="${heatSwitch!=null && heatSwitch=='on'}">
												<DIV id=menu6 class=dao02>
													<A onclick="javascript:loadRoom('meterController.do?treeCalorie','meterController.do?caloriehome','menu6');" href="#">供热管网管理</A>
												</DIV>
											</c:if>
											
											
												<DIV id=menu2 class=dao02>
													<A onclick="javascript:loadRoom('roomController.do?treeRoom','roomController.do?roomhome','menu2');" href="#">配电房管理</A>
												</DIV>
												<DIV id=menu3 class=dao02>
													<A onclick="javascript:loadtree('buildinginfoController.do?treeLeftReport','buildinginfoController.do?reportManage','menu3');addOnlyOneTab('建筑物','buildinginfoController.do?reportManage');" href="#">报表管理</A>
												</DIV>
												
												<DIV id=menu4 class=dao02>
													<A onclick="javascript:loadtree('buildingItemizeController.do?treeContrast','buildingItemizeController.do?contrasthome','menu4');addOnlyOneTab('同分类同大楼不通时间','buildingItemizeController.do?contrasthome&id=conrastdate','');" href="#">能耗比较</A>
												</DIV>
												
											<c:if test="${ssydSwitch!=null && ssydSwitch=='on'}">
												<DIV id=menu8 class=dao02>
													<A onclick="javascript:loadSsyd('ssydinfoController.do?treeSsyd','ssydinfoController.do?layerTabs','menu8');" href="#">宿舍用电</A>
												</DIV>
											</c:if>
											
											<c:if test="${yplxjSwitch!=null && yplxjSwitch=='on'}">
												<DIV id=menu9 class=dao02>
													<A href="http://172.16.116.244/doc/page/login.asp" target="_blank">硬盘录像机</A>
												</DIV>
											</c:if>
											
											<c:if test="${jszmSwitch!=null && jszmSwitch=='on'}">
												<DIV id=menu10 class=dao02>
													<A onclick="javascript:loadJszm('roomlightController.do?treeRoomlight','roomlightController.do?roomlightControl','menu10');" href="#">教室照明</A>
												</DIV>
											</c:if>
											
											
											</DIV>
										</TD>
									</TR>
								</TBODY>
							</TABLE>
							</td>
						</tr>
					</TBODY>
				</TABLE>
     </td>
     
     
     
      <td align="right" nowrap width=100>
      <table>
       <tr>
        <td valign="top" height="50">
         <span style="color: #CC33FF">当前用户:</span><span style="color: #666633">(${userName }) <span style="color: #CC33FF">职务</span>:<span style="color: #666633">${roleName }</span>
        </td>
       </tr>
       <tr>
       		<td height="50"></td>
       </tr>
       <tr>
       <td>
        <div style="position: absolute; right: 0px; bottom: 0px;">
         <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-help">控制面板</a>
        </div>
        <div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
         <div onclick="add('修改密码','userController.do?changepassword')">
          修改密码
         </div>
         <div class="menu-sep"></div>
         <c:forEach items="${systemlink }" var="link">
         	<div onclick="systemlink('${link.url}','${link.type }')">${link.name }</div>
         </c:forEach>
        </div>
       </td>
       </tr>
      </table>
     </td>
     
    
     <td align="right">
      &nbsp;&nbsp;&nbsp;
     </td>
    </tr>
   </table>
  </div>
  <!-- 左侧-->
  <div id="west" region="west" split="true" href="loginController.do?schoolleft" title="导航菜单" style="width: 150px; padding: 1px;">
  
  
  </div>
  <!-- 中间-->
  <div id="mainPanle" region="center" style="overflow: hidden;">
   <div id="maintabs" class="easyui-tabs" fit="true" border="false">
    <div class="easyui-tab" title="首页" href="schoolinfoController.do?home" style="padding:2px; overflow: hidden;">
   				 
    </div>
    <c:if test="${map=='1'}">
     <div class="easyui-tab" title="地图" style="padding: 1px; overflow: hidden;">
      <iframe name="myMap" id="myMap" scrolling="no" frameborder="0" src="mapController.do?map" style="width: 100%; height: 99.5%;"></iframe>
     </div>
    </c:if>
   </div>
  </div>
  <!-- 右侧 -->
  <div collapsed="true"  region="east" iconCls="icon-reload" title="辅助工具" split="true" style="width: 190px;">
   <div id="tabs"  class="easyui-tabs" border="false" style="height: 240px">
    <div title="日历" style="padding: 0px; overflow: hidden; color: red;">
     <div id="layout_east_calendar"></div>
    </div>
   </div>
   <div id="tabs" class="easyui-tabs" border="false">
    <div title="在线人员" style="padding: 20px; overflow: hidden; color: red;">
    </div>
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