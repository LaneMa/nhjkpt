package nhjkpt.configmanage.controller.departmentinfo;

import java.math.BigDecimal;
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
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.tag.core.easyui.TagUtil;
import org.framework.tag.vo.easyui.ComboTreeModel;

import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.departmentinfo.DepartmentinfoEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.service.departmentinfo.DepartmentinfoServiceI;

/**   
 * @Title: Controller
 * @Description: 系基本信息
 * @author qf
 * @date 2014-11-18 23:36:22
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/departmentinfoController")
public class DepartmentinfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartmentinfoController.class);

	@Autowired
	private DepartmentinfoServiceI departmentinfoService;
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
	 * 系基本信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "departmentinfo")
	public ModelAndView departmentinfo(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/departmentinfo/departmentinfoList");
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
	public void datagrid(DepartmentinfoEntity departmentinfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DepartmentinfoEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, departmentinfo);
		this.departmentinfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除系基本信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DepartmentinfoEntity departmentinfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		departmentinfo = systemService.getEntity(DepartmentinfoEntity.class, departmentinfo.getId());
		message = "删除成功";
		departmentinfoService.delete(departmentinfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加系基本信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DepartmentinfoEntity departmentinfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(departmentinfo.getId())) {
			message = "更新成功";
			DepartmentinfoEntity t = departmentinfoService.get(DepartmentinfoEntity.class, departmentinfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(departmentinfo, t);
				departmentinfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			departmentinfoService.save(departmentinfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 系基本信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DepartmentinfoEntity departmentinfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(departmentinfo.getId())) {
			departmentinfo = departmentinfoService.getEntity(DepartmentinfoEntity.class, departmentinfo.getId());
			req.setAttribute("departmentinfoPage", departmentinfo);
		}
		return new ModelAndView("nhjkpt/configmanage/departmentinfo/departmentinfo");
	}
	/**
	 * 分户管理左侧树形展示
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "treeDepartment")
	@ResponseBody
	public List<ComboTree> treeDepartment(HttpServletRequest request){
		List<DepartmentinfoEntity> list=systemService.getList(DepartmentinfoEntity.class);
		List<ComboTree> comboTrees=new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "departmentname", "");
		comboTrees = systemService.ComboTree(list, comboTreeModel,null);
		return comboTrees;
	}
	/**
	 * 分户统计界面
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "departmenthome")
	public ModelAndView departmenthome(String id, HttpServletRequest req) {
		DepartmentinfoEntity department=systemService.get(DepartmentinfoEntity.class, id);
		
		
		
		Double waterUse=0.0,waterLimit=0.0,waterRatio=0.0;
		Double electricUse=0.0,electricLimit=0.0,electricRatio=0.0,co2=0.0;
		Double gasUse=0.0,gasLimit=0.0,gasRatio=0.0;
		Double heatUse=0.0,heatLimit=0.0,heatRatio=0.0;
		
		
		FuncEntity funcWater=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "水量");
		if(funcWater!=null){
			waterUse=departmentinfoService.getUseNhThisYear(department.getDepartmentid(),funcWater.getFuncid().toString());
		}
		waterLimit=department.getWaterlimit();
		if(waterLimit!=null && waterLimit>0){
			BigDecimal bg = new BigDecimal(waterUse/waterLimit*100);
			waterRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		
		FuncEntity funcElectric=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "电量");
		if(funcElectric!=null){
			electricUse=departmentinfoService.getUseNhThisYear(department.getDepartmentid(),funcElectric.getFuncid().toString());
		}
		BigDecimal bg1 = new BigDecimal(electricUse*0.785);
		co2 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		electricLimit=department.getElectriclimit();
		if(electricLimit!=null && electricLimit>0){
			BigDecimal bg = new BigDecimal(electricUse/electricLimit*100);
			electricRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		FuncEntity funcGas=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "气量");
		if(funcGas!=null){
			gasUse=departmentinfoService.getUseNhThisYear(department.getDepartmentid(),funcGas.getFuncid().toString());
		}
		gasLimit=department.getGaslimit();
		if(gasLimit!=null && gasLimit>0){
			BigDecimal bg = new BigDecimal(gasUse/gasLimit*100);
			gasRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		FuncEntity funcHeat=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "热量");
		if(funcHeat!=null){
			heatUse=departmentinfoService.getUseNhThisYear(department.getDepartmentid(),funcHeat.getFuncid().toString());
		}
		heatLimit=department.getHeatlimit();
		if(heatLimit!=null && heatLimit>0){
			BigDecimal bg = new BigDecimal(heatUse/heatLimit*100);
			heatRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
			
		req.setAttribute("waterUse", waterUse);
		req.setAttribute("waterLimit", waterLimit);
		req.setAttribute("waterRatio", waterRatio);
		
		req.setAttribute("electricUse", electricUse);
		req.setAttribute("electricLimit", electricLimit);
		req.setAttribute("electricRatio", electricRatio);
		req.setAttribute("co2", co2);
		
		req.setAttribute("gasUse", gasUse);
		req.setAttribute("gasLimit", gasLimit);
		req.setAttribute("gasRatio", gasRatio);
		
		req.setAttribute("heatUse", heatUse);
		req.setAttribute("heatLimit", heatLimit);
		req.setAttribute("heatRatio", heatRatio);
		
		
		
		
		
		
		
		
		
		
		req.setAttribute("departmentinfo", department);
		return new ModelAndView("nhjkpt/configmanage/departmentinfo/departmenthome");
	}
	/**
	 * 获取所有分户下拉列表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "departmentTree")
	@ResponseBody
	public List<ComboTree> departmentTree(HttpServletRequest request) {
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTree comboTree=null;
		List<DepartmentinfoEntity> list=systemService.getList(DepartmentinfoEntity.class);
		for(DepartmentinfoEntity department:list){
			comboTree=new ComboTree();
			if(CommonUtil.NEWID){
				comboTree.setId(department.getId());
			}else{
				comboTree.setId(department.getDepartmentid());
			}
			comboTree.setText(department.getDepartmentname());
			comboTrees.add(comboTree);
		}
		comboTree=new ComboTree();
		comboTree.setId("-1");
		comboTree.setText("全部");
		comboTree.setChildren(comboTrees);
		comboTrees = new ArrayList<ComboTree>();
		comboTrees.add(comboTree);
		return comboTrees;
	}
}
