package nhjkpt.system.util;

import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TempSessionFactory {
	private static HibernateConfiguration configurationTemp = new HibernateConfiguration();
	private static SessionFactory sessionFactoryTemp;

	@SuppressWarnings("deprecation")
	public static void reflashSessionFactory(HibernateConfiguration tempConfiguration) {
		try {
			if (tempConfiguration.getProperty("hibernate.dialect").equals(
					configurationTemp.getProperty("hibernate.dialect"))
					&& tempConfiguration
							.getProperty("hibernate.connection.url")
							.equalsIgnoreCase(
									configurationTemp
											.getProperty("hibernate.connection.url"))
					&& tempConfiguration
							.getProperty("hibernate.connection.username")
							.equals(configurationTemp
									.getProperty("hibernate.connection.username"))
					&& tempConfiguration
							.getProperty("hibernate.connection.password")
							.equals(configurationTemp
									.getProperty("hibernate.connection.password"))) {
				
				

			} else {
				configurationTemp = tempConfiguration;
				
				sessionFactoryTemp = configurationTemp.buildSessionFactory();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactoryTemp;
	}

//	public  Session getSession(Interceptor inter) {
//		return sessionFactory.withOptions().interceptor(inter).openSession();
//	}
//	public  Session getSession(){
//		return sessionFactory.getCurrentSession();
//		
//	}
}
