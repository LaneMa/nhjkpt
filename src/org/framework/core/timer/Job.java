package org.framework.core.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nhjkpt.configmanage.entity.alarminfo.AlarminfoEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.metershowhistory.MeterShowHistoryEntity;
import nhjkpt.configmanage.service.alarminfo.AlarminfoServiceI;
import nhjkpt.configmanage.service.buildingdayitemize.BuildingDayItemizeServiceI;
import nhjkpt.configmanage.service.buildingdaysum.BuildingDaySumServiceI;
import nhjkpt.configmanage.service.buildinghouritemize.BuildingHourItemizeServiceI;
import nhjkpt.configmanage.service.buildinghoursum.BuildingHourSumServiceI;
import nhjkpt.configmanage.service.buildingmonthitemize.BuildingMonthItemizeServiceI;
import nhjkpt.configmanage.service.buildingmonthsum.BuildingMonthSumServiceI;
import nhjkpt.configmanage.service.departmentdayitemize.DepartmentDayItemizeServiceI;
import nhjkpt.configmanage.service.departmentdaysum.DepartmentDaySumServiceI;
import nhjkpt.configmanage.service.departmenthouritemize.DepartmentHourItemizeServiceI;
import nhjkpt.configmanage.service.departmenthoursum.DepartmentHourSumServiceI;
import nhjkpt.configmanage.service.departmentmonthitemize.DepartmentMonthItemizeServiceI;
import nhjkpt.configmanage.service.departmentmonthsum.DepartmentMonthSumServiceI;
import nhjkpt.configmanage.service.hourdata.HourdataServiceI;
import nhjkpt.configmanage.service.metermanage.MeterServiceI;
import nhjkpt.configmanage.service.metershowhistory.MeterShowHistoryServiceI;
import nhjkpt.configmanage.service.pointdata.PointdataServiceI;
import nhjkpt.configmanage.service.schooldayitemize.SchoolDayItemizeServiceI;
import nhjkpt.configmanage.service.schooldaysum.SchooldaysumServiceI;
import nhjkpt.configmanage.service.schoolhouritemize.SchoolHourItemizeServiceI;
import nhjkpt.configmanage.service.schoolhoursum.SchoolhoursumServiceI;
import nhjkpt.configmanage.service.schoolmonthitemize.SchoolMonthItemizeServiceI;
import nhjkpt.configmanage.service.schoolmonthsum.SchoolMonthSumServiceI;
import nhjkpt.system.service.SystemService;
import nhjkpt.system.util.CommonUtil;

import org.framework.core.util.LicenseSign;
import org.framework.core.util.ResourceUtil;
import org.framework.core.util.SignVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class Job {
	@Autowired
	private PointdataServiceI pointdataService;
	@Autowired
	private HourdataServiceI hourdataService;
	@Autowired
	private SchoolhoursumServiceI schoolhoursumService;
	@Autowired
	private SchooldaysumServiceI schooldaysumService;
	@Autowired
	private SchoolMonthSumServiceI schoolMonthSumService;
	@Autowired
	private SchoolHourItemizeServiceI schoolHourItemizeService;
	@Autowired
	private SchoolDayItemizeServiceI schoolDayItemizeService;
	@Autowired
	private SchoolMonthItemizeServiceI schoolMonthItemizeService;
	@Autowired
	private BuildingHourSumServiceI buildingHourSumService;
	@Autowired
	private BuildingDaySumServiceI buildingDaySumService;
	@Autowired
	private BuildingMonthSumServiceI buildingMonthSumService;
	@Autowired
	private BuildingHourItemizeServiceI buildingHourItemizeService;
	@Autowired
	private BuildingDayItemizeServiceI buildingDayItemizeService;
	@Autowired
	private BuildingMonthItemizeServiceI buildingMonthItemizeService;
	@Autowired
	private DepartmentHourSumServiceI departmentHourSumService;
	@Autowired
	private DepartmentHourItemizeServiceI departmentHourItemizeService;
	@Autowired
	private DepartmentDaySumServiceI departmentDaySumService;
	@Autowired
	private DepartmentDayItemizeServiceI departmentDayItemizeService;
	@Autowired
	private DepartmentMonthSumServiceI departmentMonthSumService;
	@Autowired
	private DepartmentMonthItemizeServiceI departmentMonthItemizeService;
	@Autowired
	private AlarminfoServiceI alarminfoService;
	@Autowired
	private MeterServiceI meterService;
	@Autowired
	private MeterShowHistoryServiceI meterShowHistoryService;

 
//    @Scheduled(cron="*/10 * * * * *") 
//    public void s10(){
//        System.out.println("==== 十秒执行一次=======10s");
//    }
//    
//    @Scheduled(cron="0 */1 * * * *") 
//    public void m1(){
//        System.out.println("1m");
//    }
    
    /**
     * 每天1点执行一次
     * */
    @Scheduled(cron="0 0 1 * * ?") 
    public void oneOClockPerDay(){
    	try{
			String path=Thread.currentThread().getContextClassLoader().getResource("").getPath();
			boolean b=SignVerify.verifyCertificate(path+"/sign/nhjkpt.cer");
			if(true){
				LicenseSign ls=(LicenseSign) SignVerify.readFile(path+"/sign/license.nhjkpt");
		    	byte[] data= SignVerify.decryptBASE64(ls.getLicense());
				b=SignVerify.verify(data, ls.getSign(), path+"/sign/nhjkpt.cer");
				CommonUtil.server.put(CommonUtil.VERIFY_SIGN, b);
				b=SignVerify.checkServer(path+"/sign/license.nhjkpt", path+"/sign/nhjkpt.cer");
				CommonUtil.server.put(CommonUtil.SIGN_SERVER, b);
			}else{
				System.err.println("\u6ce8\u518c\u7801\u8fc7\u671f");
				System.exit(0);
			}
		}catch(Exception e){
			System.exit(0);
		}
    }
    /**
     * 每小时第0分钟执行一次检查是否断线
     */
    @Scheduled(cron="0 20 * * * ?")
    public void breakLineCheck(){
    	String timerSwitch=ResourceUtil.getConfigByName("timerSwitch");
    	if(timerSwitch!=null && timerSwitch.equals("on")){
	        System.out.println("每小时第20分钟执行开始检查是否断线");
	//        Map<String, Date> map=CommonUtil.meters;
	//        List<MeterEntity> list=meterService.getList(MeterEntity.class);
	//        AlarminfoEntity alarm=null;
	//        Date date=null;
	//        Date now=new Date();
	//        for(MeterEntity meter:list){
	//        	try{
	//        		date=map.get(meter.getId());
	//            	if(date==null||date.getTime()-now.getTime()>15*60*1000){
	//            		alarm=new AlarminfoEntity();
	//            		alarm.setMeterid(meter.getId());
	//            		alarm.setInfo(meter.getMetertext()+"断线提醒");
	//            		alarm.setAlarmtime(now);
	//            		alarm.setType("1");
	//            		alarminfoService.saveOrUpdate(alarm);
	//            		meter.setAlarm("1");
	//            		System.err.println(meter.getMetertext()+"断线提醒");
	//            	}else{
	//            		meter.setAlarm("");
	//            	}
	//        		meterService.saveOrUpdate(meter);
	//        	}catch(Exception e){
	//        		
	//        	}
	//        }
	        try{
	        	pointdataService.breakLineCheck();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        
    	}
    }
    /**
     * 每个小时第15分钟执行一次整点数据运算
     */
    @Scheduled(cron="0 35 * * * ?") 
    public void oneHourOnce(){
    	String timerSwitch=ResourceUtil.getConfigByName("timerSwitch");
    	if(timerSwitch!=null && timerSwitch.equals("on")){
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
	    	Calendar calendar=Calendar.getInstance();//获取当前日期
	    	System.out.println("每个小时第35分钟执行一次整点数据获取==================："+sdf.format(calendar.getTime()));
	    	//计算上一个小时的数据
	        calendar.add(Calendar.HOUR_OF_DAY, -1);
	        //System.out.println("上一小时时间=================="+calendar.getTime().toString());
	        System.out.println("开始读取配置表数据");
	        pointdataService.exportIntegralPointData(calendar);
	        System.out.println("开始计算每小时用电量");
	        hourdataService.exportPointDataHour(calendar);
	        System.out.println("开始计算学校用能分时统计");
	        schoolhoursumService.exportHourdata(calendar);
	        System.out.println("开始计算大楼用能总量的分天统计");
	        buildingHourSumService.exportHourData(calendar);
	        System.out.println("开始计算系用能总量的分天统计");
	        departmentHourSumService.exportHourData(calendar);
	        System.out.println("开始计算学校用能分类分时统计");
	        schoolHourItemizeService.exportHourdata(calendar);
	        System.out.println("开始计算建筑物分类分时统计");
	        buildingHourItemizeService.exportHourdata(calendar);
	        System.out.println("开始计算系分类分时统计");
	        departmentHourItemizeService.exportHourdata(calendar);
	        //计算完上一个小时的数据之后恢复时间
	        calendar.add(Calendar.HOUR_OF_DAY, 1);
	        
	        System.out.println("开始读取配置表数据");
	        pointdataService.exportIntegralPointData(calendar);
	        System.out.println("开始计算每小时用电量");
	        hourdataService.exportPointDataHour(calendar);
	        System.out.println("开始计算学校用能分时统计");
	        schoolhoursumService.exportHourdata(calendar);
	        System.out.println("开始计算大楼用能总量的分时统计");
	        buildingHourSumService.exportHourData(calendar);
	        System.out.println("开始计算系用能总量的分天统计");
	        departmentHourSumService.exportHourData(calendar);
	        System.out.println("开始计算学校用能分类分时统计");
	        schoolHourItemizeService.exportHourdata(calendar);
	        System.out.println("开始计算建筑物分类分时统计");
	        buildingHourItemizeService.exportHourdata(calendar);
	        System.out.println("开始计算系分类分时统计");
	        departmentHourItemizeService.exportHourdata(calendar);
	        
	        //System.out.println("原有时间=================="+calendar.getTime().toString());
	        System.out.println("开始计算学校用能分天统计");
	    	schooldaysumService.exportSchoolhoursum(calendar);
	    	System.out.println("开始计算大楼用能总量的分天统计");
	    	buildingDaySumService.exportBuildingHourSum(calendar);
	    	System.out.println("开始计算系用能总量的分天统计");
	    	departmentDaySumService.exportDepartmentHourSum(calendar);
	    	System.out.println("开始计算学校用能分类分天统计");
	    	schoolDayItemizeService.exportSchoolHourItemize(calendar);
	    	System.out.println("开始计算建筑物分类分天统计");
	    	buildingDayItemizeService.exportBuildingHourItemize(calendar);
	    	System.out.println("开始计算系分类分天统计");
	    	departmentDayItemizeService.exportDepartmentHourItemize(calendar);
	    	
	    	System.out.println("开始计算学校用能分月统计");
	    	schoolMonthSumService.exportSchooldaySum(calendar);
	    	System.out.println("开始计算大楼用能总量的分月统计");
	    	buildingMonthSumService.exportBuildingDaySum(calendar);
	    	System.out.println("开始计算系用能总量的分月统计");
	    	departmentMonthSumService.exportDepartmentDaySum(calendar);
	    	System.out.println("开始计算学校用能分类分月统计");
	    	schoolMonthItemizeService.exportSchooldayItemize(calendar);
	    	System.out.println("开始计算建筑物分类分月统计");
	    	buildingMonthItemizeService.exportBuildingDayItemize(calendar);
	    	System.out.println("开始计算系分类分月统计");
	    	departmentMonthItemizeService.exportDepartmentDayItemize(calendar);
    	}
    }
    
    /**
     * 每1分钟检查一次是否有数据需要导入
     */
    @Scheduled(cron="0/10 * * * * ?") 
    public void oneHourOnceImport(){
//    	String timerSwitch=ResourceUtil.getConfigByName("timerSwitch");
//    	if(timerSwitch!=null && timerSwitch.equals("on")){
//    		System.out.println("=====每1分钟检查一次是否有数据需要导入======");
//    	}
    	if(!CommonUtil.isNull(CommonUtil.START_DATE)&&!CommonUtil.isNull(CommonUtil.END_DATE)&&!CommonUtil.IMPORT){
    		CommonUtil.IMPORT=true;
    		Calendar calendar=Calendar.getInstance();//获取当前日期
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
        	
    		try{
    			Date startD=sdf.parse(CommonUtil.START_DATE);
    			Date endD=sdf.parse(CommonUtil.END_DATE);
    			calendar.setTime(startD);
    			while(calendar.getTime().getTime()<=endD.getTime()){
    				
    				System.out.println("参与计算的具体时间========："+ sdf.format(calendar.getTime()));
    				System.out.println("开始读取配置表数据");
    		        pointdataService.exportIntegralPointData(calendar);
    		        System.out.println("开始计算每小时用电量");
    		        hourdataService.exportPointDataHour(calendar);
    		        System.out.println("开始计算学校用能分时统计");
    		        schoolhoursumService.exportHourdata(calendar);
    		        System.out.println("开始计算大楼用能总量的分时统计");
    		        buildingHourSumService.exportHourData(calendar);
    		        System.out.println("开始计算系用能总量的分天统计");
    		        departmentHourSumService.exportHourData(calendar);
    		        System.out.println("开始计算学校用能分类分时统计");
    		        schoolHourItemizeService.exportHourdata(calendar);
    		        System.out.println("开始计算建筑物分类分时统计");
    		        buildingHourItemizeService.exportHourdata(calendar);
    		        System.out.println("开始计算系分类分时统计");
    		        departmentHourItemizeService.exportHourdata(calendar);
    				if(calendar.getTime().getHours()==23){
    					System.out.println("开始计算学校用能分天统计");
    			    	schooldaysumService.exportSchoolhoursum(calendar);
    			    	System.out.println("开始计算大楼用能总量的分天统计");
    			    	buildingDaySumService.exportBuildingHourSum(calendar);
    			    	System.out.println("开始计算系用能总量的分天统计");
    			    	departmentDaySumService.exportDepartmentHourSum(calendar);
    			    	System.out.println("开始计算学校用能分类分天统计");
    			    	schoolDayItemizeService.exportSchoolHourItemize(calendar);
    			    	System.out.println("开始计算建筑物分类分天统计");
    			    	buildingDayItemizeService.exportBuildingHourItemize(calendar);
    			    	System.out.println("开始计算系分类分天统计");
    			    	departmentDayItemizeService.exportDepartmentHourItemize(calendar);
    			    	//判断月末最后一天的23小时
    			    	if(calendar.getTime().getDate()==calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
			    			System.out.println("开始计算学校用能分月统计");
    			        	schoolMonthSumService.exportSchooldaySum(calendar);
    			        	System.out.println("开始计算大楼用能总量的分月统计");
    			        	buildingMonthSumService.exportBuildingDaySum(calendar);
    			        	System.out.println("开始计算系用能总量的分月统计");
    				    	departmentMonthSumService.exportDepartmentDaySum(calendar);
    			        	System.out.println("开始计算学校用能分类分月统计");
    			        	schoolMonthItemizeService.exportSchooldayItemize(calendar);
    			        	System.out.println("开始计算建筑物分类分月统计");
    			        	buildingMonthItemizeService.exportBuildingDayItemize(calendar);
    			        	System.out.println("开始计算系分类分月统计");
    				    	departmentMonthItemizeService.exportDepartmentDayItemize(calendar);
    			    	}
    				}
    				calendar.add(Calendar.HOUR_OF_DAY, 1);
    			}
    			System.out.println("开始计算学校用能分天统计==============");
		    	schooldaysumService.exportSchoolhoursum(calendar);
		    	System.out.println("开始计算大楼用能总量的分天统计");
		    	buildingDaySumService.exportBuildingHourSum(calendar);
		    	System.out.println("开始计算系用能总量的分天统计");
		    	departmentDaySumService.exportDepartmentHourSum(calendar);
		    	System.out.println("开始计算学校用能分类分天统计");
		    	schoolDayItemizeService.exportSchoolHourItemize(calendar);
		    	System.out.println("开始计算建筑物分类分天统计");
		    	buildingDayItemizeService.exportBuildingHourItemize(calendar);
		    	System.out.println("开始计算系分类分天统计");
		    	departmentDayItemizeService.exportDepartmentHourItemize(calendar);
		    	System.out.println("开始计算学校用能分月统计");
	        	schoolMonthSumService.exportSchooldaySum(calendar);
	        	System.out.println("开始计算大楼用能总量的分月统计");
	        	buildingMonthSumService.exportBuildingDaySum(calendar);
	        	System.out.println("开始计算系用能总量的分月统计");
		    	departmentMonthSumService.exportDepartmentDaySum(calendar);
	        	System.out.println("开始计算学校用能分类分月统计");
	        	schoolMonthItemizeService.exportSchooldayItemize(calendar);
	        	System.out.println("开始计算建筑物分类分月统计");
	        	buildingMonthItemizeService.exportBuildingDayItemize(calendar);
	        	System.out.println("开始计算系分类分月统计");
		    	departmentMonthItemizeService.exportDepartmentDayItemize(calendar);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		CommonUtil.START_DATE=null;
    		CommonUtil.END_DATE=null;
    		CommonUtil.IMPORT=false;
        }
    }
    
    /**
     * 每天6:00 删除两个月前的历史记录
     */
    @Scheduled(cron="0 0 0/1 * * ?")
    public void delHistory(){
    	String timerSwitch=ResourceUtil.getConfigByName("timerSwitch");
    	if(timerSwitch!=null && timerSwitch.equals("on")){
	    	System.out.println("删除两个月前的历史记录");
	    	Calendar calendar=Calendar.getInstance();//获取当前日期
	    	calendar.add(Calendar.HOUR_OF_DAY, -50);
	    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String sql="DELETE FROM metershowhistory WHERE sendDate<='"+sdf.format(calendar.getTime())+"'";
	    	meterShowHistoryService.executeSql(sql);
	    	sql="DELETE FROM meterfuncshowhistory WHERE meterhistoryid NOT IN (SELECT id FROM metershowhistory)";
	    	meterShowHistoryService.executeSql(sql);
    	}
    }
}