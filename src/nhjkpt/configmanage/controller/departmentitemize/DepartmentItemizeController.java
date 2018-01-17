package nhjkpt.configmanage.controller.departmentitemize;

import java.text.SimpleDateFormat;
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
import org.framework.core.common.model.json.DataGrid;
import org.framework.core.common.model.json.Highchart;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.tag.core.easyui.TagUtil;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.departmentinfo.DepartmentinfoEntity;
import nhjkpt.configmanage.entity.departmentitemize.DepartmentItemizeEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.service.departmentitemize.DepartmentItemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 系分类用能计算用表
 * @author qf
 * @date 2014-11-18 23:40:21
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/departmentItemizeController")
public class DepartmentItemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartmentItemizeController.class);

	@Autowired
	private DepartmentItemizeServiceI departmentItemizeService;
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
	 * 系分类用能计算用表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "departmentItemize")
	public ModelAndView departmentItemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/departmentitemize/departmentItemizeList");
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
	public void datagrid(DepartmentItemizeEntity departmentItemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DepartmentItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, departmentItemize);
		this.departmentItemizeService.getDataGridReturn(cq, true);
		List<DepartmentItemizeEntity> departmentlist=dataGrid.getReaults();
		List<FuncEntity> funcList = systemService.getList(FuncEntity.class);
		List<ItemizeEntity> itemizeList = systemService.getList(ItemizeEntity.class);
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		List<DepartmentinfoEntity> departList = systemService.getList(DepartmentinfoEntity.class);
		for(DepartmentItemizeEntity department:departmentlist){
			for (FuncEntity func : funcList) {
				if(CommonUtil.NEWID){
					if(func.getId().equals(department.getFuncid())){
						department.setFuncid(func.getFuncname());
					}
				}else{
					if(func.getFuncid().toString().equals(department.getFuncid())){
						department.setFuncid(func.getFuncname());
					}
				}
			}
			for (ItemizeEntity itemize : itemizeList) {
				if(CommonUtil.NEWID){
					if(itemize.getId().equals(department.getItemizeid())){
						department.setItemizeid(itemize.getItemizetext());
					}
				}else{
					if(itemize.getItemizeid().toString().equals(department.getItemizeid())){
						department.setItemizeid(itemize.getItemizetext());
					}
				}
			}
			for (MeterEntity meter : meterList) {
				if(CommonUtil.NEWID){
					if(meter.getId().equals(department.getMeterid())){
						department.setMeterid(meter.getMetertext());
					}
				}else{
					if(meter.getMeterid().toString().equals(department.getMeterid())){
						department.setMeterid(meter.getMetertext());
					}
				}
			}
			for (DepartmentinfoEntity depart : departList) {
				if(CommonUtil.NEWID){
					if(depart.getId().equals(department.getDepartmentid())){
						department.setDepartmentid(depart.getDepartmentname());
					}
				}else{
					if(depart.getDepartmentid().equals(department.getDepartmentid())){
						department.setDepartmentid(depart.getDepartmentname());
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除系分类用能计算用表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DepartmentItemizeEntity departmentItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		departmentItemize = systemService.getEntity(DepartmentItemizeEntity.class, departmentItemize.getId());
		message = "删除成功";
		departmentItemizeService.delete(departmentItemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加系分类用能计算用表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DepartmentItemizeEntity departmentItemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(departmentItemize.getId())) {
			message = "更新成功";
			DepartmentItemizeEntity t = departmentItemizeService.get(DepartmentItemizeEntity.class, departmentItemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(departmentItemize, t);
				departmentItemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			departmentItemizeService.save(departmentItemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 系分类用能计算用表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DepartmentItemizeEntity departmentItemize, HttpServletRequest req) {
		List<DepartmentinfoEntity> departmentlist = systemService.getList(DepartmentinfoEntity.class);
		req.setAttribute("departmentlist", departmentlist);
		List<FuncEntity> funclist = systemService.getList(FuncEntity.class);
		req.setAttribute("funclist", funclist);
		if (StringUtil.isNotEmpty(departmentItemize.getId())) {
			departmentItemize = departmentItemizeService.getEntity(DepartmentItemizeEntity.class, departmentItemize.getId());
			req.setAttribute("departmentItemizePage", departmentItemize);
			if(CommonUtil.NEWID){
				MeterEntity meter=systemService.getEntity(MeterEntity.class, departmentItemize.getMeterid());
				req.setAttribute("meterName", meter.getMetertext());
				ItemizeEntity itemize=systemService.getEntity(ItemizeEntity.class, departmentItemize.getItemizeid());
				req.setAttribute("itemizeName", itemize.getItemizetext());
			}else{
				List<MeterEntity> meters=systemService.findByProperty(MeterEntity.class, "meterid", departmentItemize.getMeterid());
				if(meters!=null&&meters.size()!=0){
					req.setAttribute("meterName", meters.get(0).getMetertext());
				}
				try{
					List<ItemizeEntity> itemizes=systemService.findByProperty(ItemizeEntity.class,"itemizeid", Integer.parseInt(departmentItemize.getItemizeid()));
					req.setAttribute("itemizeName", itemizes.get(0).getItemizetext());
				}catch(Exception e){
					
				}
			}
		}
		return new ModelAndView("nhjkpt/configmanage/departmentitemize/departmentItemize");
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
	public List<Highchart> queryBroswerBarItemize(String departmentid,String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return departmentItemizeService.queryHighchartItemize(departmentid,itemizeid, type, startDate, endDate);
	}
	
	/**
	 * 分户主页中根据大楼id和分类
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "stacurveItemizeByDepartmentid")
	public ModelAndView stacurveItemizeByDepartmentid(String departmentid,HttpServletRequest req) {
		req.setAttribute("departmentid", departmentid);
		return new ModelAndView("nhjkpt/configmanage/departmentitemize/stacurveItemizeByDepartmentid");
	}
	
	/**
	 * 打开饼状图页面
	 * @param departmentid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "stacurveItemizePie")
	public ModelAndView stacurveItemizePie(String departmentid,HttpServletRequest req) {
		List<ItemizeEntity> list = systemService.findHql(" from ItemizeEntity where itemize is null");
		req.setAttribute("list", list);
		req.setAttribute("departmentid", departmentid);
		return new ModelAndView("nhjkpt/configmanage/departmentitemize/stacurveItemizePie");
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
	public List<Highchart> queryBroswerBarItemizePie(String departmentid,String itemizeid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		
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
		
		
		return departmentItemizeService.queryHighchartItemizePie(departmentid,itemizeid, type, startDate, endDate);
	}
}
