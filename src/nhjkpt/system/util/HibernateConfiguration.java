package nhjkpt.system.util;

import nhjkpt.configmanage.entity.originadata.OriginadataEntity;

import org.framework.core.util.ResourceUtil;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;

@SuppressWarnings("deprecation")
public class HibernateConfiguration extends AnnotationConfiguration {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HibernateConfiguration() {
		super();
	}

	public void reset() {
		super.reset();
	}

	public HibernateConfiguration(OriginadataEntity orgin) {
		String connection_url = null;
		String dialect=null;
		String driverClass=null;
		if (orgin.getDbtype().equals("mysql")) {
			connection_url = "jdbc:mysql://" + orgin.getDbip()+":3306/" + orgin.getDbname();
			dialect="org.hibernate.dialect.MySQLDialect";
			driverClass="com.mysql.jdbc.Driver";
		} else if (orgin.getDbtype().equals("sqlserver")) {
			String instancename=ResourceUtil.getConfigByName("sqlserver_instance");
			if(instancename!=null && instancename.length()>0){
				connection_url = "jdbc:sqlserver://" + orgin.getDbip() + ";InstanceName="+ResourceUtil.getConfigByName("sqlserver_instance")+";DatabaseName=" + orgin.getDbname();
			}else{
				connection_url = "jdbc:sqlserver://" + orgin.getDbip() + ";DatabaseName=" + orgin.getDbname();
			}
			
			
			
			//dialect="org.hibernate.dialect.SQLServerDialect";
			dialect="nhjkpt.system.util.MySQLServerDialect";
			driverClass="com.microsoft.sqlserver.jdbc.SQLServerDriver";
			
		} else if (orgin.getDbtype().equals("oracle")) {
			connection_url = "jdbc:oracle:thin:@" + orgin.getDbip() + ":1521:" + orgin.getDbname();
			dialect="org.hibernate.dialect.OracleDialect";
			driverClass="oracle.jdbc.driver.OracleDriver";
		} else {
			throw new HibernateException("未知的数据库类型");
		}

		super.setProperty("hibernate.dialect", dialect);
		super.setProperty("hibernate.connection.url", connection_url);
		super.setProperty("hibernate.connection.driver_class", driverClass);
		super.setProperty("hibernate.connection.username", orgin.getDbuser());
		super.setProperty("hibernate.connection.password", orgin.getDbpwd());
	}

	public HibernateConfiguration(String dialect, String driverClass,
			String ipAddress, String port, String dataBaseName,
			String username, String password, String schema, String catalog) {
		String connection_url = "";
		if (dialect.indexOf("MySQL") > -1) {
			connection_url = "jdbc:mysql://" + ipAddress + "/" + dataBaseName;
		} else if (dialect.indexOf("SQLServer") > -1) {
			connection_url = "jdbc:sqlserver://" + ipAddress + ":" + port
					+ ";DataBaseName=" + dataBaseName;
		} else if (dialect.indexOf("Oracle") > -1) {
			connection_url = "jdbc:oracle:thin:@" + ipAddress + ":" + port
					+ ":" + dataBaseName;
		} else {
			throw new HibernateException("The dialect was not allowed.==fd=="
					+ dialect);
		}
		super.setProperty("hibernate.dialect", dialect);
		super.setProperty("hibernate.connection.url", connection_url);
		super.setProperty("hibernate.connection.driver_class", driverClass);
		super.setProperty("hibernate.connection.username", username);
		super.setProperty("hibernate.connection.password", password);
		super.setProperty("hibernate.default_schema", schema);
		super.setProperty("hibernate.default_catalog", catalog);
		super.setProperty("hibernate.show_sql", "true");
	}
}