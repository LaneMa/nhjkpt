package nhjkpt.configmanage.service.impl.metermanage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.hourdata.HourdataEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.service.metermanage.MeterServiceI;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;

@Service("meterService")
@Transactional
public class MeterServiceImpl extends CommonServiceImpl implements MeterServiceI {
	
	@Override
	public String queryIdByMeterid(String meterid){
		
		List<MeterEntity> list=this.findByProperty(MeterEntity.class,"meterid",meterid);
		
		if(list!=null&&list.size()!=0){
			return list.get(0).getId();
		}else{
			System.err.println("不存在的表具:"+meterid);
			return null;
		}
	}
	
	
	@Override
	public String queryMeterDaySum(Calendar cal,String meterId) {
		String ret="";
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("hourdata","hourdata_"+simpledateformat.format(calendar.getTime()));
		Session session=getSession(interceptor);
		try{
			Query query=session.createQuery("select round(sum(data),2) from HourdataEntity where meterid=:meterId and receivetime>=:startDate and receivetime<=:endDate");
			calendar.set(Calendar.HOUR_OF_DAY, 1);
			query.setString("startDate", sdf.format(calendar.getTime()));
			calendar.add(Calendar.HOUR_OF_DAY, 23);
			query.setString("endDate", sdf.format(calendar.getTime()));
			query.setString("meterId", meterId);
			Object ret1=query.uniqueResult();
			if(ret1!=null){
				ret=ret1.toString();
			}else{
				ret="0";
			}
		}catch(Exception e){
			ret="0";
		}finally{
			if(session!=null){
				session.clear();
				session.close();
			}
		}
		return ret;
	}
	public String queryMeterHourNum(Calendar cal,String meterId){
		String ret="";
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("pointdata","pointdata_"+simpledateformat.format(calendar.getTime()));
		Session session=getSession(interceptor);
		try{
			Query query=session.createQuery("select round(max(data),2),max(receivetime) from PointdataEntity where meterid=:meterId order by receivetime desc");
			calendar.add(Calendar.HOUR_OF_DAY, -1);
			query.setString("meterId", meterId);
			Object[] ret1=(Object[]) query.uniqueResult();
			if(ret1!=null){
				ret=ret1[0].toString();
			}else{
				ret="0";
			}
		}catch(Exception e){
			ret="0";
		}finally{
			if(session!=null){
				session.clear();
				session.close();
			}
		}
		return ret;
	}
	
	public Integer getMetersByRoom(Integer roomid){
		Integer ret=0;
	
		ret=this.getCountForJdbc("select count(*) from meter where roomid="+roomid).intValue();
			
			
			
		return ret;
		
	}
	
	
	public Integer getOkMetersByRoom(Integer roomid){
		Integer ret=0;
		
		ret=this.getCountForJdbc("select count(*) from meter where roomid="+roomid+"   AND (alarm <>'1' and alarm<>'2')").intValue();
			
			
		
		return ret;
		
	}
	
	public Integer getErrorMetersByRoom(Integer roomid){
		Integer ret=0;
		
		ret=this.getCountForJdbc("select count(*) from meter where roomid="+roomid+"   AND (alarm = '1' OR alarm='2')").intValue();
		
		
		return ret;
		
	}
	
	
	
	//获取学校今日用电量  查询年表当前月前几个月总用电量+本月小时表用电量
			public Double getUseNhTodayByRoom(int roomid){
				double ret=0;
				Calendar calendar=Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
				
				SimpleDateFormat sdf_today=new SimpleDateFormat("yyyy-MM-dd");
				
				
				Interceptor interceptor=new MyInterceptor("hourdata","hourdata_"+sdf.format(calendar.getTime()));
				Session session=null;
				Query query=null;
				try{
					
					session=getSession(interceptor);
					
					query=session.createSQLQuery("select ifnull(sum(data),0) as data from hourdata where date_format(receivetime,'%Y-%m-%d')=:receivetime and meterid in (select meterid from meter where roomid=:roomid)");
					query.setString("receivetime", sdf_today.format(calendar.getTime()));
					query.setInteger("roomid", roomid);
					ret=(Double)query.uniqueResult();
					
				
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(session!=null){
						session.clear();
						session.close();
					}
				}
				
				BigDecimal bg = new BigDecimal(ret);
		        double ret2 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				return ret2;
			}
			
			
			
}