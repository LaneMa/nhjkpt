package nhjkpt.configmanage.service.schooldayitemize;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 学校用能分类分天统计表
 * @author qf
 * @date 2013-08-17 13:55:46
 * @version V1.0   
 *
 */
public interface SchoolDayItemizeServiceI extends CommonService{

	void exportSchoolHourItemize(Calendar calendar);

	Double querySchoolDayItemize(String itemizeid, Calendar calendar);

	List<Map<String, Object>> querySchoolDayItemize(Calendar calendar);

}
