package nhjkpt.configmanage.controller.itemize;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.framework.core.common.model.json.TreeGrid;
import org.framework.core.common.model.json.ValidForm;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.core.util.oConvertUtils;
import org.framework.tag.core.easyui.TagUtil;
import org.framework.tag.vo.easyui.ComboTreeModel;
import org.framework.tag.vo.easyui.TreeGridModel;

import nhjkpt.system.pojo.base.TSDemo;
import nhjkpt.system.pojo.base.TSDepart;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.meterfuncshowhistory.MeterFuncShowHistoryEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.schoolshowfunc.SchoolShowFuncEntity;
import nhjkpt.configmanage.service.itemize.itemizeServiceI;

/**   
 * @Title: Controller
 * @Description: 分类配置
 * @author zhangdaihao
 * @date 2013-07-21 00:27:06
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/itemizeController")
public class itemizeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(itemizeController.class);

	@Autowired
	private itemizeServiceI itemizeService;
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
	 * 分类配置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "itemize")
	public ModelAndView itemize(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/itemize/itemizeList");
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
	public void datagrid(ItemizeEntity itemize,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ItemizeEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, itemize);
		this.itemizeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	/**
	 * 树形表格
	 */
	@RequestMapping(params = "treeGrid")
	@ResponseBody
	public List<TreeGrid> treeGrid(HttpServletRequest request, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(ItemizeEntity.class);
		if (treegrid.getId() != null) {
			cq.eq("itemize.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("itemize");
		}
		cq.add();
		List<ItemizeEntity> itemizeList = systemService.getListByCriteriaQuery(cq, false);
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("itemizetext");
		treeGridModel.setParentText("itemize_itemizetext");
		treeGridModel.setParentId("itemize_id");
		treeGridModel.setIdField("id");
		
		treeGridModel.setSrc("itemizeid");
		treeGridModel.setOrder("level");
		
		treeGridModel.setChildList("itemizes");
		List<TreeGrid> treeGrids = systemService.treegrid(itemizeList, treeGridModel);
		return treeGrids;
	}
	

	/**
	 * 删除分类配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(ItemizeEntity itemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		itemize = systemService.getEntity(ItemizeEntity.class, itemize.getId());
		message = "删除成功";
		itemizeService.delete(itemize);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加分类配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(ItemizeEntity itemize, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		if (itemize.getItemize() == null || StringUtil.isEmpty(itemize.getItemize().getId())) {
			itemize.setItemize(null);
			
		}
		
		if (StringUtil.isNotEmpty(itemize.getId())) {
			message = "更新成功";
			ItemizeEntity t = itemizeService.get(ItemizeEntity.class, itemize.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(itemize, t);
				itemizeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			itemizeService.save(itemize);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 分类配置列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(ItemizeEntity itemize, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(itemize.getId())) {
			itemize = itemizeService.getEntity(ItemizeEntity.class, itemize.getId());
			req.setAttribute("itemizePage", itemize);
		}
		return new ModelAndView("nhjkpt/configmanage/itemize/itemize");
	}
	
	
	/**
	 * 表具标识号是否唯一
	 * 
	 * @param meterid
	 * @return
	 */
	@RequestMapping(params = "checkItemizeid")
	@ResponseBody
	public ValidForm checkItemizeid(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String itemizeid=oConvertUtils.getString(request.getParameter("param"));
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			ItemizeEntity itemize=systemService.getEntity(ItemizeEntity.class, id);
			if(itemize!=null){
				code=itemize.getItemizeid().toString();
			}
		}
		List<ItemizeEntity> list=systemService.findByProperty( ItemizeEntity.class ,"itemizeid",Integer.valueOf(itemizeid));
		if(list.size()>0&&!code.equals(itemizeid))
		{
			v.setInfo("此分类标识号已存在");
			v.setStatus("n");
		}
		return v;
	}
	
	
	
	/**
	 * 表具标识号是否唯一
	 * 
	 * @param metertext
	 * @return
	 */
	@RequestMapping(params = "checkItemizetext")
	@ResponseBody
	public ValidForm checkItemizetext(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String param=oConvertUtils.getString(request.getParameter("param"));
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			ItemizeEntity itemize=systemService.getEntity(ItemizeEntity.class, id);
			if(itemize!=null){
				code=itemize.getItemizetext();
			}
		}
		List<ItemizeEntity> list=systemService.findByProperty( ItemizeEntity.class ,"itemizetext",param);
		if(list.size()>0&&!code.equals(param))
		{
			v.setInfo("此分类名称已存在");
			v.setStatus("n");
		}
		return v;
	}
	
	
	//下拉框选择树 combotree
		@RequestMapping(params = "itemizeTree")
		@ResponseBody
		public List<ComboTree> itemizeTree(HttpServletRequest request, ComboTree comboTree) {
			CriteriaQuery cq = new CriteriaQuery(ItemizeEntity.class);
			
			if (comboTree.getId() != null) {
				cq.eq("itemize.id", comboTree.getId());
			}
			if (comboTree.getId() == null) {
				cq.isNull("itemize");
			}

			cq.add();
			List<ItemizeEntity> itemizeList = systemService.getListByCriteriaQuery(cq, false);
			List<ComboTree> comboTrees = new ArrayList<ComboTree>();
			ComboTreeModel comboTreeModel = new ComboTreeModel("id", "itemizetext", "itemizes");
			comboTrees = systemService.ComboTree(itemizeList, comboTreeModel,null);
			return comboTrees;
		}
		@RequestMapping(params = "buildingItemizeTree")
		@ResponseBody
		public List<ComboTree> buildingItemizeTree(String buildingid,HttpServletRequest request, ComboTree comboTree) {
			String hql="select distinct item from ItemizeEntity item, BuildingItemizeEntity buildingitem where 1=1 ";
			
			if (comboTree.getId() != null) {
				hql+=" and item.itemizeid=buildingitem.itemizeid and buildingitem.buildingid='"+systemService.get(BuildinginfoEntity.class, buildingid).getBuildingid()+"' and item.itemize.id='"+comboTree.getId()+"'";
			}
			if (comboTree.getId() == null) {
//				hql+=" and item.itemize is null and item.id in (SELECT itemize.itemize.id FROM BuildingItemizeEntity buildingitemize,ItemizeEntity itemize WHERE buildingitemize.itemizeid=itemize.id and buildingitemize.buildingid='"+buildingid+"')";
				hql+=" and item.itemize is null";
			}
			
			
			List<ItemizeEntity> itemizeList = systemService.findByQueryString(hql);
			List<ComboTree> comboTrees = new ArrayList<ComboTree>();
			ComboTreeModel comboTreeModel = new ComboTreeModel("id", "itemizetext", "itemizes");
			comboTrees = systemService.ComboTree(itemizeList, comboTreeModel,null);
			return comboTrees;
		}
		@RequestMapping(params = "departmentItemizeTree")
		@ResponseBody
		public List<ComboTree> departmentItemizeTree(String departmentid,HttpServletRequest request, ComboTree comboTree) {
			String hql="select distinct item from ItemizeEntity item, DepartmentItemizeEntity departmentitem where 1=1 ";
			
			if (comboTree.getId() != null) {
				hql+=" and item.itemizeid=departmentitem.itemizeid and departmentitem.departmentid='"+departmentid+"' and item.itemize.id='"+comboTree.getId()+"'";
			}
			if (comboTree.getId() == null) {
//				hql+=" and item.itemize is null and item.id in (SELECT itemize.itemize.id FROM DepartmentItemizeEntity departmentitem,ItemizeEntity itemize WHERE departmentitem.itemizeid=itemize.itemizeid and departmentitem.departmentid='"+departmentid+"')";
				hql+=" and item.itemize is null";
			}
			
			
			List<ItemizeEntity> itemizeList = systemService.findByQueryString(hql);
			List<ComboTree> comboTrees = new ArrayList<ComboTree>();
			ComboTreeModel comboTreeModel = new ComboTreeModel("id", "itemizetext", "itemizes");
			comboTrees = systemService.ComboTree(itemizeList, comboTreeModel,null);
			return comboTrees;
		}
		/**
		 * 打开分类选择页面
		 * @return
		 */
		@RequestMapping(params = "itemizes")
		public String meters() {
			return "nhjkpt/configmanage/itemize/itemizesChoose";
		}
		/**
		 * 分类选择页面查询数据
		 * @param meter
		 * @param request
		 * @param response
		 * @param dataGrid
		 */
		@RequestMapping(params = "datagridItemize")
		public void datagridItemize(MeterEntity meter,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
			CriteriaQuery cq = new CriteriaQuery(ItemizeEntity.class, dataGrid);
			org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, meter);
			this.systemService.getDataGridReturn(cq, true);
			TagUtil.datagrid(response, dataGrid);
		}
		@RequestMapping(params = "treeItemize")
		@ResponseBody
		public List<ComboTree> treeItemize(HttpServletRequest request, ComboTree comboTree) {
			//String hql="select item from ItemizeEntity item, SchoolItemizeEntity school where item.id=school.itemizeid group by item.id";
			
            String hql="select distinct item from ItemizeEntity item, SchoolItemizeEntity schoolitem where 1=1 ";
			
			if (comboTree.getId() != null) {
				hql+=" and item.itemizeid=schoolitem.itemizeid and item.itemize.id='"+comboTree.getId()+"'";
			}
			if (comboTree.getId() == null) {
				hql+=" and item.itemize is null and item.id in (SELECT itemize.itemize.id FROM SchoolItemizeEntity schoolitemize,ItemizeEntity itemize WHERE schoolitemize.itemizeid=itemize.itemizeid)";
			}
			
			
			
			List<ItemizeEntity> itemizeList = systemService.findByQueryString(hql);
			
			
			
			List<ComboTree> comboTrees = new ArrayList<ComboTree>();
			ComboTreeModel comboTreeModel = new ComboTreeModel("id", "itemizetext", "itemizes");
			comboTrees = systemService.ComboTree(itemizeList, comboTreeModel,null);
			return comboTrees;
		}
		/**
		 * 学校首页分类显示信息
		 * @param id
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(params = "schoolitemize")
		public ModelAndView schoolitemize(String id,HttpServletRequest request, HttpServletResponse response) {
			request.setAttribute("id", id);
			return new ModelAndView("nhjkpt/configmanage/itemize/schoolitemize");
		}
	
}
