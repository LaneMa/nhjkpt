package nhjkpt.configmanage.controller.metershowfunc;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.metershowfunc.MetershowfuncEntity;

import nhjkpt.configmanage.entity.roommanage.RoomEntity;
import nhjkpt.configmanage.service.metershowfunc.MetershowfuncServiceI;

/**   
 * @Title: Controller
 * @Description: 电表需要展示的功能
 * @author zhangdaihao
 * @date 2013-07-17 23:09:43
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/metershowfuncController")
public class MetershowfuncController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MetershowfuncController.class);

	@Autowired
	private MetershowfuncServiceI metershowfuncService;
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
	 * 电表需要展示的功能列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "metershowfunc")
	public ModelAndView metershowfunc(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/metershowfunc/metershowfuncList");
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
	public void datagrid(MetershowfuncEntity metershowfunc,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(MetershowfuncEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, metershowfunc);
		this.metershowfuncService.getDataGridReturn(cq, true);
		List<MetershowfuncEntity> listshow=dataGrid.getReaults();
		List<FuncEntity> funcList = systemService.getList(FuncEntity.class);
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		for(MetershowfuncEntity show:listshow){
			for (FuncEntity func : funcList) {
				if(CommonUtil.NEWID){
					if(func.getId().equals(show.getFuncid())){
						show.setFuncid(func.getFuncname());
					}
				}else{
					if(func.getFuncid().toString().equals(show.getFuncid())){
						show.setFuncid(func.getFuncname());
					}
				}
			}
			for (MeterEntity meter : meterList) {
				if(CommonUtil.NEWID){
					if(meter.getId().equals(show.getMeterid())){
						show.setMeterid(meter.getMetertext());
					}
				}else{
					if(meter.getMeterid().toString().equals(show.getMeterid())){
						show.setMeterid(meter.getMetertext());
					}
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除电表需要展示的功能
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(MetershowfuncEntity metershowfunc, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		metershowfunc = systemService.getEntity(MetershowfuncEntity.class, metershowfunc.getId());
		message = "删除成功";
		metershowfuncService.delete(metershowfunc);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加电表需要展示的功能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(MetershowfuncEntity metershowfunc, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		System.out.println("id====:"+metershowfunc.getMeterid());
		
		
		if (StringUtil.isNotEmpty(metershowfunc.getId())) {
			message = "更新成功";
			MetershowfuncEntity t = metershowfuncService.get(MetershowfuncEntity.class, metershowfunc.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(metershowfunc, t);
				metershowfuncService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			metershowfuncService.save(metershowfunc);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}
	
	
	
	
	/**
	 * 批量添加表具上下限功能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveBatch")
	@ResponseBody
	public AjaxJson saveBatch(@RequestBody List<LinkedHashMap> metershowfuncs, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();

		if(metershowfuncs!=null){
			for(int i=0;i< metershowfuncs.size();i++){
				MetershowfuncEntity metershowfunc=new MetershowfuncEntity();
				String ID= (String)metershowfuncs.get(i).get("id");
				Double top=Double.valueOf((String)metershowfuncs.get(i).get("top"));
				Double floor=Double.valueOf((String)metershowfuncs.get(i).get("floor"));
				
				metershowfunc.setFloor(floor);
				metershowfunc.setId(ID);
				metershowfunc.setTop(top);
				message = "更新成功";
				MetershowfuncEntity t = metershowfuncService.get(MetershowfuncEntity.class, metershowfunc.getId());
				try {
					MyBeanUtils.copyBeanNotNull2Bean(metershowfunc, t);
					metershowfuncService.saveOrUpdate(t);
					systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		}
		

		return j;
	}
	
	
	

	/**
	 * 电表需要展示的功能列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(MetershowfuncEntity metershowfunc, HttpServletRequest req) {
		List<FuncEntity> funclist = systemService.getList(FuncEntity.class);
		req.setAttribute("funclist", funclist);
		
		
		List<MeterEntity> meterlist = systemService.getList(MeterEntity.class);
		req.setAttribute("meterlist", meterlist);
		
		
		if (StringUtil.isNotEmpty(metershowfunc.getId())) {
			metershowfunc = metershowfuncService.getEntity(MetershowfuncEntity.class, metershowfunc.getId());
			req.setAttribute("metershowfuncPage", metershowfunc);
			
			//获取meterid对应的meter对象
			if(CommonUtil.NEWID){
				MeterEntity meter=systemService.getEntity(MeterEntity.class, metershowfunc.getMeterid());
				req.setAttribute("meterName", meter.getMetertext());
			}else{
				List<MeterEntity> meters=systemService.findByProperty(MeterEntity.class,"meterid", metershowfunc.getMeterid());
				if(meters!=null&&meters.size()!=0){
					req.setAttribute("meterName", meters.get(0).getMetertext());
				}
			}
		}

		return new ModelAndView("nhjkpt/configmanage/metershowfunc/metershowfunc");
	}
	
	
	
	//打开表具列表页面
	/**
	 * 表具列表选择页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "meters")
	public String meters() {
		return "nhjkpt/configmanage/metermanage/metersChoose";
	}
	
	
}




