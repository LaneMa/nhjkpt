package nhjkpt.configmanage.service.impl.schoolinfo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.service.schoolinfo.SchoolinfoServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;

@Service("schoolinfoService")
@Transactional
public class SchoolinfoServiceImpl extends CommonServiceImpl implements SchoolinfoServiceI {
	//获取学校今年用电量  查询年表当前月前几个月总用电量+本月小时表用电量
	public Double getUseNhThisYear(String funcid){
		double ret=0;
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf_hg=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf_year=new SimpleDateFormat("yyyy");
		
		
		Interceptor interceptor=new MyInterceptor("schoolhoursum","schoolhoursum_"+sdf.format(calendar.getTime()));
		Session session=null;
		Query query=null;
		try{
			
			session=getSession(interceptor);
			
			query=session.createSQLQuery("select ifnull(sum(data),0) as data from schoolhoursum where funcid='"+funcid+"'");
			ret=(Double)query.uniqueResult();
			query=session.createSQLQuery("select ifnull(sum(data),0) as data from schoolmonthsum where funcid='"+funcid+"' and receivetime > '"+sdf_year.format(calendar.getTime())+"-01' and receivetime<'"+sdf_hg.format(calendar.getTime())+"'");
			ret=ret+(Double)query.uniqueResult();
		
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
	
	
	
	//获取学校今日用电量  查询年表当前月前几个月总用电量+本月小时表用电量
		public Double getUseNhToday(String funcid){
			double ret=0;
			Calendar calendar=Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
			
			SimpleDateFormat sdf_today=new SimpleDateFormat("yyyy-MM-dd");
			
			
			Interceptor interceptor=new MyInterceptor("schoolhoursum","schoolhoursum_"+sdf.format(calendar.getTime()));
			Session session=null;
			Query query=null;
			try{
				
				session=getSession(interceptor);
				
				query=session.createSQLQuery("select ifnull(sum(data),0) as data from schoolhoursum where funcid='"+funcid+"'");
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