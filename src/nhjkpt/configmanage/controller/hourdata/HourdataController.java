package nhjkpt.configmanage.controller.hourdata;

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

import nhjkpt.configmanage.entity.hourdata.HourdataEntity;
import nhjkpt.configmanage.service.hourdata.HourdataServiceI;

/**   
 * @Title: Controller
 * @Description: 历史数据的小时用电量表
 * @author qf
 * @date 2013-08-13 15:25:25
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/hourdataController")
public class HourdataController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HourdataController.class);

	@Autowired
	private HourdataServiceI hourdataService;
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
	 * 历史数据的小时用电量表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "hourdata")
	public ModelAndView hourdata(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/hourdata/hourdataList");
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
	public void datagrid(HourdataEntity hourdata,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(HourdataEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hourdata);
		this.hourdataService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除历史数据的小时用电量表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(HourdataEntity hourdata, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		hourdata = systemService.getEntity(HourdataEntity.class, hourdata.getId());
		message = "删除成功";
		hourdataService.delete(hourdata);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加历史数据的小时用电量表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(HourdataEntity hourdata, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(hourdata.getId())) {
			message = "更新成功";
			HourdataEntity t = hourdataService.get(HourdataEntity.class, hourdata.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(hourdata, t);
				hourdataService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			hourdataService.save(hourdata);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 历史数据的小时用电量表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(HourdataEntity hourdata, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hourdata.getId())) {
			hourdata = hourdataService.getEntity(HourdataEntity.class, hourdata.getId());
			req.setAttribute("hourdataPage", hourdata);
		}
		return new ModelAndView("nhjkpt/configmanage/hourdata/hourdata");
	}
}
