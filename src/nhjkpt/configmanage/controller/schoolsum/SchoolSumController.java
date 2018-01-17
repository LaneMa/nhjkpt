package nhjkpt.configmanage.controller.schoolsum;

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
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingshowfunc.BuildingShowFuncEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.schoolsum.SchoolSumEntity;
import nhjkpt.configmanage.service.schoolsum.SchoolSumServiceI;

/**   
 * @Title: Controller
 * @Description: 学校水电气总量计算表
 * @author qf
 * @date 2013-07-31 19:22:49
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/schoolSumController")
public class SchoolSumController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchoolSumController.class);

	@Autowired
	private SchoolSumServiceI schoolSumService;
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
	 * 学校水电气总量计算表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schoolSum")
	public ModelAndView schoolSum(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/schoolsum/schoolSumList");
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
	public void datagrid(SchoolSumEntity schoolSum,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchoolSumEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, schoolSum);
		this.schoolSumService.getDataGridReturn(cq, true);
		List<SchoolSumEntity> schoollist=dataGrid.getReaults();
		List<FuncEntity> funcList = systemService.getList(FuncEntity.class);
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		for(SchoolSumEntity sum:schoollist){
			for (FuncEntity func : funcList) {
				if(CommonUtil.NEWID){
					if(func.getId().equals(sum.getFuncid())){
						sum.setFuncid(func.getFuncname());
					}
				}else{
					if(func.getFuncid().toString().equals(sum.getFuncid())){
						sum.setFuncid(func.getFuncname());
					}
				}
			}
			for (MeterEntity meter : meterList) {
				if(CommonUtil.NEWID){
					if(meter.getId().equals(sum.getMeterid())){
						sum.setMeterid(meter.getMetertext());
					}
				}else{
					if(meter.getMeterid().toString().equals(sum.getMeterid())){
						sum.setMeterid(meter.getMetertext());
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学校水电气总量计算表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SchoolSumEntity schoolSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		schoolSum = systemService.getEntity(SchoolSumEntity.class, schoolSum.getId());
		message = "删除成功";
		schoolSumService.delete(schoolSum);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学校水电气总量计算表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SchoolSumEntity schoolSum, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(schoolSum.getId())) {
			message = "更新成功";
			SchoolSumEntity t = schoolSumService.get(SchoolSumEntity.class, schoolSum.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(schoolSum, t);
				schoolSumService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			schoolSumService.save(schoolSum);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学校水电气总量计算表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SchoolSumEntity schoolSum, HttpServletRequest req) {
		List<FuncEntity> funclist = systemService.getList(FuncEntity.class);
		req.setAttribute("funclist", funclist);
		if (StringUtil.isNotEmpty(schoolSum.getId())) {
			schoolSum = schoolSumService.getEntity(SchoolSumEntity.class, schoolSum.getId());
			req.setAttribute("schoolSumPage", schoolSum);
			if(CommonUtil.NEWID){
				MeterEntity meter=systemService.getEntity(MeterEntity.class, schoolSum.getMeterid());
				req.setAttribute("meterName", meter.getMetertext());
			}else{
				List<MeterEntity> meters=systemService.findByProperty(MeterEntity.class, "meterid", schoolSum.getMeterid());
				if(meters!=null&&meters.size()!=0){
					req.setAttribute("meterName", meters.get(0).getMetertext());
				}
			}
		}
		return new ModelAndView("nhjkpt/configmanage/schoolsum/schoolSum");
	}
	/**
	 * 表具标识号是否唯一
	 * 
	 * @param meterid
	 * @return
	 */
	@RequestMapping(params = "checkMeterid")
	@ResponseBody
	public ValidForm checkMeterid(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String meterid=oConvertUtils.getString(request.getParameter("param"));
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			SchoolSumEntity school=systemService.getEntity(SchoolSumEntity.class, id);
			if(school!=null){
				code=school.getMeterid();
			}
		}
		List<SchoolSumEntity> meters=systemService.findByProperty( SchoolSumEntity.class ,"meterid",meterid);
		if(meters.size()>0&&!code.equals(meterid))
		{
			v.setInfo("此表具标识号已存在");
			v.setStatus("n");
		}
		return v;
	}
	/**
	 * 学校用能总量的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurve")
	public ModelAndView stacurve(HttpServletRequest req) {
		List<FuncEntity> funclist = systemService.findHql(" from FuncEntity where ischeck=? order by funcid", "1");
		req.setAttribute("funclist", funclist);
		return new ModelAndView("nhjkpt/configmanage/schoolsum/stacurve");
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
	public List<Highchart> queryBroswerBar(String funcid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return schoolSumService.queryHighchart(funcid, type, startDate, endDate,"column");
	}
	/**
	 * 学校用能总量的统计曲线
	 * 
	 * @return
	 */
	@RequestMapping(params = "stacurvepie")
	public ModelAndView stacurvepie(HttpServletRequest req) {
		List<FuncEntity> funclist = systemService.getList(FuncEntity.class);
		req.setAttribute("funclist", funclist);
		return new ModelAndView("nhjkpt/configmanage/schoolsum/stacurvepie");
	}
	/**
	 * 获取图形数据
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryBroswerBarpie")
	@ResponseBody
	public List<Highchart> queryBroswerBarpie(String funcid,String type,String startDate,String endDate,HttpServletRequest request,String reportType, HttpServletResponse response) {
		return schoolSumService.queryHighchart(funcid, type, startDate, endDate,"pie");
	}
	/**
	 * 四大分项饼状图
	 * @param funcid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param reportType
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "queryEnergySortCCBar")
	@ResponseBody
	public List<Highchart> queryEnergySortNCBar(HttpServletRequest request,HttpServletResponse response) {
		return schoolSumService.queryEnergySortCCBar();
	}
}
