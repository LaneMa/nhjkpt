package nhjkpt.configmanage.controller.schoolunit;

import java.io.UnsupportedEncodingException;
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
import org.framework.core.common.model.json.ValidForm;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.core.util.oConvertUtils;
import org.framework.tag.core.easyui.TagUtil;
import nhjkpt.system.service.SystemService;
import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.schoolunit.SchoolUnitEntity;
import nhjkpt.configmanage.service.schoolunit.SchoolUnitServiceI;

/**   
 * @Title: Controller
 * @Description: 学校单位用能统计计算表
 * @author qf
 * @date 2013-08-02 01:14:55
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/schoolUnitController")
public class SchoolUnitController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchoolUnitController.class);

	@Autowired
	private SchoolUnitServiceI schoolUnitService;
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
	 * 学校单位用能统计计算表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schoolUnit")
	public ModelAndView schoolUnit(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/schoolunit/schoolUnitList");
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
	public void datagrid(SchoolUnitEntity schoolUnit,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchoolUnitEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, schoolUnit);
		this.schoolUnitService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学校单位用能统计计算表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SchoolUnitEntity schoolUnit, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		schoolUnit = systemService.getEntity(SchoolUnitEntity.class, schoolUnit.getId());
		message = "删除成功";
		schoolUnitService.delete(schoolUnit);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学校单位用能统计计算表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SchoolUnitEntity schoolUnit, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(schoolUnit.getId())) {
			message = "更新成功";
			SchoolUnitEntity t = schoolUnitService.get(SchoolUnitEntity.class, schoolUnit.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(schoolUnit, t);
				schoolUnitService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			schoolUnitService.save(schoolUnit);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学校单位用能统计计算表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SchoolUnitEntity schoolUnit, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(schoolUnit.getId())) {
			schoolUnit = schoolUnitService.getEntity(SchoolUnitEntity.class, schoolUnit.getId());
			req.setAttribute("schoolUnitPage", schoolUnit);
		}
		return new ModelAndView("nhjkpt/configmanage/schoolunit/schoolUnit");
	}
	/**
	 * 学校单位平均用能的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurve")
	public ModelAndView stacurve(HttpServletRequest req) {
		List<FuncEntity> funclist = systemService.findHql(" from FuncEntity where ischeck=? order by funcid", "1");
		req.setAttribute("funclist", funclist);
		List<SchoolUnitEntity> list=systemService.getList(SchoolUnitEntity.class);
		req.setAttribute("list", list);
		return new ModelAndView("nhjkpt/configmanage/schoolunit/stacurve");
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
	public List<Highchart> queryBroswerBar(String funcid,String unitid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return schoolUnitService.queryHighchart(funcid,unitid,type, startDate, endDate);
	}
	/**
	 * 验证单位的唯一标识
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkunitid")
	@ResponseBody
	public ValidForm checkunitid(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		int unitid=oConvertUtils.getInt(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		List<SchoolUnitEntity> func=systemService.findByProperty( SchoolUnitEntity.class ,"unitid",unitid);
		try {
			code=new String (code.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(func.size()>0&&!code.equals(oConvertUtils.getString(unitid)))
		{
			v.setInfo("此功能标识号已存在");
			v.setStatus("n");
		}
		return v;
	}
	/**
	 * 学校单位分类平均用能的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurveItemize")
	public ModelAndView stacurveItemize(String itemizeid,HttpServletRequest req) {
		req.setAttribute("itemizeid", itemizeid);
		return new ModelAndView("nhjkpt/configmanage/schoolunit/stacurveItemize");
	}
	/**
	 * 学校分类平均获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarItemize")
	@ResponseBody
	public List<Highchart> queryBroswerBarItemize(String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return schoolUnitService.queryHighchartItemize(itemizeid,type, startDate, endDate);
	}
}
