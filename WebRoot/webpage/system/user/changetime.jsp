<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>修改系统时间</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" refresh="false" dialog="true" action="userController.do?savetime" usePlugin="password" layout="table">
   
   <table style="width: 550px" cellpadding="0" cellspacing="1" class="formtable">
    <tbody>
     <tr>
      <td align="right" width="30%">
       <span class="filedzt">设置系统时间:</span>
      </td>
      <td class="value">
       
       2015-06-28&nbsp;&nbsp;12:00:00
      </td>
     </tr>
    
    </tbody>
   </table>
  </t:formvalid>
 </body>