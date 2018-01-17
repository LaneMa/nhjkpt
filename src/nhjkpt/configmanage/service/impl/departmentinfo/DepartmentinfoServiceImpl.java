package nhjkpt.configmanage.service.impl.departmentinfo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.service.departmentinfo.DepartmentinfoServiceI;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;

@Service("departmentinfoService")
@Transactional
public class DepartmentinfoServiceImpl extends CommonServiceImpl implements DepartmentinfoServiceI {
	//获取学校今年用电量  查询年表当前月前几个月总用电量+本月小时表用电量
		public Double getUseNhThisYear(String departmentid,String funcid){
			double ret=0;
			Calendar calendar=Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
			SimpleDateFormat sdf_hg=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat sdf_year=new SimpleDateFormat("yyyy");
			
			
			Interceptor interceptor=new MyInterceptor("departmenthoursum","departmenthoursum_"+sdf.format(calendar.getTime()));
			Session session=null;
			Query query=null;
			try{
				
				session=getSession(interceptor);
				
				query=session.createSQLQuery("select ifnull(sum(data),0) as data from departmenthoursum where departmentid='"+departmentid+"' and funcid='"+funcid+"'");
				ret=(Double)query.uniqueResult();
				query=session.createSQLQuery("select ifnull(sum(data),0) as data from departmentmonthsum where  departmentid='"+departmentid+"' and funcid='"+funcid+"' and receivetime > '"+sdf_year.format(calendar.getTime())+"-01' and receivetime<'"+sdf_hg.format(calendar.getTime())+"'");
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
}