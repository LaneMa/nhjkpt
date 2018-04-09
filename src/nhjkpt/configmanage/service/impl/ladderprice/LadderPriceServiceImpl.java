package nhjkpt.configmanage.service.impl.ladderprice;

import java.text.SimpleDateFormat;
import java.util.List;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.hibernate.Interceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhjkpt.configmanage.entity.hourdata.HourdataEntity;
import nhjkpt.configmanage.entity.ladderprice.LadderPriceEntity;
import nhjkpt.configmanage.service.ladderprice.LadderPriceServiceI;
import nhjkpt.system.util.MyInterceptor;

@Service("ladderPriceService")
@Transactional
public class LadderPriceServiceImpl extends CommonServiceImpl implements LadderPriceServiceI {

	@Override
	public List<LadderPriceEntity> getEntityByType(Integer priceType) {
		List<LadderPriceEntity> list=null; 
//		Session session=null;
//		try{
//			session=getSession();
//			Query query=session.createQuery(" from LadderPriceEntity where priceType=:priceType");
//			query.setInteger("priceType", priceType);
//			list=query.list();
//		}catch(Exception e){
//		}finally{
//			if(session!=null){
//				session.clear();
//				session.close();
//			}
//		}
		list = this.findHql(" from LadderPriceEntity where priceType=? ", priceType);
		return list;
	}
	
}