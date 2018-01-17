package nhjkpt.configmanage.service.impl.departmentmonthitemize;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.departmentmonthitemize.DepartmentMonthItemizeEntity;
import nhjkpt.configmanage.service.departmentdayitemize.DepartmentDayItemizeServiceI;
import nhjkpt.configmanage.service.departmentmonthitemize.DepartmentMonthItemizeServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("departmentMonthItemizeService")
@Transactional
public class DepartmentMonthItemizeServiceImpl extends CommonServiceImpl implements DepartmentMonthItemizeServiceI {
	
	@Autowired
	private DepartmentDayItemizeServiceI departmentDayItemizeService;
	@Override
	public void exportDepartmentDayItemize(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		DepartmentMonthItemizeEntity departmentMonthItemize=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		List<Map<String,Object>> list=departmentDayItemizeService.queryDepartmentDayItemize(calendar);
		List<DepartmentMonthItemizeEntity> listitemize=null;
		for(Map<String,Object> map:list){
			try{
				listitemize=this.findHql(" from DepartmentMonthItemizeEntity where departmentid=? and itemizeid=? and receivetime=?", map.get("departmentid"),map.get("itemizeid"),sdf.parse(sdf.format(calendar.getTime())));
				if(listitemize!=null&&listitemize.size()!=0){
					departmentMonthItemize=new DepartmentMonthItemizeEntity();
					departmentMonthItemize=listitemize.get(0);
					departmentMonthItemize.setData((Double) map.get("data"));
					departmentMonthItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.saveOrUpdate(departmentMonthItemize);
				}else{
					departmentMonthItemize=new DepartmentMonthItemizeEntity();
					departmentMonthItemize.setDepartmentid((String) map.get("departmentid"));
					departmentMonthItemize.setItemizeid((String) map.get("itemizeid"));
					departmentMonthItemize.setData((Double) map.get("data"));
					departmentMonthItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.save(departmentMonthItemize);
				}
			}catch(Exception e){
				
			}
		}
	}
}