package nhjkpt.configmanage.service.departmentdaysum;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 系天用能
 * @author qf
 * @date 2014-11-19 00:00:27
 * @version V1.0   
 *
 */
public interface DepartmentDaySumServiceI extends CommonService{

void exportDepartmentHourSum(Calendar calendar);
	
	List<Map<String, Object>> queryDepartmentDaySum(Calendar calendar);

	Double queryDepartmentDaySum(String funcid, Calendar calendar);
	
	List<Highchart> queryEnergySortCEBar();
}
