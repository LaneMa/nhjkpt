package nhjkpt.configmanage.service.impl.schoolmonthitemize;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.schoolmonthitemize.SchoolMonthItemizeEntity;
import nhjkpt.configmanage.entity.schoolmonthsum.SchoolMonthSumEntity;
import nhjkpt.configmanage.service.schooldayitemize.SchoolDayItemizeServiceI;
import nhjkpt.configmanage.service.schoolmonthitemize.SchoolMonthItemizeServiceI;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Query;

@Service("schoolMonthItemizeService")
@Transactional
public class SchoolMonthItemizeServiceImpl extends CommonServiceImpl implements SchoolMonthItemizeServiceI {

	@Autowired
	private SchoolDayItemizeServiceI schoolDayItemizeService;
	@Override
	public List<Highchart> queryHighchart(String itemizeid,String type,String startDate,String endDate){
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
			hql=" from SchoolMonthItemizeEntity SchoolMonthItemizeEntity where 1=1";
			if(!CommonUtil.isNull(itemizeid)){
				hql+=" and SchoolMonthItemizeEntity.itemizeid='"+itemizeid+"'";
			}
			sdf=new SimpleDateFormat("YYYY-MM");
			if(!CommonUtil.isNull(startDate)){
				hql+=" and SchoolMonthItemizeEntity.receivetime>='"+startDate+"'";
			}
			if(!CommonUtil.isNull(endDate)){
				hql+=" and SchoolMonthItemizeEntity.receivetime<='"+endDate+"'";
			}
			list=this.findByQueryString(hql);
			
		}else if(type.equals("day")){
			sdf=new SimpleDateFormat("yyyyMM");
			hql="select {SchoolMonthItemizeEntity.*} from schooldayitemize_";
			if(!CommonUtil.isNull(startDate)){
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
				hql+=sdf.format(sdf1.parse(startDate));
			}else{
				hql+=sdf.format(new Date());
			}
			hql+=" {SchoolMonthItemizeEntity} where 1=1 ";
			if(!CommonUtil.isNull(itemizeid)){
				hql+=" and SchoolMonthItemizeEntity.itemizeid='"+itemizeid+"'";
			}
			sdf=new SimpleDateFormat("YYYY-MM-dd");
			if(!CommonUtil.isNull(startDate)){
				hql+=" and SchoolMonthItemizeEntity.receivetime>='"+startDate+"'";
			}
			if(!CommonUtil.isNull(endDate)){
				hql+=" and SchoolMonthItemizeEntity.receivetime<='"+endDate+"'";
			}
			Query query = this.getSession().createSQLQuery(hql).addEntity("SchoolMonthItemizeEntity", SchoolMonthItemizeEntity.class);
			list=query.list();
		}else{
			sdf=new SimpleDateFormat("yyyyMM");
			hql="select {SchoolMonthItemizeEntity.*} from schoolhouritemize_";
			if(!CommonUtil.isNull(startDate)){
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
				hql+=sdf.format(sdf1.parse(startDate));
			}else{
				hql+=sdf.format(new Date());
			}
			hql+=" {SchoolMonthItemizeEntity} where 1=1 ";
			if(!CommonUtil.isNull(itemizeid)){
				hql+=" and SchoolMonthItemizeEntity.itemizeid='"+itemizeid+"'";
			}
			if(CommonUtil.isNull(startDate)&&CommonUtil.isNull(endDate)){
				sdf=new SimpleDateFormat("YYYY-MM-dd 00:00:00");
				hql+=" and SchoolMonthItemizeEntity.receivetime>='"+sdf.format(new Date())+"'";
				sdf=new SimpleDateFormat("YYYY-MM-dd 23:59:59");
				hql+=" and SchoolMonthItemizeEntity.receivetime<='"+sdf.format(new Date())+"'";
			}else{
				if(!CommonUtil.isNull(startDate)){
					hql+=" and SchoolMonthItemizeEntity.receivetime>='"+startDate+"'";
				}
				if(!CommonUtil.isNull(endDate)){
					hql+=" and SchoolMonthItemizeEntity.receivetime<='"+endDate+"'";
				}
			}
			sdf=new SimpleDateFormat("YYYY-MM-dd hh");
			Query query = this.getSession().createSQLQuery(hql).addEntity("SchoolMonthItemizeEntity", SchoolMonthItemizeEntity.class);
			list=query.list();
		}
		SchoolMonthItemizeEntity sms=null;
		for(int i=0;i<list.size();i++){
			sms=(SchoolMonthItemizeEntity) list.get(i);
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
				sms=(SchoolMonthItemizeEntity) object;
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
	public void exportSchooldayItemize(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(cal.getTime());
		SchoolMonthItemizeEntity schoolMonthItemize=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		List<Map<String,Object>> list=schoolDayItemizeService.querySchoolDayItemize(calendar);
		List<SchoolMonthItemizeEntity> listitemize=null;
		for(Map<String,Object> map:list){
			try {
				listitemize=this.findHql(" from SchoolMonthItemizeEntity where itemizeid=? and receivetime=?",map.get("itemizeid"),sdf.parse(sdf.format(calendar.getTime())));
				if(listitemize!=null&&listitemize.size()!=0){
					schoolMonthItemize=new SchoolMonthItemizeEntity();
					schoolMonthItemize=listitemize.get(0);
					schoolMonthItemize.setData((Double) map.get("data"));
					schoolMonthItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.saveOrUpdate(schoolMonthItemize);
				}else{
					schoolMonthItemize=new SchoolMonthItemizeEntity();
					schoolMonthItemize.setItemizeid((String) map.get("itemizeid"));
					schoolMonthItemize.setData((Double) map.get("data"));
					schoolMonthItemize.setReceivetime(sdf.parse(sdf.format(calendar.getTime())));
					this.save(schoolMonthItemize);
				}
			} catch (Exception e) {
			}
		}
	}
}