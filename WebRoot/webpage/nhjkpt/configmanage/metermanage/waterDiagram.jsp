<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<html>
 <head>
  <title>电路图</title>
   <script language="javascript" src="plug-in/jsgraphics/wz_jsgraphics.js" type="text/javascript"></script>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
   </head>
   <body>
 	<div id="meter"></div>
 	</body>
</html>
  
  <script type="text/javascript">
  var addx=200;
  var addy=50;
  var harf=12;
  var jg= new jsGraphics("meter");
	$(document).ready(function() {
	  $.ajax({
			type : "POST",
			url : "meterController.do?queryWaterMeters&roomid=${roomid}",
			success : function(jsondata) {
				var dx=20;
				var dy=20;
				jg.drawLine(dx+harf,dy,dx+harf,dy+addx);
				jg.drawImage("plug-in/jsgraphics/images/water/All_U.png", dx,dy+100,25,100); 
				jg.setColor("#55bb55");
				jg.paint();
				var data=eval(jsondata);
				var oneLevel=true;
				for(var i=0;i<data.length;i++){
					if(data[i].level>0){
						oneLevel=false;
						break;
					}
				}
				
				if(oneLevel){
					drawcirLevel(data,'-1',dx,dy);
				}else{
					drawcirfirst(data,'-1',dx,dy);
				}
			}
	  });
	});
	function drawcirfirst(data,parentid,x,y){
		var dy=y;
		var ox=x;
		var ax=x;
		for(var i=0;i<data.length;i++){
			if(data[i].parentid==parentid){
				if(parentid=="-1"){
					jg.drawLine(ox+harf,dy, ax+addx/2+harf, dy);
				}
				jg.drawLine(ax+addx/2,dy,ax+addx/2,dy+addy+15);
				jg.drawImage("plug-in/jsgraphics/images/water/All_R.png", ax+addx/2,dy+addy-harf,200,50);
				jg.drawImage("plug-in/jsgraphics/images/water/Ground_R.png", ax+addx/2+200,dy+addy-harf,120,30); 
				if(data[i].alarm=="0"){
					jg.drawString("<div style='color:yellow;cursor:pointer' onclick='showdata(\""+data[i].id+"\");'>"+data[i].meterid+"</div>",ax+addx/2+70+85,dy+addy-12);
				}else if(data[i].alarm=="1"){
					jg.drawString("<div style='color:red;cursor:pointer' onclick='showdata(\""+data[i].id+"\");'>"+data[i].meterid+"</div>",ax+addx/2+70+85,dy+addy-12);
				}else{
					jg.drawString("<div onclick='showdata(\""+data[i].id+"\");'>"+data[i].meterid+"</div>",ax+addx/2+70+85,dy+addy-12);
				}
				jg.drawString(data[i].metertext,ax+addx/2+110+100,dy+addy-14);
				var meterId=data[i].meterid;
				
				if(meterId && meterId.indexOf("9")==0 )
				{
					jg.drawString(""+data[i].daysum+"吨",ax+addx/2+105+100,dy+addy+5);
				}else{
				
					jg.drawString(""+data[i].daysum+"kwh",ax+addx/2+105+100,dy+addy+5);
				}
				
				jg.setColor("#55bb55");
				jg.paint();
				ox=ax;
				ax=drawcir(data,data[i].id,ax+addx/2,dy);
				if(ax==0){
					ax+=addx;//特殊情况，只有第一级节点
				}
				ax+=addx/2+150;
			}
		}
	}
	function drawcir(data,parentid,x,y){
		var dx=x;
		var dy=y;
		var ax=x;
		var level=-1;
		for(var i=0;i<data.length;i++){
			if(data[i].parentid==parentid){
				if(level!=-1){
					jg.drawLine(dx+addx+120,dy,dx+addx+120,dy+addy);
				}
				jg.drawImage("plug-in/jsgraphics/images/water/All_R.png", dx+addx+120,dy+addy-harf,200,50);
				jg.drawImage("plug-in/jsgraphics/images/water/Ground_R.png", dx+addx+200+120,dy+addy-harf,120,30); 
				if(data[i].alarm=="0"){
					jg.drawString("<div style='color:yellow;cursor:pointer' onclick='showdata(\""+data[i].id+"\");'>"+data[i].meterid+"</div>",dx+addx+70+100+108,dy+addy-12);
				}else if(data[i].alarm=="1"){
					jg.drawString("<div style='color:red;cursor:pointer' onclick='showdata(\""+data[i].id+"\");'>"+data[i].meterid+"</div>",dx+addx+70+100+108,dy+addy-12);
				}else{
					jg.drawString("<div onclick='showdata(\""+data[i].id+"\");'>"+data[i].meterid+"</div>",dx+addx+70+100+108,dy+addy-12);
				}
				jg.drawString(data[i].metertext,dx+addx+110+100+120,dy+addy-14);
				
				
				var meterId=data[i].meterid;
				
				if(meterId && meterId.indexOf("9")==0 )
				{
					jg.drawString(""+data[i].daysum+"吨",dx+addx+105+100+120,dy+addy+5);
				}else{
				
					jg.drawString(""+data[i].daysum+"kwh",dx+addx+105+100+120,dy+addy+5);
				}
				
				
				jg.setColor("#55bb55");
				jg.paint();
				var ax1=drawcir(data,data[i].id,dx,dy+50);
				if(level!=-1){
					if(level!=data[i].level){
						dx+=addx;
					}
				}
				if(ax1>ax){
					ax=ax1;
				}else{
					//首节点增加了半个addx
					ax=dx+addx+addx/2;
				}
				dy+=50;
				level=data[i].level;
			}else{
				//ax=x+addx/2;
			}
		}
		return ax;
	}
	function drawcirLevel(data,parentid,x,y){
		var dy=y;
		var ox=x;
		var ax=x;
		for(var i=0;i<data.length;i++){
			if(data[i].parentid==parentid){
				if(parentid=="-1"){
					if(dy==y){
						jg.drawLine(ox+harf,dy, ax+addx/2+harf, dy);
					}else{
						jg.drawLine(ax+addx/2,dy-addy/2-30, ax+addx/2, dy);
					}
				}
				jg.drawLine(ax+addx/2,dy,ax+addx/2,dy+addy);
				jg.drawImage("plug-in/jsgraphics/images/water/All_R.png", ax+addx/2,dy+addy-harf,200,50);
				jg.drawImage("plug-in/jsgraphics/images/water/Ground_R.png", ax+addx/2+200,dy+addy-harf,120,30); 
				if(data[i].alarm=="0"){
					jg.drawString("<div style='color:yellow;cursor:pointer' onclick='showdata(\""+data[i].id+"\");'>"+data[i].meterid+"</div>",ax+addx/2+70+88,dy+addy-12);
				}else if(data[i].alarm=="1"){
					jg.drawString("<div style='color:red;cursor:pointer' onclick='showdata(\""+data[i].id+"\");'>"+data[i].meterid+"</div>",ax+addx/2+70+88,dy+addy-12);
				}else{
					jg.drawString("<div onclick='showdata(\""+data[i].id+"\");'>"+data[i].meterid+"</div>",ax+addx/2+70+88,dy+addy-12);
				}
				jg.drawString(data[i].metertext,ax+addx/2+110+100,dy+addy-14);
				var meterId=data[i].meterid;
				
				if(meterId && meterId.indexOf("9")==0 )
				{
					jg.drawString(""+data[i].daysum+"吨",ax+addx/2+105+100,dy+addy+5);
				}else{
				
					jg.drawString(""+data[i].daysum+"kwh",ax+addx/2+105+100,dy+addy+5);
				}
				
				jg.setColor("#55bb55");
				jg.paint();
				ox=ax;
				if(dy==0){
					dy+=addy;//特殊情况，只有第一级节点
				}
				dy+=addy/2+30;
			}
		}
	}
	
	function showdata(id){
		openwindow('瞬时信息','alarminfoController.do?openAlarm&id='+id,"",1200,600);
	}
  </script>
