package nhjkpt.system.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 静态工具类
 * @author qf
 *
 */
public class CommonUtil {
	public static final int HARF_HOUR=30;
	public static final String SHOW_TEXT_NULL="无";
	public static String START_DATE=null;
	public static String END_DATE=null;
	public static boolean IMPORT=false;
	public static final String ADMIN="admin";
	
	public static final boolean NEWID=false;
	
	public static final String MAC_ADDRESS="Server.macaddress";
	public static final String CPU_ID="Server.cpu";
	public static final String BOARD_ID="Server.board";
	public static final String PRODUCT_NUM="product.num";
	public static final String PRODUCT_FUNCTION="product.function";
	public static final String VERIFY_DATE="verifyDate";
	public static final String VERIFY_SIGN="sign";
	public static final String SIGN_SERVER="server";
	public static final String SIGN_NUM="num";
	public static final String SIGN_FUNCTION="function";
	public static final String EQ="==";
	public static final String LICENSE_SPLIT="\r\n";
	public static final String LICENSE_EQ="=";
	public static final String WATER_CODE="9%";
	public static final String CALORIE_CODE="8%";
	public static final Map<String,Boolean> server=new HashMap<String, Boolean>();
	public static Map<String,Object> showmap=new HashMap<String, Object>();
	public static Map<String,Date> meters=new HashMap<String, Date>();
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("dbconfig");
	
	public static boolean isNull(String str){
		if(str!=null&&str.length()!=0){
			return false;
		}else{
			return true;
		}
	}
	public static boolean isNull(List list){
		if(list!=null&&list.size()!=0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 数据库类型
	 * @return
	 */
	public static List<Map<String,Object>> listDataBase(){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("dbtype", "mysql");
		list.add(map);
		map=new HashMap<String, Object>();
		map.put("dbtype", "oracle");
		list.add(map);
		map=new HashMap<String, Object>();
		map.put("dbtype", "sqlserver");
		list.add(map);
		return list;
	}
	public static Date utcToDate(String utc){
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			return sdf.parse(utc.substring(0, 14));
		}catch (Exception e) {
		}
		return null;
	}
	public static Double formateResult(Object obj){
		if(obj!=null){
			Double d=new Double(new java.text.DecimalFormat("#.00").format(obj));
			if(d>0){
				return d;
			}else{
				return 0.0;
			}
		}else{
			return 0.0;
		}
	}
	public static boolean checkDb(String dialect,String url,String username,String password){
		if(bundle.getString("jdbc.url.nhjkpt").contains(url)&&bundle.getString("hibernate.dialect").equals(dialect)&&bundle.getString("jdbc.username.nhjkpt").equals(username)&&bundle.getString("jdbc.password.nhjkpt").equals(password)){
			return true;
		}else{
			return false;
		}
	}
}
