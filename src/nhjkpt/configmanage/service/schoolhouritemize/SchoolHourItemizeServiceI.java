package nhjkpt.configmanage.service.schoolhouritemize;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 学校用能分类分时统计表
 * @author qf
 * @date 2013-08-17 13:52:50
 * @version V1.0   
 *
 */
public interface SchoolHourItemizeServiceI extends CommonService{

	Double querySchoolHourItemizeid(String itemizeid, Calendar calendar);

	List<Map<String, Object>> querySchoolHourItemizeid(Calendar calendar);

	void exportHourdata(Calendar calendar);

}
