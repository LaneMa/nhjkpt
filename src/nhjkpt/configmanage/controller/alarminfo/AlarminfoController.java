package nhjkpt.configmanage.controller.alarminfo;
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
import nhjkpt.system.pojo.base.TSDepart;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.alarminfo.AlarminfoEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.schoolshowfunc.SchoolShowFuncEntity;
import nhjkpt.configmanage.service.alarminfo.AlarminfoServiceI;

/**   
 * @Title: Controller
 * @Description: 报警信息列表
 * @author zhangdaihao
 * @date 2013-07-21 00:20:34
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/alarminfoController")
public class AlarminfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AlarminfoController.class);

	@Autowired
	private AlarminfoServiceI alarminfoService;
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
	 * 报警信息列表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "alarminfo")
	public ModelAndView alarminfo(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/alarminfo/alarminfoList");
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
	public void datagrid(AlarminfoEntity alarminfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(AlarminfoEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, alarminfo);
		this.alarminfoService.getDataGridReturn(cq, true);
		List<AlarminfoEntity> alarmlist=dataGrid.getReaults();
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		for(AlarminfoEntity alarm:alarmlist){
			for(MeterEntity meter:meterList){
				if(CommonUtil.NEWID){
					if(meter.getId().equals(alarm.getMeterid())){
						alarm.setMeterid("("+meter.getMeterid()+")"+meter.getMetertext());
					}
				}else{
					if(meter.getMeterid().equals(alarm.getMeterid())){
						alarm.setMeterid("("+meter.getMeterid()+")"+meter.getMetertext());
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除报警信息列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(AlarminfoEntity alarminfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		alarminfo = systemService.getEntity(AlarminfoEntity.class, alarminfo.getId());
		message = "删除成功";
		alarminfoService.delete(alarminfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}
	/**
	 * 删除所有报警信息
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "deleteAll")
	@ResponseBody
	public AjaxJson deleteAll(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		systemService.executeSql("DELETE FROM alarminfo");
		List<MeterEntity> list=systemService.findByQueryString(" from MeterEntity where alarm='0'");
		for(MeterEntity meter:list){
			systemService.delete(meter);
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加报警信息列表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(AlarminfoEntity alarminfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(alarminfo.getId())) {
			message = "更新成功";
			AlarminfoEntity t = alarminfoService.get(AlarminfoEntity.class, alarminfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(alarminfo, t);
				alarminfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			alarminfoService.save(alarminfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 报警信息列表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(AlarminfoEntity alarminfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(alarminfo.getId())) {
			alarminfo = alarminfoService.getEntity(AlarminfoEntity.class, alarminfo.getId());
			req.setAttribute("alarminfoPage", alarminfo);
//获取meterid对应的meter对象
			MeterEntity meter=null;
			if(CommonUtil.NEWID){
				meter=systemService.getEntity(MeterEntity.class, alarminfo.getMeterid());
			}else{
				List<MeterEntity> list=systemService.findHql(" from MeterEntity where meterid=?", alarminfo.getMeterid());
				if(!CommonUtil.isNull(list)){
					meter=list.get(0);
				}
			}
			if(meter!=null){
				req.setAttribute("meterName", meter.getMetertext());	
			}
		}
		return new ModelAndView("nhjkpt/configmanage/alarminfo/alarminfo");
	}
	/**
	 * 打开瞬时信息页面
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "openAlarm")
	public ModelAndView openAlarm(String id,HttpServletRequest req) {
		req.setAttribute("id", id);
		return new ModelAndView("nhjkpt/configmanage/alarminfo/openAlarm");
	}
	/**
	 * 获取报警信息
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "alarminfoByid")
	public ModelAndView alarminfoByid(String id,HttpServletRequest request) {
		request.setAttribute("id", id);
		return new ModelAndView("nhjkpt/configmanage/alarminfo/alarminfoListByid");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagridByid")
	public void datagridByid(String id,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		AlarminfoEntity alarminfo=new AlarminfoEntity();
		alarminfo.setMeterid(systemService.get(MeterEntity.class, id).getMeterid());
		CriteriaQuery cq = new CriteriaQuery(AlarminfoEntity.class, dataGrid);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("alarmtime", "desc");
		cq.setOrder(map);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, alarminfo);
		this.alarminfoService.getDataGridReturn(cq, true);
		List<AlarminfoEntity> alarmlist=dataGrid.getReaults();
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		for(AlarminfoEntity alarm:alarmlist){
			for(MeterEntity meter:meterList){
				if(meter.getMeterid().equals(alarm.getMeterid())){
					alarm.setMeterid("("+meter.getMeterid()+")"+meter.getMetertext());
				}
			}
			if(alarm.getType().equals("0")){
				alarm.setType("报警");
			}else{
				alarm.setType("断线");
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}
}
