package nhjkpt.configmanage.controller.departmentdaysum;

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

import nhjkpt.configmanage.entity.departmentdaysum.DepartmentDaySumEntity;
import nhjkpt.configmanage.service.departmentdaysum.DepartmentDaySumServiceI;

/**   
 * @Title: Controller
 * @Description: 系天用能
 * @author qf
 * @date 2014-11-19 00:00:27
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/departmentDaySumController")
public class DepartmentDaySumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartmentDaySumController.class);

	@Autowired
	private DepartmentDaySumServiceI departmentDaySumService;
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
	 * 系天用能列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "departmentDaySum")
	public ModelAndView departmentDaySum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/departmentdaysum/departmentDaySumList");
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
	public void datagrid(DepartmentDaySumEntity departmentDaySum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DepartmentDaySumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, departmentDaySum);
		this.departmentDaySumService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除系天用能
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DepartmentDaySumEntity departmentDaySum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		departmentDaySum = systemService.getEntity(DepartmentDaySumEntity.class, departmentDaySum.getId());
		message = "删除成功";
		departmentDaySumService.delete(departmentDaySum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加系天用能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DepartmentDaySumEntity departmentDaySum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(departmentDaySum.getId())) {
			message = "更新成功";
			DepartmentDaySumEntity t = departmentDaySumService.get(DepartmentDaySumEntity.class, departmentDaySum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(departmentDaySum, t);
				departmentDaySumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			departmentDaySumService.save(departmentDaySum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 系天用能列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DepartmentDaySumEntity departmentDaySum, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(departmentDaySum.getId())) {
			departmentDaySum = departmentDaySumService.getEntity(DepartmentDaySumEntity.class, departmentDaySum.getId());
			req.setAttribute("departmentDaySumPage", departmentDaySum);
		}
		return new ModelAndView("nhjkpt/configmanage/departmentdaysum/departmentDaySum");
	}
}
