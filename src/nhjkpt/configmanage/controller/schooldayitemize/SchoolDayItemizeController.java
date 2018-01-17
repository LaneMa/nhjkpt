package nhjkpt.configmanage.controller.schooldayitemize;

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

import nhjkpt.configmanage.entity.schooldayitemize.SchoolDayItemizeEntity;
import nhjkpt.configmanage.service.schooldayitemize.SchoolDayItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 学校用能分类分天统计表
 * @author qf
 * @date 2013-08-17 13:55:46
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/schoolDayItemizeController")
public class SchoolDayItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchoolDayItemizeController.class);

	@Autowired
	private SchoolDayItemizeServiceI schoolDayItemizeService;
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
	 * 学校用能分类分天统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schoolDayItemize")
	public ModelAndView schoolDayItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/schooldayitemize/schoolDayItemizeList");
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
	public void datagrid(SchoolDayItemizeEntity schoolDayItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchoolDayItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, schoolDayItemize);
		this.schoolDayItemizeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学校用能分类分天统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SchoolDayItemizeEntity schoolDayItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		schoolDayItemize = systemService.getEntity(SchoolDayItemizeEntity.class, schoolDayItemize.getId());
		message = "删除成功";
		schoolDayItemizeService.delete(schoolDayItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学校用能分类分天统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SchoolDayItemizeEntity schoolDayItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(schoolDayItemize.getId())) {
			message = "更新成功";
			SchoolDayItemizeEntity t = schoolDayItemizeService.get(SchoolDayItemizeEntity.class, schoolDayItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(schoolDayItemize, t);
				schoolDayItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			schoolDayItemizeService.save(schoolDayItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学校用能分类分天统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SchoolDayItemizeEntity schoolDayItemize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(schoolDayItemize.getId())) {
			schoolDayItemize = schoolDayItemizeService.getEntity(SchoolDayItemizeEntity.class, schoolDayItemize.getId());
			req.setAttribute("schoolDayItemizePage", schoolDayItemize);
		}
		return new ModelAndView("nhjkpt/configmanage/schooldayitemize/schoolDayItemize");
	}
}
