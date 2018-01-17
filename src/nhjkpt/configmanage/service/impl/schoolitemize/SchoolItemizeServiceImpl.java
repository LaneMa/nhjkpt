package nhjkpt.configmanage.service.impl.schoolitemize;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.buildingmonthitemize.BuildingMonthItemizeEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.schoolmonthitemize.SchoolMonthItemizeEntity;
import nhjkpt.configmanage.service.schoolitemize.SchoolItemizeServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;

@Service("schoolItemizeService")
@Transactional
public class SchoolItemizeServiceImpl extends CommonServiceImpl implements SchoolItemizeServiceI {

	@Override
	public List<Highchart> queryHighchart(String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sdf=null;
		List<SchoolMonthItemizeEntity> listdata=null;
		String hql=" from SchoolMonthItemizeEntity where  1=1  ";
		if(!CommonUtil.isNull(itemizeid)){
			hql+=" and itemizeid='"+itemizeid+"'";
		}
		listdata=queryHighchartData(hql, type, "schoolmonthitemize", "schooldayitemize", "schoolhouritemize", startDate, endDate,SchoolMonthItemizeEntity.class,"itemizeid");
		hql=" from ItemizeEntity";
		List<ItemizeEntity> listitemize=this.findByQueryString(hql);
		//每一个分类
		for(ItemizeEntity itemize:listitemize){
			lt = null;
			hc = new Highchart();
			hc.setName(itemize.getItemizetext());
			hc.setType("column");
			for(SchoolMonthItemizeEntity school:listdata){
				if(itemize.getId().equals(school.getItemizeid())){
					if(lt==null){
						lt = new ArrayList<Map<String, Object>>();
					}
					map = new HashMap<String, Object>();
					map.put("name", findName(type, school.getReceivetime()));
					map.put("y", CommonUtil.formateResult(school.getData()));
					lt.add(map);
				}
			}
			if(lt!=null){
				hc.setData(lt);
				listhc.add(hc);
			}
		}
		return listhc;
	}
	@Override
	public List<Highchart> queryHighchartItemize(String itemizeid, String type,String startDate, String endDate,String pictype) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<SchoolMonthItemizeEntity> listdata=null;
		String hql=" from SchoolMonthItemizeEntity where  1=1 ";
		if(!CommonUtil.isNull(itemizeid)){
			if(itemizeid.indexOf(",")<0){
				hql+=" and itemizeid ='"+commonDao.get(ItemizeEntity.class, itemizeid).getItemizeid()+"'";
			}else{
				itemizeid=itemizeid.replaceAll(",", "','");
				hql+=" and itemizeid in (select itemizeid from ItemizeEntity where id in ('"+itemizeid+"'))";
			}
		}
		listdata=queryHighchartData(hql, type, "schoolmonthitemize", "schooldayitemize", "schoolhouritemize", startDate, endDate,SchoolMonthItemizeEntity.class,"itemizeid");
		hql=" from ItemizeEntity where 1=1";
		if(!CommonUtil.isNull(itemizeid)){
			if(itemizeid.indexOf(",")<0){
				hql+=" and id ='"+itemizeid+"'";
			}else{
				hql+=" and id in('"+itemizeid+"')";
			}
		}
		List<ItemizeEntity> listitemize=this.findByQueryString(hql);
		//每一个分类显示一条线
		for(ItemizeEntity itemize:listitemize){
			lt = null;
			hc = new Highchart();
			String tmpName="";
			tmpName=itemize.getItemizetext();
			if(tmpName!=null && (tmpName.indexOf("电")>=0 || tmpName.indexOf("照明")>=0)){
				tmpName+="(千瓦时)";
			}else{
				tmpName+="(吨)";
			}
			hc.setName(tmpName);
			hc.setType(pictype);
			for(SchoolMonthItemizeEntity school:listdata){
				if(itemize.getItemizeid().toString().equals(school.getItemizeid())){
					if(lt==null){
						lt = new ArrayList<Map<String, Object>>();
					}
					map = new HashMap<String, Object>();
					map.put("name", findName(type, school.getReceivetime()));
					map.put("y", CommonUtil.formateResult(school.getData()));
					lt.add(map);
				}
			}
			if(lt!=null){
				hc.setData(lt);
				listhc.add(hc);
			}
		}
		return listhc;
	}

	@Override
	public List<Highchart> queryHighchartDate(String itemizeid, String type,String[] startDate, String[] endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		if(startDate==null){
			return listhc;
		}
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<SchoolMonthItemizeEntity> listdata=null;
		String hql=" from SchoolMonthItemizeEntity where  1=1 ";
		if(!CommonUtil.isNull(itemizeid)){
			hql+=" and itemizeid='"+commonDao.get(ItemizeEntity.class, itemizeid).getItemizeid()+"'";
		}
		String endTime=null;
		String startTime=null;
		for(int i=0;i<startDate.length;i++){
			startTime=startDate[i];
			endTime=endDate[i];
			listdata=queryHighchartData(hql, type, "schoolmonthitemize", "schooldayitemize", "schoolhouritemize", startTime, endTime,SchoolMonthItemizeEntity.class,null);
			lt = null;
			hc = new Highchart();
			hc.setName(startTime+"-"+endTime);
			hc.setType("column");
			for(SchoolMonthItemizeEntity school:listdata){
				if(lt==null){
					lt = new ArrayList<Map<String, Object>>();
				}
				map = new HashMap<String, Object>();
				map.put("name", findName(type, school.getReceivetime()));
				map.put("y", CommonUtil.formateResult(school.getData()));
				lt.add(map);
			}
			if(lt!=null){
				hc.setData(lt);
				listhc.add(hc);
			}
		}
		return listhc;
	}
	@Override
	public List<Highchart> queryHighchartItemizePie(String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<SchoolMonthItemizeEntity> listdata=null;
		List<ItemizeEntity> listItemize=this.getList(ItemizeEntity.class);
		List<ItemizeEntity> list=new ArrayList<ItemizeEntity>();
		if(!CommonUtil.isNull(itemizeid)){
			for(ItemizeEntity itemize:listItemize){
				if(itemize.getId().equals(itemizeid)){
					list.add(itemize);
					break;
				}
			}
		}
		findItemizes(listItemize, itemizeid, list);
		itemizeid="";
		if(list.size()!=0){
			for(ItemizeEntity itemize:list){
				if(itemizeid!=""){
					itemizeid+="','";
				}
				if(CommonUtil.NEWID){
					itemizeid+=itemize.getId();
				}else{
					itemizeid+=itemize.getItemizeid();
				}
			}
			String hql="select new SchoolMonthItemizeEntity (itemizeid,sum(data) as data) from SchoolMonthItemizeEntity where  1=1  and itemizeid in('"+itemizeid+"') ";
			listdata=queryHighchartData(hql, type, "schoolmonthitemize", "schooldayitemize", "schoolhouritemize", startDate, endDate,SchoolMonthItemizeEntity.class,null," group by itemizeid");
			lt = null;
			hc = new Highchart();
			hc.setName(startDate+"~"+endDate+"用能(千瓦时)");
			hc.setType("pie");
			for(SchoolMonthItemizeEntity school:listdata){
				if(lt==null){
					lt = new ArrayList<Map<String, Object>>();
				}
				map = new HashMap<String, Object>();
				for(ItemizeEntity itemize:list){
					if(itemize.getItemizeid().toString().equals(school.getItemizeid())){
						map.put("name", itemize.getItemizetext());
						break;
					}
				}
				map.put("y", CommonUtil.formateResult(school.getData()));
				lt.add(map);
			}
			if(lt!=null){
				hc.setData(lt);
				listhc.add(hc);
			}
		}
		return listhc;
	}
	private void findItemizes(List<ItemizeEntity> listItemize,String itemizeid,List<ItemizeEntity> list){
		if(CommonUtil.isNull(itemizeid)){
			for(ItemizeEntity itemize:listItemize){
				if(itemize.getItemize()!=null){
					itemizeid=itemize.getId();
					list.add(itemize);
					continue;
				}
			}
		}else{
			for(ItemizeEntity itemize:listItemize){
				if(itemize.getItemize()!=null&&itemize.getItemize().getId().equals(itemizeid)){
					list.add(itemize);
					findItemizes(listItemize, itemize.getId(), list);
				}
			}
		}
	}
}