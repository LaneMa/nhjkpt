package nhjkpt.configmanage.controller.schoolhouritemize;

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

import nhjkpt.configmanage.entity.schoolhouritemize.SchoolHourItemizeEntity;
import nhjkpt.configmanage.service.schoolhouritemize.SchoolHourItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 学校用能分类分时统计表
 * @author qf
 * @date 2013-08-17 13:52:51
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/schoolHourItemizeController")
public class SchoolHourItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchoolHourItemizeController.class);

	@Autowired
	private SchoolHourItemizeServiceI schoolHourItemizeService;
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
	 * 学校用能分类分时统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schoolHourItemize")
	public ModelAndView schoolHourItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/schoolhouritemize/schoolHourItemizeList");
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
	public void datagrid(SchoolHourItemizeEntity schoolHourItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchoolHourItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, schoolHourItemize);
		this.schoolHourItemizeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学校用能分类分时统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SchoolHourItemizeEntity schoolHourItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		schoolHourItemize = systemService.getEntity(SchoolHourItemizeEntity.class, schoolHourItemize.getId());
		message = "删除成功";
		schoolHourItemizeService.delete(schoolHourItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学校用能分类分时统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SchoolHourItemizeEntity schoolHourItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(schoolHourItemize.getId())) {
			message = "更新成功";
			SchoolHourItemizeEntity t = schoolHourItemizeService.get(SchoolHourItemizeEntity.class, schoolHourItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(schoolHourItemize, t);
				schoolHourItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			schoolHourItemizeService.save(schoolHourItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学校用能分类分时统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SchoolHourItemizeEntity schoolHourItemize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(schoolHourItemize.getId())) {
			schoolHourItemize = schoolHourItemizeService.getEntity(SchoolHourItemizeEntity.class, schoolHourItemize.getId());
			req.setAttribute("schoolHourItemizePage", schoolHourItemize);
		}
		return new ModelAndView("nhjkpt/configmanage/schoolhouritemize/schoolHourItemize");
	}
}
