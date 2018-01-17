package nhjkpt.configmanage.service.impl.schoolsum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingmonthsum.BuildingMonthSumEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.schoolhouritemize.SchoolHourItemizeEntity;
import nhjkpt.configmanage.entity.schoolmonthitemize.SchoolMonthItemizeEntity;
import nhjkpt.configmanage.entity.schoolmonthsum.SchoolMonthSumEntity;
import nhjkpt.configmanage.entity.schoolsum.SchoolSumEntity;
import nhjkpt.configmanage.service.schoolsum.SchoolSumServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;

@Service("schoolSumService")
@Transactional
public class SchoolSumServiceImpl extends CommonServiceImpl implements SchoolSumServiceI {

	public List<Highchart> queryHighchartSql(String funcid, String type,String startDate, String endDate) {
		List<Highchart> listhc = new ArrayList<Highchart>();
		try{
			if(CommonUtil.isNull(type)){
				type="-1";
			}
			
			Highchart hc = new Highchart();
			List list=null;
			Double total=0d;
			SimpleDateFormat sdf=null;
			String hql=null;
			if(type.equals("month")){
				hql=" from SchoolMonthSumEntity SchoolSumEntity where 1=1";
				if(!CommonUtil.isNull(funcid)){
					hql+=" and SchoolSumEntity.funcid='"+funcid+"'";
				}
				sdf=new SimpleDateFormat("YYYY-MM");
				if(!CommonUtil.isNull(startDate)){
					hql+=" and SchoolMonthSumEntity.receivetime>='"+startDate+"'";
				}
				if(!CommonUtil.isNull(endDate)){
					hql+=" and SchoolMonthSumEntity.receivetime<='"+endDate+"'";
				}
				list=this.findByQueryString(hql);
				
			}else if(type.equals("day")){
				sdf=new SimpleDateFormat("yyyyMM");
				hql="select {SchoolMonthSumEntity.*} from schooldaysum_";
				if(!CommonUtil.isNull(startDate)){
					SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
					hql+=sdf.format(sdf1.parse(startDate));
				}else{
					hql+=sdf.format(new Date());
				}
				hql+=" {SchoolMonthSumEntity} where 1=1 ";
				if(!CommonUtil.isNull(funcid)){
					hql+=" and SchoolMonthSumEntity.funcid='"+funcid+"'";
				}
				sdf=new SimpleDateFormat("YYYY-MM-dd");
				if(!CommonUtil.isNull(startDate)){
					hql+=" and SchoolMonthSumEntity.receivetime>='"+startDate+"'";
				}
				if(!CommonUtil.isNull(endDate)){
					hql+=" and SchoolMonthSumEntity.receivetime<='"+endDate+"'";
				}
				Query query = this.getSession().createSQLQuery(hql).addEntity("SchoolMonthSumEntity", SchoolMonthSumEntity.class);
				list=query.list();
			}else{
				sdf=new SimpleDateFormat("yyyyMM");
				hql="select {SchoolMonthSumEntity.*} from schoolhoursum_";
				if(!CommonUtil.isNull(startDate)){
					SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
					hql+=sdf.format(sdf1.parse(startDate));
				}else{
					hql+=sdf.format(new Date());
				}
				hql+=" {SchoolMonthSumEntity} where 1=1 ";
				if(!CommonUtil.isNull(funcid)){
					hql+=" and SchoolMonthSumEntity.funcid='"+funcid+"'";
				}
				if(CommonUtil.isNull(startDate)&&CommonUtil.isNull(endDate)){
					sdf=new SimpleDateFormat("YYYY-MM-dd 00:00:00");
					hql+=" and SchoolMonthSumEntity.receivetime>='"+sdf.format(new Date())+"'";
					sdf=new SimpleDateFormat("YYYY-MM-dd 23:59:59");
					hql+=" and SchoolMonthSumEntity.receivetime<='"+sdf.format(new Date())+"'";
				}else{
					if(!CommonUtil.isNull(startDate)){
						hql+=" and SchoolMonthSumEntity.receivetime>='"+startDate+"'";
					}
					if(!CommonUtil.isNull(endDate)){
						hql+=" and SchoolMonthSumEntity.receivetime<='"+endDate+"'";
					}
				}
				sdf=new SimpleDateFormat("YYYY-MM-dd hh");
				Query query = this.getSession().createSQLQuery(hql).addEntity("SchoolMonthSumEntity", SchoolMonthSumEntity.class);
				list=query.list();
			}
			SchoolMonthSumEntity sms=null;
			for(int i=0;i<list.size();i++){
				sms=(SchoolMonthSumEntity) list.get(i);
				total+=sms.getData();
			}
			List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
			hc = new Highchart();
			hc.setName("用电量分时统计");
			hc.setType("column");
			Map<String, Object> map;
			if (list.size() > 0) {
				for (Object object : list) {
					map = new HashMap<String, Object>();
					sms=(SchoolMonthSumEntity) object;
					map.put("name", sdf.format(sms.getReceivetime()));
					map.put("y", CommonUtil.formateResult(sms.getData()));
					Double groupCount = sms.getData();
					Double  percentage = 0.0;
					if (total != null && total.intValue() != 0) {
						percentage = groupCount/total;
					}
					map.put("percentage", percentage*100);
					lt.add(map);
				}
			}
			hc.setData(lt);
			listhc.add(hc);
		}catch(Exception e){
			
		}
		return listhc;
	}

	@Override
	public List<Highchart> queryHighchart(String funcid, String type,String startDate, String endDate,String pictype) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		Calendar calendar=Calendar.getInstance();
		List<SchoolMonthSumEntity> listdata=null;
		String hql=" from SchoolMonthSumEntity where 1=1 ";
		if(!CommonUtil.isNull(funcid)){
			hql+=" and funcid='"+commonDao.get(FuncEntity.class, funcid).getFuncid()+"'";
		}
		listdata=queryHighchartData(hql, type, "schoolmonthsum", "schooldaysum", "schoolhoursum", startDate, endDate,SchoolMonthSumEntity.class,"funcid");
		hql=" from FuncEntity where ischeck='1'"; //统计功能才进行统计
		if(!CommonUtil.isNull(funcid)){
			hql+=" and id='"+funcid+"'";
		}
		if(listdata!=null&&listdata.size()!=0){
			List<FuncEntity> listfunc=this.findByQueryString(hql);
			for(FuncEntity func:listfunc){
				lt = null;
				hc = new Highchart();
				String funName=func.getFuncname();
				if(funName.indexOf("电")>=0){
					funName=funName+"(千瓦时)";
				}else if(funName.indexOf("水")>=0){
					funName=funName+"(吨)";
				}else{
					
				}
				hc.setName(funName);
				hc.setType(pictype);
				long count=0;
				for(SchoolMonthSumEntity sum:listdata){
					count+=sum.getData();
				}
				for(SchoolMonthSumEntity sum:listdata){
					if(sum.getFuncid().equals(func.getFuncid().toString())){
						if(lt==null){
							lt = new ArrayList<Map<String, Object>>();
						}
						map = new HashMap<String, Object>();
						map.put("name", findName(type, sum.getReceivetime()));
						map.put("y", CommonUtil.formateResult(sum.getData()));
						map.put("percentage", sum.getData()>0?(sum.getData()/count*100):0);
						lt.add(map);
					}
				}
				if(lt!=null){
					hc.setData(lt);
					listhc.add(hc);
				}
			}
		}
		return listhc;
	}

	@Override
	public List<Highchart> queryEnergySortCCBar() {
		// TODO Auto-generated method stub
		
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<SchoolMonthItemizeEntity> listdata=null;
		List<ItemizeEntity> listItemize=this.getList(ItemizeEntity.class);
		
		
		
		
		SimpleDateFormat sdf=null;
		Calendar calendar=Calendar.getInstance();
		sdf=new SimpleDateFormat("yyyy-MM-dd");
		
	    String strToday=sdf.format(calendar.getTime());
	    String startDate=strToday+" 00";
	    String endDate=strToday+" 23";
		
	    String hql="select new SchoolMonthItemizeEntity (itemizeid,sum(data) as data) from SchoolHourItemizeEntity where  1=1  ";
		listdata=queryHighchartData(hql, "-1", "schoolhouritemize", "schooldayitemize", "schoolhouritemize", startDate, endDate,SchoolMonthItemizeEntity.class,null," group by itemizeid");
		
		lt = null;
		hc = new Highchart();
		hc.setName("本日分类用能(千瓦时)");
		hc.setType("pie");
		String itemizeid=null;
		if(listdata!=null){
			for(SchoolMonthItemizeEntity school:listdata){
				if(lt==null){
					lt = new ArrayList<Map<String, Object>>();
				}
				map = new HashMap<String, Object>();
				for(ItemizeEntity itemize:listItemize){
					if(CommonUtil.NEWID){
						itemizeid=itemize.getId();
					}else{
						itemizeid=itemize.getItemizeid().toString();
					}
					if(itemizeid.equals(school.getItemizeid())){
						map.put("name", itemize.getItemizetext());
						break;
					}
				}
				map.put("y", CommonUtil.formateResult(school.getData()));
				lt.add(map);
			}
		}
		if(lt!=null){
			hc.setData(lt);
			listhc.add(hc);
		}
		
		return listhc;
		
		
		
	}
}