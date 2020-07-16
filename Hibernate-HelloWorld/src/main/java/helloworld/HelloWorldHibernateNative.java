package helloworld;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import model.Message;

public class HelloWorldHibernateNative {

	/**
	 * Hibernate’s native bootstrap API is split into several stages, each giving you access to certain configuration aspects. This method is its most
	 * compact form of building a SessionFactory. This loads all settings from a Hibernate configuration file 'hibernate.cfg.xml'
	 */
	protected static SessionFactory simpleBoot() {
		SessionFactory sessionFactory = new MetadataSources(new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build())
				.buildMetadata().buildSessionFactory();
		return sessionFactory;
	}

	/**
	 * Similar to persistence.xml, this configuration file contains database connection details, as well as a list of persistent classes and other
	 * configuration properties.
	 */
	protected static SessionFactory createSessionFactory() {

		/* This builder helps you create the immutable service registry with chained method calls. */
		StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

		/*
		 * Configure the services registry by applying settings.
		 * 
		 * If you want to externalize your service registry configuration, you can load settings from a properties file on the classpath with
		 * StandardServiceRegistryBuilder#loadProperties(file).
		 */
		serviceRegistryBuilder

				.applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")

				.applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/hibernate")

				.applySetting("hibernate.connection.username", "root")

				.applySetting("hibernate.connection.password", "admin")

//				.applySetting("hibernate.show_sql", "true")
//
//				.applySetting("hibernate.format_sql", "true")
//
//				.applySetting("hibernate.use_sql_comments", "true")

				.applySetting("hibernate.hbm2ddl.auto", "create-drop");

		/*
		 * Enable JTA (this is a bit crude because Hibernate devs still believe that JTA is used only in monstrous application servers and you'll
		 * never see this code).
		 */
//		serviceRegistryBuilder.applySetting(Environment.TRANSACTION_COORDINATOR_STRATEGY, JtaTransactionCoordinatorBuilderImpl.class);

		/* Configure the metadata sources. This requires service registry */
		ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
		MetadataSources metadataSources = new MetadataSources(serviceRegistry);

		/* Add your persistent classes to the (mapping) metadata sources. */
		metadataSources.addAnnotatedClass(model.Message.class);

		/* The MetadataSources API has many methods for adding mapping sources */
		// Add hbm.xml mapping files
		metadataSources.addFile(new File("src/main/java/model/Message.hbm.xml").getAbsolutePath());

		// Read all hbm.xml mapping files from a JAR
		// metadataSources.addJar(...)

		/*
		 * The next stage of the boot procedure is building all the metadata needed by Hibernate, with the MetadataBuilder you obtained from the
		 * metadata sources
		 */
		MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder();
		Metadata metadata = metadataBuilder.build();

		/*
		 * You can then query the metadata to interact with Hibernate’s completed configuration programmatically, or continue and build the final
		 * SessionFactory
		 */
		SessionFactory sessionFactory = metadata.buildSessionFactory();

		return sessionFactory;
	}

	public static void main(String[] args) {
		SessionFactory sessionFactory = createSessionFactory();

		/**
		 * First unit of work.
		 */
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Message message = new Message();
		message.setText("Hello World with Hibernate native !");

		/* The native Hibernate API is very similar to the standard Java Persistence API and most methods have the same name. */
		session.persist(message);

		/*
		 * Hibernate synchronizes the session with the database and closes the "current" session on commit of the bound transaction automatically.
		 */
		tx.commit();
		// INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World with Hibernate native !')

		/**
		 * Second unit of work
		 */
		Session secondSession = sessionFactory.openSession();
		Transaction secondTransaction = secondSession.beginTransaction();

		/*
		 * A Hibernate criteria query is a type-safe programmatic way to express queries, automatically translated into SQL.
		 */
		List<Message> messages = secondSession.createQuery("from Message m order by m.text asc").list();
		// SELECT * from MESSAGE order by TEXT asc

		System.out.println(messages.size() + " message(s) found:");

		for (Iterator<Message> iter = messages.iterator(); iter.hasNext();) {
			Message loadedMsg = (Message) iter.next();
			System.out.println(loadedMsg.getText());
		}

		secondTransaction.commit();

		/**
		 * Third unit of work
		 */
		Session thirdSession = sessionFactory.openSession();
		Transaction thirdTransaction = thirdSession.beginTransaction();

		/* message.getId() holds the identifier value of the first message */
		Message loadedMessage = (Message) thirdSession.get(Message.class, message.getId());
		loadedMessage.setText("Take me to your leader !");

		thirdTransaction.commit();
	}

}
