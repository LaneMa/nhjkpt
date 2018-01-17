package nhjkpt.configmanage.controller.systemlink;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.framework.core.common.controller.BaseController;
import org.framework.core.common.hibernate.qbc.CriteriaQuery;
import org.framework.core.common.model.json.AjaxJson;
import org.framework.core.common.model.json.DataGrid;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.tag.core.easyui.TagUtil;
import nhjkpt.system.service.SystemService;
import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.systemlink.SystemLinkEntity;
import nhjkpt.configmanage.service.systemlink.SystemLinkServiceI;

/**   
 * @Title: Controller
 * @Description: 系统链接接口
 * @author qf
 * @date 2014-11-16 21:03:16
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/systemLinkController")
public class SystemLinkController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SystemLinkController.class);

	@Autowired
	private SystemLinkServiceI systemLinkService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 系统链接接口列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "systemLink")
	public ModelAndView systemLink(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/systemlink/systemLinkList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(SystemLinkEntity systemLink,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SystemLinkEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, systemLink);
		this.systemLinkService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除系统链接接口
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SystemLinkEntity systemLink, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		systemLink = systemService.getEntity(SystemLinkEntity.class, systemLink.getId());
		message = "删除成功";
		systemLinkService.delete(systemLink);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加系统链接接口
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SystemLinkEntity systemLink, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(systemLink.getId())) {
			message = "更新成功";
			SystemLinkEntity t = systemLinkService.get(SystemLinkEntity.class, systemLink.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(systemLink, t);
				systemLinkService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			systemLinkService.save(systemLink);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 系统链接接口列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SystemLinkEntity systemLink, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(systemLink.getId())) {
			systemLink = systemLinkService.getEntity(SystemLinkEntity.class, systemLink.getId());
			req.setAttribute("systemLinkPage", systemLink);
		}
		return new ModelAndView("nhjkpt/configmanage/systemlink/systemLink");
	}
}
