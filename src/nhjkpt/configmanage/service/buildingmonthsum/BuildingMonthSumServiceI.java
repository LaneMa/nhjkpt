package nhjkpt.configmanage.service.buildingmonthsum;

import java.util.Calendar;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 建筑用电总量分月统计表
 * @author qf
 * @date 2013-08-02 00:56:29
 * @version V1.0   
 *
 */
public interface BuildingMonthSumServiceI extends CommonService{

	void exportBuildingDaySum(Calendar calendar);

}
