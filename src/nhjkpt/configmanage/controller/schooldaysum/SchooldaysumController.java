package nhjkpt.configmanage.controller.schooldaysum;

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

import nhjkpt.configmanage.entity.schooldaysum.SchooldaysumEntity;
import nhjkpt.configmanage.service.schooldaysum.SchooldaysumServiceI;

/**   
 * @Title: Controller
 * @Description: 学校用电总量分天统计表
 * @author qf
 * @date 2013-08-16 10:05:33
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/schooldaysumController")
public class SchooldaysumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchooldaysumController.class);

	@Autowired
	private SchooldaysumServiceI schooldaysumService;
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
	 * 学校用电总量分天统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schooldaysum")
	public ModelAndView schooldaysum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/schooldaysum/schooldaysumList");
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
	public void datagrid(SchooldaysumEntity schooldaysum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchooldaysumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, schooldaysum);
		this.schooldaysumService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学校用电总量分天统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SchooldaysumEntity schooldaysum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		schooldaysum = systemService.getEntity(SchooldaysumEntity.class, schooldaysum.getId());
		message = "删除成功";
		schooldaysumService.delete(schooldaysum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学校用电总量分天统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SchooldaysumEntity schooldaysum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(schooldaysum.getId())) {
			message = "更新成功";
			SchooldaysumEntity t = schooldaysumService.get(SchooldaysumEntity.class, schooldaysum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(schooldaysum, t);
				schooldaysumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			schooldaysumService.save(schooldaysum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学校用电总量分天统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SchooldaysumEntity schooldaysum, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(schooldaysum.getId())) {
			schooldaysum = schooldaysumService.getEntity(SchooldaysumEntity.class, schooldaysum.getId());
			req.setAttribute("schooldaysumPage", schooldaysum);
		}
		return new ModelAndView("nhjkpt/configmanage/schooldaysum/schooldaysum");
	}
}
