package nhjkpt.configmanage.service.impl.buildingmonthitemize;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.buildingmonthitemize.BuildingMonthItemizeEntity;
import nhjkpt.configmanage.service.buildingdayitemize.BuildingDayItemizeServiceI;
import nhjkpt.configmanage.service.buildingmonthitemize.BuildingMonthItemizeServiceI;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Query;

@Service("buildingMonthItemizeService")
@Transactional
public class BuildingMonthItemizeServiceImpl extends CommonServiceImpl implements BuildingMonthItemizeServiceI {
	@Autowired
	private BuildingDayItemizeServiceI buildingDayItemizeService;
	@Override
	public void exportBuildingDayItemize(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		BuildingMonthItemizeEntity buildingMonthItemize=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		List<Map<String,Object>> list=buildingDayItemizeService.queryBuildingDayItemize(calendar);
		List<BuildingMonthItemizeEntity> listitemize=null;
		for(Map<String,Object> map:list){
			try{
				listitemize=this.findHql(" from BuildingMonthItemizeEntity where buildingid=? and itemizeid=? and receivetime=?", map.get("buildingid"),map.get("itemizeid"),sdf.parse(sdf.format(calendar.getTime())));
				if(listitemize!=null&&listitemize.size()!=0){
					buildingMonthItemize=new BuildingMonthItemizeEntity();
					buildingMonthItemize=listitemize.get(0);
					buildingMonthItemize.setData((Double) map.get("data"));
					buildingMonthItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.saveOrUpdate(buildingMonthItemize);
				}else{
					buildingMonthItemize=new BuildingMonthItemizeEntity();
					buildingMonthItemize.setBuildingid((String) map.get("buildingid"));
					buildingMonthItemize.setItemizeid((String) map.get("itemizeid"));
					buildingMonthItemize.setData((Double) map.get("data"));
					buildingMonthItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.save(buildingMonthItemize);
				}
			}catch(Exception e){
				
			}
		}
	}
}