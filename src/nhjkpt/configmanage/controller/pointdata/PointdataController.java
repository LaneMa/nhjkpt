package nhjkpt.configmanage.controller.pointdata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.pointdata.PointdataEntity;
import nhjkpt.configmanage.service.buildingdayitemize.BuildingDayItemizeServiceI;
import nhjkpt.configmanage.service.buildingdaysum.BuildingDaySumServiceI;
import nhjkpt.configmanage.service.buildinghouritemize.BuildingHourItemizeServiceI;
import nhjkpt.configmanage.service.buildinghoursum.BuildingHourSumServiceI;
import nhjkpt.configmanage.service.buildingmonthitemize.BuildingMonthItemizeServiceI;
import nhjkpt.configmanage.service.buildingmonthsum.BuildingMonthSumServiceI;
import nhjkpt.configmanage.service.hourdata.HourdataServiceI;
import nhjkpt.configmanage.service.pointdata.PointdataServiceI;
import nhjkpt.configmanage.service.schooldayitemize.SchoolDayItemizeServiceI;
import nhjkpt.configmanage.service.schooldaysum.SchooldaysumServiceI;
import nhjkpt.configmanage.service.schoolhouritemize.SchoolHourItemizeServiceI;
import nhjkpt.configmanage.service.schoolhoursum.SchoolhoursumServiceI;
import nhjkpt.configmanage.service.schoolmonthitemize.SchoolMonthItemizeServiceI;
import nhjkpt.configmanage.service.schoolmonthsum.SchoolMonthSumServiceI;

/**   
 * @Title: Controller
 * @Description: 历史数据
 * @author qf
 * @date 2013-08-11 00:21:48
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/pointdataController")
public class PointdataController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PointdataController.class);

	@Autowired
	private PointdataServiceI pointdataService;
	@Autowired
	private HourdataServiceI hourdataService;
	@Autowired
	private SchoolhoursumServiceI schoolhoursumService;
	@Autowired
	private SchooldaysumServiceI schooldaysumService;
	@Autowired
	private SchoolMonthSumServiceI schoolMonthSumService;
	@Autowired
	private SchoolHourItemizeServiceI schoolHourItemizeService;
	@Autowired
	private SchoolDayItemizeServiceI schoolDayItemizeService;
	@Autowired
	private SchoolMonthItemizeServiceI schoolMonthItemizeService;
	@Autowired
	private BuildingHourSumServiceI buildingHourSumService;
	@Autowired
	private BuildingDaySumServiceI buildingDaySumService;
	@Autowired
	private BuildingMonthSumServiceI buildingMonthSumService;
	@Autowired
	private BuildingHourItemizeServiceI buildingHourItemizeService;
	@Autowired
	private BuildingDayItemizeServiceI buildingDayItemizeService;
	@Autowired
	private BuildingMonthItemizeServiceI buildingMonthItemizeService;
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
	 * 历史数据列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "pointdata")
	public ModelAndView pointdata(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/pointdata/pointdataList");
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
	public void datagrid(PointdataEntity pointdata,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(PointdataEntity.class, dataGrid);
//		//查询条件组装器
//		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, pointdata);
//		this.pointdataService.getDataGridReturn(cq, true);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("startDate", CommonUtil.START_DATE);
		map.put("endDate", CommonUtil.END_DATE);
		map.put("nowDate", CommonUtil.IMPORT);
		list.add(map);
		dataGrid.setReaults(list);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除历史数据
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(PointdataEntity pointdata, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		pointdata = systemService.getEntity(PointdataEntity.class, pointdata.getId());
		message = "删除成功";
		pointdataService.delete(pointdata);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加历史数据
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(PointdataEntity pointdata, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(pointdata.getId())) {
			message = "更新成功";
			PointdataEntity t = pointdataService.get(PointdataEntity.class, pointdata.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(pointdata, t);
				pointdataService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			pointdataService.save(pointdata);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 历史数据列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(PointdataEntity pointdata, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(pointdata.getId())) {
			pointdata = pointdataService.getEntity(PointdataEntity.class, pointdata.getId());
			req.setAttribute("pointdataPage", pointdata);
		}
		return new ModelAndView("nhjkpt/configmanage/pointdata/pointdata");
	}
	@RequestMapping(params = "importPointData")
	public ModelAndView importPointData(HttpServletRequest req) {
		return new ModelAndView("nhjkpt/configmanage/pointdata/importPointdata");
	}
	@RequestMapping(params = "doImport")
	public ModelAndView doImport(String startDate,String endDate,HttpServletRequest req) {
		if(CommonUtil.isNull(CommonUtil.START_DATE)&&CommonUtil.isNull(CommonUtil.END_DATE)){
			CommonUtil.START_DATE=startDate;
			CommonUtil.END_DATE=endDate;
			req.setAttribute("message", "start");
		}else{
			req.setAttribute("message", "has");
		}
		return new ModelAndView("nhjkpt/configmanage/pointdata/importPointdata");
	}
}
