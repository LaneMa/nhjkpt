package nhjkpt.configmanage.controller.departmentmonthitemize;

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

import nhjkpt.configmanage.entity.departmentmonthitemize.DepartmentMonthItemizeEntity;
import nhjkpt.configmanage.service.departmentmonthitemize.DepartmentMonthItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 系月分类用能
 * @author qf
 * @date 2014-11-19 00:04:52
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/departmentMonthItemizeController")
public class DepartmentMonthItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartmentMonthItemizeController.class);

	@Autowired
	private DepartmentMonthItemizeServiceI departmentMonthItemizeService;
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
	 * 系月分类用能列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "departmentMonthItemize")
	public ModelAndView departmentMonthItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/departmentmonthitemize/departmentMonthItemizeList");
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
	public void datagrid(DepartmentMonthItemizeEntity departmentMonthItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DepartmentMonthItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, departmentMonthItemize);
		this.departmentMonthItemizeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除系月分类用能
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DepartmentMonthItemizeEntity departmentMonthItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		departmentMonthItemize = systemService.getEntity(DepartmentMonthItemizeEntity.class, departmentMonthItemize.getId());
		message = "删除成功";
		departmentMonthItemizeService.delete(departmentMonthItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加系月分类用能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DepartmentMonthItemizeEntity departmentMonthItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(departmentMonthItemize.getId())) {
			message = "更新成功";
			DepartmentMonthItemizeEntity t = departmentMonthItemizeService.get(DepartmentMonthItemizeEntity.class, departmentMonthItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(departmentMonthItemize, t);
				departmentMonthItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			departmentMonthItemizeService.save(departmentMonthItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 系月分类用能列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DepartmentMonthItemizeEntity departmentMonthItemize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(departmentMonthItemize.getId())) {
			departmentMonthItemize = departmentMonthItemizeService.getEntity(DepartmentMonthItemizeEntity.class, departmentMonthItemize.getId());
			req.setAttribute("departmentMonthItemizePage", departmentMonthItemize);
		}
		return new ModelAndView("nhjkpt/configmanage/departmentmonthitemize/departmentMonthItemize");
	}
}
