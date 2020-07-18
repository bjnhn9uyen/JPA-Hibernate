package persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class HibernateUtil {

	/*
	 * If you want to use 'persistance.xml' without 'hibernate.cfg.xml' you should use EntityManager instead of Session (see JPA_MySQL_HelloWorld).
	 * Using 'hibernate.cfg.xml' is more sufficient and convenient (with GUI) than using only 'persistance.xml'. The 'hibernate.cfg.xml' configuration
	 * file contains database connection details, as well as a list of persistent classes and other configuration properties, similar to
	 * 'persistence.xml'
	 */
	public static SessionFactory getSessionFactory() {

		SessionFactory sessionFactory = new MetadataSources(new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build())
				.buildMetadata().buildSessionFactory();
		return sessionFactory;
	}

}
