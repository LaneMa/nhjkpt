package nhjkpt.configmanage.service.impl.buildinghouritemize;

import java.text.ParseException;
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

import nhjkpt.configmanage.entity.buildinghouritemize.BuildingHourItemizeEntity;
import nhjkpt.configmanage.entity.buildinghoursum.BuildingHourSumEntity;
import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingitemize.BuildingItemizeEntity;
import nhjkpt.configmanage.entity.buildingsum.BuildingSumEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.hourdata.HourdataEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.schoolhouritemize.SchoolHourItemizeEntity;
import nhjkpt.configmanage.entity.schoolhoursum.SchoolhoursumEntity;
import nhjkpt.configmanage.entity.schoolitemize.SchoolItemizeEntity;
import nhjkpt.configmanage.service.buildinghouritemize.BuildingHourItemizeServiceI;
import nhjkpt.configmanage.service.hourdata.HourdataServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("buildingHourItemizeService")
@Transactional
public class BuildingHourItemizeServiceImpl extends CommonServiceImpl implements BuildingHourItemizeServiceI {
	@Autowired
	private HourdataServiceI hourdataService;
	@Override
	public void exportHourdata(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		List<FuncEntity> listFunc=findByQueryString(" from FuncEntity");
		List<ItemizeEntity> listItemize=findByQueryString(" from ItemizeEntity");
		List<BuildinginfoEntity> listbuilding=findHql(" from BuildinginfoEntity");
		List<BuildingItemizeEntity> list=findByQueryString(" from BuildingItemizeEntity");
		List<HourdataEntity> listHourdata=null;
		Double value=0.0;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		String sql="CREATE TABLE IF NOT EXISTS  `buildinghouritemize_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`buildingid` VARCHAR(32) DEFAULT NULL," +
				"`itemizeid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("buildinghouritemize","buildinghouritemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			BuildingHourItemizeEntity buildingHourItemize=null;
			Transaction tran=null;
			Query query=null;
			List<BuildingHourItemizeEntity> listitemize=null;
			String funcid=null;
			String itemid=null;
			String buildingid=null;
			for(BuildinginfoEntity build:listbuilding){
				if(CommonUtil.NEWID){
					buildingid=build.getId();
				}else{
					buildingid=build.getBuildingid().toString();
				}
				for(ItemizeEntity item:listItemize){
					value=0.0;
					if(CommonUtil.NEWID){
						itemid=item.getId();
					}else{
						itemid=item.getItemizeid().toString();
					}
					buildingHourItemize=new BuildingHourItemizeEntity();
					buildingHourItemize.setItemizeid(itemid);
					for(FuncEntity func:listFunc){
						if(CommonUtil.NEWID){
							funcid=func.getId();
						}else{
							funcid=func.getFuncid().toString();
						}
						for(BuildingItemizeEntity building:list){
							if(building.getBuildingid().equals(buildingid)&&building.getFuncid().equals(funcid)&&itemid.equals(building.getItemizeid())){
								listHourdata=hourdataService.queryHourdataByHour(building.getFuncid(),building.getMeterid(),calendar);
								for(HourdataEntity hourdata : listHourdata){
									buildingHourItemize.setBuildingid(buildingid);
									buildingHourItemize.setItemizeid(itemid);
									value+=hourdata.getData()*100*building.getFactor()/100;
								}
							}
						}
					}
					if(!CommonUtil.isNull(buildingHourItemize.getBuildingid())){
						try {
							query=session.createQuery(" from BuildingHourItemizeEntity where buildingid=:buildingid and itemizeid=:itemizeid and date_format(receivetime,'%Y-%m-%d %H')=:receivetime");
							query.setString("buildingid", buildingHourItemize.getBuildingid());
							query.setString("itemizeid", buildingHourItemize.getItemizeid());
							query.setString("receivetime", sdf.format(calendar.getTime()));
							listitemize=query.list();
							if(listitemize!=null&&listitemize.size()!=0){
								buildingHourItemize=listitemize.get(0);
								buildingHourItemize.setData(value);
								tran=session.beginTransaction();
								session.update(buildingHourItemize);
								tran.commit();
							}else{
								buildingHourItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
								buildingHourItemize.setData(value);
								tran=session.beginTransaction();
								session.save(buildingHourItemize);
								tran.commit();
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
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
	public Double queryBuildingHourItemizeid(String itemizeid,Calendar calendar) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("buildinghouritemize","buildinghouritemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select sum(data) from BuildingHourItemizeEntity where itemizeid=:itemizeid and date_format(receivetime,'%Y-%m-%d')=:receivetime");
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
		return null;
	}
	@Override
	public List<Map<String, Object>> queryBuildingHourItemizeid(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("buildinghouritemize","buildinghouritemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select itemizeid,buildingid, sum(data) from BuildingHourItemizeEntity where receivetime>=:startDate and receivetime<=:endDate group by itemizeid,buildingid");
			calendar.set(Calendar.HOUR_OF_DAY, 1);
			query.setString("startDate", sdf.format(calendar.getTime()));
			calendar.add(Calendar.HOUR_OF_DAY, 23);
			query.setString("endDate", sdf.format(calendar.getTime()));
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