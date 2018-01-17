<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link rel="stylesheet" href="plug-in/accordion/css/icons.css" type="text/css"></link>
<link rel="stylesheet" href="plug-in/accordion/css/accordion.css" type="text/css"></link>

<script type="text/javascript" src="plug-in/accordion/js/leftmenu.js"></script>
<ul id="basetree" class="easyui-tree"></ul>
<script type="text/javascript">
<!--
function loadtree(url,taburl,menu){
	if(menu){
		var divnow=$("a.hover");
		divnow.removeClass("hover");
		$("#"+menu).addClass("hover");
	}
	$('#basetree').tree({
		url:url,
		
		onClick: function(node) {
			addOnlyOneTab(node.text,taburl+'&id='+node.id,'');
			document.ondblclick=null;
        },
        
	});
}


function loadtree_home(url,taburl,menu){
	if(menu){
		var divnow=$("a.hover");
		divnow.removeClass("hover");
		$("#"+menu).addClass("hover");
	}
	$('#basetree').tree({
		url:url,
		
		
		onClick: function(node) {
			
			var isRoot =  $('#basetree').tree('getChildren', node.target);
			if(isRoot==''){
				//addOnlyOneTab(node.text,taburl+'&id='+node.id,'');
				document.ondblclick=null;
			}else {
			}
			

			
        },
        
	});
}



function loadtree_building(url,taburl,menu){
	if(menu){
		var divnow=$("a.hover");
		divnow.removeClass("hover");
		$("#"+menu).addClass("hover");
	}
	$('#basetree').tree({
		url:url,
		onClick: function(node) {
			
			openWindow_building(node.id);
        },
        
	});
}


function openWindow_building(buildid){
	addmask();
	openwindow('建筑物能耗','buildinginfoController.do?buildinghome&id='+buildid,"",800,500);
}


function loadSsyd(url,taburl,menu){
	$.ajax({
		type : "POST",
		url : url,
		success : function(jsondata) {
			var data=eval(jsondata);
			addOnlyOneTab(data[0].text,taburl+'&id='+data[0].id,'');
		}
	});
	loadtree(url,taburl,menu);
}



function loadRoom(url,taburl,menu){
	$.ajax({
		type : "POST",
		url : url,
		success : function(jsondata) {
			var data=eval(jsondata);
			addOnlyOneTab(data[0].text,taburl+'&id='+data[0].id,'');
		}
	});
	loadtree(url,taburl,menu);
}
function loadHome(url,taburl,menu){
	loadtree_home(url,taburl,menu);
	addOnlyOneTab("学校","schoolinfoController.do?home");
}
function loadBuilding(url,taburl,menu){
	loadtree_building(url,taburl,menu);
	addOnlyOneTab("建筑物",taburl);
}
function loadReport(taburl,menu){
	if(menu){
		var divnow=$("a.hover");
		divnow.removeClass("hover");
		$("#"+menu).addClass("hover");
	}
	$("#basetree").text("报表管理");
	addOnlyOneTab("报表管理",taburl);
	document.ondblclick=null;
}
$(document).ready(function () {
	loadtree('itemizeController.do?treeItemize','itemizeController.do?schoolitemize');
});



function loadJszm(url,taburl,menu){
	
	
	if(menu){
		var divnow=$("a.hover");
		divnow.removeClass("hover");
		$("#"+menu).addClass("hover");
	}
	$('#basetree').tree({
		url:url,
		
		
		onClick: function(node) {
			
			var level=node.attributes.level;
			if(level==2){
			
				addOnlyOneTab(node.text,taburl+'&id='+node.id,'');
			}
			

			
        },
        
	});
	
	
}


//-->
</script>
