package nhjkpt.configmanage.controller.metershowhistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.meterfuncshowhistory.MeterFuncShowHistoryEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.metershowhistory.MeterShowHistoryEntity;
import nhjkpt.configmanage.service.metershowhistory.MeterShowHistoryServiceI;
import nhjkpt.system.service.SystemService;

import org.framework.core.common.controller.BaseController;
import org.framework.core.common.hibernate.qbc.CriteriaQuery;
import org.framework.core.common.model.json.DataGrid;
import org.framework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/meterShowHistoryControl")
public class MeterShowHistoryControl extends BaseController{

	@Autowired
	private SystemService systemService;
	@Autowired
	private MeterShowHistoryServiceI meterShowHistoryService;
	
	@RequestMapping(params = "meterShowHistory")
	public ModelAndView meterShowHistory(String id,HttpServletRequest request) {
		request.setAttribute("id", id);
		String funcReplace = "";
		List<FuncEntity> funcList = systemService.getList(FuncEntity.class);
		for (FuncEntity func : funcList) {
			if (funcReplace.length() > 0) {
				funcReplace += ",";
			}
			funcReplace += func.getFuncname() + "_" + func.getFuncid();
		}
		request.setAttribute("funcReplace", funcReplace);
		String meterReplace = "";
		List<MeterEntity> meterList = systemService.getList(MeterEntity.class);
		for (MeterEntity meter : meterList) {
			if (meterReplace.length() > 0) {
				meterReplace += ",";
			}
			meterReplace += meter.getMetertext() + "_" + meter.getMeterid();
		}
		request.setAttribute("meterReplace", meterReplace);
		return new ModelAndView("nhjkpt/configmanage/metershowhistory/meterShowHistoryList");
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
	public void datagrid(String id,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		MeterShowHistoryEntity meterShowHistory=new MeterShowHistoryEntity();
		meterShowHistory.setMeterid(id);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("senddate", "desc");
		CriteriaQuery cq = new CriteriaQuery(MeterFuncShowHistoryEntity.class, dataGrid);
		cq.setOrder(map);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, meterShowHistory);
		this.meterShowHistoryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
}
