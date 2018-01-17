package nhjkpt.configmanage.service.buildinghouritemize;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 建筑用能分类分时统计表
 * @author qf
 * @date 2013-08-17 16:35:29
 * @version V1.0   
 *
 */
public interface BuildingHourItemizeServiceI extends CommonService{

	void exportHourdata(Calendar calendar);

	Double queryBuildingHourItemizeid(String itemizeid, Calendar calendar);

	List<Map<String, Object>> queryBuildingHourItemizeid(Calendar calendar);

}
