package nhjkpt.configmanage.service.impl.schoolhouritemize;

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
import nhjkpt.configmanage.entity.hourdata.HourdataEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.schoolhouritemize.SchoolHourItemizeEntity;
import nhjkpt.configmanage.entity.schoolitemize.SchoolItemizeEntity;
import nhjkpt.configmanage.service.hourdata.HourdataServiceI;
import nhjkpt.configmanage.service.schoolhouritemize.SchoolHourItemizeServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("schoolHourItemizeService")
@Transactional
public class SchoolHourItemizeServiceImpl extends CommonServiceImpl implements SchoolHourItemizeServiceI {
	@Autowired
	private HourdataServiceI hourdataService;
	@Override
	public void exportHourdata(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		List<FuncEntity> listFunc=findByQueryString(" from FuncEntity");
		List<ItemizeEntity> listItemize=findByQueryString(" from ItemizeEntity");
		List<SchoolItemizeEntity> list=findByQueryString(" from SchoolItemizeEntity");
		List<HourdataEntity> listHourdata=null;
		Double value=0.0;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		String sql="CREATE TABLE IF NOT EXISTS  `schoolhouritemize_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`itemizeid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("schoolhouritemize","schoolhouritemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Transaction tran=null;
			Query query=null;
			List<SchoolHourItemizeEntity> listitemize=null;
			SchoolHourItemizeEntity schoolHourItemize=null;
			String funcid=null;
			String itemizeid=null;
			for(ItemizeEntity itemize:listItemize){
				value=0.0;
				if(CommonUtil.NEWID){
					itemizeid=itemize.getId();
				}else{
					itemizeid=itemize.getItemizeid().toString();
				}
				schoolHourItemize=new SchoolHourItemizeEntity();
				for(FuncEntity func:listFunc){
					if(CommonUtil.NEWID){
						funcid=func.getId();
					}else{
						funcid=func.getFuncid().toString();
					}
					for(SchoolItemizeEntity school:list){
						if(itemizeid.equals(school.getItemizeid())&&school.getFuncid().equals(funcid)){
							listHourdata=hourdataService.queryHourdataByHourByMeterid(school.getFuncid(),school.getMeterid(), calendar);
							if(listHourdata!=null&&listHourdata.size()!=0){
								for(HourdataEntity hourdata : listHourdata){
									schoolHourItemize.setItemizeid(itemizeid);
									value+=hourdata.getData()*100*school.getFactor()/100;
								}
							}
						}
					}
					if(!CommonUtil.isNull(schoolHourItemize.getItemizeid())){
						try {
							query=session.createQuery(" from SchoolHourItemizeEntity where itemizeid=:itemizeid and date_format(receivetime,'%Y-%m-%d %H')=:receivetime");
							query.setString("itemizeid", schoolHourItemize.getItemizeid());
							query.setString("receivetime", sdf.format(calendar.getTime()));
							listitemize=query.list();
							if(listitemize!=null&&listitemize.size()!=0){
								schoolHourItemize=listitemize.get(0);
								schoolHourItemize.setData(value);
								tran=session.beginTransaction();
								session.update(schoolHourItemize);
								tran.commit();
							}else{
								schoolHourItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
								schoolHourItemize.setData(value);
								tran=session.beginTransaction();
								session.save(schoolHourItemize);
								tran.commit();
							}
						} catch (ParseException e) {
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
	public Double querySchoolHourItemizeid(String itemizeid,Calendar calendar) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("schoolhouritemize","schoolhouritemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select sum(data) from SchoolHourItemizeEntity where date_format(receivetime,'%Y-%m-%d')=:itemizeid and receivetime=:receivetime");
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
	public List<Map<String, Object>> querySchoolHourItemizeid(Calendar cal) {
		try{
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(cal.getTime());
			List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
			Interceptor interceptor=new MyInterceptor("schoolhouritemize","schoolhouritemize_"+simpledateformat.format(calendar.getTime()));
			Session session=null;
			try{
				session=getSession(interceptor);
				Query query=session.createQuery("select itemizeid, sum(data) from SchoolHourItemizeEntity where receivetime>=:startDate and receivetime<=:endDate group by itemizeid");
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
					map.put("data", obj[1]);
					list.add(map);
				}
			}catch(Exception e){
				
			}finally{
				if(session!=null){
					session.close();
				}
			}
			return list;
		}catch(Exception e){
			return new ArrayList<Map<String,Object>>();
		}
	}
}