package nhjkpt.configmanage.service.impl.departmentdayitemize;

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

import nhjkpt.configmanage.entity.departmentdayitemize.DepartmentDayItemizeEntity;
import nhjkpt.configmanage.service.departmentdayitemize.DepartmentDayItemizeServiceI;
import nhjkpt.configmanage.service.departmenthouritemize.DepartmentHourItemizeServiceI;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("departmentDayItemizeService")
@Transactional
public class DepartmentDayItemizeServiceImpl extends CommonServiceImpl implements DepartmentDayItemizeServiceI {
	
	@Autowired
	private DepartmentHourItemizeServiceI departmentHourItemizeService;
	@Override
	public void exportDepartmentHourItemize(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		DepartmentDayItemizeEntity departmentDayItemize=null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String sql="CREATE TABLE IF NOT EXISTS  `departmentdayitemize_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`departmentid` VARCHAR(32) DEFAULT NULL," +
				"`itemizeid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("departmentdayitemize","departmentdayitemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Transaction tran=null;
			Query query=null;
			List<DepartmentDayItemizeEntity> listitemize=null;
			List<Map<String,Object>> list=departmentHourItemizeService.queryDepartmentHourItemizeid(calendar);
			for(Map<String,Object> map:list){
				try{
					query=session.createQuery(" from DepartmentDayItemizeEntity where departmentid=:departmentid and itemizeid=:itemizeid and date_format(receivetime,'%Y-%m-%d')=:receivetime");
					query.setString("departmentid", (String) map.get("departmentid"));
					query.setString("itemizeid", (String) map.get("itemizeid"));
					query.setString("receivetime", sdf.format(calendar.getTime()));
					listitemize=query.list();
					if(listitemize!=null&&listitemize.size()!=0){
						departmentDayItemize=listitemize.get(0);
						departmentDayItemize.setData((Double) map.get("data"));
						departmentDayItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						tran=session.beginTransaction();
						session.update(departmentDayItemize);
						tran.commit();
					}else{
						departmentDayItemize=new DepartmentDayItemizeEntity();
						departmentDayItemize.setDepartmentid((String) map.get("departmentid"));
						departmentDayItemize.setItemizeid((String) map.get("itemizeid"));
						departmentDayItemize.setData((Double) map.get("data"));
						departmentDayItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						tran=session.beginTransaction();
						session.save(departmentDayItemize);
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
	public Double queryDepartmentDayItemize(String itemizeid,Calendar calendar) {
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
			Interceptor interceptor=new MyInterceptor("departmentdayitemize","departmentdayitemize_"+simpledateformat.format(calendar.getTime()));
			Session session=null;
			try{
				session=getSession(interceptor);
				Query query=session.createQuery("select sum(data) from DepartmentDayItemizeEntity where itemizeid=:itemizeid and receivetime=:receivetime");
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
	public List<Map<String, Object>> queryDepartmentDayItemize(Calendar calendar) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("departmentdayitemize","departmentdayitemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select itemizeid,departmentid, sum(data) from DepartmentDayItemizeEntity where date_format(receivetime,'%Y-%m')=:receivetime group by itemizeid,departmentid");
			query.setString("receivetime", sdf.format(calendar.getTime()));
			Iterator iter=query.iterate();
			Map<String,Object> map=null;
			Object[] obj=null;
			while(iter.hasNext()){
				map=new HashMap<String, Object>();
				obj=(Object[])iter.next();
				map.put("itemizeid", obj[0]);
				map.put("departmentid", obj[1]);
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