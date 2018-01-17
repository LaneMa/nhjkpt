package nhjkpt.configmanage.controller.departmentunit;

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
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingunit.BuildingUnitEntity;
import nhjkpt.configmanage.entity.departmentinfo.DepartmentinfoEntity;
import nhjkpt.configmanage.entity.departmentunit.DepartmentUnitEntity;
import nhjkpt.configmanage.service.departmentunit.DepartmentUnitServiceI;

/**   
 * @Title: Controller
 * @Description: 分户单位用能
 * @author qf
 * @date 2014-11-19 22:06:40
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/departmentUnitController")
public class DepartmentUnitController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartmentUnitController.class);

	@Autowired
	private DepartmentUnitServiceI departmentUnitService;
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
	 * 分户单位用能列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "departmentUnit")
	public ModelAndView departmentUnit(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/departmentunit/departmentUnitList");
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
	public void datagrid(DepartmentUnitEntity departmentUnit,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DepartmentUnitEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, departmentUnit);
		this.departmentUnitService.getDataGridReturn(cq, true);
		List<DepartmentUnitEntity> unitlist=dataGrid.getReaults();
		List<DepartmentinfoEntity> departList = systemService.getList(DepartmentinfoEntity.class);
		for(DepartmentUnitEntity unit:unitlist){
			for (DepartmentinfoEntity depart : departList) {
				if(CommonUtil.NEWID){
					if(depart.getId().equals(unit.getDepartmentid())){
						unit.setDepartmentid(depart.getDepartmentname());
					}
				}else{
					if(depart.getDepartmentid().equals(unit.getDepartmentid())){
						unit.setDepartmentid(depart.getDepartmentname());
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除分户单位用能
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DepartmentUnitEntity departmentUnit, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		departmentUnit = systemService.getEntity(DepartmentUnitEntity.class, departmentUnit.getId());
		message = "删除成功";
		departmentUnitService.delete(departmentUnit);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加分户单位用能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DepartmentUnitEntity departmentUnit, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(departmentUnit.getId())) {
			message = "更新成功";
			DepartmentUnitEntity t = departmentUnitService.get(DepartmentUnitEntity.class, departmentUnit.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(departmentUnit, t);
				departmentUnitService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			departmentUnitService.save(departmentUnit);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 分户单位用能列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DepartmentUnitEntity departmentUnit, HttpServletRequest req) {
		List<DepartmentinfoEntity> departmentlist = systemService.getList(DepartmentinfoEntity.class);
		req.setAttribute("departmentlist", departmentlist);
		if (StringUtil.isNotEmpty(departmentUnit.getId())) {
			departmentUnit = departmentUnitService.getEntity(DepartmentUnitEntity.class, departmentUnit.getId());
			req.setAttribute("departmentUnitPage", departmentUnit);
		}
		return new ModelAndView("nhjkpt/configmanage/departmentunit/departmentUnit");
	}
	
	/**
	 * 分户平均用能的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurveItemize")
	public ModelAndView stacurveItemize(String departmentid,HttpServletRequest req) {
		req.setAttribute("departmentid", departmentid);
		//获取此大楼的单位能耗方式
		List<DepartmentUnitEntity> list=systemService.findByProperty(DepartmentUnitEntity.class, "departmentid", departmentid);
		req.setAttribute("list", list);	
		return new ModelAndView("nhjkpt/configmanage/departmentunit/stacurveItemize");
	}
	
	/**
	 * 获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBar")
	@ResponseBody
	public List<Highchart> queryBroswerBar(String unitid,String departmentid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return departmentUnitService.queryHighchart(unitid,departmentid,type, startDate, endDate);
	}
	/**
	 * 获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarItemize")
	@ResponseBody
	public List<Highchart> queryBroswerBarItemize(String unitid,String buildingid,String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return departmentUnitService.queryHighchartItemize(unitid,buildingid,itemizeid,type, startDate, endDate);
	}
}
