package nhjkpt.configmanage.service.impl.buildingitemize;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.buildingitemize.BuildingItemizeEntity;
import nhjkpt.configmanage.entity.buildingmonthitemize.BuildingMonthItemizeEntity;
import nhjkpt.configmanage.entity.buildingmonthsum.BuildingMonthSumEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.entity.schoolmonthitemize.SchoolMonthItemizeEntity;
import nhjkpt.configmanage.service.buildingitemize.BuildingItemizeServiceI;
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

@Service("buildingItemizeService")
@Transactional
public class BuildingItemizeServiceImpl extends CommonServiceImpl implements BuildingItemizeServiceI {

	@Override
	public List<Highchart> queryHighchart(String buildingid,String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<BuildingMonthItemizeEntity> listdata=null;
		String hql=" from BuildingMonthItemizeEntity where  1=1  ";
		if(!CommonUtil.isNull(itemizeid)){
			hql+=" and itemizeid='"+itemizeid+"'";
		}
		if(!CommonUtil.isNull(buildingid)){
			hql+=" and buildingid='"+buildingid+"'";
		}
		listdata=queryHighchartData(hql, type, "buildingmonthitemize", "buildingdayitemize", "buildinghouritemize", startDate, endDate,BuildingMonthItemizeEntity.class,"itemizeid");
		hql=" from ItemizeEntity";
		List<ItemizeEntity> listitemize=this.findByQueryString(hql);
		//每一个分类显示一条线
		for(ItemizeEntity itemize:listitemize){
			lt = null;
			hc = new Highchart();
			hc.setName(itemize.getItemizetext());
			hc.setType("column");
			for(BuildingMonthItemizeEntity build:listdata){
				if(itemize.getId().equals(build.getItemizeid())){
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
	public List<Highchart> queryHighchartItemize(String buildingid,String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<BuildingMonthItemizeEntity> listdata=null;
		String hql=" from BuildingMonthItemizeEntity where  1=1  and buildingid='"+commonDao.get(BuildinginfoEntity.class,buildingid).getBuildingid()+"'";
		if(!CommonUtil.isNull(itemizeid)){
			if(itemizeid.indexOf(",")<0){
				hql+=" and itemizeid ='"+commonDao.get(ItemizeEntity.class, itemizeid).getItemizeid()+"'";
			}else{
				itemizeid=itemizeid.replaceAll(",", "','");
				hql+=" and itemizeid in(select itemizeid from ItemizeEntity where id in('"+itemizeid+"'))";
			}
		}
		listdata=queryHighchartData(hql, type, "buildingmonthitemize", "buildingdayitemize", "buildinghouritemize", startDate, endDate,BuildingMonthItemizeEntity.class,"itemizeid");
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
			lt = null;
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
	public List<Highchart> queryHighchartBuilding(String buildingid,String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<BuildingMonthItemizeEntity> listdata=null;
		String hql=" from BuildingMonthItemizeEntity where  1=1  and itemizeid='"+commonDao.get(ItemizeEntity.class, itemizeid).getItemizeid()+"'";
		if(!CommonUtil.isNull(buildingid)){
			if(buildingid.indexOf(",")<0){
				hql+=" and buildingid ='"+buildingid+"'";
			}else{
				buildingid=buildingid.replaceAll(",", "','");
				hql+=" and buildingid in('"+buildingid+"')";
			}
		}
		listdata=queryHighchartData(hql, type, "buildingmonthitemize", "buildingdayitemize", "buildinghouritemize", startDate, endDate,BuildingMonthItemizeEntity.class,"itemizeid");
		hql=" from BuildinginfoEntity where 1=1";
		if(!CommonUtil.isNull(buildingid)){
			if(buildingid.indexOf(",")<0){
				hql+=" and buildingid ='"+buildingid+"'";
			}else{
				hql+=" and buildingid in ('"+buildingid+"')";
			}
		}
		List<BuildinginfoEntity> listBuilding=this.findByQueryString(hql);
		//每一个分类显示一条线
		for(BuildinginfoEntity building:listBuilding){
			lt = null;
			hc = new Highchart();
			hc.setName(building.getBuildingname());
			hc.setType("column");
			for(BuildingMonthItemizeEntity build:listdata){
				if(building.getBuildingid().equals(build.getBuildingid())){
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
	public List<Highchart> queryHighchartDate(String buildingid,String itemizeid, String type,String[] startDate, String[] endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		if(CommonUtil.isNull(itemizeid)){
			List<BuildingItemizeEntity> list=getList(BuildingItemizeEntity.class);
			if(list!=null&&list.size()!=0){
				itemizeid=list.get(0).getItemizeid();
			}else{
				return null;
			}
		}else{
			if(!CommonUtil.NEWID){
				itemizeid=commonDao.get(ItemizeEntity.class, itemizeid).getItemizeid().toString();
			}
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<BuildingMonthItemizeEntity> listdata=null;
		String hql=" from BuildingMonthItemizeEntity where  1=1  and buildingid='"+buildingid+"'";
		if(!CommonUtil.isNull(itemizeid)){
			hql+=" and itemizeid='"+itemizeid+"'";
		}
		String endTime=null;
		String startTime=null;
		for(int i=0;i<startDate.length;i++){
			startTime=startDate[i];
			endTime=endDate[i];
			listdata=queryHighchartData(hql, type, "buildingmonthitemize", "buildingdayitemize", "buildinghouritemize", startTime, endTime,BuildingMonthItemizeEntity.class,"itemizeid");
			lt = null;
			hc = new Highchart();
			hc.setName(startTime+"-"+endTime);
			hc.setType("column");
			for(BuildingMonthItemizeEntity build:listdata){
				if(lt==null){
					lt = new ArrayList<Map<String, Object>>();
				}
				map = new HashMap<String, Object>();
				map.put("name", findName(type, build.getReceivetime()));
				map.put("y", CommonUtil.formateResult(build.getData()));
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
	public List<Highchart> queryHighchartpie(String buildingid,String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<BuildingMonthItemizeEntity> listdata=null;
		String hql=" from BuildingMonthItemizeEntity where  1=1  ";
		String itemizetext=null;
		if(CommonUtil.isNull(itemizeid)){
			List<ItemizeEntity> listitemize=this.getList(ItemizeEntity.class);
			if(listitemize!=null&&listitemize.size()!=0){
				itemizetext=listitemize.get(0).getItemizetext();
				itemizeid=listitemize.get(0).getId();
			}
		}else{
			itemizetext=this.get(ItemizeEntity.class, itemizeid).getItemizetext();
		}
		hql+=" and itemizeid='"+itemizeid+"'";
		if(!CommonUtil.isNull(buildingid)){
			hql+=" and buildingid='"+buildingid+"'";
		}
		listdata=queryHighchartData(hql, type, "buildingmonthitemize", "buildingdayitemize", "buildinghouritemize", startDate, endDate,BuildingMonthItemizeEntity.class,"itemizeid");
		lt = null;
		hc = new Highchart();
		hc.setName(itemizetext);
		hc.setType("pie");
		for(BuildingMonthItemizeEntity build:listdata){
			if(itemizeid.equals(build.getItemizeid())){
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
		}
		listhc.add(hc);
		return listhc;
	}
	@Override
	public List<Highchart> queryHighchartItemizePie(String buildingid,String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<BuildingMonthItemizeEntity> listdata=null;
		List<ItemizeEntity> listItemize=this.getList(ItemizeEntity.class);
		List<ItemizeEntity> list=new ArrayList<ItemizeEntity>();
		String itemid=null;
		if(!CommonUtil.isNull(itemizeid)){
			for(ItemizeEntity itemize:listItemize){
				if(CommonUtil.NEWID){
					itemid=itemize.getId();
				}else{
					itemid=itemize.getItemizeid().toString();
				}
				if(itemid.equals(itemizeid)){
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
			String hql="select new BuildingMonthItemizeEntity(itemizeid,sum(data) as data) from BuildingMonthItemizeEntity where  1=1  and itemizeid in ('"+itemizeid+"')";
			if(!CommonUtil.isNull(buildingid)){
				hql+=" and buildingid='"+commonDao.get(BuildinginfoEntity.class, buildingid).getBuildingid()+"'";
			}
			listdata=queryHighchartData(hql, type, "buildingmonthitemize", "buildingdayitemize", "buildinghouritemize", startDate, endDate,BuildingMonthItemizeEntity.class,null," group by itemizeid ");
			lt = null;
			hc = new Highchart();
			hc.setName(startDate+"-"+endDate);
			hc.setType("pie");
			for(BuildingMonthItemizeEntity build:listdata){
				if(lt==null){
					lt = new ArrayList<Map<String, Object>>();
				}
				map = new HashMap<String, Object>();
				for(ItemizeEntity itemize:list){
					if(CommonUtil.NEWID){
						itemid=itemize.getId();
					}else{
						itemid=itemize.getItemizeid().toString();
					}
					if(itemid.equals(build.getItemizeid())){
						map.put("name", itemize.getItemizetext());
						break;
					}
				}
				map.put("y", CommonUtil.formateResult(build.getData()));
				lt.add(map);
			}
			if(lt!=null){
				hc.setData(lt);
			}
			listhc.add(hc);
		}
		return listhc;
	}
	@Override
	public void exportExcel(String buildingid,String itemizeid, String type,String startDate, String endDate,OutputStream out) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		SimpleDateFormat sdf=null;
		List<BuildingMonthItemizeEntity> listdata=null;
		String hql=" from BuildingMonthItemizeEntity where  1=1  ";
		if(!CommonUtil.isNull(itemizeid)){
			hql+=" and itemizeid='"+itemizeid+"'";
		}
		if(!CommonUtil.isNull(buildingid)){
			hql+=" and buildingid='"+buildingid+"'";
		}
		listdata=queryHighchartData(hql, type, "buildingmonthitemize", "buildingdayitemize", "buildinghouritemize", startDate, endDate,BuildingMonthItemizeEntity.class,"itemizeid");
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("建筑物分类用能");
		Row row = sheet.createRow(0);  
		row.setHeight((short) 500);// 设定行的高度  
		Cell cell = row.createCell(0);  
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));  
		cell.setCellValue("建筑物分类用能"+startDate+"-"+endDate);
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
		for(BuildingMonthItemizeEntity itemize:listdata){
			index++;
			row = sheet.createRow(index);
			cell = row.createCell(0);
			cell.setCellValue(sdf.format(itemize.getReceivetime()));
			cell = row.createCell(1);
			cell.setCellValue(CommonUtil.formateResult(itemize.getData()));
		}
		try {
			wb.write(out);
		} catch (IOException e) {
		}
	}
	private void findItemizes(List<ItemizeEntity> listItemize,String itemizeid,List<ItemizeEntity> list){
		if(CommonUtil.isNull(itemizeid)){
			for(ItemizeEntity itemize:listItemize){
				if(itemize.getItemize()==null){
					itemizeid=itemize.getId();
					list.add(itemize);
					break;
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