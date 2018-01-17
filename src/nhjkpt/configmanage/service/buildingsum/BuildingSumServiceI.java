package nhjkpt.configmanage.service.buildingsum;

import java.io.OutputStream;
import java.util.List;

import nhjkpt.configmanage.entity.buildingdaysum.BuildingDaySumEntity;
import nhjkpt.configmanage.entity.buildingmonthsum.BuildingMonthSumEntity;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.CommonService;
/**   
 * @Title: service
 * @Description: 大楼水电气总量计算表
 * @author qf
 * @date 2013-08-02 00:52:44
 * @version V1.0   
 *
 */
public interface BuildingSumServiceI extends CommonService{

	/**
	 * 查询所有大楼的对比曲线或指定大楼的曲线
	 * @param buildingid
	 * @param funcid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Highchart> queryHighchart(String buildingid,String funcid, String type,String startDate, String endDate);
	/**
	 * 查询相应表中数据
	 * @param buildingid
	 * @param funcid
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	void exportExcel(String buildingid, String funcid,String type, String startDate, String endDate,OutputStream out);
	/**
	 * 根据大楼id查询大楼所有电表的当日数据
	 * @param buildingid
	 * @return
	 */
	List<BuildingDaySumEntity> queryBuildingDaySumByBuildingid(String buildingid);
	/**
	 * 查询排名
	 * @param data
	 * @param funcid
	 * @return
	 */
	long queryRanking(Double data, String funcid);
	/**
	 * 报表查询数据，首先通过map将数据统计出来并获取列表头数据，然后根据列表头将整个表格数据通过list方式拼接
	 * @param buildingid
	 * @param funcid
	 * @param startDate
	 * @param endDate
	 * @param reportType
	 * @param tableType
	 * @return
	 */
	List<List<String>> queryReportData(String buildingid, String funcid,
			String startDate, String endDate, String reportType,
			String tableType);
	/**
	 * 导出报表
	 * @param buildingid
	 * @param funcid
	 * @param startDate
	 * @param endDate
	 * @param reportType
	 * @param tableType
	 * @param out
	 */
	void exportReport(String buildingid, String funcid, String startDate,String endDate, String reportType, String tableType,OutputStream out);

}
