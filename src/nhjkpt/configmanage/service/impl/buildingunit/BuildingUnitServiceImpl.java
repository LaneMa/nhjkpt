package nhjkpt.configmanage.service.impl.buildingunit;

import java.io.IOException;
import java.io.OutputStream;
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
import nhjkpt.configmanage.entity.buildingmonthitemize.BuildingMonthItemizeEntity;
import nhjkpt.configmanage.entity.buildingmonthsum.BuildingMonthSumEntity;
import nhjkpt.configmanage.entity.buildingunit.BuildingUnitEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.schoolunit.SchoolUnitEntity;
import nhjkpt.configmanage.service.buildingunit.BuildingUnitServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;

@Service("buildingUnitService")
@Transactional
public class BuildingUnitServiceImpl extends CommonServiceImpl implements BuildingUnitServiceI {
	
	@Override
	public List<Highchart> queryHighchart(String unitid,String buildingid,String type,String startDate,String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<FuncEntity> list=this.findByProperty(FuncEntity.class, "ischeck", "1");
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map;
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sdf=null;
		//根据相应的类型查询相应的按月日时进行查询
		List<BuildingMonthSumEntity> listdata=null;
		String hql=" from BuildingMonthSumEntity where  1=1 ";
		if(!CommonUtil.isNull(buildingid)){
			hql+=" and buildingid='"+commonDao.get(BuildinginfoEntity.class, buildingid).getBuildingid()+"'";
		}
		listdata=queryHighchartData(hql, type, "buildingmonthsum", "buildingdaysum", "buildinghoursum", startDate, endDate,BuildingMonthSumEntity.class,"funcid,buildingid");
		Integer unitSum=1; 
		if(CommonUtil.isNull(unitid)){
			List<BuildingUnitEntity> listunit=this.getList(BuildingUnitEntity.class);
			if(listunit!=null&&listunit.size()!=0){
				unitSum=listunit.get(0).getUnitsum();
			}
		}else{
			BuildingUnitEntity buildingUnitEntity=this.get(BuildingUnitEntity.class, unitid);
			if(buildingUnitEntity!=null){
				unitSum=this.get(BuildingUnitEntity.class, unitid).getUnitsum();
			}
		}
		//遍历所有配置信息。每个配置信息显示一条统计线
		for(FuncEntity func:list){
			lt = new ArrayList<Map<String, Object>>();
			hc = new Highchart();
			hc.setName(func.getFuncname());
			hc.setType("column");
			for(BuildingMonthSumEntity build:listdata){
				if(build.getFuncid().equals(func.getFuncid().toString())){
					map = new HashMap<String, Object>();
					map.put("name", findName(type, build.getReceivetime()));
					map.put("y", CommonUtil.formateResult(build.getData()/unitSum));
					lt.add(map);
				}
			}
			if(lt.size()!=0){
				hc.setData(lt);
				listhc.add(hc);
			}
		}
		return listhc;
	}
	@Override
	public List<Highchart> queryHighchartItemize(String unitid,String buildingid,String itemizeid,String type,String startDate,String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map;
		//根据相应的类型查询相应的按月日时进行查询
		List<BuildingMonthItemizeEntity> listdata=null;
		String hql=" from BuildingMonthItemizeEntity where  1=1  ";
		if(!CommonUtil.isNull(buildingid)){
			hql+=" and buildingid='"+commonDao.get(BuildinginfoEntity.class, buildingid).getBuildingid()+"'";
		}
		
		
		if(!CommonUtil.isNull(itemizeid)){
			if(itemizeid.indexOf(",")<0){
				hql+=" and itemizeid ='"+commonDao.get(ItemizeEntity.class, itemizeid).getItemizeid()+"'";
			}else{
				itemizeid=itemizeid.replaceAll(",", "','");
				hql+=" and itemizeid in (select itemizeid from ItemizeEntity where id in ('"+itemizeid+"'))";
			}
		}
		
		
		listdata=queryHighchartData(hql, type, "buildingmonthitemize", "buildingdayitemize", "buildinghouritemize", startDate, endDate,BuildingMonthItemizeEntity.class,"itemizeid");
		Integer unitSum=1; 
		if(CommonUtil.isNull(unitid)){
			List<BuildingUnitEntity> listunit=this.getList(BuildingUnitEntity.class);
			if(listunit!=null&&listunit.size()!=0){
				unitSum=listunit.get(0).getUnitsum();
			}
		}else{
			BuildingUnitEntity buildingUnitEntity=this.get(BuildingUnitEntity.class, unitid);
			if(buildingUnitEntity!=null){
				unitSum=this.get(BuildingUnitEntity.class, unitid).getUnitsum();
			}
		}
		
		
		hql=" from ItemizeEntity where 1=1";
		if(!CommonUtil.isNull(itemizeid)){
			if(itemizeid.indexOf(",")<0){
				hql+=" and id ='"+itemizeid+"'";
			}else{
				hql+=" and fatherid is not null and id in('"+itemizeid+"')";
			}
		}
		List<ItemizeEntity> listitemize=this.findByQueryString(hql);
		//每一个分类显示一条线
		for(ItemizeEntity itemize:listitemize){
		
			lt = new ArrayList<Map<String, Object>>();
			hc = new Highchart();
			hc.setName(itemize.getItemizetext());
			hc.setType("column");
			for(BuildingMonthItemizeEntity build:listdata){
				
				if(itemize.getItemizeid().toString().equals(build.getItemizeid())){
					if(lt==null){
						lt = new ArrayList<Map<String, Object>>();
					}
					map = new HashMap<String, Object>();
					map.put("name", findName(type, build.getReceivetime()));
					map.put("y", CommonUtil.formateResult(build.getData()));
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
	public void exportExcel(String buildingid,String type,String startDate,String endDate,OutputStream out) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<BuildingUnitEntity> list=this.findHql(" from BuildingUnitEntity");
		SimpleDateFormat sdf=null;
		//根据相应的类型查询相应的按月日时进行查询
		List<Object[]> listdata=null;
		String hql="select receivetime,sum(data) from BuildingMonthSumEntity where  1=1 ";
		if(!CommonUtil.isNull(buildingid)){
			hql+=" and buildingid='"+buildingid+"'";
		}else{
			if(list!=null&&list.size()!=0){
				hql+=" and buildingid='"+list.get(0).getBuildingid()+"'";
			}
		}
		hql+=" group by receivetime";
		listdata=queryHighchartData(hql, type, "buildingmonthsum", "buildingdaysum", "buildinghoursum", startDate, endDate,BuildingMonthSumEntity.class,null);
		Date receivetime=null;
		Double data=0.0;
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("建筑物平均用能");
		Row row = sheet.createRow(0);  
		row.setHeight((short) 500);// 设定行的高度  
		Cell cell = row.createCell(0);  
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));  
		cell.setCellValue("建筑物平均用能"+startDate+"-"+endDate);
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("时间");
		cell = row.createCell(1);
		cell.setCellValue("指标值");
		int index=1;
		if(type.equals("year")){
			sdf=new SimpleDateFormat("yyyy");
		}else if(type.equals("month")){
			sdf=new SimpleDateFormat("yyyy-MM");
		}else if(type.equals("day")){
			sdf=new SimpleDateFormat("yyyy-MM-dd");
		}else{
			sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		}
		for(Object[] build:listdata){
			receivetime=(Date) build[0];
			data=(Double) build[1];
			index++;
			row = sheet.createRow(index);
			cell = row.createCell(0);
			cell.setCellValue(sdf.format(receivetime));
			cell = row.createCell(1);
			cell.setCellValue(CommonUtil.formateResult(data));
		}
		try {
			wb.write(out);
		} catch (IOException e) {
		}
	}
}