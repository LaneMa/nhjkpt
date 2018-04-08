package nhjkpt.configmanage.controller.buildingitemize;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.framework.core.common.model.json.ComboTree;
import org.framework.core.common.model.json.DataGrid;
import org.framework.core.common.model.json.Highchart;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.tag.core.easyui.TagUtil;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingitemize.BuildingItemizeEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.roommanage.RoomEntity;
import nhjkpt.configmanage.entity.schoolitemize.SchoolItemizeEntity;
import nhjkpt.configmanage.service.buildingitemize.BuildingItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 建筑分类用能计算用表
 * @author qf
 * @date 2013-08-02 01:08:19
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/buildingItemizeController")
public class BuildingItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BuildingItemizeController.class);

	@Autowired
	private BuildingItemizeServiceI buildingItemizeService;
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
	 * 建筑分类用能计算用表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "buildingItemize")
	public ModelAndView buildingItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/buildingitemize/buildingItemizeList");
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
	public void datagrid(BuildingItemizeEntity buildingItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BuildingItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, buildingItemize);
		this.buildingItemizeService.getDataGridReturn(cq, true);
		List<BuildingItemizeEntity> buildinglist=dataGrid.getReaults();
		List<FuncEntity> funcList = systemService.getList(FuncEntity.class);
		List<ItemizeEntity> itemizeList = systemService.getList(ItemizeEntity.class);
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		List<BuildinginfoEntity> buildList = systemService.getList(BuildinginfoEntity.class);
		for(BuildingItemizeEntity building:buildinglist){
			for (FuncEntity func : funcList) {
				if(CommonUtil.NEWID){
					if(func.getId().equals(building.getFuncid())){
						building.setFuncid(func.getFuncname());
					}
				}else{
					if(func.getFuncid().toString().equals(building.getFuncid())){
						building.setFuncid(func.getFuncname());
					}
				}
			}
			for (ItemizeEntity itemize : itemizeList) {
				if(CommonUtil.NEWID){
					if(itemize.getId().equals(building.getItemizeid())){
						building.setItemizeid(itemize.getItemizetext());
					}
				}else{
					if(itemize.getItemizeid().toString().equals(building.getItemizeid())){
						building.setItemizeid(itemize.getItemizetext());
					}
				}
			}
			for (MeterEntity meter : meterList) {
				if(CommonUtil.NEWID){
					if(meter.getId().equals(building.getMeterid())){
						building.setMeterid(meter.getMetertext());
					}
				}else{
					if(meter.getMeterid().toString().equals(building.getMeterid())){
						building.setMeterid(meter.getMetertext());
					}
				}
			}
			for (BuildinginfoEntity build : buildList) {
				if(CommonUtil.NEWID){
					if(build.getId().equals(building.getBuildingid())){
						building.setBuildingid(build.getBuildingname());
					}
				}else{
					if(build.getBuildingid().equals(building.getBuildingid())){
						building.setBuildingid(build.getBuildingname());
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除建筑分类用能计算用表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BuildingItemizeEntity buildingItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		buildingItemize = systemService.getEntity(BuildingItemizeEntity.class, buildingItemize.getId());
		message = "删除成功";
		buildingItemizeService.delete(buildingItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加建筑分类用能计算用表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BuildingItemizeEntity buildingItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(buildingItemize.getId())) {
			message = "更新成功";
			BuildingItemizeEntity t = buildingItemizeService.get(BuildingItemizeEntity.class, buildingItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(buildingItemize, t);
				buildingItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			buildingItemizeService.save(buildingItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 建筑分类用能计算用表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BuildingItemizeEntity buildingItemize, HttpServletRequest req) {
		List<BuildinginfoEntity> buildinglist = systemService.getList(BuildinginfoEntity.class);
		req.setAttribute("buildinglist", buildinglist);
		List<FuncEntity> funclist = systemService.getList(FuncEntity.class);
		req.setAttribute("funclist", funclist);
		if (StringUtil.isNotEmpty(buildingItemize.getId())) {
			buildingItemize = buildingItemizeService.getEntity(BuildingItemizeEntity.class, buildingItemize.getId());
			req.setAttribute("buildingItemizePage", buildingItemize);
			if(CommonUtil.NEWID){
				MeterEntity meter=systemService.getEntity(MeterEntity.class, buildingItemize.getMeterid());
				req.setAttribute("meterName", meter.getMetertext());
				ItemizeEntity itemize=systemService.getEntity(ItemizeEntity.class, buildingItemize.getItemizeid());
				req.setAttribute("itemizeName", itemize.getItemizetext());
			}else{
				List<MeterEntity> meters=systemService.findByProperty(MeterEntity.class, "meterid", buildingItemize.getMeterid());
				if(meters!=null&&meters.size()!=0){
					req.setAttribute("meterName", meters.get(0).getMetertext());
				}
				try{
					List<ItemizeEntity> itemizes=systemService.findByProperty(ItemizeEntity.class,"itemizeid", Integer.parseInt(buildingItemize.getItemizeid()));
					req.setAttribute("itemizeName", itemizes.get(0).getItemizetext());
				}catch(Exception e){
					
				}
			}
		}
		return new ModelAndView("nhjkpt/configmanage/buildingitemize/buildingItemize");
	}
	/**
	 * 学校用能总量的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurve")
	public ModelAndView stacurve(String buildingid,String type,HttpServletRequest req) {
		List<FuncEntity> funclist = systemService.getList(FuncEntity.class);
		req.setAttribute("funclist", funclist);
		req.setAttribute("buildingid", buildingid);
		req.setAttribute("type", type);
		return new ModelAndView("nhjkpt/configmanage/buildingitemize/stacurve");
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
	public List<Highchart> queryBroswerBar(String buildingid,String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return buildingItemizeService.queryHighchart(buildingid,itemizeid, type, startDate, endDate);
	}
	/**
	 * 学校用能总量按分类进行统计比较
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurveItemize")
	public ModelAndView stacurveItemize(HttpServletRequest req) {
		List<BuildinginfoEntity> buildinglist=systemService.getList(BuildinginfoEntity.class);
		req.setAttribute("buildinglist", buildinglist);
		return new ModelAndView("nhjkpt/configmanage/buildingitemize/stacurveItemize");
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
	public List<Highchart> queryBroswerBarItemize(String buildingid,String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return buildingItemizeService.queryHighchartItemize(buildingid,itemizeid, type, startDate, endDate);
	}
	/**
	 * 学校用能总量按分类进行统计比较
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurveBuilding")
	public ModelAndView stacurveBuilding(HttpServletRequest req) {
		return new ModelAndView("nhjkpt/configmanage/buildingitemize/stacurveBuilding");
	}
	
	/**
	 * 学校用能总量按分类进行统计比较
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurveBuildingsum")
	public ModelAndView stacurveBuildingsum(HttpServletRequest req) {
		return new ModelAndView("nhjkpt/configmanage/buildingitemize/stacurveBuildingsum");
	}
	/**
	 * 获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarBuilding")
	@ResponseBody
	public List<Highchart> queryBroswerBarBuilding(String buildingid,String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return buildingItemizeService.queryHighchartBuilding(buildingid,itemizeid, type, startDate, endDate);
	}
	
	/**
	 * 获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarBuildingsum")
	@ResponseBody
	public List<Highchart> queryBroswerBarBuildingsum(String buildingid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return buildingItemizeService.queryHighchartBuildingsum(buildingid, type, startDate, endDate);
	}
	/**
	 * 学校用能总量按分类进行统计比较
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurveDate")
	public ModelAndView stacurveDate(HttpServletRequest req) {
		List<BuildinginfoEntity> buildinglist=systemService.getList(BuildinginfoEntity.class);
		req.setAttribute("buildinglist", buildinglist);
		return new ModelAndView("nhjkpt/configmanage/buildingitemize/stacurveDate");
	}
	/**
	 * 获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarDate")
	@ResponseBody
	public List<Highchart> queryBroswerBarDate(String buildingid,String itemizeid,String type,String[] startDate,String[] endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return buildingItemizeService.queryHighchartDate(buildingid,itemizeid, type, startDate, endDate);
	}
	/**
	 * 打开饼状图页面
	 * @param buildingid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "stacurvepie")
	public ModelAndView stacurvepie(String buildingid,HttpServletRequest req) {
		List<ItemizeEntity> itemizelist = systemService.getList(ItemizeEntity.class);
		req.setAttribute("itemizelist", itemizelist);
		req.setAttribute("buildingid", buildingid);
		return new ModelAndView("nhjkpt/configmanage/buildingitemize/stacurvepie");
	}
	/**
	 * 获取饼状图数据
	 * @param buildingid
	 * @param itemizeid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarpie")
	@ResponseBody
	public List<Highchart> queryBroswerBarpie(String buildingid,String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return buildingItemizeService.queryHighchartpie(buildingid,itemizeid, type, startDate, endDate);
	}
	
	/**
	 * 打开饼状图页面
	 * @param buildingid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "stacurveItemizePie")
	public ModelAndView stacurveItemizePie(String buildingid,HttpServletRequest req) {
		List<ItemizeEntity> list = systemService.findHql(" from ItemizeEntity where itemize is null");
		req.setAttribute("list", list);
		req.setAttribute("buildingid", buildingid);
		return new ModelAndView("nhjkpt/configmanage/buildingitemize/stacurveItemizePie");
	}
	/**
	 * 获取饼状图数据
	 * @param buildingid
	 * @param itemizeid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarItemizePie")
	@ResponseBody
	public List<Highchart> queryBroswerBarItemizePie(String buildingid,String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		
		if(CommonUtil.isNull(startDate)){
			Calendar calendar=Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			startDate=sdf.format(calendar.getTime())+"-01";
		}
		
		if(CommonUtil.isNull(endDate)){
			Calendar calendar=Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			endDate=sdf.format(calendar.getTime())+"-"+calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		
		
		return buildingItemizeService.queryHighchartItemizePie(buildingid,itemizeid, type, startDate, endDate);
	}
	/**
	 * 左侧对比列表
	 * @param buildingid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "treeContrast")
	@ResponseBody
	public List<ComboTree> treeContrast(HttpServletRequest req) {
		List<ComboTree> comboTrees=new ArrayList<ComboTree>();
		ComboTree comboTree=new ComboTree();
		comboTree.setId("conrastdate");
		comboTree.setText("同分类同大楼不同时间");
		comboTrees.add(comboTree);
		comboTree=new ComboTree();
		comboTree.setId("conrastitemize");
		comboTree.setText("同时间同大楼不同分类");
		comboTrees.add(comboTree);
		comboTree=new ComboTree();
		comboTree.setId("conrastbuilding");
		comboTree.setText("同时间同分类不同大楼");
		comboTrees.add(comboTree);
		comboTree=new ComboTree();
		comboTree.setId("conrastbuildingsum");
		comboTree.setText("同时间不同大楼总量对比");
		comboTrees.add(comboTree);
		req.setAttribute("contrast", comboTrees);
		return comboTrees;
	}
	/**
	 * 打开相应的对比页面
	 * @param buildingid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "contrasthome")
	public ModelAndView contrasthome(String id,HttpServletRequest req) {
		List<ItemizeEntity> itemizelist = systemService.getList(ItemizeEntity.class);
		req.setAttribute("itemizelist", itemizelist);
		req.setAttribute("id", id);
		return new ModelAndView("nhjkpt/configmanage/buildingitemize/contrasthome");
	}
	/**
	 * 大楼主页中根据大楼id和分类
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "stacurveItemizeByBuildingid")
	public ModelAndView stacurveItemizeByBuildingid(String buildingid,HttpServletRequest req) {
		req.setAttribute("buildingid", buildingid);
		return new ModelAndView("nhjkpt/configmanage/buildingitemize/stacurveItemizeByBuildingid");
	}
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "exportExcel")
	public void exportExcel(String buildingid,String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response)
			throws IOException {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition","attachment; filename=" + new String("建筑物分类用能".getBytes("GBK"),"ISO-8859-1") + ".xls");
		OutputStream out=null;
		try{
			out = response.getOutputStream();
			buildingItemizeService.exportExcel(buildingid, itemizeid, reportType, startDate, endDate, out);
		}catch(Exception e){
			
		}finally{
			out.close();
		}
	}
}
