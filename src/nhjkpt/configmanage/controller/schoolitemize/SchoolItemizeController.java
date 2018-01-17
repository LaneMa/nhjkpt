package nhjkpt.configmanage.controller.schoolitemize;

import java.util.ArrayList;
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
import org.framework.tag.vo.easyui.ComboTreeModel;

import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.buildingitemize.BuildingItemizeEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.schoolitemize.SchoolItemizeEntity;
import nhjkpt.configmanage.service.schoolitemize.SchoolItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 学校分类用能计算用表
 * @author qf
 * @date 2013-08-02 01:00:14
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/schoolItemizeController")
public class SchoolItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchoolItemizeController.class);

	@Autowired
	private SchoolItemizeServiceI schoolItemizeService;
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
	 * 学校分类用能计算用表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schoolItemize")
	public ModelAndView schoolItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/schoolitemize/schoolItemizeList");
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
	public void datagrid(SchoolItemizeEntity schoolItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchoolItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, schoolItemize);
		this.schoolItemizeService.getDataGridReturn(cq, true);
		List<SchoolItemizeEntity> schoollist=dataGrid.getReaults();
		List<FuncEntity> funcList = systemService.getList(FuncEntity.class);
		List<ItemizeEntity> itemizeList = systemService.getList(ItemizeEntity.class);
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		for(SchoolItemizeEntity school:schoollist){
			for (FuncEntity func : funcList) {
				if(CommonUtil.NEWID){
					if(func.getId().equals(school.getFuncid())){
						school.setFuncid(func.getFuncname());
					}
				}else{
					if(func.getFuncid().toString().equals(school.getFuncid())){
						school.setFuncid(func.getFuncname());
					}
				}
			}
			for (ItemizeEntity itemize : itemizeList) {
				if(CommonUtil.NEWID){
					if(itemize.getId().equals(school.getItemizeid())){
						school.setItemizeid(itemize.getItemizetext());
					}
				}else{
					if(itemize.getItemizeid().toString().equals(school.getItemizeid())){
						school.setItemizeid(itemize.getItemizetext());
					}
				}
			}
			for (MeterEntity meter : meterList) {
				if(CommonUtil.NEWID){
					if(meter.getId().equals(school.getMeterid())){
						school.setMeterid(meter.getMetertext());
					}
				}else{
					if(meter.getMeterid().toString().equals(school.getMeterid())){
						school.setMeterid(meter.getMetertext());
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学校分类用能计算用表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SchoolItemizeEntity schoolItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		schoolItemize = systemService.getEntity(SchoolItemizeEntity.class, schoolItemize.getId());
		message = "删除成功";
		schoolItemizeService.delete(schoolItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学校分类用能计算用表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SchoolItemizeEntity schoolItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(schoolItemize.getId())) {
			message = "更新成功";
			SchoolItemizeEntity t = schoolItemizeService.get(SchoolItemizeEntity.class, schoolItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(schoolItemize, t);
				schoolItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			schoolItemizeService.save(schoolItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学校分类用能计算用表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SchoolItemizeEntity schoolItemize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(schoolItemize.getId())) {
			schoolItemize = schoolItemizeService.getEntity(SchoolItemizeEntity.class, schoolItemize.getId());
			req.setAttribute("schoolItemizePage", schoolItemize);
			if(CommonUtil.NEWID){
				MeterEntity meter=systemService.getEntity(MeterEntity.class, schoolItemize.getMeterid());
				req.setAttribute("meterName", meter.getMetertext());
				ItemizeEntity itemize=systemService.getEntity(ItemizeEntity.class, schoolItemize.getItemizeid());
				req.setAttribute("itemizeName", itemize.getItemizetext());
			}else{
				List<MeterEntity> meters=systemService.findByProperty(MeterEntity.class, "meterid", schoolItemize.getMeterid());
				if(meters!=null&&meters.size()!=0){
					req.setAttribute("meterName", meters.get(0).getMetertext());
				}
				try{
					List<ItemizeEntity> itemizes=systemService.findByProperty(ItemizeEntity.class,"itemizeid", Integer.parseInt(schoolItemize.getItemizeid()));
					req.setAttribute("itemizeName", itemizes.get(0).getItemizetext());
				}catch(Exception e){
					
				}
			}
		}
		List<FuncEntity> funclist = systemService.getList(FuncEntity.class);
		req.setAttribute("funclist", funclist);
		return new ModelAndView("nhjkpt/configmanage/schoolitemize/schoolItemize");
	}
	/**
	 * 学校用能总量的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurve")
	public ModelAndView stacurve(HttpServletRequest req) {
		return new ModelAndView("nhjkpt/configmanage/schoolitemize/stacurve");
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
		return schoolItemizeService.queryHighchart(itemizeid, type, startDate, endDate);
	}
	/**
	 * 学校首页显示学校分类用能
	 * @param itemizeid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "stacurveItemize")
	public ModelAndView stacurveItemize(String itemizeid,HttpServletRequest req) {
		req.setAttribute("itemizeid", itemizeid);
		return new ModelAndView("nhjkpt/configmanage/schoolitemize/stacurveItemize");
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
	public List<Highchart> queryBroswerBarItemize(String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return schoolItemizeService.queryHighchartItemize(itemizeid, type, startDate, endDate,"column");
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
	public List<Highchart> queryBroswerBarDate(String itemizeid,String type,String[] startDate,String[] endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return schoolItemizeService.queryHighchartDate(itemizeid, type, startDate, endDate);
	}
	/**
	 * 学校首页显示学校分类用能
	 * @param itemizeid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "stacurvePie")
	public ModelAndView stacurvePie(String itemizeid,HttpServletRequest req) {
		req.setAttribute("itemizeid", itemizeid);
		return new ModelAndView("nhjkpt/configmanage/schoolitemize/stacurvePie");
	}
	/**
	 * 获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarPie")
	@ResponseBody
	public List<Highchart> queryBroswerBarPie(String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return schoolItemizeService.queryHighchartItemize(itemizeid, type, startDate, endDate,"pie");
	}
	/**
	 * 学校首页面饼状图，默认取第一个分类
	 * @param itemizeid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "stacurveSchoolPie")
	public ModelAndView stacurveSchoolPie(HttpServletRequest req) {
		return new ModelAndView("nhjkpt/configmanage/schoolitemize/stacurveSchoolPie");
	}
	/**
	 * 获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarSchoolPie")
	@ResponseBody
	public List<Highchart> queryBroswerBarSchoolPie(String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		if(CommonUtil.isNull(itemizeid)){
			List<ItemizeEntity> list=systemService.getList(ItemizeEntity.class);
			if(list!=null&&list.size()!=0){
				itemizeid=list.get(0).getId();
			}
		}
		return schoolItemizeService.queryHighchartItemize(itemizeid, type, startDate, endDate,"pie");
	}
	/**
	 * 学校首页面饼状图，默认取第一个分类
	 * @param itemizeid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "stacurveSchoolItemizePie")
	public ModelAndView stacurveSchoolItemizePie(HttpServletRequest req) {
		List<ItemizeEntity> list=systemService.findHql(" from ItemizeEntity where itemize is null");
		req.setAttribute("list", list);
		return new ModelAndView("nhjkpt/configmanage/schoolitemize/stacurveSchoolItemizePie");
	}
	/**
	 * 获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarSchoolItemizePie")
	@ResponseBody
	public List<Highchart> queryBroswerBarSchoolItemizePie(String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return schoolItemizeService.queryHighchartItemizePie(itemizeid, type, startDate, endDate);
	}
	@RequestMapping(params = "treeItemize")
	@ResponseBody
	public List<ComboTree> treeItemize(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(ItemizeEntity.class);
		if (comboTree.getId() != null) {
			cq.eq("itemize.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("itemize");
		}

		cq.add();
		List<ItemizeEntity> itemizeList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "itemizetext", "itemizes");
		comboTrees = systemService.ComboTree(itemizeList, comboTreeModel,null);
		return comboTrees;
	}
}
