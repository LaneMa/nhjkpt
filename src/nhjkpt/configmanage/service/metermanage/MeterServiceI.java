package nhjkpt.configmanage.service.metermanage;

import java.util.Calendar;

import org.framework.core.common.service.CommonService;

public interface MeterServiceI extends CommonService{

	String queryIdByMeterid(String meterid);
	public String queryMeterDaySum(Calendar cal,String meterId) ;
	/**
	 * 获取表具整点数据
	 * @param meterId
	 * @return
	 */
	public String queryMeterHourNum(Calendar cal,String meterId);
	public Integer getMetersByRoom(Integer roomid);
	public Integer getErrorMetersByRoom(Integer roomid);
	public Integer getOkMetersByRoom(Integer roomid);
	public Double getUseNhTodayByRoom(int roomid);

}
