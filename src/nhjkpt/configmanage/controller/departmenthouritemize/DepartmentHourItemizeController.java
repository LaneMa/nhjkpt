package nhjkpt.configmanage.controller.departmenthouritemize;

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

import nhjkpt.configmanage.entity.departmenthouritemize.DepartmentHourItemizeEntity;
import nhjkpt.configmanage.service.departmenthouritemize.DepartmentHourItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 系小时分类用能
 * @author qf
 * @date 2014-11-18 23:58:10
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/departmentHourItemizeController")
public class DepartmentHourItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartmentHourItemizeController.class);

	@Autowired
	private DepartmentHourItemizeServiceI departmentHourItemizeService;
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
	 * 系小时分类用能列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "departmentHourItemize")
	public ModelAndView departmentHourItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/departmenthouritemize/departmentHourItemizeList");
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
	public void datagrid(DepartmentHourItemizeEntity departmentHourItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DepartmentHourItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, departmentHourItemize);
		this.departmentHourItemizeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除系小时分类用能
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DepartmentHourItemizeEntity departmentHourItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		departmentHourItemize = systemService.getEntity(DepartmentHourItemizeEntity.class, departmentHourItemize.getId());
		message = "删除成功";
		departmentHourItemizeService.delete(departmentHourItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加系小时分类用能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DepartmentHourItemizeEntity departmentHourItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(departmentHourItemize.getId())) {
			message = "更新成功";
			DepartmentHourItemizeEntity t = departmentHourItemizeService.get(DepartmentHourItemizeEntity.class, departmentHourItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(departmentHourItemize, t);
				departmentHourItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			departmentHourItemizeService.save(departmentHourItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 系小时分类用能列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DepartmentHourItemizeEntity departmentHourItemize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(departmentHourItemize.getId())) {
			departmentHourItemize = departmentHourItemizeService.getEntity(DepartmentHourItemizeEntity.class, departmentHourItemize.getId());
			req.setAttribute("departmentHourItemizePage", departmentHourItemize);
		}
		return new ModelAndView("nhjkpt/configmanage/departmenthouritemize/departmentHourItemize");
	}
}
