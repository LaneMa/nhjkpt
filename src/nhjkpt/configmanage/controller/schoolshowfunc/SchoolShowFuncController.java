package nhjkpt.configmanage.controller.schoolshowfunc;

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

import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.schoolshowfunc.SchoolShowFuncEntity;
import nhjkpt.configmanage.entity.schoolsum.SchoolSumEntity;
import nhjkpt.configmanage.service.schoolshowfunc.SchoolShowFuncServiceI;

/**   
 * @Title: Controller
 * @Description: 学校需要展示的瞬时功能
 * @author qf
 * @date 2013-07-29 22:43:46
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/schoolShowFuncController")
public class SchoolShowFuncController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchoolShowFuncController.class);

	@Autowired
	private SchoolShowFuncServiceI schoolShowFuncService;
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
	 * 学校需要展示的瞬时功能列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schoolShowFunc")
	public ModelAndView schoolShowFunc(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/schoolshowfunc/schoolShowFuncList");
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
	public void datagrid(SchoolShowFuncEntity schoolShowFunc,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchoolShowFuncEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, schoolShowFunc);
		this.schoolShowFuncService.getDataGridReturn(cq, true);
		List<SchoolShowFuncEntity> schoollist=dataGrid.getReaults();
		List<FuncEntity> funcList = systemService.getList(FuncEntity.class);
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		for(SchoolShowFuncEntity sum:schoollist){
			for (FuncEntity func : funcList) {
				if(func.getFuncid().toString().equals(sum.getFuncid())){
					sum.setFuncid(func.getFuncname());
				}
			}
			for (MeterEntity meter : meterList) {
				if(meter.getMeterid().toString().equals(sum.getMeterid())){
					sum.setMeterid(meter.getMetertext());
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学校需要展示的瞬时功能
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SchoolShowFuncEntity schoolShowFunc, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		schoolShowFunc = systemService.getEntity(SchoolShowFuncEntity.class, schoolShowFunc.getId());
		message = "删除成功";
		schoolShowFuncService.delete(schoolShowFunc);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学校需要展示的瞬时功能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SchoolShowFuncEntity schoolShowFunc, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(schoolShowFunc.getId())) {
			message = "更新成功";
			SchoolShowFuncEntity t = schoolShowFuncService.get(SchoolShowFuncEntity.class, schoolShowFunc.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(schoolShowFunc, t);
				schoolShowFuncService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			schoolShowFuncService.save(schoolShowFunc);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学校需要展示的瞬时功能列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SchoolShowFuncEntity schoolShowFunc, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(schoolShowFunc.getId())) {
			schoolShowFunc = schoolShowFuncService.getEntity(SchoolShowFuncEntity.class, schoolShowFunc.getId());
			req.setAttribute("schoolShowFuncPage", schoolShowFunc);
			if(CommonUtil.NEWID){
				MeterEntity meter=systemService.getEntity(MeterEntity.class, schoolShowFunc.getMeterid());
				req.setAttribute("meterName", meter.getMetertext());
			}else{
				MeterEntity meter=systemService.findByProperty(MeterEntity.class,"meterid", schoolShowFunc.getMeterid()).get(0);
				req.setAttribute("meterName", meter.getMetertext());
			}
		}
		List<FuncEntity> funclist = systemService.getList(FuncEntity.class);
		req.setAttribute("funclist", funclist);
		return new ModelAndView("nhjkpt/configmanage/schoolshowfunc/schoolShowFunc");
	}
	@RequestMapping(params = "meters")
	public String meters() {
		return "nhjkpt/configmanage/metermanage/metersChoose";
	}
}
