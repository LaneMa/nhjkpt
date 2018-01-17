package nhjkpt.configmanage.service.buildingdaysum;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 建筑用电总量分日统计表
 * @author qf
 * @date 2013-08-17 11:46:39
 * @version V1.0   
 *
 */
public interface BuildingDaySumServiceI extends CommonService{
	
	void exportBuildingHourSum(Calendar calendar);
	
	List<Map<String, Object>> queryBuildingDaySum(Calendar calendar);

	Double queryBuildingDaySum(String funcid, Calendar calendar);
	
	List<Highchart> queryEnergySortCEBar();
}
