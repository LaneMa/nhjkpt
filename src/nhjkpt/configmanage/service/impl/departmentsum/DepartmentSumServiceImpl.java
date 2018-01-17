package nhjkpt.configmanage.service.impl.departmentsum;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.departmentdaysum.DepartmentDaySumEntity;
import nhjkpt.configmanage.entity.departmentinfo.DepartmentinfoEntity;
import nhjkpt.configmanage.entity.departmentmonthsum.DepartmentMonthSumEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.service.departmentsum.DepartmentSumServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;

@Service("departmentSumService")
@Transactional
public class DepartmentSumServiceImpl extends CommonServiceImpl implements DepartmentSumServiceI {
	
	@Override
	public List<Highchart> queryHighchart(String departmentid,String funcid,String type,String startDate,String endDate) {
		if(CommonUtil.isNull(type)){
			type="-1";
		}
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		Map<String, Object> map=null;
		List<DepartmentMonthSumEntity> listdata=null;
		String hql=" from DepartmentMonthSumEntity where  1=1  ";
		if(!CommonUtil.isNull(funcid)){
			hql+=" and funcid='"+funcid+"'";
		}else{
			List<FuncEntity> listFunc=this.findByProperty(FuncEntity.class, "ischeck", "1");
			if(listFunc!=null&&listFunc.size()!=0){
				if(CommonUtil.NEWID){
					hql+=" and funcid='"+listFunc.get(0).getId()+"'";
				}else{
					hql+=" and funcid='"+listFunc.get(0).getFuncid()+"'";
				}
			}
		}
		if(!CommonUtil.isNull(departmentid)){
			hql+=" and departmentid='"+departmentid+"'";
		}
		listdata=queryHighchartData(hql, type, "departmentmonthsum", "departmentdaysum", "departmenthoursum", startDate, endDate,DepartmentMonthSumEntity.class,"funcid,departmentid");
		hql=" from DepartmentinfoEntity where 1=1";
		if(!CommonUtil.isNull(departmentid)){
			hql+=" and departmentid='"+departmentid+"'";
		}
		List<DepartmentinfoEntity> listdepartment=this.findByQueryString(hql);
		//每一个大楼显示一条线
		for(DepartmentinfoEntity department:listdepartment){
			lt = null;
			hc = new Highchart();
			hc.setName(department.getDepartmentname());
			hc.setType("column");
			for(DepartmentMonthSumEntity sum:listdata){
				if(department.getDepartmentid().equals(sum.getDepartmentid())){
					if(lt==null){
						lt = new ArrayList<Map<String, Object>>();
					}
					map = new HashMap<String, Object>();
					map.put("name", findName(type, sum.getReceivetime()));
					map.put("y", CommonUtil.formateResult(sum.getData()));
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
	public void exportExcel(String departmentid,String funcid,String type,String startDate,String endDate,OutputStream out){
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sdf=null;
		List<DepartmentMonthSumEntity> listdata=null;
		String hql=" from DepartmentMonthSumEntity where  1=1  ";
		if(!CommonUtil.isNull(funcid)){
			hql+=" and funcid='"+funcid+"'";
		}
		if(!CommonUtil.isNull(departmentid)){
			hql+=" and departmentid='"+departmentid+"'";
		}
		listdata=queryHighchartData(hql, type, "departmentmonthsum", "departmentdaysum", "departmenthoursum", startDate, endDate,DepartmentMonthSumEntity.class,null);
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("建筑物总用能");
		Row row = sheet.createRow(0);  
		row.setHeight((short) 500);// 设定行的高度  
		Cell cell = row.createCell(0);  
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));  
		cell.setCellValue("建筑物总能量"+startDate+"-"+endDate);
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
		for(DepartmentMonthSumEntity sum:listdata){
			index++;
			row = sheet.createRow(index);
			cell = row.createCell(0);
			cell.setCellValue(sdf.format(sum.getReceivetime()));
			cell = row.createCell(1);
			cell.setCellValue(CommonUtil.formateResult(sum.getData()));
		}
		try {
			wb.write(out);
		} catch (IOException e) {
		}
	}
	@Override
	public List<DepartmentDaySumEntity> queryDepartmentDaySumByDepartmentid(String departmentid){
		List<DepartmentDaySumEntity> listdata=null;
		Session session=null;
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
			SimpleDateFormat simpledateformat=new SimpleDateFormat("yyyy-MM-dd");
			String hql=" from DepartmentDaySumEntity where departmentid='"+departmentid+"' and date_format(receivetime,'%Y-%m-%d')=?";
			Date date=new Date();
			Interceptor interceptor=new MyInterceptor("departmentdaysum","departmentdaysum_"+sdf.format(date));
			session=getSession(interceptor);
			Query query=session.createQuery(hql);
			query.setDate(0, simpledateformat.parse(simpledateformat.format(date)));
			listdata=query.list();
		}catch(Exception e){
			
		}finally{
			if(session!=null){
				session.clear();
				session.close();
			}
		}
		return listdata;
	}
	@Override
	public long queryRanking(Double data,String funcid){
		long value=0;
		Session session=null;
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
			SimpleDateFormat simpledateformat=new SimpleDateFormat("yyyy-MM-dd");
			String hql="select count(id) from DepartmentDaySumEntity where data>"+data+" and date_format(receivetime,'%Y-%m-%d')=? and funcid=?";
			Date date=new Date();
			Interceptor interceptor=new MyInterceptor("departmentdaysum","departmentdaysum_"+sdf.format(date));
			session=getSession(interceptor);
			Query query=session.createQuery(hql);
			query.setDate(0, simpledateformat.parse(simpledateformat.format(date)));
			query.setString(1, funcid);
			value= (Long) query.uniqueResult()+1;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.clear();
				session.close();
			}
		}
		return value;
	}
	@Override
	public List<List<String>> queryReportData(String departmentid,String funcid,String startDate,String endDate,String reportType,String tableType){
		List<List<String>> listtr=new ArrayList<List<String>>();
		try{
			String hql=" from DepartmentMonthSumEntity where departmentid in('"+departmentid.replaceAll(",", "','")+"') and funcid='"+funcid+"'";
			List<DepartmentMonthSumEntity> listdata=queryHighchartData(hql, reportType, "departmentmonthsum", "departmentdaysum", "departmenthoursum", startDate, endDate,DepartmentMonthSumEntity.class,"departmentid");
			List<String> listtd=null;
			Set<String> sethead=null;
			List<String> listhead=null;
			SimpleDateFormat sdf=null;
			Map<String,Map<String,Object>> map=null;
			Map<String,Object> mapinner=null;
			Iterator<String> iter=null;
			if(listdata!=null&&listdata.size()!=0){
				map=new LinkedHashMap<String, Map<String,Object>>();
				sethead=new LinkedHashSet<String>();
				listhead=new ArrayList<String>();
				if(reportType.equals("year")){
					sdf=new SimpleDateFormat("yyyy");
				}else if(reportType.equals("month")){
					sdf=new SimpleDateFormat("yyyy-MM");
				}else if(reportType.equals("day")){
					sdf=new SimpleDateFormat("yyyy-MM-dd");
				}else{
					sdf=new SimpleDateFormat("yyyy-MM-dd HH");
				}
				if(tableType.equals("0")){
					for(DepartmentMonthSumEntity sum:listdata){
						mapinner=map.get(sum.getDepartmentid());
						if(mapinner==null){
							mapinner=new LinkedHashMap<String, Object>();
							map.put(sum.getDepartmentid(), mapinner);
						}
						mapinner.put(sdf.format(sum.getReceivetime()), CommonUtil.formateResult(sum.getData()));
						sethead.add(sdf.format(sum.getReceivetime()));
					}
						listhead.add("");
						iter=sethead.iterator();
						while(iter.hasNext()){
							listhead.add(iter.next());
						}
						listtr.add(listhead);
						sethead=map.keySet();
						iter=sethead.iterator();
						while(iter.hasNext()){
							listtd=new ArrayList<String>();
							departmentid=iter.next();
							listtd.add(commonDao.findByProperty(DepartmentinfoEntity.class, "departmentid", departmentid).get(0).getDepartmentname());
							mapinner=map.get(departmentid);
							for(String str:listhead){
								if(!CommonUtil.isNull(str)){
									if(mapinner.get(str)!=null){
										listtd.add(mapinner.get(str).toString());
									}else{
										listtd.add("");
									}
								}
							}
							listtr.add(listtd);
						}
				}else{
					for(DepartmentMonthSumEntity sum:listdata){
						mapinner=map.get(sdf.format(sum.getReceivetime()));
						if(mapinner==null){
							mapinner=new LinkedHashMap<String, Object>();
							map.put(sdf.format(sum.getReceivetime()), mapinner);
						}
						mapinner.put(sum.getDepartmentid(), CommonUtil.formateResult(sum.getData()));
						sethead.add(sum.getDepartmentid());
					}
						listhead.add("");
						iter=sethead.iterator();
						while(iter.hasNext()){
							listhead.add(this.get(DepartmentinfoEntity.class, iter.next()).getDepartmentname());
						}
						listtr.add(listhead);
						listhead=new ArrayList<String>();
						iter=sethead.iterator();
						while(iter.hasNext()){
							listhead.add(iter.next());
						}
						sethead=map.keySet();
						iter=sethead.iterator();
						while(iter.hasNext()){
							listtd=new ArrayList<String>();
							startDate=iter.next();
							listtd.add(startDate);
							mapinner=map.get(startDate);
							for(String str:listhead){
								if(mapinner.get(str)!=null){
									listtd.add(mapinner.get(str).toString());
								}else{
									listtd.add("");
								}
							}
							listtr.add(listtd);
						}
					}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return listtr;
	}
	@Override
	public void exportReport(String departmentid,String funcid,String startDate,String endDate,String reportType,String tableType,OutputStream out){
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("分户用能");
		Row row = sheet.createRow(0);  
		row.setHeight((short) 500);// 设定行的高度  
		List<List<String>> listdata=queryReportData(departmentid, funcid, startDate, endDate, reportType, tableType);
		if(listdata!=null&&listdata.size()!=0){
			Cell cell = row.createCell(0);  
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, listdata.get(0).size())); 
			cell.setCellValue("分户用能"+startDate+"-"+endDate);
			CellStyle cellStyle = wb.createCellStyle(); 
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cell.setCellStyle(cellStyle);
			int index=0;
			for(List<String> list:listdata){
				index++;
				row = sheet.createRow(index);
				for(int i=0,l=list.size();i<l;i++){
					cell = row.createCell(i);
					cell.setCellValue(list.get(i));
				}
			}
		}
		try {
			wb.write(out);
		} catch (IOException e) {
		}
	}
}