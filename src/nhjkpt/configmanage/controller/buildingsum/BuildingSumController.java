package nhjkpt.configmanage.controller.buildingsum;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.svg.PDFTranscoder;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.roommanage.RoomEntity;
import nhjkpt.configmanage.entity.schoolsum.SchoolSumEntity;
import nhjkpt.configmanage.service.buildingsum.BuildingSumServiceI;

/**   
 * @Title: Controller
 * @Description: 大楼水电气总量计算表
 * @author qf
 * @date 2013-08-02 00:52:44
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/buildingSumController")
public class BuildingSumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BuildingSumController.class);

	@Autowired
	private BuildingSumServiceI buildingSumService;
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
	 * 大楼水电气总量计算表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "buildingSum")
	public ModelAndView buildingSum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/buildingsum/buildingSumList");
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
	public void datagrid(BuildingSumEntity buildingSum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BuildingSumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, buildingSum);
		this.buildingSumService.getDataGridReturn(cq, true);
		List<BuildingSumEntity> buildinglist=dataGrid.getReaults();
		List<FuncEntity> funcList = systemService.getList(FuncEntity.class);
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		List<BuildinginfoEntity> buildList = systemService.getList(BuildinginfoEntity.class);
		for(BuildingSumEntity building:buildinglist){
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
					if(build.getBuildingid().toString().equals(building.getBuildingid())){
						building.setBuildingid(build.getBuildingname());
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除大楼水电气总量计算表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BuildingSumEntity buildingSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		buildingSum = systemService.getEntity(BuildingSumEntity.class, buildingSum.getId());
		message = "删除成功";
		buildingSumService.delete(buildingSum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加大楼水电气总量计算表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BuildingSumEntity buildingSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(buildingSum.getId())) {
			message = "更新成功";
			BuildingSumEntity t = buildingSumService.get(BuildingSumEntity.class, buildingSum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(buildingSum, t);
				buildingSumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			buildingSumService.save(buildingSum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 大楼水电气总量计算表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BuildingSumEntity buildingSum, HttpServletRequest req) {
		List<BuildinginfoEntity> buildinglist = systemService.getList(BuildinginfoEntity.class);
		req.setAttribute("buildinglist", buildinglist);
		List<FuncEntity> funclist = systemService.getList(FuncEntity.class);
		req.setAttribute("funclist", funclist);
		if (StringUtil.isNotEmpty(buildingSum.getId())) {
			buildingSum = buildingSumService.getEntity(BuildingSumEntity.class, buildingSum.getId());
			req.setAttribute("buildingSumPage", buildingSum);
			if(CommonUtil.NEWID){
				MeterEntity meter=systemService.getEntity(MeterEntity.class, buildingSum.getMeterid());
				req.setAttribute("meterName", meter.getMetertext());
			}else{
				List<MeterEntity> meters=systemService.findByProperty(MeterEntity.class, "meterid", buildingSum.getMeterid());
				if(meters!=null&&meters.size()!=0){
					req.setAttribute("meterName", meters.get(0).getMetertext());
				}
			}
		}
		return new ModelAndView("nhjkpt/configmanage/buildingsum/buildingSum");
	}
	/**
	 * 大楼用能总量的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurve")
	public ModelAndView stacurve(String buildingid,String type,HttpServletRequest req) {
		List<FuncEntity> funclist = systemService.findHql(" from FuncEntity where ischeck=? order by funcid", "1");
		req.setAttribute("funclist", funclist);
		req.setAttribute("buildingid", buildingid);
		req.setAttribute("type", type);
		return new ModelAndView("nhjkpt/configmanage/buildingsum/stacurve");
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
	public List<Highchart> queryBroswerBar(String buildingid,String funcid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		return buildingSumService.queryHighchart(buildingid,funcid, type, startDate, endDate);
	}
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "exportExcel")
	public void exportExcel(String buildingid,String funcid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response)
			throws IOException {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition","attachment; filename=" + new String("建筑物总用能".getBytes("GBK"),"ISO-8859-1") + ".xls");
		OutputStream out=null;
		try{
			out = response.getOutputStream();
			buildingSumService.exportExcel(buildingid, funcid, type, startDate, endDate, out);
		}catch(Exception e){
		}finally{
			out.close();
		}
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
	public void exportReport(String buildingid,String funcid,String startDate,String endDate,String reportType,String tableType,HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition","attachment; filename=" + new String("建筑物用能".getBytes("GBK"),"ISO-8859-1") + ".xls");
		OutputStream out=null;
		try{
			out = response.getOutputStream();
			buildingSumService.exportReport(buildingid, funcid, startDate, endDate, reportType, tableType, out);
		}catch(Exception e){
		}finally{
			out.close();
		}
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
	public List<List<String>> reportData(String buildingid,String funcid,String startDate,String endDate,String reportType,String tableType,HttpServletRequest req) {
		return buildingSumService.queryReportData(buildingid, funcid, startDate, endDate, reportType, tableType);
	}
}
