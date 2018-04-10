package nhjkpt.configmanage.controller.buildinginfo;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.framework.core.common.controller.BaseController;
import org.framework.core.common.hibernate.qbc.CriteriaQuery;
import org.framework.core.common.model.json.AjaxJson;
import org.framework.core.common.model.json.ComboTree;
import org.framework.core.common.model.json.DataGrid;
import org.framework.core.common.model.json.ValidForm;
import org.framework.core.constant.Globals;
import org.framework.core.util.MyBeanUtils;
import org.framework.core.util.StringUtil;
import org.framework.core.util.oConvertUtils;
import org.framework.tag.core.easyui.TagUtil;
import org.framework.tag.vo.easyui.ComboTreeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import nhjkpt.configmanage.entity.buildingdaysum.BuildingDaySumEntity;
import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingshowfunc.BuildingShowFuncEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.service.buildinginfo.BuildinginfoServiceI;
import nhjkpt.configmanage.service.buildingsum.BuildingSumServiceI;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

/**   
 * @Title: Controller
 * @Description: 建筑物配置
 * @author zhangdaihao
 * @date 2013-07-21 00:18:04
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/buildinginfoController")
public class BuildinginfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BuildinginfoController.class);

	@Autowired
	private BuildinginfoServiceI buildinginfoService;
	@Autowired
	private BuildingSumServiceI buildingSumService;
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
	 * 建筑物配置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "buildinginfo")
	public ModelAndView buildinginfo(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/buildinginfo/buildinginfoList");
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
	public void datagrid(BuildinginfoEntity buildinginfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BuildinginfoEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, buildinginfo);
		this.buildinginfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除建筑物配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BuildinginfoEntity buildinginfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		buildinginfo = systemService.getEntity(BuildinginfoEntity.class, buildinginfo.getId());
		message = "删除成功";
		buildinginfoService.delete(buildinginfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加建筑物配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(BuildinginfoEntity buildinginfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(buildinginfo.getId())) {
			message = "更新成功";
			BuildinginfoEntity t = buildinginfoService.get(BuildinginfoEntity.class, buildinginfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(buildinginfo, t);
				buildinginfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			buildinginfoService.save(buildinginfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 建筑物配置列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BuildinginfoEntity buildinginfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(buildinginfo.getId())) {
			buildinginfo = buildinginfoService.getEntity(BuildinginfoEntity.class, buildinginfo.getId());
			req.setAttribute("buildinginfoPage", buildinginfo);
		}
		return new ModelAndView("nhjkpt/configmanage/buildinginfo/buildinginfo");
	}
	
	
	/**
	 * 检查建筑物名称是否唯一
	 * 
	 * @param funcid
	 * @return
	 */
	@RequestMapping(params = "checkBuildingname")
	@ResponseBody
	public ValidForm checkBuildingname(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String name=oConvertUtils.getString(request.getParameter("param"));
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			BuildinginfoEntity info=systemService.getEntity(BuildinginfoEntity.class, id);
			if(info!=null){
				code=info.getBuildingname();
			}
		}
		List<BuildinginfoEntity> list=systemService.findByProperty( BuildinginfoEntity.class ,"buildingname",name);
		if(list.size()>0&&!code.equals(name))
		{
			v.setInfo("此建筑物名称已存在");
			v.setStatus("n");
		}
		return v;
	}
	
	
	/**
	 * 检查建筑物名称是否唯一
	 * 
	 * @param funcid
	 * @return
	 */
	@RequestMapping(params = "checkBuildingid")
	@ResponseBody
	public ValidForm checkBuildingid(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String param=oConvertUtils.getString(request.getParameter("param"));
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			BuildinginfoEntity info=systemService.getEntity(BuildinginfoEntity.class, id);
			if(info!=null){
				code=info.getBuildingid();
			}
		}
		List<BuildinginfoEntity> list=systemService.findByProperty( BuildinginfoEntity.class ,"buildingid",param);
		if(list.size()>0&&!code.equals(param))
		{
			v.setInfo("此建筑物标识号已存在");
			v.setStatus("n");
		}
		return v;
	}
	@RequestMapping(params = "treeBuilding")
	@ResponseBody
	public List<ComboTree> treeBuilding(HttpServletRequest request){
		List<BuildinginfoEntity> list=systemService.getList(BuildinginfoEntity.class);
		List<ComboTree> comboTrees=new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "buildingname", "");
		comboTrees = systemService.ComboTree(list, comboTreeModel,null);
		return comboTrees;
	}
	@RequestMapping(params = "queryBuildings")
	@ResponseBody
	public List<BuildinginfoEntity> queryBuildings(HttpServletRequest request){
		return systemService.getList(BuildinginfoEntity.class);
	}
	
	@RequestMapping(params = "querybuildingsbyname")
	@ResponseBody
	public List<BuildinginfoEntity> queryBuildingsByName(HttpServletRequest request){
		String buildName = request.getParameter("buildName");
		return buildinginfoService.queryListByName(buildName);
	}
	
	@RequestMapping(params = "queryBuildingsMessage")
	@ResponseBody
	public List<Map<String,Object>> queryBuildingsMessage(HttpServletRequest request){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<BuildinginfoEntity> listbuilding= systemService.getList(BuildinginfoEntity.class);
		List<FuncEntity> listfunc=systemService.getList(FuncEntity.class);
		Map<String,Object> map=null;
		List<BuildingDaySumEntity> listsum=null;
		for(BuildinginfoEntity building:listbuilding){
			map=new HashMap<String, Object>();
			map.put("id", building.getId());
			map.put("name", building.getBuildingname());
			listsum=buildingSumService.queryBuildingDaySumByBuildingid(building.getId());
			String message="";
			if(listsum!=null&&listsum.size()!=0){
				for(BuildingDaySumEntity sum:listsum){
					for(FuncEntity func:listfunc){
						if(sum.getFuncid().equals(func.getId())){
							message+=func.getFuncname()+":"+CommonUtil.formateResult(sum.getData())+";排名:"+buildingSumService.queryRanking(sum.getData(), sum.getFuncid())+"<br>";
						}
					}
				}
			}else{
				message="没有数据";
			}
			map.put("message", message);
			list.add(map);
		}
		return list;
	}
	@RequestMapping(params = "queryBuildingMessage")
	@ResponseBody
	public Map<String,Object> queryBuildingMessage(String id,HttpServletRequest request){
		List<FuncEntity> listfunc=systemService.getList(FuncEntity.class);
		List<BuildingDaySumEntity> listsum=buildingSumService.queryBuildingDaySumByBuildingid(id);
		String message="";
		if(listsum!=null&&listsum.size()!=0){
			for(BuildingDaySumEntity sum:listsum){
				for(FuncEntity func:listfunc){
					if(sum.getFuncid().equals(func.getFuncid().toString())){
						message+=func.getFuncname()+":"+CommonUtil.formateResult(sum.getData())+";排名:"+buildingSumService.queryRanking(sum.getData(), sum.getFuncid())+"<br>";
					}
				}
			}
		}else{
			message="没有数据";
		}
		Map<String, Object> map=new HashMap<String, Object>();
		BuildinginfoEntity building=systemService.get(BuildinginfoEntity.class, id);
		map.put("id", building.getId());
		map.put("name", building.getBuildingname());
		map.put("message", message);
		return map;
	}
	/**
	 * 打开建筑物坐标设置页面
	 * @param x
	 * @param y
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "buildingCoor")
	public ModelAndView buildingCoor(String x,String y,HttpServletRequest req){
		List<BuildinginfoEntity> listbuilding=systemService.getList(BuildinginfoEntity.class);
		req.setAttribute("listbuilding", listbuilding);
		req.setAttribute("x", x);
		req.setAttribute("y", y);
		return new ModelAndView("nhjkpt/configmanage/buildinginfo/buildingCoor");
	}
	/**
	 * 保存建筑物坐标信息
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "saveBuildingCoor")
	@ResponseBody
	public BuildinginfoEntity saveBuildingCoor(String id,String x,String y,HttpServletRequest req) {
		BuildinginfoEntity building=systemService.get(BuildinginfoEntity.class, id);
		building.setX(Integer.parseInt(x));
		building.setY(Integer.parseInt(y));
		systemService.save(building);
		return building;
	}
	/**
	 * 打开学校地图显示大楼信息
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "buildingMap")
	public ModelAndView buildingMap(String id,String type, HttpServletRequest req) {
		req.setAttribute("id", id);
		req.setAttribute("type", type);

		String filepath = req.getSession().getServletContext().getRealPath("/")+"webpage/images/";
		File file = new File(filepath);
		String[] filelist = file.list();
		List<MapVo> list = new ArrayList<MapVo>();
		ModelAndView mav = new ModelAndView("nhjkpt/configmanage/buildinginfo/buildingMap");
		mav.addObject("firstAddr", "");
        for (int i = 0; i < filelist.length; i++) {
            File readfile = new File(filepath + "\\" + filelist[i]);
            MapVo vo = new MapVo();
            vo.setMapAddr("webpage/images/" + readfile.getName());
            vo.setId((i + 1) + "");
            list.add(vo);
            if(i == 0) {
            	mav.addObject("firstAddr", "webpage/images/" + readfile.getName());
            }
        }
		
		mav.addObject("mapList", list);
		return mav;
		
	}
	@RequestMapping(params = "buildingMapShell")
	public ModelAndView buildingMapShell(String id, HttpServletRequest req) {
		req.setAttribute("id", id);
		return new ModelAndView("nhjkpt/configmanage/buildinginfo/buildingMapShell");
		
	}
	/**
	 * 打开大楼主页面
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "buildinghome")
	public ModelAndView buildinghome(String id, HttpServletRequest req) {
		List<String> listshow=new ArrayList<String>();
		BuildinginfoEntity building=systemService.get(BuildinginfoEntity.class, id);
		
		
		Double waterUse=0.0,waterLimit=0.0,waterRatio=0.0;
		Double electricUse=0.0,electricLimit=0.0,electricRatio=0.0,co2=0.0;
		Double gasUse=0.0,gasLimit=0.0,gasRatio=0.0;
		Double heatUse=0.0,heatLimit=0.0,heatRatio=0.0;
		
		
		FuncEntity funcWater=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "水量");
		if(funcWater!=null){
			waterUse=buildinginfoService.getUseNhThisYear(building.getBuildingid(),funcWater.getFuncid().toString());
		}
		waterLimit=building.getWaterlimit();
		if(waterLimit!=null && waterLimit>0){
			BigDecimal bg = new BigDecimal(waterUse/waterLimit*100);
			waterRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		
		FuncEntity funcElectric=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "电量");
		if(funcElectric!=null){
			electricUse=buildinginfoService.getUseNhThisYear(building.getBuildingid(),funcElectric.getFuncid().toString());
		}
		BigDecimal bg1 = new BigDecimal(electricUse*0.785);
		co2 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		electricLimit=building.getElectriclimit();
		if(electricLimit!=null && electricLimit>0){
			BigDecimal bg = new BigDecimal(electricUse/electricLimit*100);
			electricRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		FuncEntity funcGas=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "气量");
		if(funcGas!=null){
			gasUse=buildinginfoService.getUseNhThisYear(building.getBuildingid(),funcGas.getFuncid().toString());
		}
		gasLimit=building.getGaslimit();
		if(gasLimit!=null && gasLimit>0){
			BigDecimal bg = new BigDecimal(gasUse/gasLimit*100);
			gasRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		FuncEntity funcHeat=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "热量");
		if(funcHeat!=null){
			heatUse=buildinginfoService.getUseNhThisYear(building.getBuildingid(),funcHeat.getFuncid().toString());
		}
		heatLimit=building.getHeatlimit();
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
		
		
		
		
		Map<String,Object> mapmeter=(Map<String, Object>) CommonUtil.showmap.get(building.getBuildingid());
		Map<String,Object> mapfunc=null;
		List<BuildingShowFuncEntity> buildingshow=systemService.findByProperty(BuildingShowFuncEntity.class,"buildingid", building.getBuildingid());
		for(BuildingShowFuncEntity show:buildingshow){
			if(mapmeter!=null){
				mapfunc=(Map<String, Object>) mapmeter.get(show.getMeterid());
				if(mapfunc!=null){
					listshow.add(show.getShowtext()+":"+mapfunc.get(show.getFuncid()));
				}else{
					listshow.add(show.getShowtext()+":"+CommonUtil.SHOW_TEXT_NULL);
				}
			}else{
				listshow.add(show.getShowtext()+":"+CommonUtil.SHOW_TEXT_NULL);
			}
		}
		req.setAttribute("listshow", listshow);
		req.setAttribute("buildinginfo", building);
		return new ModelAndView("nhjkpt/configmanage/buildinginfo/buildinghome");
	}
	/**
	 * 左侧打开列表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "treeReport")
	@ResponseBody
	public List<ComboTree> treeReport(HttpServletRequest request){
		List<ComboTree> comboTrees=new ArrayList<ComboTree>();
		ComboTree comboTree=new ComboTree();
		comboTree.setId("buildingsum");
		comboTree.setText("建筑物总用能");
		comboTrees.add(comboTree);
		comboTree=new ComboTree();
		comboTree.setId("buildingitemize");
		comboTree.setText("建筑物分类用能");
		comboTrees.add(comboTree);
		comboTree=new ComboTree();
		comboTree.setId("buildingunit");
		comboTree.setText("建筑物平均用能");
		comboTrees.add(comboTree);
		request.setAttribute("contrast", comboTrees);
		return comboTrees;
	}
	/**
	 * 报表管理
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "reportManage")
	public ModelAndView reportManage(String id,HttpServletRequest req) {
		List<FuncEntity> listfunc=systemService.findByQueryString(" from FuncEntity where ischeck='1'");
		req.setAttribute("listfunc", listfunc);
		if("building".equals(id)){
			return new ModelAndView("nhjkpt/configmanage/buildinginfo/reportManage");
		}else{
			return new ModelAndView("nhjkpt/configmanage/departmentinfo/reportManage");
		}
	}
	/**
	 * 获取所有大楼下拉列表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "buildingTree")
	@ResponseBody
	public List<ComboTree> buildingTree(HttpServletRequest request) {
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTree comboTree=null;
		List<BuildinginfoEntity> list=systemService.getList(BuildinginfoEntity.class);
		for(BuildinginfoEntity building:list){
			comboTree=new ComboTree();
			if(CommonUtil.NEWID){
				comboTree.setId(building.getId());
			}else{
				comboTree.setId(building.getBuildingid());
			}
			comboTree.setText(building.getBuildingname());
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
	/**
	 * 打开报表页面
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "reporthome")
	public ModelAndView reporthome(String id,HttpServletRequest req) {
		List<ItemizeEntity> itemizelist = systemService.getList(ItemizeEntity.class);
		req.setAttribute("itemizelist", itemizelist);
		req.setAttribute("id", id);
		return new ModelAndView("nhjkpt/configmanage/buildinginfo/reporthome");
	}
	/**
	 * 获取瞬时信息值
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loaddata")
	@ResponseBody
	public List<String> loaddata(String id,HttpServletRequest req) {
		List<String> listshow=new ArrayList<String>();
		BuildinginfoEntity building=systemService.get(BuildinginfoEntity.class, id);
		Map<String,Object> mapmeter=(Map<String, Object>) CommonUtil.showmap.get(building.getBuildingid());
		Map<String,Object> mapfunc=null;
		List<BuildingShowFuncEntity> buildingshow=systemService.findByProperty(BuildingShowFuncEntity.class,"buildingid", building.getBuildingid());
		for(BuildingShowFuncEntity show:buildingshow){
			if(mapmeter!=null){
				mapfunc=(Map<String, Object>) mapmeter.get(show.getMeterid());
				if(mapfunc!=null){
					listshow.add(show.getShowtext()+":"+mapfunc.get(show.getFuncid()));
				}else{
					listshow.add(show.getShowtext()+":"+CommonUtil.SHOW_TEXT_NULL);
				}
			}else{
				listshow.add(show.getShowtext()+":"+CommonUtil.SHOW_TEXT_NULL);
			}
		}
		req.setAttribute("listshow", listshow);
		return listshow;
	}
	/**
	 * 左侧报表列表
	 * @param buildingid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "treeLeftReport")
	@ResponseBody
	public List<ComboTree> treeLeftReport(HttpServletRequest req) {
		List<ComboTree> comboTrees=new ArrayList<ComboTree>();
		ComboTree comboTree=new ComboTree();
		comboTree.setId("building");
		comboTree.setText("建筑物");
		comboTrees.add(comboTree);
		comboTree=new ComboTree();
		comboTree.setId("department");
		comboTree.setText("分户");
		comboTrees.add(comboTree);
		req.setAttribute("report", comboTrees);
		return comboTrees;
	}
}
