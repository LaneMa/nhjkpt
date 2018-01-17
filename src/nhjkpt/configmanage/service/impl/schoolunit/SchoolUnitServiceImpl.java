package nhjkpt.configmanage.service.impl.schoolunit;

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

import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.schoolmonthitemize.SchoolMonthItemizeEntity;
import nhjkpt.configmanage.entity.schoolmonthsum.SchoolMonthSumEntity;
import nhjkpt.configmanage.entity.schoolunit.SchoolUnitEntity;
import nhjkpt.configmanage.service.schoolmonthsum.SchoolMonthSumServiceI;
import nhjkpt.configmanage.service.schoolunit.SchoolUnitServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("schoolUnitService")
@Transactional
public class SchoolUnitServiceImpl extends CommonServiceImpl implements SchoolUnitServiceI {

	@Override
	public List<Highchart> queryHighchart(String funcid,String unitid,String type,String startDate,String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<FuncEntity> list=this.findHql(" from FuncEntity where ischeck=? and id=?", "1",funcid);
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map;
		String hql=null;
		//根据相应的类型查询相应的按月日时进行查询
		List<SchoolMonthSumEntity> listdata=null;
		hql=" from SchoolMonthSumEntity where  1=1 ";
		listdata=queryHighchartData(hql, type, "schoolmonthsum", "schooldaysum", "schoolhoursum", startDate, endDate,SchoolMonthSumEntity.class,null);
		Integer unitSum=1; 
		if(CommonUtil.isNull(unitid)){
			List<SchoolUnitEntity> listunit=this.getList(SchoolUnitEntity.class);
			if(listunit!=null&&listunit.size()!=0){
				unitSum=listunit.get(0).getUnitsum();
			}
		}else{
			unitSum=this.get(SchoolUnitEntity.class, unitid).getUnitsum();
		}
		//遍历所有配置信息。每个配置信息显示一条统计线
		for(FuncEntity func:list){
			lt = new ArrayList<Map<String, Object>>();
			hc = new Highchart();
			String funName=func.getFuncname();
			if(funName.indexOf("电")>=0){
				funName=funName+"(千瓦时)";
			}else if(funName.indexOf("水")>=0){
				funName=funName+"(吨)";
			}else{
				
			}
			
			hc.setName(funName);
			hc.setType("column");
			for(SchoolMonthSumEntity school:listdata){
				if(school.getFuncid().equals(func.getFuncid().toString())){
					map = new HashMap<String, Object>();
					map.put("name", findName(type, school.getReceivetime()));
					map.put("y", CommonUtil.formateResult(school.getData()));
					lt.add(map);
				}
			}
			hc.setData(lt);
			if(lt.size()!=0){
				listhc.add(hc);
			}
		}
		return listhc;
	}
	@Override
	public List<Highchart> queryHighchartItemize(String itemizeid,String type,String startDate,String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<SchoolUnitEntity> list=this.findHql(" from SchoolUnitEntity");
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map;
		String hql=null;
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sdf=null;
		//根据相应的类型查询相应的按月日时进行查询
		List<SchoolMonthItemizeEntity> listdata=null;
		hql=" from SchoolMonthItemizeEntity where  1=1  ";
		if(!CommonUtil.isNull(itemizeid)){
			hql+=" and itemizeid='"+commonDao.get(ItemizeEntity.class, itemizeid).getItemizeid()+"'";
		}
		listdata=queryHighchartData(hql, type, "schoolmonthitemize", "schooldayitemize", "schoolhouritemize", startDate, endDate,SchoolMonthItemizeEntity.class,null);
		//遍历所有配置信息。每个配置信息显示一条统计线
		for(SchoolUnitEntity unit:list){
			lt = new ArrayList<Map<String, Object>>();
			hc = new Highchart();
			hc.setName(unit.getUnittext()+"(千瓦时)");
			hc.setType("column");
			for(SchoolMonthItemizeEntity school:listdata){
				map = new HashMap<String, Object>();
				map.put("name", findName(type, school.getReceivetime()));
				map.put("y", CommonUtil.formateResult(school.getData()/unit.getUnitsum()));
				lt.add(map);
			}
			hc.setData(lt);
		}
		listhc.add(hc);
		return listhc;
	}
}