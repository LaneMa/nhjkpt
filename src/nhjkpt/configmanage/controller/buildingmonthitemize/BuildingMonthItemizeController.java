package nhjkpt.configmanage.controller.buildingmonthitemize;

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

import nhjkpt.configmanage.entity.buildingmonthitemize.BuildingMonthItemizeEntity;
import nhjkpt.configmanage.service.buildingmonthitemize.BuildingMonthItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 大楼用电分类分月统计表
 * @author qf
 * @date 2013-08-02 01:11:37
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/buildingMonthItemizeController")
public class BuildingMonthItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BuildingMonthItemizeController.class);

	@Autowired
	private BuildingMonthItemizeServiceI buildingMonthItemizeService;
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
	 * 大楼用电分类分月统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "buildingMonthItemize")
	public ModelAndView buildingMonthItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/buildingmonthitemize/buildingMonthItemizeList");
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
	public void datagrid(BuildingMonthItemizeEntity buildingMonthItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BuildingMonthItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, buildingMonthItemize);
		this.buildingMonthItemizeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除大楼用电分类分月统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BuildingMonthItemizeEntity buildingMonthItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		buildingMonthItemize = systemService.getEntity(BuildingMonthItemizeEntity.class, buildingMonthItemize.getId());
		message = "删除成功";
		buildingMonthItemizeService.delete(buildingMonthItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加大楼用电分类分月统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BuildingMonthItemizeEntity buildingMonthItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(buildingMonthItemize.getId())) {
			message = "更新成功";
			BuildingMonthItemizeEntity t = buildingMonthItemizeService.get(BuildingMonthItemizeEntity.class, buildingMonthItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(buildingMonthItemize, t);
				buildingMonthItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			buildingMonthItemizeService.save(buildingMonthItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 大楼用电分类分月统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BuildingMonthItemizeEntity buildingMonthItemize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(buildingMonthItemize.getId())) {
			buildingMonthItemize = buildingMonthItemizeService.getEntity(BuildingMonthItemizeEntity.class, buildingMonthItemize.getId());
			req.setAttribute("buildingMonthItemizePage", buildingMonthItemize);
		}
		return new ModelAndView("nhjkpt/configmanage/buildingmonthitemize/buildingMonthItemize");
	}
}
