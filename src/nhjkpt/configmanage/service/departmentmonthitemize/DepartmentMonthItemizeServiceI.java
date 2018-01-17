package nhjkpt.configmanage.service.departmentmonthitemize;

import java.util.Calendar;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 系月分类用能
 * @author qf
 * @date 2014-11-19 00:04:52
 * @version V1.0   
 *
 */
public interface DepartmentMonthItemizeServiceI extends CommonService{

	void exportDepartmentDayItemize(Calendar calendar);
}
