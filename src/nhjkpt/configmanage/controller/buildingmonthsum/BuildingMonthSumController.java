package nhjkpt.configmanage.controller.buildingmonthsum;

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

import nhjkpt.configmanage.entity.buildingmonthsum.BuildingMonthSumEntity;
import nhjkpt.configmanage.service.buildingmonthsum.BuildingMonthSumServiceI;

/**   
 * @Title: Controller
 * @Description: 建筑用电总量分月统计表
 * @author qf
 * @date 2013-08-02 00:56:29
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/buildingMonthSumController")
public class BuildingMonthSumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BuildingMonthSumController.class);

	@Autowired
	private BuildingMonthSumServiceI buildingMonthSumService;
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
	 * 建筑用电总量分月统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "buildingMonthSum")
	public ModelAndView buildingMonthSum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/buildingmonthsum/buildingMonthSumList");
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
	public void datagrid(BuildingMonthSumEntity buildingMonthSum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BuildingMonthSumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, buildingMonthSum);
		this.buildingMonthSumService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除建筑用电总量分月统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BuildingMonthSumEntity buildingMonthSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		buildingMonthSum = systemService.getEntity(BuildingMonthSumEntity.class, buildingMonthSum.getId());
		message = "删除成功";
		buildingMonthSumService.delete(buildingMonthSum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加建筑用电总量分月统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BuildingMonthSumEntity buildingMonthSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(buildingMonthSum.getId())) {
			message = "更新成功";
			BuildingMonthSumEntity t = buildingMonthSumService.get(BuildingMonthSumEntity.class, buildingMonthSum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(buildingMonthSum, t);
				buildingMonthSumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			buildingMonthSumService.save(buildingMonthSum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 建筑用电总量分月统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BuildingMonthSumEntity buildingMonthSum, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(buildingMonthSum.getId())) {
			buildingMonthSum = buildingMonthSumService.getEntity(BuildingMonthSumEntity.class, buildingMonthSum.getId());
			req.setAttribute("buildingMonthSumPage", buildingMonthSum);
		}
		return new ModelAndView("nhjkpt/configmanage/buildingmonthsum/buildingMonthSum");
	}
}
