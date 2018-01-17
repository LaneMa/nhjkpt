package nhjkpt.test.controller.person2;

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

import nhjkpt.test.entity.person2.Person2Entity;
import nhjkpt.test.service.person2.Person2ServiceI;

/**   
 * @Title: Controller
 * @Description: 人员管理
 * @author zhangdaihao
 * @date 2013-07-17 17:02:56
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/person2Controller")
public class Person2Controller extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Person2Controller.class);

	@Autowired
	private Person2ServiceI person2Service;
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
	 * 人员管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "person2")
	public ModelAndView person2(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/test/person2/person2List");
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
	public void datagrid(Person2Entity person2,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(Person2Entity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, person2);
		this.person2Service.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除人员管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(Person2Entity person2, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		person2 = systemService.getEntity(Person2Entity.class, person2.getPersonid());
		message = "删除成功";
		person2Service.delete(person2);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加人员管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(Person2Entity person2, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(person2.getPersonid())) {
			message = "更新成功";
			Person2Entity t = person2Service.get(Person2Entity.class, person2.getPersonid());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(person2, t);
				person2Service.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			person2Service.save(person2);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 人员管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(Person2Entity person2, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(person2.getPersonid())) {
			person2 = person2Service.getEntity(Person2Entity.class, person2.getPersonid());
			req.setAttribute("person2Page", person2);
		}
		return new ModelAndView("nhjkpt/test/person2/person2");
	}
}
