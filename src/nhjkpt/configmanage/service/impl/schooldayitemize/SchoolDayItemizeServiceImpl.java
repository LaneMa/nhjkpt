package nhjkpt.configmanage.service.impl.schooldayitemize;

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

import nhjkpt.configmanage.entity.schooldayitemize.SchoolDayItemizeEntity;
import nhjkpt.configmanage.entity.schooldaysum.SchooldaysumEntity;
import nhjkpt.configmanage.service.schooldayitemize.SchoolDayItemizeServiceI;
import nhjkpt.configmanage.service.schoolhouritemize.SchoolHourItemizeServiceI;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("schoolDayItemizeService")
@Transactional
public class SchoolDayItemizeServiceImpl extends CommonServiceImpl implements SchoolDayItemizeServiceI {
	@Autowired
	private SchoolHourItemizeServiceI schoolHourItemizeService;
	@Override
	public void exportSchoolHourItemize(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		SchoolDayItemizeEntity schooldayitemize=null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String sql="CREATE TABLE IF NOT EXISTS  `schooldayitemize_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`itemizeid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("schooldayitemize","schooldayitemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Transaction tran=null;
			Query query=null;
			List<SchoolDayItemizeEntity> listitemize=null;
			List<Map<String,Object>> list=schoolHourItemizeService.querySchoolHourItemizeid(calendar);
			for(Map<String,Object> map:list){
				try {
					query=session.createQuery(" from SchoolDayItemizeEntity where itemizeid=:itemizeid and date_format(receivetime,'%Y-%m-%d')=:receivetime");
					query.setString("itemizeid", (String) map.get("itemizeid"));
					query.setString("receivetime", sdf.format(calendar.getTime()));
					listitemize=query.list();
					if(listitemize!=null&&listitemize.size()!=0){
						schooldayitemize=listitemize.get(0);
						schooldayitemize.setData((Double) map.get("data"));
						schooldayitemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						tran=session.beginTransaction();
						session.update(schooldayitemize);
						tran.commit();
					}else{
						schooldayitemize=new SchoolDayItemizeEntity();
						schooldayitemize.setItemizeid((String) map.get("itemizeid"));
						schooldayitemize.setData((Double) map.get("data"));
						schooldayitemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						tran=session.beginTransaction();
						session.save(schooldayitemize);
						tran.commit();
					}
				} catch (ParseException e) {
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
	public Double querySchoolDayItemize(String itemizeid,Calendar calendar) {
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
			Interceptor interceptor=new MyInterceptor("schooldayitemize","schooldayitemize_"+simpledateformat.format(calendar.getTime()));
			Session session=null;
			try{
				session=getSession(interceptor);
				Query query=session.createQuery("select sum(data) from SchoolDayItemizeEntity where date_format(receivetime,'%Y-%m')=:itemizeid and receivetime=:receivetime");
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
	public List<Map<String, Object>> querySchoolDayItemize(Calendar calendar) {
		try{
			List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
			Interceptor interceptor=new MyInterceptor("schooldayitemize","schooldayitemize_"+simpledateformat.format(calendar.getTime()));
			Session session=null;
			try{
				session=getSession(interceptor);
				Query query=session.createQuery("select itemizeid, sum(data) from SchoolDayItemizeEntity where date_format(receivetime,'%Y-%m')=:receivetime group by itemizeid");
				query.setString("receivetime", sdf.format(calendar.getTime()));
				Iterator iter=query.iterate();
				Map<String,Object> map=null;
				Object[] obj=null;
				while(iter.hasNext()){
					map=new HashMap<String, Object>();
					obj=(Object[])iter.next();
					map.put("itemizeid", obj[0]);
					map.put("data", obj[1]);
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
		}catch(Exception e){
			return new ArrayList<Map<String,Object>>();
		}
	}
}