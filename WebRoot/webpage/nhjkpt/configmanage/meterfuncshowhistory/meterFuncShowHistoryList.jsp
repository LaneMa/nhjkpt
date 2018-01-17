<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addMeterFuncShowHistoryBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delMeterFuncShowHistoryBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addMeterFuncShowHistoryBtn').bind('click', function(){   
 		 var tr =  $("#add_meterFuncShowHistory_table_template tr").clone();
	 	 $("#add_meterFuncShowHistory_table").append(tr);
	 	 resetTrNum('add_meterFuncShowHistory_table');
    });  
	$('#delMeterFuncShowHistoryBtn').bind('click', function(){   
      	$("#add_meterFuncShowHistory_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_meterFuncShowHistory_table'); 
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addMeterFuncShowHistoryBtn" href="#">添加</a> <a id="delMeterFuncShowHistoryBtn" href="#">删除</a> 
</div>
<div style="width: auto;height: 135px;overflow-y:auto;overflow-x:scroll;">
<table border="0" cellpadding="2" cellspacing="0" id="meterFuncShowHistory_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
				  <td align="left" bgcolor="#EEEEEE">funcid</td>
				  <td align="left" bgcolor="#EEEEEE">showdat</td>
	</tr>
	<tbody id="add_meterFuncShowHistory_table">	
	<c:if test="${fn:length(meterFuncShowHistoryList)  <= 0 }">
			<tr>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
				  <td align="left"><input name="meterFuncShowHistoryList[0].funcid" maxlength="32" type="text" style="width:120px;" ></td>
				  <td align="left"><input name="meterFuncShowHistoryList[0].showdata" maxlength="" type="text" style="width:120px;" ></td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(meterFuncShowHistoryList)  > 0 }">
		<c:forEach items="${meterFuncShowHistoryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
				   <td align="left"><input name="meterFuncShowHistoryList[${stuts.index }].funcid" maxlength="32" value="${poVal.funcid }" type="text" style="width:120px;"></td>
				   <td align="left"><input name="meterFuncShowHistoryList[${stuts.index }].showdata" maxlength="" value="${poVal.showdata }" type="text" style="width:120px;"></td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
</div>