package nhjkpt.configmanage.controller.buildinghoursum;

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

import nhjkpt.configmanage.entity.buildinghoursum.BuildingHourSumEntity;
import nhjkpt.configmanage.service.buildinghoursum.BuildingHourSumServiceI;

/**   
 * @Title: Controller
 * @Description: 建筑用电总量分时统计表
 * @author qf
 * @date 2013-08-17 11:14:39
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/buildingHourSumController")
public class BuildingHourSumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BuildingHourSumController.class);

	@Autowired
	private BuildingHourSumServiceI buildingHourSumService;
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
	 * 建筑用电总量分时统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "buildingHourSum")
	public ModelAndView buildingHourSum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/buildinghoursum/buildingHourSumList");
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
	public void datagrid(BuildingHourSumEntity buildingHourSum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BuildingHourSumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, buildingHourSum);
		this.buildingHourSumService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除建筑用电总量分时统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BuildingHourSumEntity buildingHourSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		buildingHourSum = systemService.getEntity(BuildingHourSumEntity.class, buildingHourSum.getId());
		message = "删除成功";
		buildingHourSumService.delete(buildingHourSum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加建筑用电总量分时统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BuildingHourSumEntity buildingHourSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(buildingHourSum.getId())) {
			message = "更新成功";
			BuildingHourSumEntity t = buildingHourSumService.get(BuildingHourSumEntity.class, buildingHourSum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(buildingHourSum, t);
				buildingHourSumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			buildingHourSumService.save(buildingHourSum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 建筑用电总量分时统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BuildingHourSumEntity buildingHourSum, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(buildingHourSum.getId())) {
			buildingHourSum = buildingHourSumService.getEntity(BuildingHourSumEntity.class, buildingHourSum.getId());
			req.setAttribute("buildingHourSumPage", buildingHourSum);
		}
		return new ModelAndView("nhjkpt/configmanage/buildinghoursum/buildingHourSum");
	}
}
