package nhjkpt.configmanage.service.departmentdayitemize;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 系天分类用能
 * @author qf
 * @date 2014-11-19 00:01:36
 * @version V1.0   
 *
 */
public interface DepartmentDayItemizeServiceI extends CommonService{

	void exportDepartmentHourItemize(Calendar calendar);

	Double queryDepartmentDayItemize(String itemizeid, Calendar calendar);

	List<Map<String, Object>> queryDepartmentDayItemize(Calendar calendar);
}
