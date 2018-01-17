package nhjkpt.configmanage.service.impl.departmenthouritemize;

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

import nhjkpt.configmanage.entity.departmenthouritemize.DepartmentHourItemizeEntity;
import nhjkpt.configmanage.entity.departmentinfo.DepartmentinfoEntity;
import nhjkpt.configmanage.entity.departmentitemize.DepartmentItemizeEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.hourdata.HourdataEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.service.departmenthouritemize.DepartmentHourItemizeServiceI;
import nhjkpt.configmanage.service.hourdata.HourdataServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("departmentHourItemizeService")
@Transactional
public class DepartmentHourItemizeServiceImpl extends CommonServiceImpl implements DepartmentHourItemizeServiceI {
	
	@Autowired
	private HourdataServiceI hourdataService;
	@Override
	public void exportHourdata(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		List<FuncEntity> listFunc=findByQueryString(" from FuncEntity");
		List<ItemizeEntity> listItemize=findByQueryString(" from ItemizeEntity");
		List<DepartmentinfoEntity> listdepartment=findHql(" from DepartmentinfoEntity");
		List<DepartmentItemizeEntity> list=findByQueryString(" from DepartmentItemizeEntity");
		List<HourdataEntity> listHourdata=null;
		Double value=0.0;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		String sql="CREATE TABLE IF NOT EXISTS  `departmenthouritemize_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`departmentid` VARCHAR(32) DEFAULT NULL," +
				"`itemizeid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("departmenthouritemize","departmenthouritemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			DepartmentHourItemizeEntity departmentHourItemize=null;
			Transaction tran=null;
			Query query=null;
			List<DepartmentHourItemizeEntity> listitemize=null;
			String funcid=null;
			String itemid=null;
			String departmentid=null;
			for(DepartmentinfoEntity depart:listdepartment){
				if(CommonUtil.NEWID){
					departmentid=depart.getId();
				}else{
					departmentid=depart.getDepartmentid();
				}
				for(ItemizeEntity item:listItemize){
					value=0.0;
					if(CommonUtil.NEWID){
						itemid=item.getId();
					}else{
						itemid=item.getItemizeid().toString();
					}
					departmentHourItemize=new DepartmentHourItemizeEntity();
					departmentHourItemize.setItemizeid(itemid);
					for(FuncEntity func:listFunc){
						if(CommonUtil.NEWID){
							funcid=func.getId();
						}else{
							funcid=func.getFuncid().toString();
						}
						for(DepartmentItemizeEntity department:list){
							if(department.getDepartmentid().equals(departmentid)&&department.getFuncid().equals(funcid)&&itemid.equals(department.getItemizeid())){
								listHourdata=hourdataService.queryHourdataByHour(department.getFuncid(),department.getMeterid(),calendar);
								for(HourdataEntity hourdata : listHourdata){
									departmentHourItemize.setDepartmentid(departmentid);
									departmentHourItemize.setItemizeid(itemid);
									value+=hourdata.getData()*100*department.getFactor()/100;
								}
							}
						}
					}
					if(!CommonUtil.isNull(departmentHourItemize.getDepartmentid())){
						try {
							query=session.createQuery(" from DepartmentHourItemizeEntity where departmentid=:departmentid and itemizeid=:itemizeid and date_format(receivetime,'%Y-%m-%d %H')=:receivetime");
							query.setString("departmentid", departmentHourItemize.getDepartmentid());
							query.setString("itemizeid", departmentHourItemize.getItemizeid());
							query.setString("receivetime", sdf.format(calendar.getTime()));
							listitemize=query.list();
							if(listitemize!=null&&listitemize.size()!=0){
								departmentHourItemize=listitemize.get(0);
								departmentHourItemize.setData(value);
								tran=session.beginTransaction();
								session.update(departmentHourItemize);
								tran.commit();
							}else{
								departmentHourItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
								departmentHourItemize.setData(value);
								tran=session.beginTransaction();
								session.save(departmentHourItemize);
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
	public Double queryDepartmentHourItemizeid(String itemizeid,Calendar calendar) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("departmenthouritemize","departmenthouritemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select sum(data) from DepartmentHourItemizeEntity where itemizeid=:itemizeid and date_format(receivetime,'%Y-%m-%d')=:receivetime");
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
	public List<Map<String, Object>> queryDepartmentHourItemizeid(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("departmenthouritemize","departmenthouritemize_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select itemizeid,departmentid, sum(data) from DepartmentHourItemizeEntity where receivetime>=:startDate and receivetime<=:endDate group by itemizeid,departmentid");
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