<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
 <head>
  <title>学校地图</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script language="javascript" src="plug-in/jsgraphics/wz_jsgraphics.js" type="text/javascript"></script>
<script language="javascript" src="plug-in/jquery-plugs/qtip/qtip.js" type="text/javascript"></script>
<link rel=stylesheet type=text/css href="plug-in/jquery-plugs/qtip/qtip.css"></link>
</head>
<body>
	<div id="map"></div>
</body>
</html>
<script type="text/javascript">
var jg;
$(document).ready(function() {
	jg= new jsGraphics("map");
	jg.drawImage("webpage/images/map.jpg",0,30,950,572);
	loadbuilding();
	if('${id}'){
		showtip('${id}');
	}else{
		if('${type}'==''){
			document.ondblclick=initialize;
		}
	}
});
var buildingTip;
function showtip(id){
	if(buildingTip){
		buildingTip.hide();
		buildingTip.destroy();
	}
	$.ajax({
		type : "POST",
		url : "buildinginfoController.do?queryBuildingMessage&id="+id,
		success : function(jsondata) {
			var data=eval('(' + jsondata + ')');
			buildingTip=$("#"+data.id).qtip({
				  content: {
				    text: "<a href='#'>"+data.message+"</a>",
				    title: {
				      text: data.name,
				      button: "关闭"
				    }
				  },
					show:{
						ready: true
					}
				}).qtip("api");
		}
	});
}
function openWindow(){
	window.open('buildinginfoController.do?buildingMap&id=${id}&type=open','建筑物','width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ ',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes');
}
function loadbuilding(){
	if('${type}'==''){
		jg.drawString("<input type='button' value='全屏' onclick='openWindow();'/>",10,10);
	}
	$.ajax({
		type : "POST",
		url : "buildinginfoController.do?queryBuildings",
		success : function(jsondata) {
			var buildings=eval(jsondata);
			var building;
			for(var i=0,l=buildings.length;i<l;i++){
				building=buildings[i];
				if($("#"+building.id)){
					$("#"+building.id).parent().remove(); 
				}
				jg.drawString("<div id='"+building.id+"' style='cursor:pointer' onclick='showBuilding(\""+building.id+"\");' onmouseover='showtip(\""+building.id+"\");'>&nbsp;&nbsp;&nbsp;</div>",building.x,building.y);
				jg.setColor("red");
				jg.setStroke(3);
				jg.drawEllipse(parseInt(building.x),parseInt(building.y), 10, 10);
			}
			jg.paint();
		}
	});
}
function loadbuildingMessage(){
	$.ajax({
		type : "POST",
		url : "buildinginfoController.do?queryBuildingsMessage",
		success : function(jsondata) {
			var data=eval(jsondata);
			var map;
			for(var i=0,l=data.length;i<l;i++){
				map=data[i];
				$("#"+map.id).qtip({
					  content: {
					    text: "<a href='#'>"+map.message+"</a>",
					    title: {
					      text: map.name,
					      button: "关闭"
					    }
					  }
					});
			}
		}
	});
}
function initialize(event){
	var e = event || window.event; 
	var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft; 
	var scrollY = document.documentElement.scrollTop || document.body.scrollTop; 
	var x = e.pageX || e.clientX + scrollX; 
	var y = e.pageY || e.clientY + scrollY;
	openwindow('建筑物坐标设置','buildinginfoController.do?buildingCoor&x='+x+"&y="+y,"",320,200);
}
function showBuilding(id){
	openwindow('建筑物能耗','buildinginfoController.do?buildinghome&id='+id,"",800,500);
}
function updateBuilding(){
	jg= new jsGraphics("map");
	jg.drawImage("webpage/images/map.jpg",0,30,950,572);
	loadbuilding();
}
</script>