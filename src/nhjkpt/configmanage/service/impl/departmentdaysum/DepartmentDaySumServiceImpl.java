package nhjkpt.configmanage.service.impl.departmentdaysum;

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

import nhjkpt.configmanage.entity.departmentdaysum.DepartmentDaySumEntity;
import nhjkpt.configmanage.entity.departmentinfo.DepartmentinfoEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.service.departmentdaysum.DepartmentDaySumServiceI;
import nhjkpt.configmanage.service.departmenthoursum.DepartmentHourSumServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("departmentDaySumService")
@Transactional
public class DepartmentDaySumServiceImpl extends CommonServiceImpl implements DepartmentDaySumServiceI {

	@Autowired
	private DepartmentHourSumServiceI departmentHourSumService;
	@Override
	public void exportDepartmentHourSum(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		DepartmentDaySumEntity departmentdaysum=null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String sql="CREATE TABLE IF NOT EXISTS  `departmentdaysum_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`departmentid` VARCHAR(32) DEFAULT NULL," +
				"`funcid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("departmentdaysum","departmentdaysum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Transaction tran=null;
			Query query=null;
			List<DepartmentDaySumEntity> listsum=null;
			List<Map<String,Object>> list=departmentHourSumService.queryDepartmentHourSum(calendar);
			for(Map<String,Object> map:list){
				try {
					query=session.createQuery(" from DepartmentDaySumEntity where departmentid=:departmentid and funcid=:funcid and date_format(receivetime,'%Y-%m-%d')=:receivetime");
					query.setString("departmentid", (String) map.get("departmentid"));
					query.setString("funcid", (String) map.get("funcid"));
					query.setString("receivetime", sdf.format(calendar.getTime()));
					listsum=query.list();
					if(listsum!=null&&listsum.size()!=0){
						departmentdaysum=listsum.get(0);
						departmentdaysum.setData((Double) map.get("data"));
						departmentdaysum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						tran=session.beginTransaction();
						session.update(departmentdaysum);
						tran.commit();
					}else{
						departmentdaysum=new DepartmentDaySumEntity();
						departmentdaysum.setDepartmentid((String) map.get("departmentid"));
						departmentdaysum.setFuncid((String) map.get("funcid"));
						departmentdaysum.setData((Double) map.get("data"));
						departmentdaysum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						tran=session.beginTransaction();
						session.save(departmentdaysum);
						tran.commit();
					}
				} catch (ParseException e) {
					e.printStackTrace();
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
	public Double queryDepartmentDaySum(String funcid,Calendar calendar) {
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
			Interceptor interceptor=new MyInterceptor("departmentdaysum","departmentdaysum_"+simpledateformat.format(calendar.getTime()));
			Session session=null;
			try{
				session=getSession(interceptor);
				Query query=session.createQuery("select sum(data) from DepartmentDaySumEntity where funcid=:funcid and receivetime=:receivetime");
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
	public List<Map<String, Object>> queryDepartmentDaySum(Calendar calendar) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("departmentdaysum","departmentdaysum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select funcid,departmentid, sum(data) from DepartmentDaySumEntity where date_format(receivetime,'%Y-%m')=:receivetime group by departmentid,funcid");
			query.setString("receivetime", sdf.format(calendar.getTime()));
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
				session.close();
			}
		}
		return list;
	}
	@Override
	public List<Highchart> queryEnergySortCEBar() {
		List<Highchart> listhc = new ArrayList<Highchart>();
		String hql=" from FuncEntity f where f.ischeck=? and f.showtext=?";
		List<FuncEntity> listFunc=this.commonDao.findHql(hql,"1","电量");
		if(listFunc!=null){
			Session session=null;
			Highchart hc=null;
			try{
				hql=" from DepartmentDaySumEntity s where s.funcid=:funcid and date_format(receivetime,'%Y-%m-%d')=:receivetime order by data desc";
				Calendar calendar=Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
				Interceptor interceptor=new MyInterceptor("departmentdaysum","departmentdaysum_"+simpledateformat.format(calendar.getTime()));
				session=getSession(interceptor);
				Query query=session.createQuery(hql);
				query.setString("funcid", listFunc.get(0).getId());
				query.setString("receivetime", sdf.format(calendar.getTime()));
				query.setFirstResult(0);
				query.setMaxResults(10);
				List<DepartmentDaySumEntity> list=query.list();
				if(list!=null){
					List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
					hc = new Highchart();
					hc.setName("本日建筑物用电排名");
					hc.setType("column");
					Map<String, Object> map;
					if (list.size() > 0) {
						for (DepartmentDaySumEntity sum : list) {
							map = new HashMap<String, Object>();
							DepartmentinfoEntity department=this.findUniqueByProperty(DepartmentinfoEntity.class, "id", sum.getDepartmentid());
							map.put("name", department.getDepartmentname());
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
		Session session=null;
		Highchart hc=null;
		return listhc;
	}
}