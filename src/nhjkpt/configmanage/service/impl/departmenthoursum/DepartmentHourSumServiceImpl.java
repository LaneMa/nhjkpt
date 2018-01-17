package nhjkpt.configmanage.service.impl.departmenthoursum;

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

import nhjkpt.configmanage.entity.departmenthoursum.DepartmentHourSumEntity;
import nhjkpt.configmanage.entity.departmentinfo.DepartmentinfoEntity;
import nhjkpt.configmanage.entity.departmentsum.DepartmentSumEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.hourdata.HourdataEntity;
import nhjkpt.configmanage.service.departmenthoursum.DepartmentHourSumServiceI;
import nhjkpt.configmanage.service.hourdata.HourdataServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("departmentHourSumService")
@Transactional
public class DepartmentHourSumServiceImpl extends CommonServiceImpl implements DepartmentHourSumServiceI {
	
	@Autowired
	private HourdataServiceI hourdataService;
	@Override
	public void exportHourData(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		List<FuncEntity> listFunc=findByQueryString(" from FuncEntity");
		List<DepartmentinfoEntity> listdepartment=findHql(" from DepartmentinfoEntity");
		List<DepartmentSumEntity> list=findByQueryString(" from DepartmentSumEntity");
		List<HourdataEntity> listHourdata=null;
		Double value=0.0;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		String sql="CREATE TABLE IF NOT EXISTS  `departmenthoursum_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`departmentid` VARCHAR(32) DEFAULT NULL," +
				"`funcid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("departmenthoursum","departmenthoursum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			DepartmentHourSumEntity departmentHourSum=null;
			Transaction tran=null;
			Query query=null;
			List<DepartmentHourSumEntity> listsum=null;
			String funcid=null;
			String departmentid=null;
			for(DepartmentinfoEntity department:listdepartment){
				if(CommonUtil.NEWID){
					departmentid=department.getId();
				}else{
					departmentid=department.getDepartmentid();
				}
				for(FuncEntity func:listFunc){
					value=0.0;
					if(CommonUtil.NEWID){
						funcid=func.getId();
					}else{
						funcid=func.getFuncid().toString();
					}
					departmentHourSum=new DepartmentHourSumEntity();
					departmentHourSum.setFuncid(funcid);
					for(DepartmentSumEntity departmentSum:list){
						if(departmentSum.getDepartmentid().equals(departmentid)&&departmentSum.getFuncid().equals(funcid)){
							listHourdata=hourdataService.queryHourdataByHour(departmentSum.getFuncid(),departmentSum.getMeterid(),calendar);
							for(HourdataEntity hourdata : listHourdata){
								departmentHourSum.setDepartmentid(departmentid);
								value+=hourdata.getData()*100*departmentSum.getFactor()/100;
							}
						}
					}
					if(!CommonUtil.isNull(departmentHourSum.getDepartmentid())){
						try {
							query=session.createQuery(" from DepartmentHourSumEntity where departmentid=:departmentid and funcid=:funcid and date_format(receivetime,'%Y-%m-%d %H')=:receivetime");
							query.setString("departmentid", departmentHourSum.getDepartmentid());
							query.setString("funcid", departmentHourSum.getFuncid());
							query.setString("receivetime", sdf.format(calendar.getTime()));
							listsum=query.list();
							if(listsum!=null&&listsum.size()!=0){
								departmentHourSum=listsum.get(0);
								departmentHourSum.setData(value);
								tran=session.beginTransaction();
								session.update(departmentHourSum);
								tran.commit();
							}else{
								departmentHourSum.setData(value);
								departmentHourSum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
								tran=session.beginTransaction();
								session.save(departmentHourSum);
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
	public Double queryDepartmentHourSum(String funcid,Calendar calendar) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("departmenthoursum","departmenthoursum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select sum(data) from DepartmentHourSumEntity where funcid=:funcid and receivetime=:receivetime");
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
	public List<Map<String, Object>> queryDepartmentHourSum(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("departmenthoursum","departmenthoursum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select funcid,departmentid, sum(data) from DepartmentHourSumEntity where receivetime>=:startDate and receivetime<=:endDate group by departmentid,funcid");
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
				map.put("departmentid", obj[1]);
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