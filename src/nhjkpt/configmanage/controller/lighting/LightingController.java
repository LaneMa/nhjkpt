package nhjkpt.configmanage.controller.lighting;

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

import nhjkpt.configmanage.entity.lighting.LightingEntity;
import nhjkpt.configmanage.service.lighting.LightingServiceI;

/**   
 * @Title: Controller
 * @Description: 教室照明
 * @author qf
 * @date 2014-11-17 22:28:39
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/lightingController")
public class LightingController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LightingController.class);

	@Autowired
	private LightingServiceI lightingService;
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
	 * 教室照明列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "lighting")
	public ModelAndView lighting(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/lighting/lightingList");
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
	public void datagrid(LightingEntity lighting,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(LightingEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, lighting);
		this.lightingService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除教室照明
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(LightingEntity lighting, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		lighting = systemService.getEntity(LightingEntity.class, lighting.getId());
		message = "删除成功";
		lightingService.delete(lighting);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加教室照明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(LightingEntity lighting, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(lighting.getId())) {
			message = "更新成功";
			LightingEntity t = lightingService.get(LightingEntity.class, lighting.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(lighting, t);
				lightingService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			lightingService.save(lighting);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 教室照明列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(LightingEntity lighting, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(lighting.getId())) {
			lighting = lightingService.getEntity(LightingEntity.class, lighting.getId());
			req.setAttribute("lightingPage", lighting);
		}
		return new ModelAndView("nhjkpt/configmanage/lighting/lighting");
	}
}
