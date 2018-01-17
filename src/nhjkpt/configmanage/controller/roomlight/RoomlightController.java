package nhjkpt.configmanage.controller.roomlight;

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
import org.framework.core.common.model.json.ComboTree;
import org.framework.core.common.model.json.DataGrid;
import org.framework.core.constant.Globals;
import org.framework.core.util.StringUtil;
import org.framework.core.util.oConvertUtils;
import org.framework.tag.core.easyui.TagUtil;
import org.framework.tag.vo.easyui.ComboTreeModel;

import nhjkpt.system.service.SystemService;
import org.framework.core.util.MyBeanUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;

import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.roomlight.RoomlightEntity;
import nhjkpt.configmanage.service.roomlight.RoomlightServiceI;

/**   
 * @Title: Controller
 * @Description: 教室照明管理
 * @author qf
 * @date 2015-01-03 19:47:13
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/roomlightController")
public class RoomlightController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RoomlightController.class);

	@Autowired
	private RoomlightServiceI roomlightService;
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
	 * 教室照明管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "roomlight")
	public ModelAndView roomlight(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/roomlight/roomlightList");
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
	public void datagrid(RoomlightEntity roomlight,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(RoomlightEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, roomlight);
		this.roomlightService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除教室照明管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(RoomlightEntity roomlight, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		roomlight = systemService.getEntity(RoomlightEntity.class, roomlight.getId());
		message = "删除成功";
		roomlightService.delete(roomlight);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加教室照明管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(RoomlightEntity roomlight, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(roomlight.getId())) {
			message = "更新成功";
			RoomlightEntity t = roomlightService.get(RoomlightEntity.class, roomlight.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(roomlight, t);
				roomlightService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			roomlightService.save(roomlight);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 教室照明管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(RoomlightEntity roomlight, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(roomlight.getId())) {
			roomlight = roomlightService.getEntity(RoomlightEntity.class, roomlight.getId());
			req.setAttribute("roomlightPage", roomlight);
		}
		return new ModelAndView("nhjkpt/configmanage/roomlight/roomlight");
	}
	
	
	
	@RequestMapping(params = "treeRoomlight")
	@ResponseBody
	public List<ComboTree> treeRoomlight(HttpServletRequest request, ComboTree comboTree) {
		
		List<ComboTree> trees = new ArrayList<ComboTree>();
		Session session=systemService.getSession();
		String treeid=comboTree.getId();
		String level="-1";
		if(treeid!=null){
			
			level=treeid.split("::")[0];
			
			
		}
		
			
		if(level!=null && level.equals("1")){
			String buildingid=treeid.split("::")[1];
			String layerid=treeid.split("::")[2];
			
			String sql = "select distinct id,layerid,roomid from roomlight t where layerid="+layerid+" and buildingid="+buildingid+" order by roomid";
			
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			
			List<HashMap> roomlightList=query.list();

			for(HashMap item:roomlightList){
				Map<String, Object> attributes = new HashMap<String, Object>();
				
				ComboTree tree = new ComboTree();
				tree.setId("2::"+(String)item.get("id").toString());
				String roomid=item.get("roomid").toString();
				
				if(roomid.length()<=1){
					tree.setText(item.get("layerid")+"0"+roomid+"室");
				}else{
					tree.setText(item.get("layerid")+roomid+"室");
				}
				
				tree.setState("open");
				tree.setChecked(false);
				attributes.put("level", "2");
				tree.setAttributes(attributes);
				trees.add(tree);
			}
			
		}else if(level!=null && level.equals("2")){
			
			
			
		}else if(level!=null && level.equals("0")) {//0或者nul值时
			
			treeid=treeid.split("::")[1];
	
			String sql = "select distinct t.layerid as layerid from roomlight t where buildingid="+treeid+" order by layerid";
			
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			
			List<HashMap> roomlightList=query.list();

			for(HashMap item:roomlightList){
				Map<String, Object> attributes = new HashMap<String, Object>();
				
				ComboTree tree = new ComboTree();
				tree.setId("1::"+treeid+"::"+(String)item.get("layerid").toString());
				tree.setText(item.get("layerid")+"层");
				tree.setState("closed");
				tree.setChecked(false);
				attributes.put("level", "1");
				tree.setAttributes(attributes);
				trees.add(tree);
			}
			
		}else{
			
			String sql = "select distinct t.buildingid,t.buildingname from roomlight t order by buildingid ";
			
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			
			List<HashMap> roomlightList=query.list();

			for(HashMap item:roomlightList){
				Map<String, Object> attributes = new HashMap<String, Object>();
				
				ComboTree tree = new ComboTree();
				tree.setId("0::"+(String)item.get("buildingid").toString());
				tree.setText((String)item.get("buildingname"));
				tree.setState("closed");
				tree.setChecked(false);
				attributes.put("level", "0");
				tree.setAttributes(attributes);
				trees.add(tree);
			}
			
		}
		
		session.close();
		return trees;
	}
	
	
	@RequestMapping(params = "roomlightControl")
	public ModelAndView roomlightControl(RoomlightEntity roomlight, HttpServletRequest req) {
		
		String roomlightId=req.getParameter("id");
		if(roomlightId!=null){
			roomlightId=roomlightId.split("::")[1];
		}
		
		
		roomlight = roomlightService.getEntity(RoomlightEntity.class, roomlightId);
		
		String lightstate_bin=  Integer.toBinaryString(roomlight.getLightstate());
		if(lightstate_bin==null)
			lightstate_bin="";
		
		
		int b=8-lightstate_bin.length();
		for (int i = 0; i < b; i++) {
			lightstate_bin="0"+lightstate_bin;
		}
		
		
		
		req.setAttribute("roomlightPage", roomlight);
		req.setAttribute("lightstate_bin", lightstate_bin);
		
		
		return new ModelAndView("nhjkpt/configmanage/roomlight/roomlightControl");
	}
	
	

	
	@RequestMapping(params = "roomlightChange")
	@ResponseBody
	public AjaxJson roomlightChange(String id,String buildingname,String controltype,String controldata1,String controldata2, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		
		message = "修改成功";
		RoomlightEntity t = roomlightService.get(RoomlightEntity.class, id);
		try {
			t.setControltype(Integer.parseInt(controltype));
			t.setControldata1(Integer.parseInt(controldata1));
			t.setControldata2(Integer.parseInt(controldata2));
			roomlightService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			//发送udp到路由器
			this.roomlightService.SendUdp(t);
			
			
			String strBuildingname="";
			if(buildingname!=null){
				strBuildingname=new String(buildingname.getBytes("ISO8859-1"),"UTF-8");
			}
			
			Object[] param = new Object[] {strBuildingname,t.getBuildingid() };
			roomlightService.executeSql("update roomlight set buildingname=? where buildingid=?", param);
		} catch (Exception e) {
			e.printStackTrace();
			message="更新失败";
		}
		
		j.setMsg(message);
		return j;
	}
	
	
}
