package nhjkpt.configmanage.service.impl.departmentmonthsum;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.departmentmonthsum.DepartmentMonthSumEntity;
import nhjkpt.configmanage.service.departmentdaysum.DepartmentDaySumServiceI;
import nhjkpt.configmanage.service.departmentmonthsum.DepartmentMonthSumServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("departmentMonthSumService")
@Transactional
public class DepartmentMonthSumServiceImpl extends CommonServiceImpl implements DepartmentMonthSumServiceI {
	
	@Autowired
	private DepartmentDaySumServiceI departmentDaySumService;
	@Override
	public void exportDepartmentDaySum(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		DepartmentMonthSumEntity departmentMonthSum=null;
		List<Map<String,Object>> list=departmentDaySumService.queryDepartmentDaySum(calendar);
		List<DepartmentMonthSumEntity> listsum=null;
		for(Map<String,Object> map:list){
			try {
				listsum=this.findHql(" from DepartmentMonthSumEntity where departmentid=? and funcid=? and receivetime=?", map.get("departmentid"),map.get("funcid"),sdf.parse(sdf.format(calendar.getTime())));
				if(listsum!=null&&listsum.size()!=0){
					departmentMonthSum=new DepartmentMonthSumEntity();
					departmentMonthSum=listsum.get(0);
					departmentMonthSum.setData((Double) map.get("data"));
					departmentMonthSum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.saveOrUpdate(departmentMonthSum);
				}else{
					departmentMonthSum=new DepartmentMonthSumEntity();
					departmentMonthSum.setDepartmentid((String) map.get("departmentid"));
					departmentMonthSum.setFuncid((String) map.get("funcid"));
					departmentMonthSum.setData((Double) map.get("data"));
					departmentMonthSum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.save(departmentMonthSum);
				}
			} catch (Exception e) {
			}
		}
	}
}