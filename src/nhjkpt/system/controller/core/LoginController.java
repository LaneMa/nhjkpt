package nhjkpt.system.controller.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nhjkpt.configmanage.entity.alarminfo.AlarminfoEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.schoolinfo.SchoolinfoEntity;
import nhjkpt.configmanage.entity.systemlink.SystemLinkEntity;
import nhjkpt.system.pojo.base.TSConfig;
import nhjkpt.system.pojo.base.TSFunction;
import nhjkpt.system.pojo.base.TSRole;
import nhjkpt.system.pojo.base.TSRoleFunction;
import nhjkpt.system.pojo.base.TSRoleUser;
import nhjkpt.system.pojo.base.TSUser;
import nhjkpt.system.pojo.base.TSVersion;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.service.UserService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.common.hibernate.qbc.HqlQuery;
import org.framework.core.common.hibernate.qbc.PageList;
import org.framework.core.common.model.common.SessionInfo;
import org.framework.core.common.model.json.AjaxJson;
import org.framework.core.constant.Globals;
import org.framework.core.extend.datasource.DataSourceContextHolder;
import org.framework.core.extend.datasource.DataSourceType;
import org.framework.core.util.ContextHolderUtils;
import org.framework.core.util.ListtoMenu;
import org.framework.core.util.NumberComparator;
import org.framework.core.util.ResourceUtil;
import org.framework.core.util.SignVerify;
import org.framework.core.util.oConvertUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import freemarker.template.utility.StringUtil;


/**
 * 登陆初始化控制器
 * 
 * 
 */
@Controller
@RequestMapping("/loginController")
public class LoginController {
	private SystemService systemService;
	private UserService userService;
	private String message = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {

		this.userService = userService;
	}
	
	
	@RequestMapping(params = "goPwdInit")
	public String goPwdInit() {
		return "login/pwd_init";
	}
	
	/**
	 * admin账户密码初始化
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "pwdInit")
	public ModelAndView pwdInit(HttpServletRequest request) {
		ModelAndView modelAndView = null;
		TSUser user = new TSUser();
		user.setUserName("admin");
		String newPwd = "123456";
		userService.pwdInit(user,newPwd);
		modelAndView = new ModelAndView(new RedirectView("loginController.do?login"));
		return modelAndView;
	}
	

	/**
	 * 检查用户名称
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(TSUser user, HttpServletRequest req) {
		HttpSession session = ContextHolderUtils.getSession();
		DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_nhjkpt);
		AjaxJson j = new AjaxJson();
		TSUser u = userService.checkUserExits(user);
		if (u != null) {
			//update-begin----author:zhangdaihao ------- date:20130318-------for:注释掉U盾的校验
			//if (user.getUserKey().equals(u.getUserKey())) {
			if (true) {
			//update-begin----author:zhangdaihao ------- date:20130318-------for:注释掉U盾的校验
				message = "用户: " + user.getUserName() + "登录成功";
				SessionInfo sessionInfo = new SessionInfo();
				sessionInfo.setUser(u);
				session.setMaxInactiveInterval(60 * 30);
				session.setAttribute(Globals.USER_SESSION, sessionInfo);
				// 添加登陆日志
				systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);

			} else {
				j.setMsg("请检查U盾是否正确");
				j.setSuccess(false);
			}
		} else {
			j.setMsg("用户名或密码错误!");
			j.setSuccess(false);
		}
		return j;
	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "login")
	public String login(HttpServletRequest request) {
		String path=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		List<SchoolinfoEntity> list=systemService.getList(SchoolinfoEntity.class);
		if(list!=null&&list.size()!=0){
			request.setAttribute("schoolinfo", list.get(0));
		}
		if(true){
			DataSourceContextHolder.setDataSourceType(DataSourceType.dataSource_nhjkpt);
			TSUser user = ResourceUtil.getSessionUserName();
			String roles = "";
			if (user != null) {
				List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
				String url="main/schoolmain";
				for (TSRoleUser ru : rUsers) {
					TSRole role = ru.getTSRole();
					if(role.getRoleCode().equals(CommonUtil.ADMIN)){
						url="main/main";
					}
					roles += role.getRoleName() + ",";
				}
				request.setAttribute("roleName", roles);
				request.setAttribute("userName", user.getRealName());
				
				String hql=" from AlarminfoEntity  ";
				Query query=systemService.getSession().createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(30);
				List<AlarminfoEntity> alarms=query.list();
				request.setAttribute("systemlink", systemService.getList(SystemLinkEntity.class));
				List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
				for(AlarminfoEntity alarm:alarms){
					for(MeterEntity meter:meterList){
						if(CommonUtil.NEWID){
							if(meter.getId().equals(alarm.getMeterid())){
								//添加表具id
								alarm.setInfo("("+meter.getMeterid()+")"+alarm.getInfo());
							}
						}else{
							if(meter.getMeterid().equals(alarm.getMeterid())){
								//添加表具id
								alarm.setInfo("("+meter.getMeterid()+")"+alarm.getInfo());
							}
						}
					}
				}
				request.setAttribute("alarms", alarms);
				
				//获取开关数据
				request.setAttribute("waterSwitch", ResourceUtil.getConfigByName("waterSwitch"));
				request.setAttribute("gasSwitch", ResourceUtil.getConfigByName("gasSwitch"));
				request.setAttribute("heatSwitch", ResourceUtil.getConfigByName("heatSwitch"));
				request.setAttribute("ssydSwitch", ResourceUtil.getConfigByName("ssydSwitch"));
				request.setAttribute("yplxjSwitch", ResourceUtil.getConfigByName("yplxjSwitch"));
				request.setAttribute("jszmSwitch", ResourceUtil.getConfigByName("jszmSwitch"));
				
				
				
				
				
				return url;
			} else {
				return "login/schoollogin";
			}
		}else{
			return "login/schoollogin";
		}

	}

	/**
	 * 退出系统
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView modelAndView = null;

		HttpSession session = ContextHolderUtils.getSession();
		String versionCode = oConvertUtils.getString(request.getParameter("versionCode"));
		TSUser user= ResourceUtil.getSessionUserName();
		// 根据版本编码获取当前软件版本信息
		TSVersion version = systemService.findUniqueByProperty(TSVersion.class, "versionCode", versionCode);
		
		//update-begin--Author:chenxu  Date:20130322 for：左侧菜单信息放入到session中
		List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		modelAndView = new ModelAndView(new RedirectView("schoolinfoController.do?schoollogin"));
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			if(role.getRoleCode().equals(CommonUtil.ADMIN)){
				modelAndView = new ModelAndView(new RedirectView("loginController.do?login"));
			}
			session.removeAttribute(role.getId());
		}
		//update-end--Author:chenxu  Date:20130322 for：左侧菜单信息放入到session中
		
		// 判断用户是否为空不为空则清空session中的用户object
		session.removeAttribute(Globals.USER_SESSION);// 注销该操作用户
		systemService.addLog("用户" + user.getUserName() + "已退出", Globals.Log_Type_EXIT, Globals.Log_Leavel_INFO);
		return modelAndView;
	}
	/**
	 * 菜单跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "left")
	public ModelAndView left(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		String roles = "";
		HttpSession session = ContextHolderUtils.getSession();
		// 登陆者的权限
		//update-begin--Author:tanghong  Date:20130531 for：[140]左侧菜单报错
		if(user.getId()==null){
			session.removeAttribute(Globals.USER_SESSION);
			return new ModelAndView(new RedirectView("loginController.do?login"));
		}
		//update-end--Author:tanghong  Date:20130531 for：[140]左侧菜单报错
		Set<TSFunction> loginActionlist = new HashSet<TSFunction>();// 已有权限菜单
		//update-begin--Author:chenxu  Date:20130322 for：左侧菜单信息放入到session中
		List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			roles += role.getRoleName() + ",";
			List<TSRoleFunction> roleFunctionList = ResourceUtil.getSessionTSRoleFunction(role.getId());
			if (roleFunctionList == null) {
				session.setMaxInactiveInterval(60 * 30);
				roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
				session.setAttribute(role.getId(), roleFunctionList);
			//update-begin--Author:tanghong  Date:20130531 for：[140]左侧菜单报错
			}else{
				if(roleFunctionList.get(0).getId()==null){
					roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
				}
			//update-end--Author:tanghong  Date:20130531 for：[140]左侧菜单报错
			}
			for (TSRoleFunction roleFunction : roleFunctionList) {
				TSFunction function = roleFunction.getTSFunction();
				loginActionlist.add(function);
			}
		}
		//update-end--Author:chenxu  Date:20130322 for：左侧菜单信息放入到session中
		
			List<TSFunction> bigActionlist = new ArrayList<TSFunction>();// 一级权限菜单
			List<TSFunction> smailActionlist = new ArrayList<TSFunction>();// 二级权限菜单
			if (loginActionlist.size() > 0) {
				for (TSFunction function : loginActionlist) {
					if (function.getFunctionLevel() == 0) {
						bigActionlist.add(function);
					} else if (function.getFunctionLevel() == 1) {
						smailActionlist.add(function);
					}
				}
			}
			// 菜单栏排序
			Collections.sort(bigActionlist, new NumberComparator());
			Collections.sort(smailActionlist, new NumberComparator());
			String logString = ListtoMenu.getEasyuiMenu(bigActionlist, smailActionlist);
			request.setAttribute("loginMenu", logString);
			request.setAttribute("parentFun", bigActionlist);
			request.setAttribute("roleName", roles);
			request.setAttribute("userName", user.getRealName());
			request.setAttribute("childFun", smailActionlist);
			request.setAttribute("userName", user.getRealName());
			List<TSConfig> configs = userService.loadAll(TSConfig.class);
			for (TSConfig tsConfig : configs) {

				request.setAttribute(tsConfig.getCode(), tsConfig.getContents());
			}
			return new ModelAndView("main/left");
	}
	/**
	 * 菜单跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schoolleft")
	public ModelAndView schoolleft(String type,HttpServletRequest request) {
		
		return new ModelAndView("main/schoolleft");
	}
	/**
	 * 首页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request) {
			return new ModelAndView("main/home");
	}

	//update-start--Author:邢双阳  Date:20130525 for[105]：菜单权限控制
	/**
	 * 无权限页面提示跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "noAuth")
	public ModelAndView noAuth(HttpServletRequest request) {
			return new ModelAndView("common/noAuth");
	}
	//update-end--Author:邢双阳  Date:20130525 for[105]：菜单权限控制
}
