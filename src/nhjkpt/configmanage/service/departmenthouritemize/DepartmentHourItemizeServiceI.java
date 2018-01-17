package nhjkpt.configmanage.service.departmenthouritemize;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 系小时分类用能
 * @author qf
 * @date 2014-11-18 23:58:10
 * @version V1.0   
 *
 */
public interface DepartmentHourItemizeServiceI extends CommonService{

	void exportHourdata(Calendar calendar);

	Double queryDepartmentHourItemizeid(String itemizeid, Calendar calendar);

	List<Map<String, Object>> queryDepartmentHourItemizeid(Calendar calendar);
}
