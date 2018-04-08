package nhjkpt.configmanage.controller.ladderprice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.framework.core.common.controller.BaseController;
import org.framework.core.common.hibernate.qbc.CriteriaQuery;
import org.framework.core.common.model.json.AjaxJson;
import org.framework.core.common.model.json.DataGrid;
import org.framework.core.constant.Globals;
import org.framework.core.util.MyBeanUtils;
import org.framework.core.util.StringUtil;
import org.framework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import nhjkpt.configmanage.entity.ladderprice.LadderPriceEntity;
import nhjkpt.configmanage.service.impl.ladderprice.PriceTypeEmun;
import nhjkpt.configmanage.service.ladderprice.LadderPriceServiceI;
import nhjkpt.system.service.SystemService;

/**   
 * @Title: Controller
 * @Description: 阶梯价格
 * @author hexy
 * @date 2014-11-17 22:28:39
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/ladderpricecontroller")
public class LadderPriceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LadderPriceController.class);

	@Autowired
	private LadderPriceServiceI ladderPriceService;
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
	 * 教室照明列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "ladderprice")
	public ModelAndView ladderprice(HttpServletRequest request) {
		return new ModelAndView("nhjkpt/configmanage/ladderprice/ladderpricelist");
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
	public void datagrid(LadderPriceEntity ladderPrice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(LadderPriceEntity.class, dataGrid);
		//查询条件组装器
		org.framework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ladderPrice);
		this.ladderPriceService.getDataGridReturn(cq, true);
		List<LadderPriceEntity> entityList = dataGrid.getReaults();
		for(LadderPriceEntity entity : entityList) {
			entity.setPriceTypeName(PriceTypeEmun.getPriceTypeName(entity.getPriceType()));
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除教室照明
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(LadderPriceEntity ladderPrice, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		ladderPrice = systemService.getEntity(LadderPriceEntity.class, ladderPrice.getId());
		message = "删除成功";
		this.ladderPriceService.delete(ladderPrice);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加教室照明
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(LadderPriceEntity ladderPrice, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(ladderPrice.getId())) {
			message = "更新成功";
			LadderPriceEntity t = ladderPriceService.get(LadderPriceEntity.class, ladderPrice.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(ladderPrice, t);
				ladderPriceService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			ladderPriceService.save(ladderPrice);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 教室照明列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(LadderPriceEntity ladderPrice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(ladderPrice.getId())) {
			ladderPrice = ladderPriceService.getEntity(LadderPriceEntity.class, ladderPrice.getId());
			req.setAttribute("ladderPrice", ladderPrice);
		}
		
		List<PriceTypeVo> priceTypeList = new ArrayList<PriceTypeVo>();
		for(int i = 0; i < PriceTypeEmun.values().length ; i++) {
			PriceTypeVo vo = new PriceTypeVo();
			vo.setPriceType(PriceTypeEmun.values()[i].getType());
			vo.setPriceTypeName(PriceTypeEmun.values()[i].getTypeName());
			priceTypeList.add(vo);
		}
		req.setAttribute("priceTypeList", priceTypeList);
		return new ModelAndView("nhjkpt/configmanage/ladderprice/ladderprice");
	}
	
}
