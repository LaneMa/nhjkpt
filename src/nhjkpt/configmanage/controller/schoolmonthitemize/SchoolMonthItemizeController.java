package nhjkpt.configmanage.controller.schoolmonthitemize;

import java.util.List;

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
import org.framework.core.common.model.json.Highchart;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.tag.core.easyui.TagUtil;
import nhjkpt.system.service.SystemService;
import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.schoolmonthitemize.SchoolMonthItemizeEntity;
import nhjkpt.configmanage.service.schoolmonthitemize.SchoolMonthItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 学校用电分类分月统计表
 * @author qf
 * @date 2013-08-02 01:03:42
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/schoolMonthItemizeController")
public class SchoolMonthItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchoolMonthItemizeController.class);

	@Autowired
	private SchoolMonthItemizeServiceI schoolMonthItemizeService;
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
	 * 学校用电分类分月统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schoolMonthItemize")
	public ModelAndView schoolMonthItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/schoolmonthitemize/schoolMonthItemizeList");
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
	public void datagrid(SchoolMonthItemizeEntity schoolMonthItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchoolMonthItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, schoolMonthItemize);
		this.schoolMonthItemizeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学校用电分类分月统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SchoolMonthItemizeEntity schoolMonthItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		schoolMonthItemize = systemService.getEntity(SchoolMonthItemizeEntity.class, schoolMonthItemize.getId());
		message = "删除成功";
		schoolMonthItemizeService.delete(schoolMonthItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学校用电分类分月统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SchoolMonthItemizeEntity schoolMonthItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(schoolMonthItemize.getId())) {
			message = "更新成功";
			SchoolMonthItemizeEntity t = schoolMonthItemizeService.get(SchoolMonthItemizeEntity.class, schoolMonthItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(schoolMonthItemize, t);
				schoolMonthItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			schoolMonthItemizeService.save(schoolMonthItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学校用电分类分月统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SchoolMonthItemizeEntity schoolMonthItemize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(schoolMonthItemize.getId())) {
			schoolMonthItemize = schoolMonthItemizeService.getEntity(SchoolMonthItemizeEntity.class, schoolMonthItemize.getId());
			req.setAttribute("schoolMonthItemizePage", schoolMonthItemize);
		}
		return new ModelAndView("nhjkpt/configmanage/schoolmonthitemize/schoolMonthItemize");
	}
	/**
	 * 学校用能总量的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurve")
	public ModelAndView stacurve(HttpServletRequest req) {
		return new ModelAndView("nhjkpt/configmanage/schoolmonthitemize/stacurve");
	}
	/**
	 * 获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBar")
	@ResponseBody
	public List<Highchart> queryBroswerBar(String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return schoolMonthItemizeService.queryHighchart(itemizeid, type, startDate, endDate);
	}
}
