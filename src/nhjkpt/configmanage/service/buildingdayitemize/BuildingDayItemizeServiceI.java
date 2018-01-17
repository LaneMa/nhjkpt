package nhjkpt.configmanage.service.buildingdayitemize;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 建筑用电分类分天统计表
 * @author qf
 * @date 2013-08-17 23:12:53
 * @version V1.0   
 *
 */
public interface BuildingDayItemizeServiceI extends CommonService{

	void exportBuildingHourItemize(Calendar calendar);

	Double queryBuildingDayItemize(String itemizeid, Calendar calendar);

	List<Map<String, Object>> queryBuildingDayItemize(Calendar calendar);

}
