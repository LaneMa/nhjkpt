package nhjkpt.configmanage.service.meterfuncshowhistory;

import java.util.List;

import nhjkpt.configmanage.entity.meterfuncshowhistory.MeterFuncShowHistoryEntity;
import nhjkpt.configmanage.entity.metershowhistory.MeterShowHistoryEntity;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 瞬时值保存两个月记录
 * @author qf
 * @date 2013-08-29 16:15:51
 * @version V1.0   
 *
 */
public interface MeterFuncShowHistoryServiceI extends CommonService{

	MeterShowHistoryEntity splitShowStr(String str);
	/**
	 * 查询48小时电流统计曲线
	 * @return
	 */
	List<Highchart> queryHighchart(String meterid);

}
