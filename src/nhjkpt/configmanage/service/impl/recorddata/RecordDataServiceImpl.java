package nhjkpt.configmanage.service.impl.recorddata;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.recorddata.RecordDataEntity;
import nhjkpt.configmanage.service.recorddata.RecordDataServiceI;
import nhjkpt.system.util.CommonUtil;
import nhjkpt.system.util.MyInterceptor;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Service("recordDataService")
@Transactional
public class RecordDataServiceImpl extends CommonServiceImpl implements RecordDataServiceI {

	@Override
	public void splitShowStr(String str) {
		if(!CommonUtil.isNull(str)){
			Session session=null;
			try{
				Transaction tran=null;
				String[] code=str.split(";");
				String buildingid=code[0];
				String tablename="recorddata_";
				for(int i=buildingid.length();i<10;i++){
					tablename+="0";
				}
				String sql="CREATE TABLE IF NOT EXISTS  `" +tablename+buildingid+
						"`(`id` VARCHAR(32) DEFAULT NULL," +
						"`meterid` VARCHAR(32) DEFAULT NULL," +
						"`funcid` VARCHAR(32) DEFAULT NULL," +
						"`receivetime` DATETIME DEFAULT NULL," +
						"`data` DOUBLE DEFAULT NULL" +
						") ENGINE=InnoDB DEFAULT CHARSET=utf8";
				this.executeSql(sql);
				Interceptor interceptor=new MyInterceptor("recorddata",tablename+buildingid);
				session=commonDao.getSession(interceptor);
				RecordDataEntity record=null;
				Date date=CommonUtil.utcToDate(code[3]);
				String meterid=code[5];
				int add=0;
				for(int j=0,len=Integer.parseInt(code[6]);j<len;j++){
					record=new RecordDataEntity();
					record.setReceivetime(date);
					record.setMeterid(meterid);
					record.setFuncid(Integer.parseInt(code[7+j+add]));
					add+=1;
					record.setData(Double.parseDouble(code[7+j+add]));
					tran=session.beginTransaction();
					session.save(record);
					tran.commit();
				}
			}catch(Exception e){
				
			}finally{
				if(session!=null){
					session.close();
				}
			}
		}
	}
	
}