package nhjkpt.configmanage.controller.buildinghouritemize;

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

import nhjkpt.configmanage.entity.buildinghouritemize.BuildingHourItemizeEntity;
import nhjkpt.configmanage.service.buildinghouritemize.BuildingHourItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 建筑用能分类分时统计表
 * @author qf
 * @date 2013-08-17 16:35:30
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/buildingHourItemizeController")
public class BuildingHourItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BuildingHourItemizeController.class);

	@Autowired
	private BuildingHourItemizeServiceI buildingHourItemizeService;
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
	 * 建筑用能分类分时统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "buildingHourItemize")
	public ModelAndView buildingHourItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/buildinghouritemize/buildingHourItemizeList");
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
	public void datagrid(BuildingHourItemizeEntity buildingHourItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BuildingHourItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, buildingHourItemize);
		this.buildingHourItemizeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除建筑用能分类分时统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BuildingHourItemizeEntity buildingHourItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		buildingHourItemize = systemService.getEntity(BuildingHourItemizeEntity.class, buildingHourItemize.getId());
		message = "删除成功";
		buildingHourItemizeService.delete(buildingHourItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加建筑用能分类分时统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BuildingHourItemizeEntity buildingHourItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(buildingHourItemize.getId())) {
			message = "更新成功";
			BuildingHourItemizeEntity t = buildingHourItemizeService.get(BuildingHourItemizeEntity.class, buildingHourItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(buildingHourItemize, t);
				buildingHourItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			buildingHourItemizeService.save(buildingHourItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 建筑用能分类分时统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BuildingHourItemizeEntity buildingHourItemize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(buildingHourItemize.getId())) {
			buildingHourItemize = buildingHourItemizeService.getEntity(BuildingHourItemizeEntity.class, buildingHourItemize.getId());
			req.setAttribute("buildingHourItemizePage", buildingHourItemize);
		}
		return new ModelAndView("nhjkpt/configmanage/buildinghouritemize/buildingHourItemize");
	}
}
