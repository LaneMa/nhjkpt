package nhjkpt.configmanage.service.impl.schoolhoursum;

import java.math.BigDecimal;
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
import nhjkpt.configmanage.entity.schoolhoursum.SchoolhoursumEntity;
import nhjkpt.configmanage.entity.schoolmonthsum.SchoolMonthSumEntity;
import nhjkpt.configmanage.entity.schoolsum.SchoolSumEntity;
import nhjkpt.configmanage.service.hourdata.HourdataServiceI;
import nhjkpt.configmanage.service.schoolhoursum.SchoolhoursumServiceI;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("schoolhoursumService")
@Transactional
public class SchoolhoursumServiceImpl extends CommonServiceImpl implements SchoolhoursumServiceI {

	@Autowired
	private HourdataServiceI hourdataService;
	@Autowired
	private SystemService systemService;
	
	@Override
	public void exportHourdata(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		
		List<FuncEntity> listFunc = systemService.findHql(" from FuncEntity where ischeck=? order by funcid", "1");
		
		List<SchoolSumEntity> list=findByQueryString(" from SchoolSumEntity");
		List<HourdataEntity> listHourdata=null;
		Double value=0.0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		String sql="CREATE TABLE IF NOT EXISTS  `schoolhoursum_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`funcid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		//创建表名替换拦截器
		Interceptor interceptor=new MyInterceptor("schoolhoursum","schoolhoursum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			SchoolhoursumEntity schoolhoursum=null;
			Transaction tran=null;
			Query query=null;
			String funcid=null;
			List<SchoolhoursumEntity> listsum=null;
			for(FuncEntity func:listFunc){
				value=0.0;
				if(CommonUtil.NEWID){
					funcid=func.getId();
				}else{
					funcid=func.getFuncid().toString();
				}
				schoolhoursum=new SchoolhoursumEntity();
				for(SchoolSumEntity schoolSum:list){
					if(funcid.equals(schoolSum.getFuncid())){
						listHourdata=hourdataService.queryHourdataByHour(schoolSum.getFuncid(),schoolSum.getMeterid(),calendar);
						if(listHourdata!=null&&listHourdata.size()!=0){
							for(HourdataEntity hourdata : listHourdata){
								if(CommonUtil.NEWID){
									schoolhoursum.setFuncid(func.getId());
								}else{
									schoolhoursum.setFuncid(func.getFuncid().toString());
								}
								value+=hourdata.getData()*100*schoolSum.getFactor()/100;
							}
						}
					}
				}
				if(!CommonUtil.isNull(schoolhoursum.getFuncid())){
					try {
						query=session.createQuery(" from SchoolhoursumEntity where funcid=:funcid and date_format(receivetime,'%Y-%m-%d %H')=:receivetime");
						query.setString("funcid", schoolhoursum.getFuncid());
						query.setString("receivetime", sdf.format(calendar.getTime()));
						listsum=query.list();
						if(listsum!=null&&listsum.size()!=0){
							schoolhoursum=listsum.get(0);
							schoolhoursum.setData(value);
							tran=session.beginTransaction();
							session.update(schoolhoursum);
							tran.commit();
						}else{
							schoolhoursum.setData(value);
							schoolhoursum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
							tran=session.beginTransaction();
							session.save(schoolhoursum);
							tran.commit();
						}
					} catch (ParseException e) {
						e.printStackTrace();
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
	public Double querySchoolhoursum(String funcid,Calendar calendar) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("schoolhoursum","schoolhoursum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select sum(data) from SchoolhoursumEntity where funcid=:funcid and date_format(receivetime,'%Y-%m-%d')=:receivetime");
			query.setString("funcid", funcid);
			query.setString("receivetime", sdf.format(calendar.getTime()));
			
			BigDecimal bg = new BigDecimal((Double) query.uniqueResult());
	        double ret = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return ret;
	
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
	public List<Map<String, Object>> querySchoolhoursum(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("schoolhoursum","schoolhoursum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select funcid, sum(data) from SchoolhoursumEntity where receivetime>=:startDate and receivetime<=:endDate group by funcid");
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
	}
	@Override
	public List<Highchart> queryEnergySortNEBar() {
		List<Highchart> listhc = new ArrayList<Highchart>();
		String hql=" from FuncEntity f where f.ischeck=? and f.showtext=?";
		List<FuncEntity> listFunc=this.commonDao.findHql(hql,"1","水量");
		if(listFunc!=null){
			Session session=null;
			Highchart hc=null;
			try{
				hql=" from SchoolhoursumEntity s where s.funcid=:funcid and date_format(receivetime,'%Y-%m-%d')=:receivetime order by receivetime";
				Calendar calendar=Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
				Interceptor interceptor=new MyInterceptor("schoolhoursum","schoolhoursum_"+simpledateformat.format(calendar.getTime()));
				session=getSession(interceptor);
				Query query=session.createQuery(hql);
				if(CommonUtil.NEWID){
					query.setString("funcid", listFunc.get(0).getId());
				}else{
					query.setString("funcid", listFunc.get(0).getFuncid().toString());
				}
				query.setString("receivetime", sdf.format(calendar.getTime()));
				List<SchoolhoursumEntity> list=query.list();
				if(list!=null){
					List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
					hc = new Highchart();
					hc.setName("本日用水柱状图");
					hc.setType("column");
					Map<String, Object> map;
					if (list.size() > 0) {
						for (SchoolhoursumEntity sum : list) {
							map = new HashMap<String, Object>();
							map.put("name", sum.getReceivetime().getHours());
							map.put("y", CommonUtil.formateResult(sum.getData()));
							lt.add(map);
						}
					}
					hc.setData(lt);
					listhc.add(hc);
				}
			}catch(Exception e){
			}finally{
				if(session!=null){
					session.close();
				}
			}
		}
		return listhc;
	}
	@Override
	public List<Highchart> queryEnergySortNCBar() {
		List<Highchart> listhc = new ArrayList<Highchart>();
		String hql=" from FuncEntity f where f.ischeck=? and f.showtext=?";
		List<FuncEntity> listFunc=this.commonDao.findHql(hql,"1","电量");
		if(!CommonUtil.isNull(listFunc)){
			Session session=null;
			Highchart hc=null;
			try{
				hql=" from SchoolhoursumEntity s where s.funcid=:funcid and date_format(receivetime,'%Y-%m-%d')=:receivetime order by receivetime";
				Calendar calendar=Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
				Interceptor interceptor=new MyInterceptor("schoolhoursum","schoolhoursum_"+simpledateformat.format(calendar.getTime()));
				session=getSession(interceptor);
				Query query=session.createQuery(hql);
				if(CommonUtil.NEWID){
					query.setString("funcid", listFunc.get(0).getId());
				}else{
					query.setString("funcid", listFunc.get(0).getFuncid().toString());
				}
				query.setString("receivetime", sdf.format(calendar.getTime()));
				List<SchoolhoursumEntity> list=query.list();
				if(list!=null){
					List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
					hc = new Highchart();
					hc.setName("本日用电柱状图");
					hc.setType("column");
					Map<String, Object> map;
					if (list.size() > 0) {
						for (SchoolhoursumEntity sum : list) {
							map = new HashMap<String, Object>();
							map.put("name", sum.getReceivetime().getHours());
							map.put("y", CommonUtil.formateResult(sum.getData()));
							lt.add(map);
						}
					}
					hc.setData(lt);
					listhc.add(hc);
				}
			}catch(Exception e){
			}finally{
				if(session!=null){
					session.close();
				}
			}
		}
		return listhc;
	}
	
}