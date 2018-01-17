package nhjkpt.configmanage.service.impl.buildingmonthsum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.buildingmonthsum.BuildingMonthSumEntity;
import nhjkpt.configmanage.service.buildingdaysum.BuildingDaySumServiceI;
import nhjkpt.configmanage.service.buildingmonthsum.BuildingMonthSumServiceI;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("buildingMonthSumService")
@Transactional
public class BuildingMonthSumServiceImpl extends CommonServiceImpl implements BuildingMonthSumServiceI {
	@Autowired
	private BuildingDaySumServiceI buildingDaySumService;
	@Override
	public void exportBuildingDaySum(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		BuildingMonthSumEntity buildingMonthSum=null;
		List<Map<String,Object>> list=buildingDaySumService.queryBuildingDaySum(calendar);
		List<BuildingMonthSumEntity> listsum=null;
		for(Map<String,Object> map:list){
			try {
				listsum=this.findHql(" from BuildingMonthSumEntity where buildingid=? and funcid=? and receivetime=?", map.get("buildingid"),map.get("funcid"),sdf.parse(sdf.format(calendar.getTime())));
				if(listsum!=null&&listsum.size()!=0){
					buildingMonthSum=new BuildingMonthSumEntity();
					buildingMonthSum=listsum.get(0);
					buildingMonthSum.setData((Double) map.get("data"));
					buildingMonthSum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.saveOrUpdate(buildingMonthSum);
				}else{
					buildingMonthSum=new BuildingMonthSumEntity();
					buildingMonthSum.setBuildingid((String) map.get("buildingid"));
					buildingMonthSum.setFuncid((String) map.get("funcid"));
					buildingMonthSum.setData((Double) map.get("data"));
					buildingMonthSum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.save(buildingMonthSum);
				}
			} catch (Exception e) {
			}
		}
	}
}