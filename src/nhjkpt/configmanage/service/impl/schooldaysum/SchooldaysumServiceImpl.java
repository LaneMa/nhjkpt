package nhjkpt.configmanage.service.impl.schooldaysum;

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

import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.schooldaysum.SchooldaysumEntity;
import nhjkpt.configmanage.service.schooldaysum.SchooldaysumServiceI;
import nhjkpt.configmanage.service.schoolhoursum.SchoolhoursumServiceI;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("schooldaysumService")
@Transactional
public class SchooldaysumServiceImpl extends CommonServiceImpl implements SchooldaysumServiceI {

	@Autowired
	private SchoolhoursumServiceI schoolhoursumService;
	@Override
	public void exportSchoolhoursum(Calendar cal) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//System.out.println("exportSchoolhoursum cal时间："+sdf.format(cal.getTime()));
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		//System.out.println("exportSchoolhoursum calendar时间："+sdf.format(calendar.getTime()));
		SchooldaysumEntity schooldaysum=null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		
		String sql="CREATE TABLE IF NOT EXISTS  `schooldaysum_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`funcid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("schooldaysum","schooldaysum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Transaction tran=null;
			Query query=null;
			List<SchooldaysumEntity> listsum=null;
			List<Map<String,Object>> list=schoolhoursumService.querySchoolhoursum(calendar);
			for(Map<String,Object> map:list){
				try {
					query=session.createQuery(" from SchooldaysumEntity where funcid=:funcid and date_format(receivetime,'%Y-%m-%d')=:receivetime");
					query.setString("funcid", (String) map.get("funcid"));
					query.setString("receivetime", sdf.format(calendar.getTime()));
					listsum=query.list();
					if(listsum!=null&&listsum.size()!=0){
						schooldaysum=listsum.get(0);
						schooldaysum.setData((Double) map.get("data"));
						schooldaysum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						System.out.println("统计学校按天能量消耗的时间："+sdf.format(calendar.getTime()));
						tran=session.beginTransaction();
						session.update(schooldaysum);
						tran.commit();
					}else{
						schooldaysum=new SchooldaysumEntity();
						schooldaysum.setFuncid((String) map.get("funcid"));
						schooldaysum.setData((Double) map.get("data"));
						schooldaysum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						System.out.println("统计学校按天能量消耗的时间："+sdf.format(calendar.getTime()));
						tran=session.beginTransaction();
						session.save(schooldaysum);
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
	public Double querySchooldaysum(String funcid,Calendar calendar) {
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
			Interceptor interceptor=new MyInterceptor("schooldaysum","schooldaysum_"+simpledateformat.format(calendar.getTime()));
			Session session=null;
			try{
				session=getSession(interceptor);
				Query query=session.createQuery("select sum(data) from SchooldaysumEntity where funcid=:funcid and receivetime=:receivetime");
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
		}catch(Exception e){
			return null;
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> querySchooldaysum(Calendar calendar) {
		try{
			List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
			Interceptor interceptor=new MyInterceptor("schooldaysum","schooldaysum_"+simpledateformat.format(calendar.getTime()));
			Session session=null;
			try{
				session=getSession(interceptor);
				Query query=session.createQuery("select funcid, sum(data) from SchooldaysumEntity where date_format(receivetime,'%Y-%m')=:receivetime group by funcid");
				query.setString("receivetime", sdf.format(calendar.getTime()));
				Iterator iter=query.iterate();
				Map<String,Object> map=null;
				Object[] obj=null;
				while(iter.hasNext()){
					map=new HashMap<String, Object>();
					obj=(Object[])iter.next();
					map.put("funcid", obj[0]);
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