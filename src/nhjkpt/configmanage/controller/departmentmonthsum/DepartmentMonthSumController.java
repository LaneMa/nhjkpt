package nhjkpt.configmanage.controller.departmentmonthsum;

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

import nhjkpt.configmanage.entity.departmentmonthsum.DepartmentMonthSumEntity;
import nhjkpt.configmanage.service.departmentmonthsum.DepartmentMonthSumServiceI;

/**   
 * @Title: Controller
 * @Description: 系月用能
 * @author qf
 * @date 2014-11-19 00:03:45
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/departmentMonthSumController")
public class DepartmentMonthSumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartmentMonthSumController.class);

	@Autowired
	private DepartmentMonthSumServiceI departmentMonthSumService;
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
	 * 系月用能列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "departmentMonthSum")
	public ModelAndView departmentMonthSum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/departmentmonthsum/departmentMonthSumList");
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
	public void datagrid(DepartmentMonthSumEntity departmentMonthSum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DepartmentMonthSumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, departmentMonthSum);
		this.departmentMonthSumService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除系月用能
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DepartmentMonthSumEntity departmentMonthSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		departmentMonthSum = systemService.getEntity(DepartmentMonthSumEntity.class, departmentMonthSum.getId());
		message = "删除成功";
		departmentMonthSumService.delete(departmentMonthSum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加系月用能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DepartmentMonthSumEntity departmentMonthSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(departmentMonthSum.getId())) {
			message = "更新成功";
			DepartmentMonthSumEntity t = departmentMonthSumService.get(DepartmentMonthSumEntity.class, departmentMonthSum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(departmentMonthSum, t);
				departmentMonthSumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			departmentMonthSumService.save(departmentMonthSum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 系月用能列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DepartmentMonthSumEntity departmentMonthSum, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(departmentMonthSum.getId())) {
			departmentMonthSum = departmentMonthSumService.getEntity(DepartmentMonthSumEntity.class, departmentMonthSum.getId());
			req.setAttribute("departmentMonthSumPage", departmentMonthSum);
		}
		return new ModelAndView("nhjkpt/configmanage/departmentmonthsum/departmentMonthSum");
	}
}
