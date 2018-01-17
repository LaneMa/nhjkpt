package nhjkpt.configmanage.service.schoolitemize;

import java.util.List;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 学校分类用能计算用表
 * @author qf
 * @date 2013-08-02 01:00:14
 * @version V1.0   
 *
 */
public interface SchoolItemizeServiceI extends CommonService{

	List<Highchart> queryHighchart(String itemizeid, String type,String startDate, String endDate);

	List<Highchart> queryHighchartDate(String itemizeid,String type, String[] startDate, String[] endDate);

	List<Highchart> queryHighchartItemize(String itemizeid,String type, String startDate, String endDate,String pictype);
	/**
	 * 按照第一级分类查询饼状图
	 * @param itemizeid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Highchart> queryHighchartItemizePie(String itemizeid, String type,String startDate, String endDate);

}
