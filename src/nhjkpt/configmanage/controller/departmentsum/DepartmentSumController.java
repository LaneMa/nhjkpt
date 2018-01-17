package nhjkpt.configmanage.controller.departmentsum;

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
import nhjkpt.configmanage.entity.buildingsum.BuildingSumEntity;
import nhjkpt.configmanage.entity.departmentinfo.DepartmentinfoEntity;
import nhjkpt.configmanage.entity.departmentsum.DepartmentSumEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.service.departmentsum.DepartmentSumServiceI;

/**   
 * @Title: Controller
 * @Description: 系水气电总量计算表
 * @author qf
 * @date 2014-11-19 00:11:11
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/departmentSumController")
public class DepartmentSumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartmentSumController.class);

	@Autowired
	private DepartmentSumServiceI departmentSumService;
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
	 * 系水气电总量计算表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "departmentSum")
	public ModelAndView departmentSum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/departmentsum/departmentSumList");
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
	public void datagrid(DepartmentSumEntity departmentSum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DepartmentSumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, departmentSum);
		this.departmentSumService.getDataGridReturn(cq, true);
		List<DepartmentSumEntity> departmentlist=dataGrid.getReaults();
		List<FuncEntity> funcList = systemService.getList(FuncEntity.class);
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		List<DepartmentinfoEntity> departList = systemService.getList(DepartmentinfoEntity.class);
		for(DepartmentSumEntity department:departmentlist){
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
					if(depart.getDepartmentid().toString().equals(department.getDepartmentid())){
						department.setDepartmentid(depart.getDepartmentname());
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除系水气电总量计算表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DepartmentSumEntity departmentSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		departmentSum = systemService.getEntity(DepartmentSumEntity.class, departmentSum.getId());
		message = "删除成功";
		departmentSumService.delete(departmentSum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加系水气电总量计算表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DepartmentSumEntity departmentSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(departmentSum.getId())) {
			message = "更新成功";
			DepartmentSumEntity t = departmentSumService.get(DepartmentSumEntity.class, departmentSum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(departmentSum, t);
				departmentSumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			departmentSumService.save(departmentSum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 系水气电总量计算表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DepartmentSumEntity departmentSum, HttpServletRequest req) {
		List<DepartmentinfoEntity> departmentlist = systemService.getList(DepartmentinfoEntity.class);
		req.setAttribute("departmentlist", departmentlist);
		List<FuncEntity> funclist = systemService.getList(FuncEntity.class);
		req.setAttribute("funclist", funclist);
		if (StringUtil.isNotEmpty(departmentSum.getId())) {
			departmentSum = departmentSumService.getEntity(DepartmentSumEntity.class, departmentSum.getId());
			req.setAttribute("departmentSumPage", departmentSum);
			if(CommonUtil.NEWID){
				MeterEntity meter=systemService.getEntity(MeterEntity.class, departmentSum.getMeterid());
				req.setAttribute("meterName", meter.getMetertext());
			}else{
				List<MeterEntity> meters=systemService.findByProperty(MeterEntity.class, "meterid", departmentSum.getMeterid());
				if(meters!=null&&meters.size()!=0){
					req.setAttribute("meterName", meters.get(0).getMetertext());
				}
			}
		}
		return new ModelAndView("nhjkpt/configmanage/departmentsum/departmentSum");
	}
	
	/**
	 * 分户用能总量的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurve")
	public ModelAndView stacurve(String departmentid,String type,HttpServletRequest req) {
		List<FuncEntity> funclist = systemService.findHql(" from FuncEntity where ischeck=? order by funcid", "1");
		req.setAttribute("funclist", funclist);
		req.setAttribute("departmentid", departmentid);
		req.setAttribute("type", type);
		return new ModelAndView("nhjkpt/configmanage/departmentsum/stacurve");
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
	public List<Highchart> queryBroswerBar(String departmentid,String funcid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		return departmentSumService.queryHighchart(departmentid,funcid, type, startDate, endDate);
	}
	/**
	 * 查询报表数据
	 * @param buildingid
	 * @param funcid
	 * @param startDate
	 * @param endDate
	 * @param reportType
	 * @param tableType
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "reportData")
	@ResponseBody
	public List<List<String>> reportData(String departmentid,String funcid,String startDate,String endDate,String reportType,String tableType,HttpServletRequest req) {
		return departmentSumService.queryReportData(departmentid, funcid, startDate, endDate, reportType, tableType);
	}
	/**
	 * 导出报表
	 * @param buildingid
	 * @param funcid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param reportType
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "exportReport")
	public void exportReport(String departmentid,String funcid,String startDate,String endDate,String reportType,String tableType,HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition","attachment; filename=" + new String("分户用能".getBytes("GBK"),"ISO-8859-1") + ".xls");
		OutputStream out=null;
		try{
			out = response.getOutputStream();
			departmentSumService.exportReport(departmentid, funcid, startDate, endDate, reportType, tableType, out);
		}catch(Exception e){
		}finally{
			out.close();
		}
	}
}
