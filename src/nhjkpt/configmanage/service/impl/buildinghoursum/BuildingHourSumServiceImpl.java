package nhjkpt.configmanage.service.impl.buildinghoursum;

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

import nhjkpt.configmanage.entity.buildinghoursum.BuildingHourSumEntity;
import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingsum.BuildingSumEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.hourdata.HourdataEntity;
import nhjkpt.configmanage.service.buildinghoursum.BuildingHourSumServiceI;
import nhjkpt.configmanage.service.hourdata.HourdataServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("buildingHourSumService")
@Transactional
public class BuildingHourSumServiceImpl extends CommonServiceImpl implements BuildingHourSumServiceI {

	@Autowired
	private HourdataServiceI hourdataService;
	@Override
	public void exportHourData(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		List<FuncEntity> listFunc=findByQueryString(" from FuncEntity");
		List<BuildinginfoEntity> listbuilding=findHql(" from BuildinginfoEntity");
		List<BuildingSumEntity> list=findByQueryString(" from BuildingSumEntity");
		List<HourdataEntity> listHourdata=null;
		Double value=0.0;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		String sql="CREATE TABLE IF NOT EXISTS  `buildinghoursum_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`buildingid` VARCHAR(32) DEFAULT NULL," +
				"`funcid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("buildinghoursum","buildinghoursum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		String buildingid=null;
		try{
			session=getSession(interceptor);
			BuildingHourSumEntity buildingHourSum=null;
			Transaction tran=null;
			Query query=null;
			List<BuildingHourSumEntity> listsum=null;
			String funcid=null;
			for(BuildinginfoEntity build:listbuilding){
				if(CommonUtil.NEWID){
					buildingid=build.getId();
				}else{
					buildingid=build.getBuildingid();
				}
				for(FuncEntity func:listFunc){
					value=0.0;
					if(CommonUtil.NEWID){
						funcid=func.getId();
					}else{
						funcid=func.getFuncid().toString();
					}
					buildingHourSum=new BuildingHourSumEntity();
					buildingHourSum.setFuncid(funcid);
					for(BuildingSumEntity building:list){
						if(building.getBuildingid().equals(buildingid)&&building.getFuncid().equals(funcid)){
							listHourdata=hourdataService.queryHourdataByHour(building.getFuncid(),building.getMeterid(),calendar);
							for(HourdataEntity hourdata : listHourdata){
								buildingHourSum.setBuildingid(building.getBuildingid());
								value+=hourdata.getData()*100*building.getFactor()/100;
							}
						}
					}
					if(!CommonUtil.isNull(buildingHourSum.getBuildingid())){
						try {
							query=session.createQuery(" from BuildingHourSumEntity where buildingid=:buildingid and funcid=:funcid and date_format(receivetime,'%Y-%m-%d %H')=:receivetime");
							query.setString("buildingid", buildingHourSum.getBuildingid());
							query.setString("funcid", buildingHourSum.getFuncid());
							query.setString("receivetime", sdf.format(calendar.getTime()));
							listsum=query.list();
							if(listsum!=null&&listsum.size()!=0){
								buildingHourSum=listsum.get(0);
								buildingHourSum.setData(value);
								tran=session.beginTransaction();
								session.update(buildingHourSum);
								tran.commit();
							}else{
								buildingHourSum.setData(value);
								buildingHourSum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
								tran=session.beginTransaction();
								session.save(buildingHourSum);
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
	public Double queryBuildingHourSum(String funcid,Calendar calendar) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("buildinghoursum","buildinghoursum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select sum(data) from BuildingHourSumEntity where funcid=:funcid and receivetime=:receivetime");
			query.setString("funcid", funcid);
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
	public List<Map<String, Object>> queryBuildingHourSum(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("buildinghoursum","buildinghoursum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select funcid,buildingid, sum(data) from BuildingHourSumEntity where receivetime>=:startDate and receivetime<=:endDate group by buildingid,funcid");
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
				map.put("funcid", obj[0]);
				map.put("buildingid", obj[1]);
				map.put("data", obj[2]);
				list.add(map);
			}
		}catch(Exception e){
			
		}finally{
			if(session!=null){
				session.clear();
				session.close();
			}
		}
		return list;
	}
}