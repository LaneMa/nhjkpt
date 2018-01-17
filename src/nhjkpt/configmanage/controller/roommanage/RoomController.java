package nhjkpt.configmanage.controller.roommanage;
import java.util.ArrayList;
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
import org.framework.core.common.model.json.ComboTree;
import org.framework.core.common.model.json.DataGrid;
import org.framework.core.common.model.json.ValidForm;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.core.util.oConvertUtils;
import org.framework.tag.core.easyui.TagUtil;
import org.framework.tag.vo.easyui.ComboTreeModel;

import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.MyBeanUtils;

import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.roommanage.RoomEntity;
import nhjkpt.configmanage.service.metermanage.MeterServiceI;
import nhjkpt.configmanage.service.roommanage.RoomServiceI;
import nhjkpt.configmanage.service.schoolinfo.SchoolinfoServiceI;

/**   
 * @Title: Controller
 * @Description: 配电房管理
 * @author zhangdaihao
 * @date 2013-07-10 21:56:41
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/roomController")
public class RoomController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RoomController.class);

	@Autowired
	private RoomServiceI roomService;
	@Autowired
	private MeterServiceI meterService;
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private SchoolinfoServiceI schoolinfoService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 配电房管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "room")
	public ModelAndView room(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/roommanage/roomList");
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
	public void datagrid(RoomEntity room,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RoomEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, room);
		this.roomService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除配电房管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(RoomEntity room, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		room = systemService.getEntity(RoomEntity.class, room.getId());
		message = "删除成功";
		roomService.delete(room);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加配电房管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(RoomEntity room, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(room.getId())) {
			message = "更新成功";
			RoomEntity t = roomService.get(RoomEntity.class, room.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(room, t);
				roomService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			roomService.save(room);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 配电房管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(RoomEntity room, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(room.getId())) {
			room = roomService.getEntity(RoomEntity.class, room.getId());
			req.setAttribute("roomPage", room);
		}
		return new ModelAndView("nhjkpt/configmanage/roommanage/room");
	}
	
	
	/**
	 * 表具标识号是否唯一
	 * 
	 * @param funcid
	 * @return
	 */
	@RequestMapping(params = "checkRoomid")
	@ResponseBody
	public ValidForm checkRoomid(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		int roomid=oConvertUtils.getInt(request.getParameter("param"));
		
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			RoomEntity room=systemService.getEntity(RoomEntity.class, id);
			if(room!=null){
				code=oConvertUtils.getString(room.getRoomid());
			}
		}
		
		
		List<RoomEntity> func=systemService.findByProperty( RoomEntity.class ,"roomid",roomid);
		if(func.size()>0&&!code.equals(oConvertUtils.getString(roomid)))
		{
			v.setInfo("此配电房标识号已存在");
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
	@RequestMapping(params = "checkRoomname")
	@ResponseBody
	public ValidForm checkRoomname(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String roomname=oConvertUtils.getString(request.getParameter("param"));
		
		String id=oConvertUtils.getString(request.getParameter("id"));
		String code="";
		if(!CommonUtil.isNull(id)){
			RoomEntity room=systemService.getEntity(RoomEntity.class, id);
			if(room!=null){
				code=room.getRoomtext();
			}
		}
		List<RoomEntity> func=systemService.findByProperty( RoomEntity.class ,"roomtext",roomname);
		if(func.size()>0&&!code.equals(roomname))
		{
			v.setInfo("此配电房名称已存在");
			v.setStatus("n");
		}
		return v;
	}
	@RequestMapping(params = "treeRoom")
	@ResponseBody
	public List<ComboTree> treeRoom(HttpServletRequest request){
		List<RoomEntity> listRoom=systemService.findHql("select r from RoomEntity r where r.roomid in (select m.roomid from MeterEntity m where m.meterid not like ? and m.meterid not like ?)",CommonUtil.WATER_CODE,CommonUtil.CALORIE_CODE);
		List<ComboTree> comboTrees=new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "roomtext", "");
		comboTrees = systemService.ComboTree(listRoom, comboTreeModel,null);
		return comboTrees;
	}
	/**
	 * 进入房间主页面
	 * @param room
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "roomhome")
	public ModelAndView roomhome(String id, HttpServletRequest req) {
		RoomEntity room=systemService.get(RoomEntity.class, id);
		Double eUse=0.0,eUseYear=0.0;
		Integer meternum=10,meterOknum=0,meterErrornum=0;
		meternum=meterService.getMetersByRoom(room.getRoomid());
		meterOknum=meterService.getOkMetersByRoom(room.getRoomid());
		meterErrornum=meterService.getErrorMetersByRoom(room.getRoomid());
		eUse=meterService.getUseNhTodayByRoom(room.getRoomid());
		FuncEntity funcElectric=systemService.findUniqueByProperty(FuncEntity.class, "showtext", "电量");
		if(funcElectric!=null){
			eUseYear=schoolinfoService.getUseNhThisYear(funcElectric.getFuncid().toString());
		}
		
		
		
		req.setAttribute("eUse", eUse);
		req.setAttribute("eUseYear", eUseYear);
		req.setAttribute("meternum", meternum);
		req.setAttribute("meterOknum", meterOknum);
		req.setAttribute("meterErrornum", meterErrornum);
		req.setAttribute("room", room);
		return new ModelAndView("nhjkpt/configmanage/roommanage/roomhome");
	}
	
	/**
	 * 电路图
	 * @param meter
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "circuitDiagram")
	public ModelAndView circuitDiagram (String roomid, HttpServletRequest req) {
		req.setAttribute("roomid", roomid);
		return new ModelAndView("nhjkpt/configmanage/roommanage/circuitDiagram");
	}
	
}
