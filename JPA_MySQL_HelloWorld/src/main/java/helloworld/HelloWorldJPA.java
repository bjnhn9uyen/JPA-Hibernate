package helloworld;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import model.Message;

/*
 * The imports above uses Hibernate as a provider of the Java Persistence API. Because the second major version of the Java Persistence API
 * specification (JPA) implemented by Hibernate. Hibernate supports all the standardized mappings, queries, and programming interfaces. The core of
 * Hibernate is now called object/relational mapping (ORM). The hibernate-entitymanager module (in Maven pom file) includes transitive dependencies on
 * other modules you’ll need, such as hibernate-core and the Java Persistence interface stubs. Using Hibernate EntityManager, you can fall back to
 * Hibernate when a plain Hibernate interface or even a JDBC Connection is needed.
 */
public class HelloWorldJPA {

	public static void main(String[] args) throws Exception {

		/*
		 * First you need an EntityManagerFactory to talk to your database. This API represents your persistence unit; most applications have one
		 * EntityManagerFactory for one configured persistence unit. The factory is thread-safe, and all code in your application that accesses the
		 * database should share it
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_HelloWorld");

		/**
		 * First unit of work. (CREATE)
		 * 
		 * Begin a new session with the database by creating an EntityManager, this is your context for all persistence operations.
		 */
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		/* Create a new instance of the mapped domain model class Message, and set its text property. */
		Message message = new Message();
		message.setText("Hello World with JPA !");

		/*
		 * Enlist the transient instance with your persistence context; you make it persistent. Hibernate now knows that you wish to store that data,
		 * but it doesn't necessarily call the database immediately, however.
		 */
		em.persist(message);

		/*
		 * Commit the transaction, Hibernate now automatically executes the necessary SQL INSERT statement. Hibernate inserts a row in the MESSAGES
		 * table, with an automatically generated value for the MESSAGE_ID primary key column, and the MESSAGE_TEXT value.
		 */
		tx.commit();
		// INSERT into MESSAGES (MESSAGE_ID, MESSAGE_TEXT) values (n, 'Hello World with JPA')

		/* If you create an EntityManager, you must close it. */
		em.close();

		/**
		 * Second unit of work. (READ)
		 * 
		 * Loading data with a database query
		 */
		EntityManager newEm = emf.createEntityManager();
		EntityTransaction newTx = newEm.getTransaction();
		newTx.begin();

		/*
		 * Execute a query to retrieve all instances of Message from the database. The query language in this query string isn’t SQL, it’s the Java
		 * Persistence Query Language (JPQL). It's doesn’t refer to the database table name, but to the persistent class name. If you map the class to
		 * a different table, the query will still work.
		 */
		List<Message> messages = newEm.createQuery("select m from Message m order by m.text asc", Message.class).getResultList();
		// SELECT * from MESSAGES order by MESSAGE_TEXT asc

		System.out.println(messages.size() + " message(s) found:");

		for (Object m : messages) {
			Message loadedMsg = (Message) m;
			System.out.println(loadedMsg.getText());
		}

		/**
		 * Still second unit of work. (UPDATE)
		 * 
		 * You can change the value of a property, Hibernate will detect this automatically because the loaded Message is still attached to the
		 * persistence context it was loaded in.
		 */
		messages.get(0).setText("Take me to your leader !");

		/*
		 * On commit, Hibernate checks the persistence context for dirty state and executes the SQL UPDATE automatically to synchronize the in-memory
		 * with the database state.
		 */
		newTx.commit();
		// UPDATE MESSAGE set TEXT = 'Take me to your leader!' where ID = 1

		newEm.close();

		/* Shutting down the application */
		emf.close();
	}

}
