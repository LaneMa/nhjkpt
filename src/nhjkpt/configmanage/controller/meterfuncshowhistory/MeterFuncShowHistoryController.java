package nhjkpt.configmanage.controller.meterfuncshowhistory;

import java.util.ArrayList;
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
import org.framework.core.common.model.json.Highchart;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.tag.core.easyui.TagUtil;
import nhjkpt.system.service.SystemService;
import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.meterfuncshowhistory.MeterFuncShowHistoryEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.metershowhistory.MeterShowHistoryEntity;
import nhjkpt.configmanage.service.meterfuncshowhistory.MeterFuncShowHistoryServiceI;

/**   
 * @Title: Controller
 * @Description: 瞬时值保存两个月记录
 * @author qf
 * @date 2013-08-29 16:15:52
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/meterFuncShowHistoryController")
public class MeterFuncShowHistoryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MeterFuncShowHistoryController.class);

	@Autowired
	private MeterFuncShowHistoryServiceI meterFuncShowHistoryService;
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
	 * 瞬时值保存两个月记录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "meterFuncShowHistory")
	public ModelAndView meterFuncShowHistory(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/meterfuncshowhistory/meterFuncShowHistoryList");
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
	public void datagrid(String id,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		List<MeterShowHistoryEntity> list=systemService.findByProperty(MeterShowHistoryEntity.class, "meterid", systemService.get(MeterEntity.class, id).getMeterid());
		if(list!=null&&list.size()!=0){
			Object[] obj=new Object[list.size()];
			for(int i=0,l=list.size();i<l;i++){
				obj[i]=list.get(i).getId();
			}
			CriteriaQuery cq = new CriteriaQuery(MeterFuncShowHistoryEntity.class, dataGrid);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("senddate", "desc");
			cq.setOrder(map);
			cq.in("meterhistoryid", obj);
			cq.add();
			this.meterFuncShowHistoryService.getDataGridReturn(cq, true);
			TagUtil.datagrid(response, dataGrid);
		}
	}

	/**
	 * 删除瞬时值保存两个月记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(MeterFuncShowHistoryEntity meterFuncShowHistory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		meterFuncShowHistory = systemService.getEntity(MeterFuncShowHistoryEntity.class, meterFuncShowHistory.getId());
		message = "删除成功";
		meterFuncShowHistoryService.delete(meterFuncShowHistory);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加瞬时值保存两个月记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(MeterFuncShowHistoryEntity meterFuncShowHistory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(meterFuncShowHistory.getId())) {
			message = "更新成功";
			MeterFuncShowHistoryEntity t = meterFuncShowHistoryService.get(MeterFuncShowHistoryEntity.class, meterFuncShowHistory.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(meterFuncShowHistory, t);
				meterFuncShowHistoryService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			meterFuncShowHistoryService.save(meterFuncShowHistory);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 瞬时值保存两个月记录列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(MeterFuncShowHistoryEntity meterFuncShowHistory, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(meterFuncShowHistory.getId())) {
			meterFuncShowHistory = meterFuncShowHistoryService.getEntity(MeterFuncShowHistoryEntity.class, meterFuncShowHistory.getId());
			req.setAttribute("meterFuncShowHistoryPage", meterFuncShowHistory);
		}
		return new ModelAndView("nhjkpt/configmanage/meterfuncshowhistory/meterFuncShowHistory");
	}
	/**
	 * 进入电流统计曲线页面
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "stacurveCurrent")
	public ModelAndView stacurveCurrent(String id, HttpServletRequest req) {
		req.setAttribute("id", id);
		return new ModelAndView("nhjkpt/configmanage/meterfuncshowhistory/stacurveCurrent");
	}
	@RequestMapping(params = "queryBroswerBarCurrent")
	@ResponseBody
	public List<Highchart> queryBroswerBarCurrent(String id,HttpServletRequest request, HttpServletResponse response) {
		return meterFuncShowHistoryService.queryHighchart(id);
	}
}
