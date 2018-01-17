package nhjkpt.configmanage.service.buildinghoursum;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 建筑用电总量分时统计表
 * @author qf
 * @date 2013-08-17 11:14:39
 * @version V1.0   
 *
 */
public interface BuildingHourSumServiceI extends CommonService{
	/**
	 * 从hourdata表中导入每小时数据
	 */
	void exportHourData(Calendar calendar);
	/**
	 * 根据功能查询出学校小时用能
	 * @param funcid
	 * @param calendar
	 * @return
	 */
	Double queryBuildingHourSum(String funcid, Calendar calendar);
	/**
	 * 根据功能分组查询出学校小时用能
	 * @param calendar
	 * @return
	 */
	List<Map<String, Object>> queryBuildingHourSum(Calendar calendar);
}
