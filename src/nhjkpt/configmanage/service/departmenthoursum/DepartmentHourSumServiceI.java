package nhjkpt.configmanage.service.departmenthoursum;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 系小时用能
 * @author qf
 * @date 2014-11-18 23:57:08
 * @version V1.0   
 *
 */
public interface DepartmentHourSumServiceI extends CommonService{

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
	Double queryDepartmentHourSum(String funcid, Calendar calendar);
	/**
	 * 根据功能分组查询出系小时用能
	 * @param calendar
	 * @return
	 */
	List<Map<String, Object>> queryDepartmentHourSum(Calendar calendar);
}
