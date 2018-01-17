package nhjkpt.configmanage.service.schoolunit;

import java.util.List;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 学校单位用能统计计算表
 * @author qf
 * @date 2013-08-02 01:14:55
 * @version V1.0   
 *
 */
public interface SchoolUnitServiceI extends CommonService{

	/**
	 * 学校平均用能统计查询
	 * @param unitid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Highchart> queryHighchart(String funcid,String unitid,String type,String startDate,String endDate);

	List<Highchart> queryHighchartItemize(String itemizeid, String type,String startDate, String endDate);
}
