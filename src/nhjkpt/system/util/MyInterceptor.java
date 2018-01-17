package nhjkpt.system.util;

import org.hibernate.EmptyInterceptor;
/**
 * 自定义hibernate拦截器
 * @author qf
 *
 */
public class MyInterceptor extends EmptyInterceptor {
	//原表名称
	private String oldTableName;
	//新表名称
	private String[] newTableName;

	public MyInterceptor(String oldTableName,String... newTableName){
		super();
		this.oldTableName=oldTableName;
		this.newTableName=newTableName;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String onPrepareStatement(String sql) {
		String newSql="";
		if(newTableName!=null&&newTableName.length!=0){
			if(newTableName.length==1){
				newSql=sql.replaceAll(oldTableName, newTableName[0]);
			}else{
				for(int i=0,l=newTableName.length;i<l;i++){
					if(i!=0){
						newSql+=" union all ";
					}
					newSql+="("+sql.replaceAll(oldTableName, newTableName[i])+")";
				}
				String[] clos=sql.substring(sql.indexOf("select")+6, sql.indexOf("from")).split(",");
				String[] ass=null;
				String groupby="";
				if(sql.indexOf("group by")>-1){
					groupby=sql.substring(sql.indexOf("group by"));
				}
				for(int j=0;j<clos.length;j++){
					ass=clos[j].split(" as ");
					if(ass[0].indexOf(")")>-1){
						sql=sql.replaceFirst(ass[0].substring(ass[0].indexOf(".")+1, ass[0].indexOf(")")),ass[1]);
						groupby=groupby.replaceFirst(ass[0].substring(ass[0].indexOf(".")+1, ass[0].indexOf(")")),ass[1]);
					}else{
						sql=sql.replaceFirst(ass[0].substring(ass[0].indexOf(".")+1),ass[1]);
						groupby=groupby.replaceFirst(ass[0].substring(ass[0].indexOf(".")+1),ass[1]);
					}
				}
				sql=sql.substring(0, sql.indexOf("where"));
				sql+=groupby;
				sql=sql.replaceAll(oldTableName, "("+newSql+")");
				newSql=sql;
			}
		}
		return newSql;
	}
	
	public String getOldTableName() {
		return oldTableName;
	}
	public void setOldTableName(String oldTableName) {
		this.oldTableName = oldTableName;
	}
	public String[] getNewTableName() {
		return newTableName;
	}
	public void setNewTableName(String[] newTableName) {
		this.newTableName = newTableName;
	}
	
}
