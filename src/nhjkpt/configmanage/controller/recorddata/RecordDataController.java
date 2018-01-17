package nhjkpt.configmanage.controller.recorddata;

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
import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.recorddata.RecordDataEntity;
import nhjkpt.configmanage.service.recorddata.RecordDataServiceI;

/**   
 * @Title: Controller
 * @Description: 瞬时数据
 * @author qf
 * @date 2014-12-05 22:58:58
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/recordDataController")
public class RecordDataController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RecordDataController.class);

	@Autowired
	private RecordDataServiceI recordDataService;
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
	 * 瞬时数据列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "recordData")
	public ModelAndView recordData(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/recorddata/recordDataList");
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
	public void datagrid(RecordDataEntity recordData,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RecordDataEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, recordData);
		this.recordDataService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除瞬时数据
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(RecordDataEntity recordData, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		recordData = systemService.getEntity(RecordDataEntity.class, recordData.getId());
		message = "删除成功";
		recordDataService.delete(recordData);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加瞬时数据
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(RecordDataEntity recordData, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(recordData.getId())) {
			message = "更新成功";
			RecordDataEntity t = recordDataService.get(RecordDataEntity.class, recordData.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(recordData, t);
				recordDataService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			recordDataService.save(recordData);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 瞬时数据列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(RecordDataEntity recordData, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(recordData.getId())) {
			recordData = recordDataService.getEntity(RecordDataEntity.class, recordData.getId());
			req.setAttribute("recordDataPage", recordData);
		}
		return new ModelAndView("nhjkpt/configmanage/recorddata/recordData");
	}
}
