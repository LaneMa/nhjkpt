package nhjkpt.configmanage.controller.originadata;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

import nhjkpt.configmanage.entity.originadata.OriginadataEntity;
import nhjkpt.configmanage.service.buildingdayitemize.BuildingDayItemizeServiceI;
import nhjkpt.configmanage.service.buildingdaysum.BuildingDaySumServiceI;
import nhjkpt.configmanage.service.buildinghouritemize.BuildingHourItemizeServiceI;
import nhjkpt.configmanage.service.buildinghoursum.BuildingHourSumServiceI;
import nhjkpt.configmanage.service.buildingmonthitemize.BuildingMonthItemizeServiceI;
import nhjkpt.configmanage.service.buildingmonthsum.BuildingMonthSumServiceI;
import nhjkpt.configmanage.service.hourdata.HourdataServiceI;
import nhjkpt.configmanage.service.meterfuncshowhistory.MeterFuncShowHistoryServiceI;
import nhjkpt.configmanage.service.originadata.OriginadataServiceI;
import nhjkpt.configmanage.service.pointdata.PointdataServiceI;
import nhjkpt.configmanage.service.schooldayitemize.SchoolDayItemizeServiceI;
import nhjkpt.configmanage.service.schooldaysum.SchooldaysumServiceI;
import nhjkpt.configmanage.service.schoolhouritemize.SchoolHourItemizeServiceI;
import nhjkpt.configmanage.service.schoolhoursum.SchoolhoursumServiceI;
import nhjkpt.configmanage.service.schoolmonthitemize.SchoolMonthItemizeServiceI;
import nhjkpt.configmanage.service.schoolmonthsum.SchoolMonthSumServiceI;

/**   
 * @Title: Controller
 * @Description: 数据库连接
 * @author qf
 * @date 2013-08-11 01:51:29
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/originadataController")
public class OriginadataController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OriginadataController.class);

	@Autowired
	private OriginadataServiceI originadataService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private MeterFuncShowHistoryServiceI meterFuncShowHistoryService;
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
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 数据库连接列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "originadata")
	public ModelAndView originadata(HttpServletRequest request) {
//		meterFuncShowHistoryService.splitShowStr("0000000001;1;report;20091015043514;1255581314001;dev1;2;1;33.44;2;3.14");
//		if(!CommonUtil.isNull(CommonUtil.START_DATE)&&!CommonUtil.isNull(CommonUtil.END_DATE)&&!CommonUtil.IMPORT){
//    		CommonUtil.IMPORT=true;
//    		Calendar calendar=Calendar.getInstance();//获取当前日期
//        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
//    		try{
//    			Date startD=sdf.parse(CommonUtil.START_DATE);
//    			Date endD=sdf.parse(CommonUtil.END_DATE);
//    			calendar.setTime(startD);
//    			while(calendar.getTime().getTime()<=endD.getTime()){
//    				System.out.println("开始读取配置表数据");
//    		        pointdataService.exportIntegralPointData(calendar);
//    		        System.out.println("开始计算每小时用电量");
//    		        hourdataService.exportPointDataHour(calendar);
//    		        System.out.println("开始计算学校用能分时统计");
//    		        schoolhoursumService.exportHourdata(calendar);
//    		        System.out.println("开始计算大楼用能总量的分天统计");
//    		        buildingHourSumService.exportHourData(calendar);
//    		        System.out.println("开始计算学校用能分类分时统计");
//    		        schoolHourItemizeService.exportHourdata(calendar);
//    		        System.out.println("开始计算建筑物分类分时统计");
//    		        buildingHourItemizeService.exportHourdata(calendar);
//    				if(calendar.getTime().getHours()==23){
//    					System.out.println("开始计算学校用能分天统计");
//    			    	schooldaysumService.exportSchoolhoursum(calendar);
//    			    	System.out.println("开始计算大楼用能总量的分天统计");
//    			    	buildingDaySumService.exportBuildingHourSum(calendar);
//    			    	System.out.println("开始计算学校用能分类分天统计");
//    			    	schoolDayItemizeService.exportSchoolHourItemize(calendar);
//    			    	System.out.println("开始计算建筑物分类分天统计");
//    			    	buildingDayItemizeService.exportBuildingHourItemize(calendar);
//    			    	//判断月末最后一天的23小时
//    			    	if(calendar.getTime().getDate()==calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
//			    			System.out.println("开始计算学校用能分时统计");
//    			        	schoolMonthSumService.exportSchooldaySum(calendar);
//    			        	System.out.println("开始计算大楼用能总量的分天统计");
//    			        	buildingMonthSumService.exportBuildingDaySum(calendar);
//    			        	System.out.println("开始计算学校用能分类分时统计");
//    			        	schoolMonthItemizeService.exportSchooldayItemize(calendar);
//    			        	System.out.println("开始计算建筑物分类分时统计");
//    			        	buildingMonthItemizeService.exportBuildingDayItemize(calendar);
//    			    	}
//    				}
//    				calendar.add(Calendar.HOUR_OF_DAY, 1);
//    			}
//    		}catch(Exception e){
//    			
//    		}
//    		CommonUtil.START_DATE=null;
//    		CommonUtil.END_DATE=null;
//    		CommonUtil.IMPORT=false;
//        }
		return new ModelAndView("nhjkpt/configmanage/originadata/originadataList");
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
	public void datagrid(OriginadataEntity originadata,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OriginadataEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, originadata);
		this.originadataService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除数据库连接
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(OriginadataEntity originadata, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		originadata = systemService.getEntity(OriginadataEntity.class, originadata.getId());
		message = "删除成功";
		originadataService.delete(originadata);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加数据库连接
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(OriginadataEntity originadata, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(originadata.getId())) {
			message = "更新成功";
			OriginadataEntity t = originadataService.get(OriginadataEntity.class, originadata.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(originadata, t);
				originadataService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			originadataService.save(originadata);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 数据库连接列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(OriginadataEntity originadata, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(originadata.getId())) {
			originadata = originadataService.getEntity(OriginadataEntity.class, originadata.getId());
			req.setAttribute("originadataPage", originadata);
		}
		req.setAttribute("databaseList", CommonUtil.listDataBase());
		return new ModelAndView("nhjkpt/configmanage/originadata/originadata");
	}
}
