package nhjkpt.configmanage.controller.buildingunit;

import java.io.IOException;
import java.io.OutputStream;
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
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingunit.BuildingUnitEntity;
import nhjkpt.configmanage.entity.roommanage.RoomEntity;
import nhjkpt.configmanage.entity.schoolunit.SchoolUnitEntity;
import nhjkpt.configmanage.service.buildingunit.BuildingUnitServiceI;

/**   
 * @Title: Controller
 * @Description: 建筑单位用能统计计算表
 * @author qf
 * @date 2013-08-02 01:18:03
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/buildingUnitController")
public class BuildingUnitController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BuildingUnitController.class);

	@Autowired
	private BuildingUnitServiceI buildingUnitService;
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
	 * 建筑单位用能统计计算表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "buildingUnit")
	public ModelAndView buildingUnit(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/buildingunit/buildingUnitList");
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
	public void datagrid(BuildingUnitEntity buildingUnit,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BuildingUnitEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, buildingUnit);
		this.buildingUnitService.getDataGridReturn(cq, true);
		List<BuildingUnitEntity> unitlist=dataGrid.getReaults();
		List<BuildinginfoEntity> buildList = systemService.getList(BuildinginfoEntity.class);
		for(BuildingUnitEntity unit:unitlist){
			for (BuildinginfoEntity build : buildList) {
				if(CommonUtil.NEWID){
					if(build.getId().equals(unit.getBuildingid())){
						unit.setBuildingid(build.getBuildingname());
					}
				}else{
					if(build.getBuildingid().equals(unit.getBuildingid())){
						unit.setBuildingid(build.getBuildingname());
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除建筑单位用能统计计算表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BuildingUnitEntity buildingUnit, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		buildingUnit = systemService.getEntity(BuildingUnitEntity.class, buildingUnit.getId());
		message = "删除成功";
		buildingUnitService.delete(buildingUnit);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加建筑单位用能统计计算表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BuildingUnitEntity buildingUnit, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(buildingUnit.getId())) {
			message = "更新成功";
			BuildingUnitEntity t = buildingUnitService.get(BuildingUnitEntity.class, buildingUnit.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(buildingUnit, t);
				buildingUnitService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			buildingUnitService.save(buildingUnit);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 建筑单位用能统计计算表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BuildingUnitEntity buildingUnit, HttpServletRequest req) {
		List<BuildinginfoEntity> buildinglist = systemService.getList(BuildinginfoEntity.class);
		req.setAttribute("buildinglist", buildinglist);
		if (StringUtil.isNotEmpty(buildingUnit.getId())) {
			buildingUnit = buildingUnitService.getEntity(BuildingUnitEntity.class, buildingUnit.getId());
			req.setAttribute("buildingUnitPage", buildingUnit);
		}
		return new ModelAndView("nhjkpt/configmanage/buildingunit/buildingUnit");
	}
	/**
	 * 学校单位平均用能的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurve")
	public ModelAndView stacurve(String buildingid,String type,HttpServletRequest req) {
		if(CommonUtil.isNull(buildingid)){
			List<BuildinginfoEntity> buildingList=systemService.getList(BuildinginfoEntity.class);
			req.setAttribute("buildingList", buildingList);
		}else{
			req.setAttribute("buildingid", buildingid);
		}
		req.setAttribute("type", type);
		List<BuildingUnitEntity> list=systemService.findByProperty(BuildingUnitEntity.class,"buildingid" , buildingid);
		req.setAttribute("list", list);
		return new ModelAndView("nhjkpt/configmanage/buildingunit/stacurve");
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
	public List<Highchart> queryBroswerBar(String unitid,String buildingid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return buildingUnitService.queryHighchart(unitid,buildingid,type, startDate, endDate);
	}
	/**
	 * 学校单位平均用能的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurveItemize")
	public ModelAndView stacurveItemize(String buildingid,HttpServletRequest req) {
		req.setAttribute("buildingid", buildingid);
		//获取此大楼的单位能耗方式
		List<BuildingUnitEntity> list=systemService.findByProperty(BuildingUnitEntity.class, "buildingid", systemService.get(BuildinginfoEntity.class, buildingid).getBuildingid());
		req.setAttribute("list", list);	
		return new ModelAndView("nhjkpt/configmanage/buildingunit/stacurveItemize");
	}
	/**
	 * 获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarItemize")
	@ResponseBody
	public List<Highchart> queryBroswerBarItemize(String unitid,String buildingid,String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return buildingUnitService.queryHighchartItemize(unitid,buildingid,itemizeid,type, startDate, endDate);
	}
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "exportExcel")
	public void exportExcel(String buildingid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response)
			throws IOException {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition","attachment; filename=" + new String("建筑物平均用能".getBytes("GBK"),"ISO-8859-1") + ".xls");
		OutputStream out=null;
		try{
			out = response.getOutputStream();
			buildingUnitService.exportExcel(buildingid, reportType, startDate, endDate, out);
		}catch(Exception e){
		}finally{
			out.close();
		}
	}
}
