package nhjkpt.configmanage.controller.funcmanage;
import java.io.UnsupportedEncodingException;
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
import org.framework.core.common.model.json.ValidForm;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.core.util.oConvertUtils;
import org.framework.tag.core.easyui.TagUtil;
import nhjkpt.system.pojo.base.TSDepart;
import nhjkpt.system.pojo.base.TSUser;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.service.funcmanage.FuncServiceI;

/**   
 * @Title: Controller
 * @Description: 表具配置
 * @author zhangdaihao
 * @date 2013-07-10 20:48:00
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/funcController")
public class FuncController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FuncController.class);

	@Autowired
	private FuncServiceI funcService;
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
	 * 表具配置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "func")
	public ModelAndView func(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/funcmanage/funcList");
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
	public void datagrid(FuncEntity func,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(FuncEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, func);
		this.funcService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除表具配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(FuncEntity func, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		func = systemService.getEntity(FuncEntity.class, func.getId());
		message = "删除成功";
		funcService.delete(func);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加表具配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(FuncEntity func, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(func.getId())) {
			message = "更新成功";
			FuncEntity t = funcService.get(FuncEntity.class, func.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(func, t);
				funcService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			funcService.save(func);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 表具配置列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(FuncEntity func, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(func.getId())) {
			func = funcService.getEntity(FuncEntity.class, func.getId());
			req.setAttribute("funcPage", func);
		}
		return new ModelAndView("nhjkpt/configmanage/funcmanage/func");
	}
	
	
	/**
	 * 表具标识号是否唯一
	 * 
	 * @param funcid
	 * @return
	 */
	@RequestMapping(params = "checkFuncid")
	@ResponseBody
	public ValidForm checkFuncid(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		int funcid=oConvertUtils.getInt(request.getParameter("param"));
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			FuncEntity func=systemService.getEntity(FuncEntity.class, id);
			if(func!=null){
				code=oConvertUtils.getString(func.getFuncid());
			}
		}
		List<FuncEntity> func=systemService.findByProperty( FuncEntity.class ,"funcid",funcid);
		
		if(func.size()>0&&!code.equals(oConvertUtils.getString(funcid)))
		{
			v.setInfo("此功能标识号已存在");
			v.setStatus("n");
		}
		return v;
	}
	
	
	/**
	 * 表具标识号是否唯一
	 * 
	 * @param funcid
	 * @return
	 */
	@RequestMapping(params = "checkFuncname")
	@ResponseBody
	public ValidForm checkFuncname(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String funcname=oConvertUtils.getString(request.getParameter("param"));
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			FuncEntity func=systemService.getEntity(FuncEntity.class, id);
			if(func!=null){
				code=func.getFuncname();
			}
		}
		
		
		List<FuncEntity> funcs=systemService.findByProperty( FuncEntity.class ,"funcname",funcname);
		if(funcs.size()>0&&!code.equals(funcname))
		{
			v.setInfo("此功能名称已存在");
			v.setStatus("n");
		}
		return v;
	}

}
