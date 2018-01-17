package nhjkpt.configmanage.controller.departmenthoursum;

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

import nhjkpt.configmanage.entity.departmenthoursum.DepartmentHourSumEntity;
import nhjkpt.configmanage.service.departmenthoursum.DepartmentHourSumServiceI;

/**   
 * @Title: Controller
 * @Description: 系小时用能
 * @author qf
 * @date 2014-11-18 23:57:08
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/departmentHourSumController")
public class DepartmentHourSumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartmentHourSumController.class);

	@Autowired
	private DepartmentHourSumServiceI departmentHourSumService;
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
	 * 系小时用能列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "departmentHourSum")
	public ModelAndView departmentHourSum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/departmenthoursum/departmentHourSumList");
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
	public void datagrid(DepartmentHourSumEntity departmentHourSum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DepartmentHourSumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, departmentHourSum);
		this.departmentHourSumService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除系小时用能
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DepartmentHourSumEntity departmentHourSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		departmentHourSum = systemService.getEntity(DepartmentHourSumEntity.class, departmentHourSum.getId());
		message = "删除成功";
		departmentHourSumService.delete(departmentHourSum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加系小时用能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DepartmentHourSumEntity departmentHourSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(departmentHourSum.getId())) {
			message = "更新成功";
			DepartmentHourSumEntity t = departmentHourSumService.get(DepartmentHourSumEntity.class, departmentHourSum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(departmentHourSum, t);
				departmentHourSumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			departmentHourSumService.save(departmentHourSum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 系小时用能列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DepartmentHourSumEntity departmentHourSum, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(departmentHourSum.getId())) {
			departmentHourSum = departmentHourSumService.getEntity(DepartmentHourSumEntity.class, departmentHourSum.getId());
			req.setAttribute("departmentHourSumPage", departmentHourSum);
		}
		return new ModelAndView("nhjkpt/configmanage/departmenthoursum/departmentHourSum");
	}
}
