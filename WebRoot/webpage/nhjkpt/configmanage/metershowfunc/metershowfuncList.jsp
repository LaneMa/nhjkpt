



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="metershowfuncList" title="电表需要展示的功能" actionUrl="metershowfuncController.do?datagrid" idField="id" fit="true" queryMode="group" onClickCell="clickCell" >
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="表具" field="meterid"> </t:dgCol>
   <t:dgCol title="功能号" field="funcid"></t:dgCol>
   <t:dgCol title="上限" field="top" editor="{type:'numberbox',options:{precision:2}}"  ></t:dgCol>
   <t:dgCol title="下限" field="floor" editor="{type:'numberbox',options:{precision:2}}" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"  ></t:dgCol>
   <t:dgDelOpt title="删除" url="metershowfuncController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="metershowfuncController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="metershowfuncController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="metershowfuncController.do?addorupdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="批量保存上下限" icon="icon-edit" url="metershowfuncController.do?saveBatch" funname="saveBatch"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
<script type="text/javascript">
        $.extend($.fn.datagrid.methods, {
            editCell: function(jq,param){
                return jq.each(function(){
                    var opts = $(this).datagrid('options');
                    var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
                    for(var i=0; i<fields.length; i++){
                        var col = $(this).datagrid('getColumnOption', fields[i]);
                        col.editor1 = col.editor;
                        if (fields[i] != param.field){
                            col.editor = null;
                        }
                    }
                    $(this).datagrid('beginEdit', param.index);
                    for(var i=0; i<fields.length; i++){
                        var col = $(this).datagrid('getColumnOption', fields[i]);
                        col.editor = col.editor1;
                    }
                });
            }
        });
        
        var editIndex = undefined;
        function endEditing(){
            if (editIndex == undefined){return true}
            if ($('#metershowfuncList').datagrid('validateRow', editIndex)){
                $('#metershowfuncList').datagrid('endEdit', editIndex);
                editIndex = undefined;
                return true;
            } else {
                return false;
            }
        }
        function clickCell(index, field){
            if (endEditing()){
                $('#metershowfuncList').datagrid('selectRow', index)
                        .datagrid('editCell', {index:index,field:field});
                editIndex = index;
            }
        }
        
        
        function saveBatch(){
        	var rows = $('#metershowfuncList').datagrid('getChanges'); 
        	var saveDataAry=[];  
        	
        	
        	for(var i=0;i<rows.length;i++){
        		var data={"id":rows[i].id,"meterid":rows[i].meterid,"funcid":rows[i].funcid,"top":(parseFloat(rows[i].top)).toFixed(2) ,"floor":(parseFloat(rows[i].floor)).toFixed(2)};  
        		saveDataAry.push(data);  
        	} 
        	        
        	var jsondata=JSON.stringify(saveDataAry);
        	
        	//alert(jsondata);
        	
        	
        	
        	
        	
        	
        	
        	$.ajax({
			type : 'POST',
			contentType:'application/json;charset=UTF-8', 
			headers : {  'Content-Type' : 'application/json;charset=utf-8'  } ,
			dataType: 'json',  
			data : jsondata ,
			url : 'metershowfuncController.do?saveBatch',// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				
						tip("操作成功");
						reloadTable();
					
				}
			});
			
			
			
			
			
        	
        	
        	
        }
    </script>
 
 
 

































