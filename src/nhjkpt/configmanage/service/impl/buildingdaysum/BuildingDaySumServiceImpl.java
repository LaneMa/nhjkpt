package nhjkpt.configmanage.service.impl.buildingdaysum;

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

import nhjkpt.configmanage.entity.buildingdaysum.BuildingDaySumEntity;
import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.schoolhoursum.SchoolhoursumEntity;
import nhjkpt.configmanage.service.buildingdaysum.BuildingDaySumServiceI;
import nhjkpt.configmanage.service.buildinghoursum.BuildingHourSumServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("buildingDaySumService")
@Transactional
public class BuildingDaySumServiceImpl extends CommonServiceImpl implements BuildingDaySumServiceI {
	@Autowired
	private BuildingHourSumServiceI buildingHourSumService;
	@Override
	public void exportBuildingHourSum(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		BuildingDaySumEntity buildingdaysum=null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String sql="CREATE TABLE IF NOT EXISTS  `buildingdaysum_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`buildingid` VARCHAR(32) DEFAULT NULL," +
				"`funcid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Interceptor interceptor=new MyInterceptor("buildingdaysum","buildingdaysum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Transaction tran=null;
			Query query=null;
			List<BuildingDaySumEntity> listsum=null;
			List<Map<String,Object>> list=buildingHourSumService.queryBuildingHourSum(calendar);
			for(Map<String,Object> map:list){
				try {
					query=session.createQuery(" from BuildingDaySumEntity where buildingid=:buildingid and funcid=:funcid and date_format(receivetime,'%Y-%m-%d')=:receivetime");
					query.setString("buildingid", (String) map.get("buildingid"));
					query.setString("funcid", (String) map.get("funcid"));
					query.setString("receivetime", sdf.format(calendar.getTime()));
					listsum=query.list();
					if(listsum!=null&&listsum.size()!=0){
						buildingdaysum=listsum.get(0);
						buildingdaysum.setData((Double) map.get("data"));
						buildingdaysum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						tran=session.beginTransaction();
						session.update(buildingdaysum);
						tran.commit();
					}else{
						buildingdaysum=new BuildingDaySumEntity();
						buildingdaysum.setBuildingid((String) map.get("buildingid"));
						buildingdaysum.setFuncid((String) map.get("funcid"));
						buildingdaysum.setData((Double) map.get("data"));
						buildingdaysum.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
						tran=session.beginTransaction();
						session.save(buildingdaysum);
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
	public Double queryBuildingDaySum(String funcid,Calendar calendar) {
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
			Interceptor interceptor=new MyInterceptor("buildingdaysum","buildingdaysum_"+simpledateformat.format(calendar.getTime()));
			Session session=null;
			try{
				session=getSession(interceptor);
				Query query=session.createQuery("select sum(data) from BuildingDaySumEntity where funcid=:funcid and receivetime=:receivetime");
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
	public List<Map<String, Object>> queryBuildingDaySum(Calendar calendar) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Interceptor interceptor=new MyInterceptor("buildingdaysum","buildingdaysum_"+simpledateformat.format(calendar.getTime()));
		Session session=null;
		try{
			session=getSession(interceptor);
			Query query=session.createQuery("select funcid,buildingid, sum(data) from BuildingDaySumEntity where date_format(receivetime,'%Y-%m')=:receivetime group by buildingid,funcid");
			query.setString("receivetime", sdf.format(calendar.getTime()));
			Iterator iter=query.iterate();
			Map<String,Object> map=null;
			Object[] obj=null;
			while(iter.hasNext()){
				map=new HashMap<String, Object>();
				obj=(Object[])iter.next();
				map.put("funcid", obj[0]);
				map.put("buildingid", obj[1]);
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
		if(!CommonUtil.isNull(listFunc)){
			Session session=null;
			Highchart hc=null;
			try{
				hql=" from BuildingDaySumEntity s where s.funcid=:funcid and date_format(receivetime,'%Y-%m-%d')=:receivetime order by data desc limit 5";
				Calendar calendar=Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
				Interceptor interceptor=new MyInterceptor("buildingdaysum","buildingdaysum_"+simpledateformat.format(calendar.getTime()));
				session=getSession(interceptor);
				Query query=session.createQuery(hql);
				if(CommonUtil.NEWID){
					query.setString("funcid", listFunc.get(0).getId());
				}else{
					query.setString("funcid", listFunc.get(0).getFuncid().toString());
				}
				query.setString("receivetime", sdf.format(calendar.getTime()));
				query.setFirstResult(0);
				query.setMaxResults(10);
				List<BuildingDaySumEntity> list=query.list();
				if(list!=null){
					List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
					hc = new Highchart();
					hc.setName("本日建筑物用电排名");
					hc.setType("column");
					Map<String, Object> map;
					if (list.size() > 0) {
						int ii=0;
						for (BuildingDaySumEntity sum : list) {
							map = new HashMap<String, Object>();
							BuildinginfoEntity building=this.findUniqueByProperty(BuildinginfoEntity.class, "buildingid", sum.getBuildingid());
							map.put("name", building.getBuildingname());
							map.put("y", CommonUtil.formateResult(sum.getData()));
							lt.add(map);
							ii=ii+1;
							if(ii>5){
								break;
							}
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