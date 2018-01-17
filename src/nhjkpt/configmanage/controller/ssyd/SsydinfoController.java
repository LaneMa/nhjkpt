package nhjkpt.configmanage.controller.ssyd;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.framework.core.constant.Globals;
import org.framework.core.util.ResourceUtil;
import org.framework.core.util.StringUtil;
import org.framework.tag.core.easyui.TagUtil;
import org.framework.tag.vo.easyui.ComboTreeModel;

import nhjkpt.system.pojo.base.TSTypegroup;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.HibernateConfiguration;
import nhjkpt.system.util.SsydSessionFactory;
import nhjkpt.system.util.TempSessionFactory;

import org.framework.core.util.MyBeanUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.originadata.OriginadataEntity;
import nhjkpt.configmanage.entity.pointdata.PointdataEntity;
import nhjkpt.configmanage.entity.systemlink.SystemLinkEntity;
import nhjkpt.configmanage.service.systemlink.SystemLinkServiceI;

/**   
 * @Title: Controller
 * @Description: 宿舍用电接口
 * @author qf
 * @date 2014-11-16 21:03:16
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/ssydinfoController")
public class SsydinfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SsydinfoController.class);


	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Session getSession(){
		
		Session session=null;
		HibernateConfiguration dyConfiguration=null;
		OriginadataEntity orgin=new OriginadataEntity();
		orgin.setDbtype("sqlserver");
		orgin.setDbip(ResourceUtil.getConfigByName("sqlserver_ip"));
		orgin.setDbname(ResourceUtil.getConfigByName("sqlserver_db"));
		orgin.setDbuser(ResourceUtil.getConfigByName("sqlserver_user"));
		orgin.setDbpwd(ResourceUtil.getConfigByName("sqlserver_pwd"));
		dyConfiguration=new HibernateConfiguration(orgin);

		SsydSessionFactory.reflashSessionFactory(dyConfiguration); 
		session=SsydSessionFactory.getSession();
		return session;
		
	}

	@RequestMapping(params = "treeSsyd")
	@ResponseBody
	public List<ComboTree> treeSsyd(HttpServletRequest request){
		
		Session session=this.getSession();
		//获取sqlserver列表宿舍楼列表保存到listmap中
		String sql="select distinct isnull(SUBSTRING(地址,1,charindex('-',地址)-1),'') as buildingname  from meterinfo";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		
		List list=query.list();
		
		session.close();

		List<ComboTree> comboTrees=new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("buildingname", "buildingname", "");
		comboTrees = systemService.ComboTree(list, comboTreeModel,null);
		return comboTrees;
	}
	
	
	
	@RequestMapping(params = "layerTabs")
	public ModelAndView layerTabs(HttpServletRequest request) {
		String buildingname="";
		try {
			buildingname = new String(request.getParameter("id").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(buildingname);
		Session session=this.getSession();
		//获取sqlserver列表宿舍楼列表保存到listmap中
		String sql="select distinct isnull(SUBSTRING(地址,charindex('-',地址)+1,charindex('-',地址,charindex('-',地址)+1)-charindex('-',地址)-1),'')  as layername  from meterinfo where SUBSTRING(地址,1,charindex('-',地址)-1)='"+buildingname+"'";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		
		List layerList=query.list();
		
		session.close();
		
		request.setAttribute("buildingname", buildingname);
		
		request.setAttribute("layerList", layerList);

		return new ModelAndView("nhjkpt/ssyd/ssydTabs");
	}
	
	@RequestMapping(params = "ssList")
	public ModelAndView ssList(HttpServletRequest request) {
		String layername = "";
		try {
			layername = new String(request.getParameter("layername").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Session session=this.getSession();
		//获取sqlserver列表宿舍楼列表保存到listmap中
		String sql="select t.电表用户编号 as meterid,t.户名 as ssname,t.表号 as meterno,'' as useyd,'' as nouseyd,'' as cbdate from meterinfo t where 地址 like '"+layername+"%'";
		SQLQuery query = session.createSQLQuery(sql);
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		
		List<HashMap> ssList=query.list();
		
		for(HashMap ss:ssList){
			sql="select top 1 总用电量 as useyd,剩余电量 as nouseyd,convert(varchar,抄表时间,20) as cbdate from meterdata where 电表用户编号="+ss.get("meterid")+" order by 抄表时间 desc";
			SQLQuery ssQuery = session.createSQLQuery(sql);
			ssQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			List<HashMap> ssydList=ssQuery.list();
			for(HashMap ssyd:ssydList){
				ss.put("useyd", ssyd.get("useyd"));
				ss.put("nouseyd", ssyd.get("nouseyd"));
				ss.put("cbdate", ssyd.get("cbdate"));
			}
			
		}
		
		session.close();
		
		
		request.setAttribute("ssList", ssList);
		return new ModelAndView("nhjkpt/ssyd/ssydList");
	}

	
}
