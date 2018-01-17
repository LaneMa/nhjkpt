package nhjkpt.configmanage.service.impl.buildingdayitemize;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.buildingdayitemize.BuildingDayItemizeEntity;
import nhjkpt.configmanage.entity.schooldayitemize.SchoolDayItemizeEntity;
import nhjkpt.configmanage.service.buildingdayitemize.BuildingDayItemizeServiceI;
import nhjkpt.configmanage.service.buildinghouritemize.BuildingHourItemizeServiceI;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("buildingDayItemizeService")
@Transactional
public class BuildingDayItemizeServiceImpl extends CommonServiceImpl implements BuildingDayItemizeServiceI {
	@Autowired
	private BuildingHourItemizeServiceI buildingHourItemizeService;
	@Override
	public void exportBuildingHourItemize(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		BuildingDayItemizeEntity buildingDayItemize=null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String sql="CREATE TABLE IF NOT EXISTS  `buildingdayitemize_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`buildingid` VARCHAR(32) DEFAULT NULL," +
				"`itemizeid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("buildingdayitemize","buildingdayitemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Transaction tran=null;
			Query query=null;
			List<BuildingDayItemizeEntity> listitemize=null;
			List<Map<String,Object>> list=buildingHourItemizeService.queryBuildingHourItemizeid(calendar);
			for(Map<String,Object> map:list){
				try{
					query=session.createQuery(" from BuildingDayItemizeEntity where buildingid=:buildingid and itemizeid=:itemizeid and date_format(receivetime,'%Y-%m-%d')=:receivetime");
					query.setString("buildingid", (String) map.get("buildingid"));
					query.setString("itemizeid", (String) map.get("itemizeid"));
					query.setString("receivetime", sdf.format(calendar.getTime()));
					listitemize=query.list();
					if(listitemize!=null&&listitemize.size()!=0){
						buildingDayItemize=listitemize.get(0);
						buildingDayItemize.setData((Double) map.get("data"));
						buildingDayItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						tran=session.beginTransaction();
						session.update(buildingDayItemize);
						tran.commit();
					}else{
						buildingDayItemize=new BuildingDayItemizeEntity();
						buildingDayItemize.setBuildingid((String) map.get("buildingid"));
						buildingDayItemize.setItemizeid((String) map.get("itemizeid"));
						buildingDayItemize.setData((Double) map.get("data"));
						buildingDayItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						tran=session.beginTransaction();
						session.save(buildingDayItemize);
						tran.commit();
					}
				}catch(Exception e){
					
				}
			}
		}catch(Exception e){
			
		}finally{
			if(session!=null){
				session.clear();
				session.close();
			}
		}
	}
	@Override
	public Double queryBuildingDayItemize(String itemizeid,Calendar calendar) {
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
			Interceptor interceptor=new MyInterceptor("buildingdayitemize","buildingdayitemize_"+simpledateformat.format(calendar.getTime()));
			Session session=null;
			try{
				session=getSession(interceptor);
				Query query=session.createQuery("select sum(data) from BuildingDayItemizeEntity where itemizeid=:itemizeid and receivetime=:receivetime");
				query.setString("itemizeid", itemizeid);
				query.setString("receivetime", sdf.format(calendar.getTime()));
				return (Double) query.uniqueResult();
			}catch(Exception e){
				
			}finally{
				if(session!=null){
					session.clear();
					session.close();
				}
			}
		}catch(Exception e){
			return null;
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> queryBuildingDayItemize(Calendar calendar) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("buildingdayitemize","buildingdayitemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select itemizeid,buildingid, sum(data) from BuildingDayItemizeEntity where date_format(receivetime,'%Y-%m')=:receivetime group by itemizeid,buildingid");
			query.setString("receivetime", sdf.format(calendar.getTime()));
			Iterator iter=query.iterate();
			Map<String,Object> map=null;
			Object[] obj=null;
			while(iter.hasNext()){
				map=new HashMap<String, Object>();
				obj=(Object[])iter.next();
				map.put("itemizeid", obj[0]);
				map.put("buildingid", obj[1]);
				map.put("data", obj[2]);
				list.add(map);
			}
		}catch(Exception e){
			
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}
}