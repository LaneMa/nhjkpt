package nhjkpt.configmanage.service.impl.departmentitemize;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.departmentinfo.DepartmentinfoEntity;
import nhjkpt.configmanage.entity.departmentitemize.DepartmentItemizeEntity;
import nhjkpt.configmanage.entity.departmentmonthitemize.DepartmentMonthItemizeEntity;
import nhjkpt.configmanage.entity.itemize.ItemizeEntity;
import nhjkpt.configmanage.service.departmentitemize.DepartmentItemizeServiceI;
import nhjkpt.system.util.CommonUtil;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("departmentItemizeService")
@Transactional
public class DepartmentItemizeServiceImpl extends CommonServiceImpl implements DepartmentItemizeServiceI {
	
	@Override
	public List<Highchart> queryHighchart(String departmentid,String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<DepartmentMonthItemizeEntity> listdata=null;
		String hql=" from DepartmentMonthItemizeEntity where  1=1  ";
		if(!CommonUtil.isNull(itemizeid)){
			hql+=" and itemizeid='"+itemizeid+"'";
		}
		if(!CommonUtil.isNull(departmentid)){
			hql+=" and departmentid='"+departmentid+"'";
		}
		listdata=queryHighchartData(hql, type, "departmentmonthitemize", "departmentdayitemize", "departmenthouritemize", startDate, endDate,DepartmentMonthItemizeEntity.class,"itemizeid");
		hql=" from ItemizeEntity";
		List<ItemizeEntity> listitemize=this.findByQueryString(hql);
		//每一个分类显示一条线
		for(ItemizeEntity itemize:listitemize){
			lt = null;
			hc = new Highchart();
			hc.setName(itemize.getItemizetext());
			hc.setType("column");
			for(DepartmentMonthItemizeEntity build:listdata){
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
	public List<Highchart> queryHighchartItemize(String departmentid,String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<DepartmentMonthItemizeEntity> listdata=null;
		String hql=" from DepartmentMonthItemizeEntity where  1=1  and departmentid='"+departmentid+"'";
		if(!CommonUtil.isNull(itemizeid)){
			if(itemizeid.indexOf(",")<0){
				hql+=" and itemizeid ='"+commonDao.get(ItemizeEntity.class, itemizeid).getItemizeid()+"'";
			}else{
				itemizeid=itemizeid.replaceAll(",", "','");
				hql+=" and itemizeid in (select itemizeid from ItemizeEntity where id in('"+itemizeid+"'))";
			}
		}
		listdata=queryHighchartData(hql, type, "departmentmonthitemize", "departmentdayitemize", "departmenthouritemize", startDate, endDate,DepartmentMonthItemizeEntity.class,"itemizeid");
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
			for(DepartmentMonthItemizeEntity build:listdata){
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
	public List<Highchart> queryHighchartDepartment(String departmentid,String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<DepartmentMonthItemizeEntity> listdata=null;
		String hql=" from DepartmentMonthItemizeEntity where  1=1  and itemizeid='"+itemizeid+"'";
		if(!CommonUtil.isNull(departmentid)){
			if(departmentid.indexOf(",")<0){
				hql+=" and departmentid ='"+departmentid+"'";
			}else{
				departmentid=departmentid.replaceAll(",", "','");
				hql+=" and departmentid in('"+departmentid+"')";
			}
		}
		listdata=queryHighchartData(hql, type, "departmentmonthitemize", "departmentdayitemize", "departmenthouritemize", startDate, endDate,DepartmentMonthItemizeEntity.class,"itemizeid");
		hql=" from DepartmentinfoEntity where 1=1";
		if(!CommonUtil.isNull(departmentid)){
			if(departmentid.indexOf(",")<0){
				hql+=" and id ='"+departmentid+"'";
			}else{
				hql+=" and id in('"+departmentid+"')";
			}
		}
		List<DepartmentinfoEntity> listitemize=this.findByQueryString(hql);
		//每一个分类显示一条线
		for(DepartmentinfoEntity department:listitemize){
			lt = null;
			hc = new Highchart();
			hc.setName(department.getDepartmentname());
			hc.setType("column");
			for(DepartmentMonthItemizeEntity build:listdata){
				if(department.getId().equals(build.getDepartmentid())){
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
	public List<Highchart> queryHighchartDate(String departmentid,String itemizeid, String type,String[] startDate, String[] endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		if(CommonUtil.isNull(itemizeid)){
			List<DepartmentItemizeEntity> list=getList(DepartmentItemizeEntity.class);
			if(list!=null&&list.size()!=0){
				itemizeid=list.get(0).getItemizeid();
			}else{
				return null;
			}
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<DepartmentMonthItemizeEntity> listdata=null;
		String hql=" from DepartmentMonthItemizeEntity where  1=1  and departmentid='"+departmentid+"'";
		if(!CommonUtil.isNull(itemizeid)){
			hql+=" and itemizeid='"+itemizeid+"'";
		}
		String endTime=null;
		String startTime=null;
		for(int i=0;i<startDate.length;i++){
			startTime=startDate[i];
			endTime=endDate[i];
			listdata=queryHighchartData(hql, type, "departmentmonthitemize", "departmentdayitemize", "departmenthouritemize", startTime, endTime,DepartmentMonthItemizeEntity.class,"itemizeid");
			lt = null;
			hc = new Highchart();
			hc.setName(startTime+"-"+endTime);
			hc.setType("column");
			for(DepartmentMonthItemizeEntity build:listdata){
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
	public List<Highchart> queryHighchartpie(String departmentid,String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<DepartmentMonthItemizeEntity> listdata=null;
		String hql=" from DepartmentMonthItemizeEntity where  1=1  ";
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
		if(!CommonUtil.isNull(departmentid)){
			hql+=" and departmentid='"+departmentid+"'";
		}
		listdata=queryHighchartData(hql, type, "departmentmonthitemize", "departmentdayitemize", "departmenthouritemize", startDate, endDate,DepartmentMonthItemizeEntity.class,"itemizeid");
		lt = null;
		hc = new Highchart();
		hc.setName(itemizetext);
		hc.setType("pie");
		for(DepartmentMonthItemizeEntity build:listdata){
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
	public List<Highchart> queryHighchartItemizePie(String departmentid,String itemizeid, String type,String startDate, String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<DepartmentMonthItemizeEntity> listdata=null;
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
			String hql="select new DepartmentMonthItemizeEntity(itemizeid,sum(data) as data) from DepartmentMonthItemizeEntity where  1=1  and itemizeid in ('"+itemizeid+"')";
			if(!CommonUtil.isNull(departmentid)){
				hql+=" and departmentid='"+departmentid+"'";
			}
			listdata=queryHighchartData(hql, type, "departmentmonthitemize", "departmentdayitemize", "departmenthouritemize", startDate, endDate,DepartmentMonthItemizeEntity.class,null," group by itemizeid ");
			lt = null;
			hc = new Highchart();
			hc.setName(startDate+"-"+endDate);
			hc.setType("pie");
			for(DepartmentMonthItemizeEntity build:listdata){
				if(lt==null){
					lt = new ArrayList<Map<String, Object>>();
				}
				map = new HashMap<String, Object>();
				for(ItemizeEntity itemize:list){
					if(itemize.getItemizeid().toString().equals(build.getItemizeid())){
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
	public void exportExcel(String departmentid,String itemizeid, String type,String startDate, String endDate,OutputStream out) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		SimpleDateFormat sdf=null;
		List<DepartmentMonthItemizeEntity> listdata=null;
		String hql=" from DepartmentMonthItemizeEntity where  1=1  ";
		if(!CommonUtil.isNull(itemizeid)){
			hql+=" and itemizeid='"+itemizeid+"'";
		}
		if(!CommonUtil.isNull(departmentid)){
			hql+=" and departmentid='"+departmentid+"'";
		}
		listdata=queryHighchartData(hql, type, "departmentmonthitemize", "departmentdayitemize", "departmenthouritemize", startDate, endDate,DepartmentMonthItemizeEntity.class,"itemizeid");
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
		for(DepartmentMonthItemizeEntity itemize:listdata){
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