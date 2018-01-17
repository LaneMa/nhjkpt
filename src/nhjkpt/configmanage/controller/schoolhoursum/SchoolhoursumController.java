package nhjkpt.configmanage.controller.schoolhoursum;

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

import nhjkpt.configmanage.entity.schoolhoursum.SchoolhoursumEntity;
import nhjkpt.configmanage.service.schoolhoursum.SchoolhoursumServiceI;

/**   
 * @Title: Controller
 * @Description: 学校用电总量分时统计表
 * @author qf
 * @date 2013-08-15 00:37:58
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/schoolhoursumController")
public class SchoolhoursumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchoolhoursumController.class);

	@Autowired
	private SchoolhoursumServiceI schoolhoursumService;
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
	 * 学校用电总量分时统计表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schoolhoursum")
	public ModelAndView schoolhoursum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/schoolhoursum/schoolhoursumList");
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
	public void datagrid(SchoolhoursumEntity schoolhoursum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchoolhoursumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, schoolhoursum);
		this.schoolhoursumService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学校用电总量分时统计表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SchoolhoursumEntity schoolhoursum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		schoolhoursum = systemService.getEntity(SchoolhoursumEntity.class, schoolhoursum.getId());
		message = "删除成功";
		schoolhoursumService.delete(schoolhoursum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学校用电总量分时统计表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SchoolhoursumEntity schoolhoursum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(schoolhoursum.getId())) {
			message = "更新成功";
			SchoolhoursumEntity t = schoolhoursumService.get(SchoolhoursumEntity.class, schoolhoursum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(schoolhoursum, t);
				schoolhoursumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			schoolhoursumService.save(schoolhoursum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学校用电总量分时统计表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SchoolhoursumEntity schoolhoursum, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(schoolhoursum.getId())) {
			schoolhoursum = schoolhoursumService.getEntity(SchoolhoursumEntity.class, schoolhoursum.getId());
			req.setAttribute("schoolhoursumPage", schoolhoursum);
		}
		return new ModelAndView("nhjkpt/configmanage/schoolhoursum/schoolhoursum");
	}
	/**
	 * 获取本日用水柱状图
	 * @param funcid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryEnergySortNEBar")
	@ResponseBody
	public List<Highchart> queryEnergySortNEBar(HttpServletRequest request,HttpServletResponse response) {
		return schoolhoursumService.queryEnergySortNEBar();
	}
	/**
	 * 获取本日用电柱状图
	 * @param funcid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryEnergySortNCBar")
	@ResponseBody
	public List<Highchart> queryEnergySortNCBar(HttpServletRequest request,HttpServletResponse response) {
		return schoolhoursumService.queryEnergySortNCBar();
	}
}
