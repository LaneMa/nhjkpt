package nhjkpt.configmanage.controller.buildingdaysum;

import java.util.List;

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
import org.framework.core.common.model.json.Highchart;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.tag.core.easyui.TagUtil;
import nhjkpt.system.service.SystemService;
import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.buildingdaysum.BuildingDaySumEntity;
import nhjkpt.configmanage.service.buildingdaysum.BuildingDaySumServiceI;

/**   
 * @Title: Controller
 * @Description: 建筑用电总量分日统计表
 * @author qf
 * @date 2013-08-17 11:46:39
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/buildingDaySumController")
public class BuildingDaySumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BuildingDaySumController.class);

	@Autowired
	private BuildingDaySumServiceI buildingDaySumService;
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
	 * 建筑用电总量分日统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "buildingDaySum")
	public ModelAndView buildingDaySum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/buildingdaysum/buildingDaySumList");
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
	public void datagrid(BuildingDaySumEntity buildingDaySum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BuildingDaySumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, buildingDaySum);
		this.buildingDaySumService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除建筑用电总量分日统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BuildingDaySumEntity buildingDaySum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		buildingDaySum = systemService.getEntity(BuildingDaySumEntity.class, buildingDaySum.getId());
		message = "删除成功";
		buildingDaySumService.delete(buildingDaySum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加建筑用电总量分日统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BuildingDaySumEntity buildingDaySum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(buildingDaySum.getId())) {
			message = "更新成功";
			BuildingDaySumEntity t = buildingDaySumService.get(BuildingDaySumEntity.class, buildingDaySum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(buildingDaySum, t);
				buildingDaySumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			buildingDaySumService.save(buildingDaySum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 建筑用电总量分日统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BuildingDaySumEntity buildingDaySum, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(buildingDaySum.getId())) {
			buildingDaySum = buildingDaySumService.getEntity(BuildingDaySumEntity.class, buildingDaySum.getId());
			req.setAttribute("buildingDaySumPage", buildingDaySum);
		}
		return new ModelAndView("nhjkpt/configmanage/buildingdaysum/buildingDaySum");
	}
	/**
	 * 用能排名
	 * @param funcid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryEnergySortCEBar")
	@ResponseBody
	public List<Highchart> queryEnergySortCEBar(HttpServletRequest request,HttpServletResponse response) {
		return buildingDaySumService.queryEnergySortCEBar();
	}
}
