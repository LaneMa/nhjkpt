package nhjkpt.configmanage.service.impl.schoolmonthsum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.schoolmonthsum.SchoolMonthSumEntity;
import nhjkpt.configmanage.service.schooldaysum.SchooldaysumServiceI;
import nhjkpt.configmanage.service.schoolmonthsum.SchoolMonthSumServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("schoolMonthSumService")
@Transactional
public class SchoolMonthSumServiceImpl extends CommonServiceImpl implements SchoolMonthSumServiceI {

	@Autowired
	private SchooldaysumServiceI schooldaysumService;
	@Override
	public void exportSchooldaySum(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		SchoolMonthSumEntity schoolMonthSum=null;
		List<Map<String,Object>> list=schooldaysumService.querySchooldaysum(calendar);
		List<SchoolMonthSumEntity> listsum=null;
		for(Map<String,Object> map:list){
			try {
				listsum=this.findHql(" from SchoolMonthSumEntity where funcid=? and receivetime=?", map.get("funcid"),sdf.parse(sdf.format(calendar.getTime())));
				if(listsum!=null&&listsum.size()!=0){
					schoolMonthSum=new SchoolMonthSumEntity();
					schoolMonthSum=listsum.get(0);
					schoolMonthSum.setData((Double) map.get("data"));
					schoolMonthSum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.saveOrUpdate(schoolMonthSum);
				}else{
					schoolMonthSum=new SchoolMonthSumEntity();
					schoolMonthSum.setFuncid((String) map.get("funcid"));
					schoolMonthSum.setData((Double) map.get("data"));
					schoolMonthSum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.save(schoolMonthSum);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}