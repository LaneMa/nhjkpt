package nhjkpt.configmanage.service.impl.hourdata;

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

import nhjkpt.configmanage.entity.hourdata.HourdataEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.schoolsum.SchoolSumEntity;
import nhjkpt.configmanage.service.hourdata.HourdataServiceI;
import nhjkpt.configmanage.service.metermanage.MeterServiceI;
import nhjkpt.configmanage.service.pointdata.PointdataServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("hourdataService")
@Transactional
public class HourdataServiceImpl extends CommonServiceImpl implements HourdataServiceI {

	@Autowired
	private PointdataServiceI pointdataService;
	@Autowired
	private MeterServiceI meterService;
	@Override
	public void exportPointDataHour(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		List<MeterEntity> listmeter=meterService.findByQueryString(" from MeterEntity");
		HourdataEntity hourdata=null;
		HourdataEntity data=null;
		List<HourdataEntity> listdata=null;
		Transaction tran=null;
		String sql="CREATE TABLE IF NOT EXISTS  `hourdata_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`meterid` VARCHAR(32) DEFAULT NULL," +
				"`funcid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("hourdata","hourdata_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		Query query=null;
		try{
			session=getSession(interceptor);
			for(MeterEntity meter:listmeter){
				if(CommonUtil.NEWID){
					hourdata=pointdataService.queryHourData(meter.getId(), calendar);
				}else{
					hourdata=pointdataService.queryHourData(meter.getMeterid(), calendar);
				}
				if(hourdata!=null){
					query=session.createQuery(" from HourdataEntity where meterid=:meterid and funcid=:funcid and date_format(receivetime,'%Y-%m-%d %H')=:receivetime");
					query.setString("meterid", hourdata.getMeterid());
					query.setString("funcid", hourdata.getFuncid());
					query.setString("receivetime", sdf.format(hourdata.getReceivetime()));
					listdata=query.list();
					if(listdata!=null&&listdata.size()!=0){
						data=listdata.get(0);
						data.setData(hourdata.getData());
						tran=session.beginTransaction();
						session.update(data);
						tran.commit();
					}else{
						tran=session.beginTransaction();
						session.save(hourdata);
						tran.commit();
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
	public List<HourdataEntity> queryHourdataByHour(String funcid,String meterid,Calendar calendar) {
		List<HourdataEntity> list=null;
		Session session=null;
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
			Interceptor interceptor=new MyInterceptor("hourdata","hourdata_"+simpledateformat.format(calendar.getTime()));
			session=getSession(interceptor);
			Query query=session.createQuery(" from HourdataEntity where funcid=:funcid and meterid=:meterid and receivetime=:receivetime");
			query.setString("funcid", funcid);
			query.setString("meterid", meterid);
			query.setTimestamp("receivetime", sdf.parse(sdf.format(calendar.getTime())));
			list=query.list();
		}catch(Exception e){
		}finally{
			if(session!=null){
				session.clear();
				session.close();
			}
		}
		return list;
	}
	
	@Override
	public List<HourdataEntity> queryHourdataByHourByMeterid(String funcid,String meterid,Calendar calendar) {
		List<HourdataEntity> list=null;
		Session session=null;
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
			Interceptor interceptor=new MyInterceptor("hourdata","hourdata_"+simpledateformat.format(calendar.getTime()));
			session=getSession(interceptor);
			Query query=session.createQuery(" from HourdataEntity where funcid=:funcid and meterid=:meterid and receivetime=:receivetime");
			query.setString("funcid", funcid);
			query.setString("meterid", meterid);
			query.setTimestamp("receivetime", sdf.parse(sdf.format(calendar.getTime())));
			list= query.list();
		}catch(Exception e){
		}finally{
			if(session!=null){
				session.clear();
				session.close();
			}
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> queryHourdata(Calendar calendar) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("hourdata","hourdata_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select funcid,sum(data) from HourdataEntity where receivetime=:receivetime group by funcid");
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
	}
	@Override
	public List<Map<String, Object>> queryHourdataByMeterid(Calendar calendar) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("hourdata","hourdata_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select meterid,sum(data) from HourdataEntity where receivetime=:receivetime group by meterid");
			query.setString("receivetime", sdf.format(calendar.getTime()));
			Iterator iter=query.iterate();
			Map<String,Object> map=null;
			Object[] obj=null;
			while(iter.hasNext()){
				map=new HashMap<String, Object>();
				obj=(Object[])iter.next();
				map.put("meterid", obj[0]);
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
	}
}