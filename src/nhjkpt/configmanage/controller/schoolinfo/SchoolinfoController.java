package nhjkpt.configmanage.controller.schoolinfo;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.framework.core.common.model.json.ValidForm;
import org.framework.core.constant.Globals;
import org.framework.core.util.ResourceUtil;
import org.framework.core.util.StringUtil;
import org.framework.core.util.oConvertUtils;
import org.framework.tag.core.easyui.TagUtil;
import org.framework.tag.vo.easyui.ComboTreeModel;

import nhjkpt.system.pojo.base.TSDepart;
import nhjkpt.system.pojo.base.TSUser;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingshowfunc.BuildingShowFuncEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.roommanage.RoomEntity;
import nhjkpt.configmanage.entity.schoolinfo.SchoolinfoEntity;
import nhjkpt.configmanage.entity.schoolshowfunc.SchoolShowFuncEntity;
import nhjkpt.configmanage.service.schoolhoursum.SchoolhoursumServiceI;
import nhjkpt.configmanage.service.schoolinfo.SchoolinfoServiceI;

/**   
 * @Title: Controller
 * @Description: 学校基本信息配置
 * @author zhangdaihao
 * @date 2013-07-20 17:52:22
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/schoolinfoController")
public class SchoolinfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchoolinfoController.class);

	@Autowired
	private SchoolinfoServiceI schoolinfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SchoolhoursumServiceI schoolhoursumService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 学校基本信息配置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "schoolinfo")
	public ModelAndView schoolinfo(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/schoolinfo/schoolinfoList");
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
	public void datagrid(SchoolinfoEntity schoolinfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchoolinfoEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, schoolinfo);
		this.schoolinfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除学校基本信息配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SchoolinfoEntity schoolinfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		schoolinfo = systemService.getEntity(SchoolinfoEntity.class, schoolinfo.getId());
		message = "删除成功";
		schoolinfoService.delete(schoolinfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加学校基本信息配置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SchoolinfoEntity schoolinfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(schoolinfo.getId())) {
			message = "更新成功";
			SchoolinfoEntity t = schoolinfoService.get(SchoolinfoEntity.class, schoolinfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(schoolinfo, t);
				schoolinfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			schoolinfoService.save(schoolinfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 学校基本信息配置列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SchoolinfoEntity schoolinfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(schoolinfo.getId())) {
			schoolinfo = schoolinfoService.getEntity(SchoolinfoEntity.class, schoolinfo.getId());
			req.setAttribute("schoolinfoPage", schoolinfo);
		}
		return new ModelAndView("nhjkpt/configmanage/schoolinfo/schoolinfo");
	}
	
	
	
	/**
	 * 检查学校名称是否唯一
	 * 
	 * @param funcid
	 * @return
	 */
	@RequestMapping(params = "checkSchoolname")
	@ResponseBody
	public ValidForm checkSchoolname(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String schoolname=oConvertUtils.getString(request.getParameter("param"));
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			SchoolinfoEntity school=systemService.getEntity(SchoolinfoEntity.class, id);
			if(school!=null){
				code=school.getSchoolname();
			}
		}
		List<SchoolinfoEntity> list=systemService.findByProperty( SchoolinfoEntity.class ,"schoolname",schoolname);
		if(list.size()>0&&!code.equals(schoolname))
		{
			v.setInfo("此学校名称已存在");
			v.setStatus("n");
		}
		return v;
	}
	@RequestMapping(params = "checkSchoolId")
	@ResponseBody
	public ValidForm checkSchoolId(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String schoolid=oConvertUtils.getString(request.getParameter("param"));
		
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			SchoolinfoEntity info=systemService.getEntity(SchoolinfoEntity.class, id);
			if(info!=null){
				code=info.getSchoolid();
			}
		}
		
		List<SchoolinfoEntity> list=systemService.findByProperty( SchoolinfoEntity.class ,"schoolid",schoolid);
		if(list.size()>0&&!code.equals(schoolid))
		{
			v.setInfo("此学校标志已存在");
			v.setStatus("n");
		}
		return v;
	}
	/**
	 * 首页显示学校基本信息
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "home")
	public ModelAndView home(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		Double waterUse=0.0,waterLimit=0.0,waterRatio=0.0;
		Double electricUse=0.0,electricLimit=0.0,electricRatio=0.0,co2=0.0;
		Double gasUse=0.0,gasLimit=0.0,gasRatio=0.0;
		Double heatUse=0.0,heatLimit=0.0,heatRatio=0.0;
		
		List<SchoolinfoEntity> list=systemService.findHql(" from SchoolinfoEntity");
		if(list!=null&&list.size()!=0){
			SchoolinfoEntity schoolinfo=list.get(0);
			
			FuncEntity funcWater=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "水量");
			if(funcWater!=null){
				waterUse=schoolinfoService.getUseNhThisYear(funcWater.getFuncid().toString());
			}
			waterLimit=schoolinfo.getWaterlimit();
			if(waterLimit!=null && waterLimit>0){
				BigDecimal bg = new BigDecimal(waterUse/waterLimit*100);
				waterRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			
			
			FuncEntity funcElectric=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "电量");
			if(funcElectric!=null){
				electricUse=schoolinfoService.getUseNhThisYear(funcElectric.getFuncid().toString());
			}
			BigDecimal bg1 = new BigDecimal(electricUse*0.785);
			co2 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			electricLimit=schoolinfo.getElectriclimit();
			if(electricLimit!=null && electricLimit>0){
				BigDecimal bg = new BigDecimal(electricUse/electricLimit*100);
				electricRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			
			FuncEntity funcGas=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "气量");
			if(funcGas!=null){
				gasUse=schoolinfoService.getUseNhThisYear(funcGas.getFuncid().toString());
			}
			gasLimit=schoolinfo.getGaslimit();
			if(gasLimit!=null && gasLimit>0){
				BigDecimal bg = new BigDecimal(gasUse/gasLimit*100);
				gasRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			
			FuncEntity funcHeat=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "热量");
			if(funcHeat!=null){
				heatUse=schoolinfoService.getUseNhThisYear(funcHeat.getFuncid().toString());
			}
			heatLimit=schoolinfo.getHeatlimit();
			if(heatLimit!=null && heatLimit>0){
				BigDecimal bg = new BigDecimal(heatUse/heatLimit*100);
				heatRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
				
			request.setAttribute("waterUse", waterUse);
			request.setAttribute("waterLimit", waterLimit);
			request.setAttribute("waterRatio", waterRatio);
			
			request.setAttribute("electricUse", electricUse);
			request.setAttribute("electricLimit", electricLimit);
			request.setAttribute("electricRatio", electricRatio);
			request.setAttribute("co2", co2);
			
			request.setAttribute("gasUse", gasUse);
			request.setAttribute("gasLimit", gasLimit);
			request.setAttribute("gasRatio", gasRatio);
			
			request.setAttribute("heatUse", heatUse);
			request.setAttribute("heatLimit", heatLimit);
			request.setAttribute("heatRatio", heatRatio);
			
			request.setAttribute("schoolinfo",schoolinfo);
		}
		List<String> listshow=new ArrayList<String>();
		Map<String, Object> buildingmap=CommonUtil.showmap;
		Map<String,Object> mapmeter=null;
		Map<String,Object> mapfunc=null;
		List<SchoolShowFuncEntity> listfunc=systemService.getList(SchoolShowFuncEntity.class);
		for(SchoolShowFuncEntity show:listfunc){
			if(buildingmap!=null){
				Set set=buildingmap.keySet();
				Iterator iter=set.iterator();
				if(!iter.hasNext()){
					listshow.add(show.getShowtext()+":"+CommonUtil.SHOW_TEXT_NULL);
				}
				while(iter.hasNext()){
					mapmeter=(Map<String, Object>) buildingmap.get(iter.next());
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
			}else{
				listshow.add(show.getShowtext()+":"+CommonUtil.SHOW_TEXT_NULL);
			}
		}
		request.setAttribute("listshow", listshow);
		return new ModelAndView("main/schoolhome");
	}
	/**
	 * 学校列表选择页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "schools")
	public String schools() {
		return "nhjkpt/configmanage/schoolinfo/schoolsChoose";
	}
	
	
	/**
	 * maphome页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "maphome")
	public String maphome() {
		return "main/maphome";
	}
	
	
	@RequestMapping(params = "maphomeIframe")
	public String maphomeIframe() {
		return "main/maphomeIframe";
	}
	
	@RequestMapping(params = "tongji")
	public ModelAndView tongji(HttpServletRequest request)  {
		Double wUseToday=0.0,waterUse=0.0,waterLimit=0.0,waterRatio=0.0;
		Double eUseToday=0.0,electricUse=0.0,electricLimit=0.0,electricRatio=0.0,co2=0.0;
		Double gUseToday=0.0,gasUse=0.0,gasLimit=0.0,gasRatio=0.0;
		Double hUseToday=0.0,heatUse=0.0,heatLimit=0.0,heatRatio=0.0;
		Calendar calendar=Calendar.getInstance();
		
		List<SchoolinfoEntity> list=systemService.findHql(" from SchoolinfoEntity");
		if(list!=null&&list.size()!=0){
			SchoolinfoEntity schoolinfo=list.get(0);
			
			FuncEntity funcWater=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "水量");
			if(funcWater!=null){
				waterUse=schoolinfoService.getUseNhThisYear(funcWater.getFuncid().toString());
				wUseToday=schoolhoursumService.querySchoolhoursum(funcWater.getFuncid().toString(), calendar);
			}
			waterLimit=schoolinfo.getWaterlimit();
			if(waterLimit!=null && waterLimit>0){
				BigDecimal bg = new BigDecimal(waterUse/waterLimit*100);
				waterRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			
			
			FuncEntity funcElectric=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "电量");
			if(funcElectric!=null){
				electricUse=schoolinfoService.getUseNhThisYear(funcElectric.getFuncid().toString());
				eUseToday=schoolhoursumService.querySchoolhoursum(funcElectric.getFuncid().toString(), calendar);
			}
			BigDecimal bg1 = new BigDecimal(electricUse*0.785);
			co2 = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			electricLimit=schoolinfo.getElectriclimit();
			if(electricLimit!=null && electricLimit>0){
				BigDecimal bg = new BigDecimal(electricUse/electricLimit*100);
				electricRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			
			FuncEntity funcGas=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "气量");
			if(funcGas!=null){
				gasUse=schoolinfoService.getUseNhThisYear(funcGas.getFuncid().toString());
				gUseToday=schoolhoursumService.querySchoolhoursum(funcGas.getFuncid().toString(), calendar);
			}
			gasLimit=schoolinfo.getGaslimit();
			if(gasLimit!=null && gasLimit>0){
				BigDecimal bg = new BigDecimal(gasUse/gasLimit*100);
				gasRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			
			FuncEntity funcHeat=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "热量");
			if(funcHeat!=null){
				heatUse=schoolinfoService.getUseNhThisYear(funcHeat.getFuncid().toString());
				hUseToday=schoolhoursumService.querySchoolhoursum(funcHeat.getFuncid().toString(), calendar);
			}
			heatLimit=schoolinfo.getHeatlimit();
			if(heatLimit!=null && heatLimit>0){
				BigDecimal bg = new BigDecimal(heatUse/heatLimit*100);
				heatRatio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			
			BigDecimal bg = new BigDecimal(electricUse*0.5+waterUse*4+heatUse*23);
	        double useMoneyYear = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			
			
				
			request.setAttribute("wUseToday", wUseToday);
			request.setAttribute("waterUse", waterUse);
			request.setAttribute("waterLimit", waterLimit);
			request.setAttribute("waterRatio", waterRatio);
			
			request.setAttribute("eUseToday", eUseToday);
			request.setAttribute("electricUse", electricUse);
			request.setAttribute("electricLimit", electricLimit);
			request.setAttribute("electricRatio", electricRatio);
			request.setAttribute("co2", co2);
			
			request.setAttribute("gUseToday", gUseToday);
			request.setAttribute("gasUse", gasUse);
			request.setAttribute("gasLimit", gasLimit);
			request.setAttribute("gasRatio", gasRatio);
			
			request.setAttribute("hUseToday", hUseToday);
			request.setAttribute("heatUse", heatUse);
			request.setAttribute("heatLimit", heatLimit);
			request.setAttribute("heatRatio", heatRatio);
			
			request.setAttribute("useMoneyYear", useMoneyYear);
			
			request.setAttribute("schoolinfo",schoolinfo);
		}
		
		
		return new ModelAndView("main/tongji");
		
	}
	/**
	 * 获取学校选择分页列表
	 * @param school
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridSchool")
	public void datagridSchool(SchoolinfoEntity school,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SchoolinfoEntity.class, dataGrid);
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, school);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 进入学校主页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "schoolMain")
	public String schoolMain(HttpServletRequest request, HttpServletResponse response) {
		List<SchoolinfoEntity> list=systemService.getList(SchoolinfoEntity.class);
		if(list!=null&&list.size()!=0){
			request.setAttribute("schoolinfo", list.get(0));
		}
		return "nhjkpt/configmanage/schoolinfo/schoolMain";
	}
	/**
	 * 进入学校主页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "schoollogin")
	public String schoollogin(HttpServletRequest request, HttpServletResponse response) {
		List<SchoolinfoEntity> list=systemService.getList(SchoolinfoEntity.class);
		if(list!=null&&list.size()!=0){
			request.setAttribute("schoolinfo", list.get(0));
		}
		return "login/schoollogin";
	}
	/**
	 * 获取瞬时信息值
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loaddata")
	@ResponseBody
	public List<String> loaddata(HttpServletRequest request) {
		List<String> listshow=new ArrayList<String>();
		Map<String, Object> buildingmap=CommonUtil.showmap;
		Map<String,Object> mapmeter=null;
		Map<String,Object> mapfunc=null;
		List<SchoolShowFuncEntity> listfunc=systemService.getList(SchoolShowFuncEntity.class);
		for(SchoolShowFuncEntity show:listfunc){
			if(buildingmap!=null){
				Set set=buildingmap.keySet();
				Iterator iter=set.iterator();
				if(!iter.hasNext()){
					listshow.add(show.getShowtext()+":"+CommonUtil.SHOW_TEXT_NULL);
				}
				while(iter.hasNext()){
					mapmeter=(Map<String, Object>) buildingmap.get(iter.next());
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
			}else{
				listshow.add(show.getShowtext()+":"+CommonUtil.SHOW_TEXT_NULL);
			}
		}
		return listshow;
	}	
	/**
	 * 打开用能排名页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "schoolEnergySort")
	public String schoolEnergySort(HttpServletRequest request, HttpServletResponse response) {
		return "nhjkpt/configmanage/schoolinfo/schoolEnergySort";
	}
	/**
	 * 右下角
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "schoolEnergySortCC")
	public String schoolEnergySortSE(HttpServletRequest request, HttpServletResponse response) {
		return "nhjkpt/configmanage/schoolinfo/schoolEnergySortCC";
	}
	/**
	 * 左下角
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "schoolEnergySortCE")
	public String schoolEnergySortCE(HttpServletRequest request, HttpServletResponse response) {
		return "nhjkpt/configmanage/schoolinfo/schoolEnergySortCE";
	}
	/**
	 * 右上角
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "schoolEnergySortNE")
	public String schoolEnergySortNE(HttpServletRequest request, HttpServletResponse response) {
		return "nhjkpt/configmanage/schoolinfo/schoolEnergySortNE";
	}
	/**
	 * 左上角
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "schoolEnergySortNC")
	public String schoolEnergySortNC(HttpServletRequest request, HttpServletResponse response) {
		return "nhjkpt/configmanage/schoolinfo/schoolEnergySortNC";
	}
	
	/**
	 * 打开建筑物坐标设置页面
	 * @param x
	 * @param y
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "mapSetPoint")
	public ModelAndView mapSetPoint(String lng,String lat,HttpServletRequest req){
		List<RoomEntity> listRoom=systemService.getList(RoomEntity.class);
		RoomEntity room0=new RoomEntity();
		listRoom.add(0, room0);
		
		List<MeterEntity> listMeter=systemService.findByQueryString("from MeterEntity where  meterid like '9%' or meterid like '8%'");
		MeterEntity meter0=new MeterEntity();
		listMeter.add(0,meter0);
		
		
		//根据经度纬度查找设置的配电室或者表具
		RoomEntity setRoom=null;
		List<RoomEntity> setRooms=systemService.findByQueryString("from RoomEntity where  lng="+lng+" and lat="+lat);
		if(setRooms.size()>0){
			setRoom=setRooms.get(0);
		}
		
		//根据经度纬度查找设置的配电室或者表具
		MeterEntity setMeter=null;
		List<MeterEntity> setMeters=systemService.findByQueryString("from MeterEntity where  lng="+lng+" and lat="+lat);
		if(setMeters.size()>0){
			setMeter=setMeters.get(0);
		}
		
		req.setAttribute("listRoom", listRoom);
		req.setAttribute("listMeter", listMeter);
		req.setAttribute("setRoom", setRoom);
		req.setAttribute("setMeter", setMeter);
		req.setAttribute("lng", lng);
		req.setAttribute("lat", lat);
		return new ModelAndView("nhjkpt/configmanage/schoolinfo/mapSetPoint");
	}
	
	
	@RequestMapping(params = "queryRooms")
	@ResponseBody
	public List<RoomEntity> queryRooms(HttpServletRequest request){
		
		List<RoomEntity> listRoom=systemService.findByQueryString("from RoomEntity where lng is not null and lat is not null");
		for (RoomEntity room : listRoom) {
			RoomEntity joinroom=systemService.findUniqueByProperty(RoomEntity.class, "roomid", room.getJoinroomid());
			if(joinroom!=null){
				room.setJoinlat(joinroom.getLat());
				room.setJoinlng(joinroom.getLng());
			}
		}
		
		
		return listRoom;
	}
	
	
	@RequestMapping(params = "queryWaterMeters")
	@ResponseBody
	public List<MeterEntity> queryWaterMeters(HttpServletRequest request){
		
		List<MeterEntity> listWaterMeter=systemService.findByQueryString("from MeterEntity where lng is not null and lat is not null and meterid like '9%'");
		
		for (MeterEntity meter : listWaterMeter) {
			MeterEntity joinmeter=systemService.findUniqueByProperty(MeterEntity.class, "meterid", meter.getJoinmeterid());
			if(joinmeter!=null){
				meter.setJoinlat(joinmeter.getLat());
				meter.setJoinlng(joinmeter.getLng());
			}
		}
		
		
		return listWaterMeter;
	}
	
	
	@RequestMapping(params = "queryHeatMeters")
	@ResponseBody
	public List<MeterEntity> queryHeatMeters(HttpServletRequest request){
		
		List<MeterEntity> listHeatMeter=systemService.findByQueryString("from MeterEntity where lng is not null and lat is not null and meterid like '8%'");
		
		for (MeterEntity meter : listHeatMeter) {
			MeterEntity joinmeter=systemService.findUniqueByProperty(MeterEntity.class, "meterid", meter.getJoinmeterid());
			if(joinmeter!=null){
				meter.setJoinlat(joinmeter.getLat());
				meter.setJoinlng(joinmeter.getLng());
			}
		}
		
		return listHeatMeter;
	}
	
	
	@RequestMapping(params = "saveMapPoint")
	@ResponseBody
	public String saveMapPoint(String roomid,String joinroomid,String meterid,String joinmeterid,String lng,String lat,HttpServletRequest req) {
		
		//保存前先删除该点在room或者表具的关联
		String sql="update room set lng=null ,lat=null where lng="+lng+"and lat="+lat;
		String sql1="update meter set lng=null ,lat=null where lng="+lng+"and lat="+lat;
		systemService.executeSql(sql);
		systemService.executeSql(sql1);
		
		if(roomid!=null && roomid.length()>0){
			RoomEntity room=systemService.get(RoomEntity.class, roomid);
			if(room!=null){
				room.setLng(Double.parseDouble(lng));
				room.setLat(Double.parseDouble(lat));
				if(joinroomid!=null && joinroomid.length()>0){
					room.setJoinroomid(Integer.parseInt(joinroomid));
				}else{
					room.setJoinroomid(null);
				}
				
				systemService.save(room);			
			}
		}
		
		if(meterid!=null && meterid.length()>0){
			MeterEntity meter=systemService.get(MeterEntity.class, meterid);
			if(meter!=null){
				meter.setLng(Double.parseDouble(lng));
				meter.setLat(Double.parseDouble(lat));
				if(joinmeterid!=null && joinmeterid.length()>0){
					meter.setJoinmeterid(joinmeterid);
				}else{
					meter.setJoinmeterid(null);
				}
				systemService.save(meter);		
			}
			
			
		}
		
		return "保存成功";
		
	}
	
}
