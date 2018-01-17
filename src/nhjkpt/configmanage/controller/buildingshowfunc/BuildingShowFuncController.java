package nhjkpt.configmanage.controller.buildingshowfunc;

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
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.tag.core.easyui.TagUtil;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingitemize.BuildingItemizeEntity;
import nhjkpt.configmanage.entity.buildingshowfunc.BuildingShowFuncEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.roommanage.RoomEntity;
import nhjkpt.configmanage.service.buildingshowfunc.BuildingShowFuncServiceI;

/**   
 * @Title: Controller
 * @Description: 大楼需要展示的瞬时功能
 * @author qf
 * @date 2013-08-02 00:44:43
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/buildingShowFuncController")
public class BuildingShowFuncController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BuildingShowFuncController.class);

	@Autowired
	private BuildingShowFuncServiceI buildingShowFuncService;
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
	 * 大楼需要展示的瞬时功能列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "buildingShowFunc")
	public ModelAndView buildingShowFunc(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/buildingshowfunc/buildingShowFuncList");
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
	public void datagrid(BuildingShowFuncEntity buildingShowFunc,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BuildingShowFuncEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, buildingShowFunc);
		this.buildingShowFuncService.getDataGridReturn(cq, true);
		List<BuildingShowFuncEntity> buildinglist=dataGrid.getReaults();
		List<FuncEntity> funcList = systemService.getList(FuncEntity.class);
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		List<BuildinginfoEntity> buildList = systemService.getList(BuildinginfoEntity.class);
		for(BuildingShowFuncEntity building:buildinglist){
			for (FuncEntity func : funcList) {
				if(func.getFuncid().toString().equals(building.getFuncid())){
					building.setFuncid(func.getFuncname());
				}
			}
			for (MeterEntity meter : meterList) {
				if(meter.getMeterid().toString().equals(building.getMeterid())){
					building.setMeterid(meter.getMetertext());
				}
			}
			for (BuildinginfoEntity build : buildList) {
				if(build.getBuildingid().equals(building.getBuildingid())){
					building.setBuildingid(build.getBuildingname());
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除大楼需要展示的瞬时功能
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BuildingShowFuncEntity buildingShowFunc, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		buildingShowFunc = systemService.getEntity(BuildingShowFuncEntity.class, buildingShowFunc.getId());
		message = "删除成功";
		buildingShowFuncService.delete(buildingShowFunc);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加大楼需要展示的瞬时功能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BuildingShowFuncEntity buildingShowFunc, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(buildingShowFunc.getId())) {
			message = "更新成功";
			BuildingShowFuncEntity t = buildingShowFuncService.get(BuildingShowFuncEntity.class, buildingShowFunc.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(buildingShowFunc, t);
				buildingShowFuncService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			buildingShowFuncService.save(buildingShowFunc);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 大楼需要展示的瞬时功能列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BuildingShowFuncEntity buildingShowFunc, HttpServletRequest req) {
		List<RoomEntity> buildinglist = systemService.getList(BuildinginfoEntity.class);
		req.setAttribute("buildinglist", buildinglist);
		List<FuncEntity> funclist = systemService.getList(FuncEntity.class);
		req.setAttribute("funclist", funclist);
		if (StringUtil.isNotEmpty(buildingShowFunc.getId())) {
			buildingShowFunc = buildingShowFuncService.getEntity(BuildingShowFuncEntity.class, buildingShowFunc.getId());
			req.setAttribute("buildingShowFuncPage", buildingShowFunc);
			if(CommonUtil.NEWID){
				MeterEntity meter=systemService.getEntity(MeterEntity.class, buildingShowFunc.getMeterid());
				req.setAttribute("meterName", meter.getMetertext());
			}else{
				MeterEntity meter=systemService.findByProperty(MeterEntity.class, "meterid", buildingShowFunc.getMeterid()).get(0);
				req.setAttribute("meterName", meter.getMetertext());
			}
		}
		return new ModelAndView("nhjkpt/configmanage/buildingshowfunc/buildingShowFunc");
	}
}
