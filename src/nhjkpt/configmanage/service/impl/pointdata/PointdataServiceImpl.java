package nhjkpt.configmanage.service.impl.pointdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.alarminfo.AlarminfoEntity;
import nhjkpt.configmanage.entity.hourdata.HourdataEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.entity.originadata.OriginadataEntity;
import nhjkpt.configmanage.entity.pointdata.PointdataEntity;
import nhjkpt.configmanage.service.alarminfo.AlarminfoServiceI;
import nhjkpt.configmanage.service.funcmanage.FuncServiceI;
import nhjkpt.configmanage.service.metermanage.MeterServiceI;
import nhjkpt.configmanage.service.originadata.OriginadataServiceI;
import nhjkpt.configmanage.service.pointdata.PointdataServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.HibernateConfiguration;
import nhjkpt.system.util.MyInterceptor;
import nhjkpt.system.util.TempSessionFactory;
import nhjkpt.system.util.UUIDGenerator;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("pointdataService")
@Transactional
public class PointdataServiceImpl extends CommonServiceImpl implements PointdataServiceI {

	@Autowired
	private OriginadataServiceI originadataService;
	@Autowired
	private FuncServiceI funcService;
	@Autowired
	private MeterServiceI meterService;
	@Autowired
	private AlarminfoServiceI alarminfoService;
	/**
	 * sql方式导入，暂时未用
	 */
	@Override
	public void addIntegralPointData(Calendar calendar) {
		//获取所有数据库连接配置
		List<OriginadataEntity> listOrigin=originadataService.findHql(" from OriginadataEntity ");
		//获取所有的表具
		List<MeterEntity> listMeter=meterService.findHql(" from MeterEntity ");
		Session sessionPoint=null;
		HibernateConfiguration dyConfiguration=null;
		String sqllast=null;
		String sqlnext=null;
		List<PointdataEntity> listlast=null;
		List<PointdataEntity> listnext=null;
		SQLQuery query=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		PointdataEntity pointdatalast=null;
		PointdataEntity pointdatanext=null;
		int minutelast=0;
		int minutenext=0;
		Double doublelast=0.0;
		Double doublenext=0.0;
		Double value=0.0;
		String sql=null;
		//不存在按月表则创建相应的表
		sql="CREATE TABLE IF NOT EXISTS  `pointdata_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`meterid` VARCHAR(16) DEFAULT NULL," +
				"`funcid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		//遍历所有数据库链接
		for(OriginadataEntity orgin:listOrigin){
			try{
				//获取数据库连接
				dyConfiguration=new HibernateConfiguration(orgin);
				dyConfiguration.addAnnotatedClass(PointdataEntity.class);
				TempSessionFactory.reflashSessionFactory(dyConfiguration); 
				sessionPoint=TempSessionFactory.getSessionFactory().openSession();
				//读取半个月前的数据
				sqllast="select * from "+orgin.getTbname()+" where receivetime<'"+sdf.format(calendar.getTime())+"'";
				sqlnext="select * from "+orgin.getTbname()+" where receivetime>'"+sdf.format(calendar.getTime())+"'";
				calendar.add(Calendar.MINUTE, -CommonUtil.HARF_HOUR);
				sqllast+=" and receivetime>'"+sdf.format(calendar.getTime())+"'";
				calendar.add(Calendar.MINUTE, CommonUtil.HARF_HOUR*2);
				sqlnext+=" and receivetime<'"+sdf.format(calendar.getTime())+"'";
				for(MeterEntity meter:listMeter){
					sqllast+=" and meterid='"+meter.getMeterid()+"' order by receivetime desc limit 0,1";
					sqlnext+=" and meterid='"+meter.getMeterid()+"' order by receivetime asc limit 0,1";
					//获取半小时内的数据
					query=sessionPoint.createSQLQuery(sqllast);
					query.addEntity(PointdataEntity.class);
					listlast=query.list();
					query=sessionPoint.createSQLQuery(sqlnext);
					query.addEntity(PointdataEntity.class);
					listnext=query.list();
					if(listlast!=null&&listnext!=null){
						//获取整点最近的两个数据
						pointdatalast=listlast.get(0);
						pointdatanext=listnext.get(0);
						minutelast=60-pointdatalast.getReceivetime().getMinutes();
						minutenext=pointdatanext.getReceivetime().getMinutes();
						doublelast=pointdatalast.getData();
						doublenext=pointdatanext.getData();
						sql="insert into pointdata_"+simpledateformat.format(calendar.getTime())+"(id,meterid,funcid,receivetime,data) values(?,?,?,?,?)";
						query=getSession().createSQLQuery(sql);
						query.setString(0, (String) new UUIDGenerator().generate());
						query.setString(1, meter.getMeterid());
						query.setString(2, pointdatanext.getFuncid());
						query.setDate(3, calendar.getTime());
//						pointdata=new PointdataEntity();
//						pointdata.setMeterid(meter.getMeterid());
//						pointdata.setFuncid(pointdata.getFuncid());
//						pointdata.setReceivetime(calendar.getTime());
						if(doublelast>doublenext){
							value=((doublenext*100)+(doublelast-doublenext)*100*(minutenext/(minutelast+minutenext)))/100;
						}else{
							value=((doublelast*100)+(doublenext-doublelast)*100*(minutelast/(minutelast+minutenext)))/100;
						}
//						pointdata.setData(value);
						query.setDouble(4, value);
						query.executeUpdate();
					}
					sessionPoint.close();
				}
			}catch(Exception e){
				
			}finally{
				
				
				
			}
		}
	}
	
	
	
	@Override
	public void exportIntegralPointData(Calendar cal) {
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		try {
			calendar.setTime(sdf.parse(sdf.format(cal.getTime())));
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		//获取所有数据库连接
		List<OriginadataEntity> listOrigin=originadataService.findHql(" from OriginadataEntity ");
		//获取所有表具信息
		List<MeterEntity> listMeter=meterService.findHql(" from MeterEntity ");
		Session sessionPoint=null;
		Session session=null;
		HibernateConfiguration dyConfiguration=null;
		List listlast=null;
		List listnext=null;
		SimpleDateFormat sdfcheck=new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Query query=null;
		String receiveTime=null;
		try {
			receiveTime=sdf.format(calendar.getTime());
		} catch (Exception e1) {
		}
		Interceptor interceptor=new MyInterceptor("pointdata","pointdata_"+simpledateformat.format(calendar.getTime()));
		String sql="CREATE TABLE IF NOT EXISTS  `pointdata_" +simpledateformat.format(calendar.getTime())+
				"`(`id` VARCHAR(32) DEFAULT NULL," +
				"`meterid` VARCHAR(32) DEFAULT NULL," +
				"`funcid` VARCHAR(32) DEFAULT NULL," +
				"`receivetime` DATETIME DEFAULT NULL," +
				"`data` DOUBLE DEFAULT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		this.executeSql(sql);
		Date startDatelast=calendar.getTime();
		//需求中取数据的时候如果半个小时内缺失数据，则可以扩展取三个点上的数据，则直接获取一个半小时以内的第一条数据作为可候补数据进行计算
		calendar.add(Calendar.MINUTE, -CommonUtil.HARF_HOUR*3);
		Date endDatelast=calendar.getTime();
		calendar.add(Calendar.MINUTE, CommonUtil.HARF_HOUR*3);
		Date startDatenext=calendar.getTime();
		calendar.add(Calendar.MINUTE, CommonUtil.HARF_HOUR*3);
		Date endDatenext=calendar.getTime();
		PointdataEntity pointdata=null;
		Object[] objlast=null;
		Object[] objnext=null;
		Double minutelast=0.0;
		Double minutenext=0.0;
		Double doublelast=0.0;
		Double doublenext=0.0;
		Double value=0.0;
		String hqllast=null;
		String hqlnext=null;
		long count=0;
		//遍历所有数据库链接
		for(OriginadataEntity orgin:listOrigin){
			try{
				//获取数据库连接
				dyConfiguration=null;
				sessionPoint=null;
				dyConfiguration=new HibernateConfiguration(orgin);
				if(CommonUtil.checkDb(dyConfiguration.getProperty("hibernate.dialect"), dyConfiguration.getProperty("hibernate.connection.url"), dyConfiguration.getProperty("hibernate.connection.username"), dyConfiguration.getProperty("hibernate.connection.password"))){
					sessionPoint=commonDao.getSession();
					dyConfiguration=null;
				}else{
					dyConfiguration.addAnnotatedClass(PointdataEntity.class);
					TempSessionFactory.reflashSessionFactory(dyConfiguration); 
					//读取半个月前的数据
					Interceptor inter=new MyInterceptor("pointdata",orgin.getTbname());
					sessionPoint=TempSessionFactory.getSessionFactory().withOptions().interceptor(inter).openSession();
				}
				String funcid=null;
				List<PointdataEntity> listPointdate=null;
				//首先查询当前表中是否有相应月份数据，如果有则进行计算，否则直接跳过该表
				hqllast=" select count(*) as num from PointdataEntity where date_format(receivetime,'%Y-%m')=:receivetime";
				query=sessionPoint.createQuery(hqllast);
				query.setString("receivetime", sdfcheck.format(startDatelast));
				count=(Long) query.uniqueResult();
				//System.out.println("count========"+count);
				if(count!=0){
					hqllast="select meterid,funcid,receivetime,data from PointdataEntity where receivetime <:startDate and receivetime>:endDate and meterid=:meterid order by receivetime desc";
					hqlnext="select meterid,funcid,receivetime,data from PointdataEntity where receivetime >:startDate and receivetime<:endDate and meterid=:meterid order by receivetime asc";
					for(MeterEntity meter:listMeter){
						query=sessionPoint.createQuery(hqllast);
						query.setTimestamp("startDate", startDatelast);
						query.setTimestamp("endDate", endDatelast);
						query.setString("meterid", meter.getMeterid());
						query.setFirstResult(0);
						query.setMaxResults(1);
						//读取半小时前的数据
						listlast=query.list();
						query=sessionPoint.createQuery(hqlnext);
						query.setTimestamp("startDate", startDatenext);
						query.setTimestamp("endDate", endDatenext);
						query.setString("meterid", meter.getMeterid());
						query.setFirstResult(0);
						query.setMaxResults(1);
						//获取半小时后的数据
						listnext=query.list();
						if(listlast!=null&&listlast.size()!=0&&listnext!=null&&listnext.size()!=0){
							//获取整点最近的两个数据
							objlast=(Object[]) listlast.get(0);
							objnext=(Object[]) listnext.get(0);
							//获取两个点的日期计算出中间整点的用电量
							Date lastdate=(Date) objlast[2];
							Date nextdate=(Date) objnext[2];
							minutelast=(double) (startDatenext.getTime()-lastdate.getTime());
							minutenext=(double) (nextdate.getTime()-startDatenext.getTime());
							doublelast=(Double) objlast[3];
							doublenext=(Double) objnext[3];
							if(doublelast>doublenext){
								value=((doublenext*100)+(doublelast-doublenext)*100*(minutenext/(minutelast+minutenext)))/100;
							}else{
								value=((doublelast*100)+(doublenext-doublelast)*100*(minutelast/(minutelast+minutenext)))/100;
							}
							if(CommonUtil.NEWID){
								funcid=funcService.queryIdByFuncid(Integer.valueOf((String) objnext[1])).getId();
							}else{
								funcid=(String) objnext[1];
							}
							try{
								session=this.getSession(interceptor);
								query=session.createQuery(" from PointdataEntity where meterid=:meterid and funcid=:funcid and date_format(receivetime,'%Y-%m-%d %H')=:receivetime");
								if(CommonUtil.NEWID){
									query.setString("meterid", meter.getId());
								}else{
									query.setString("meterid", meter.getMeterid());
								}
								query.setString("funcid", funcid);
								query.setString("receivetime", receiveTime);
								listPointdate=query.list();
								Transaction tran=null;
								if(listPointdate!=null&&listPointdate.size()!=0){
									pointdata=listPointdate.get(0);
									pointdata.setData(value);
									//将获取到的数据保存到数据库相应的月份表里边
									tran=session.beginTransaction();
									session.update(pointdata);
									tran.commit();
									//System.out.println("要保存的pointdata===:"+pointdata.getFuncid()+":"+pointdata.getMeterid()+":"+pointdata.getData()+":"+pointdata.getReceivetime());
								}else{
									pointdata=new PointdataEntity();
									if(CommonUtil.NEWID){
										pointdata.setMeterid(meter.getId());
									}else{
										pointdata.setMeterid(meter.getMeterid());
									}
									pointdata.setFuncid(funcid);
									pointdata.setReceivetime(sdf.parse(receiveTime));
									pointdata.setData(value);
									//将获取到的数据保存到数据库相应的月份表里边
									tran=session.beginTransaction();
									session.save(pointdata);
									tran.commit();
									//System.out.println("要保存的pointdata===:"+pointdata.getFuncid()+":"+pointdata.getMeterid()+":"+pointdata.getData()+":"+pointdata.getReceivetime());
								}
							}catch(Exception e){
								System.out.println("导入整点数异常exportpointdata======:"+e.getMessage());
							}finally{
								if(session!=null){
									session.clear();
									session.close();
								}
							}
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("查找原始表异常exportpointdata======:"+e.getMessage());
			}finally{
				if(sessionPoint!=null){
					sessionPoint.close();
				}
			}
		}
		
	}
	@Override
	public HourdataEntity queryHourData(String meterid,Calendar cal) {
		Calendar calendar=Calendar.getInstance();//获取当前日期
		calendar.setTime(cal.getTime());
		Double value=0.0;
		Double valuelast=0.0;
		Double valuenow=0.0;
		HourdataEntity hourdata=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		Date receiveTime=null;
		Session session=null;
		try{
			receiveTime=sdf.parse(sdf.format(calendar.getTime()));
			Query query=null;
			//进行表名替换拦截器配置
			Interceptor interceptor=new MyInterceptor("pointdata","pointdata_"+simpledateformat.format(calendar.getTime()));
			session=this.getSession(interceptor);
			//获取本小时的数据
			String hql=" from PointdataEntity where meterid=:meterid and receivetime=:receivetime";
			query=session.createQuery(hql);
			query.setString("meterid", meterid);
			query.setTimestamp("receivetime", sdf.parse(sdf.format(calendar.getTime())));
			query.setFirstResult(0);
			query.setMaxResults(1);
			List<PointdataEntity> list=query.list();
			//获取上一小时数据
			query=session.createQuery(hql);
			calendar.add(Calendar.HOUR, -1);
			query.setString("meterid", meterid);
			query.setTimestamp("receivetime", sdf.parse(sdf.format(calendar.getTime())));
			query.setFirstResult(0);
			query.setMaxResults(1);
			List<PointdataEntity> listlast=query.list();
			if(listlast!=null&&listlast.size()==1){
				hourdata=new HourdataEntity();
				hourdata.setFuncid(listlast.get(0).getFuncid());
				valuelast=listlast.get(0).getData();
				if(list!=null&&list.size()==1){
					hourdata=new HourdataEntity();
					valuenow=list.get(0).getData();
					hourdata.setFuncid(list.get(0).getFuncid());
					hourdata.setReceivetime(calendar.getTime());
					value=valuenow-valuelast;
				}
			}
		}catch(Exception e){
			
		}finally{
			if(session!=null){
				session.clear();
				session.close();
			}
		}
		if(hourdata!=null){
			try {
				hourdata.setMeterid(meterid);
				hourdata.setReceivetime(receiveTime);
				hourdata.setData(value>0?value:0);
			} catch (Exception e) {
			}
		}
		return hourdata;
	}
	
	@Override
	public void importPointData(String startDate,String endDate){
		
	}
	@Override
	public void breakLineCheck(){
		Calendar calendar=Calendar.getInstance();//获取当前日期
		Calendar cal=Calendar.getInstance();
		List<MeterEntity> list=meterService.getList(MeterEntity.class);
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Session session=null;
		String hql=null;
		Query query=null;
		List<PointdataEntity> listdata=null;
		Interceptor interceptor=null;
		AlarminfoEntity alarm=null;
		for(MeterEntity meter:list){
			try{
				alarm=new AlarminfoEntity();
				if(CommonUtil.NEWID){
					alarm.setMeterid(meter.getId());
				}else{
					alarm.setMeterid(meter.getMeterid());
				}
	    		alarm.setInfo(meter.getMetertext()+"断线提醒");
	    		alarm.setAlarmtime(cal.getTime());
				interceptor=new MyInterceptor("pointdata","pointdata_"+simpledateformat.format(calendar.getTime()));
				session=this.getSession(interceptor);
				//获取本小时的数据
				hql=" from PointdataEntity where meterid=:meterid and receivetime>=:startDate and receivetime<=:endDate";
				query=session.createQuery(hql);
				if(CommonUtil.NEWID){
					query.setString("meterid", meter.getId());
				}else{
					query.setString("meterid", meter.getMeterid());
				}
				query.setString("endDate", sdf.format(calendar.getTime()));
				//查询4小时前的数据
				calendar.add(Calendar.HOUR_OF_DAY, -24);
				query.setString("startDate", sdf.format(calendar.getTime()));
				listdata=query.list();
			}catch(Exception e){
				
			}finally{
				if(session!=null){
					session.clear();
					session.close();
				}
			}
			if(listdata!=null&&listdata.size()!=0){
				calendar.add(Calendar.HOUR_OF_DAY, 24);
				meter.setAlarm("");
				meterService.saveOrUpdate(meter);
			}else{
				if(alarm.getAlarmtime().getMonth()!=calendar.getTime().getMonth()){
					try{
						interceptor=new MyInterceptor("pointdata","pointdata_"+simpledateformat.format(calendar.getTime()));
						session=this.getSession(interceptor);
						hql=" from PointdataEntity where meterid=:meterid and receivetime>=:startDate and receivetime<=:endDate";
						query=session.createQuery(hql);
						if(CommonUtil.NEWID){
							query.setString("meterid", meter.getId());
						}else{
							query.setString("meterid", meter.getMeterid());
						}
						query.setString("startDate", sdf.format(calendar.getTime()));
						calendar.add(Calendar.HOUR_OF_DAY, 24);
						query.setString("endDate", sdf.format(calendar.getTime()));
						listdata=query.list();
					}catch(Exception e){
						
					}finally{
						if(session!=null){
							session.clear();
							session.close();
						}
					}
					if(listdata!=null&&listdata.size()!=0){
						meter.setAlarm("");
						meterService.saveOrUpdate(meter);
					}else{
						
						//判断是否要存报警表
	            		if(meter.getAlarm()!=null && meter.getAlarm().equals("1")  ){
	            			//如果是已断线状态，不保存报警记录。
	            		}else{
	            			//非断线状态下保存报警记录
	            			alarm.setType("1");
		            		alarminfoService.save(alarm);
	            		}
	            		
						
						meter.setAlarm("1");
						meterService.saveOrUpdate(meter);
	            		
	            		
					}
				}else{
					calendar.add(Calendar.HOUR_OF_DAY, 24);
					
					
					//判断是否要存报警表
            		if(meter.getAlarm()!=null && meter.getAlarm().equals("1")  ){
            			//如果是已断线状态，不保存报警记录。
            		}else{
            			//非断线状态下保存报警记录
            			alarm.setType("1");
	            		alarminfoService.save(alarm);
            		}
					
					meter.setAlarm("1");
					meterService.saveOrUpdate(meter);
            		
				}
			}
		}
	}
	
}