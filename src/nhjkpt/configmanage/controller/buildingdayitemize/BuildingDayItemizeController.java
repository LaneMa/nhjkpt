package nhjkpt.configmanage.controller.buildingdayitemize;

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

import nhjkpt.configmanage.entity.buildingdayitemize.BuildingDayItemizeEntity;
import nhjkpt.configmanage.service.buildingdayitemize.BuildingDayItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 建筑用电分类分天统计表
 * @author qf
 * @date 2013-08-17 23:12:53
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/buildingDayItemizeController")
public class BuildingDayItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BuildingDayItemizeController.class);

	@Autowired
	private BuildingDayItemizeServiceI buildingDayItemizeService;
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
	 * 建筑用电分类分天统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "buildingDayItemize")
	public ModelAndView buildingDayItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/buildingdayitemize/buildingDayItemizeList");
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
	public void datagrid(BuildingDayItemizeEntity buildingDayItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BuildingDayItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, buildingDayItemize);
		this.buildingDayItemizeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除建筑用电分类分天统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BuildingDayItemizeEntity buildingDayItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		buildingDayItemize = systemService.getEntity(BuildingDayItemizeEntity.class, buildingDayItemize.getId());
		message = "删除成功";
		buildingDayItemizeService.delete(buildingDayItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加建筑用电分类分天统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BuildingDayItemizeEntity buildingDayItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(buildingDayItemize.getId())) {
			message = "更新成功";
			BuildingDayItemizeEntity t = buildingDayItemizeService.get(BuildingDayItemizeEntity.class, buildingDayItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(buildingDayItemize, t);
				buildingDayItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			buildingDayItemizeService.save(buildingDayItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 建筑用电分类分天统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BuildingDayItemizeEntity buildingDayItemize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(buildingDayItemize.getId())) {
			buildingDayItemize = buildingDayItemizeService.getEntity(BuildingDayItemizeEntity.class, buildingDayItemize.getId());
			req.setAttribute("buildingDayItemizePage", buildingDayItemize);
		}
		return new ModelAndView("nhjkpt/configmanage/buildingdayitemize/buildingDayItemize");
	}
}
