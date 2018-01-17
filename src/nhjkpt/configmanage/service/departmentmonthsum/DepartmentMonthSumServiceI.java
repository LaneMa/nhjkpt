package nhjkpt.configmanage.service.departmentmonthsum;

import java.util.Calendar;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 系月用能
 * @author qf
 * @date 2014-11-19 00:03:45
 * @version V1.0   
 *
 */
public interface DepartmentMonthSumServiceI extends CommonService{

	void exportDepartmentDaySum(Calendar calendar);
}
