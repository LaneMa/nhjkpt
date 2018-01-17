<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<link rel="shortcut icon" href="resources/fc/images/icon/favicon.ico">
<!--[if lt IE 9]>
   <script src="plug-in/login/js/html5.js"></script>
  <![endif]-->
<!--[if lt IE 7]>
  <script src="plug-in/login/js/iepng.js" type="text/javascript"></script>
  <script type="text/javascript">
	EvPNG.fix('div, ul, img, li, input'); //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
</script>
  <![endif]-->
<link href="plug-in/login/css/zice.style.css" rel="stylesheet"
	type="text/css" />
<link href="plug-in/login/css/buttons.css" rel="stylesheet"
	type="text/css" />
<link href="plug-in/login/css/icon.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="plug-in/login/css/tipsy.css" media="all" />
<style type="text/css">
html {
	background-image: none;
}

label.iPhoneCheckLabelOn span {
	padding-left: 0px
}

#versionBar {
	background-color: #212121;
	position: fixed;
	width: 100%;
	height: 35px;
	bottom: 0;
	left: 0;
	text-align: center;
	line-height: 35px;
	z-index: 11;
	-webkit-box-shadow: black 0px 10px 10px -10px inset;
	-moz-box-shadow: black 0px 10px 10px -10px inset;
	box-shadow: black 0px 10px 10px -10px inset;
}

.copyright {
	text-align: center;
	font-size: 10px;
	color: #CCC;
}

.copyright a {
	color: #A31F1A;
	text-decoration: none
}

.on_off_checkbox {
	width: 0px;
}

#login .logo {
	width: 500px;
	height: 51px;
}
</style>
</head>
<body style="background-image: url('');">
	<div id="alertMessage"></div>
	<div id="successLogin"></div>
	<div class="text_success">
		<img src="plug-in/login/images/loader_green.gif" alt="Please wait" />
		<span>登陆成功!请稍后....</span>
	</div>
	<div>
	<form name="formLogin" id="formLogin" action="loginController.do?login" check="loginController.do?checkuser" method="post">
      <input name="userKey" type="hidden" id="userKey" value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900"/>
		<TABLE border=0 cellSpacing=0 cellPadding=0 width=1002 align=center>
			<TBODY>
				<TR>
					<TD width=1000 height=228
						background=webpage/login/login_files/login_logo.gif align="center"><span
						style="font-size: 30pt; color: blue; font-weight: bold; font-family: '微软雅黑';"> ${schoolinfo.schoolname }能耗管理分析平台</span></TD>
					<TD></TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE border=0 cellSpacing=0 cellPadding=0 width=1002 align=center>
			<TBODY>
				<TR>
					<TD width=543 background="webpage/login/login_files/login_tree.gif"></TD>
					<TD vAlign=top width=459 class=login_dl
										background=webpage/login/login_files/login_login.gif >
						<TABLE border=0 cellSpacing=0 cellPadding=0 width=459 height=221>
							<TBODY>
								<TR>
									<TD width=318>
										<TABLE border=0 cellSpacing=0 cellPadding=2 width="100%" >
											<TBODY>
												<tr>
													<td colspan="2" height="40"></td>
												</tr>
												<TR>
													<TD height=40 align="right">用户名：</TD>
													<TD align=left>
														<div class="tip">
													       <input class="userName" name="userName" style="height: 20px;width: 130px" type="text" id="userName" title="用户名" iscookie="true" value=""  nullmsg="请输入用户名!"/>
													      </div>
													</TD>
												</TR>
												<TR>
													<TD height=40 align="right">密&nbsp;&nbsp;&nbsp;&nbsp;码：</TD>
													<TD align=left>
													<div class="tip">
												       <input class="password" name="password" style="height: 20px;width: 130px" type="password" id="password" title="密码" iscookie="true" value="" nullmsg="请输入密码!"/>
												      </div>
													</TD>
												</TR>
												<tr>
													<td height="30" align="right"><input type="checkbox" id="on_off" name="remember" checked="ture" value="0" /></td>
													<td><span class="f_help">记住用户名</span></td>
												</tr>
												<TR>
													<TD style="COLOR: red" colSpan=2></TD>
												</TR>
											</TBODY>
										</TABLE>
										<TABLE border=0 cellSpacing=0 cellPadding=12 width="100%">
											<TBODY>
												<TR>
													<TD width="60%">
														<A href="#" id="forgetpass"><IMG border=0 src="webpage/login/login_files/login_cz.gif" width=53 height=27></A>
													</TD>
													<TD width="40%">
														<A href="#" id="but_login"><IMG border=0 src="webpage/login/login_files/login_dl.gif" width=64 height=27></A>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
									<TD background="webpage/login/login_files/login_con.gif"
										width=141 height=221></TD>
								</TR>
							</TBODY>
						</TABLE>
						<TABLE border=0 cellSpacing=0 cellPadding=0 width=459 height=163>
							<TBODY>
								<TR>
									<TD background=webpage/login/login_files/login_white.gif>
										<TABLE class=login_wz border=0 cellSpacing=0 cellPadding=0
											width="100%">
											<TBODY>
												<TR>
													<TD width="50%">&nbsp;</TD>
													<TD width="56%">&nbsp;</TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>
	</div>
	<!-- Link JScript-->
	<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
	<script type="text/javascript" src="plug-in/login/js/jquery-jrumble.js"></script>
	<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
	<script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
	<script type="text/javascript" src="plug-in/login/js/login.js"></script>
</body>
</html>