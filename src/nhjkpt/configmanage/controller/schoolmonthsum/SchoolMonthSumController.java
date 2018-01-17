package nhjkpt.configmanage.controller.schoolmonthsum;

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

import nhjkpt.configmanage.entity.schoolmonthsum.SchoolMonthSumEntity;
import nhjkpt.configmanage.service.schoolmonthsum.SchoolMonthSumServiceI;

/**   
 * @Title: Controller
 * @Description: 学校用电总量分月统计表
 * @author qf
 * @date 2013-08-02 00:49:01
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/schoolMonthSumController")
public class SchoolMonthSumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchoolMonthSumController.class);

	@Autowired
	private SchoolMonthSumServiceI schoolMonthSumService;
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
	 * 学校用电总量分月统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schoolMonthSum")
	public ModelAndView schoolMonthSum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/schoolmonthsum/schoolMonthSumList");
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
	public void datagrid(SchoolMonthSumEntity schoolMonthSum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchoolMonthSumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, schoolMonthSum);
		this.schoolMonthSumService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学校用电总量分月统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SchoolMonthSumEntity schoolMonthSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		schoolMonthSum = systemService.getEntity(SchoolMonthSumEntity.class, schoolMonthSum.getId());
		message = "删除成功";
		schoolMonthSumService.delete(schoolMonthSum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学校用电总量分月统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SchoolMonthSumEntity schoolMonthSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(schoolMonthSum.getId())) {
			message = "更新成功";
			SchoolMonthSumEntity t = schoolMonthSumService.get(SchoolMonthSumEntity.class, schoolMonthSum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(schoolMonthSum, t);
				schoolMonthSumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			schoolMonthSumService.save(schoolMonthSum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学校用电总量分月统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SchoolMonthSumEntity schoolMonthSum, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(schoolMonthSum.getId())) {
			schoolMonthSum = schoolMonthSumService.getEntity(SchoolMonthSumEntity.class, schoolMonthSum.getId());
			req.setAttribute("schoolMonthSumPage", schoolMonthSum);
		}
		return new ModelAndView("nhjkpt/configmanage/schoolmonthsum/schoolMonthSum");
	}
}
