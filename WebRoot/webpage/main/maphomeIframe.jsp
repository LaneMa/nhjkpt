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

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=8kg1r7mt6U48fudS2dwCu62B"></script>

</head>
<body style="overflow-y: hidden" scroll="no">
<div>
	&nbsp;&nbsp;&nbsp;&nbsp;显示：
	<input type="checkbox" id="eShow" value="1" checked onclick="loadOverlays();">配电室&nbsp;&nbsp;
	<input type="checkbox" id="wShow" value="1"   onclick="loadOverlays();">水表&nbsp;&nbsp;
	<input type="checkbox" id="hShow" value="1"   onclick="loadOverlays();">热表&nbsp;&nbsp;
</div>
<div class=" main">
	
	<div class="ditu" id="allmap" style=" height:708px;"></div>
	
</div>
</body>
</html>


<script type="text/javascript">









	// 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例

	//var btPoint=new BMap.Point(116.3259, 40.0285);   //北体经纬度
	//map.setCurrentCity("北京"); 
	var btPoint=new BMap.Point(119.042025, 33.555113);  //淮阴工学院经纬度
	map.setCurrentCity("淮安"); 
	
	
	map.centerAndZoom(btPoint, 17);  // 初始化地图,设置中心点坐标和地图级别
	map.disableDoubleClickZoom() ;
	
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	         // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放


function mapSetPoint(e){
		//alert(e.point.lng + ", " + e.point.lat);
		lng=e.point.lng;
		lat=e.point.lat;
		openwindow('经纬度设置','schoolinfoController.do?mapSetPoint&lng='+lng+'&lat='+lat,"",400,300);
	}
	map.addEventListener("dblclick", mapSetPoint);
	
	
	






loadOverlays();

function loadOverlays(){
	map.clearOverlays();
	
	
	
	if($("#eShow").attr("checked"))
	{
		
	
	
	
	//添加配电室点
	
	
	
	$.ajax({
		type : "POST",
		url : "schoolinfoController.do?queryRooms",
		success : function(jsondata) {
		
			var rooms=eval(jsondata);
			
			var room;
			for(var i=0,l=rooms.length;i<l;i++){
				room=rooms[i];
				var point = new BMap.Marker(new BMap.Point(room.lng,room.lat), {
				  // 指定Marker的icon属性为Symbol
				  icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
				    scale: 1,//图标缩放大小
				    fillColor: "blue",//填充颜色
				    fillOpacity: 0.8//填充透明度
				  })
				});
				
				
				point.addEventListener("click",getPoint);
				map.addOverlay(point);
				
				if(room.joinlng && room.joinlat && room.joinlng>0){
					var Polyline = new BMap.Polyline([
						new BMap.Point(room.lng,room.lat),
						new BMap.Point(room.joinlng,room.joinlat)
					], {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});   //创建折线
					
				
				    map.addOverlay(Polyline);
				}
				
				
				
			}
			
		}
	});
	
	}
	
	if($("#wShow").attr("checked"))
	{
	//添加水表
	$.ajax({
		type : "POST",
		url : "schoolinfoController.do?queryWaterMeters",
		success : function(jsondata) {
			var meters=eval(jsondata);
			var meter;
			for(var i=0,l=meters.length;i<l;i++){
				meter=meters[i];
				var point = new BMap.Marker(new BMap.Point(meter.lng,meter.lat), {
				  // 指定Marker的icon属性为Symbol
				  icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
				    scale: 1,//图标缩放大小
				    fillColor: "green",//填充颜色
				    fillOpacity: 0.8//填充透明度
				  })
				});
				
				
				point.addEventListener("click",getPoint);
				map.addOverlay(point);
				
				
				if(meter.joinlng && meter.joinlat && meter.joinlng>0){
					
					var Polyline = new BMap.Polyline([
						new BMap.Point(meter.lng,meter.lat),
						new BMap.Point(meter.joinlng,meter.joinlat)
					], {strokeColor:"green", strokeWeight:2, strokeOpacity:0.5});   //创建折线
					
				
				    map.addOverlay(Polyline);
				}
				
			}
			
		}
	});
	}
	
	if($("#hShow").attr("checked"))
	{
	
	//添加热表
	$.ajax({
		type : "POST",
		url : "schoolinfoController.do?queryHeatMeters",
		success : function(jsondata) {
			var meters=eval(jsondata);
			var meter;
			for(var i=0,l=meters.length;i<l;i++){
				meter=meters[i];
				var point = new BMap.Marker(new BMap.Point(meter.lng,meter.lat), {
				  // 指定Marker的icon属性为Symbol
				  icon: new BMap.Symbol(BMap_Symbol_SHAPE_POINT, {
				    scale: 1,//图标缩放大小
				    fillColor: "red",//填充颜色
				    fillOpacity: 0.8//填充透明度
				  })
				});
				
				
				point.addEventListener("click",getPoint);
				map.addOverlay(point);
				
				if(meter.joinlng && meter.joinlat && meter.joinlng>0){
					
					var Polyline = new BMap.Polyline([
						new BMap.Point(meter.lng,meter.lat),
						new BMap.Point(meter.joinlng,meter.joinlat)
					], {strokeColor:"red", strokeWeight:2, strokeOpacity:0.5});   //创建折线
					
				
				    map.addOverlay(Polyline);
				}
				
			}
			
		}
	});
	
	}
}


function getPoint(){

	var p = this.getPosition();       //获取marker的位置
		//alert("电监测点的位置是" + p.lng + "," + p.lat);   
		lng=p.lng;
		lat=p.lat;
		openwindow('经纬度设置','schoolinfoController.do?mapSetPoint&lng='+lng+'&lat='+lat,"",400,300);

}






</script>