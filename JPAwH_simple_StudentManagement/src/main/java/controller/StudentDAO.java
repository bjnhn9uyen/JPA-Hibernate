package controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Student;
import persistence.HibernateUtil;

public class StudentDAO {

	public void createStudent(Student student) {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		/*
		 * The main difference between save() and persist() is that save() is Hibernate-proprietary, whereas persist() is a standard JPA method.
		 * Additionally, save() is guaranteed to assign and return an ID for the entity, whereas persist() is not. If you call them on entities that
		 * already exist in the database, they'll do nothing.
		 */
		session.persist(student);
//		session.save(student);
		transaction.commit();
	}

	public List<Student> readAllStudent() {
		List<Student> students = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		// get all Student entities from a List
		students = session.createQuery("from Student", Student.class).list();
		students.forEach(stud -> System.out.println(stud.getId() + "-" + stud.getFirstName() + "-" + stud.getLastName() + "-" + stud.getEmail()));
		return students;
	}

	public Student readStudentById(Long id) {
		Student student = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		// obtain an entity
		student = session.get(Student.class, id);
		System.out.println(student.getFirstName() + "-" + student.getLastName() + "-" + student.getEmail());
		return student;
	}

	public void updateStudent(Student student) {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		/*
		 * update() is used to attach a detached entity to the session. saveOrUpdate() is used to either save or update an entity depending on the
		 * state (new or detached) of the entity.
		 */
		session.update(student);
//		session.saveOrUpdate(student);
		transaction.commit();
	}

	public void deleteStudent(Student student) {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		/*
		 * Remove a persistent instance from the datastore. The argument may bean instance associated with the receiving Session or a transient
		 * instance with an identifier associated with existing persistent state.
		 */
		session.delete(student);
		transaction.commit();
	}

}
