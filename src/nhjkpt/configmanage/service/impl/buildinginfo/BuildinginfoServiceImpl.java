package nhjkpt.configmanage.service.impl.buildinginfo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.service.buildinginfo.BuildinginfoServiceI;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;

@Service("buildinginfoService")
@Transactional
public class BuildinginfoServiceImpl extends CommonServiceImpl implements BuildinginfoServiceI {
	
	@Override
	public String queryIdByBuildingid(String buildingid){
		if(buildingid !=null && buildingid.length()>0){
			List<BuildinginfoEntity> list=this.findByProperty(BuildinginfoEntity.class, "buildingid",String.valueOf(Integer.parseInt(buildingid)) );
			if(list!=null&&list.size()!=0){
				return list.get(0).getId();
			}else{
	//			BuildinginfoEntity building=new BuildinginfoEntity();
	//			building.setBuildingid(buildingid);
	//			building.setBuildingname(buildingid);
	//			building.setBuildingtext("瞬时信息导入");
	//			save(building);
	//			return building.getId();
				//System.out.println("大楼信息不存在："+buildingid);
				return null;
			}
		}
		else{
			System.out.println("发过来大楼id为空====：");
			return null;
		}
	}
	
	
	
	//获取学校今年用电量  查询年表当前月前几个月总用电量+本月小时表用电量
		public Double getUseNhThisYear(String buildingid,String funcid){
			double ret=0;
			Calendar calendar=Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
			SimpleDateFormat sdf_hg=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat sdf_year=new SimpleDateFormat("yyyy");
			
			
			Interceptor interceptor=new MyInterceptor("buildinghoursum","buildinghoursum_"+sdf.format(calendar.getTime()));
			Session session=null;
			Query query=null;
			try{
				
				session=getSession(interceptor);
				
				query=session.createSQLQuery("select ifnull(sum(data),0) as data from buildinghoursum where buildingid='"+buildingid+"' and funcid='"+funcid+"'");
				ret=(Double)query.uniqueResult();
				query=session.createSQLQuery("select ifnull(sum(data),0) as data from buildingmonthsum where buildingid='"+buildingid+"' and funcid='"+funcid+"' and receivetime > '"+sdf_year.format(calendar.getTime())+"-01' and receivetime<'"+sdf_hg.format(calendar.getTime())+"'");
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