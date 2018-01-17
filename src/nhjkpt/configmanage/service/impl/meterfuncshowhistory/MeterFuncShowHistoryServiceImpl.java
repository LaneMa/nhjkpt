package nhjkpt.configmanage.service.impl.meterfuncshowhistory;

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

import nhjkpt.configmanage.entity.alarminfo.AlarminfoEntity;
import nhjkpt.configmanage.entity.buildinginfo.BuildinginfoEntity;
import nhjkpt.configmanage.entity.funcmanage.FuncEntity;
import nhjkpt.configmanage.entity.meterfuncshowhistory.MeterFuncShowHistoryEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.metershowfunc.MetershowfuncEntity;
import nhjkpt.configmanage.entity.metershowhistory.MeterShowHistoryEntity;
import nhjkpt.configmanage.entity.schoolinfo.SchoolinfoEntity;
import nhjkpt.configmanage.entity.schoolmonthsum.SchoolMonthSumEntity;
import nhjkpt.configmanage.entity.udprecord.UdpRecordEntity;
import nhjkpt.configmanage.service.alarminfo.AlarminfoServiceI;
import nhjkpt.configmanage.service.buildinginfo.BuildinginfoServiceI;
import nhjkpt.configmanage.service.funcmanage.FuncServiceI;
import nhjkpt.configmanage.service.meterfuncshowhistory.MeterFuncShowHistoryServiceI;
import nhjkpt.configmanage.service.metermanage.MeterServiceI;
import nhjkpt.configmanage.service.metershowhistory.MeterShowHistoryServiceI;
import nhjkpt.configmanage.service.schoolinfo.SchoolinfoServiceI;
import nhjkpt.configmanage.service.udprecord.UdpRecordServiceI;
import nhjkpt.system.controller.core.DataSourceController;
import nhjkpt.system.util.CommonUtil;

import org.apache.log4j.Logger;
import org.framework.core.common.model.json.Highchart;
import org.framework.core.common.service.impl.CommonServiceImpl;

@Service("meterFuncShowHistoryService")
@Transactional
public class MeterFuncShowHistoryServiceImpl extends CommonServiceImpl implements MeterFuncShowHistoryServiceI {
	
	@Autowired
	private BuildinginfoServiceI buildinginfoService;
	@Autowired
	private MeterServiceI meterService;
	@Autowired
	private FuncServiceI funcService;
	@Autowired
	private MeterShowHistoryServiceI meterShowHistoryService;
	@Autowired
	private AlarminfoServiceI alarminfoService;
	@Autowired
	private UdpRecordServiceI udpRecordService;
	@Autowired
	private SchoolinfoServiceI schoolinfoService;
	
	@Override
	public MeterShowHistoryEntity splitShowStr(String str)  {
		MeterShowHistoryEntity metershow=new MeterShowHistoryEntity();
		
		SchoolinfoEntity schoolInfo=null;
		List<SchoolinfoEntity> listSchool=schoolinfoService.getList(SchoolinfoEntity.class);
		if(listSchool!=null&&listSchool.size()!=0){
			schoolInfo=listSchool.get(0);
		}
			
			int add=0;
			Map<String, Object> mapmeter=null;
			Map<String,Object> mapfunc=null;
			MeterEntity meter=null;
			FuncEntity func=null;
			String meterid=null;
			String buildingid=null;
			List<MeterFuncShowHistoryEntity> listshow=null;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
			if(!CommonUtil.isNull(str)){
				//System.out.println("udp数据包========================："+str);
				
				
				MetershowfuncEntity msf=null;
				List<MetershowfuncEntity> msfList=null;
				AlarminfoEntity alarm=null;
				String[] code=str.split(";");
				
				if(CommonUtil.NEWID){
					buildingid=buildinginfoService.queryIdByBuildingid(code[0]);
					
				}else{
					buildingid=code[0];
					
				}
				metershow.setBuildingid(buildingid);
				metershow.setGatewayid(code[1]);
				metershow.setDatatype(code[2]);
				java.util.Date sendDate=CommonUtil.utcToDate(code[3]);
				Calendar cal=Calendar.getInstance();//获取当前日期
				//设置时间，后移8个小时
				cal.setTime(sendDate);
				if(schoolInfo!=null){
					cal.add(schoolInfo.getAlarmTimeType(), schoolInfo.getAlarmTime());
				}else{
					cal.add(11, 8);
				}
				sendDate=cal.getTime();
				metershow.setSenddate(sendDate);
				metershow.setSequence(code[4]);
				if(CommonUtil.NEWID){
					meterid=meterService.queryIdByMeterid(code[5]);
				}else{
					meterid=code[5];
				}
				if(!CommonUtil.isNull(meterid)&&!CommonUtil.isNull(buildingid)){
					metershow.setMeterid(meterid);
					List<MeterShowHistoryEntity> listMeterShow=this.findHql(" from MeterShowHistoryEntity where buildingid=? and gatewayid=? and datatype=? and date_format(senddate,'%Y-%m-%d %H')=? and sequence=? and meterid=?", metershow.getBuildingid(),metershow.getGatewayid(),metershow.getDatatype(),sdf.format(metershow.getSenddate()),metershow.getSequence(),metershow.getMeterid());
					if(listMeterShow!=null&&listMeterShow.size()!=0){
						metershow=listMeterShow.get(0);
						metershow.setSenddate(sendDate);
						String sql="DELETE FROM meterfuncshowhistory WHERE meterhistoryid='"+metershow.getId()+"'";
						meterShowHistoryService.executeSql(sql);
					}
					//记录表具传递过来数据的时间
					CommonUtil.meters.put(metershow.getMeterid(), metershow.getSenddate());
					if(CommonUtil.showmap.get(metershow.getBuildingid())!=null){
						mapmeter=(Map<String, Object>) CommonUtil.showmap.get(metershow.getBuildingid());
					}else{
						mapmeter=new HashMap<String, Object>();
						CommonUtil.showmap.put(metershow.getBuildingid(), mapmeter);
					}
					if(mapmeter.get(metershow.getMeterid())!=null){
						mapfunc=(Map<String, Object>) mapmeter.get(metershow.getMeterid());
					}else{
						mapfunc=new HashMap<String, Object>();
						mapmeter.put(metershow.getMeterid(), mapfunc);
					}
					if(CommonUtil.NEWID){
						meter=meterService.get(MeterEntity.class, metershow.getMeterid());
					}else{
						List<MeterEntity> listMeter=meterService.findByProperty(MeterEntity.class, "meterid", metershow.getMeterid());
						if(!CommonUtil.isNull(listMeter)){
							meter=listMeter.get(0);
						}
					}
					try{
						if(metershow!=null){
							if(CommonUtil.isNull(metershow.getId())){
								meterShowHistoryService.save(metershow);
							}else{
								meterShowHistoryService.updateEntitie(metershow);
							}
						}
						
					}catch(Exception e){
						
					}
					
					for(int j=0,len=Integer.parseInt(code[6]);j<len;j++){
						try{
							func=funcService.queryIdByFuncid(Integer.parseInt(code[7+j+add]));
						}catch(Exception e){
							System.out.println(code[6]+"查询功能函数异常=====："+code[7+j+add]);
						}
						if(func==null){
							continue;
						}
						
						
						MeterFuncShowHistoryEntity show=new MeterFuncShowHistoryEntity();
						add+=1;
						show.setShowdata(Double.parseDouble(code[7+j+add]));
						show.setMeterhistoryid(metershow.getId());
						show.setSenddate(metershow.getSenddate());
						if(CommonUtil.NEWID){
							mapfunc.put(func.getId(), show.getShowdata());
						}else{
							mapfunc.put(func.getFuncid().toString(), show.getShowdata());
						}
						if(func!=null && func.getIscheck()!=null && "2".equals(func.getIscheck())){
							if(CommonUtil.NEWID){
								show.setFuncid(func.getId());
							}else{
								show.setFuncid(func.getFuncid().toString());
							}
							listshow=this.findHql(" from MeterFuncShowHistoryEntity where meterhistoryid=? and funcid=?", show.getMeterhistoryid(),show.getFuncid());
							
							if(listshow!=null&&listshow.size()!=0){
								show=listshow.get(0);
								show.setShowdata(Double.parseDouble(code[7+j+add]));
							}
							
							
							if(show!=null && show.getShowdata()!=null){
								if(CommonUtil.isNull(show.getId())){
									this.save(show);
								}else{
									this.updateEntitie(show);
								}
							}
							
							
							
						}
						msfList=this.findByProperty(MetershowfuncEntity.class, "funcid", show.getFuncid());
						if(msfList!=null&&msfList.size()!=0){
							msf=msfList.get(0);
							//超出上下限
							if(show.getShowdata()>msf.getTop()||show.getShowdata()<msf.getFloor()){
								alarm=new AlarminfoEntity();
								alarm.setAlarmtime(new Date());
								alarm.setInfo("数据："+show.getShowdata()+",上限："+msf.getTop()+",下限："+msf.getFloor());
								alarm.setMeterid(metershow.getMeterid());
								alarm.setType("0");
								alarminfoService.save(alarm);
								meter.setAlarm("0");
								meterService.updateEntitie(meter);
							}else{
								meter.setAlarm("");
								meterService.updateEntitie(meter);
							}
						}
					}
					
					
					//打印mapmeter
//					if(metershow.getMeterid().equals("4011")||metershow.getMeterid().equals("4012")||metershow.getMeterid().equals("4013")){
//						System.out.println("meterid:"+metershow.getMeterid()+"====="+mapmeter);
//					}
				}else{
					System.out.println("此条udp信息在配置表里找不到对应的表具或者建筑物:"+str);
				}
			}
		
		return metershow;
	}
	@Override
	public List<Highchart> queryHighchart(String idmeter){
		String meterid=meterService.get(MeterEntity.class, idmeter).getMeterid();
		List<Highchart> listhc=new ArrayList<Highchart>();
		List<FuncEntity> listfunc=this.findByQueryString(" from FuncEntity where ischeck='2'");
		String funcids="";
		for(FuncEntity func:listfunc){
			if(!CommonUtil.isNull(funcids)){
				funcids+=",";
			}
			funcids+="'"+func.getFuncid()+"'";
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar calendar=Calendar.getInstance();
		String sql="SELECT c.funcid,c.showdata,c.sendDate FROM (" +
				"SELECT a.meterid,b.funcid,b.showdata,a.sendDate FROM metershowhistory a,meterfuncshowhistory b  WHERE a.id=b.meterhistoryid and b.funcid IN ("+funcids+") and a.meterid='"+meterid+"' GROUP BY DATE_FORMAT(a.sendDate,'%Y-%m-%d %H') ,funcid" +
//				" UNION ALL " +
//				" SELECT a.meterid, b.funcid,b.showdata,sendDate FROM metershowhistory a,meterfuncshowhistory b WHERE DATE_FORMAT(sendDate,'%i')>=30 GROUP BY DATE_FORMAT(a.sendDate,'%Y-%m-%d %H') ,funcid" +
				") c WHERE c.sendDate<='"+sdf.format(calendar.getTime())+"'" ;
//		calendar.add(Calendar.HOUR_OF_DAY, -48);
//		sql+=" and c.sendDate>'"+sdf.format(calendar.getTime())+"'";
		
		System.out.println("电流48小时曲线sql============:"+sql);
		List<Object[]> list=this.findListbySql(sql);
		List<Map<String, Object>> lt = null;
		Highchart hc = null;
		String funcid=null;
		Date date=null;
		Double data=null;
		Map<String,Object> map=null;
		sdf=new SimpleDateFormat("HH");
		for(FuncEntity func:listfunc){
			lt = null;
			hc = new Highchart();
			hc.setName(func.getFuncname());
			hc.setType("column");
			for(Object[] obj:list){
				funcid=(String) obj[0];
				data=(Double) obj[1];
				date=(Date) obj[2];
				if(funcid.equals(func.getFuncid().toString())){
					if(lt==null){
						lt = new ArrayList<Map<String, Object>>();
					}
					map = new HashMap<String, Object>();
					map.put("name", sdf.format(date));
					map.put("y", CommonUtil.formateResult(data));
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
}