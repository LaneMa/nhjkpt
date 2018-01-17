package nhjkpt.configmanage.service.impl.alarminfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.alarminfo.AlarminfoEntity;
import nhjkpt.configmanage.entity.metermanage.MeterEntity;
import nhjkpt.configmanage.service.alarminfo.AlarminfoServiceI;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;

@Service("alarminfoService")
@Transactional
public class AlarminfoServiceImpl extends CommonServiceImpl implements AlarminfoServiceI {
	//判断此表具是否已存有断线记录
	public boolean existDxAlarm (String  meterid) {
		boolean ret=false;
		Session session=null;
		try{
			session=getSession();
			Query query=session.createQuery(" from AlarminfoEntity where meterid=:meterid and type=1");
			query.setString("meterid", meterid);
			List<AlarminfoEntity> listdata= query.list();
			if(listdata!=null&&listdata.size()!=0){
				ret=true;
			}
		}catch(Exception e){
			
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return ret;
	}
}