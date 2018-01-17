package nhjkpt.configmanage.controller.departmentdayitemize;

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

import nhjkpt.configmanage.entity.departmentdayitemize.DepartmentDayItemizeEntity;
import nhjkpt.configmanage.service.departmentdayitemize.DepartmentDayItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 系天分类用能
 * @author qf
 * @date 2014-11-19 00:01:36
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/departmentDayItemizeController")
public class DepartmentDayItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartmentDayItemizeController.class);

	@Autowired
	private DepartmentDayItemizeServiceI departmentDayItemizeService;
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
	 * 系天分类用能列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "departmentDayItemize")
	public ModelAndView departmentDayItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/departmentdayitemize/departmentDayItemizeList");
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
	public void datagrid(DepartmentDayItemizeEntity departmentDayItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DepartmentDayItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, departmentDayItemize);
		this.departmentDayItemizeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除系天分类用能
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DepartmentDayItemizeEntity departmentDayItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		departmentDayItemize = systemService.getEntity(DepartmentDayItemizeEntity.class, departmentDayItemize.getId());
		message = "删除成功";
		departmentDayItemizeService.delete(departmentDayItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加系天分类用能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DepartmentDayItemizeEntity departmentDayItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(departmentDayItemize.getId())) {
			message = "更新成功";
			DepartmentDayItemizeEntity t = departmentDayItemizeService.get(DepartmentDayItemizeEntity.class, departmentDayItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(departmentDayItemize, t);
				departmentDayItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			departmentDayItemizeService.save(departmentDayItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 系天分类用能列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DepartmentDayItemizeEntity departmentDayItemize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(departmentDayItemize.getId())) {
			departmentDayItemize = departmentDayItemizeService.getEntity(DepartmentDayItemizeEntity.class, departmentDayItemize.getId());
			req.setAttribute("departmentDayItemizePage", departmentDayItemize);
		}
		return new ModelAndView("nhjkpt/configmanage/departmentdayitemize/departmentDayItemize");
	}
}
