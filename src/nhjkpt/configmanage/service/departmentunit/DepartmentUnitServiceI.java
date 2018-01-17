package nhjkpt.configmanage.service.departmentunit;

import java.io.OutputStream;
import java.util.List;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 分户单位用能
 * @author qf
 * @date 2014-11-19 22:06:40
 * @version V1.0   
 *
 */
public interface DepartmentUnitServiceI extends CommonService{

	/**
	 * 建筑物单位用能统计曲线显示数据
	 * @param departmentid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Highchart> queryHighchart(String unitid,String departmentid, String type,String startDate, String endDate);
	/**
	 * 学校主页面中建筑物单位用能分类统计曲线数据
	 * @param departmentid
	 * @param itemizeid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Highchart> queryHighchartItemize(String unitid,String departmentid, String itemizeid,String type, String startDate, String endDate);
	/**
	 * 导出excel
	 * @param departmentid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param out
	 */
	void exportExcel(String departmentid, String type, String startDate,String endDate, OutputStream out);
}
