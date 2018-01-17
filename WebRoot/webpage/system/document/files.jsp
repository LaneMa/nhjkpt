<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>文件列表</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="div" dialog="true">
   <fieldset class="step">
   <div class="form">
   	<a href="systemController.do?downFile">下载配置文件</a>
   </div>
    <div class="form">
     <t:upload name="fiels" buttonText="上传文件" uploader="systemController.do?saveFiles" extend="*.xls" id="file_upload" formData="documentTitle" auto="true"></t:upload>
    </div>
    <div class="form" id="filediv" style="height:50px">
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>
