package nhjkpt.configmanage.controller.metermanage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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

import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.alarminfo.AlarminfoEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.roommanage.RoomEntity;
import nhjkpt.configmanage.entity.schoolshowfunc.SchoolShowFuncEntity;
import nhjkpt.configmanage.service.funcmanage.FuncServiceI;
import nhjkpt.configmanage.service.metermanage.MeterServiceI;

/**   
 * @Title: Controller
 * @Description: 表具管理
 * @author zhangdaihao
 * @date 2013-07-10 23:11:30
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/meterController")
public class MeterController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MeterController.class);

	@Autowired
	private MeterServiceI meterService;
	@Autowired
	private FuncServiceI funcService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	/**
	 * 表具管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "meter")
	public ModelAndView meter(HttpServletRequest request) {
		
		String roomReplace = "";
		List<RoomEntity> roomList = systemService.getList(RoomEntity.class);
		for (RoomEntity room : roomList) {
			if (roomReplace.length() > 0) {
				roomReplace += ",";
			}
			if(CommonUtil.NEWID){
				roomReplace += room.getRoomtext() + "_" + room.getId();
			}else{
				roomReplace += room.getRoomtext() + "_" + room.getRoomid();
			}
		}
		request.setAttribute("roomReplace", roomReplace);
		
		return new ModelAndView("nhjkpt/configmanage/metermanage/meterList");
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
	public void datagrid(MeterEntity meter,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(MeterEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, meter);
		this.meterService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	
	/**
	 * 表具选择显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridMeter")
	public void datagridMeter(MeterEntity meter,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(MeterEntity.class, dataGrid);
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, meter);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除表具管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(MeterEntity meter, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		meter = systemService.getEntity(MeterEntity.class, meter.getId());
		message = "删除成功";
		meterService.delete(meter);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加表具管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(MeterEntity meter, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (meter.getMeter() == null || StringUtil.isEmpty(meter.getMeter().getId())) {
			meter.setMeter(null);
		}
		if (StringUtil.isNotEmpty(meter.getId())) {
			message = "更新成功";
			MeterEntity t = meterService.get(MeterEntity.class, meter.getId());
			try {
				
				MyBeanUtils.copyBeanNotNull2Bean(meter, t);
				
				meterService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			meterService.save(meter);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}
	
	/**
	 * 表具管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(MeterEntity meter, HttpServletRequest req) {
		List<RoomEntity> roomlist = systemService.getList(RoomEntity.class);
		req.setAttribute("roomlist", roomlist);
		
		if (StringUtil.isNotEmpty(meter.getId())) {
			meter = meterService.getEntity(MeterEntity.class, meter.getId());
			req.setAttribute("meterPage", meter);
		}
		return new ModelAndView("nhjkpt/configmanage/metermanage/meter");
	}
	/**
	 * 电路图
	 * @param meter
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "waterDiagram")
	public ModelAndView waterDiagram (String roomid, HttpServletRequest req) {
		req.setAttribute("roomid", roomid);
		return new ModelAndView("nhjkpt/configmanage/metermanage/waterDiagram");
	}
	@RequestMapping(params = "queryMeters")
	@ResponseBody
	public List<Map<String,Object>> queryMeters(String roomid, HttpServletRequest req){
		String hql=" from MeterEntity where roomid= ? and meterid not like ? and meterid not like ? order by level,meterid";
		List<MeterEntity> list=meterService.findHql(hql,systemService.get(RoomEntity.class, roomid).getRoomid().toString(),CommonUtil.WATER_CODE,CommonUtil.CALORIE_CODE);
		List<Map<String,Object>> listMeter=new ArrayList<Map<String,Object>>();
		Calendar calendar=Calendar.getInstance();
		Map<String,Object> map=null;
		for(MeterEntity meter:list){
			map=new HashMap<String, Object>();
			map.put("id", meter.getId());
			map.put("level", meter.getLevel());
			map.put("meterid", meter.getMeterid());
			map.put("metertext", meter.getMetertext());
			map.put("roomid", meter.getRoomid());
			map.put("alarm", meter.getAlarm());
			//单个表具当天能耗
			try{
				map.put("daysum", meterService.queryMeterHourNum(calendar,meter.getMeterid()));
			}catch(Exception e){
				map.put("daysum", "0");
			}
			if(meter.getMeter()!=null){
				map.put("parentid", meter.getMeter().getId());
			}else{
				map.put("parentid", "-1");
			}
			listMeter.add(map);
		}
		return listMeter;
	}
	/**
	 * 查询水表的电路图
	 * @param roomid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "queryWaterMeters")
	@ResponseBody
	public List<Map<String,Object>> queryWaterMeters(String roomid, HttpServletRequest req){
		String hql=" from MeterEntity where roomid=? and meterid like ? order by level,meterid";
		List<MeterEntity> list=meterService.findHql(hql,systemService.get(RoomEntity.class, roomid).getRoomid().toString(),CommonUtil.WATER_CODE);
		List<Map<String,Object>> listMeter=new ArrayList<Map<String,Object>>();
		Calendar calendar=Calendar.getInstance();
		Map<String,Object> map=null;
		for(MeterEntity meter:list){
			map=new HashMap<String, Object>();
			map.put("id", meter.getId());
			map.put("level", meter.getLevel());
			map.put("meterid", meter.getMeterid());
			map.put("metertext", meter.getMetertext());
			map.put("roomid", meter.getRoomid());
			map.put("alarm", meter.getAlarm());
			//单个表具当天能耗
			try{
				map.put("daysum", meterService.queryMeterHourNum(calendar,meter.getMeterid()));
			}catch(Exception e){
				map.put("daysum", "0");
			}
			if(meter.getMeter()!=null){
				map.put("parentid", meter.getMeter().getId());
			}else{
				map.put("parentid", "-1");
			}
			listMeter.add(map);
		}
		return listMeter;
	}
	
	//下拉框选择树 combotree
	@RequestMapping(params = "meterTree")
	@ResponseBody
	public List<ComboTree> meterTree(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(MeterEntity.class);
		
		if (comboTree.getId() != null) {
			cq.eq("meter.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("meter");
		}

		cq.add();
		List<MeterEntity> meterList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "metertext", "meters");
		comboTrees = systemService.ComboTree(meterList, comboTreeModel,null);
		return comboTrees;
	}
	
	
	/**
	 * 树形表格
	 */
	@RequestMapping(params = "treeGrid")
	@ResponseBody
	public List<TreeGrid> treeGrid(HttpServletRequest request, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(MeterEntity.class);
		if (treegrid.getId() != null) {
			cq.eq("meter.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("meter");
		}
		cq.add();
		List<MeterEntity> meterList = systemService.getListByCriteriaQuery(cq, false);
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("metertext");
		treeGridModel.setParentText("meter_metertext");
		treeGridModel.setParentId("meter_id");
		treeGridModel.setIdField("id");
		
		treeGridModel.setSrc("roomid");
		treeGridModel.setOrder("meterid");
		
		treeGridModel.setChildList("meters");
		List<TreeGrid> treeGrids = systemService.treegrid(meterList, treeGridModel);
		return treeGrids;
	}
	
	
	/**
	 * 表具标识号是否唯一
	 * 
	 * @param meterid
	 * @return
	 */
	@RequestMapping(params = "checkMeterid")
	@ResponseBody
	public ValidForm checkMeterid(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String meterid=oConvertUtils.getString(request.getParameter("param"));
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			MeterEntity meter=systemService.getEntity(MeterEntity.class, id);
			if(meter!=null){
				code=meter.getMeterid();
			}
		}
		List<MeterEntity> meters=systemService.findByProperty( MeterEntity.class ,"meterid",meterid);
		if(meters.size()>0&&!code.equals(meterid))
		{
			v.setInfo("此表具标识号已存在");
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
	@RequestMapping(params = "checkMetertext")
	@ResponseBody
	public ValidForm checkMetertext(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String param=oConvertUtils.getString(request.getParameter("param"));
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			MeterEntity meter=systemService.getEntity(MeterEntity.class, id);
			if(meter!=null){
				code=meter.getMetertext();
			}
		}
		List<MeterEntity> resulList=systemService.findByProperty( MeterEntity.class ,"metertext",param);
		if(resulList.size()>0&&!code.equals(param))
		{
			v.setInfo("此表具名称已存在");
			v.setStatus("n");
		}
		return v;
	}
	/**
	 * 表具列表选择页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "metersChoose")
	public String metersChoose() {
		return "nhjkpt/configmanage/metermanage/metersChoose";
	}
	/**
	 * 显示瞬时值
	 * @param meter
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "showdata")
	public ModelAndView showdata(String id,HttpServletRequest req) {
		List<String> listshow=new ArrayList<String>();
		Map<String, Object> buildingmap=CommonUtil.showmap;
		Map<String,Object> mapmeter=null;
		Map<String,Object> mapfunc=null;
		Iterator iterfunc=null;
		MeterEntity meter=meterService.getEntity(MeterEntity.class, id);
		FuncEntity func=null;
		if(buildingmap!=null){
			Iterator iter=buildingmap.keySet().iterator();
			if(!iter.hasNext()){
				listshow.add(meter.getMetertext()+":"+CommonUtil.SHOW_TEXT_NULL);
			}
			while(iter.hasNext()){
				mapmeter=(Map<String, Object>) buildingmap.get(iter.next());
				if(mapmeter!=null){
					mapfunc=(Map<String, Object>) mapmeter.get(meter.getMeterid());
					if(mapfunc!=null){
						List<String> listTemp=new ArrayList<String>();
						iterfunc=mapfunc.keySet().iterator();
						
						while(iterfunc.hasNext()){
							
							
							func=systemService.findByProperty(FuncEntity.class,"funcid", Integer.parseInt(iterfunc.next().toString())).get(0);
							//System.out.println("功能id====:"+func.getFuncname());
							listTemp.add(func.getFuncname()+":"+(mapfunc.get(func.getFuncid().toString())==null?"0":mapfunc.get(func.getFuncid().toString())));
							Collections.sort(listTemp);
						}
						//把listTemp拍好序后添加到listshow中
						listshow.addAll(listTemp);
					}
				}else{
					listshow.add(meter.getMetertext()+":"+CommonUtil.SHOW_TEXT_NULL);
				}
			}
		}else{
			listshow.add(meter.getMetertext()+":"+CommonUtil.SHOW_TEXT_NULL);
		}
		if(listshow.size()==0){
			listshow.add(meter.getMetertext()+":"+CommonUtil.SHOW_TEXT_NULL);
		}
		req.setAttribute("listshow", listshow);
		req.setAttribute("id", id);
		return new ModelAndView("nhjkpt/configmanage/metermanage/showdata");
	}
	/**
	 * 表具管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "treeWater")
	@ResponseBody
	public List<ComboTree> treeWater(HttpServletRequest req) {
		List<RoomEntity> listRoom=systemService.findHql("select r from RoomEntity r where r.roomid in (select m.roomid from MeterEntity m where m.meterid like ?)",CommonUtil.WATER_CODE);
		List<ComboTree> comboTrees=new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "roomtext", "");
		comboTrees = systemService.ComboTree(listRoom, comboTreeModel,null);
		return comboTrees;
	}
	/**
	 * 进入水表主页面
	 * @param room
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "waterhome")
	public ModelAndView waterhome(String id, HttpServletRequest req) {
		RoomEntity room=systemService.get(RoomEntity.class, id);
		req.setAttribute("room", room);
		return new ModelAndView("nhjkpt/configmanage/metermanage/waterhome");
	}
	/**
	 * 热量管理
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "treeCalorie")
	@ResponseBody
	public List<ComboTree> treeCalorie(HttpServletRequest req) {
		List<RoomEntity> listRoom=systemService.findHql("select r from RoomEntity r where r.roomid in (select m.roomid from MeterEntity m where m.meterid like ?)",CommonUtil.CALORIE_CODE);
		List<ComboTree> comboTrees=new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "roomtext", "");
		comboTrees = systemService.ComboTree(listRoom, comboTreeModel,null);
		return comboTrees;
	}
	/**
	 * 热量主页面
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "caloriehome")
	public ModelAndView caloriehome(String id, HttpServletRequest req) {
		RoomEntity room=systemService.get(RoomEntity.class, id);
		req.setAttribute("room", room);
		return new ModelAndView("nhjkpt/configmanage/metermanage/caloriehome");
	}
	/**
	 * 热量图
	 * @param roomid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "calorieDiagram")
	public ModelAndView calorieDiagram (String roomid, HttpServletRequest req) {
		req.setAttribute("roomid", roomid);
		return new ModelAndView("nhjkpt/configmanage/metermanage/calorieDiagram");
	}
	/**
	 * 获取热表
	 * @param roomid
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "queryCalorieMeters")
	@ResponseBody
	public List<Map<String,Object>> queryCalorieMeters(String roomid, HttpServletRequest req){
		String hql=" from MeterEntity where roomid=? and meterid like ? order by level,meterid";
		List<MeterEntity> list=meterService.findHql(hql,systemService.get(RoomEntity.class, roomid).getRoomid().toString(),CommonUtil.CALORIE_CODE);
		List<Map<String,Object>> listMeter=new ArrayList<Map<String,Object>>();
		Calendar calendar=Calendar.getInstance();
		Map<String,Object> map=null;
		for(MeterEntity meter:list){
			map=new HashMap<String, Object>();
			map.put("id", meter.getId());
			map.put("level", meter.getLevel());
			map.put("meterid", meter.getMeterid());
			map.put("metertext", meter.getMetertext());
			map.put("roomid", meter.getRoomid());
			map.put("alarm", meter.getAlarm());
			//单个表具当天能耗
			try{
				map.put("daysum", meterService.queryMeterHourNum(calendar,meter.getMeterid()));
			}catch(Exception e){
				map.put("daysum", "0");
			}
			if(meter.getMeter()!=null){
				map.put("parentid", meter.getMeter().getId());
			}else{
				map.put("parentid", "-1");
			}
			listMeter.add(map);
		}
		return listMeter;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
