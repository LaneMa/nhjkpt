package nhjkpt.configmanage.service.buildingmonthitemize;

import java.util.Calendar;

import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 大楼用电分类分月统计表
 * @author qf
 * @date 2013-08-02 01:11:37
 * @version V1.0   
 *
 */
public interface BuildingMonthItemizeServiceI extends CommonService{

	void exportBuildingDayItemize(Calendar calendar);

}
