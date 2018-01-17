package nhjkpt.configmanage.service.departmentsum;

import java.io.OutputStream;
import java.util.List;

import nhjkpt.configmanage.entity.departmentdaysum.DepartmentDaySumEntity;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 系水气电总量计算表
 * @author qf
 * @date 2014-11-19 00:11:11
 * @version V1.0   
 *
 */
public interface DepartmentSumServiceI extends CommonService{

	/**
	 * 查询所有大楼的对比曲线或指定大楼的曲线
	 * @param departmentid
	 * @param funcid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Highchart> queryHighchart(String departmentid,String funcid, String type,String startDate, String endDate);
	/**
	 * 查询相应表中数据
	 * @param departmentid
	 * @param funcid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	void exportExcel(String departmentid, String funcid,String type, String startDate, String endDate,OutputStream out);
	/**
	 * 根据大楼id查询大楼所有电表的当日数据
	 * @param departmentid
	 * @return
	 */
	List<DepartmentDaySumEntity> queryDepartmentDaySumByDepartmentid(String departmentid);
	/**
	 * 查询排名
	 * @param data
	 * @param funcid
	 * @return
	 */
	long queryRanking(Double data, String funcid);
	/**
	 * 报表查询数据，首先通过map将数据统计出来并获取列表头数据，然后根据列表头将整个表格数据通过list方式拼接
	 * @param departmentid
	 * @param funcid
	 * @param startDate
	 * @param endDate
	 * @param reportType
	 * @param tableType
	 * @return
	 */
	List<List<String>> queryReportData(String departmentid, String funcid,
			String startDate, String endDate, String reportType,
			String tableType);
	/**
	 * 导出报表
	 * @param departmentid
	 * @param funcid
	 * @param startDate
	 * @param endDate
	 * @param reportType
	 * @param tableType
	 * @param out
	 */
	void exportReport(String departmentid, String funcid, String startDate,String endDate, String reportType, String tableType,OutputStream out);

}
