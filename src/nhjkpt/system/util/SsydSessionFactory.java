package nhjkpt.system.util;

import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SsydSessionFactory {
	public static HibernateConfiguration configurationSsyd = new HibernateConfiguration();
	public static SessionFactory sessionFactorySsyd;

	public static void reflashSessionFactory(HibernateConfiguration tempConfiguration) {
		try {
			
				
			configurationSsyd.reset();
			configurationSsyd = tempConfiguration;
				sessionFactorySsyd = configurationSsyd.buildSessionFactory();
				

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Session getSession(Interceptor inter) {
		return sessionFactorySsyd.withOptions().interceptor(inter).openSession();
	}
	public static Session getSession(){
		return sessionFactorySsyd.openSession();
	}
}
